package com.course.system.controller.admin;

import com.course.server.dto.*;
import com.course.server.service.UserService;
import com.course.server.util.ValidatorUtil;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    public static final String BUSINESS_NAME="";

    @Resource
    private UserService userService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存
     * @param userDto
     * @return
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody UserDto userDto) {
        //对传进来的密码进行二次加密
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));

        //保存校验
            ValidatorUtil.require(userDto.getLoginName(), "登录名");
            ValidatorUtil.length(userDto.getLoginName(), "登录名", 1, 50);
            ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
            ValidatorUtil.require(userDto.getPassword(), "密码");

        ResponseDto responseDto = new ResponseDto();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        userService.delete(id);
        return responseDto;
    }



    /**
     * 重置密码
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }


    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto =  userService.login(userDto);
        request.getSession().setAttribute(Constants.LOGIN_USER,loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public ResponseDto login(HttpServletRequest request) {
        ResponseDto responseDto = new ResponseDto();
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return responseDto;
    }

}
