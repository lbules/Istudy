<template>
    <div class="card mb-4 shadow-sm course">
        <img class="img-fluid" style="width: 345px;height: 192px;" v-bind:src="newcourse.image">
        <div class="card-body">
            <h4 class="">{{newcourse.name}}</h4>
            <p class="card-text" style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">{{newcourse.summary}}</p>
            <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                    <router-link v-bind:to="'/courseDetail?id=' + newcourse.id" class="btn btn-outline-secondary">课程详情</router-link>
                </div>
                <div class="text-muted">
                    <span class="badge badge-info"><i class="fa fa-yen" aria-hidden="true"></i>&nbsp;{{newcourse.price}}</span>&nbsp;
                    <span class="badge badge-info"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;{{newcourse.enroll}}</span>&nbsp;
                    <span class="badge badge-info">{{COURSE_LEVEL | optionKV(newcourse.level)}}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'new-course',
        props: {
            newcourse: {}
        },
        data: function () {
            return {
                COURSE_LEVEL: COURSE_LEVEL
            }
        },
    }

</script>

<style>
    .course h4 {
        font-size: 1.25rem;
        margin: 15px 0;
    }

    .course .text-muted .badge {
        font-size: 1rem;
    }

    .course {
        border-bottom-right-radius:25px;
        border-bottom-left-radius:25px;
    }
</style>