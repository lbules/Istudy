// 删除确认提示框
Confirm = {
  show: function (message, callback) {
    Swal.fire({
      title: '确认删除？', //标题
      text: message, //显示内容
      confirmButtonColor: '#3085d6',// 确定按钮的 颜色
      confirmButtonText: '确定',// 确定按钮的 文字
      showCancelButton: true, // 是否显示取消按钮
      cancelButtonColor: '#d33', // 取消按钮的 颜色
      cancelButtonText: '取消', // 取消按钮的 文字
      focusCancel: true, // 是否聚焦 取消按钮
      reverseButtons: true  // 是否 反转 两个按钮的位置 默认是  左边 确定  右边 取消
    }).then((result) => {
      if (result.value) {
        if (callback) {  //调用外部方法
          callback()
        }
      }
    })
  }
}