package com.course.business.controller.admin;

import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理查询会员
 */
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

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        memberService.delete(id);
        return responseDto;
    }

}
