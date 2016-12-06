package cn.zhisheng.mybatis.mapper;

import cn.zhisheng.mybatis.po.Orders;
import cn.zhisheng.mybatis.po.OrdersCustom;
import cn.zhisheng.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 10412 on 2016/12/4.
 */
public class OrdersMapperCustomTest
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
    public void testFindOrdersUser() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        //调用OrdersMapperCustom的方法
         List<OrdersCustom> list = ordersMapperCustom.findOrdersUser();


        System.out.println(list);

        sqlSession.close();
    }



    @Test
    public void testFindOrdersUserResultMap() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        //调用OrdersMapperCustom的方法
        List<Orders> list = ordersMapperCustom.findOrdersUserResultMap();


        System.out.println(list);

        sqlSession.close();
    }

    @Test
    public void testFindOrdersAndOrderDetailResultMap() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        //调用OrdersMapperCustom的方法
        List<Orders> list = ordersMapperCustom.findOrdersAndOrderDetailResultMap();


        System.out.println(list);

        sqlSession.close();
    }

    @Test
    public void testFindUserAndItemsResultMap() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        //调用OrdersMapperCustom的方法
        List<User> list = ordersMapperCustom.findUserAndItemsResultMap();


        System.out.println(list);

        sqlSession.close();
    }

    @Test
    public void testFindOrdersUserLazyLoading() throws Exception
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建OrdersMapperCustom对象,mybatis自动生成代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        //查询订单信息
        List<Orders> list = ordersMapperCustom.findOrdersUserLazyLoading();

        //遍历所查询的的订单信息
        for (Orders orders : list)
        {
            //查询用户信息
            User user = orders.getUser();
            System.out.println(user);
        }
        sqlSession.close();
    }

    //一级缓存测试
    @Test
    public void  testCache1() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建UserMapper对象,mybatis自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //查询使用的是同一个session
        //第一次发起请求，查询Id 为1的用户信息
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        //如果sqlSession去执行commit操作（执行插入、更新、删除），
        // 清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

        //更新user1的信息，
        user1.setUsername("李飞");
        user1.setSex("男");
        user1.setAddress("北京");
        userMapper.updateUserById(user1);
        //提交事务,才会去清空缓存
        sqlSession.commit();

        //第二次发起请求，查询Id 为1的用户信息
        User user2 = userMapper.findUserById(1);

        System.out.println(user2);

        sqlSession.close();
    }


    //二级缓存测试
    @Test
    public void testCache2() throws Exception
    {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();


        //创建UserMapper对象,mybatis自动生成代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        //sqlSession1 执行查询 写入缓存(第一次查询请求)
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);
        //这里执行关闭操作，将sqlsession中的数据写入到二级缓存区域
        sqlSession1.close();


        //sqlSession3  执行提交  清空缓存
        /*UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
        User user3 = userMapper3.findUserById(1);
        user3.setSex("女");
        user3.setAddress("山东济南");
        user3.setUsername("崔建");
        userMapper3.updateUserById(user3);
        //提交事务，清空缓存
        sqlSession3.commit();
        sqlSession3.close();*/

        //sqlSession2 执行查询(第二次查询请求)
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.findUserById(1);
        System.out.println(user2);
        sqlSession2.close();



   }
}
