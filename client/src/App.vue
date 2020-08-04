<template>
    <div id="app">

        <v-app id="inspire">
            <Header
                    :drawerState="drawerState"
                    @drawBar="drawBar"
            />
            <NavigationBar
                    :drawerState="drawerState"
            />
            <MainPanel :user="user" />
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
                drawerState: false,
              //TODO create loading user data from back end
              // mock user data
                user: {
                  id : 1,
                  addresses : [{
                    id : 1,
                    longitude : 12,
                    latitude : 13,
                    name : 'Russia, Moscow, Москва, округ Тимирязевский, 127206, Ул. Астрадамская 3'
                  },
                    {
                      id : 2,
                      longitude : 13,
                      latitude : 14,
                      name : 'bbbb'
                    }]
                }
            }
        },
        methods: {
            drawBar(drawerState) {
                this.drawerState = !drawerState;
            }
        }
    };
</script>

