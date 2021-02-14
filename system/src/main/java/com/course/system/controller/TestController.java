package com.course.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: luoss
 * @Date: 2021/2/14 16:44
 * @Description:
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "success";
    }
}
