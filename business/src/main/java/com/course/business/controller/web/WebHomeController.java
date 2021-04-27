package com.course.business.controller.web;

import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/4/1 20:55
 * @Description: 会员个人中心
 */

@RestController
@RequestMapping("/web/home")
public class WebHomeController {
    private static final Logger LOG = LoggerFactory.getLogger(WebHomeController.class);

    @Resource
    private CourseService courseService;




}
