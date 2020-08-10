import Vue from 'vue'
import Router from 'vue-router'
import DepartureCreationPage from "@/components/DepartureCreationPage";
import AllDepartures from "@/components/AllDepartures";
import Departure from "@/components/Departure";

Vue.use(Router)

let router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: () => import('./components/Greeting'),
        },
        {
            path: '/departure/create',
            component: DepartureCreationPage,
        },
        {
            path: '/user',
        },
        {
            path: '/departures',
            component: AllDepartures,
        },
        {
            path: '/departure',
            component: Departure,
        },
        {
            path: '/login',
            component: () => import('./components/Login'),
        },
        {
            path: '/register',
            component: () => import('./components/Register'),
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