package com.chengzi.dao;

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
    void insertStudent(Student studnet) throws IOException;

    //主键自增插入
    void insertStudentCachId(Student student) throws IOException;

    //删除
    void deleteStudentById(int id) throws IOException;

    //改
    void updateStudent(Student student) throws IOException;

    //查询所有
    List<Student> selectAllStudents() throws IOException;

    //查询map
    Map<String, Student> selectStudentMap() throws IOException;

    //查询指定学生
    Student selectStudentById(int id) throws IOException;


    //根据姓名查询
    List<Student> selectStudentByName(String name) throws IOException;

    //查询参数封装为map
    List<Student> selectStudentByMap(Map<String, Object> map) throws IOException;

    //用别名或者resultMap
    Student selectStudentById2(int id) throws IOException;

    //ReturnType为Map
    List<Map<String, Object>> selectStudentMapType();

    //多查询条件无法整体接收问题的解决
    List<Student> selectStudentsByConditions(String n, int i);


    //测试if
    List<Student> selectStudentIf(Student student);

    //测试where
    Student selectStudentWhere(Student student);

    //choose标签
    List<Student> selectStudentChoose(Student student);

    //遍历Array
    List<Student> selectStudentForEachArray(Object[] studentIds);

    //遍历数组
    List<Student> selectStudentForEachList(List<Integer> studentIds);


    //sql片段
    List<Student> selectStudentBySqlFragment(List<Student> studentIds);


}


