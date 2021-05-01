<template>

    <div>

        <div class="row">
            <div class="col-md-6">
                <!--新增按钮-->
                <p>
                    <button v-on:click="add1()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit"></i>
                        增加一级分类
                    </button>

                    <button v-on:click="all()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit"></i>
                        刷新
                    </button>
                </p>
                <!--新增按钮--END-->

                <!--左边详细列表-->
                <table id="level1-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="category in level1" v-on:click="clickLevel1(category)" v-bind:class="{'active': category.id===active.id}">
                        <td>{{category.id}}</td>
                        <td>{{category.name}}</td>
                        <td>{{category.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <!--编辑-->
                                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                <!--删除,通过id来删除-->
                                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                            <!--隐藏按钮-->
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                                            data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                        <span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i></span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <!--详细列表-END-->
            </div>

            <div class="col-md-6">
                <p>
                    <button v-on:click="add2()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit"></i>
                        增加二级分类
                    </button>

                </p>
                <table id="level2-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id
                        </th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="category in level2">
                        <td>{{category.id}}</td>
                        <td>{{category.name}}</td>
                        <td>{{category.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <!--编辑-->
                                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                <!--删除,通过id来删除-->
                                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                            <!--隐藏按钮-->
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                                            data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                        <span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i></span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <!--模态框-->
        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增分类</h4>
                    </div>
                    <div class="modal-body">

                        <!--弹出框内容-->
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">上一级分类</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static">{{active.name||"无上一级分类"}}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="category.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">顺序</label>
                                <div class="col-sm-10">
                                    <input v-model="category.sort" class="form-control">
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
    </div>
</template>

<script>

    export default {
        name: 'category',
        data: function () {
            return {
                category: {}, //映射表单数据
                categorys: [],
                active:{},
                level1: [], //一级分类
                level2: [], //二级分类

            }
        },
        mounted: function () {
            let _this = this;
            _this.all();
        },
        methods: {
            /**
             * 点击【新增一级】
             */
            add1() {
                let _this = this;
                _this.active = {};
                _this.level2 = [];
                _this.category = {
                    parent: "00000000"
                };
                $("#form-modal").modal("show");
            },

            /**
             * 点击【新增二级】
             */
            add2() {
                let _this = this;
                if (Tool.isEmpty(_this.active)) {
                    Toast.warning("请先点击一级分类");
                    return;
                }
                _this.category = {
                    parent: _this.active.id
                };
                $("#form-modal").modal("show");
            },
            //编辑
            edit(category) {
                let _this = this;
                _this.category = $.extend({}, category); //将传递过来一行数据category赋值给_this.category
                $("#form-modal").modal("show"); //让模态框显示出来
            },

            //查询
            all() {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all', {}).then((response) => {
                    Loading.hide();
                    let resp = response.data;//response.data就相当于responseDto
                    _this.categorys = resp.content;
                    //将查询到的记录转换成树形结构
                    _this.level1 = [];
                    for (let i = 0; i < _this.categorys.length; i++) {
                        let c = _this.categorys[i];
                        if (c.parent === '00000000') { //00000000代表一级分类
                            _this.level1.push(c);
                            for (let j = 0; j < _this.categorys.length; j++) {
                                let child = _this.categorys[j];
                                if (child.parent === c.id) {
                                    if (Tool.isEmpty(c.children)) {
                                        c.children = [];  //其二级分类为空
                                    }
                                    c.children.push(child);
                                }
                            }
                        }
                    }

                    _this.level2 = [];
                    // 对当前一级分类中选中的表格触发一次点击事件，以刷新二级菜单列表
                    // 注意：界面的渲染需要等vue绑定好变量后才做，所以加延时100ms
                    setTimeout(function () {
                        $("tr.active").trigger("click");
                    }, 100);
                })
            },

            //保存
            save() {
                let _this = this;

                // 保存时进行校验，检查是输入是否为空，是否符合长度
                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.category.parent, "父id")
                    || !Validator.require(_this.category.name, "名称")
                    || !Validator.length(_this.category.name, "名称", 1, 50)
                ) {
                    return;
                }


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/save', _this.category).then((response) => {
                    console.log("保存结果:", response);
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) { //保存成功就将弹出框隐藏
                        $("#form-modal").modal("hide");

                        _this.all(); //重新刷新
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/category/delete/' + id).then((response) => {
                        console.log("删除结果:", response);
                        let resp = response.data;
                        if (resp.success) {
                            _this.all(); //重新刷新
                            Toast.success("删除成功！");
                        }
                    })
                });
            },

            clickLevel1(category) {
                let _this = this;
                _this.active = category;
                 _this.level2 = category.children; //children保存二级分类的数组
            }
        }
    }
</script>

<style scoped>
    .active td {
        background-color: #19aee9 !important;
    }
</style>