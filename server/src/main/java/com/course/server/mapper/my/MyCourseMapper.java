package com.course.server.mapper.my;

import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

    int updateSort(SortDto sortDto);

    int moveSortsBackward(SortDto sortDto);

    int moveSortsForward(SortDto sortDto);

    List<CourseDto> listCourse(@Param("pageDto") CoursePageDto pageDto);

    List<String> listCollection(@Param("memberId") String memberId);

    List<String> listMemberCourse(@Param("memberId") String memberId);

}


