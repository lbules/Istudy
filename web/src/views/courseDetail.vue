<template>
    <main role="main">
        <div class="album py-5 bg-light">
            <div class="container">
                <div class="row course-head">
                    <div class="col-sm-6" id="cover-video-div">
                        <img class="img-fluid" v-bind:src="course.image">
                    </div>
                    <div class="col-sm-6">
                        <h1>{{course.name}}</h1>
                        <p class="course-head-item">
                            <span><i class="fa fa-clock-o"></i> {{(course.time) | formatSecond}}</span>
                            <span>{{COURSE_LEVEL | optionKV(course.level)}}</span>
                            <span><i class="fa fa-user"></i> {{course.enroll}}</span>
                        </p>
                        <p class="course-head-desc">{{course.summary}}</p>
                        <p class="course-head-price">
                            <span class="price-now text-danger"><i class="fa fa-yen"></i>&nbsp;{{course.price}}&nbsp;&nbsp;</span>
                        </p>
                        <p class="course-head-button-links">
                            <a v-show="!memberCourse.id" v-on:click="enroll()" class="btn btn-lg btn-primary btn-shadow"
                               href="javascript:;">立即报名</a>
                            <a v-show="memberCourse.id" href="#"
                               class="btn btn-lg btn-success btn-shadow disabled">已报名</a>
                        </p>
                    </div>
                </div>

                <div class="row">

                    <!-- 课程内容 & 大章小节 -->
                    <div class="col-md-9">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs">
                            <li class="nav-item">
                                <a class="nav-link active" href="#info" data-toggle="tab">课程介绍</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#chapter" data-toggle="tab">章节目录</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#comment" data-toggle="tab">评论</a>
                            </li>
                        </ul>

                        <br>

                        <!-- 课程页面切换显示Tab panes -->
                        <div class="tab-content">
                            <!--课程介绍-->
                            <div class="tab-pane active" id="info" v-html="course.content"></div>
                            <!--章节-->
                            <div class="tab-pane" id="chapter">
                                <div v-for="(chapter, i) in chapters" class="chapter">
                                    <div v-on:click="doFolded(chapter, i)" class="chapter-chapter">
                                        <span>{{chapter.name}}</span>
                                        <span class="pull-right">
                                        <i v-show="chapter.folded" class="fa fa-plus-square" aria-hidden="true"></i>
                                        <i v-show="!chapter.folded" class="fa fa-minus-square" aria-hidden="true"></i>
                                        </span>
                                    </div>
                                    <div v-show="!chapter.folded">
                                        <table class="table table-striped">
                                            <tr v-for="(s, j) in chapter.sections" class="chapter-section-tr">
                                                <td class="col-sm-8 col-xs-12">
                                                    <div v-on:click="play(s)" class="section-title">
                                                        <i class="fa fa-video-camera d-none d-sm-inline"></i>&nbsp;&nbsp;
                                                        <span class="d-none d-sm-inline">第{{j+1}}节&nbsp;&nbsp;</span>
                                                        {{s.title}}
                                                        <span v-show="s.charge !== SECTION_CHARGE.CHARGE.key"
                                                              class="badge badge-primary hidden-xs">免费</span>
                                                    </div>
                                                </td>
                                                <td class="col-sm-1 text-right">
                                                    <span class="badge badge-primary">{{s.time | formatSecond}}</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--评论-->
                            <div class="tab-pane" id="comment" >
                                <!--发表评论-->
                                <div>
                                    <form role="form">
                                        <div class="form-group">
                                            <label>写评论</label>
                                            <input v-model="newComment"type="text" class="form-control" placeholder="写下您的评论....">
                                        </div>
                                        <button v-on:click="submitComment()" type="button" class="btn btn-info" style="margin-left: 735px">评论一下</button>
                                    </form>
                                    <hr>
                                </div>
                                <!--显示评论-->
                                <div v-for="commentItem in courseComment">
                                    <div class="comment clearfix">
                                        <div class="comment-header"><img class="lecturer-uimg" src="../../public/static/image/header.jpg"></div>
                                        <div class="comment-main">
                                            <div class="user-name">{{commentItem.memberName}}</div>
                                            <div class="comment-content">{{commentItem.comment}}</div>
                                            <div class="comment-footer">{{commentItem.createTime}}</div>
                                        </div>
                                    </div>
                                </div>

                            </div>


                        </div>

                    </div>

                    <!-- 讲师信息 -->
                    <div class="col-md-3">
                        <div class="card" style="width: 18rem;">
                            <img v-bind:src="teacher.image" class="card-img-top">
                            <div class="card-body">
                                <h5 class="card-title">{{teacher.name}}</h5>
                                <p class="card-text">{{teacher.motto}}</p>
                                <p class="card-text">{{teacher.intro}}</p>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </main>
</template>

<script>
    export default {
        // 课程详情
        name: 'courseDetail',
        data: function () {
            return {
                id: "",
                course: {},
                teacher: {},
                chapters: [],
                sections: [],
                courseComment:{}, //评论
                newComment:"",
                memberCourse: {},
                COURSE_LEVEL: COURSE_LEVEL,
                SECTION_CHARGE: SECTION_CHARGE
            }
        },
        mounted() {
            let _this = this;
            // 从地址栏中取出id
            _this.id = _this.$route.query.id;
            _this.CourseDetail();
            //获取课程评论
            _this.commentList();
        },

        methods: {
            // 查找课程信息
            CourseDetail() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/find/' + _this.id).then((response) => {
                    let resp = response.data;
                    _this.course = resp.content;
                    _this.teacher = _this.course.teacher || {};
                    _this.chapters = _this.course.chapters || [];
                    _this.sections = _this.course.sections || [];

                    //获取报名信息
                    _this.getEnroll();

                    // 将所有的节放入对应的章中
                    for (let i = 0; i < _this.chapters.length; i++) {
                        let c = _this.chapters[i];
                        c.sections = [];
                        for (let j = 0; j < _this.sections.length; j++) {
                            let s = _this.sections[j];
                            if (c.id === s.chapterId) {
                                c.sections.push(s);
                            }
                        }

                        // Tool.sortAsc(c.sections, "sort");
                    }
                })
            },

            /**
             * 展开/收缩一个章节
             * @param chapter
             */
            doFolded(chapter, i) {
                let _this = this;
                chapter.folded = !chapter.folded;
                // 在v-for里写v-show，只修改属性不起作用，需要$set
                _this.$set(_this.chapters, i, chapter);
            },

            /**
             * 报名
             */
            enroll() {
                let _this = this;
                let loginMember = Tool.getLoginMember();
                if (Tool.isEmpty(loginMember)) {
                    Toast.warning("请先登录");
                    return;
                }
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/memberCourse/enroll', {
                    courseId: _this.course.id,
                    memberId: loginMember.id
                }).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        _this.memberCourse = resp.content;
                        Toast.success("报名成功！");
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },

            /**
             * 获取报名
             */
            getEnroll() {
                let _this = this;
                let loginMember = Tool.getLoginMember();
                if (Tool.isEmpty(loginMember)) {
                    console.log("未登录");
                    return;
                }
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/memberCourse/get-enroll', {
                    courseId: _this.course.id,
                    memberId: loginMember.id
                }).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        _this.memberCourse = resp.content || {};
                    }
                });
            },


            /**
             * 播放视频
             * @param section
             */
            play(section) {
                let _this = this;
                if (section.charge === _this.SECTION_CHARGE.CHARGE.key) {
                    //取出已经登录的会员
                    let loginMember = Tool.getLoginMember();
                    if (Tool.isEmpty(loginMember)) {
                        Toast.warning("请先登录");
                        return;
                    } else {
                        if (Tool.isEmpty(_this.memberCourse)) {
                            Toast.warning("请先报名");
                            return;
                        }
                    }
                }
                _this.$refs.modalPlayer.playVod(section.vod);
            },

            //获取课程评论
            commentList() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/courseComment/comment-list/' + _this.id).then((response) => {
                    let resp = response.data;
                    _this.courseComment = resp.content;
                })
            },

            //发表评论
            submitComment() {
              let _this = this;
                //取出登录的会员
                let loginMember = Tool.getLoginMember();
                //登录且报名课程后才能发表评论
                if (Tool.isEmpty(loginMember)) {
                    Toast.warning("请先登录");
                    return;
                }
                else if (Tool.isEmpty(_this.memberCourse)) {
                    Toast.warning("请先报名");
                    return;
                }
                else{
                    //判断评论内容是否为空
                        if (_this.newComment===null) {
                            return
                        } else {
                            //前端校验通过，发送请求
                            _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/web/courseComment/save',{
                                courseId:_this.course.id,
                                memberId:loginMember.id,
                                memberName:loginMember.name,
                                comment:_this.newComment
                            }).then((response) => {
                                console.log("评论结果:", response);
                                let resp = response.data;
                                if (resp.success) { //保存成功就将弹出框隐藏
                                    _this.commentList() //重新刷新评论
                                    Toast.success("评论成功");
                                }
                                else {
                                    Toast.warning("评论不能为空");
                                }
                            })
                        }
                    }
            },


        }
    }
</script>

<style>
    /* 课程信息 */
    .course-head {
    }

    .course-head h1 {
        font-size: 2rem;
        margin-bottom: 1.5rem;
    }

    .course-head-item span {
        margin-right: 1rem;
    }

    .course-head-desc {
        font-size: 1rem;
        color: #555
    }

    .course-head a {
    }

    .course-head-price {
        font-size: 2rem;
    }

    @media (max-width: 700px) {
        .course-head h1 {
            font-size: 1.5rem;
        }
    }

    /* 章节列表 */
    .chapter {
        padding-bottom: 1.25rem;
    }

    .chapter-chapter {
        font-size: 1.25rem;
        padding: 1.25rem;
        background-color: #23527c;
        color: white;
        cursor: pointer;
    }

    .chapter-section-tr {
        font-size: 1rem;
    }

    .chapter-section-tr td {
        padding: 1rem 1.25rem;
        vertical-align: middle;
    }

    /*鼠标手势*/
    .chapter-section-tr td .section-title {
        color: #555;
    }

    .chapter-section-tr td .section-title:hover {
        color: #23527c;
        font-weight: bolder;
        cursor: pointer;
    }

    /*行头小图标*/
    .chapter-section-tr td .section-title i {
        color: #2a6496;
    }

    @media (max-width: 700px) {
        .chapter-chapter {
            font-size: 1.2rem;
        }

        .chapter-section-tr {
            font-size: 0.9rem;
        }
    }

    /**
*评论-start
**/
    .comment{
        padding: 0px 10px 20px 10px;
        border-bottom: 2px solid #eeeab6;
        margin: 20px 0px;
        min-height: 120px;
    }
    .comment-header{
        float: left;
        width: 70px;
    }
    .comment-main{
        float: right;
        width: 730px;
    }
    .comment-header img{
        width: 40px;
        height: 40px;
        border-radius:50%;
    }
    .comment-main .user-name{
        font-weight: bold;
        font-size: 14px;
    }
    .comment-main .comment-content{
        margin-top: 20px;
        width:100%;
    }
    .comment-main .comment-footer{
        margin-top: 20px;
    }
    .comment-main .comment-footer a{
        margin-left: 50px;
        display: inline-block;
        color: #93999f;
    }
    .comment-main .comment-footer a:hover{
        color: #0089D2;
    }
    /**
    *评论-end
    **/
</style>
