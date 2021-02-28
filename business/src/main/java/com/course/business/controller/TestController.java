package com.course.business.controller;

import com.course.server.domain.Test;
import com.course.server.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/2/14 16:44
 * @Description:
 */
@RestController
public class TestController {

    @Resource
    private TestService testService;


    @RequestMapping("/test")
    public List<Test> test() {
        return testService.list();
    }
}
