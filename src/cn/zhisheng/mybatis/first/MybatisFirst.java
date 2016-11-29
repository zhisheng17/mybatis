package cn.zhisheng.mybatis.first;

import cn.zhisheng.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 10412 on 2016/11/28.
 */
public class MybatisFirst
{
    //根据id查询用户信息，得到用户的一条记录
    @Test
    public void findUserByIdTest() throws IOException
    {
        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";

        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂,传入Mybatis的配置文件信息
        SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //通过SqlSession操作数据库
        //第一个参数：映射文件中Statement的id，等于 = namespace + "." + Statement的id
        //第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        //sqlSession.selectOne 结果与映射文件中所匹配的resultType类型的对象
        //selectOne 查询结果为一条
        User user =  sqlSession.selectOne("test.findUserById", 1);

        System.out.println(user);

        //释放资源
        sqlSession.close();
    }


    //根据用户名称模糊查询用户信息列表
    @Test
    public void findUserByUsernameTest() throws IOException
    {
        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";

        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂,传入Mybatis的配置文件信息
        SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //通过SqlSession操作数据库
        //第一个参数：映射文件中Statement的id，等于 = namespace + "." + Statement的id
        //第二个参数：指定和映射文件中所匹配的parameterType类型的参数

        //selectList 查询结果可能多条
        //list中的user和映射文件中resultType所指定的类型一致
        List<User> list = sqlSession.selectList("test.findUserByUsername", "小明");

        System.out.println(list);

        //释放资源
        sqlSession.close();
    }


    //添加用户
    @Test
    public void insetrUser() throws IOException, ParseException {
        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";

        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂,传入Mybatis的配置文件信息
        SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();

        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");


        user.setUsername("田志声");
        user.setSex("男");
        user.setBirthday(sdf.parse("2016-11-29"));
        user.setAddress("江西南昌");

        sqlSession.insert("test.insetrUser", user);
        sqlSession.commit();


        //释放资源
        sqlSession.close();

    }
}
