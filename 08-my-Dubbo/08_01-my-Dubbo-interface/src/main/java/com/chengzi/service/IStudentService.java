package com.chengzi.service;

import com.chengzi.beans.Student;

import java.io.IOException;
import java.util.List;

public interface IStudentService {

    void addStudent(Student student) throws IOException;

    void deleteStudent(Integer id) throws IOException;

    void updateStudnent(Student student) throws IOException;

    List<Student> getAllStudnet() throws IOException;

}
