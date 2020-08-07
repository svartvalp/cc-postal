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
                            sm="8"
                            md="4"
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
                                            label="Логин"
                                            name="login"
                                            v-model="userLoginData.login"
                                            prepend-icon="mdi-account"
                                            type="text"
                                            color="black"
                                    ></v-text-field>

                                    <v-text-field
                                            id="password"
                                            label="Пароль"
                                            name="password"
                                            v-model="userLoginData.password"
                                            prepend-icon="mdi-lock"
                                            type="password"
                                            color="black"
                                    ></v-text-field>
                                </v-form>
                            </v-card-text>
                            <v-card-actions class="justify-center">
                                <v-btn color="amber lighten-3" id="register" @click="registerMove">Зарегистрироваться
                                </v-btn>
                                <v-btn color="amber lighten-3" id="login" @click="login">Войти</v-btn>
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