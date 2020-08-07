<template>
    <v-app id="inspire">
        <v-main>
            <v-container
                    class="fill-height"
                    fluid
            >
                <v-row
                        align="center"
                        justify="center"
                >
                    <v-col
                            cols="12"
                            md="4"
                            sm="8"
                    >
                        <v-alert type="error" v-model="show">
                            Неверное имя пользователя или пароль
                        </v-alert>
                        <v-card class="elevation-12">
                            <v-toolbar
                                    color="amber lighten-3"
                                    dark
                                    flat
                            >
                                <v-spacer></v-spacer>
                                <v-toolbar-title class="black--text">Aвторизация</v-toolbar-title>
                                <v-spacer></v-spacer>
                            </v-toolbar>
                            <v-card-text>
                                <v-form>
                                    <v-text-field
                                            color="black"
                                            label="Логин"
                                            name="login"
                                            prepend-icon="mdi-account"
                                            type="text"
                                            v-model="userLoginData.login"
                                    ></v-text-field>

                                    <v-text-field
                                            color="black"
                                            id="password"
                                            label="Пароль"
                                            name="password"
                                            prepend-icon="mdi-lock"
                                            type="password"
                                            v-model="userLoginData.password"
                                    ></v-text-field>
                                </v-form>
                            </v-card-text>
                            <v-card-actions class="justify-center">
                                <v-btn @click="registerMove" color="amber lighten-3" id="register">Зарегистрироваться
                                </v-btn>
                                <v-btn @click="login" color="amber lighten-3" id="login">Войти</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    export default {
        data() {
            return {
                userLoginData: {
                    login: null,
                    password: null
                },
                show: false
            }
        },
        methods: {
            login() {
                this.$http
                    .post(`/login?username=${this.userLoginData.login}&password=${this.userLoginData.password}`)
                    .then((response) => {
                        localStorage.setItem('jwt', response.headers.authorization);
                        this.$http.defaults.headers.common['Authorization'] = response.headers.authorization
                        this.$http
                            .get(`/user`)
                            .then((response) => {
                                this.$emit('login', response.data)
                                this.$router.push('/')
                            })
                    })
                    .catch(() =>
                        this.show = true
                    )
            },
            registerMove() {
                this.$router.push('/register')
            }
        },
    }
</script>
<style>
    #inspire {
        width: 100%;
    }
</style>