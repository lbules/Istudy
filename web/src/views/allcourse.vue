<template>
    <main role="main">
        <div class="album py-5 bg-light">
            <div class="container">
                <!--课程列表-->
                <div class="row">
                    <div v-for="courseItem in courses" class="col-md-4">
                        <the-course v-bind:course="courseItem"></the-course>
                    </div>
                    <h3 v-show="courses.length===0">课程还未上架，敬请期待！</h3>
                </div>
                <!--分页-->
                <div class="row">
                    <div class="col-md-12">
                        <pagination ref="pagination" v-bind:list="listAllCourse"></pagination>
                    </div>
                </div>

            </div>
        </div>
    </main>
</template>
<script>
    import TheCourse from "../components/the-course";
    import Pagination from "../components/pagination";
    export default {
        name: 'allcourse',
        components: {Pagination, TheCourse},
        data:function () {
            return {
                courses:[],
            }
        },
        mounted() {
            let _this = this;
            //每页显示的记录条数
            _this.$refs.pagination.size = 1;
            _this.listAllCourse(1)
        },

        methods: {

            //查找已发布的全部课程
            listAllCourse(page) {
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/course/list', {
                    page: page,
                    size:_this.$refs.pagination.size,
                    // categoryId: _this.level2Id || _this.level1Id || "", // 优先取level2Id
                }).then((response) => {
                    console.log("查询所有课程的结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        _this.courses = resp.content.list;
                        _this.$refs.pagination.render(page, resp.content.total);
                    }
                }).catch((response) => {
                    console.log("error：", response);
                })
            },
        }
    }
</script>