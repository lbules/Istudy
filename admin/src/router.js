import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Chapter from "./views/admin/chapter.vue"
import Section from "./views/admin/section.vue"
import Course from "./views/admin/course.vue"
import Category from "./views/admin/category.vue"
import Teacher from "./views/admin/teacher.vue"
import File from "./views/admin/file.vue"
import Content from "./views/admin/content.vue"
import User from "./views/admin/user.vue"
import Resources from "./views/admin/resources.vue"
import Role from "./views/admin/role.vue"
import Member from "./views/admin/member.vue"
import Count    from "./views/admin/count.vue"

Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    }, {
        path: "/login",
        component: Login
    },
        {
            path: "",
            component: Login
        },
        {
            path: "/",
            name: "admin",
            component: Admin,
            // 登录拦截
            meta: {
                loginRequire: true
            },
            children: [{
                // 子路由不需要加/
                path: 'welcome',
                name: "welcome",
                component: Welcome
            }, {
                path: 'business/chapter',
                name: "business/chapter",
                component: Chapter,
            },
                {
                    path: 'business/category',
                    name: "business/category",
                    component: Category,
                },
                {
                    path: 'business/section',
                    name: "business/section",
                    component: Section,
                },
                {
                    path: 'business/course',
                    name: "business/course",
                    component: Course,
                },

                {
                    path: 'business/teacher',
                    name: "business/teacher",
                    component: Teacher,
                },

                {
                    path: 'file/file',
                    name: "file/file",
                    component: File,
                },

                {
                    path: "business/content",
                    name: "business/content",
                    component: Content,
                },
                {
                    path: "system/user",
                    name: "system/user",
                    component: User,
                },
                {
                    path: "system/resources",
                    name: "system/resources",
                    component: Resources,
                },

                {
                    path: "system/role",
                    name: "system/role",
                    component: Role,
                },

                {
                    path: "business/member",
                    name: "business/member",
                    component: Member,
                },

                {
                    path: "business/count",
                    name: "business/count",
                    component: Count,
                }

            ]
        }]
})
