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
        <div class="row">
            <div v-for="teacher in teachers" class="col-md-3">
                <div>
          <span class="profile-picture">
            <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/static/image/讲师头像/头像1.jpg" v-bind:title="teacher.intro"/>
            <img v-show="teacher.image" class="editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
          </span>

                    <div class="space-4"></div>

                    <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                        <div class="inline position-relative">
                            <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                                <i class="ace-icon fa fa-circle light-green"></i>
                                &nbsp;
                                <span class="white">{{teacher.position}}</span>
                            </a>
                        </div>
                    </div>
                </div>

                <div class="space-6"></div>

                <div class="text-center">
                    <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
                        <i class="ace-icon fa fa-user"></i>
                        {{teacher.name}}【{{teacher.nickname}}】
                    </a>
                </div>

                <div class="space-6"></div>

                <div class="profile-social-links align-center">
                    <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                        <i class="ace-icon fa fa-pencil bigger-120"></i>
                    </button>
                    &nbsp;
                    <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </button>
                </div>

                <div class="hr hr16 dotted"></div>

            </div>
        </div>
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
                    <h4 class="modal-title">新增讲师</h4>
                </div>
                <div class="modal-body">

                    <!--弹出框内容-->
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input v-model="teacher.name" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">昵称</label>
                            <div class="col-sm-10">
                                <input v-model="teacher.nickname" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">头像</label>
                            <div class="col-sm-10">
                                <big-file
                                        v-bind:suffixs="['jpg', 'jpeg', 'png']"
                                        v-bind:use="FILE_USE.TEACHER.key"
                                        v-bind:input-id="'image-upload'"
                                        v-bind:text="'上传头像'"
                                        v-bind:after-upload="afterUpload"></big-file>
                                <div v-show="teacher.image" class="row">
                                    <div class="col-md-4">
                                        <img v-bind:src="teacher.image" class="img-responsive">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">职位</label>
                            <div class="col-sm-10">
                                <input v-model="teacher.position" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">座右铭</label>
                            <div class="col-sm-10">
                                <input v-model="teacher.motto" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">简介</label>
                            <div class="col-sm-10">
                                <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>
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
    import File from "../../components/file";
    import BigFile from "../../components/big-file";

    export default {
        components: {BigFile, File, Pagination},
        name: 'teacher',
        data: function () {
            return {
                teacher: {}, //映射表单数据
                teachers: [],
                FILE_USE: FILE_USE
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
                _this.teacher = {}; //清空上一次输入的内容
                $("#form-modal").modal("show"); //让模态框显示出来
            },

            //编辑
            edit(teacher) {
                let _this = this;
                _this.teacher = $.extend({}, teacher); //将传递过来一行数据teacher赋值给_this.teacher
                $("#form-modal").modal("show"); //让模态框显示出来

            },

            //查询
            list(page) {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/list', {
                    page: page, //第几页
                    size: _this.$refs.pagination.size, //每页查询多少条记录
                }).then((response) => {
                    Loading.hide();
                    console.log("查询结果:", response);
                    let resp = response.data;//response.data就相当于responseDto
                    _this.teachers = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },

            //保存
            save() {
                let _this = this;

                // 保存时进行校验，检查是输入是否为空，是否符合长度
                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.teacher.name, "")
                    || !Validator.length(_this.teacher.name, "", 1, 50)
                    || !Validator.length(_this.teacher.nickname, "", 1, 50)
                    || !Validator.length(_this.teacher.image, "", 1, 100)
                    || !Validator.length(_this.teacher.position, "", 1, 50)
                    || !Validator.length(_this.teacher.motto, "", 1, 50)
                    || !Validator.length(_this.teacher.intro, "", 1, 500)
                ) {
                    return;
                }


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/save', _this.teacher).then((response) => {
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/teacher/delete/' + id).then((response) => {
                        console.log("删除结果:", response);
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1); //重新刷新
                            Toast.success("删除成功！");
                        }
                    })
                });
            },

            //回调函数
            afterUpload(resp) {
                let _this = this;
                let image = resp.content.path;
                _this.teacher.image = image;
            }
        }
    }
</script>