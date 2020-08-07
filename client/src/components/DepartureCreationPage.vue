<template>
  <div class="departure-creation-container">
    <div class="departure-creation-wrap">
      <div class="departures-title-text">Отправка посылки</div>
      <div v-if="this.hasRoutes" class="warning-message">There is no route for it</div>
      <div class="map-container">
        <div class="map-wrapper">
          <MglMap
              :accessToken="accessToken"
              :map-style="mapStyle"
              :center="center"
              zoom="9"
              @click="onClick"
              class="map"
          >
            <MglMarker v-bind:coordinates="from_point.center" color="blue"/>
            <MglMarker :coordinates="to_point.center" color="blue"/>
            <MglGeojsonLayer :source="geoJsonSource" source-id="route" :layer="geoJsonLayer" layer-id="layer"/>
          </MglMap>
          <div class="select-address-container">
            <div class="select-address-container-text">Выбрать из сохраненных адресов</div>
            <select @change="setAddress" v-model="selectedAddress" class="user-address-select">
              <option v-for="address of user.addresses" v-bind:value="address.id" v-bind:key="address.id">
                {{ address.name }}
              </option>
            </select>
          </div>
        </div>
        <div class="departure-points-container">
          <button class="departure-point" v-bind:class="{selected : selected_point == from_point}"
                  @click.prevent="selected_point = from_point">
            <div class="departure-point-text" style="font-weight: bold">Откуда</div>
            <div class="departure-point-text">{{ from_point.name }}</div>
          </button>
          <button class="departure-point" v-bind:class="{selected : selected_point == to_point}"
                  @click.prevent="selected_point = to_point">
            <div class="departure-point-text" style="font-weight: bold">Куда</div>
            <div class="departure-point-text">{{ to_point.name }}</div>
          </button>
          <div class="type-input-container">
            <label for="type">Тип посылки</label>
            <input id="type" name="type" type="text" v-model="type" class="type-input-text">
            <label for="weight">Вес посылки в граммах</label>
            <input id="weight" name="weight" type="number" v-model="weight" class="type-input-text">
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
  props: {
    user: Object
  },
  components: {
    MglMap,
    MglMarker,
    MglGeojsonLayer
  },
  mounted() {
    this.selected_point = this.from_point
    this.$http.get(`/geocoding/point?longitude=${this.from_point.center[0]}&latitude=${this.from_point.center[1]}`)
        .then(response => {
          this.from_point.center[0] = response.data.longitude
          this.from_point.center[1] = response.data.latitude
          this.from_point.name = response.data.placeName
        }).then(() => {
      this.$http.get(`/geocoding/point?longitude=${this.to_point.center[0]}&latitude=${this.to_point.center[1]}`)
          .then(response => {
            this.to_point.center[0] = response.data.longitude
            this.to_point.center[1] = response.data.latitude
            this.to_point.name = response.data.placeName
          }).then(() => this.loadDirections().then(() => {
        localStorage.setItem("initial_from_point", JSON.stringify(this.from_point))
        localStorage.setItem("initial_to_point", JSON.stringify(this.to_point))
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
    setAddress(e) {
      if (this.selectedAddress !== '') {
        let id = e.target.value
        let address = this.user.addresses.filter(addr => addr.id == id)[0]
        this.selected_point.center = [address.longitude, address.latitude]
        this.updateData()
        this.selectedAddress = ''
      }
    },
    updateData() {
      return this.loadPoint().then(() => {
        this.loadDirections()
      }).catch(() => this.geoJsonSource.data.geometry.coordinates = [])
    },
    loadPoint() {
      return this.$http.get(`/geocoding/point?longitude=${this.selected_point.center[0]}&latitude=${this.selected_point.center[1]}`)
          .then(response => {
            this.selected_point.center[0] = response.data.longitude
            this.selected_point.center[1] = response.data.latitude
            this.selected_point.name = response.data.placeName
          })
    },
    onClick(e) {
      this.selected_point.center = [e.mapboxEvent.lngLat.lng].concat([e.mapboxEvent.lngLat.lat])
      this.updateData()
    },
    loadDirections() {
      let from_longitude = this.from_point.center[0]
      let from_latitude = this.from_point.center[1]
      let to_longitude = this.to_point.center[0]
      let to_latitude = this.to_point.center[1]
      return this.$http
          .get(`/direction?from_longitude=${from_longitude}&from_latitude=${from_latitude}&to_longitude=${to_longitude}&to_latitude=${to_latitude}`)
          .then(response => {
            this.geoJsonSource.data.geometry.coordinates = response.data.map(point => [point.longitude, point.latitude])
          }).catch(() => this.geoJsonSource.data.geometry.coordinates = [])
    },
    onCreateDeparture() {
      let body = {
        userId: this.user.id,
        departurePoint: {
          longitude: this.from_point.center[0],
          latitude: this.from_point.center[1],
          address: this.from_point.name
        },
        arrivingPoint: {
          longitude: this.to_point.center[0],
          latitude: this.to_point.center[1],
          address: this.to_point.name
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
      this.from_point = JSON.parse(localStorage.getItem("initial_from_point"))
      this.to_point = JSON.parse(localStorage.getItem("initial_to_point"))
      this.geoJsonSource.data.geometry.coordinates = JSON.parse(localStorage.getItem("initial_directions"))
      this.weight = 0
      this.description = ""
      this.type = ""
    }
  },
  data() {
    return {
      activeColor: "#ffe082",
      accessToken: config.api.accessToken,
      mapStyle: config.api.mapStyle,
      center: config.api.init_values.map_center,
      from_point: {
        center: config.api.init_values.from_point,
        name: ""
      },
      to_point: {
        center: config.api.init_values.to_point,
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
      selectedAddress: ""
    }
  },
  created() {
    this.mapbox = Mapbox
  }
}
</script>
<style>

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
}

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

.user-address-select {
  border: 1px solid black;
  border-radius: 5px;
  width: 250px;
}

.departures-title-text {
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

.select-address-container-text {
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
</style>