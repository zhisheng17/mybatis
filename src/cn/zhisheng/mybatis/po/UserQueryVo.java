package cn.zhisheng.mybatis.po;

/**
 * Created by 10412 on 2016/12/3.
 */
public class UserQueryVo    //用户包装类型
{
    //在这里包装所需要的查询条件

    //用户查询条件
    private UserCustom userCustom;

    public UserCustom getUserCustom()
    {
        return userCustom;
    }

    public void setUserCustom(UserCustom userCustom)
    {
        this.userCustom = userCustom;
    }

    //还可以包装其他的查询条件，比如订单、商品

}
