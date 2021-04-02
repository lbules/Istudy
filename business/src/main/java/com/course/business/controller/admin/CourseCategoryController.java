package com.course.business.controller.admin;

import com.course.server.domain.CourseCategory;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseCategoryService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/courseCategory")
public class CourseCategoryController {

    public static final String BUSINESS_NAME="";

    @Resource
    private CourseCategoryService courseCategoryService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param courseCategoryDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody CourseCategoryDto courseCategoryDto) {
        //保存校验

        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.save(courseCategoryDto);
        responseDto.setContent(courseCategoryDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.delete(id);
        return responseDto;
    }



}
