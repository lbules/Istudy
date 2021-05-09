<template>
    <div class="course-card-container">
        <div class="course-card-top purple-bg">
            <router-link v-bind:to="'/courseDetail?id=' + collectionCourse.id" class="course-name">
                <span>{{collectionCourse.name}}</span>
            </router-link>
        </div>
        <img class="img-fluid" v-bind:src="collectionCourse.image">
        <div class="course-card-content">
            <h3 class="course-card-name">{{collectionCourse.summary}}</h3>
            <div class="clearfix course-card-bottom">

            </div>
        </div>
    </div>
</template>



<script>
    export default {
        name: 'the-collection',
        props: {
            collectionCourse: {}
        },
        data: function () {
            return {
                COURSE_LEVEL: COURSE_LEVEL
            }
        },
    }

</script>


<style>
    .course-card-container {
        width: 224px;
        height: 228px;
        border-radius: 4px;
        background-color: #fff;
        box-shadow: 0 4px 8px 0 rgb(7 17 27/10%);
        transition: .3s all linear;
        float: left;
        margin-right: 20px;
        margin-bottom: 20px;
    }
    .course-card-container .course-card-top {
        height: 48px;
        line-height: 48px;
        padding: 0 24px;
        overflow: hidden;
        color: #FFF;
        border-top-left-radius: 4px;
        border-top-right-radius: 4px;
    }

    .purple-bg {
        background-color: #CD71E1;
    }

    .course-card-container .course-card-content {
        background-color: #fff;
        padding: 0px 22px;
        box-sizing: border-box;
    }

    .course-card-container .course-card-name {
        font-size: 14px;
        color: #07111b;
        height: 44px;
        line-height: 20px;
        width: 180px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .course-card-container .course-card-bottom {
        font-size: 12px;
        text-align: right;
    }
    .clearfix {
        clear: both;
    }

    .course-name {
        height: 48px;
        line-height: 48px;
        padding: 0 24px;
        overflow: hidden;
        color: #FFF;
        border-top-left-radius: 4px;
        border-top-right-radius: 4px;
    }
</style>