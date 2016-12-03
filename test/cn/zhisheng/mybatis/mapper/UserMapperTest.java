package cn.zhisheng.mybatis.mapper;

import cn.zhisheng.mybatis.po.User;
import cn.zhisheng.mybatis.po.UserCustom;
import cn.zhisheng.mybatis.po.UserQueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10412 on 2016/12/2.
 */
public class UserMapperTest
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
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //调用UserMapper的方法
        User user = userMapper.findUserById(1);


        System.out.println(user);
    }

    @Test
    public void testFindUserByUsername() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //调用UserMapper的方法
        List<User> list = userMapper.findUserByUsername("张小明");

        sqlSession.close();

        System.out.println(list);
    }

    //测试用户信息综合查询
    @Test
    public void testFindUserList() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        //因为设置了动态的sql，如果不设置某个值，那么条件就不会拼接在sql上
        userCustom.setSex("男");
        //userCustom.setUsername("张小明");

        //传入多个id
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(10);
        ids.add(16);
        userQueryVo.setIds(ids);
        userQueryVo.setUserCustom(userCustom);

        //调用UserMapper的方法
        List<UserCustom> list = userMapper.findUserList(userQueryVo);

        System.out.println(list);
    }



    //测试用户信息综合查询总数
    @Test
    public void testFindUserCount() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //创建包装对象，设置查询条件
        UserQueryVo userQueryVo = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("男");
        userCustom.setUsername("张小明");
        userQueryVo.setUserCustom(userCustom);

        //调用UserMapper的方法

        System.out.println(userMapper.findUserCount(userQueryVo));
    }


    //测试根据id查询用户信息，使用 resultMap 输出
    @Test
    public void testFindUserByIdResultMap() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建usermapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //调用UserMapper的方法
        User user = userMapper.findUserByIdResultMap(1);


        System.out.println(user);
    }

}
