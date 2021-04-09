package com.course.server.service;

import com.course.server.domain.CourseContentFile;
import com.course.server.domain.CourseContentFileExample;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.mapper.CourseContentFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseContentFileService {

    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 列表查询
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        return CopyUtil.copyList(fileList, CourseContentFileDto.class);
    }

    /**
     * 保存
     * @param courseContentFileDto
     */
    public void save(CourseContentFileDto courseContentFileDto) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto, CourseContentFile.class);
        if (StringUtils.isEmpty(courseContentFileDto.getId())){ //id为空则调用insert方法添加
            this.insert(courseContentFile);
        }
        else { //id不为空
            this.update(courseContentFile);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(CourseContentFile courseContentFile) {

        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 更新
     * @param courseContentFile
     */
    private void update(CourseContentFile courseContentFile) {

        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }

}
