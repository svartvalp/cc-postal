<template>
  <div class="main-field">
    <div class="departures-title-container">
      <v-switch v-model="departureSwitch" v-on:click="switchDepartureLists"></v-switch>
      <div v-if="departureSwitch" class="departures-title-text">Ваши посылки</div>
      <div v-if="!departureSwitch" class="departures-title-text">Посылки вам</div>
      <v-btn @click="addDeparture" class="mx-2 departures-title-add-departure-button" color="indigo" dark fab
             justify-end>
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
    </div>
    <v-container id="inspire" v-if="departures.length">
      <v-card class="mx-auto">
        <v-list two-line>
          <v-list-item-group>
            <template v-for="(item,index) in departures">
              <v-divider
                  :key="index"
                  v-if="index !== 0"
              ></v-divider>
              <v-list-item :key="item.id" @click="redirectToDeparture(item)">
                <v-list-item-content>
                  <v-list-item-title class="addresses">Адрес доставки: <span>{{ item.arrivingPoint.address }}</span>
                  </v-list-item-title>
                </v-list-item-content>
                <v-list-item-content class="middle-content">
                  <v-list-item-title class="departure-date">Дата отправления: <span>{{ item.departureDate }}</span>
                  </v-list-item-title>
                  <v-list-item-title class="departure-type">Тип посылки: <span>{{ item.type }}</span>
                  </v-list-item-title>
                </v-list-item-content>
                <v-list-item-content class="text-right">
                  <v-list-item-title class="addresses"><span
                      v-if="item.arrived">Доставлено</span><span
                      v-else-if="!item.arrived">В пути</span></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-list-item-group>
        </v-list>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import router from "@/router";

export default {
  data() {
    return {
      departures: [],
      departureSwitch: true
    }
  },
  methods: {
    addDeparture() {
      this.$router.push('/departure/create')
    },
    redirectToDeparture(departure) {
      departure.isReceived = !this.departureSwitch
      localStorage.setItem('currentDeparture', JSON.stringify(departure));
      router.push('/departure');
    },
    getDate(dateFromServer) {
      let date = new Date(dateFromServer)
      let options = {
        year: 'numeric', month: 'numeric', day: 'numeric',
        hour: 'numeric', minute: 'numeric', hour12: false
      };
      return new Intl.DateTimeFormat("ru-RU", options).format(date)
    },
    switchDepartureLists() {
      this.departures = [];
      this.updateList(this.departureSwitch)
    },
    updateList(type) {
      this.$http.get(`/departure/list/${this.user.id}?type=${type}`)
          .then(response => {
            this.departures = response.data
            for (let i = 0; i < this.departures.length; ++i) {
              this.departures[i].departureDate = this.getDate(this.departures[i].departureDate)
              if (this.departures[i].arrivingDate != null) {
                this.departures[i].arrivingDate = this.getDate(this.departures[i].arrivingDate)
              }
            }
          })
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.switchDepartureLists();
  }
}
</script>

<style>
.main-field {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: center;
  width: 100%;
  height: 100%;
}

.departures-title-text {
  font-size: 30pt;
  margin-bottom: 15px;
  font-style: revert;
  text-decoration: underline;
}

.departure-date {
  font-size: 18pt;
}

.departure-type {
  font-size: 18pt;
}

.middle-content {
  position: absolute;
  left: 50%;
}

.addresses {
  font-size: 18pt;
}

#inspire {
  width: 100%;
}

.departures-title-container {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: center;
}
</style>