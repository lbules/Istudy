package com.course.business.controller.web;

import com.course.server.domain.Collection;
import com.course.server.dto.CollectionDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CollectionService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/web/courseCollection")
public class WebCollectionController {

    public static final String BUSINESS_NAME = "";
    private static final Logger LOG = LoggerFactory.getLogger(WebCollectionController.class);

    @Resource
    private CollectionService collectionService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        collectionService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     *
     * @param collectionDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody CollectionDto collectionDto) {
        //保存校验
        ValidatorUtil.require(collectionDto.getMemberId(), "会员id");
        ValidatorUtil.require(collectionDto.getCourseId(), "课程id");

        ResponseDto responseDto = new ResponseDto();
        collectionService.save(collectionDto);
        responseDto.setContent(collectionDto);
        return responseDto;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        collectionService.delete(id);
        return responseDto;
    }

    //查询是否已收藏课程
    @PostMapping("/ifCollection")
    public ResponseDto ifCollection(@RequestBody CollectionDto collectionDto) {
        ResponseDto responseDto = new ResponseDto();
        collectionDto = collectionService.ifCollection(collectionDto);
        responseDto.setContent(collectionDto);
        return responseDto;
    }

    //    取消收藏
    @PostMapping("/delect-collection")
    public ResponseDto delectCollection(@RequestBody CollectionDto collectionDto) {
        ResponseDto responseDto = new ResponseDto();
        collectionDto = collectionService.delectCollection(collectionDto);
        responseDto.setContent(collectionDto);
        return responseDto;
    }



    //    查找会员的收藏课程
    @GetMapping("/listCollection/{memberId}")
    public ResponseDto findCourse(@PathVariable String memberId) {
        LOG.info("查找收藏课程开始：{}", memberId);

        ResponseDto responseDto = new ResponseDto();
        List<CourseDto> courseDtoList = collectionService.listCollection(memberId);
        responseDto.setContent(courseDtoList);

        LOG.info("查找收藏课程结束：{}", responseDto);
        return responseDto;
    }
}
