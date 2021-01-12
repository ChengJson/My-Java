package com.chengzi.service;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;
import com.chengzi.dto.PageDto;
import com.chengzi.util.PageUtil;
import com.github.pagehelper.PageHelper;
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

    /**
     * 测试分页插件
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageDto pageHelper(Integer pageNo, Integer pageSize) throws IOException {
        PageHelper.startPage(pageNo, pageSize);
        List<Student> students = studentDao.selectAllStudents();
        PageDto pageDto = PageUtil.getPageDto(students);
        return pageDto;
    }
}
