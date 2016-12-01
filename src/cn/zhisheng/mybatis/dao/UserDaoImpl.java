package cn.zhisheng.mybatis.dao;

import cn.zhisheng.mybatis.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by 10412 on 2016/12/1.
 */
public class UserDaoImpl  implements UserDao  //dao接口实现类
{

    //需要在 Dao 实现类中注入 SqlsessionFactory
    //这里通过构造方法注入
    private SqlSessionFactory sqlSessionFactory;
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory)
    {
        this.sqlSessionFactory = sqlSessionFactory;
    }



    @Override
    public User findUserById(int id) throws Exception
    {
        //在方法体内通过 SqlsessionFactory 创建 Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = sqlSession.selectOne("test.findUserById", id);

        sqlSession.close();

        return user;
    }

    @Override
    public void insertUser(User user) throws Exception
    {
        //在方法体内通过 SqlsessionFactory 创建 Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //执行插入的操作
        sqlSession.insert("test.insetrUser", user);

        //提交事务
        sqlSession.commit();


        //释放资源
        sqlSession.close();

    }

    @Override
    public void deleteUser(int id) throws Exception
    {

        //在方法体内通过 SqlsessionFactory 创建 Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("test.deleteUserById", id);

        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Override
    public List<User> findUserByname(String username) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> list = sqlSession.selectList("test.findUserByName", username);

        // 释放资源
        sqlSession.close();

        return list;
    }
}
