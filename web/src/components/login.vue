<template>
    <div id="login-modal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-login" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <!--登录-->
                    <div class="login-div" v-show="MODAL_STATUS === STATUS_LOGIN">
                        <h3>登&nbsp;&nbsp;录</h3>
                        <div class="form-group">
                            <input v-model="member.mobile" class="form-control" placeholder="手机号">
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="password" placeholder="密码" v-model="member.password">
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <input id="image-code-input" class="form-control" type="text" placeholder="验证码"
                                       v-model="member.imageCode">
                                <div class="input-group-addon" id="image-code-addon">
                                    <img id="image-code" style="height: 45px;" alt="验证码" v-on:click="loadImageCode()"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <button v-on:click="login()" class="btn btn-primary btn-block submit-button">
                                登&nbsp;&nbsp;录
                            </button>
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" class="remember" v-model="remember"> 记住密码
                                </label>
                                <div class="pull-right">
                                    <a href="javascript:;" v-on:click="toRegisterDiv()">注册</a>
                                </div>
                            </div>
                        </div>
                        <div class="form-group to-register-div">
                        </div>
                    </div>
                    <!--注册-->
                    <div class="register-div" v-show="MODAL_STATUS === STATUS_REGISTER">
                        <h3>注&nbsp;&nbsp;册</h3>
                        <div class="form-group">
                            <input v-on:blur="onRegisterMobileBlur()"
                                   v-bind:class="registerMobileValidateClass"
                                   id="register-mobile" v-model="memberRegister.mobile"
                                   class="form-control" placeholder="手机号">
                            <!--校验未通过-->
                            <span v-show="registerMobileValidate === false" class="text-danger">请输入正确的手机号！</span>
                        </div>

                        <div class="form-group">
                            <input v-on:blur="onRegisterNameBlur()"
                                   v-bind:class="registerNameValidateClass"
                                    id="register-name" v-model="memberRegister.name"
                                   class="form-control" placeholder="昵称">
                            <span v-show="registerNameValidate === false" class="text-danger">昵称2到20位中文,字母,数字,下划线组合</span>
                        </div>
                        <div class="form-group">
                            <input v-on:blur="onRegisterPasswordBlur()"
                                   v-bind:class="registerPasswordValidateClass"
                                    id="register-password" v-model="memberRegister.passwordOriginal"
                                   class="form-control" placeholder="密码" type="password">
                            <span v-show="registerPasswordValidate === false" class="text-danger">密码最少6位，必须包含字母和数字</span>
                        </div>
                        <div class="form-group">
                            <input v-on:blur="onRegisterConfirmPasswordBlur()"
                                   v-bind:class="registerConfirmPasswordValidateClass"
                                    id="register-confirm-password" v-model="memberRegister.confirm"
                                   class="form-control" placeholder="确认密码"
                                   name="memberRegisterConfirm" type="password">
                            <span v-show="registerConfirmPasswordValidate === false" class="text-danger">确认两次密码一致</span>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-block submit-button" v-on:click="register()">
                                注&nbsp;&nbsp;册
                            </button>
                        </div>
                        <div class="form-group to-login-div">
                            <a href="javascript:;" v-on:click="toLoginDiv()">返回登录</a>
                        </div>
                    </div>

                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</template>

<script>

    export default {
        name: 'the-login',
        data: function () {
            return {
                // 模态框内容切换：登录、注册、忘记密码
                STATUS_LOGIN: "STATUS_LOGIN",  //登录
                STATUS_REGISTER: "STATUS_REGISTER", //注册
                STATUS_FORGET: "STATUS_FORGET", //忘记密码
                MODAL_STATUS: "", //状态默认是空


                member: {}, //登录
                memberForget: {}, //忘记密码
                memberRegister: {}, //注册

                remember: true, // 记住密码
                imageCodeToken: "",

                //========注册框的错误信息提示=======
                registerMobileValidate: null, //手机号
                registerNameValidate: null, //昵称
                registerPasswordValidate: null, //密码
                registerConfirmPasswordValidate: null, //密码二次确认

            }
        },
        mounted() {
            let _this = this;
            //初始时显示登录模态框
            _this.toLoginDiv();
        },

        computed: {
            registerMobileValidateClass: function () {
                return {
                    'border-success': this.registerMobileValidate === true,
                    'border-danger': this.registerMobileValidate === false,
                }
            },

            registerPasswordValidateClass: function () {
                return {
                    'border-success': this.registerPasswordValidate === true,
                    'border-danger': this.registerPasswordValidate === false,
                }
            },
            registerNameValidateClass: function () {
                return {
                    'border-success': this.registerNameValidate === true,
                    'border-danger': this.registerNameValidate === false,
                }
            },
            registerConfirmPasswordValidateClass: function () {
                return {
                    'border-success': this.registerConfirmPasswordValidate === true,
                    'border-danger': this.registerConfirmPasswordValidate === false,
                }
            },
        },
        methods: {

            /**
             * 打开登录注册窗口
             */
            openLoginModal() {
                let _this = this;
                $("#login-modal").modal("show");
            },

            //---------------登录框、注册框、忘记密码框切换-----------------
            toLoginDiv() {
                let _this = this;
                _this.MODAL_STATUS = _this.STATUS_LOGIN
            },
            toRegisterDiv() {
                let _this = this;
                _this.MODAL_STATUS = _this.STATUS_REGISTER
            },

            // 注册
            register() {
                let _this = this;
                // 先判断手机号是否已经被注册过了
                this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/member/is-mobile-exist/' + _this.memberRegister.mobile).then((res) => {
                    let response = res.data;
                    if (response.success) { //已经被注册过了
                        Toast.warning("手机号:" + _this.memberRegister.mobile + "已被注册");
                        //将手机号输入栏清空
                        _this.memberRegister.mobile = "";
                    } else {  //手机号未被注册

                        _this.memberRegister.password = hex_md5(_this.memberRegister.passwordOriginal + KEY);
                        // 调服务端注册接口
                        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/member/register', _this.memberRegister).then((response) => {
                            let resp = response.data;
                            if (resp.success) {
                                Toast.success("注册成功");
                            } else {
                                Toast.warning(resp.message);
                            }
                        })
                    }
                })
            },


            //---------------登录-----------------
            login() {
                let _this = this;
                // 如果密码是从缓存带出来的，则不需要重新加密
                let md5 = hex_md5(_this.member.password);
                let rememberMember = LocalStorage.get(LOCAL_KEY_REMEMBER_MEMBER) || {};
                if (md5 !== rememberMember.md5) {
                    _this.member.password = hex_md5(_this.member.password + KEY);
                }
                _this.member.imageCodeToken = _this.imageCodeToken;

                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/member/login', _this.member).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        console.log("登录成功：", resp.content);
                        let loginMember = resp.content;
                        Tool.setLoginMember(resp.content);

                        // 判断“记住我”
                        if (_this.remember) {
                            // 如果勾选记住我，则将用户名密码保存到本地缓存
                            // 这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
                            let md5 = hex_md5(_this.member.password);
                            LocalStorage.set(LOCAL_KEY_REMEMBER_MEMBER, {
                                mobile: loginMember.mobile,
                                password: _this.member.password,
                                md5: md5
                            });
                        } else {
                            // 没有勾选“记住我”时，要把本地缓存清空，否则下次显示登录框时会自动显示用户名密码
                            LocalStorage.set(LOCAL_KEY_REMEMBER_MEMBER, null);
                        }
                        // 登录成功
                        _this.$parent.setLoginMember(loginMember);
                        $("#login-modal").modal("hide");

                    } else {
                        Toast.warning(resp.message);
                        _this.member.password = "";
                        _this.loadImageCode();
                    }
                });
            },
            /**
             * 加载图形验证码
             */
            loadImageCode: function () {
                let _this = this;
                _this.imageCodeToken = Tool.uuid(8);
                $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/business/web/kaptcha/image-code/' + _this.imageCodeToken);
            },


            // ====================注册框校验===================
            // 手机号输入栏失去焦点时校验输入的手机号是否符合格式
            onRegisterMobileBlur() {
                let _this = this;
                _this.registerMobileValidate = Pattern.validateMobile(_this.memberRegister.mobile);
                return _this.registerMobileValidate;
            },
            onRegisterNameBlur () {
                let _this = this;
                _this.registerNameValidate = Pattern.validateName(_this.memberRegister.name);
                return _this.registerMobileValidate;
            },

            onRegisterPasswordBlur () {
                let _this = this;
                _this.registerPasswordValidate = Pattern.validatePasswordWeak(_this.memberRegister.passwordOriginal);
                return _this.registerMobileValidate;
            },

            onRegisterConfirmPasswordBlur () {
                let _this = this;
                let confirmPassword = $("#register-confirm-password").val();
                if (Tool.isEmpty(confirmPassword)) {
                    return _this.registerConfirmPasswordValidate = false;
                }
                return _this.registerConfirmPasswordValidate = (confirmPassword === _this.memberRegister.passwordOriginal);
            },

        }
    }
</script>

<style scoped>
    /* 登录框 */
    .login-div .input-group-addon {
        padding: 0;
        border: 0;
    }

    #login-modal h3 {
        text-align: center;
        margin-bottom: 20px;
    }

    #login-modal .modal-login {
        max-width: 400px;
    }

    #login-modal input:not(.remember) {
        height: 45px;
        font-size: 16px;
    }

    #login-modal .submit-button {
        height: 50px;
        font-size: 20px;
    }

    #login-modal .to-login-div {
        text-align: center;
    }
</style>
