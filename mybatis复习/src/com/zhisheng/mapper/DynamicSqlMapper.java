package com.zhisheng.mapper;

import com.zhisheng.pojo.Course;

import java.util.List;
import java.util.Map;

/**
 * Created by 10412 on 2017/4/11.
 */
public interface DynamicSqlMapper
{
    List<Course> searchCourses(Map<String, Object> map);
    List<Course> searchCoursesByTutors(Map<String,Object> map);
}
