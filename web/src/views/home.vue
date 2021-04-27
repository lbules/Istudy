<template>
    <div class="row">
        <div class="f-main clearfix">
            <div class="setting-left col-md-2">
                <img class="setting-header" src="../../public/static/image/header.jpg">
                <div>牛氓gadfly</div>

                <div class="split-line" style="margin-bottom: 20px;"></div>

                <ul class="user-menu-nav-block">
                    <a href="#">
                        <li class="user-menu-nav">我的课程</li>
                    </a>
                    <a href="#">
                        <li class="user-menu-nav">我的收藏</li>
                    </a>
                    <a href="#">
                        <li class="user-menu-nav">个人信息</li>
                    </a>
                    <a href="">
                        <li class="user-menu-nav">修改密码</li>
                    </a>
                </ul>
            </div>
            <div class="setting-right col-md-10">
                <div><span class="f-16">最新动态</span></div>
                <div class="split-line" style="margin: 20px 0px;"></div>
                <div class="clearfix">

                    <!--收藏的课程-->
                    <div v-for="c in collection" :key=c.id class="col-md-3" style="float: left">
                        <!--<the-collection v-bind:collectionCourse="c"></the-collection>-->
                    </div>

                    <!--测试个人信息-->
                    <!--<div id="form-modal" class="modal fade" tabindex="-1" role="dialog">-->
                    <div class="col-md-9" style="float: left">
                        <div class="modal-content">
                            <div class="modal-body">

                                <!--弹出框内容-->
                                <form class="form-horizontal">
                                    <div class="form-group">
                                    <label class="col-sm-2 control-label">昵称</label>
                                    <div class="col-sm-10">
                                        <input class="form-control">
                                    </div>
                                </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">性别</label>
                                        <div class="col-sm-10">
                                            <select class="form-control">
                                                <option v-for="o in SEX" v-bind:value="o.key">{{o.value}}</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">头像</label>
                                        <div class="col-sm-10">
                                            <file
                                                    v-bind:suffixs="['jpg', 'jpeg', 'png']"
                                                    v-bind:use="FILE_USE.MEMBER.key"
                                                    v-bind:input-id="'image-upload'"
                                                    v-bind:text="'上传头像'"
                                                    v-bind:after-upload="afterUpload"></file>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <img src="../../public/static/image/header.jpg"
                                                         class="img-responsive">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">个人简介</label>
                                        <div class="col-sm-10">
                                            <input class="form-control">
                                        </div>
                                    </div>
                                </form>
                                <!--弹出框内容--END-->
                            </div>
                            <div class="modal-footer">
                                <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                    <!--</div>-->
                    <!--测试个人信息-END-->
                </div>
            </div>
        </div>
    </div>

</template>

<script type="text/javascript">
    import TheCollection from "../components/the-collection";
    import File from "../components/file";

    $(function () {
        $('.user-menu-nav').hover(function () {
            $(this).find('span').css('color', '#0089D2');
        }, function () {
            $(this).find('span').css('color', '#777');
        });
    });
    export default {
        components: {TheCollection,File},
        data: function () {
            return {
                collection: [], //收藏课程

                FILE_USE: FILE_USE, //上传文件
                SEX:SEX, //性别选项
            }
        },

        mounted() {
            let _this = this;

            //测试获取用户报名的课程
            _this.listMemberCourse();

        },
        methods: {
            /*获取会员所有的收藏课程*/
            listCollection() {
                let _this = this;
                //获取登录会员的信息
                let loginMember = Tool.getLoginMember();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/courseCollection/listCollection/' + loginMember.id).then((response) => {
                    console.log("查询收藏的结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        _this.collection = resp.content;
                    }
                }).catch((response) => {
                    console.log("error：", response);
                })
            },

            /*获取会员已报名的所有课程*/
            listMemberCourse() {
                let _this = this;
                //获取登录会员的信息
                let loginMember = Tool.getLoginMember();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/home/listMemberCourse/' + loginMember.id).then((response) => {
                    console.log("查询我的课程结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        _this.collection = resp.content;
                    }
                }).catch((response) => {
                    console.log("error：", response);
                })
            },

            //回调函数
            afterUpload(resp) {
                let _this = this;
                let image = resp.content;
                // _this.teacher.image = image;
            },

        }
    }
</script>