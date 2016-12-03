package cn.zhisheng.mybatis.po;

import java.util.List;

/**
 * Created by 10412 on 2016/12/3.
 */
public class UserQueryVo    //用户包装类型
{
    //在这里包装所需要的查询条件

    //传入多个id
    private List<Integer> ids;

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

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

//还可以包装其他的查询条件，比如订单、商品

}
