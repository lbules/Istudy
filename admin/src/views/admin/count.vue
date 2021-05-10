<template>
    <div>
        <div class="space-12"></div>
        <div class="row">
            <div class="col-xs-12">
                <div class="text-center">
          <span class="btn btn-app btn-sm btn-light no-hover">
              <span class="line-height-1 bigger-170 blue">{{count.allCourse}}</span>
              <br>
              <span class="line-height-1 smaller-90"> 全部课程 </span>
          </span>
                    <span class="btn btn-app btn-sm btn-yellow no-hover">
              <span class="line-height-1 bigger-170"> {{count.allChapter}} </span>

              <br>
              <span class="line-height-1 smaller-90"> 大章 </span>
          </span>

                    <span class="btn btn-app btn-sm btn-pink no-hover">
              <span class="line-height-1 bigger-170"> {{count.allSection}} </span>

              <br>
              <span class="line-height-1 smaller-90"> 小节 </span>
          </span>

                    <span class="btn btn-app btn-sm btn-success no-hover">
              <span class="line-height-1 bigger-170"> {{count.allMember}} </span>

              <br>
              <span class="line-height-1 smaller-90"> 会员总数 </span>
          </span>

                    <span class="btn btn-app btn-sm btn-primary no-hover">
              <span class="line-height-1 bigger-170"> {{count.allEnroll}} </span>

              <br>
              <span class="line-height-1 smaller-90"> 报名人数 </span>
          </span>

                    <span class="btn btn-app btn-sm btn-grey no-hover">
              <span class="line-height-1 bigger-170"> {{count.allComment}}</span>

              <br>
              <span class="line-height-1 smaller-90"> 评论总数 </span>
          </span>

                </div>

                <div class="space-12"></div>

                <div class="hr hr2 hr-double"></div>

                <div class="space-12"></div>

            </div>

            <div class="col-sm-12 infobox-container">
                <div class="infobox infobox-green">
                    <div class="infobox-icon">
                        <i class="ace-icon fa fa-comments"></i>
                    </div>

                    <div class="infobox-data">
                        <span class="infobox-data-number">3</span>
                        <div class="infobox-content">新增评论</div>
                    </div>

                    <!--<div class="stat stat-success">8%</div>-->
                </div>

                <div class="infobox infobox-blue">
                    <div class="infobox-icon">
                        <i class="ace-icon fa fa-twitter"></i>
                    </div>

                    <div class="infobox-data">
                        <span class="infobox-data-number">5</span>
                        <div class="infobox-content">新增会员</div>
                    </div>

                </div>

                <div class="infobox infobox-pink">
                    <div class="infobox-icon">
                        <i class="ace-icon fa fa-shopping-cart"></i>
                    </div>

                    <div class="infobox-data">
                        <span class="infobox-data-number">8</span>
                        <div class="infobox-content">新增报名</div>
                    </div>
                </div>

                <div class="space-12"></div>

                <div class="hr hr2 hr-double"></div>

                <div class="space-12"></div>

            </div>

            <!--新会员，课程报名人数和评论-->
            <div class="col-sm-12">
                <div class="widget-box transparent">
                    <div class="widget-header widget-header-flat">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-signal"></i>
                                注册会员
                        </h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main padding-4">
                            <div id="sales-charts"></div>
                        </div><!-- /.widget-main -->
                    </div><!-- /.widget-body -->
                </div><!-- /.widget-box -->

                <div class="space-12"></div>

                <div class="hr hr2 hr-double"></div>

                <div class="space-12"></div>
            </div>

            <!--课程销售排行-->
            <div class="col-sm-6">
                <div class="widget-box transparent">
                    <div class="widget-header widget-header-flat">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-star orange"></i>
                            课程报名人数排行
                        </h4>

                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <table class="table table-bordered table-striped">
                                <thead class="thin-border-bottom">
                                <tr>
                                    <th>
                                        <i class="ace-icon fa fa-caret-right blue"></i>课程名称
                                    </th>

                                    <th>
                                        <i class="ace-icon fa fa-caret-right blue"></i>课程价格
                                    </th>

                                    <th>
                                        <i class="ace-icon fa fa-caret-right blue"></i>报名人数
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr v-for="course in mostEnroll">
                                    <td>{{course.name}}</td>

                                    <td>
                                        <b class="green">￥{{course.price}}</b>
                                    </td>

                                    <td>
                                        <span class="label label-warning arrowed arrowed-right">{{course.enroll}}</span>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </div><!-- /.widget-main -->
                    </div><!-- /.widget-body -->
                </div><!-- /.widget-box -->
            </div><!-- /.col -->
            <!--课程销售排行-END-->

            <!--<div class="col-sm-6">
                <div class="widget-box transparent">
                    <div class="widget-header widget-header-flat">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-star orange"></i>
                            分类销售排名
                        </h4>

                    </div>

                    <div class="widget-body">
                        <div class="widget-main">
                            <div id="piechart-placeholder"></div>

                        </div>&lt;!&ndash; /.widget-main &ndash;&gt;
                    </div>&lt;!&ndash; /.widget-body &ndash;&gt;
                </div>&lt;!&ndash; /.widget-box &ndash;&gt;
            </div>&lt;!&ndash; /.col &ndash;&gt;-->
        </div><!-- /.row -->
    </div>
</template>

<script>
    export default {
        name: "welcome",
        mounted: function () {
            let _this = this;
            _this.getAllCount();
            _this.getMostEnroll();

        },
        data: function () {
            return {
                count:{}, //汇总数据
                mostEnroll:[], //报名人数最多的五门课
            }
        },
        methods: {

            //获取课程相关的全部信息
            getAllCount() {
                let _this = this;
                _this.$ajax
                    .get(
                        process.env.VUE_APP_SERVER +
                        "/business/admin/count/allCount/"
                    )
                    .then((response) => {
                        console.log("汇总数据");
                        let resp = response.data;
                        _this.count = resp.content;
                    });
            },

            getMostEnroll() {
                let _this = this;
                _this.$ajax
                    .get(
                        process.env.VUE_APP_SERVER +
                        "/business/admin/count/most-enroll"
                    )
                    .then((response) => {
                        console.log("报名数最多的五门课");
                        let resp = response.data;
                        _this.mostEnroll = resp.content;
                    });
            },




            drawSaleChart() {
                // 生成随机两组数据
                let d1 = [];
                for (let i = 0; i < 7; i += 1) {
                    d1.push([i + 1, 0 + Math.floor((Math.random()*10)+1)]);
                }
                let d2 = [];
                for (let i = 0; i < 7; i += 1) {
                    d2.push([i + 1, 0 + Math.floor((Math.random()*10)+1)]);
                }

                let sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
                $.plot("#sales-charts", [
                    { label: "最近7天", data: d1 },
                    // { label: "上一周期", data: d2 },
                ], {
                    hoverable: true,
                    shadowSize: 0,
                    series: {
                        lines: { show: true },
                        points: { show: true }
                    },
                    xaxis: {
                        tickLength: 0
                    },
                    yaxis: {
                        tickLength: 0
                    },
                    grid: {
                        backgroundColor: { colors: [ "#fff", "#fff" ] },
                        borderWidth: 1,
                        borderColor:'#555'
                    }
                });
            },

        }
    }
</script>