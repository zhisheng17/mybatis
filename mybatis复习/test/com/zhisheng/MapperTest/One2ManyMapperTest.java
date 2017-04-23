package com.zhisheng.MapperTest;

import com.zhisheng.mapper.One2ManyMapper;
import com.zhisheng.pojo.Course;
import com.zhisheng.pojo.Tutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 10412 on 2017/4/11.
 */
public class One2ManyMapperTest
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
    public void findTutorById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        One2ManyMapper one2ManyMapper = sqlSession.getMapper(One2ManyMapper.class);
        /*第一种方法
        Tutor tutor = one2ManyMapper.findTutorById(1);
        System.out.println(tutor);*/

        //第二种方法
        Tutor tutor = one2ManyMapper.findTutorById(1);
        System.out.println(tutor);
        List<Course> courses = tutor.getCourses();
        for (Course course : courses){
            System.out.println(course);
        }
        sqlSession.close();
    }
}