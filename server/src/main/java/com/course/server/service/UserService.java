package com.course.server.service;

import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.UserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<UserDto> userDtoList = new ArrayList<UserDto>(); //将查到到所有数据转换成userDto
        for (int i = 0; i <userList.size(); i++) {
            User user = userList.get(i);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtoList.add(userDto);
        }
        pageDto.setList(userDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param userDto
     */
    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isEmpty(userDto.getId())){ //id为空则调用insert方法添加
            this.insert(user);
        }
        else { //id不为空
            this.update(user);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(User user) {

        user.setId(UuidUtil.getShortUuid());
        User userDb = this.selectByLoginName(user.getLoginName());
        //用户已经存在
        if (userDb!=null) {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
            userMapper.insert(user);
    }

    /**
     * 更新
     *
     */
    private void update(User user) {
        user.setPassword(null);
        //如果user中的password有值就会去更新，没有值时更新user时不会去更新password字段
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 重置密码
     * @param userDto
     */
    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 用户登录
     * @param
     */
    public LoginUserDto login(UserDto userDto) {
        //先根据用户名去查找
        User user = selectByLoginName(userDto.getLoginName());
        if (user==null) {
            //用户名不存在
            LOG.info("用户名不存在,{}",userDto.getLoginName());
            //返回信息给前端
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else {
            //用户存在则去匹对密码是否正确
            if (user.getPassword().equals(userDto.getPassword())) {
                //登录成功,只返回id，loginName和name三个字段
               return CopyUtil.copy(user,LoginUserDto.class);
            }else {
                //密码不对
                LOG.info("密码不对，输入密码：{}，数据库密码：{}",userDto.getPassword(),user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

}
