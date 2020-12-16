package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;

import java.io.IOException;
import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private IStudentDao studentDao;

    /**
     *
     * @param studentDao
     */
    public void setStudentDao(IStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void addStudent(Student student) throws IOException {
        studentDao.insertStudent(student);
    }

    @Override
    public List<Student> getAllStudnet() throws IOException {
        List<Student> students = studentDao.selectAllStudents();
        return students;
    }
}
