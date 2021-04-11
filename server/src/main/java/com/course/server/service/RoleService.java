package com.course.server.service;

import com.course.server.domain.Role;
import com.course.server.domain.RoleExample;
import com.course.server.domain.RoleResources;
import com.course.server.domain.RoleResourcesExample;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.RoleResourcesMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourcesMapper roleResourceMapper;

//    @Resource
//    private RoleUserMapper roleUserMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<RoleDto> roleDtoList = new ArrayList<RoleDto>(); //将查到到所有数据转换成roleDto
        for (int i = 0; i <roleList.size(); i++) {
            Role role = roleList.get(i);
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role,roleDto);
            roleDtoList.add(roleDto);
        }
        pageDto.setList(roleDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param roleDto
     */
    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (StringUtils.isEmpty(roleDto.getId())){ //id为空则调用insert方法添加
            this.insert(role);
        }
        else { //id不为空
            this.update(role);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(Role role) {

        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }

    /**
     * 更新
     * @param role
     */
    private void update(Role role) {

        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }


    /**
     * 按角色保存资源
     */
    @Transactional
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourcesExample example = new RoleResourcesExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResources roleResource = new RoleResources();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourcesId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 按角色加载资源,根据roleId去查
     * @param roleId
     */
    public List<String> listResource(String roleId) {
        RoleResourcesExample example = new RoleResourcesExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResources> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourcesId());
        }
        return resourceIdList;
    }

}
