package com.chengzi.test;

import com.chengzi.beans.Student;
import com.chengzi.service.IStudentDao;
import com.chengzi.service.StudentDaoImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 12:53
 */
public class MyTest {
    IStudentDao iStudentDao;

    @Before
    public void before(){
        iStudentDao = new StudentDaoImpl();
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() throws IOException {
        iStudentDao.insertStu(new Student(1,"chengzi",21,1.0));
    }
    /**
     * 插入后获取主键id  注意oracle是序列，mysql是主键自增
     */
    @Test
    public void testInsertStudentCachId() throws IOException {
        //这里的id不起作用
        Student chengzi = new Student(3, "chengzi", 21, 1.0);
        iStudentDao.insertStudentCachId(chengzi);
        System.out.println(chengzi.getId());
    }

    /**
     * 删除
     * @throws IOException
     */
    @Test
    public void testDeleteStudentById() throws IOException {
        iStudentDao.deleteStudentById(5);
    }

    /**
     * 修改
     * @throws IOException
     */
    @Test
    public void testUpdateStudent() throws IOException {
        Student chengzi = new Student(10, "chengziupdate", 21, 1.0);
        iStudentDao.updateStudent(chengzi);
    }

    /**
     * 查所有
     */
    @Test
    public void testSelectAllStudents() throws IOException {
        List<Student> students = iStudentDao.selectAllStudents();
        students.forEach(s -> System.out.println(s));

    }

    /**
     * 查询结果封装为map k->Student
     */
    @Test
    public void testselectStudentMap() throws IOException {
        Map<String, Student> map = iStudentDao.selectStudentMap();
        map.forEach((k , v) -> System.out.println(k + "->" + v));
    }

    /**
     * 查询单个
     * @throws IOException
     */
    @Test
    public void testSelectStudentById() throws IOException {
        Student student = iStudentDao.selectStudentById(3);
        System.out.println(student);
    }

    /**
     * 模糊查询
     */
    @Test
    public void testSelectStudentByName() throws IOException {
        List<Student> list = iStudentDao.selectStudentByName("n");
        list.forEach(s -> System.out.println(s));
    }

    @Test
    public void testSelectStudentByMap() throws IOException {
        Student student = new Student();
        student.setId(5);
        Map<String, Object> map = new HashMap<>();
        map.put("studentId",2);
        map.put("stu",student);
        List<Student> students = iStudentDao.selectStudentByMap(map);
        students.forEach(s -> System.out.println(s));
    }

    @Test
    public void testSelectStudentById2() throws IOException {
        Student student = iStudentDao.selectStudentById2(3);
        System.out.println(student);
    }


}

