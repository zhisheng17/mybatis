package com.zhisheng.mapper;

import com.zhisheng.pojo.Student;

/**
 * Created by 10412 on 2017/4/10.
 */
public interface ExtendMapper
{
    Student findStudentById(int id);
    Student selectStudentWithAddress(int id);
}
