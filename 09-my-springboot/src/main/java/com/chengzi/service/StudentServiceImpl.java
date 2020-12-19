package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentDao;

    /**
     *
     * @param studentDao
     */
    public void setStudentDao(StudentMapper studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * 增
     * @param student
     * @throws IOException
     */
    @Override
    @Transactional
    public void addStudent(Student student) throws IOException {

        studentDao.insert(student);

        int i = 10 / 0;

    }


    /**
     * 删
     * @param id
     * @throws IOException
     */
    @Override
    public void deleteStudent(Long id) throws IOException {
        studentDao.deleteByPrimaryKey( id );
    }

    /*改
     */
    @Override
    public void updateStudnent(Student student) throws IOException {
        studentDao.updateByPrimaryKeySelective(student) ;
    }


    /**
     * 查
     * @return
     * @throws IOException
     */
    @Override
    public List<Student> getAllStudnet() throws IOException {
        List<Student> students = new ArrayList<>();
        return students;
    }
}
