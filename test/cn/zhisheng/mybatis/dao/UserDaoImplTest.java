package cn.zhisheng.mybatis.dao;

import cn.zhisheng.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by 10412 on 2016/12/1.
 */
public class UserDaoImplTest
{
    private SqlSessionFactory sqlSessionFactory;


    //此方法是在 testFindUserById 方法之前执行的
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
    public void testFindUserById() throws Exception
    {
        //创建UserDao的对象
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);

        //调用UserDao方法
        User user = userDao.findUserById(1);

        System.out.println(user);
    }



}
