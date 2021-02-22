package com.course.system.controller;

import com.course.system.domain.Test;
import com.course.system.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TestService testService;


    @RequestMapping("/test")
    public List<Test> test() {
        return testService.list();
    }
}
