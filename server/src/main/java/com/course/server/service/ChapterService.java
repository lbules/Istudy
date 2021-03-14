package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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

    public void list(ChapterPageDto chapterPageDto) {

        PageHelper.startPage(chapterPageDto.getPage(),chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();

        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        //CourseId不为空的时候查找对应课程下的大章，CourseId为空则查找全部
        if (!StringUtils.isEmpty(chapterPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }

        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<ChapterDto> chapterDtoList = new ArrayList<ChapterDto>(); //将查到到所有数据转换成chapterDto
        for (int i = 0; i <chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        chapterPageDto.setList(chapterDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存章节,编辑保存时根据id是否为空
     * @param
     */
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())){ //id为空则调用insert方法添加
            this.insert(chapter);
        }
        else { //id不为空
            this.update(chapter);
        }
    }

    /**
     * 新增章节
     * @param
     */
    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    /**
     * 更新章节
     * @param chapter
     */
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除章节
     * @param id
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }

}
