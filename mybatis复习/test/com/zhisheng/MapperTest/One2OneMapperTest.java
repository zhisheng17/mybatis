package com.zhisheng.MapperTest;

import com.zhisheng.mapper.One2OneMapper;
import com.zhisheng.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by 10412 on 2017/4/11.
 */
public class One2OneMapperTest
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
    public void findStudentWithAddress() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        One2OneMapper one2OneMapper = sqlSession.getMapper(One2OneMapper.class);

        Student student = one2OneMapper.findStudentWithAddress(2);
        System.out.println(student);
        sqlSession.close();
    }
}