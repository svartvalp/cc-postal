import Vue from 'vue'
import Router from 'vue-router'
import DepartureCreationPage from "@/components/DepartureCreationPage";


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
        }
    ]
})