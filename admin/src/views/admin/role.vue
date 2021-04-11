<template>

    <div>
        <!--新增按钮-->
        <p>
            <button v-on:click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit"></i>
                新增
            </button>
        </p>
        <!--新增按钮--END-->

        <!--详细列表-->
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>角色</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="role in roles">
                <td>{{role.id}}</td>
                <td>{{role.name}}</td>
                <td>{{role.desc}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <!--编辑-->
                        <button v-on:click="editResource(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-list bigger-120"></i>
                        </button>

                        <!--编辑-->
                        <button v-on:click="edit(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <!--删除,通过id来删除-->
                        <button v-on:click="del(role.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <!--详细列表-END-->
        <!--分页组件-->
        <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
        <!--分页组件--END-->

        <!--模态框-->
        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">

                        <!--弹出框内容-->
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">角色</label>
                                <div class="col-sm-10">
                                    <input v-model="role.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">描述</label>
                                <div class="col-sm-10">
                                    <input v-model="role.desc" class="form-control">
                                </div>
                            </div>
                        </form>
                        <!--弹出框内容--END-->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--模态框--END-->

        <!--角色资源关联配置-->
        <!-- 角色资源关联配置 -->
        <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">角色资源关联配置</h4>
                    </div>
                    <div class="modal-body">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            关闭
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveResource()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- 角色用户关联配置 -->
        <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">角色用户关联配置</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <table id="user-table" class="table table-hover">
                                    <tbody>
                                    <tr v-for="user in users">
                                        <td>{{user.loginName}}</td>
                                        <td class="text-right">
                                            <a v-on:click="addUser(user)" href="javascript:;" class="">
                                                <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-6">
                                <table id="role-user-table" class="table table-hover">
                                    <tbody>
                                    <tr v-for="user in roleUsers">
                                        <td>{{user.loginName}}</td>
                                        <td class="text-right">
                                            <a v-on:click="deleteUser(user)" href="javascript:;" class="">
                                                <i class="ace-icon fa fa-trash blue"></i>
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            关闭
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveUser()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div>
</template>

<script>
    import Pagination from "../../components/pagination.vue"

    export default {
        components: {Pagination},
        name: 'role',
        data: function () {
            return {
                role: {}, //映射表单数据
                roles: [],
                resourcess:[],
                zTree:{},
                users: [],
                roleUsers: []
            }
        },
        mounted: function () {
            let _this = this;
            _this.list(1);
        },
        methods: {
            //添加
            add() {
                let _this = this;
                _this.role = {}; //清空上一次输入的内容
                $("#form-modal").modal("show"); //让模态框显示出来
            },

            //编辑
            edit(role) {
                let _this = this;
                _this.role = $.extend({}, role); //将传递过来一行数据role赋值给_this.role
                $("#form-modal").modal("show"); //让模态框显示出来

            },

            //查询
            list(page) {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/list', {
                    page: page, //第几页
                    size: _this.$refs.pagination.size, //每页查询多少条记录
                }).then((response) => {
                    Loading.hide();
                    console.log("查询结果:", response);
                    let resp = response.data;//response.data就相当于responseDto
                    _this.roles = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },

            //保存
            save() {
                let _this = this;

                // 保存时进行校验，检查是输入是否为空，是否符合长度
                // 保存校验
                if (1 != 1
                    || !Validator.length(_this.role.name, "角色", 1, 50)
                    || !Validator.length(_this.role.desc, "描述", 1, 100)
                ) {
                    return;
                }


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save', _this.role).then((response) => {
                    console.log("保存结果:", response);
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) { //保存成功就将弹出框隐藏
                        $("#form-modal").modal("hide");

                        _this.list(1); //重新刷新
                        Toast.success("保存成功");
                    } else {
                        Toast.warning(resp.message);
                    }
                })
            },
            //删除,传入参数id
            del(id) {
                let _this = this;
                // 确认弹出框
                Confirm.show("删除后将无法恢复，请谨慎操作！", function () {
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/role/delete/' + id).then((response) => {
                        console.log("删除结果:", response);
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1); //重新刷新
                            Toast.success("删除成功！");
                        }
                    })
                });
            },

            /**
             * 点击角色资源配置
             */
            editResource(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.loadResource();
                $("#resource-modal").modal("show");
            },

            /**
             * 加载资源树
             */
            loadResource() {
                let _this = this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/resources/load-tree').then((res)=>{
                    Loading.hide();
                    let response = res.data;
                    _this.resourcess = response.content;
                    // 初始化树
                    _this.initTree();
                    _this.listRoleResource();
                })
            },

            /**
             * 初始资源树
             */
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                            enable: true
                        }
                    }
                };

                _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resourcess);
                _this.zTree.expandAll(true);
            },


            /**
             * 资源模态框点击【保存】
             */
            saveResource() {
                let _this = this;
                let resources = _this.zTree.getCheckedNodes();
                console.log("勾选的资源：", resources);

                // 保存时，只需要保存资源id，所以使用id数组进行参数传递
                let resourceIds = [];
                for (let i = 0; i < resources.length; i++) {
                    resourceIds.push(resources[i].id);
                }

                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save-resources', {
                    id: _this.role.id,
                    resourceIds: resourceIds
                }).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        Toast.success("保存成功!");
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },


            /**
             * 加载角色资源关联记录
             */
            listRoleResource() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/role/list-resources/' + _this.role.id).then((response)=>{
                    let resp = response.data;
                    let resources = resp.content;

                    // 勾选查询到的资源：先把树的所有节点清空勾选，再勾选查询到的节点
                    _this.zTree.checkAllNodes(false);
                    for (let i = 0; i < resources.length; i++) {
                        let node = _this.zTree.getNodeByParam("id", resources[i]);
                        _this.zTree.checkNode(node, true);
                    }
                });
            },

        }
    }
</script>