<template>
    <div class="main-container">
        <div class="main-content">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1">
                    <div class="login-container">
                        <div class="center">
                            <h1>
                                <!--<i class="ace-icon fa fa-leaf green"></i>-->
                                <span class="red">iStudy控台登录</span>
                            </h1>
                        </div>

                        <div class="space-6"></div>

                        <div class="position-relative">
                            <div id="login-box" class="login-box visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="ace-icon fa fa-coffee green"></i>
                                            请输入用户名密码
                                        </h4>

                                        <div class="space-6"></div>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input v-model="user.loginName" type="text"
                                                                   class="form-control" placeholder="用户名"/>
  															<i class="ace-icon fa fa-user"></i>
  														</span>
                                                </label>

                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input v-model="user.password" type="password"
                                                                   class="form-control"
                                                                   placeholder="密码"/>
  															<i class="ace-icon fa fa-lock"></i>
  														</span>
                                                </label>

                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
                                                            <div class="input-group">
                                                                <input v-model="user.imageCode" type="text" class="form-control" placeholder="验证码">
                                                                <span class="input-group-addon" id="basic-addon2">
                                                                    <img v-on:click="loadImageCode()" id="image-code" alt="验证码">

                                                                </span>
                                                            </div>
  														</span>
                                                </label>

                                                <div class="space"></div>

                                                <div class="clearfix">
                                                    <label class="inline">
                                                        <input v-model="remember" type="checkbox" class="ace"/>
                                                        <span class="lbl"> 记住我</span>
                                                    </label>

                                                    <button type="button"
                                                            class="width-35 pull-right btn btn-sm btn-primary"
                                                            v-on:click="login()"
                                                    >
                                                        <i class="ace-icon fa fa-key"></i>
                                                        <span class="bigger-110">登录</span>
                                                    </button>
                                                </div>

                                                <div class="space-4"></div>
                                            </fieldset>
                                        </form>


                                    </div><!-- /.widget-main -->
                                </div><!-- /.widget-body -->
                            </div><!-- /.login-box -->

                            <div id="forgot-box" class="forgot-box widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header red lighter bigger">
                                            <i class="ace-icon fa fa-key"></i>
                                            Retrieve Password
                                        </h4>

                                        <div class="space-6"></div>
                                        <p>
                                            Enter your email and to receive instructions
                                        </p>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input type="email" class="form-control"
                                                                   placeholder="Email"/>
  															<i class="ace-icon fa fa-envelope"></i>
  														</span>
                                                </label>

                                                <div class="clearfix">
                                                    <button type="button"
                                                            class="width-35 pull-right btn btn-sm btn-danger">
                                                        <i class="ace-icon fa fa-lightbulb-o"></i>
                                                        <span class="bigger-110">Send Me!</span>
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div><!-- /.widget-main -->

                                    <div class="toolbar center">
                                        <a href="#" data-target="#login-box" class="back-to-login-link">
                                            Back to login
                                            <i class="ace-icon fa fa-arrow-right"></i>
                                        </a>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.forgot-box -->

                            <div id="signup-box" class="signup-box widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header green lighter bigger">
                                            <i class="ace-icon fa fa-users blue"></i>
                                            New User Registration
                                        </h4>

                                        <div class="space-6"></div>
                                        <p> Enter your details to begin: </p>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input type="email" class="form-control"
                                                                   placeholder="Email"/>
  															<i class="ace-icon fa fa-envelope"></i>
  														</span>
                                                </label>

                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input type="text" class="form-control"
                                                                   placeholder="Username"/>
  															<i class="ace-icon fa fa-user"></i>
  														</span>
                                                </label>

                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input type="password" class="form-control"
                                                                   placeholder="Password"/>
  															<i class="ace-icon fa fa-lock"></i>
  														</span>
                                                </label>

                                                <label class="block clearfix">
  														<span class="block input-icon input-icon-right">
  															<input type="password" class="form-control"
                                                                   placeholder="Repeat password"/>
  															<i class="ace-icon fa fa-retweet"></i>
  														</span>
                                                </label>

                                                <label class="block">
                                                    <input type="checkbox" class="ace"/>
                                                    <span class="lbl">
  															I accept the
  															<a href="#">User Agreement</a>
  														</span>
                                                </label>

                                                <div class="space-24"></div>

                                                <div class="clearfix">
                                                    <button type="reset" class="width-30 pull-left btn btn-sm">
                                                        <i class="ace-icon fa fa-refresh"></i>
                                                        <span class="bigger-110">Reset</span>
                                                    </button>

                                                    <button type="button"
                                                            class="width-65 pull-right btn btn-sm btn-success">
                                                        <span class="bigger-110">Register</span>

                                                        <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>

                                    <div class="toolbar center">
                                        <a href="#" data-target="#login-box" class="back-to-login-link">
                                            <i class="ace-icon fa fa-arrow-left"></i>
                                            Back to login
                                        </a>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.signup-box -->
                        </div><!-- /.position-relative -->

                        <div class="navbar-fixed-top align-right">
                            <br/>
                            &nbsp;
                            <a id="btn-login-dark" href="#">Dark</a>
                            &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-blur" href="#">Blur</a>
                            &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-light" href="#">Light</a>
                            &nbsp; &nbsp; &nbsp;
                        </div>
                    </div>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container -->
</template>
<script>

    export default {
        name: 'login',
        data: function () {
            return {
                user: {},
                remember: true, //记住登录信息
                imageCodeToken: ""

            }
        },
        mounted: function () {
            let _this = this;
            $('body').remove('no-skin');
            $('body').attr('class', 'login-layout light-login');
            //获取记住的用户信息（账号密码）
            let rememberUser = LocalStorage.get("loginUser");
            if (rememberUser) {
                //有缓存则说明已记住
                _this.user = rememberUser;
            }
            //加载初始化的验证码图片
            _this.loadImageCode();
        },
        methods: {
            login() {
                let _this = this;

                //判断用户名和密码是否未输入
                if (_this.user.loginName != null && _this.user.password != null) {
                    //如果缓存中已有密码，就不需要对密码进行加密
                    let md5 = hex_md5(_this.user.password);
                    let rememberUser = LocalStorage.get("loginUser")||{};
                    if (md5 !== rememberUser.md5) {
                        //如果md5的值和rememberUser中不一样时，才进行加密
                        _this.user.password = hex_md5(_this.user.password + KEY);
                    }
                    //获取图片验证码
                    _this.user.imageCodeToken = _this.imageCodeToken;

                    if (_this.user.imageCode!=null) {
                        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then((response) => {

                            let resp = response.data;
                            if (resp.success) {
                                console.log("登录成功:", resp.content);
                                console.log(resp.content);
                                let loginUser = resp.content;
                                //保存登录信息
                                Tool.setLoginUser(resp.content);
                                //记住我
                                if (_this.remember) {
                                    //勾选记住我，将用户名和密码保存到本地缓存，md5用于检测是否被重新输入过
                                    let md5 = hex_md5(_this.user.password);
                                    LocalStorage.set("loginUser", {
                                        loginName: loginUser.loginName,
                                        password: _this.user.password,
                                        md5: md5
                                    })
                                } else {
                                    //清空本地缓存
                                    LocalStorage.set("loginUser", null);
                                }

                                this.$router.push("/welcome")
                            } else {
                                Toast.warning(resp.message);
                                _this.user.password = "";
                                _this.loadImageCode();
                            }
                        })
                    }
                    else {
                        Toast.warning("请输入验证码！")
                    }
                }
                else {
                    Toast.warning("请先输入用户名和密码！");
                }
            },

            /**
             * 加载图形验证码
             */
            loadImageCode: function () {
                let _this = this;
                _this.imageCodeToken = Tool.uuid(8);
                $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
            },

        }
    }
</script>

<style scoped>
    .input-group-addon {
        padding: 0;
    }
</style>

