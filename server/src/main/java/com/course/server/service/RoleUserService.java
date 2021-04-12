package com.course.server.service;

import com.course.server.domain.RoleUser;
import com.course.server.domain.RoleUserExample;
import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleUserMapper;
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

@Service
public class RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<RoleUserDto> roleUserDtoList = new ArrayList<RoleUserDto>(); //将查到到所有数据转换成roleUserDto
        for (int i = 0; i <roleUserList.size(); i++) {
            RoleUser roleUser = roleUserList.get(i);
            RoleUserDto roleUserDto = new RoleUserDto();
            BeanUtils.copyProperties(roleUser,roleUserDto);
            roleUserDtoList.add(roleUserDto);
        }
        pageDto.setList(roleUserDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param roleUserDto
     */
    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if (StringUtils.isEmpty(roleUserDto.getId())){ //id为空则调用insert方法添加
            this.insert(roleUser);
        }
        else { //id不为空
            this.update(roleUser);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(RoleUser roleUser) {

        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }

    /**
     * 更新
     * @param roleUser
     */
    private void update(RoleUser roleUser) {

        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }

}
