package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.CountDto;
import com.course.server.mapper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

        CourseExample courseExample = new CourseExample();
        ChapterExample chapterExample = new ChapterExample();
        SectionExample sectionExample = new SectionExample();
        MemberExample memberExample = new MemberExample();
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        CourseCommentExample courseCommentExample  =new CourseCommentExample();

        int allcourse = (int) courseMapper.countByExample(courseExample);  //总课程数
        int allChapter = (int) chapterMapper.countByExample(chapterExample); //总章节
        int allSection = (int)sectionMapper.countByExample(sectionExample); //总小节
        int allMember = (int)memberMapper.countByExample(memberExample); //总会员数
        int allEnroll = (int)memberCourseMapper.countByExample(memberCourseExample); // 总报名数
        int allComment =(int)courseCommentMapper.countByExample(courseCommentExample); //总评论数


        countDto.setAllCourse(allcourse);
        countDto.setAllChapter(allChapter);
        countDto.setAllSection(allSection);
        countDto.setAllMember(allMember);
        countDto.setAllEnroll(allEnroll);
        countDto.setAllComment(allComment);

//        LOG.info("课程总数为：{}",allcourse);

        return countDto;
    }
}
