package com.course.server.service;

import com.course.server.domain.Member;
import com.course.server.domain.MemberExample;
import com.course.server.dto.MemberDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.MemberMapper;
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
import java.util.Date;
import java.util.List;

@Service
public class MemberService {

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

}
