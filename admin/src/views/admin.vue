<template>
    <div>
        <div id="navbar" class="navbar navbar-default          ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler"
                        data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="index.html" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            iStudy管理后台
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">

                        <!--右侧退出登录-->
                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <!--<img class="nav-user-photo" src="../../public/ace/assets/images/avatars/user.jpg" alt="Jason's Photo" />-->
                                <span>{{loginUser.name}}</span>
                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a v-on:click="changeUser(user)">
                                        <i class="ace-icon fa fa-user"></i>
                                        切换用户
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a v-on:click="loginout()" href="#">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        退出登录
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div><!-- /.navbar-container -->
        </div>

        <div class="main-container ace-save-state" id="main-container">


            <div id="sidebar" class="sidebar                  responsive                    ace-save-state">

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div><!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list">
                    <li class="" id="welcome-sidebar">
                        <!--跳转到welcome页面-->
                        <router-link to="/welcome">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text"> 欢迎 </span>
                        </router-link>

                        <b class="arrow"></b>
                    </li>

                    <!--table-->
                    <li class="" v-show="hasResources('01')">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 权限管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li v-show="hasResources('0101')" class="" id="system-user-sidebar" >
                                <router-link to="/system/user">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    用户管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>


                            <li v-show="hasResources('0103')" class="" id="system-role-sidebar">
                                <router-link to="/system/role" href="jqgrid.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    角色管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class="" v-show="hasResources('02')">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 业务管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class="" id="business-category-sidebar" v-show="hasResources('0201')">
                                <router-link to="/business/category">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    课程分类
                                </router-link>
                                <b class="arrow"></b>
                            </li>

                            <li class="" id="business-course-sidebar" v-show="hasResources('0202')">
                                <router-link to="/business/course">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    课程管理
                                </router-link>
                                <b class="arrow"></b>
                            </li>

                        </ul>
                    </li>

                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 讲师管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class="" id="business-teacher-sidebar" v-show="hasResources('0203')">
                                <router-link to="/business/teacher">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    讲师管理
                                </router-link>
                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> 运维管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class="" id="business-count-sidebar">
                                <router-link to="/business/count">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    统计分析
                                </router-link>
                                <b class="arrow"></b>
                            </li>
                        </ul>

                        <ul class="submenu">
                            <li class="" id="business-member-sidebar">
                                <router-link to="/business/member">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    会员管理
                                </router-link>
                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>
                </ul><!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
                       data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <!--欢迎页面-->
                                <router-view/>
                                <!-- PAGE CONTENT ENDS -->
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
        						<span class="bigger-120">
        							<span class="blue bolder">Luoss</span>
        							 &copy; 2020-2021
        						</span>

                        &nbsp; &nbsp;
                        <span class="action-buttons">

        						</span>
                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div>
        <!-- /.main-container -->

        <!--模态框-->
        <div id="change-user-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <!--<h4 class="modal-title">切换用户</h4>-->
                    </div>
                    <div class="modal-body">

                        <!--弹出框内容-->
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">账号</label>
                                <div class="col-sm-10">
                                    <input v-model="user.loginName" class="form-control">
                                </div>
                            </div>

                            <div v-show="!user.id" class="form-group">
                                <label class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-10">
                                    <input type="password " v-model="user.password" class="form-control">
                                </div>
                            </div>
                        </form>
                        <!--弹出框内容--END-->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">登录</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--模态框--END-->
    </div>
</template>

<script>

    export default {
        name: 'admin',
        data: function () {
            return {
                loginUser: {},
                user: {}
            }

        },
        mounted: function () {
            let _this = this;
            $('body').remove('login-layout light-login');
            $('body').attr('class', 'no-skin');
            // 初始化时重新加载一遍ace的js，否则从登陆界面跳转到admin页面时会出现侧边栏无法点击的情况
            $.getScript('/ace/assets/js/ace.min.js');
            //获取登录的用户信息
            _this.loginUser = Tool.getLoginUser()

            //路由权限
            if (!_this.hasResourceRouter(_this.$route.name)) {
                _this.$router.push("/login");
            }
        },

        methods: {

            /**
             * 查找是否有权限
             * @param router
             */
            hasResourceRouter(router) {
                let _this = this;
                let resources = Tool.getLoginUser().resources;
                if (Tool.isEmpty(resources)) {
                    return false;
                }
                for (let i = 0; i < resources.length; i++) {
                    if (router === resources[i].page) {
                        return true;
                    }
                }
                return false;
            },

            /**
             * 查找是否有对应的资源权限
             * @param id
             * @returns {*|boolean}
             */
            hasResources(id) {
                return Tool.hasResource(id);
            },

            login() {
                this.$router.push("/admin")
            },

            loginout() {
                let _this = this;

                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/user/logout/' + _this.loginUser.token).then((response) => {
                    console.log("退出登录:", response);
                    let resp = response.data;
                    if (resp.success) {
                        Tool.setLoginUser(null);
                        this.$router.push("/login")
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            //切换用户
            changeUser(user) {
                let _this = this;
                _this.user = $.extend({}, user); //将传递过来一行数据user赋值给_this.user
                $("#change-user-modal").modal("show"); //让模态框显示出来
            }
        },


    }

</script>


