<template>
    <div class="row">
        <div class="f-main clearfix">
            <div class="setting-left col-md-2">
                <img class="setting-header" v-bind:src="memberInfo.photo">
                <div>{{memberInfo.name}}</div>

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
                    <div class="col-md-9" style="float: left">
                        <div class="modal-content">
                            <div class="modal-body">

                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <!--<label class="col-sm-2 control-label">头像</label>-->
                                        <div class="col-sm-10">
                                            <big-file
                                                    v-bind:suffixs="['jpg', 'jpeg', 'png']"
                                                    v-bind:use="FILE_USE.MEMBER.key"
                                                    v-bind:input-id="'image-upload'"
                                                    v-bind:text="'上传头像'"
                                                    v-bind:after-upload="afterUpload"></big-file>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <!--<img v-bind:src="memberInfo.photo" class="img-responsive" style="width: 100px;height: 100px;">-->
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                    <label class="col-sm-2 control-label">昵称</label>
                                    <div class="col-sm-10">
                                        <input v-model="memberInfo.name" class="form-control">
                                    </div>
                                </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">性别</label>
                                        <div class="col-sm-10">
                                            <select v-model="memberInfo.sex" class="form-control">
                                                <option v-for="o in SEX" v-bind:value="o.key">{{o.value}}</option>
                                            </select>
                                        </div>
                                    </div>



                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">个人简介</label>
                                        <div class="col-sm-10">
                                            <input v-model="memberInfo.introduction" class="form-control">
                                        </div>
                                    </div>
                                </form>
                                <!--弹出框内容--END-->
                            </div>
                            <div class="modal-footer">
                                <button v-on:click="saveMember()" type="button" class="btn btn-primary">保存</button>
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
    import BigFile from "../components/big-file";

    $(function () {
        $('.user-menu-nav').hover(function () {
            $(this).find('span').css('color', '#0089D2');
        }, function () {
            $(this).find('span').css('color', '#777');
        });
    });
    export default {
        components: {TheCollection,BigFile},
        data: function () {
            return {
                collection: [], //收藏课程
                FILE_USE: FILE_USE, //上传文件
                SEX:SEX, //性别选项
                memberInfo:{},
            }
        },

        mounted() {
            let _this = this;

            //测试获取用户报名的课程
            // _this.listMemberCourse();

            _this.memberinfo();

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

           memberinfo() {
               let _this = this;
               //获取登录会员的信息
               let loginMember = Tool.getLoginMember();
               _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/member/findMember/' + loginMember.id).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        console.log("查询会员成功",resp.content);
                        _this.memberInfo = resp.content;
                        console.log("查询member成功",_this.memberInfo);
                    }
               });
           },

            saveMember() {
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/member/save', _this.memberInfo).then((response) => {
                    console.log("保存结果:", response);
                    let resp = response.data;
                    if (resp.success) { //保存成功就将弹出框隐藏
                        _this.memberinfo(); //重新刷新
                        Toast.success("保存成功");
                    } else {
                        Toast.warning(resp.message);
                    }
                })
            },

            //回调函数
            afterUpload(resp) {
                let _this = this;
                let image = resp.content.path;
                _this.memberInfo.photo = image;
            },

        }
    }
</script>