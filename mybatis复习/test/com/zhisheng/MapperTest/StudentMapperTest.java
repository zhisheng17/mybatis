package com.zhisheng.MapperTest;

import com.zhisheng.mapper.StudentMapper;
import com.zhisheng.pojo.PhoneNumber;
import com.zhisheng.pojo.Student;
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
 * Created by 10412 on 2017/4/6.
 */
public class StudentMapperTest
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
    public void findAllStudents() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建StudentMapper对象,mybatis自动生成代理对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);


        List<Student> list = studentMapper.findAllStudents();

        System.out.println(list);
    }


    @Test
    public void findStudentById() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();


        //创建StudentMapper对象,mybatis自动生成代理对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);


        //调用StudentMapper的方法
        Student student = studentMapper.findStudentById(5);


        System.out.println(student);
    }


    @Test
    public void insertStudent() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建StudentMapper对象,mybatis自动生成代理对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student student1 = new Student();
        student1.setStudId(5);
        student1.setName("hah");
        student1.setEmail("1247545@gmail.com");
        student1.setDob(new Date());

        PhoneNumber pn = new PhoneNumber("0086", "0521", "1234");

        student1.setPn(pn);

        studentMapper.insertStudent(student1);

        sqlSession.commit();
        System.out.println("新增用户ID：" + student1.getStudId());

    }


    @Test
    public void deleteStudent() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建StudentMapper对象,mybatis自动生成代理对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        studentMapper.deleteStudent(1);
        sqlSession.commit();

    }


    @Test
    public void updateStudent() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建StudentMapper对象,mybatis自动生成代理对象
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);

        Student student2 = new Student();
        student2.setStudId(5);//需要修改的students的studId
        student2.setName("haha");
        student2.setEmail("987656@163.com");

        studentMapper.updateStudent(student2);
        sqlSession.commit();
       System.out.println("更过用户的ID：" + student2.getStudId());

    }


}