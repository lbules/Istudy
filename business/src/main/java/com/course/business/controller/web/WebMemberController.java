package com.course.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Member;
import com.course.server.dto.*;
import com.course.server.exception.BusinessException;
import com.course.server.service.MemberService;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/web/member")
public class WebMemberController {

    private static final Logger LOG = LoggerFactory.getLogger(WebMemberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/register")
    public ResponseDto register(@RequestBody MemberDto memberDto) {
        // 保存校验
        ValidatorUtil.require(memberDto.getMobile(), "手机号");
        ValidatorUtil.length(memberDto.getMobile(), "手机号", 11, 11);
        ValidatorUtil.require(memberDto.getPassword(), "密码");
        ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);
        ValidatorUtil.length(memberDto.getPhoto(), "头像url", 1, 200);

        // 密码加密
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        memberService.save(memberDto);
        responseDto.setContent(memberDto);
        return responseDto;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody MemberDto memberDto) {
        LOG.info("用户登录开始");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        String imageCode = (String) redisTemplate.opsForValue().get(memberDto.getImageCodeToken());
        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(memberDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            LOG.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
            redisTemplate.delete(memberDto.getImageCodeToken());
        }

        LoginMemberDto loginMemberDto = memberService.login(memberDto);
        String token = UuidUtil.getShortUuid();
        loginMemberDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginMemberDto), 3600, TimeUnit.SECONDS);
        responseDto.setContent(loginMemberDto);
        return responseDto;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token) {
        ResponseDto responseDto = new ResponseDto();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token:{}", token);
        return responseDto;
    }

    /**
     * 校验手机号是否存在
     * 存在则success=true，不存在则success=false
     */
    @GetMapping(value = "/is-mobile-exist/{mobile}")
    public ResponseDto isMobileExist(@PathVariable(value = "mobile") String mobile) throws BusinessException {
        LOG.info("查询手机号是否存在开始");
        ResponseDto responseDto = new ResponseDto();
        MemberDto memberDto = memberService.findByMobile(mobile);
        if (memberDto == null) {
            responseDto.setSuccess(false);
        } else {
            responseDto.setSuccess(true);
        }
        return responseDto;
    }

    @PostMapping("/reset")
    public ResponseDto resetPassword(@RequestBody MemberDto memberDto) throws BusinessException {
        LOG.info("会员密码重置开始:");
        memberDto.setNewPassword(DigestUtils.md5DigestAsHex(memberDto.getNewPassword().getBytes()));
//        memberDto.setNewPassword(DigestUtils.md5DigestAsHex(memberDto.getNewPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        // 重置密码
        memberService.resetPassword(memberDto);

        return responseDto;
    }


    //    查找会员的信息
    @GetMapping("/findMember/{memberId}")
    public ResponseDto findCourse(@PathVariable String memberId) {
        LOG.info("查找会员信息开始：{}", memberId);
        ResponseDto responseDto = new ResponseDto();
        Member member = memberService.findMember(memberId);
        responseDto.setContent(member);

        LOG.info("查找会员信息结束：{}", responseDto);
        return responseDto;
    }


    //保存会员信息
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody MemberDto memberDto) {
        ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);

        ResponseDto responseDto = new ResponseDto();
        memberService.save(memberDto);
        responseDto.setContent(memberDto);
        return responseDto;

    }


    @PostMapping("/check")
    public ResponseDto checkPassword(@RequestBody MemberDto memberDto) {

        memberDto.setOldPassword(DigestUtils.md5DigestAsHex(memberDto.getOldPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        LOG.info("开始校验密码");
        MemberDto MemberDto = memberService.checkPassword(memberDto);
        responseDto.setContent(MemberDto);
        return responseDto;
    }


}
