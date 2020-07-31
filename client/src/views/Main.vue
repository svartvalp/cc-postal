<template>
    <v-app id="inspire">
        <v-navigation-drawer
                v-model="drawer"
                app
                dark
        >
            <v-list dense>
                <v-list-item link>
                    <v-list-item-action>
                        <v-icon>mdi-home</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Home</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link>
                    <v-list-item-action>
                        <v-icon>mdi-email</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Departures</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link>
                    <v-list-item-action>
                        <v-icon>mdi-account</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Account</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link>
                    <v-list-item-action>
                        <v-icon>mdi-arrow-left-circle</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Log out</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar
                app
                color="amber lighten-3"
                dark
        >
            <v-app-bar-nav-icon @click.stop="drawer = !drawer" color="black"></v-app-bar-nav-icon>
            <v-toolbar-title class="black--text">cc-postal</v-toolbar-title>

        </v-app-bar>

        <v-main>
            <v-container
                    class="fill-height"
                    fluid
            >
                <v-row
                        align="center"
                        justify="center"
                >

                </v-row>
            </v-container>
        </v-main>
        <v-footer
                color="amber lighten-3"
                app
        >
            <v-spacer></v-spacer>
            <span class="black--text">&copy; {{ new Date().getFullYear() }}</span>
        </v-footer>
        <router-view />
    </v-app>
</template>

<script>

    export default {
        name: 'App',

        components: {
        },
        props: {
            source: String,
        },

        data: () => ({
            drawer: null,
        }),
    };
</script>
