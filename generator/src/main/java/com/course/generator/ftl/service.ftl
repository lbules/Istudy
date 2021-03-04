package com.course.server.service;

import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.${Domain}Mapper;
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
import java.util.List;

/**
 * @Auther: luoss
 * @Date: 2021/2/28 12:20
 * @Description:
 */
@Service
public class ${Domain}Service {

    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        ${Domain}Example ${domain}Example = new ${Domain}Example();
//        ${domain}Example.setOrderByClause("id desc");
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<${Domain}Dto> ${domain}DtoList = new ArrayList<${Domain}Dto>(); //将查到到所有数据转换成${domain}Dto
        for (int i = 0; i <${domain}List.size(); i++) {
            ${Domain} ${domain} = ${domain}List.get(i);
            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            BeanUtils.copyProperties(${domain},${domain}Dto);
            ${domain}DtoList.add(${domain}Dto);
        }
        pageDto.setList(${domain}DtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存章节,编辑保存时根据id是否为空
     * @param ${domain}Dto
     */
    public void save(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isEmpty(${domain}Dto.getId())){ //id为空则调用insert方法添加
            this.insert(${domain});
        }
        else { //id不为空
            this.update(${domain});
        }
    }

    /**
     * 新增章节
     * @param 利用包装工具类CopyUtil将${domain}Dto转换成${domain}
     */
    private void insert(${Domain} ${domain}) {
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }

    /**
     * 更新章节
     * @param ${domain}
     */
    private void update(${Domain} ${domain}) {
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 删除章节
     * @param id
     */
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

}
