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
                                    <v-checkbox
                                            v-model="checkbox"
                                            :error-messages="checkboxErrors"
                                            label="Вы согласны с условиями использования?"
                                            required
                                            @change="$v.checkbox.$touch()"
                                            @blur="$v.checkbox.$touch()"
                                            color="green"
                                    ></v-checkbox>
                                    <v-card-actions class="justify-center">
                                        <v-btn class="mr-4" @click="submit" color="amber lighten-3">Зарегистрироваться</v-btn>
                                        <v-btn @click="clear">Очистить</v-btn>
                                    </v-card-actions>
                                </form>
                            </v-card-text>
                        </v-card>
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
            user: {}
        }),
        methods: {
            submit() {
                this.user.id = this.id;
                this.user.firstName = this.firstName;
                this.user.middleName = this.middleName;
                this.user.lastName = this.lastName;
                this.user.login = this.login;
                this.user.passportNumber = this.passportNumber;
                this.user.password = this.password;

                this.$http
                    .post('/register', this.user)
                    .then(() => this.$router.push('/'))
                    .catch(err => console.log(err))
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