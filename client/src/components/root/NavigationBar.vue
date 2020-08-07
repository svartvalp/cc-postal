<template>
    <v-navigation-drawer
            v-model="drawerState"
            app
            dark
    >
        <v-list dense>
            <v-list-item link @click="homePage">
                <v-list-item-action>
                    <v-icon>mdi-home</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Домашняя страница</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item link @click="departures">
                <v-list-item-action>
                    <v-icon>mdi-email</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Посылки</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item link @click="account">
                <v-list-item-action>
                    <v-icon>mdi-account</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                    <v-list-item-title>Личный кабинет</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
            <v-list-item link @click="exit">
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