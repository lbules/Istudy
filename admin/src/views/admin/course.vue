<template>

    <div>
        <!--新增章节按钮-->
        <p>
            <button v-on:click="add()" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit"></i>
                新增
            </button>
        </p>
        <!--新增章节按钮--END-->

        <!--详细列表-->
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                                    <th>id</th>

                      <th>名称</th>

                      <th>概述</th>

                      <th>时长</th>

                      <th>价格</th>

                      <th>封面</th>

                      <th>级别</th>

                      <th>类型</th>

                      <th>状态</th>

                      <th>报名数</th>

                      <th>排序</th>



              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="course in courses">
                <td>{{course.id}}</td>
                <td>{{course.name}}</td>
                <td>{{course.summary}}</td>
                <td>{{course.time}}</td>
                <td>{{course.price}}</td>
                <td>{{course.image}}</td>
                <td>{{COURSE_LEVEL| optionKV(course.level)}}</td>
                <td>{{COURSE_CHARGE| optionKV(course.charge)}}</td>
                <td>{{COURSE_STATUS| optionKV(course.status)}}</td>
                <td>{{course.enroll}}</td>
                <td>{{course.sort}}</td>


                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <!--编辑-->
                        <button v-on:click="edit(course)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <!--删除,通过id来删除-->
                        <button v-on:click="del(course.id)" class="btn btn-xs btn-danger">
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
                                      <span class="blue"><i class="ace-icon fa fa-search-plus bigger-120"></i></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                      <span class="green"><i class="ace-icon fa fa-pencil-square-o bigger-120"></i></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                      <span class="red"><i class="ace-icon fa fa-trash-o bigger-120"></i></span>
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
                              <label class="col-sm-2 control-label">名称</label>
                              <div class="col-sm-10">
                                <input v-model="course.name" class="form-control">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">概述</label>
                              <div class="col-sm-10">
                                <input v-model="course.summary" class="form-control">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">时长</label>
                              <div class="col-sm-10">
                                <input v-model="course.time" class="form-control">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">价格</label>
                              <div class="col-sm-10">
                                <input v-model="course.price" class="form-control">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">封面</label>
                              <div class="col-sm-10">
                                <input v-model="course.image" class="form-control">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">级别</label>
                              <div class="col-sm-10">
                                  <select v-model="course.level" class="form-control">
                                      <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                                  </select>
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">类型</label>
                              <div class="col-sm-10">
                                  <select v-model="course.charge" class="form-control">
                                      <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                  </select>
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">状态</label>
                              <div class="col-sm-10">
                                  <select v-model="course.status" class="form-control">
                                      <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                                  </select>
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">报名数</label>
                              <div class="col-sm-10">
                                <input v-model="course.enroll" class="form-control">
                              </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-2 control-label">排序</label>
                              <div class="col-sm-10">
                                <input v-model="course.sort" class="form-control">
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
        name: 'course',
        data: function () {
            return {
                course: {}, //映射表单数据
                courses: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
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
                _this.course = {}; //清空上一次输入的内容
                $("#form-modal").modal("show"); //让模态框显示出来
            },

            //编辑
            edit(course) {
                let _this = this;
                _this.course = $.extend({}, course); //将传递过来一行数据course赋值给_this.course
                $("#form-modal").modal("show"); //让模态框显示出来

            },

            //查询课程表节记录
            list(page) {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/course/list', {
                    page: page, //第几页
                    size: _this.$refs.pagination.size, //每页查询多少条记录
                }).then((response) => {
                    Loading.hide();
                    console.log("查询课程表列表结果:", response);
                    let resp = response.data;//response.data就相当于responseDto
                    _this.courses = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },

            //保存课程表
            save() {
                let _this = this;

                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.course.name, "名称")
                    || !Validator.length(_this.course.name, "名称", 1, 50)
                    || !Validator.length(_this.course.summary, "概述", 1, 2000)
                    || !Validator.length(_this.course.image, "封面", 1, 100)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/course/save', _this.course).then((response) => {
                    console.log("保存课程表列表结果:", response);
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/course/delete/'+id).then((response) => {
                        console.log("删除课程表列表结果:", response);
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