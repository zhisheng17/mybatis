package cn.zhisheng.mybatis.mapper;

import cn.zhisheng.mybatis.po.OrdersCustom;
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
}
