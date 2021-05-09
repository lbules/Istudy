package com.course.server.service;

import com.course.server.domain.Member;
import com.course.server.domain.MemberExample;
import com.course.server.dto.CheckMemberDto;
import com.course.server.dto.LoginMemberDto;
import com.course.server.dto.MemberDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.MemberMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        MemberExample memberExample = new MemberExample();
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<MemberDto> memberDtoList = new ArrayList<MemberDto>(); //将查到到所有数据转换成memberDto
        for (int i = 0; i <memberList.size(); i++) {
            Member member = memberList.get(i);
            MemberDto memberDto = new MemberDto();
            BeanUtils.copyProperties(member,memberDto);
            memberDtoList.add(memberDto);
        }
        pageDto.setList(memberDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param memberDto
     */
    public void save(MemberDto memberDto) {
        Member member = CopyUtil.copy(memberDto, Member.class);
        if (StringUtils.isEmpty(memberDto.getId())){ //id为空则调用insert方法添加
            this.insert(member);
        }
        else { //id不为空
            this.update(member);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(Member member) {
        Date now = new Date();

        member.setId(UuidUtil.getShortUuid());
        //注册时间
        member.setRegisterTime(now);
        memberMapper.insert(member);
    }

    /**
     * 更新
     * @param member
     */
    private void update(Member member) {

        memberMapper.updateByPrimaryKey(member);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据手机号查找对应的会员信息
     * @param mobile
     * @return
     */
    public MemberDto findByMobile(String mobile) {
        Member member = this.selectByMobile(mobile);
        return CopyUtil.copy(member, MemberDto.class);
    }

    /**
     * 登录时校验否有该手机号
     * @param mobile
     * @return
     */
    public Member selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }

    /**
     * 登录
     * @param memberDto
     */
    public LoginMemberDto login(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            LOG.info("手机号不存在, {}", memberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                // 登录成功
                LoginMemberDto loginMemberDto = CopyUtil.copy(member, LoginMemberDto.class);
                return loginMemberDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
            }
        }
    }

    /**
     * 重置密码
     */
    public void resetPassword(MemberDto memberDto) throws BusinessException {
        Member memberDb = this.selectByMobile(memberDto.getMobile());
        if (memberDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            Member member = new Member();
            member.setId(memberDb.getId());
            member.setPassword(memberDto.getNewPassword());
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }

    /*
    * 查找会员信息
    * */
    public Member findMember(String memberId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andIdEqualTo(memberId);
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }
    }

    public MemberDto checkPassword(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            LOG.info("手机号不存在, {}", memberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getOldPassword())) {
                //校验成功
                LOG.info("旧密码：{}, 数据库密码：{}", memberDto.getOldPassword(), member.getPassword());
                MemberDto MemberDto = CopyUtil.copy(member,MemberDto.class);
                return MemberDto;
            } else {
                LOG.info("旧密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getOldPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
            }
        }
    }
}
