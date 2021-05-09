<template>
<div>
    <!--详细列表-->
    <table id="simple-table" class="table  table-bordered table-hover">
        <thead>
        <tr>
            <th>课程名</th>
            <th>评论内容</th>
            <th>评论时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="courseComment in courseComments">
            <td>{{courseComment.courseName}}</td>
            <td>{{courseComment.comment}}</td>
            <td>{{courseComment.createTime.slice(0,10)}}</td>
            <td>
                <div class="hidden-sm hidden-xs btn-group">
                    <!--删除,通过id来删除-->
                    <button v-on:click="del(courseComment.id)" class="btn btn-xs btn-danger">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </button>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <!--详细列表-END-->
</div>
</template>


<script>
    export default {
        name: 'the-comment',
        props: {
            courseComments: {}
        },
        data: function () {
            return {

            }
        },
        methods:{
            //删除评论,传入参数id
            del(id) {
                let _this = this;
                // 确认弹出框
                Confirm.show("删除后将无法恢复，请谨慎操作！",function () {
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/web/courseComment/delete/'+id).then((response) => {
                        console.log("删除结果:", response);
                        let resp = response.data;
                        if (resp.success) {
                            // _this.list(1); //重新刷新
                            Toast.success("删除成功！");
                        }
                    })
                });
            }
        }
    }

</script>