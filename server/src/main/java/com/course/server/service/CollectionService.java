package com.course.server.service;

import com.course.server.domain.Collection;
import com.course.server.domain.CollectionExample;
import com.course.server.domain.Course;
import com.course.server.dto.CollectionDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CollectionMapper;
import com.course.server.mapper.CourseMapper;
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
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectionService {

    @Resource
    private CollectionMapper collectionMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    @Resource
    private CourseMapper courseMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());

        CollectionExample collectionExample = new CollectionExample();
        List<Collection> collectionList = collectionMapper.selectByExample(collectionExample);
        PageInfo<Collection> pageInfo = new PageInfo<>(collectionList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

        List<CollectionDto> collectionDtoList = new ArrayList<CollectionDto>(); //将查到到所有数据转换成collectionDto
        for (int i = 0; i < collectionList.size(); i++) {
            Collection collection = collectionList.get(i);
            CollectionDto collectionDto = new CollectionDto();
            BeanUtils.copyProperties(collection, collectionDto);
            collectionDtoList.add(collectionDto);
        }
        pageDto.setList(collectionDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     *
     * @param collectionDto
     */
    public void save(CollectionDto collectionDto) {
        Collection collection = CopyUtil.copy(collectionDto, Collection.class);
        if (StringUtils.isEmpty(collectionDto.getId())) { //id为空则调用insert方法添加
            this.insert(collection);
        } else { //id不为空
            this.update(collection);
        }
    }

    /**
     * 新增
     *
     * @param
     */
    private void insert(Collection collection) {
        Date now = new Date();
        collection.setCollectionTime(now);
        collection.setId(UuidUtil.getShortUuid());
        collectionMapper.insert(collection);
    }

    /**
     * 更新
     *
     * @param collection
     */
    private void update(Collection collection) {

        collectionMapper.updateByPrimaryKey(collection);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(String id) {
        collectionMapper.deleteByPrimaryKey(id);
    }


    public CollectionDto ifCollection(CollectionDto collectionDto) {
        Collection collection = this.select(collectionDto.getMemberId(),collectionDto.getCourseId());
        return CopyUtil.copy(collection,CollectionDto.class);
    }

    /**
     * 根据memberId和courseId查询记录
     */
    public Collection select(String memberId, String courseId) {
        CollectionExample example = new CollectionExample();
        example.createCriteria().andCourseIdEqualTo(courseId).andMemberIdEqualTo(memberId);
        List<Collection> collectionList = collectionMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(collectionList)) {
            return null;
        } else {
            return collectionList.get(0);
        }
    }

    //取消收藏
    public CollectionDto delectCollection(CollectionDto collectionDto) {
        //删除收藏记录
        CollectionExample example = new CollectionExample();
        example.createCriteria()
                .andCourseIdEqualTo(collectionDto.getCourseId())
                .andMemberIdEqualTo(collectionDto.getMemberId());
        collectionMapper.deleteByExample(example);
        //返回删除结果
        Collection collection = this.select(collectionDto.getMemberId(),collectionDto.getCourseId());
        return CopyUtil.copy(collection,CollectionDto.class);
    }


    /**
     * 查找收藏的课程
     * @param  memberId 会员id
     * @return
     */
    public List<CourseDto> listCollection(String memberId) {
        //查找出收藏课程对应的课程id，去收藏表查
        List<String> courseId = myCourseMapper.listCollection(memberId);
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
