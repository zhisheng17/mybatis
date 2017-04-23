package com.zhisheng.MapperTest;

import com.zhisheng.mapper.Many2ManyMapper;
import com.zhisheng.pojo.Course2;
import com.zhisheng.pojo.Student2;
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
public class Many2ManyMapperTest
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
    public void insertStudent() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        Student2 student2 = new Student2();
        student2.setId(3);
        student2.setName("徐光宪");
        student2.setGender("男");
        student2.setMajor("软件");
        student2.setGrade("大二");
        many2ManyMapper.insertStudent(student2);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void insertCourse() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        Course2 course2 = new Course2();
        course2.setId(3);
        course2.setCourse_name("mybatis");
        course2.setCourse_code("003");
        many2ManyMapper.insertCourse(course2);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void getStudentById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        Student2 student2 = many2ManyMapper.getStudentById(1);
        System.out.println(student2);
        sqlSession.close();
    }

    @Test
    public void getCourseById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        Course2 course2 = many2ManyMapper.getCourseById(1);
        System.out.println(course2);
        sqlSession.close();
    }

    @Test
    public void studentSelectCourse() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        Student2 student2 = many2ManyMapper.getStudentById(1);
        Course2 course2 = many2ManyMapper.getCourseById(1);
        many2ManyMapper.studentSelectCourse(student2, course2);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void getStudentByIdOnCondition() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        List<Student2> list = many2ManyMapper.getStudentByIdOnCondition(2);
        for (Student2 student2 : list) {
            System.out.println(student2);
        }
        sqlSession.close();
    }

    @Test
    public void getStudentByIdWithCourses() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Many2ManyMapper many2ManyMapper = sqlSession.getMapper(Many2ManyMapper.class);
        Student2 student2 = many2ManyMapper.getStudentByIdWithCourses(1);
        System.out.println(student2);
        sqlSession.close();
    }

}