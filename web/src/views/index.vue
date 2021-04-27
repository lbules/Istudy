<template>
    <main role="main">
        <section class="jumbotron text-center">
            <div class="container">
                <h1>在线视频课程平台</h1>
                <p class="lead text-muted m-3">
                    知识付费时代刚刚起步，在这个领域有很多的发展机会。整个课程以实战为基础，手把手从零开始，一步一步搭建一个完整的企业级开发架构。不讲废话，只讲干货。
                </p>
                <p>
                    <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</router-link>
                </p>
            </div>
        </section>

        <div class="album py-5 bg-light">
            <div class="container">
                <div class="index-container">
                    <h1 class="index-row skew-title">
                        <span>N</span>
                        <span>E</span>
                        <span>W</span>
                    </h1>
                </div>
                <!--显示课程卡片-->
                <div class="row">
                    <div v-for="o in news" :key=o.id class="col-md-4">
                        <new-course v-bind:newcourse="o"></new-course>
                    </div>
                </div>

                <hr>

                <div class="index-container">
                    <h1 class="index-row skew-title">
                        <span class="alt">H</span>
                        <span class="alt">O</span>
                        <span class="alt">T</span>
                    </h1>
                </div>
                <div class="row">
                    <div class="row">
                        <div v-for="o in news" :key=o.id class="col-md-4">
                            <new-course v-bind:newcourse="o"></new-course>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>


<script>

    import NewCourse from "../components/new-course";
    export default {
        name: 'index',
        components: {NewCourse},
        data: function () {
            return {
                news: [], //最新课程
            }
        },

        mounted() {
            let _this = this;
            console.log("hello!")
            _this.listNew();
        },
        methods: {
            listNew() {
                console.log("kaissjil")
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/list-new').then((response) => {
                    console.log("查询新上好课结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        _this.news = resp.content;
                    }
                }).catch((response) => {
                    console.log("error：", response);
                })
            }
        }

    }

</script>