package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.util.MybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 12:50
 */
public class StudentDaoImpl implements IStudentDao {

    private SqlSession sqlSession;


    public void insertStu(Student student) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        //4、执行插入
        this.sqlSession.insert("insertStudent", student);
        //5、事务提交
        this.sqlSession.commit();
    }

    @Override
    public void insertStudentCachId(Student student) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        //4、执行插入
        this.sqlSession.insert("insertStudentCachId", student);
        //5、事务提交
        this.sqlSession.commit();
    }

    @Override
    public void deleteStudentById(int id) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        this.sqlSession.insert("deleteStudentById", id);
        this.sqlSession.commit();
    }


    @Override
    public void updateStudent(Student student) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        this.sqlSession.insert("updateStudent", student);
        this.sqlSession.commit();
    }


    @Override
    public List<Student> selectAllStudents() throws IOException {
        List<Student> list = null;
        sqlSession = MybatisUtils.getSqlSession();
        list = this.sqlSession.selectList("selectAllStudents");
        return list;
    }


    @Override
    public Map<String, Student> selectStudentMap() throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        Map<String, Student> map = this.sqlSession.selectMap("selectStudentMap", "name");
        return map;
    }

    @Override
    public Student selectStudentById(int id) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        Student student = this.sqlSession.selectOne("selectStudentById", id);
        return student;
    }

    @Override
    public List<Student> selectStudentByName(String name) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        List<Student> list = this.sqlSession.selectList("selectStudentByName", name);
        return list;
    }

    @Override
    public List<Student> selectStudentByMap(Map<String, Object> map) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        List<Student> list = this.sqlSession.selectList("selectStudentByMap", map);
        return list;
    }

    @Override
    public Student selectStudentById2(int id) throws IOException {
        sqlSession = MybatisUtils.getSqlSession();
        Student student = this.sqlSession.selectOne("selectStudentById2",id);
        return student;
    }
}


