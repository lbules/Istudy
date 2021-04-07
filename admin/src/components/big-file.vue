<!--文件上传公共组件-->

<template>
    <div>
        <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-upload"></i>
            {{text}}
        </button>
        <input class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
    </div>
</template>

<script>
    export default {
        name: 'big-file',
        props: {
            text: {
                default: "上传文件"
            },
            inputId: {
                default: "file-upload"
            },
            afterUpload: {
                type: Function,
                default:null
            },
            suffixs: {
                default: []
            },
            use: {
              default: ""
            },

        },
        data: function () {
            return {

            }
        },

        methods: {
            uploadFile() {
                let _this = this;
                let formData = new window.FormData();
                let file = _this.$refs.file.files[0];

                //生成文件标识
                let key = hex_md5(file);
                let key10 = parseInt(key, 16);
                let key62 = Tool._10to62(key10);
                console.log(key, key10,key62);


                // 判断文件格式
                let suffixs =_this.suffixs;
                let fileName = file.name;
                let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
                let validateSuffix = false;
                for (let i = 0; i < suffixs.length; i++) {
                    if (suffixs[i].toLowerCase() === suffix) {
                        validateSuffix = true;
                        break;
                    }
                }
                if (!validateSuffix) {
                    Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));
                    $("#" + _this.inputId + "-input").val("");
                    return;
                }

                //文件分片
                let shardSize = 20*1024*1024; //每个分片大小为20mb
                let shardIndex = 0; // 分片索引
                let start = shardIndex ; //当前分片的索引位置
                let end = Math.min(file.size,start+shardSize); // 当前分片的结束位置
                let fileShard  = file.slice(start,end); // 从文件中截取当前分片数据
                let size = file.size;
                let shardToal = Math.ceil(size/shardSize);

                formData.append('shard',fileShard);
                formData.append('shardIndex',shardIndex);
                formData.append('shardSize',shardSize);
                formData.append('shardTotal',shardToal);
                formData.append('name',file.name);
                formData.append('suffix',suffix);
                formData.append('size',size);
                formData.append('file',fileShard);
                formData.append('use',_this.use);
                formData.append('key',key62);


                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/file/admin/upload',formData).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    let image = resp.content; //取出照片

                    _this.afterUpload(resp);
                    $("#"+_this.inputId + "-input").val("");

                });
            },

            selectFile() {
                let _this = this;
                $("#"+_this.inputId + "-input").trigger("click");
            },
        }
    }
</script>