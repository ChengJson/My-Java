package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

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
    @Transactional
    public void addStudent(Student student) throws IOException {

        studentDao.insertStudent(student);
        int i = 1 / 0;
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
        return students;
    }
}
