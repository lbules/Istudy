package com.course.business.controller.admin;

import com.course.server.domain.CourseContentFile;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseContentFileService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course-content-file")
public class CourseContentFileController {

    public static final String BUSINESS_NAME="";

    @Resource
    private CourseContentFileService courseContentFileService;

    /**
     * 列表查询
     */
    @GetMapping("/list/{courseId}")
    public ResponseDto list(@PathVariable String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseContentFileDto> fileDtoList = courseContentFileService.list(courseId);
        responseDto.setContent(fileDtoList);
        return responseDto;
    }

    /**
     * 保存
     * @param courseContentFileDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody CourseContentFileDto courseContentFileDto) {
        //保存校验
            ValidatorUtil.require(courseContentFileDto.getCouseId(), "课程id");
            ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
            ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.save(courseContentFileDto);
        responseDto.setContent(courseContentFileDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.delete(id);
        return responseDto;
    }



}
