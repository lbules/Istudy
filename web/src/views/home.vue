<template>
        <div class="row">
            <div class="f-main clearfix">
                <div class="setting-left col-md-2">
                    <img class="setting-header" src="../../public/static/image/header.jpg">
                    <div>牛氓gadfly</div>

                    <div class="split-line" style="margin-bottom: 20px;"></div>

                    <ul class="user-menu-nav-block">
                        <a href="#"><li class="user-menu-nav">我的课程</li></a>
                        <a href="#"><li class="user-menu-nav">我的收藏</li></a>
                        <a href="#"><li class="user-menu-nav">个人信息</li></a>
                        <a href=""><li class="user-menu-nav">修改密码</li></a>
                    </ul>
                </div>
                <div class="setting-right col-md-10">
                    <div><span class="f-16">最新动态</span></div>
                    <div class="split-line" style="margin: 20px 0px;"></div>
                    <div class="clearfix">
                        <div v-for="c in collection" :key=c.id class="col-md-3" style="float: left">
                            <the-collection v-bind:collectionCourse="c"></the-collection>
                        </div>
                    </div>
                </div>
            </div>
        </div>

</template>

<script type="text/javascript">
    import TheCollection from "../components/the-collection";
    $(function(){
        $('.user-menu-nav').hover(function(){
            $(this).find('span').css('color','#0089D2');
        },function(){
            $(this).find('span').css('color','#777');
        });
    });
    export default {
        components: {TheCollection},
        data: function () {
            return {
                collection: [], //收藏课程
            }
        },

        mounted() {
            let _this = this;

            //测试收藏课程功能
            _this.listCollection();
        },
        methods: {
            /*获取会员所有的收藏课程*/
            listCollection() {
                let _this = this;
                //获取登录会员的信息
                let loginMember = Tool.getLoginMember();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/courseCollection/listCollection/'+loginMember.id).then((response) => {
                    console.log("查询收藏的结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        _this.collection = resp.content;
                    }
                }).catch((response) => {
                    console.log("error：", response);
                })
            }
        }
    }
</script>