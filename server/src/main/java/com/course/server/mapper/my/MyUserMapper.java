package com.course.server.mapper.my;

import com.course.server.dto.ResourcesDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyUserMapper {

    List<ResourcesDto> findResources(@Param("userId") String userId);

}
