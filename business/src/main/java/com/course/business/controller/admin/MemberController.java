package com.course.business.controller.admin;

import com.course.server.domain.Member;
import com.course.server.dto.MemberDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.MemberService;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/member")
public class MemberController {

    public static final String BUSINESS_NAME="";

    @Resource
    private MemberService memberService;

    //列表查询
    @RequestMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        memberService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody MemberDto memberDto) {
        //保存校验
        ValidatorUtil.require(memberDto.getMobile(),"手机号");
        ValidatorUtil.length(memberDto.getMobile(),"手机号",11,11);
        ValidatorUtil.require(memberDto.getPassword(),"密码");
        ValidatorUtil.length(memberDto.getName(),"昵称",1,50);
        ValidatorUtil.length(memberDto.getName(),"头像",1,200);
        //加密密码
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));

        ResponseDto responseDto = new ResponseDto();
        memberService.save(memberDto);
        responseDto.setContent(memberDto);
        return responseDto;

    }

}
