<template>
    <v-navigation-drawer
            app
            dark
            v-model="drawerState"
    >
        <v-list dense>
            <v-list-item @click="homePage" link>
                <v-list-item-action>
                    <v-icon>mdi-home</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Домашняя страница</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item @click="departures" link>
                <v-list-item-action>
                    <v-icon>mdi-email</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Посылки</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item @click="account" link>
                <v-list-item-action>
                    <v-icon>mdi-account</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Личный кабинет</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item @click="exit" link>
                <v-list-item-action>
                    <v-icon>mdi-arrow-left-circle</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Выход</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
    export default {
        props: ['drawerState'],
        methods: {
            homePage() {
                this.$router.push('/')
            },
            departures() {
                this.$router.push('/departures')
            },
            account() {
                this.$router.push('/user')
            },
            exit() {
                this.$emit('logout')
            },
        }
    }
</script>