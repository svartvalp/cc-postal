import Vue from 'vue'
import Router from 'vue-router'
import DepartureCreationPage from "@/components/DepartureCreationPage";
import AllDepartures from "@/components/AllDepartures";
import Departure from "@/components/Departure";
import Account from "@/components/Account";
import Login from "@/components/Login";
import Greeting from "@/components/Greeting"
import Register from "@/components/Register";

Vue.use(Router)

let router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: Greeting
        },
        {
            path: '/departure/create',
            component: DepartureCreationPage
        },
        {
            path: '/user',
            component: Account
        },
        {
            path: '/departures',
            component: AllDepartures
        },
        {
            path: '/departure',
            component: Departure
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/register',
            component: Register,
        }
    ]
})

router.beforeEach((to, from, next) => {
    if (!to.path.match("\\/login|\\/register") && localStorage.getItem('jwt') == null) {
        next('/login')
    } else {
        next()
    }
})

export default router