package com.course.server.service;

import com.course.server.domain.Course;
import com.course.server.domain.CourseContent;
import com.course.server.domain.CourseExample;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private SectionService sectionService;



    public void list(CoursePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseDto> courseDtoList = myCourseMapper.listCourse(pageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(courseDtoList);
    }


    /**
     * 保存章节,编辑保存时根据id是否为空
     *
     * @param courseDto
     */
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())) { //id为空则调用insert方法添加
            this.insert(course);
        } else { //id不为空
            this.update(course);
        }

        //批量保存课程对应的分类
        courseCategoryService.saveBatch(course.getId(), courseDto.getCategorys());
    }

    /**
     * 新增章节
     *
     * @param
     */
    private void insert(Course course) {
        Date now = new Date();
        course.setCreateAt(now);
        course.setUpdateAt(now);

        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 更新章节
     *
     * @param course
     */
    private void update(Course course) {
        course.setUpdateAt(new Date());

        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除章节
     *
     * @param id
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新课程的时长
     *
     * @param courseId
     */
    public void updateTime(String courseId) {
        myCourseMapper.updateTime(courseId);
    }


    /**
     * 查找课程内容
     */
    public CourseContentDto findContent(String id) {
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content == null) {
            return null;
        }
        return CopyUtil.copy(content, CourseContentDto.class);
    }


    /**
     * 保存课程内容，包含新增和修改
     */
    public int saveContent(CourseContentDto contentDto) {
        CourseContent content = CopyUtil.copy(contentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if (i == 0) {
            i = courseContentMapper.insert(content);
        }
        return i;
    }

    /**
     * 排序
     *
     * @param sortDto
     */
    @Transactional
    public void sort(SortDto sortDto) {
        // 修改当前记录的排序值
        myCourseMapper.updateSort(sortDto);

        //自身排序改变，其他的课程排序也应该做相应的更改
        // 如果排序值变大
        if (sortDto.getNewSort() > sortDto.getOldSort()) {
            myCourseMapper.moveSortsForward(sortDto);
        }

        // 如果排序值变小
        if (sortDto.getNewSort() < sortDto.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortDto);
        }
    }

    /**
     * 新课列表查询，只查询已发布的，按创建日期倒序
     */
    public List<CourseDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        //只有已发布的才会显示，课程有草稿和已发布两种状态
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        //时间倒叙排序
        courseExample.setOrderByClause("create_at desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, CourseDto.class);
    }

    /**
     * 新课列表查询，只查询已发布的，按创建日期倒序
     */
    public List<CourseDto> listHot(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        //只有已发布的才会显示，课程有草稿和已发布两种状态
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        //时间倒叙排序
        courseExample.setOrderByClause("enroll desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, CourseDto.class);
    }


    /**
     * 查找某一课程，包括该课程的大章、小节、讲师等信息，只能查已发布的
     * @param id
     * @return
     */
    public CourseDto findCourse(String id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())) {
            return null;
        }
        CourseDto courseDto = CopyUtil.copy(course, CourseDto.class);

        // 查询内容
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content != null) {
            courseDto.setContent(content.getContent());
        }

        // 查找讲师信息
        TeacherDto teacherDto = teacherService.findById(courseDto.getTeacherId());
        courseDto.setTeacher(teacherDto);

        // 查找章信息
        List<ChapterDto> chapterDtoList = chapterService.listByCourse(id);
        courseDto.setChapters(chapterDtoList);

        // 查找节信息
        List<SectionDto> sectionDtoList = sectionService.listByCourse(id);
        courseDto.setSections(sectionDtoList);

        return courseDto;
    }

    //查询最多人报名的五门课程
    public List<CourseDto> mostEnroll(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample CourseExample = new CourseExample();
        CourseExample.setOrderByClause("enroll desc");

        List<Course> courseList = courseMapper.selectByExample(CourseExample);
        return CopyUtil.copyList(courseList,CourseDto.class);

    }

    public List<CourseDto> search(String courseName) {
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andNameLike(courseName);
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList,CourseDto.class);
    }
}
