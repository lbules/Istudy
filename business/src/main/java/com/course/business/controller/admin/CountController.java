package com.course.business.controller.admin;

import com.course.server.dto.CountDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CountService;
import com.course.server.service.CourseService;
import com.course.server.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理查询会员
 */
@RestController
@RequestMapping("/admin/count")
public class CountController {
    private static final Logger LOG = LoggerFactory.getLogger(CountController.class);

    public static final String BUSINESS_NAME="统计分析";

    @Resource
    private CourseService courseService;

    @Resource
    private CountService countService;

    @RequestMapping("/allCount")
    public ResponseDto allCount() {
        LOG.info("开始查找所有的统计数据");
        ResponseDto responseDto = new ResponseDto();
        CountDto countDto = countService.allCount();
        responseDto.setContent(countDto);
        LOG.info("汇总数据查找结束：{}");
        return responseDto;
    }


}
