package com.zhisheng.MapperTest;

import com.zhisheng.mapper.DynamicSqlMapper;
import com.zhisheng.pojo.Course;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

/**
 * Created by 10412 on 2017/4/11.
 */
public class DynamicSqlMapperTest
{
    private SqlSessionFactory sqlSessionFactory;


    @Before
    public void setup() throws Exception
    {
        //创建sqlSessionFactory

        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";

        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂,传入Mybatis的配置文件信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void searchCourses() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DynamicSqlMapper dynamicSqlMapper = sqlSession.getMapper(DynamicSqlMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("tutorId", 1);
        map.put("courseName", "%Java%");
        map.put("startDate", new Date());
        List<Course> courses = dynamicSqlMapper.searchCourses(map);
        for (Course course : courses) {
            System.out.println(course);
        }
        sqlSession.close();
    }

    @Test
    public void searchCoursesByTutors() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DynamicSqlMapper dynamicSqlMapper = sqlSession.getMapper(DynamicSqlMapper.class);
        Map<String,Object> map = new HashMap<String,Object>();
        List<Integer> tutorIds = new ArrayList<Integer>();
        tutorIds.add(1);
        tutorIds.add(3);
        tutorIds.add(6);
        map.put("tutorIds", tutorIds);
        List<Course> courses = dynamicSqlMapper.searchCoursesByTutors(map);
        for (Course course : courses){
            System.out.println(course);
        }
    }

}