package com.course.server.mapper;

import com.course.server.domain.CourseComment;
import com.course.server.domain.CourseCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseCommentMapper {
    long countByExample(CourseCommentExample example);

    int deleteByExample(CourseCommentExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseComment record);

    int insertSelective(CourseComment record);

    List<CourseComment> selectByExample(CourseCommentExample example);

    CourseComment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseComment record, @Param("example") CourseCommentExample example);

    int updateByExample(@Param("record") CourseComment record, @Param("example") CourseCommentExample example);

    int updateByPrimaryKeySelective(CourseComment record);

    int updateByPrimaryKey(CourseComment record);
}