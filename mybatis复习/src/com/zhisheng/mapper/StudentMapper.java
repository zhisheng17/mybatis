package com.zhisheng.mapper;

import com.zhisheng.pojo.Student;

import java.util.List;

/**
 * Created by 10412 on 2017/4/6.
 */
public interface StudentMapper
{
    List<Student> findAllStudents() throws Exception;

    Student findStudentById(int id) throws  Exception;

    void insertStudent(Student s) throws Exception;

    void deleteStudent(int id) throws Exception;

    void updateStudent(Student s) throws Exception;
}
