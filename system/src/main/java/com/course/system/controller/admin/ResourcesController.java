package com.course.system.controller.admin;

import com.course.server.domain.Resources;
import com.course.server.dto.ResourcesDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ResourcesService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/resources")
public class ResourcesController {

    public static final String BUSINESS_NAME="";

    @Resource
    private ResourcesService resourcesService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        resourcesService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param resourcesDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody ResourcesDto resourcesDto) {
        //保存校验
            ValidatorUtil.length(resourcesDto.getName(), "名称", 1, 100);
            ValidatorUtil.length(resourcesDto.getPage(), "页面", 1, 50);
            ValidatorUtil.length(resourcesDto.getRequest(), "请求", 1, 200);

        ResponseDto responseDto = new ResponseDto();
        resourcesService.save(resourcesDto);
        responseDto.setContent(resourcesDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        resourcesService.delete(id);
        return responseDto;
    }

    /**
     * 资源树查询
     */
    @GetMapping("/load-tree")
    public ResponseDto loadTree() {
        ResponseDto responseDto = new ResponseDto();
        List<ResourcesDto> resourcesDtoList = resourcesService.loadTree();
        responseDto.setContent(resourcesDtoList);
        return responseDto;
    }

}
