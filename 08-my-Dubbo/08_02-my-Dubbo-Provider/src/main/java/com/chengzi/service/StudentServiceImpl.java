package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private static Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private IStudentDao studentDao;

    /**
     *
     * @param studentDao
     */
    public void setStudentDao(IStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * 增
     * @param student
     * @throws IOException
     */
    @Override
    public void addStudent(Student student) throws IOException {
        studentDao.insertStudent(student);
        new com.chengzi.beans.Student();
    }


    /**
     * 删
     * @param id
     * @throws IOException
     */
    @Override
    public void deleteStudent(Integer id) throws IOException {
        studentDao.deleteStudentById( id );
    }

    /*改
     */
    @Override
    public void updateStudnent(Student student) throws IOException {
        studentDao.updateStudent(student) ;
    }


    /**
     * 查
     * @return
     * @throws IOException
     */
    @Override
    public List<Student> getAllStudnet() throws IOException {
        List<Student> students = studentDao.selectAllStudents();
        LOG.info("服务提供者1被调用");
        return students;
    }
}
