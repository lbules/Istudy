package com.course.business.controller.web;

import com.course.server.domain.CourseComment;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.service.CourseCommentService;
import com.course.server.service.CourseService;
import com.course.server.service.MemberCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/4/1 20:55
 * @Description: 会员个人中心
 */

@RestController
@RequestMapping("/web/home")
public class WebHomeController {
    private static final Logger LOG = LoggerFactory.getLogger(WebHomeController.class);

    @Resource
    private CourseService courseService;

    @Resource
    private MemberCourseService memberCourseService;

    @Resource
    private CourseCommentService courseCommentService;



    //    查找会员的收藏课程
    @GetMapping("/listMemberCourse/{memberId}")
    public ResponseDto findCourse(@PathVariable String memberId) {
        LOG.info("查找报名课程开始：{}", memberId);
        ResponseDto responseDto = new ResponseDto();
        List<CourseDto> courseDtoList = memberCourseService.listMemberCourse(memberId);
        responseDto.setContent(courseDtoList);

        LOG.info("查找收藏课程结束：{}", responseDto);
        return responseDto;
    }


    //    查找会员的评论
    @GetMapping("/listComment/{memberId}")
    public ResponseDto findComment(@PathVariable String memberId) {
        LOG.info("查找会员评论开始：{}", memberId);
        ResponseDto responseDto = new ResponseDto();
        List<CourseComment> courseCommentDto = courseCommentService.listComment(memberId);
        responseDto.setContent(courseCommentDto);

        LOG.info("查找收藏课程结束：{}", responseDto);
        return responseDto;
    }


}
