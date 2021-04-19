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
                        <th>课程id</th>
                        <th>会员id</th>
                        <th>会员昵称</th>
                        <th>评论内容</th>
                        <th>评论时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="courseComment in courseComments">
                            <td>{{courseComment.id}}</td>
                            <td>{{courseComment.courseId}}</td>
                            <td>{{courseComment.memberId}}</td>
                            <td>{{courseComment.memberName}}</td>
                            <td>{{courseComment.comment}}</td>
                            <td>{{courseComment.createTime}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <!--编辑-->
                        <button v-on:click="edit(courseComment)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <!--删除,通过id来删除-->
                        <button v-on:click="del(courseComment.id)" class="btn btn-xs btn-danger">
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
                                            <label class="col-sm-2 control-label">课程id</label>
                                            <div class="col-sm-10">
                                                <input v-model="courseComment.courseId" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">会员id</label>
                                            <div class="col-sm-10">
                                                <input v-model="courseComment.memberId" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">会员昵称</label>
                                            <div class="col-sm-10">
                                                <input v-model="courseComment.memberName" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">评论内容</label>
                                            <div class="col-sm-10">
                                                <input v-model="courseComment.comment" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">评论时间</label>
                                            <div class="col-sm-10">
                                                <input v-model="courseComment.createTime" class="form-control">
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
    import Pagination from "../../components/pagination.vue"

    export default {
        components: {Pagination},
        name: 'courseComment',
        data: function () {
            return {
                courseComment: {}, //映射表单数据
                courseComments: [],
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
                _this.courseComment = {}; //清空上一次输入的内容
                $("#form-modal").modal("show"); //让模态框显示出来
            },

            //编辑
            edit(courseComment) {
                let _this = this;
                _this.courseComment = $.extend({}, courseComment); //将传递过来一行数据courseComment赋值给_this.courseComment
                $("#form-modal").modal("show"); //让模态框显示出来

            },

            //查询
            list(page) {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/courseComment/list', {
                    page: page, //第几页
                    size: _this.$refs.pagination.size, //每页查询多少条记录
                }).then((response) => {
                    Loading.hide();
                    console.log("查询结果:", response);
                    let resp = response.data;//response.data就相当于responseDto
                    _this.courseComments = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },

            //保存
            save() {
                let _this = this;

                // 保存时进行校验，检查是输入是否为空，是否符合长度
                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.courseComment.courseId, "课程id")
                    || !Validator.require(_this.courseComment.memberId, "会员id")
                    || !Validator.require(_this.courseComment.memberName, "会员昵称")
                    || !Validator.length(_this.courseComment.memberName, "会员昵称", 1, 50)
                    || !Validator.length(_this.courseComment.comment, "评论内容", 1, 255)
                ) {
                    return;
                }


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/courseComment/save', _this.courseComment).then((response) => {
                    console.log("保存结果:", response);
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) { //保存成功就将弹出框隐藏
                        $("#form-modal").modal("hide");

                        _this.list(1); //重新刷新
                        Toast.success("保存成功");
                    }
                    else {
                        Toast.warning(resp.message);
                    }
                })
            },
            //删除,传入参数id
            del(id) {
                let _this = this;
                // 确认弹出框
                Confirm.show("删除后将无法恢复，请谨慎操作！",function () {
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/courseComment/delete/'+id).then((response) => {
                        console.log("删除结果:", response);
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1); //重新刷新
                            Toast.success("删除成功！");
                        }
                    })
                });
            }
        }
    }
</script>