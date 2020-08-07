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
                                            v-model="firstName"
                                            :error-messages="nameErrors"
                                            label="Имя"
                                            required
                                            @input="$v.firstName.$touch()"
                                            @blur="$v.firstName.$touch()"
                                            color="black"
                                    ></v-text-field>
                                    <v-text-field
                                            v-model="middleName"
                                            :error-messages="middleNameErrors"
                                            label="Фамилия"
                                            required
                                            @input="$v.middleName.$touch()"
                                            @blur="$v.middleName.$touch()"
                                            color="black"
                                    ></v-text-field>
                                    <v-text-field
                                            v-model="lastName"
                                            :error-messages="lastNameErrors"
                                            label="Отчество"
                                            required
                                            @input="$v.lastName.$touch()"
                                            @blur="$v.lastName.$touch()"
                                            color="black"
                                    ></v-text-field>
                                    <v-text-field
                                            v-model="login"
                                            :error-messages="loginErrors"
                                            label="Логин"
                                            required
                                            @input="$v.login.$touch()"
                                            @blur="$v.login.$touch()"
                                            color="black"
                                    ></v-text-field>
                                    <v-text-field
                                            v-model="passportNumber"
                                            :error-messages="passportNumberErrors"
                                            :counter="10"
                                            label="Серия и номер паспорта"
                                            required
                                            @input="$v.passportNumber.$touch()"
                                            @blur="$v.passportNumber.$touch()"
                                            color="black"
                                    ></v-text-field>
                                    <v-text-field
                                            v-model="password"
                                            :error-messages="passwordErrors"
                                            label="Пароль"
                                            type="password"
                                            required
                                            @input="$v.password.$touch()"
                                            @blur="$v.password.$touch()"
                                            color="black"
                                    ></v-text-field>
                                    <v-card-actions class="justify-center">
                                        <v-btn class="mr-4" @click="submit" color="amber lighten-3">Зарегистрироваться
                                        </v-btn>
                                        <v-btn @click="clear">Очистить</v-btn>
                                    </v-card-actions>
                                </form>
                            </v-card-text>
                        </v-card>
                        <v-alert
                                class="mt-6"
                                id="errorAlert"
                                v-model="this.alert.show"
                                text
                                prominent
                                type="error"
                                icon="mdi-alert-octagram"
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
    import {maxLength, minLength, required} from 'vuelidate/lib/validators'

    export default {
        mixins: [validationMixin],

        validations: {
            firstName: {required},
            middleName: {required},
            lastName: {required},
            login: {required},
            passportNumber: {required, minLength: minLength(10), maxLength: maxLength(10)},
            password: {required},
            checkbox: {
                checked(val) {
                    return val
                },
            },
        },
        data: () => ({
            firstName: '',
            middleName: '',
            lastName: '',
            login: '',
            passportNumber: '',
            password: '',
            checkbox: false,
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
                this.checkbox = false
            },
        },
        computed: {
            checkboxErrors() {
                const error = []
                if (!this.$v.checkbox.$dirty) return error
                !this.$v.checkbox.checked && error.push('Вы должны быть согласны, чтобы продолжить!')
                return error
            },
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
                !this.$v.passportNumber.maxLength && error.push('Серия и номер паспорта должны состоять из 10 символов')
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