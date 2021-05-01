package com.course.business.controller.admin;

import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.TeacherService;
import com.course.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    public static final String BUSINESS_NAME="";

    @Resource
    private TeacherService teacherService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        teacherService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param teacherDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody TeacherDto teacherDto) {
        //保存校验
            ValidatorUtil.require(teacherDto.getName(), "");
            ValidatorUtil.length(teacherDto.getName(), "", 1, 50);
            ValidatorUtil.length(teacherDto.getNickname(), "", 1, 50);
//            ValidatorUtil.length(teacherDto.getImage(), "", 1, 100);
            ValidatorUtil.length(teacherDto.getPosition(), "", 1, 50);
            ValidatorUtil.length(teacherDto.getMotto(), "", 1, 50);
            ValidatorUtil.length(teacherDto.getIntro(), "", 1, 500);

        ResponseDto responseDto = new ResponseDto();
        teacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        teacherService.delete(id);
        return responseDto;
    }

    /**
     * 查询所有的讲师
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<TeacherDto> teacherDtoList = teacherService.all();
        responseDto.setContent(teacherDtoList);
        return responseDto;
    }




}
