<template>
  <div class="departure-view-container">
    <div class="departure-view-wrap">
      <div class="departures-title-text">Посылка № {{ this.departure.id }}</div>
      <div class="map-container">


        <div class="map-wrapper">
          <MglMap
              :accessToken="accessToken"
              :center="center"
              :map-style="mapStyle"
              class="map"
              :zoom="zoom"
          >
            <MglMarker color="blue" v-bind:coordinates="fromPoint.center"/>
            <MglMarker :coordinates="toPoint.center" color="blue"/>
            <MglGeojsonLayer :layer="geoJsonLayer" :source="geoJsonSource" layer-id="layer"
                             source-id="route"/>
          </MglMap>
        </div>


        <div class="departure-points-container">
          <v-container>
            <v-row dense>
              <DepartureInfoCard :paramValue="departure.departurePoint.address"
                                 paramName="Адрес отправления"/>
              <DepartureInfoCard :paramValue="departure.arrivingPoint.address"
                                 paramName="Адрес получения"/>
              <DepartureInfoCard :paramValue="departure.departureDate" paramName="Дата отправки"/>
              <DepartureInfoCard v-if="departure.type" :paramValue="departure.type" paramName="Тип посылки"/>
              <DepartureInfoCard v-if="departure.weight" :paramValue="departure.weight" paramName="Вес посылки (в граммах)"/>
              <DepartureInfoCard v-if="departure.description" :paramValue="departure.description" paramName="Описание посылки"/>
              <DepartureInfoCard v-if="departure.arrivingDate" :paramValue="departure.arrivingDate" paramName="Дата прибытия посылки"/>
              <DepartureInfoCard v-if="recipient" :paramValue="recipient" paramName="Получатель"/>
              <DepartureInfoCard v-if="departure.isReceived" :paramValue="sender" paramName="Отправитель"/>
            </v-row>
          </v-container>
        </div>


      </div>
    </div>
  </div>
</template>

<script>
import config from "../../config";
import {MglGeojsonLayer, MglMap, MglMarker} from "vue-mapbox";
import DepartureInfoCard from "@/components/DepartureInfoCard";

export default {
  components: {
    MglMap,
    MglMarker,
    MglGeojsonLayer,
    DepartureInfoCard
  },
  data() {
    return {
      zoom: 9,
      recipient: null,
      sender: null,
      departure: {
        id: null,
        userId: null,
        departurePoint: {},
        arrivingPoint: {},
        type: null,
        arrived: false,
        weight: null,
        description: null,
        departureDate: null,
        arrivingDate: null,
        addressee: {},
      },
      direction: Object,
      activeColor: "#ffe082",
      accessToken: config.api.accessToken,
      mapStyle: config.api.mapStyle,
      center: config.api.init_values.map_center,
      fromPoint: {
        center: config.api.init_values.from_point
      },
      toPoint: {
        center: config.api.init_values.to_point
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
    }
  },
  mounted() {
    this.departure = JSON.parse(localStorage.getItem('currentDeparture'))
    if (this.departure.isReceived) {
      this.$http.get('/user/' + this.departure.userId)
          .then(response => {
            this.sender = response.data.firstName + ' ' + response.data.lastName;
          })
    } else {
      if (this.departure.addressee.id !== null) {
        this.$http.get('/user/' + this.departure.addressee.id)
            .then(response => {
              this.recipient = response.data.firstName + ' ' + response.data.lastName;
            })
      }
    }
    this.fromPoint.center[0] = this.departure.departurePoint.longitude;
    this.fromPoint.center[1] = this.departure.departurePoint.latitude
    this.toPoint.center[0] = this.departure.arrivingPoint.longitude;
    this.toPoint.center[1] = this.departure.arrivingPoint.latitude
    this.loadDirections();
  },
  methods: {
    loadDirections() {
      let fromLongitude = this.fromPoint.center[0]
      let fromLatitude = this.fromPoint.center[1]
      let toLongitude = this.toPoint.center[0]
      let toLatitude = this.toPoint.center[1]
      return this.$http
          .get(`/direction?from_longitude=${fromLongitude}&from_latitude=${fromLatitude}&to_longitude=${toLongitude}&to_latitude=${toLatitude}`)
          .then(response => {
            this.geoJsonSource.data.geometry.coordinates = response.data.map(point => [point.longitude, point.latitude])
          }).catch(() => this.geoJsonSource.data.geometry.coordinates = [])
    },
  }
}
</script>
<style>
.departure-points-container {
  align-self: flex-start;
  display: flex;
  flex-direction: column;
}

.map {
  width: 600px;
  height: 600px;
  border: 1px solid black;
}

.map-wrapper {
  align-self: flex-start;
  padding: 40pt;
  display: flex;
  flex-direction: column;
}

.map-container {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.departure-view-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: fit-content;
  height: 100%;
}

.departure-view-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}
</style>