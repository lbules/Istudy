<template>

    <div>

        <!--详细列表-->
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>后缀</th>
                <th>相对路径</th>
                <th>文件名</th>
                <th>大小</th>
                <th>用途</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="file in files">
                <td>{{file.id}}</td>
                <td>{{file.suffix}}</td>
                <td>{{file.path}}</td>
                <td>{{file.name}}</td>
                <td>{{file.size}}</td>
                <td>{{file.use}}</td>
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
        name: 'file',
        data: function () {
            return {
                file: {}, //映射表单数据
                files: [],
            }
        },
        mounted: function () {
            let _this = this;
            _this.list(1);
        },
        methods: {

            //查询
            list(page) {
                let _this = this;
                Loading.show(); //缓冲提示
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/list', {
                    page: page, //第几页
                    size: _this.$refs.pagination.size, //每页查询多少条记录
                }).then((response) => {
                    Loading.hide();
                    console.log("查询结果:", response);
                    let resp = response.data;//response.data就相当于responseDto
                    _this.files = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total)
                })
            },
        }
    }
</script>