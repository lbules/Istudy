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

        <div class="col-md-6">
            <ul id="tree" class="ztree"></ul>
        </div>

        <!--模态框-->
        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">添加资源配置</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <textarea id="resources-textarea" class="form-control" v-model="resourceStr" name="resource" rows="5"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">添加</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--模态框--END-->

    </div>
</template>

<script>

    export default {
        name: 'resources',
        data: function () {
            return {
                resources: {}, //映射表单数据
                resourcess: [],
                tree: {},
                resourceStr:"",
            }
        },
        mounted: function () {
            let _this = this;
            _this.list();
        },
        methods: {
            //添加
            add() {
                let _this = this;
                _this.resources = {}; //清空上一次输入的内容
                $("#form-modal").modal("show"); //让模态框显示出来
            },



            //查询
            list() {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/resources/load-tree', {
                }).then((response) => {
                    Loading.hide();
                    console.log("查询结果:", response);
                    let resp = response.data;
                    _this.resourcess = resp.content;
                    _this.initTree();
                })
            },

            //保存
            save() {
                let _this = this;

                // 保存校验
                if (Tool.isEmpty(_this.resourceStr)) {
                    Toast.warning("输入不能为空！");
                    return;
                }
                let json = JSON.parse(_this.resourceStr);

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resources/save', json).then((response) => {
                    console.log("保存结果:", response);
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) { //保存成功就将弹出框隐藏
                        $("#form-modal").modal("hide");

                        _this.list(); //重新刷新
                        Toast.success("添加成功");
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/resources/delete/' + id).then((response) => {
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
             * 初始资源树
             */
            initTree() {
                let _this = this;
                let setting = {
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
        }
    }
</script>