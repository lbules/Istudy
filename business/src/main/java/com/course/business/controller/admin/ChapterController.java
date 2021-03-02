package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/2/28 12:26
 * @Description:
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {



    @Resource
    private ChapterService chapterService;

    @RequestMapping("/list")
    /**
     * pageDto既用来接收参数，也用来返回结果
     * 前端会传来pageDto的page（当前页码）size（每页条数），
     */
    public PageDto list(@RequestBody PageDto pageDto) {
        chapterService.list(pageDto);
        return pageDto;
    }


}
