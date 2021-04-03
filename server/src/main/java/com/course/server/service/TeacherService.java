package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<TeacherDto> teacherDtoList = new ArrayList<TeacherDto>(); //将查到到所有数据转换成teacherDto
        for (int i = 0; i <teacherList.size(); i++) {
            Teacher teacher = teacherList.get(i);
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher,teacherDto);
            teacherDtoList.add(teacherDto);
        }
        pageDto.setList(teacherDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param teacherDto
     */
    public void save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if (StringUtils.isEmpty(teacherDto.getId())){ //id为空则调用insert方法添加
            this.insert(teacher);
        }
        else { //id不为空
            this.update(teacher);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(Teacher teacher) {

        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    /**
     * 更新
     * @param teacher
     */
    private void update(Teacher teacher) {

        teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

}
