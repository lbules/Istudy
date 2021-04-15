<template>
    <div class="card mb-4 shadow-sm course">
        <img class="img-fluid" v-bind:src="newcourse.image">
        <div class="card-body">
            <h4 class="">{{newcourse.name}}</h4>
            <p class="card-text">{{newcourse.summary}}</p>
            <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                    <!--<router-link v-bind:to="'/detail?id=' + course.id" class="btn btn-outline-secondary">课程详情</router-link>-->
                    <button type="button" class="btn btn-sm btn-outline-secondary">课程详情</button>
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
</style>