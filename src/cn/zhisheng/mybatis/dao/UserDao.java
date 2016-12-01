package cn.zhisheng.mybatis.dao;

import cn.zhisheng.mybatis.po.User;

import java.util.List;

/**
 * Created by 10412 on 2016/12/1.
 */
public interface UserDao    //dao接口，用户管理
{
    //根据id查询用户信息
    public User findUserById(int id) throws Exception;

    //添加用户信息
    public void insertUser(User user) throws Exception;

    //删除用户信息
    public void deleteUser(int id) throws Exception;

    //根据名称查询用户信息
    public List<User> findUserByname(String username) throws Exception;
}
