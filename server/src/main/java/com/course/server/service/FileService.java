package com.course.server.service;

import com.course.server.domain.File;
import com.course.server.domain.FileExample;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.FileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Resource
    private FileMapper fileMapper;

    public void list(PageDto pageDto) {

        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());

        FileExample fileExample = new FileExample();
        List<File> fileList = fileMapper.selectByExample(fileExample);
        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal()); //获取总记录行数

         List<FileDto> fileDtoList = new ArrayList<FileDto>(); //将查到到所有数据转换成fileDto
        for (int i = 0; i <fileList.size(); i++) {
            File file = fileList.get(i);
            FileDto fileDto = new FileDto();
            BeanUtils.copyProperties(file,fileDto);
            fileDtoList.add(fileDto);
        }
        pageDto.setList(fileDtoList); //将记录存放到pageDto的List
    }

    /**
     * 保存
     * @param fileDto
     */
    public void save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto, File.class);
        if (StringUtils.isEmpty(fileDto.getId())){ //id为空则调用insert方法添加
            this.insert(file);
        }
        else { //id不为空
            this.update(file);
        }
    }

    /**
     * 新增
     * @param
     */
    private void insert(File file) {
                Date now = new Date();
                file.setCreateAt(now);
                file.setUpdateAt(now);

        file.setId(UuidUtil.getShortUuid());
        fileMapper.insert(file);
    }

    /**
     * 更新
     * @param file
     */
    private void update(File file) {
                file.setUpdateAt(new Date());

        fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }

}
