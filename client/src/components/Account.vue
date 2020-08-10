<template>
  <div id="account">
    <v-form :readonly="isReadOnly">
      <p class="text-center text-h4">Профиль</p>
      <v-text-field
          class="centered-input"
          label="ID"
          readonly
          outlined
          background-color="#FAFAFA"
          v-model="userInfo.id"
      ></v-text-field>
      <v-text-field
          label="Логин"
          readonly
          required
          outlined
          background-color="#FAFAFA"
          v-model="userInfo.login"
      ></v-text-field>
      <v-text-field
          label="Имя"
          required
          outlined
          :background-color="fieldBackgroundColor()"
          v-model="userInfo.firstName"
      ></v-text-field>
      <v-text-field
          label="Отчество"
          required
          outlined
          :background-color="fieldBackgroundColor()"
          v-model="userInfo.middleName"
      ></v-text-field>
      <v-text-field
          label="Фамилия"
          required
          outlined
          :background-color="fieldBackgroundColor()"
          v-model="userInfo.lastName"
      ></v-text-field>
      <v-text-field
          label="Номер паспорта"
          required
          filled
          outlined
          :background-color="fieldBackgroundColor()"
          v-model="userInfo.passportNumber"
      ></v-text-field>
      <v-text-field
          label="Адрес"
          required
          readonly
          outlined
          background-color='#FAFAFA'
          v-model="userInfo.address.address"
      ></v-text-field>
      <v-btn @click="switchReadOnly" style="margin: 10px" v-if="isReadOnly">Изменить информацию</v-btn>
      <div v-else>
        <v-btn @click="onCancelChangingUsersInfoClick"
               style="margin: 10px">
          Отмена
        </v-btn>
        <v-btn @click="submitUserInfo">Сохранить</v-btn>
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
            <v-text-field v-model="newPassword" label="Новый пароль" required type="password"></v-text-field>
            <v-text-field v-model="newPasswordVerification" label="Повторите" required type="password"></v-text-field>
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
          <v-btn @click='onCancelChangingPasswordClick'
                 color="blue darken-1"
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
      userInfo: null,
      userInfoOrig: null,
      isReadOnly: true,
      dialog: false,
      newPassword: null,
      newPasswordVerification: null,
      passwordChangeAlert: {
        show: false,
        errorMessage: null
      }
    }
  },
  mounted() {
    this.$http.get('/user')
        .then(response => {
          if (!response.data.address) {
            response.data.address = {};
          }
          this.userInfo = response.data;
          this.userInfoOrig = JSON.parse(JSON.stringify(response.data));
        });
  },
  methods: {
    submitUserInfo() {
      this.switchReadOnly();
      this.$http
          .put(`/user`, JSON.parse(JSON.stringify(this.userInfo)))
          .then(() => {
            this.userInfoOrig = JSON.parse(JSON.stringify(this.userInfo));
            this.readonly = !this.readonly
          })
    },
    submitPassword() {
      if (this.newPassword !== this.newPasswordVerification) {
        this.passwordChangeAlert.show = true;
        this.passwordChangeAlert.errorMessage = 'Пароли должны совпадать';
        this.newPasswordVerification = null;
        this.newPassword = null;
        return
      }
      if (!this.newPassword) {
        this.passwordChangeAlert.show = true;
        this.passwordChangeAlert.errorMessage = 'Пароль не может быть пустым';
        this.newPasswordVerification = null;
        this.newPassword = null;
        return
      }
      this.userInfo.password = this.newPassword;
      this.$http
          .put(`/user`, this.userInfo)
      this.dialog = false;
      this.userInfo = this.userInfoOrig;
    },
    fieldBackgroundColor() {
      return this.isReadOnly ? "#FAFAFA" : "#FFFFFF"
    },
    switchReadOnly() {
      this.isReadOnly = !this.isReadOnly
    },
    onCancelChangingUsersInfoClick() {
      this.switchReadOnly();
      this.userInfo = JSON.parse(JSON.stringify(this.userInfoOrig))
    },
    onCancelChangingPasswordClick() {
      this.dialog = false;
      this.newPassword = '';
      this.newPasswordVerification = '';
      this.passwordChangeAlert.show = false
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