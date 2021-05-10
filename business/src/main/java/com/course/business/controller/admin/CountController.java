package com.course.business.controller.admin;

import com.course.server.domain.MemberCourse;
import com.course.server.dto.*;
import com.course.server.service.CountService;
import com.course.server.service.CourseService;
import com.course.server.service.MemberCourseService;
import com.course.server.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理查询会员
 */
@RestController
@RequestMapping("/admin/count")
public class CountController {
    private static final Logger LOG = LoggerFactory.getLogger(CountController.class);

    public static final String BUSINESS_NAME="统计分析";

    @Resource
    private CourseService courseService;

    @Resource
    private CountService countService;

    @Resource
    private MemberCourseService memberCourseService;

    //获取所有的汇总数据
    @RequestMapping("/allCount")
    public ResponseDto allCount() {
        LOG.info("开始查找所有的统计数据");
        ResponseDto responseDto = new ResponseDto();
        CountDto countDto = countService.allCount();
        responseDto.setContent(countDto);
        LOG.info("汇总数据查找结束：{}");
        return responseDto;
    }


    //获取报名人数最多的五门课程
    @RequestMapping("/most-enroll")
    public ResponseDto mostEnroll() {
        PageDto pageDto = new PageDto();
        pageDto.setPage(1);
        pageDto.setSize(5);
        ResponseDto responseDto = new ResponseDto();
        List<CourseDto> CourseDto = courseService.mostEnroll(pageDto);
        responseDto.setContent(CourseDto);

        return responseDto;
    }

    //获取近一个月会员注册数量
    @RequestMapping("/member-analyse")
    public ResponseDto memberAnalyse() {
        ResponseDto responseDto = new ResponseDto();
        //存放近一个月的会员注册数量
        ArrayList memberArray= new ArrayList();
        memberArray = countService.memberAnalyse();
        responseDto.setContent(memberArray);

        return responseDto;
    }


}
