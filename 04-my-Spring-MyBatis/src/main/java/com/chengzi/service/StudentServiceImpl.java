package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;

import java.io.IOException;

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
}
