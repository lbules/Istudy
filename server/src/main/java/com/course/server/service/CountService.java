package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.CountDto;
import com.course.server.mapper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class CountService {

    private static final Logger LOG = LoggerFactory.getLogger(CountService.class);

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private MemberCourseMapper memberCourseMapper;

    @Resource
    private CourseCommentMapper courseCommentMapper;


    public CountDto allCount() {
        CountDto countDto = new CountDto();
//        Date now = new Date();
        SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();

//      当天
        Date now =calendar.getTime();
        LOG.info("当前时间为：{}",now);

        //三天前
        calendar.add(Calendar.DATE,-7);
        Date before =calendar.getTime();
        LOG.info("当前时间为：{}",before);

        CourseExample courseExample = new CourseExample();
        ChapterExample chapterExample = new ChapterExample();
        SectionExample sectionExample = new SectionExample();
        MemberExample memberExample = new MemberExample();
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        CourseCommentExample courseCommentExample  =new CourseCommentExample();

        //七日内新增的评论
        CourseCommentExample newCommentExample  =new CourseCommentExample();
        newCommentExample.createCriteria().andCreateTimeBetween(before,now);

        //七日内新增的会员
        MemberExample newMemberExample = new MemberExample();
        newMemberExample.createCriteria().andRegisterTimeBetween(before,now);

        //七日内新增的报名人数
        MemberCourseExample newMemberCourseExample = new MemberCourseExample();
        newMemberCourseExample.createCriteria().andAtBetween(before,now);


        int allcourse = (int) courseMapper.countByExample(courseExample);  //总课程数
        int allChapter = (int) chapterMapper.countByExample(chapterExample); //总章节
        int allSection = (int)sectionMapper.countByExample(sectionExample); //总小节
        int allMember = (int)memberMapper.countByExample(memberExample); //总会员数
        int allEnroll = (int)memberCourseMapper.countByExample(memberCourseExample); // 总报名数
        int allComment =(int)courseCommentMapper.countByExample(courseCommentExample); //总评论数
        int newComment = (int)courseCommentMapper.countByExample(newCommentExample);
        int newMember =(int)memberMapper.countByExample(newMemberExample);
        int newEnroll = (int)memberCourseMapper.countByExample(newMemberCourseExample);


        countDto.setAllCourse(allcourse);
        countDto.setAllChapter(allChapter);
        countDto.setAllSection(allSection);
        countDto.setAllMember(allMember);
        countDto.setAllEnroll(allEnroll);
        countDto.setAllComment(allComment);
        countDto.setNewComment(newComment);
        countDto.setNewMember(newMember);
        countDto.setNewEnroll(newEnroll);


        return countDto;
    }

    public ArrayList memberAnalyse() {
        ArrayList memberArray = new ArrayList();
        //获取现在的时间
        Date now  = new Date();

//         new Date(now.getTime()-2*24*60*60*1000);

        LOG.info("减去两天的时间为：{}",new Date(now.getTime()-2*24*60*60*1000));
        //设置当前时间为起始时间点
        /*Calendar calendar  = Calendar.getInstance();
        calendar.setTime(now);*/

        //循环获取最近30天，每天的注册会员人数

        for (int i=0;i<30;i++) {
            //时间间隔为1天
            MemberExample memberExample = new MemberExample();
            Date pre = new Date(now.getTime()-i*24*60*60*1000);
            Date next = new Date(now.getTime()-(i+1)*24*60*60*1000);
            memberExample.createCriteria().andRegisterTimeBetween(next,pre);
            memberArray.add(memberMapper.countByExample(memberExample));
        }
        return memberArray;
    }

    //最近30天报名人数数据
    public ArrayList courseAnalyse() {
        ArrayList memberArray = new ArrayList();
        //获取现在的时间
        Date now  = new Date();

        for (int i=0;i<30;i++) {
            //时间间隔为1天
            MemberCourseExample memberCourseExample = new MemberCourseExample();
            Date pre = new Date(now.getTime()-i*24*60*60*1000);
            Date next = new Date(now.getTime()-(i+1)*24*60*60*1000);
            memberCourseExample.createCriteria().andAtBetween(next,pre);
            memberArray.add(memberCourseMapper.countByExample(memberCourseExample));
        }
        return memberArray;
    }
}
