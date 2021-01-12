package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dto.PageDto;

import java.io.IOException;
import java.util.List;

public interface IStudentService {

    void addStudent(Student student) throws IOException;

    void deleteStudent(Integer id) throws IOException;

    void updateStudnent(Student student) throws IOException;

    List<Student> getAllStudnet() throws IOException;

    /**
     * 测试分页插件
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto pageHelper(Integer pageNo, Integer pageSize) throws IOException;
}
