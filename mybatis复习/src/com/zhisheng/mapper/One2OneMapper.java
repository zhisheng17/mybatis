package com.zhisheng.mapper;

import com.zhisheng.pojo.Student;

/**
 * Created by 10412 on 2017/4/11.
 */
public interface One2OneMapper
{
    Student findStudentWithAddress(int id);
}
