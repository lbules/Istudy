package com.course.business.controller.admin;

import com.course.server.domain.Course;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    public static final String BUSINESS_NAME="课程表";

    @Resource
    private CourseService courseService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param courseDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        //保存校验
            ValidatorUtil.require(courseDto.getName(), "名称");
            ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
            ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
            ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }



}