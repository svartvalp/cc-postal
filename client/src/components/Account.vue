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
          outlined
          readonly
          @click="onAddressFieldClick"
          :background-color="fieldBackgroundColor()"
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
    <v-dialog max-width="600px" persistent v-model="changePasswordDialog">
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

    <v-dialog max-width="800px" persistent v-model="changeAddressDialog">
      <v-card>
        <v-card-title>
          <span class="headline">Изменение адреса</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <div class="map-wrapper">
              <MglMap
                  :accessToken="accessToken"
                  :center="center"
                  :map-style="mapStyle"
                  @click="onClick"
                  class="map"
                  zoom="9"
              >
                <MglMarker color="blue" v-bind:coordinates="point.center"/>
                <MglGeojsonLayer :layer="geoJsonLayer" :source="geoJsonSource" layer-id="layer"
                                 source-id="route"/>
              </MglMap>
            </div>
            <div class="departure-point-text" style="font-weight: bold">Откуда</div>
            <div class="departure-point-text">{{ point.name }}</div>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="changeAddressDialog = false" color="blue darken-1" text>Закрыть</v-btn>
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
      activeColor: "#ffe082",
      accessToken: config.api.accessToken,
      mapStyle: config.api.mapStyle,
      center: config.api.init_values.map_center,
      point: {
        center: config.api.init_values.map_center,
        name: ""
      },
      selected_point: null,
      type: "",
      weight: 0,
      description: '',
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
      selectedAddress: "",

      userInfo: null,
      userInfoOrig: null,
      isReadOnly: true,
      changePasswordDialog: false,
      changeAddressDialog: false,
      newPassword: null,
      newPasswordVerification: null,
      passwordChangeAlert: {
        show: false,
        errorMessage: null
      }
    }
  },
  mounted() {
    this.selected_point = this.point
    this.$http.get(`/geocoding/point?longitude=${this.point.center[0]}&latitude=${this.point.center[1]}`)
        .then(response => {
          this.point.center[0] = response.data.longitude
          this.point.center[1] = response.data.latitude
          this.point.name = response.data.placeName
        });

    this.$http.get('/user')
        .then(response => {
          if (!response.data.address) {
            response.data.address = {};
          }
          this.userInfo = JSON.parse(JSON.stringify(response.data));
          this.userInfoOrig = JSON.parse(JSON.stringify(response.data));
        });
  },
  methods: {
    updateData() {
      return this.loadPoint().then(() => {
        this.loadDirections()
      }).catch(() => this.geoJsonSource.data.geometry.coordinates = [])
    },
    loadPoint() {
      return this.$http.get(`/geocoding/point?longitude=${this.selected_point.center[0]}&latitude=${this.selected_point.center[1]}`)
          .then(response => {
            this.point.center[0] = response.data.longitude
            this.point.center[1] = response.data.latitude
            this.point.name = response.data.placeName
          })
    },
    onClick(e) {
      this.selected_point.center = [e.mapboxEvent.lngLat.lng].concat([e.mapboxEvent.lngLat.lat])
      this.updateData()
    },

    submitUserInfo() {
      this.switchReadOnly();
      this.$http
          .put(`/user`, this.userInfo)
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
      this.changePasswordDialog = false;
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
      this.changePasswordDialog = false;
      this.newPassword = '';
      this.newPasswordVerification = '';
      this.passwordChangeAlert.show = false
    },
    onAddressFieldClick() {
      if (!this.isReadOnly) {
        console.log("inside if")
        this.changeAddressDialog = true
      }
    },
    onSaveAddress() {
      this.userInfo.address = {
        address: this.point.name,
        longitude: this.point.center[0],
        latitude: this.point.center[1]
      };
      this.changeAddressDialog = false;
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
