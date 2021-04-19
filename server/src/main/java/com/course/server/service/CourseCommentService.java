package com.course.server.service;

import com.course.server.domain.CourseComment;
import com.course.server.domain.CourseCommentExample;
import com.course.server.dto.CourseCommentDto;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCommentMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.boot.Banner.Mode.LOG;

@Service
public class CourseCommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseCommentService.class);

    @Resource
    private CourseCommentMapper courseCommentMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        CourseCommentExample courseCommentExample = new CourseCommentExample();
        List<CourseComment> courseCommentList = courseCommentMapper.selectByExample(courseCommentExample);
        PageInfo<CourseComment> pageInfo = new PageInfo<>(courseCommentList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<CourseCommentDto> courseCommentDtoList = new ArrayList<CourseCommentDto>(); //将查到到所有数据转换成courseCommentDto
        for (int i = 0; i <courseCommentList.size(); i++) {
            CourseComment courseComment = courseCommentList.get(i);
            CourseCommentDto courseCommentDto = new CourseCommentDto();
            BeanUtils.copyProperties(courseComment,courseCommentDto);
            courseCommentDtoList.add(courseCommentDto);
        }
        pageDto.setList(courseCommentDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param courseCommentDto
     */
    public void save(CourseCommentDto courseCommentDto) {
        CourseComment courseComment = CopyUtil.copy(courseCommentDto, CourseComment.class);
        if (StringUtils.isEmpty(courseCommentDto.getId())){ //id为空则调用insert方法添加
            this.insert(courseComment);
        }
        else { //id不为空
            this.update(courseComment);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(CourseComment courseComment) {
        Date now = new Date();
        courseComment.setCreateTime(now);
        courseComment.setId(UuidUtil.getShortUuid());
        courseCommentMapper.insert(courseComment);
    }

    /**
     * 更新
     * @param courseComment
     */
    private void update(CourseComment courseComment) {

        courseCommentMapper.updateByPrimaryKey(courseComment);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        courseCommentMapper.deleteByPrimaryKey(id);
    }


    /**
     * 查找课程评论
     */
    public List<CourseComment> findComment(String id) {
        CourseCommentExample example = new CourseCommentExample();
        example.createCriteria().andCourseIdEqualTo(id);
        List<CourseComment> courseCommentList= courseCommentMapper.selectByExample(example);

        LOG.info("课程评论"+courseCommentList);

        if (courseCommentList==null||courseCommentList.size()==0) {
            return null;
        }else {
           return courseCommentList;
        }
    }







}
