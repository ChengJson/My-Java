package com.chengzi.test;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;
import com.chengzi.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 14:53
 */
public class MyTestAuto {
    private IStudentDao iStudentDao;
    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        iStudentDao = sqlSession.getMapper(IStudentDao.class);
    }
    @After
    public void after() {
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() throws IOException {
        iStudentDao.insertStudent(new Student(2,"chengzi",21,1.0));
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

    /**Expected one result (or null) to be returned by selectOne(), but found: 2
     * 查询map k->Student
     */
    @Test
    public void testselectStudentMap() throws IOException {
        Map<String,Student> map = iStudentDao.selectStudentMap();
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

    /**
     * 参数为map
     * @throws IOException
     */
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

    /**
     * 别名或者resultMap
     * @throws IOException
     */
    @Test
    public void testSelectStudentById2() throws IOException {
        Student student = iStudentDao.selectStudentById2(3);
        System.out.println(student);
    }


    /**
     * 返回类型为List<Map></>
     * @throws IOException
     */
    @Test
    public void testselectStudentMapType(){
        List<Map<String, Object>> list = iStudentDao.selectStudentMapType();
        for (Map<String, Object> l : list) {
            Set<String> strings = l.keySet();
            for (String k : strings){
                System.out.println( k);
                System.out.println(l.get(k));
            }
        }
    }

    /**
     * 多查询条件无法整体接收问题的解决
     */
    @Test
    public void testSelectStudentsByConditions() {
        List<Student> students = iStudentDao.selectStudentsByConditions("n",20);
    }

    @Test
    public void testSelectStudentsIf() {
        Student student = new Student(1,"name",22,95);
        List<Student> students = iStudentDao.selectStudentIf(student);
        System.out.println(students);
    }

    @Test
    public void testSelectStudentsWhere() {
        Student student = new Student(1,"name",22,95);
        Student students = iStudentDao.selectStudentWhere(student);
        System.out.println(students);
    }

    @Test
    public void testSelectStudentChoose() {
        Student student = new Student(1,"name",22,95);
        List<Student> students = iStudentDao.selectStudentChoose(student);
        System.out.println(students);
    }

    @Test
    public void testSelectStudentForEachArray() {
        Object [] studentIds = new Object[] {1,3};
        List<Student> students = iStudentDao.selectStudentForEachArray(studentIds);
        System.out.println(students);
    }


    @Test
    public void testSelectStudentForEachList() {
        List<Integer> studentIds = new ArrayList<>();
        studentIds.add(1);
        studentIds.add(3);
        List<Student> students = iStudentDao.selectStudentForEachList(studentIds);
        System.out.println(students);
    }

    @Test
    public void testSelectStudentBySqlFragment() {
        List<Student> studentIds = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        Student student2 = new Student();
        student2.setId(3);

        studentIds.add(student);
        studentIds.add(student2);
        List<Student> students = iStudentDao.selectStudentBySqlFragment(studentIds);
        System.out.println(students);
    }




}

