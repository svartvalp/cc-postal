<template>
    <div id="app">

        <v-app id="inspire">
            <Header
                    :drawerState="drawerState"
                    :isAuthenticated="isAuthenticated"
                    @drawBar="drawBar"
            />
            <NavigationBar
                    :drawerState="drawerState"
                    @logout="logout"
                    v-if="isAuthenticated"
            />
            <MainPanel
                    :user="user"
                    @login="login"
                    @logout="logout"
            />
            <Footer/>
        </v-app>
    </div>
</template>

<script>
    import Header from '@/components/root/Header'
    import NavigationBar from '@/components/root/NavigationBar'
    import Footer from '@/components/root/Footer'
    import MainPanel from '@/components/root/MainPanel'

    export default {
        name: 'App',

        components: {
            Header,
            NavigationBar,
            MainPanel,
            Footer
        },
        props: {
            source: String,
        },
        data() {
            return {
                jwt: localStorage.getItem('jwt'),
                drawerState: false,
                isAuthenticated: false,
                user: {}
            }
        },
        methods: {
            drawBar(drawerState) {
                this.drawerState = !drawerState;
            },
            logout() {
                console.log('logout')
                localStorage.clear();
                this.$http.defaults.headers.common['Authorization'] = '';
                this.$router.push('/login')
                this.isAuthenticated = false
                this.drawerState = false
            },
            login(user) {
                this.isAuthenticated = true
                this.user = user
                console.log(user)
            }
        },
        created() {
            let token = localStorage.getItem('jwt')
            if (token != null) {
                this.$http.defaults.headers.common['Authorization'] = token
                this.$http
                    .get(`/user`)
                    .then((response) => {
                        this.isAuthenticated = true
                        this.user = response.data
                    })
                    .catch(() => {
                        this.$http.defaults.headers.common['Authorization'] = ''
                        this.logout()
                    })
            }
        }
    };
</script>

