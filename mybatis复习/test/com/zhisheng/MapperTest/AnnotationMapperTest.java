package com.zhisheng.MapperTest;

import com.zhisheng.annotation.AnnotationMapper;
import com.zhisheng.pojo.Person;
import com.zhisheng.pojo.Student;
import com.zhisheng.pojo.Tutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by 10412 on 2017/4/12.
 */
public class AnnotationMapperTest
{
    private SqlSessionFactory sqlSessionFactory;


    @Before
    public void setup() throws Exception {
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
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        Student student = new Student();
        student.setStudId(1);
        student.setName("tzs");
        student.setEmail("104128@sa.com");
        student.setDob(new Date());
        annotationMapper.insertStudent(student);
        System.out.println(student);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void insertPerson() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        Person person = new Person();
        person.setId(1);
        person.setName("田志声");
        person.setAge(30);
        annotationMapper.insertPerson(person);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updatePerson() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        Person person = new Person();
        person.setId(23);
        person.setAge(20);
        person.setName("haha");
        annotationMapper.updatePerson(person);
        System.out.println(person);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deletePerson() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        annotationMapper.deletePerson(23);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void findStudentById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        Student student = annotationMapper.findStudentById(5);
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void findAllStudents() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        List<Student> list = annotationMapper.findAllStudents();
        for (Student student : list) {
            System.out.println(student);
        }
        sqlSession.close();
    }

    //@One注解来使用嵌套select语句（Nested-Select）加载一对一关联查询数据
    /*@Test
    public void selectStudentWithAddress() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        Student student = annotationMapper.selectStudentWithAddress(5);
        System.out.println("Student:" + student);
        System.out.println("Address:" + student.getAddress());
        sqlSession.close();
    }*/

    @Test
    public void findTutorById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationMapper annotationMapper = sqlSession.getMapper(AnnotationMapper.class);
        Tutor tutor = annotationMapper.findTutorById(1);
        System.out.println(tutor);
        System.out.println("Courses"+tutor.getCourses());
        sqlSession.close();
    }
}