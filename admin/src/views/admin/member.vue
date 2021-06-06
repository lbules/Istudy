<template>

    <div>
        <!--详细列表-->
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                        <!--<th>id</th>-->
                        <th>手机号</th>
                        <th>昵称</th>
                        <th>注册时间</th>
                        <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="member in members">
                            <!--<td>{{member.id}}</td>-->
                            <td>{{member.mobile}}</td>
                            <td>{{member.name}}</td>
                            <td>{{member.registerTime.slice(0,10)}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">

                        <!--删除,通过id来删除-->
                        <button v-on:click="del(member.id)" class="btn btn-xs btn-danger">
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



    </div>
</template>

<script>
    import Pagination from "../../components/pagination.vue"

    export default {
        components: {Pagination},
        name: 'member',
        data: function () {
            return {
                member: {}, //映射表单数据
                members: [],
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
                _this.member = {}; //清空上一次输入的内容
                $("#form-modal").modal("show"); //让模态框显示出来
            },

            //编辑
            edit(member) {
                let _this = this;
                _this.member = $.extend({}, member); //将传递过来一行数据member赋值给_this.member
                $("#form-modal").modal("show"); //让模态框显示出来

            },

            //查询
            list(page) {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/member/list', {
                    page: page, //第几页
                    size: _this.$refs.pagination.size, //每页查询多少条记录
                }).then((response) => {
                    Loading.hide();
                    console.log("查询结果:", response);
                    let resp = response.data;//response.data就相当于responseDto
                    _this.members = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },

            //保存
            save() {
                let _this = this;

                // 保存时进行校验，检查是输入是否为空，是否符合长度
                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.member.mobile, "手机号")
                    || !Validator.length(_this.member.mobile, "手机号", 1, 11)
                    || !Validator.length(_this.member.password, "密码", 1, 32)
                    || !Validator.length(_this.member.name, "昵称", 1, 50)
                    || !Validator.length(_this.member.photo, "头像", 1, 200)
                ) {
                    return;
                }


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/member/save', _this.member).then((response) => {
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/member/delete/'+id).then((response) => {
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