import Vue from "vue"
import Router from "vue-router"
import Index from "./views/index.vue"
import List from "./views/allCourse.vue"
import CourseDetail from "./views/courseDetail.vue"

Vue.use(Router);

export default new Router({
    mode:"history",
    base:process.env.BASE_URL,
    routes:[{
        path: "*",
        redirect:"/index",
    },
        {
            path:"/index",
            component: Index
        },
        {
            path:"/list",
            component: List
        },

        {
            path:"/courseDetail",
            component: CourseDetail
        }
    ]
})