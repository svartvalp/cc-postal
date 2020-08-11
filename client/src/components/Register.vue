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
                                <form>
                                    <v-text-field
                                            maxlength="50"
                                            :counter="50"
                                            :error-messages="middleNameErrors"
                                            @blur="$v.middleName.$touch()"
                                            @input="$v.middleName.$touch()"
                                            color="black"
                                            label="Фамилия"
                                            required
                                            v-model="middleName"

                                    ></v-text-field>
                                    <v-text-field
                                            maxlength="50"
                                            :counter="50"
                                            :error-messages="nameErrors"
                                            @blur="$v.firstName.$touch()"
                                            @input="$v.firstName.$touch()"
                                            color="black"
                                            label="Имя"
                                            required
                                            v-model="firstName"
                                    ></v-text-field>
                                    <v-text-field
                                            maxlength="50"
                                            :counter="50"
                                            :error-messages="lastNameErrors"
                                            @blur="$v.lastName.$touch()"
                                            @input="$v.lastName.$touch()"
                                            color="black"
                                            label="Отчество"
                                            required
                                            v-model="lastName"
                                    ></v-text-field>
                                    <v-text-field
                                            maxlength="10"
                                            :counter="10"
                                            :error-messages="loginErrors"
                                            @blur="$v.login.$touch()"
                                            @input="$v.login.$touch()"
                                            color="black"
                                            label="Логин"
                                            required
                                            v-model.trim="login"
                                            @keypress="loginKeyPressed"
                                    ></v-text-field>
                                    <v-text-field
                                            maxlength="10"
                                            :counter="10"
                                            :error-messages="passportNumberErrors"
                                            @blur="$v.passportNumber.$touch()"
                                            @input="$v.passportNumber.$touch()"
                                            color="black"
                                            label="Серия и номер паспорта"
                                            required
                                            v-model="passportNumber"
                                            v-mask="'##########'"
                                    ></v-text-field>
                                    <v-text-field
                                            maxlength="30"
                                            :counter="30"
                                            :error-messages="passwordErrors"
                                            @blur="$v.password.$touch()"
                                            @input="$v.password.$touch()"
                                            color="black"
                                            label="Пароль"
                                            required
                                            type="password"
                                            v-model="password"
                                    ></v-text-field>
                                    <v-card-actions class="justify-center">
                                        <v-btn @click="submit" class="mr-4" color="amber lighten-3">Зарегистрироваться
                                        </v-btn>
                                        <v-btn @click="clear">Очистить</v-btn>
                                    </v-card-actions>
                                </form>
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
    import {minLength, maxLength, required} from 'vuelidate/lib/validators'

    export default {
        mixins: [validationMixin],

        validations: {
            firstName: {required, maxLength: maxLength(50)},
            middleName: {required, maxLength: maxLength(50)},
            lastName: {required, maxLength: maxLength(50)},
            login: {required, maxLength: maxLength(10)},
            passportNumber: {required, minLength: minLength(10)},
            password: {required, maxLength: maxLength(30)}
        },
        data: () => ({
            firstName: '',
            middleName: '',
            lastName: '',
            login: '',
            passportNumber: '',
            password: '',
            user: {},
            alert: {
                show: false,
                errorMessage: ''
            }
        }),
        methods: {
            submit() {
                if (this.firstName !== '') {
                    this.user.firstName = this.firstName;
                } else {
                    this.alert.show = true
                    this.alert.errorMessage = 'Укажите имя'
                    return
                }
                if (this.middleName !== '') {
                    this.user.middleName = this.middleName;
                } else {
                    this.alert.show = true
                    this.alert.errorMessage = 'Укажите фамилию'
                    return
                }
                if (this.lastName !== '') {
                    this.user.lastName = this.lastName;
                } else {
                    this.alert.show = true
                    this.alert.errorMessage = 'Укажите отчество'
                    return
                }
                if (this.login !== '') {
                    this.user.login = this.login;
                } else {
                    this.alert.show = true
                    this.alert.errorMessage = 'Укажите логин'
                    return
                }
                if (this.passportNumber !== '' && this.passportNumber.length === 10) {
                    this.user.passportNumber = this.passportNumber;
                } else {
                    this.alert.show = true
                    this.alert.errorMessage = 'Паспортные данные некорректны'
                    return
                }
                if (this.password !== '') {
                    this.user.password = this.password;
                } else {
                    this.alert.show = true
                    this.alert.errorMessage = 'Укажите пароль'
                    return
                }


                this.$http
                    .post('/register', this.user)
                    .then(() => this.$router.push('/'))
                    .catch((err) => {
                            console.log(err)
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
                this.firstName = ''
                this.middleName = ''
                this.lastName = ''
                this.login = ''
                this.passportNumber = ''
                this.password = ''
            },
            loginKeyPressed(e) {
                if(!/\w/.test(e.key)){
                    e.preventDefault()
                }
            }

        },
        computed: {
            nameErrors() {
                const error = []
                if (!this.$v.firstName.$dirty) return error
                !this.$v.firstName.required && error.push('Это поле обязательно для заполнения.')
                return error
            },
            middleNameErrors() {
                const error = []
                if (!this.$v.middleName.$dirty) return error
                !this.$v.middleName.required && error.push('Это поле обязательно для заполнения.')
                return error
            },
            lastNameErrors() {
                const error = []
                if (!this.$v.lastName.$dirty) return error
                !this.$v.lastName.required && error.push('Это поле обязательно для заполнения.')
                return error
            },
            loginErrors() {
                const error = []
                if (!this.$v.login.$dirty) return error
                !this.$v.login.required && error.push('Это поле обязательно для заполнения.')
                return error
            },
            passportNumberErrors() {
                const error = []
                if (!this.$v.passportNumber.$dirty) return error
                !this.$v.passportNumber.minLength && error.push('Серия и номер паспорта должны состоять из 10 символов')
                !this.$v.passportNumber.required && error.push('Это поле обязательно для заполнения.')
                return error
            },
            passwordErrors() {
                const error = []
                if (!this.$v.password.$dirty) return error
                !this.$v.password.required && error.push('Это поле обязательно для заполнения.')
                return error
            },
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