package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/2/28 12:20
 * @Description:
 */
@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        ChapterExample chapterExample = new ChapterExample();
//        chapterExample.setOrderByClause("id desc");
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<ChapterDto> chapterDtoList = new ArrayList<ChapterDto>(); //将查到到所有数据转换成chapterDto
        for (int i = 0; i <chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList); //将记录存放到pageDto的List

    }

}
