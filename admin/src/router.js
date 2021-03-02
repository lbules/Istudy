import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Chapter from "./views/admin/chapter"

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    },{
        path:'/login',
        component: Login
    },{
        path: '/',
        name: "admin",
        component: Admin,
        children:[{
            // 子路由不需要加/
            path:'welcome',
            name:"welcome-sidebar",
            component: Welcome
        },{
            path: 'business/chapter',
            name:"business-chapter-sidebar",
            component: Chapter,
        }]
    }]
})