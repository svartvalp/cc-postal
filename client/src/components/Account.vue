<template>
  <div id="account">
    <v-form :readonly="isReadOnly">
      <p class="text-center text-h4">Профиль</p>
      <v-text-field
          class="centered-input"
          filled
          label="ID"
          readonly
          v-model="userInfo.id"
      ></v-text-field>
      <v-text-field
          filled
          label="Логин"
          readonly
          required
          v-model="userInfo.login"
      ></v-text-field>
      <v-text-field
          :filled="isReadOnly"
          label="Имя"
          required
          v-model="userInfo.firstName"
      ></v-text-field>
      <v-text-field
          :filled="isReadOnly"
          label="Отчество"
          required
          v-model="userInfo.middleName"
      ></v-text-field>
      <v-text-field
          :filled="isReadOnly"
          label="Фамилия"
          required
          v-model="userInfo.lastName"
      ></v-text-field>
      <v-text-field
          :filled="isReadOnly"
          label="Номер паспорта"
          required
          v-model="userInfo.passportNumber"
      ></v-text-field>
      <v-text-field
          label="Адрес"
          required
          readonly
          filled
          v-model="userInfo.address.address"
      ></v-text-field>
      <v-btn @click="isReadOnly=!isReadOnly" style="margin: 10px" v-if="isReadOnly">Изменить информацию</v-btn>
      <div v-else>
        <v-btn @click="isReadOnly=!isReadOnly; userInfo = JSON.parse(JSON.stringify(userInfoOrig))"
               style="margin: 10px">
          Отмена
        </v-btn>
        <v-btn @click="isReadOnly=!isReadOnly; submitUserInfo()">Сохранить</v-btn>
      </div>
    </v-form>
    <v-dialog max-width="600px" persistent v-model="dialog">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on"
            style="margin: 10px"
        >
          Изменить пароль
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Изменение пароля</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-text-field v-model="newPass1" label="Новый пароль" required type="password"></v-text-field>
            <v-text-field v-model="newPass2" label="Повторите" required type="password"></v-text-field>
            <v-alert
                class="mt-6"
                icon="mdi-alert-octagram"
                id="errorAlert"
                prominent
                text
                type="error"
                v-model="this.passwordChangeAlert.show"
            >
              {{ this.passwordChangeAlert.errorMessage }}
            </v-alert>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="dialog = false; newPass1=''; newPass2=''; passwordChangeAlert.show=false" color="blue darken-1"
                 text>Закрыть
          </v-btn>
          <v-btn @click="submitPassword" color="blue darken-1" text>Сохранить</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>

export default {
  name: "Account",
  data() {
    return {
      userInfo: {},
      userInfoOrig: {},
      isReadOnly: true,
      dialog: false,
      newPass1: '',
      newPass2: '',
      passwordChangeAlert: {
        show: false,
        errorMessage: ''
      }
    }
  },
  mounted() {
    this.$http.get('/user')
        .then(response => {
          if (response.data.address == null) {
            response.data.address = {};
          }
          this.userInfo = response.data;
          this.userInfoOrig = JSON.parse(JSON.stringify(response.data));
        });
  },
  methods: {
    submitUserInfo() {
      this.$http
          .put(`/user`, JSON.parse(JSON.stringify(this.userInfo)))
          .then(() => {
            this.userInfoOrig = JSON.parse(JSON.stringify(this.userInfo));
            this.readonly = !this.readonly
          })
    },
    submitPassword() {
      if (this.newPass1 !== this.newPass2) {
        this.passwordChangeAlert.show = true;
        this.passwordChangeAlert.errorMessage = 'Пароли должны совпадать';
        this.newPass2 = '';
        this.newPass1 = '';
        return
      }
      if (this.newPass1 === '') {
        this.passwordChangeAlert.show = true;
        this.passwordChangeAlert.errorMessage = 'Пароль не может быть пустым';
        this.newPass2 = '';
        this.newPass1 = '';
        return
      }
      this.userInfo.password = this.newPass1;
      this.$http
          .put(`/user`, this.userInfo)
      this.dialog = false;
      this.userInfo = this.userInfoOrig;
    }
  }
}
</script>

<style scoped>
#account {
  margin: auto;
  width: 30%;
  padding: 10px;
}
</style>