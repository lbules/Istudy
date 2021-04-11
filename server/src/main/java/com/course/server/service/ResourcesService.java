package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Resources;
import com.course.server.domain.ResourcesExample;
import com.course.server.dto.ResourcesDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ResourcesMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcesService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourcesService.class);

    @Resource
    private ResourcesMapper resourcesMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        ResourcesExample resourcesExample = new ResourcesExample();
        List<Resources> resourcesList = resourcesMapper.selectByExample(resourcesExample);
        PageInfo<Resources> pageInfo = new PageInfo<>(resourcesList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<ResourcesDto> resourcesDtoList = new ArrayList<ResourcesDto>(); //将查到到所有数据转换成resourcesDto
        for (int i = 0; i <resourcesList.size(); i++) {
            Resources resources = resourcesList.get(i);
            ResourcesDto resourcesDto = new ResourcesDto();
            BeanUtils.copyProperties(resources,resourcesDto);
            resourcesDtoList.add(resourcesDto);
        }
        pageDto.setList(resourcesDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param resourcesDto
     */
    public void save(ResourcesDto resourcesDto) {
        Resources resources = CopyUtil.copy(resourcesDto, Resources.class);
        if (StringUtils.isEmpty(resourcesDto.getId())){ //id为空则调用insert方法添加
            this.insert(resources);
        }
        else { //id不为空
            this.update(resources);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(Resources resources) {

        resources.setId(UuidUtil.getShortUuid());
        resourcesMapper.insert(resources);
    }

    /**
     * 更新
     * @param resources
     */
    private void update(Resources resources) {

        resourcesMapper.updateByPrimaryKey(resources);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        resourcesMapper.deleteByPrimaryKey(id);
    }


    /**
     * 保存资源树
     * @param json
     */
    @Transactional
    public void saveJson(String json) {
        List<ResourcesDto> jsonList = JSON.parseArray(json, ResourcesDto.class);
        List<ResourcesDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourcesDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        LOG.info("共{}条", list.size());

        resourcesMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            this.insert(CopyUtil.copy(list.get(i), Resources.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourcesDto> list, ResourcesDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourcesDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }




    /**
     * 按约定将列表转成树
     * 要求：ID要正序排列
     * @return
     */
    public List<ResourcesDto> loadTree() {
        ResourcesExample example = new ResourcesExample();
        example.setOrderByClause("id asc");
        List<Resources> resourcesList = resourcesMapper.selectByExample(example);
        List<ResourcesDto> resourcesDtoList = CopyUtil.copyList(resourcesList, ResourcesDto.class);
        for (int i = resourcesDtoList.size() - 1; i >= 0; i--) {
            // 当前要移动的记录
            ResourcesDto child = resourcesDtoList.get(i);

            // 如果当前节点没有父节点，则不用往下了
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            // 查找父节点
            for (int j = i - 1; j >= 0; j--) {
                ResourcesDto parent = resourcesDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0, child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourcesDtoList.remove(child);
                }
            }
        }
        return resourcesDtoList;
    }

}
