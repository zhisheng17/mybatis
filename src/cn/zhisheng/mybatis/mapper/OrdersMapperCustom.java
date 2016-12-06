package cn.zhisheng.mybatis.mapper;

import cn.zhisheng.mybatis.po.Orders;
import cn.zhisheng.mybatis.po.OrdersCustom;
import cn.zhisheng.mybatis.po.User;

import java.util.List;

/**
 * Created by 10412 on 2016/12/4.
 */
public interface OrdersMapperCustom
{

    //查询订单关联查询用户信息
    public List<OrdersCustom> findOrdersUser() throws Exception;

    //查询订单关联查询用户信息
    public List<Orders> findOrdersUserResultMap() throws Exception;

    //查询订单及订单明细信息
    public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;

    //查询用户及用户购买商品信息
    public List<User> findUserAndItemsResultMap() throws  Exception;

    //查询订单并且关联查询用户信息 关联用户信息用延迟加载
    public List<Orders> findOrdersUserLazyLoading() throws Exception;
}
