<template>
    <div class="main-field">
        <div class="departures-title-container">
            <div class="departures-title-text">Ваши посылки</div>
            <v-btn @click="addDeparture" class="mx-2 departures-title-add-departure-button" color="indigo" dark fab
                   justify-end>
                <v-icon dark>mdi-plus</v-icon>
            </v-btn>
        </div>
        <v-container id="inspire">
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
                                    <v-list-item-title class="addresses">Адрес доставки: <span>{{item.arrivingPoint.address}}</span>
                                    </v-list-item-title>
                                </v-list-item-content>
                                <v-list-item-content class="middle-content">
                                    <v-list-item-title class="departure-date">Дата отправления: <span>{{item.departureDate}}</span>
                                    </v-list-item-title>
                                    <v-list-item-title class="departure-type">Тип посылки: <span>{{item.type}}</span>
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
      props: {
        user: Object
      },
      data() {
        return {
          departures: [],
        }
      },
        methods: {
            addDeparture() {
                this.$router.push('/departure/create')
            },
            redirectToDeparture(departure) {
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
        },
        mounted() {
            this.$http.get(`/departure/list/${this.user.id}`)
                .then(response => {
                    this.departures = response.data.filter(item => item.userId === this.user.id)
                    for (let i = 0; i < this.departures.length; ++i) {
                        this.departures[i].departureDate = this.getDate(this.departures[i].departureDate)
                    }
                })
        }
    }
</script>

<style>
    .main-field {
        display: flex;
        flex-direction: column;
        align-items: center;
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

    .departures-title-add-departure-button {

    }
</style>