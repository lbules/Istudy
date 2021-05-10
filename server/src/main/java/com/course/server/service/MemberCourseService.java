package com.course.server.service;

import com.course.server.domain.Course;
import com.course.server.domain.MemberCourse;
import com.course.server.domain.MemberCourseExample;
import com.course.server.dto.CourseDto;
import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.MemberCourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberCourseService {

    @Resource
    private MemberCourseMapper memberCourseMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        MemberCourseExample memberCourseExample = new MemberCourseExample();
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);
        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<MemberCourseDto> memberCourseDtoList = new ArrayList<MemberCourseDto>(); //将查到到所有数据转换成memberCourseDto
        for (int i = 0; i <memberCourseList.size(); i++) {
            MemberCourse memberCourse = memberCourseList.get(i);
            MemberCourseDto memberCourseDto = new MemberCourseDto();
            BeanUtils.copyProperties(memberCourse,memberCourseDto);
            memberCourseDtoList.add(memberCourseDto);
        }
        pageDto.setList(memberCourseDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param memberCourseDto
     */
    public void save(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
        if (StringUtils.isEmpty(memberCourseDto.getId())){ //id为空则调用insert方法添加
            this.insert(memberCourse);
        }
        else { //id不为空
            this.update(memberCourse);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(MemberCourse memberCourse) {
        Date now = new Date();
        memberCourse.setId(UuidUtil.getShortUuid());
        memberCourse.setAt(now);
        memberCourseMapper.insert(memberCourse);
    }

    /**
     * 更新
     * @param memberCourse
     */
    private void update(MemberCourse memberCourse) {

        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }


    /**
     * 报名，先判断是否已报名
     * @param memberCourseDto
     */
    public MemberCourseDto enroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourseDb = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        //未报名
        if (memberCourseDb == null) {
            MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
            //添加报名信息
            this.insert(memberCourse);
            //对应课程报名人数加1
            courseMapper.increase(memberCourseDto.getCourseId());
            // 将数据库信息全部返回，包括id, at
            return CopyUtil.copy(memberCourse, MemberCourseDto.class);
        } else {
            // 如果已经报名，则直接返回已报名的信息
            return CopyUtil.copy(memberCourseDb, MemberCourseDto.class);
        }
    }

    /**
     * 根据memberId和courseId查询记录
     */
    public MemberCourse select(String memberId, String courseId) {
        MemberCourseExample example = new MemberCourseExample();
        example.createCriteria()
                .andCourseIdEqualTo(courseId)
                .andMemberIdEqualTo(memberId);
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberCourseList)) {
            return null;
        } else {
            return memberCourseList.get(0);
        }
    }

    /**
     * 获取报名信息
     */
    public MemberCourseDto getEnroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        return CopyUtil.copy(memberCourse, MemberCourseDto.class);
    }

    /*
    * 获取会员报名的课程信息
    * */
    public List<CourseDto> listMemberCourse(String memberId) {
        //查找出收藏课程对应的课程id，去收藏表查
        List<String> courseId = myCourseMapper.listMemberCourse(memberId);
        //根据课程id去查找课程详情
        List<CourseDto> courseDtoList = new ArrayList<>();

        for (int i=0;i<courseId.size();i++){
            Course course = courseMapper.selectByPrimaryKey(courseId.get(i));
            CourseDto courseDto = CopyUtil.copy(course,CourseDto.class);
            courseDtoList.add(courseDto);
        }

        return courseDtoList;
    }


}
