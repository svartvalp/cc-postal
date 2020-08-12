<template>
  <div id="account">
    <v-form :readonly="isReadOnly">
      <p class="text-center text-h4">Профиль</p>
      <v-text-field
          background-color="#FAFAFA"
          class="centered-input"
          label="ID"
          outlined
          readonly
          v-model="userInfo.id"
      ></v-text-field>
      <v-text-field
          background-color="#FAFAFA"
          label="Логин"
          outlined
          readonly
          required
          v-model="userInfo.login"
      ></v-text-field>

      <v-text-field
          :background-color="fieldBackgroundColor()"
          :rules="[requiredFieldRule, less50SymbolsRule]"
          label="Имя"
          :counter="50"
          outlined
          required
          v-model="userInfo.firstName"
          ref="firstName"
      ></v-text-field>

      <v-text-field
          :background-color="fieldBackgroundColor()"
          :rules="[requiredFieldRule, less50SymbolsRule]"
          label="Отчество"
          :counter="50"
          outlined
          required
          v-model="userInfo.middleName"
          ref="middleName"
      ></v-text-field>
      <v-text-field
          :background-color="fieldBackgroundColor()"
          :rules="[requiredFieldRule, less50SymbolsRule]"
          label="Фамилия"
          :counter="50"
          outlined
          required
          ref="lastName"
          v-model="userInfo.lastName"
      ></v-text-field>
      <v-text-field
          :background-color="fieldBackgroundColor()"
          :rules="[requiredFieldRule, passportNumberRule]"
          :counter="10"
          filled
          label="Серия и номер паспорта"
          outlined
          required
          ref="passportNumber"
          v-mask="'##########'"
          v-model="userInfo.passportNumber"
      ></v-text-field>
      <v-text-field
          :background-color="fieldBackgroundColor()"
          @click="onAddressFieldClick"
          label="Адрес"
          outlined
          readonly
          required
          v-model="userInfo.address.address"
      ></v-text-field>
      <v-alert
          class="mt-6"
          icon="mdi-alert-octagram"
          id="userInfoChangeAlert"
          prominent
          text
          type="error"
          v-model="this.userInfoChangeAlert.show"
      >
        {{ this.userInfoChangeAlert.errorMessage }}
      </v-alert>
      <v-btn @click="switchReadOnly" style="margin: 10px" v-if="isReadOnly">Изменить информацию</v-btn>
      <div v-else>
        <v-btn @click="onCancelChangingUsersInfoClick"
               style="margin: 10px">
          Отмена
        </v-btn>
        <v-btn @click="submitUserInfo">Сохранить</v-btn>
      </div>
    </v-form>
    <v-dialog max-width="600px" persistent v-model="showPasswordDialog">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="primary"
            dark
            style="margin: 10px"
            v-bind="attrs"
            v-on="on"
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
            <v-text-field ref="newPassword" counter="30" :rules="[requiredFieldRule,passwordRule, less30SymbolsRule]"
                          label="Новый пароль" required :type="showPassword ? 'text' : 'password'"
                          v-model="newPassword" :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                          @click:append="showPassword = !showPassword"
            ></v-text-field>
            <v-text-field ref="newPasswordValidation" counter="30"
                          :rules="[requiredFieldRule,passwordRule, less30SymbolsRule]"
                          label="Повторите" required :type="showPasswordVerification ? 'text' : 'password'"
                          v-model="newPasswordVerification"
                          :append-icon="showPasswordVerification ? 'mdi-eye' : 'mdi-eye-off'"
                          @click:append="showPasswordVerification = !showPasswordVerification"></v-text-field>
            <p class="text-center">Пароль может содержать только латинские буквы, цифры, '-', '_'.</p>
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

    <v-dialog max-width="800px" persistent v-model="showAddressDialog">
      <v-card>
        <v-card-title>
          <span class="headline">Изменение адреса</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <div class="map-wrapper">
              <MglMap
                  :accessToken="accessToken"
                  :center="currentAddress.point"
                  :map-style="mapStyle"
                  @click="onClick"
                  class="map"
                  zoom="9"
              >
                <MglMarker color="blue" v-bind:coordinates="selectedAddress.point"/>
                <MglGeojsonLayer :layer="geoJsonLayer" :source="geoJsonSource" layer-id="layer"
                                 source-id="route"/>
              </MglMap>
            </div>
            <div class="departure-point-text text-center">{{ selectedAddress.name }}</div>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="showAddressDialog = false" color="blue darken-1" text>Закрыть</v-btn>
          <v-btn @click="onSaveAddress" color="blue darken-1" text>Сохранить</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import {MglGeojsonLayer, MglMap, MglMarker} from "vue-mapbox";
import config from "../../config";

export default {
  name: "Account",
  components: {
    MglMap,
    MglMarker,
    MglGeojsonLayer
  },
  data() {
    return {
      accessToken: config.api.accessToken,
      mapStyle: config.api.mapStyle,
      center: config.api.init_values.map_center,
      selectedAddress: {
        point: [],
        name: null
      },

      geoJsonSource: {
        'type': 'geojson',
        'data': {
          'type': 'Feature',
          'properties': {},
          'geometry': {
            'type': 'LineString',
            'coordinates': []
          }
        }
      },
      geoJsonLayer: {
        'type': 'line',
        'layout': {
          'line-join': 'round',
          'line-cap': 'round'
        },
        'paint': {
          'line-color': '#363636',
          'line-width': 10
        }
      },
      userInfo: {firstName: null, middleName: null, lastName: null, passportNumber: null, address: {}},
      userInfoOrig: null,
      isReadOnly: true,
      showPasswordDialog: false,
      showAddressDialog: false,
      userInfoChangeAlert: {
        show: false,
        errorMessage: null
      },
      currentAddress: {
        point: [],
        name: null
      },
      newPassword: null,
      newPasswordVerification: null,
      passwordChangeAlert: {
        show: false,
        errorMessage: null
      },
      showPassword: false,
      showPasswordVerification: false,

      requiredFieldRule: v => (v !== null && !!v.trim()) || 'Необходимо заполнить поле',
      less50SymbolsRule: v => (v !== null && v.length <= 50) || 'Поле может содержать максимум 50 символов',
      passportNumberRule: v => ((v !== null && v.length === 10 && (v.match('^[0-9]+$') != null)) || 'Номер паспорта указан некорректно'),
      passwordRule: v => ((v !== null && (v.match('^[a-zA-Z0-9-_!;]+$') != null)) || 'Пароль содержит недопустимые символы'),
      less30SymbolsRule: v => (v !== null && v.length <= 30) || 'Поле может содержать максимум 30 символов',
    }
  },
  mounted() {
    this.userInfo = JSON.parse(localStorage.getItem('user'))
    if (!this.userInfo.address) {
      this.userInfo.address = {};
      this.currentAddress.point = this.center;
    } else {
      this.currentAddress.point = [this.userInfo.address.longitude, this.userInfo.address.latitude];
      this.currentAddress.name = this.userInfo.address.address;
    }
    this.selectedAddress.point = [this.currentAddress.point[0], this.currentAddress.point[1]];
    this.userInfoOrig = JSON.parse(JSON.stringify(this.userInfo));
    this.$http.get(`/geocoding/point?longitude=${this.selectedAddress.point[0]}&latitude=${this.selectedAddress.point[1]}`)
        .then(response => {
          this.selectedAddress.name = response.data.placeName;
        })
  },
  methods: {
    checkPasswordField() {
      this.newPassword = this.newPassword.match("^[a-zA-Z0-9-_!;]+$")[0]
    },
    updateData() {
      return this.loadPoint()
    },

    loadPoint() {
      return this.$http.get(`/geocoding/point?longitude=${this.selectedAddress.point[0]}&latitude=${this.selectedAddress.point[1]}`)
          .then(response => {
            this.selectedAddress.name = response.data.placeName;
          })
    },

    onClick(e) {
      this.selectedAddress.point = [e.mapboxEvent.lngLat.lng].concat([e.mapboxEvent.lngLat.lat])
      this.updateData()
    },

    submitUserInfo() {
      if (!this.$refs.firstName.validate() || !this.$refs.middleName.validate() || !this.$refs.lastName.validate() ||
          !this.$refs.passportNumber.validate()) {
        return
      }
      this.switchReadOnly();
      this.$http
          .put(`/user`, this.userInfo)
          .then((response) => {
            this.$emit('update-user', response.data);
            if (!response.data.address) {
              response.data.address = {}
            }
            this.userInfo = JSON.parse(JSON.stringify(response.data));
            this.userInfoOrig = JSON.parse(JSON.stringify(response.data));
            this.readonly = !this.readonly;
            this.userInfoChangeAlert.show = false;
          })
          .catch(() => {
                this.userInfoChangeAlert.show = true;
                this.userInfoChangeAlert.errorMessage = "Произошла ошибка при сохранение изменений.";
              }
          )
    },
    submitPassword() {
      if (!this.$refs.newPassword.validate() || !this.$refs.newPasswordValidation.validate()) {
        return
      }
      if (this.newPassword !== this.newPasswordVerification) {
        this.passwordChangeAlert.show = true;
        this.passwordChangeAlert.errorMessage = 'Пароли должны совпадать';
        return
      }
      if (!this.newPassword || !this.newPassword.trim()) {
        this.passwordChangeAlert.show = true;
        this.passwordChangeAlert.errorMessage = 'Пароль не может быть пустым';
        return
      }
      this.passwordChangeAlert.show = false;
      this.userInfo.password = this.newPassword;
      this.$http
          .put(`/user`, this.userInfo)
      this.newPassword = '';
      this.newPasswordVerification = '';
      this.showPasswordDialog = false;
      this.userInfo = this.userInfoOrig;
    },
    fieldBackgroundColor() {
      return this.isReadOnly ? "#FAFAFA" : "#FFFFFF"
    },
    switchReadOnly() {
      this.userInfoChangeAlert.show = false;
      this.isReadOnly = !this.isReadOnly
    },
    onCancelChangingUsersInfoClick() {
      this.switchReadOnly();
      this.userInfo = JSON.parse(JSON.stringify(this.userInfoOrig));
      this.userInfoChangeAlert.show = false;
    },
    onCancelChangingPasswordClick() {
      this.showPasswordDialog = false;
      this.newPassword = '';
      this.newPasswordVerification = '';
      this.passwordChangeAlert.show = false
    },
    onAddressFieldClick() {
      if (!this.isReadOnly) {
        this.showAddressDialog = true
      }
    },
    onSaveAddress() {
      this.userInfo.address = {
        address: this.selectedAddress.name,
        longitude: this.selectedAddress.point[0],
        latitude: this.selectedAddress.point[1]
      };
      this.showAddressDialog = false;
      this.currentAddress = {
        name: this.selectedAddress.name,
        point: [this.selectedAddress.point[0], this.selectedAddress.point[1]]
      };
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
