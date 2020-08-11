<template>
  <div class="departure-creation-container">
    <div class="departure-creation-wrap">
      <div class="departures-title">Отправка посылки</div>
      <div v-if="this.hasRoutes" class="warning-message">Невозможно доставить посылку для данных точек</div>
      <div class="map-container">
        <div class="map-wrapper">
          <MglMap
              :accessToken="accessToken"
              :map-style="mapStyle"
              :center="center"
              :zoom="zoom"
              @click="onClick"
              class="map"
          >
            <MglMarker v-bind:coordinates="fromPoint.center" color="blue"/>
            <MglMarker :coordinates="toPoint.center" color="blue"/>
            <MglGeojsonLayer :source="geoJsonSource" source-id="route" :layer="geoJsonLayer" layer-id="layer"/>
          </MglMap>
          <div class="select-address-container">
            <button class="select-address-button" @click.prevent="setAddress">Выбрать домашний адрес</button>
          </div>
        </div>
        <div class="departure-points-container">
          <button class="departure-point" v-bind:class="{selected : selectedPoint === fromPoint}"
                  @click.prevent="selectedPoint = fromPoint">
            <div class="departure-point-text" style="font-weight: bold">Откуда</div>
            <div class="departure-point-text">{{ fromPoint.name }}</div>
          </button>
          <button class="departure-point" v-bind:class="{selected : selectedPoint === toPoint}"
                  @click.prevent="selectedPoint = toPoint">
            <div class="departure-point-text" style="font-weight: bold">Куда</div>
            <div class="departure-point-text">{{ toPoint.name }}</div>
          </button>
          <div class="type-input-container">
            <div class="validation-errors" v-if="validationErrors.length">
              <div v-for="error of this.validationErrors" :key="error" class="validation-error">{{ error }}</div>
            </div>
            <label for="type">Тип посылки</label>
            <input id="type" name="type" type="text" v-model="type" class="type-input-text">
            <label for="weight">Вес посылки в граммах</label>
            <input id="weight" name="weight" :value="weight"
                   type="text"
                   v-on:input="changeWeight"
                   class="type-input-text">
            <label for="description">Описание посылки</label>
            <textarea id="description" name="description" rows="4" class="type-input-text"
                      v-model="description"></textarea>
          </div>
        </div>
      </div>
      <div class="bottom-buttons-container">
        <button class="bottom-button" @click="onCreateDeparture">Отправить</button>
        <button class="bottom-button" @click.prevent="resetValues">Сбросить</button>
      </div>
    </div>
  </div>
</template>
<script>
import {MglGeojsonLayer, MglMap, MglMarker} from "vue-mapbox";
import Mapbox from "mapbox-gl";
import config from '../../config.js';

export default {
  data() {
    return {
      activeColor: "#ffe082",
      accessToken: config.api.accessToken,
      mapStyle: config.api.mapStyle,
      center: config.api.init_values.map_center,
      fromPoint: {
        center: [].concat(config.api.init_values.from_point),
        name: ""
      },
      toPoint: {
        center: [].concat(config.api.init_values.to_point),
        name: ""
      },
      selectedPoint: null,
      type: "",
      weight: 0,
      zoom:9,
      validationErrors: [],
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
      }
    }
  },
  props: {
    user: Object
  },
  components: {
    MglMap,
    MglMarker,
    MglGeojsonLayer
  },
  mounted() {
    this.selectedPoint = this.fromPoint
    this.$http.get(`/geocoding/point?longitude=${this.fromPoint.center[0]}&latitude=${this.fromPoint.center[1]}`)
        .then(response => {
          this.fromPoint.center[0] = response.data.longitude
          this.fromPoint.center[1] = response.data.latitude
          this.fromPoint.name = response.data.placeName
        }).then(() => {
      this.$http.get(`/geocoding/point?longitude=${this.toPoint.center[0]}&latitude=${this.toPoint.center[1]}`)
          .then(response => {
            this.toPoint.center[0] = response.data.longitude
            this.toPoint.center[1] = response.data.latitude
            this.toPoint.name = response.data.placeName
          }).then(() => this.loadDirections().then(() => {
        localStorage.setItem("initial_from_point", JSON.stringify(this.fromPoint))
        localStorage.setItem("initial_to_point", JSON.stringify(this.toPoint))
        localStorage.setItem("initial_directions", JSON.stringify(this.geoJsonSource.data.geometry.coordinates))
      }))
    })
  },
  computed: {
    hasRoutes() {
      return !this.geoJsonSource.data.geometry.coordinates.length
    },
  },
  methods: {
    changeWeight(e) {
      this.weight = e.target.value.replace(/\D/g, '').slice(0,5)
      e.target.value = this.weight
    },
    setAddress() {
      this.selectedPoint.center = [this.user.address.longitude, this.user.address.latitude]
      this.updateData()
    },
    updateData() {
      return this.loadPoint().then(() => {
        this.loadDirections()
      }).catch(() => this.geoJsonSource.data.geometry.coordinates = [])
    },
    loadPoint() {
      return this.$http.get(`/geocoding/point?longitude=${this.selectedPoint.center[0]}&latitude=${this.selectedPoint.center[1]}`)
          .then(response => {
            this.selectedPoint.center[0] = response.data.longitude
            this.selectedPoint.center[1] = response.data.latitude
            this.selectedPoint.name = response.data.placeName
          }).catch(error => {
            if (error.response.status === 401) {
              this.$emit('logout')
            }
          })
    },
    onClick(e) {
      this.selectedPoint.center = [e.mapboxEvent.lngLat.lng].concat([e.mapboxEvent.lngLat.lat])
      this.updateData()
    },
    loadDirections() {
      let fromLongitude = this.fromPoint.center[0]
      let fromLatitude = this.fromPoint.center[1]
      let toLongitude = this.toPoint.center[0]
      let toLatitude = this.toPoint.center[1]
      return this.$http
          .get(`/direction?from_longitude=${fromLongitude}&from_latitude=${fromLatitude}&to_longitude=${toLongitude}&to_latitude=${toLatitude}`)
          .then(response => {
            this.geoJsonSource.data.geometry.coordinates = response.data.map(point => [point.longitude, point.latitude])
          }).catch((error) => {
                if (error.response.status === 401) {
                  console.log('logout')
                  this.$emit('logout')
                }
                this.geoJsonSource.data.geometry.coordinates = []
              }
          )
    },
    checkValidation() {
      this.validationErrors = []
      if (!this.type.trim().length) {
        this.validationErrors.push('Тип посылки не указан')
      }
      if (this.weight <= 0 || this.weight.trim() === "") {
        this.validationErrors.push('Вес посылки не может быть меньше 0')
      }
      if (this.description.length > 255) {
        this.validationErrors.push('Описание не может быть длиннее 255 символов')
      }
      if (this.type.length > 20) {
        this.validationErrors.push('Название типа не может быть больше 20 символов')
      }
      if (this.weight > 10000) {
        this.validationErrors.push('Вес не может быть больше 10 кг')
      }

    },
    onCreateDeparture() {
      console.log(this.weight)
      this.checkValidation()
      if (this.validationErrors.length)
        return
      let body = {
        userId: this.user.id,
        departurePoint: {
          longitude: this.fromPoint.center[0],
          latitude: this.fromPoint.center[1],
          address: this.fromPoint.name
        },
        arrivingPoint: {
          longitude: this.toPoint.center[0],
          latitude: this.toPoint.center[1],
          address: this.toPoint.name
        },
        type: this.type,
        departureDate: new Date().toISOString(),
        arrived: false,
        weight: this.weight,
        description: this.description
      }
      console.log(body)
      this.$http.post('/departure', body).then(() => {
        this.$router.push('/')
      })
    },
    async resetValues() {
      this.fromPoint = JSON.parse(localStorage.getItem("initial_from_point"))
      this.toPoint = JSON.parse(localStorage.getItem("initial_to_point"))
      this.geoJsonSource.data.geometry.coordinates = JSON.parse(localStorage.getItem("initial_directions"))
      this.weight = 0
      this.description = ""
      this.type = ""
      this.validationErrors = []
    }
  },
  created() {
    this.mapbox = Mapbox
  }
}
</script>
<style>

.selected {
  display: flex;
  flex-direction: column;
  border-radius: 15px;
  border: 1.5px solid black;
  padding: 10px;
}

.departure-creation-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}

.type-input-container {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  font-size: 15pt;
}

.warning-message {
  font-size: 15pt;
  color: red;
}

.departure-points-container {
  padding: 30px;
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 460px;
  height: fit-content;
}

.type-input-text {
  border: 3px solid black;
  border-radius: 5px;
  margin: 10px;
}

.departure-point {
  display: flex;
  flex-direction: row;
  margin-bottom: 15px;
  padding: 10px;
}

.departure-point:focus {
  outline: none;
}

.departures-title {
  font-size: 20pt;
  margin-bottom: 15px;
  font-style: revert;
  text-decoration: underline;
}

.departure-point-text {
  margin-right: 10px;
  font-size: 13pt;
}

.select-address-container {
  margin: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.select-address-button {
  font-size: 15pt;
  margin-bottom: 10px;
}

.departure-creation-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: fit-content;
  height: 100%;
}

.bottom-buttons-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.bottom-button {
  border-radius: 5px;
  border: 2px solid black;
  padding: 5px;
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 10px;
}

.bottom-button:focus {
  outline: none;
}

.validation-errors {
  align-self: center;
  margin-bottom: 10px;
}

.validation-error {
  font-size: 12pt;
  color: red;
}
</style>
<style scoped>
.map {
  width: 600px;
  height: 600px;
  border: 1px solid black;
}

.map-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.map-wrapper {
  display: flex;
  flex-direction: column;
  padding: 0;
}
</style>