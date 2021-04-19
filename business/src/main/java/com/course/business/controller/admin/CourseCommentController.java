package com.course.business.controller.admin;

import com.course.server.domain.CourseComment;
import com.course.server.dto.CourseCommentDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseCommentService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/courseComment")
public class CourseCommentController {

    public static final String BUSINESS_NAME="";

    @Resource
    private CourseCommentService courseCommentService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseCommentService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param courseCommentDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody CourseCommentDto courseCommentDto) {
        //保存校验
            ValidatorUtil.require(courseCommentDto.getCourseId(), "课程id");
            ValidatorUtil.require(courseCommentDto.getMemberId(), "会员id");
            ValidatorUtil.require(courseCommentDto.getMemberName(), "会员昵称");
            ValidatorUtil.length(courseCommentDto.getMemberName(), "会员昵称", 1, 50);
            ValidatorUtil.length(courseCommentDto.getComment(), "评论内容", 1, 255);

        ResponseDto responseDto = new ResponseDto();
        courseCommentService.save(courseCommentDto);
        responseDto.setContent(courseCommentDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseCommentService.delete(id);
        return responseDto;
    }



}
