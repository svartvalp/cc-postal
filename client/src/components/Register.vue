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

                        <v-card class="elevation-12">
                            <v-toolbar
                                    color="amber lighten-3"
                                    dark
                                    flat
                            >
                                <v-spacer></v-spacer>
                                <v-toolbar-title class="black--text">Регистрация</v-toolbar-title>
                                <v-spacer></v-spacer>
                            </v-toolbar>
                            <v-card-text>
                                <v-form v-model="isValid">
                                    <v-text-field
                                            label="Фамилия"
                                            v-model="user.middleName"
                                            maxlength="50"
                                            required
                                            :rules="middleNameRules"
                                            :counter="50"
                                    ></v-text-field>
                                    <v-text-field
                                            label="Имя"
                                            v-model="user.firstName"
                                            maxlength="50"
                                            required
                                            :rules="firstNameRules"
                                            :counter="50"
                                    ></v-text-field>
                                    <v-text-field
                                            label="Отчество"
                                            v-model="user.lastName"
                                            maxlength="50"
                                            required
                                            :rules="lastNameRules"
                                            :counter="50"
                                    ></v-text-field>
                                    <v-text-field
                                            label="Логин"
                                            v-model="user.login"
                                            maxlength="30"
                                            required
                                            :rules="loginRules"
                                            :counter="30"
                                    ></v-text-field>
                                    <v-text-field
                                            label="Серия и номер паспорта"
                                            v-model="user.passportNumber"
                                            maxlength="10"
                                            v-mask="'##########'"
                                            required
                                            :rules="passportNumberRules"
                                            :counter="10"
                                    ></v-text-field>
                                    <v-text-field
                                            label="Пароль"
                                            v-model="user.password"
                                            type="password"
                                            maxlength="30"
                                            required
                                            :rules="passwordRules"
                                            :counter="30"
                                    ></v-text-field>
                                    <v-card-actions class="justify-center">
                                        <v-btn
                                                @click="submit"
                                                class="mr-4"
                                                color="amber lighten-3"
                                                :disabled="!isValid"
                                        >Зарегистрироваться
                                        </v-btn>
                                        <v-btn @click="clear">Очистить</v-btn>
                                    </v-card-actions>
                                </v-form>
                            </v-card-text>
                        </v-card>
                        <v-alert
                                class="mt-6"
                                icon="mdi-alert-octagram"
                                id="errorAlert"
                                prominent
                                text
                                type="error"
                                v-model="this.alert.show"
                        >
                            {{ this.alert.errorMessage }}
                        </v-alert>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    import {validationMixin} from 'vuelidate'
    import { minLength, required} from 'vuelidate/lib/validators'

    export default {
        mixins: [validationMixin],

        validations: {
            firstName: {required},
            middleName: {required},
            lastName: {required},
            login: {required},
            passportNumber: {required, minLength: minLength(10)},
            password: {required}
        },
        data: () => ({
            user: {
                firstName: null,
                middleName: null,
                lastName: null,
                login: null,
                passportNumber: null,
                password: null
            },
            isValid: true,
            firstNameRules: [
                v => !!v || 'Имя обязательно для заполнения'
            ],
            middleNameRules: [
                v => !!v || 'Фамилия обязательна для заполнения'
            ],
            lastNameRules: [
                v => !!v || 'Отчество обязательно для заполнения'
            ],
            loginRules: [
                v => !!v || 'Логин обязателен для заполнения'
            ],
            passportNumberRules: [
                v => !!v || 'Поле обязательно для заполнения',
                v => (v && v.length === 10) || 'Поле должно содержать 10 цифр'
            ],
            passwordRules: [
                v => !!v || 'Пароль обязателен для заполнения',
                v => /^[a-zA-Z0-9-_!;]+$/.test(v) || 'Некорректный пароль'
            ],
            alert: {
                show: false,
                errorMessage: ''
            }
        }),
        methods: {
            submit() {
                this.$http
                    .post('/register', this.user)
                    .then(() => this.$router.push('/'))
                    .catch((err) => {
                            this.alert.show = true
                            if (err.response.status === 400) {
                                this.alert.errorMessage = 'Логин уже существует'
                            } else if (err.response.status === 500) {
                                this.alert.errorMessage = 'Ошибка на сервере'
                            }
                        }
                    )
            },
            clear() {
                this.$v.$reset()
                this.user.firstName = null
                this.user.middleName = null
                this.user.lastName = null
                this.user.login = null
                this.user.passportNumber = null
                this.user.password = null
            },
            loginKeyPressed(e) {
                if (!/\w/.test(e.key)) {
                    e.preventDefault()
                }
            },
            passwordKeyPressed(e) {
                if (!/[a-zA-Z0-9-_!;]+/.test(e.key)) {
                    e.preventDefault()
                }
            }
        }
    }
</script>

<codepen-resources lang="json">
    {
    "js": [
    "https://cdn.jsdelivr.net/npm/vuelidate/dist/vuelidate.min.js",
    "https://cdn.jsdelivr.net/npm/vuelidate/dist/validators.min.js"
    ]
    }
</codepen-resources>

<codepen-additional>
    const { required, maxLength, email } = validators
    const validationMixin = vuelidate.validationMixin

    Vue.use(vuelidate.default)
</codepen-additional>

<style>
    #inspire {
        width: 100%;
    }
</style>