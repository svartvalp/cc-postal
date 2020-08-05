import Vue from 'vue'
import Router from 'vue-router'
import DepartureCreationPage from "@/components/DepartureCreationPage";
import AllDepartures from "@/components/AllDepartures";
import Departure from "@/components/Departure";

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/login',
            component: () => import('./components/Login')
        },
        {
            path: '/departure/create',
            component : DepartureCreationPage
        },
        {
            path: '/departures',
            component: AllDepartures
        },
        {
            path: '/departure',
            component: Departure
        }
    ]
})