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

    public List<OrdersCustom> findOrdersUser() throws Exception;

    public List<Orders> findOrdersUserResultMap() throws Exception;

    public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;

    public List<User> findUserAndItemsResultMap() throws  Exception;
}
