package com.course.business.controller.web;

import com.course.business.controller.admin.CategoryController;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/4/3 20:22
 * @Description: web课程页面请求
 */

@RestController
@RequestMapping("/web/category")
public class WebCategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryService categoryService;

    /**
     * 查询所有的课程分类
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<CategoryDto> categoryDtoList = categoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }
}
