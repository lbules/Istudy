package com.course.file.controller.admin;

import com.course.server.domain.File;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.FileService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    public static final String BUSINESS_NAME="";

    @Resource
    private FileService fileService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        fileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param fileDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody FileDto fileDto) {
        //保存校验
            ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 100);
            ValidatorUtil.require(fileDto.getPath(), "相对路径");
            ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
            ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        fileService.save(fileDto);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        fileService.delete(id);
        return responseDto;
    }



}
