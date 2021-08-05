# Istudy

基于Spring Boot和Vue.js的在线视频课程网站

##### 后台管理界面

![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/45c1efca702d438f8282fa979c86e30a~tplv-k3u1fbpfcp-watermark.image)

##### 用户前台界面

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1ebfc385ecff4e8fa473f7fd091eb8b7~tplv-k3u1fbpfcp-watermark.image)

## 特性

- 前后端分离
- 数据可视化
- 阿里云OSS

## 技术栈

- springBoot
- Vue.js
- Bootstra
- Echarts.js
- Redis
- Mysql
- 阿里云OSS对象存储

## 安装

需要提前安装好Redis和配置好[阿里云OSS对象存储](https://www.aliyun.com/product/oss)


1.克隆本地仓库 

`git clone git@github.com:lbules/Istudy.git`

2.进入相关目录安装依赖

```
   # 后台管理系统
   cd admin
   npm install
   npm run serve-dev
   
   # 前台用户系统
   cd web
   npm install
   npm run serve-dev
```

##### 阿里云oss云存储配置文件

**file**文件上传模块，file/src/main/resources/application.properties
*将下图的配置项换成自己的，具体参考官方文档*

![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ce6146ea4175491085c6918e1a7d2bd4~tplv-k3u1fbpfcp-watermark.image)


## 部分演示截图

#### 1.课程详情页面

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/309ebe06814d493f90cb82856c9dcffb~tplv-k3u1fbpfcp-watermark.image)

![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f5d3b1e03a724fbf91e1728012c9948a~tplv-k3u1fbpfcp-watermark.image)

#### 2.后台管理登录页面

![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bab8823ac7a24835863f54d7b2800029~tplv-k3u1fbpfcp-watermark.image)

#### 3.课程管理和发布

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4c7ecc41e85e430c8d00581fb6943ed1~tplv-k3u1fbpfcp-watermark.image)

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fecf3e0fc7ea45f78b21ac49dfe82147~tplv-k3u1fbpfcp-watermark.image)

#### 4.课程数据统计页面

![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fe0ba1b2fc5849f6bec522b9cdc606e6~tplv-k3u1fbpfcp-watermark.image)