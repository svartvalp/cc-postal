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
            <MainPanel />
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
                user: {}
            }
        },
        methods: {
            drawBar(drawerState) {
                this.drawerState = !drawerState;
            }
        }
    };
</script>

