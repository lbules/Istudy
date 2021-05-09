<template>
    <div class="row">
        <div class="f-main clearfix">
            <div class="setting-left col-md-2">
                <img class="setting-header" v-bind:src="memberInfo.photo">
                <div>{{memberInfo.name}}</div>

                <div class="split-line" style="margin-bottom: 20px;"></div>

                <ul class="user-menu-nav-block">
                    <a href="#">
                        <li v-on:click="openInfo()" class="user-menu-nav">基本信息</li>
                    </a>
                    <a href="#">
                        <li v-on:click="openCourse()" class="user-menu-nav">我的课程</li>
                    </a>
                    <a href="#">
                        <li v-on:click="openCollection()" class="user-menu-nav">收藏管理</li>
                    </a>
                    <a href="#">
                        <li v-on:click="openComment()" class="user-menu-nav">评论管理</li>
                    </a>
                    <a href="#">
                        <li v-on:click="openPassword()" class="user-menu-nav">修改密码</li>
                    </a>
                </ul>
            </div>
            <div class="setting-right col-md-10">
                <div><span class="f-16">{{title}}</span></div>
                <div class="split-line" style="margin: 20px 0px;"></div>
                <!--右侧显示内容区域-->
                <div class="clearfix col-md-12">

                    <!--收藏的课程-->
                    <div v-for="c in collection" :key=c.id style="float: left;" v-show="STATUS===STATUS_COLLECTION">
                        <the-collection v-bind:collectionCourse="c"></the-collection>
                    </div>

                    <!--报名的课程-->
                    <div v-for="c in memberCourse" style="float: left;" v-show="STATUS===STATUS_COURSE">
                        <member-course v-bind:memberCourse="c"></member-course>
                    </div>

                    <!--测试个人信息-->
                    <div class="col-md-9" style="float: left" v-show="STATUS===STATUS_INFO">
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
                                            <textarea v-model="memberInfo.introduction" class="form-control" rows="5"></textarea>
                                        </div>
                                    </div>
                                </form>
                                <!--弹出框内容--END-->
                            </div>
                            <div class="modal-footer">
                                <button v-on:click="saveMember()" type="button" class="btn btn-primary">保存</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div>
                    </div>
                    <!--测试个人信息-END-->

                    <!--测试修改密码-->
                    <div id="resetPassword-modal" class="modal fade" tabindex="-1" role="dialog">
                        <div class="modal-dialog modal-login" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="register-div">
                                        <h3 style="text-align: center">修改密码</h3>
                                        <div class="form-group">
                                            <input v-on:blur="onPasswordBlur()"
                                                   v-bind:class="PasswordValidateClass"
                                                   id="password" v-model="memberInfo.password"
                                                   class="form-control" placeholder="原密码" type="password">
                                            <span v-show="PasswordValidate === false" class="text-danger">密码最少6位，必须包含字母和数字</span>
                                        </div>
                                        <div class="form-group">
                                            <input v-on:blur="onConfirmPasswordBlur()"
                                                   v-bind:class="ConfirmPasswordValidateClass"
                                                   id="confirm-password" v-model="memberInfo.password"
                                                   class="form-control" placeholder="新密码"
                                                   name="memberRegisterConfirm" type="password">
                                            <span v-show="ConfirmPasswordValidate === false" class="text-danger">确认两次密码一致</span>
                                        </div>
                                        <div class="form-group">
                                            <button class="btn btn-primary btn-block submit-button" v-on:click="resetPassword()">
                                                确认
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--测试修改密码-END-->
                </div>
            </div>
        </div>

</template>

<script type="text/javascript">
    import TheCollection from "../components/the-collection";
    import BigFile from "../components/big-file";
    import MemberCourse from "../components/member-course";

    $(function () {
        $('.user-menu-nav').hover(function () {
            $(this).find('span').css('color', '#0089D2');
        }, function () {
            $(this).find('span').css('color', '#777');
        });
    });
    export default {
        components: {MemberCourse, TheCollection,BigFile},
        data: function () {
            return {
                collection: [], //收藏课程
                memberCourse:[], //报名的课程
                memberComment:[], //评论

                FILE_USE: FILE_USE, //上传文件
                SEX:SEX, //性别选项
                memberInfo:{},

                PasswordValidate:null,
                ConfirmPasswordValidate:null,

                //右侧显示内容的切换
                STATUS:'', //初始默认为空
                STATUS_COLLECTION:'STATUS_COLLECTION', //收藏
                STATUS_COURSE:'STATUS_COURSE',
                STATUS_INFO:'STATUS_INFO',
                STATUS_COMMENT:'STATUS_COMMENT',
                title:'',

            }
        },

        mounted() {
            let _this = this;
            //获取会员信息
            _this.memberinfo();
            //获取用户报名的课程
            _this.listMemberCourse();
            //获取会员收藏的课程
            _this.listCollection();
            //查找会员的评论
            _this.listComment();
            //默认进入到个人主页显示个人信息页面
            _this.openInfo();


        },
        computed:{
            PasswordValidateClass: function () {
                return {
                    'border-success': this.PasswordValidate === true,
                    'border-danger': this.PasswordValidate === false,
                }
            },

            ConfirmPasswordValidateClass: function () {
                return {
                    'border-success': this.ConfirmPasswordValidate === true,
                    'border-danger': this.ConfirmPasswordValidate === false,
                }
            },
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
                        _this.memberCourse = resp.content;
                    }
                }).catch((response) => {
                    console.log("error：", response);
                })
            },

            /*获取会员所有的评论*/
            listComment() {
                let _this = this;
                //获取登录会员的信息
                let loginMember = Tool.getLoginMember();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/home/listComment/' + loginMember.id).then((response) => {
                    console.log("查询我的评论：", response);
                    let resp = response.data;

                    if (resp.success) {
                        _this.memberComment = resp.content;
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

            //打开修改密码模态框
            openPassword() {
                let _this = this;
                $("#resetPassword-modal").modal("show");
            },

            //修改密码
            resetPassword() {

            },

            //打开个人信息页面
            openInfo() {
              let _this = this;
              _this.STATUS = _this.STATUS_INFO;
                _this.title ='基本信息';
            },

            //打开我的收藏
            openCollection() {
                let _this = this;
                _this.STATUS = _this.STATUS_COLLECTION;
                _this.title ='我的收藏';
            },

            //打开我的报名课程
            openCourse() {
                let _this = this;
                _this.STATUS = _this.STATUS_COURSE;
                _this.title ='我的报名';
            },

            //打开我的评论
            openComment() {
                let _this = this;
                _this.STATUS = _this.STATUS_COMMENT;
                _this.title ='我的评论';
            },


            // ==================重置密码校验============
            onPasswordBlur () {
                let _this = this;
                _this.PasswordValidate = Pattern.validatePasswordWeak(_this.memberInfo.password);
                return _this.registerMobileValidate;
            },
            onConfirmPasswordBlur () {
                let _this = this;
                let confirmPassword = $("#confirm-password").val();
                if (Tool.isEmpty(confirmPassword)) {
                    return _this.ConfirmPasswordValidate = false;
                }
                return _this.ConfirmPasswordValidate = (confirmPassword === _this.memberInfo.password);
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