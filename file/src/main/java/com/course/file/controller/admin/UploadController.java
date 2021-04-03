package com.course.file.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.service.TeacherService;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UploadController {
    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @Resource
    private TeacherService teacherService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始");
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //保存文件
        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullpath = "D:/file/imooc/course/teacher/"+key+"-"+fileName;
        File dest = new File(fullpath);
        file.transferTo(dest);

       ResponseDto responseDto= new ResponseDto();
       responseDto.setContent("http://127.0.0.1:9003/file/f/teacher/"+key+"-"+fileName);
       return responseDto;
    }
}
