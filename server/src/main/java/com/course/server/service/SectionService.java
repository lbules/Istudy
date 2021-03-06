package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.enums.SectionChargeEnum;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());

        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

        List<SectionDto> sectionDtoList = new ArrayList<SectionDto>(); //将查到到所有数据转换成sectionDto
        for (int i = 0; i < sectionList.size(); i++) {
            Section section = sectionList.get(i);
            SectionDto sectionDto = new SectionDto();
            BeanUtils.copyProperties(section, sectionDto);
            sectionDtoList.add(sectionDto);
        }
        pageDto.setList(sectionDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存章节,编辑保存时根据id是否为空
     *
     * @param sectionDto
     */
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) { //id为空则调用insert方法添加
            this.insert(section);
        } else { //id不为空
            this.update(section);
        }
    }

    /**
     * 新增章节
     *
     * @param
     */
    private void insert(Section section) {
        Date now = new Date();
        section.setCreateAt(now);
        section.setUpdateAt(now);
        section.setCharge(SectionChargeEnum.CHARGE.getCode());

        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 更新章节
     *
     * @param section
     */
    private void update(Section section) {
        section.setUpdateAt(new Date());

        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除章节
     *
     * @param id
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }

}
