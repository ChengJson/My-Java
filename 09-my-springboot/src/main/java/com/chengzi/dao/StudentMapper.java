package com.chengzi.dao;

import com.chengzi.beans.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}