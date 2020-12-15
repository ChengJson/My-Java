package com.chengzi.service;

import com.chengzi.beans.Student;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 12:50
 */
public interface IStudentDao {

    //插入
    void insertStu(Student studnet) throws IOException;

    //主键自增插入
    void insertStudentCachId(Student student) throws IOException;

    //删除
    void deleteStudentById(int id) throws IOException;

    //改
    void updateStudent(Student student) throws IOException;

    //查询所有
    List<Student> selectAllStudents() throws IOException;

    //查询map
    Map<String,Student> selectStudentMap() throws IOException;

    //查询指定学生
    Student selectStudentById(int id) throws IOException;


    //根据姓名查询
    List<Student> selectStudentByName(String name) throws IOException;

    //查询参数封装为map
    List<Student> selectStudentByMap(Map<String,Object> map) throws IOException;

    //用别名或者resultMap
    Student selectStudentById2(int id) throws IOException;
}


