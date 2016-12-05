## Mybatis 高级知识

安排：对订单商品数据模型进行分析

1、高级映射：

+ 实现一对一查询、一对多、多对多查询
+ 延迟加载

2、查询缓存：

+ 一级缓存
+ 二级缓存

3、Mybatis 和 Spring 整合

4、逆向工程





## 1、订单商品数据模型

![](pic/sql.jpg)

### 数据模型分析思路：

1、每张表记录的数据内容（分模块对每张表记录的内容进行熟悉，相当于学习系统需求的过程）

2、每张表重要的的字段设置（非空字段、外键字段）

3、数据库级别表与表之间的关系（外键关系）

4、表与表业务之间的关系（要建立在每个业务意义的基础上去分析）

### 数据模型分析模型

+ 用户表 user：记录购买商品的用户信息
+ 订单表 order：记录用户所创建的订单(购买商品的订单)
+ 订单明细表 orderdetail：（记录了订单的详细信息即购买商品的信息）
+ 商品表 items：记录了商品信息

**表与表业务之间的关系**：

在分析表与表之间的业务关系时需要建立在某个业务意义基础上去分析。

先分析数据级别之间有关系的表之间的业务关系：

1、**usre和orders**：

user ---> orders：一个用户可以创建多个订单，一对多

orders  ---> user：一个订单只由一个用户创建，一对一

2、 **orders和orderdetail**：

orders  --->  orderdetail：一个订单可以包括 多个订单明细，因为一个订单可以购买多个商品，每个商品的购买信息在orderdetail记录，一对多关系

 orderdetail  --->  orders：一个订单明细只能包括在一个订单中，一对一

3、 **orderdetail 和 itesm**：

orderdetail --->  itesms：一个订单明细只对应一个商品信息，一对一

 items  --->   orderdetail:一个商品可以包括在多个订单明细 ，一对多

 再分析数据库级别没有关系的表之间是否有业务关系：

4、 **orders 和 items**：

orders 和 items 之间可以通过 orderdetail 表建立 关系。

![](pic/sqlmodel.jpg)



## 2、一对一查询

### 需求：

查询订单信息，关联查询创建订单的用户信息

### 使用 resultType

+ sql 语句

  确定查询的主表：订单表

  确定查询的关联表：用户表

  关联查询使用内链接？还是外链接？

  由于orders表中有一个外键（user_id），通过外键关联查询用户表只能查询出一条记录，可以使用内链接。

  ```sql
  SELECT orders.*, USER.username, USER.sex, USER.address FROM orders, USER WHERE orders.user_id = user.id
  ```

+ 创建 pojo

  `Orders.java`

  ```java
  public class Orders {
      private Integer id;
      private Integer userId;
      private String number;
      private Date createtime;
      private String note;
      //用户信息
      private User user;
      //订单明细
      private List<Orderdetail> orderdetails;
      //getter and setter
  }
  ```

  `OrderCustom.java`

  ```java
  //通过此类映射订单和用户查询的结果，让此类继承包括 字段较多的pojo类
  public class OrdersCustom extends Orders{
  	//添加用户属性
  	/*USER.username,
  	  USER.sex,
  	  USER.address */
  	private String username;
  	private String sex;
  	private String address;
  	//getter and setter	
  }
  ```

+ 映射文件 

  `OrdersMapperCustom.xml`

  ```xml
  <!--查询订单关联查询用户信息-->
      <select id="findOrdersUser" resultType="cn.zhisheng.mybatis.po.OrdersCustom">
          SELECT orders.*, USER.username, USER.sex, USER.address FROM orders, USER WHERE orders.user_id = user.id
      </select>
  ```

+ Mapper 文件

  `OrdersMapperCustom.java`

  ```java
  public interface OrdersMapperCustom
  {
      public OrdersCustom findOrdersUser() throws Exception;
  }
  ```

+ 测试代码（记得在 SqlConfig.xml中添加载 OrdersMapperCustom.xml 文件）

  ```java
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
  ```

+ 测试结果

  ![](pic/Test9.jpg)

  ​

  ​



### 使用 resultMap

+ sql 语句（和上面的一致）

+ 使用 resultMap 映射思路

  使用 resultMap 将查询结果中的订单信息映射到 Orders 对象中，在 orders 类中添加 User 属性，将关联查询出来的用户信息映射到 orders 对象中的 user 属性中。 

  ```java
   //用户信息
   private User user;
  ```

+ 映射文件

  `OrdersMapperCustom.xml`

  先定义 resultMap

  ```xml
  <!--定义查询订单关联查询用户信息的resultMap
          将整个查询结果映射到cn.zhisheng.mybatis.po.Orders
      -->
      <resultMap id="OrdersUserResultMap" type="cn.zhisheng.mybatis.po.Orders">
          <!--配置映射的订单信息-->
          <!--id表示查询结果中的唯一标识  在这里是订单的唯一标识  如果是由多列组成的唯一标识，那么就需要配置多个id
          column：id 是订单信息中的唯一标识列
          property：id 是订单信息唯一标识列所映射到orders中的id属性
          最终resultMap对column和property做一个映射关系（对应关系）
          -->
          <id column="id" property="id"/>
          <result column="user_id" property="userId"/>
          <result column="number" property="number"/>
          <result column="createtime" property="createtime"/>
          <result column="note" property="note"/>

          <!--配置映射的关联用户信息
              association 用于映射关联查询单个对象的信息
              property  将要关联查询的用户信息映射到 orders中的属性中去
          -->
          <association property="user" javaType="cn.zhisheng.mybatis.po.User">
              <!--id 关联用户信息的唯一标识
                  column: 指定唯一标识用户的信息
                  property：映射到user的那个属性
              -->
              <id column="user_id" property="id"/>
              <result column="username" property="username"/>
              <result column="sex" property="sex"/>
              <result column="address" property="address"/>
              <result column="birthday" property="birthday"/>
          </association>
      </resultMap>
  ```

  ```xml
  <!--查询订单关联查询用户信息, 使用 resultMap-->
      <select id="findOrdersUserResultMap" resultMap="OrdersUserResultMap">
          SELECT orders.*, USER.username, USER.sex, USER.address FROM orders, USER WHERE orders.user_id = user.id
      </select>
  ```

+ Mapper 文件

  ```java
   public List<Orders> findOrdersUserResultMap() throws Exception;
  ```

+ 测试代码

  ```java
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
  ```

+ 测试结果

  ![](pic/Test10.jpg)



### 使用 resultType 和 resultMap 一对一查询小结

+ resultType：使用resultType实现较为简单，如果pojo中没有包括查询出来的列名，需要增加列名对应的属性，即可完成映射。如果没有查询结果的特殊要求建议使用resultType。
+ resultMap：需要单独定义resultMap，实现有点麻烦，如果对查询结果有特殊的要求，使用resultMap可以完成将关联查询映射pojo的属性中。resultMap可以实现延迟加载，resultType无法实现延迟加载。



## 一对多查询

**需求**：查询订单及订单明细信息

**SQL语句**：

确定主查询表：订单表

确定关联查询表：订单明细表

在一对一查询基础上添加订单明细表关联即可。

```sql
SELECT orders.*, USER.username, USER.sex, USER.address, orderdetail.id orderdetail_id, orderdetail.items_id, orderdetail.items_num, orderdetail.orders_id FROM orders, USER,
orderdetail WHERE orders.user_id = user.id AND orderdetail.orders_id=orders.id
```

分析：

使用 resultType 将上边的查询结果映射到 pojo 中，订单信息的就是重复。

要求：

> 对 orders 映射不能出现重复记录。

在 orders.java 类中添加 List<orderDetail> orderDetails 属性。

最终会将订单信息映射到 orders 中，订单所对应的订单明细映射到 orders 中的 orderDetails 属性中。

映射成的 orders 记录数为两条（orders信息不重复）

每个 orders 中的 orderDetails 属性存储了该订单所对应的订单明细。

**映射文件**：

首先定义 resultMap

```xml
<!--定义查询订单及订单明细信息的resultMap使用extends继承，不用在中配置订单信息和用户信息的映射-->
    <resultMap id="OrdersAndOrderDetailResultMap" type="cn.zhisheng.mybatis.po.Orders" extends="OrdersUserResultMap">
        <!-- 订单信息 -->
        <!-- 用户信息 -->
        <!-- 使用extends继承，不用在中配置订单信息和用户信息的映射 -->
        <!-- 订单明细信息
        一个订单关联查询出了多条明细，要使用collection进行映射
        collection：对关联查询到多条记录映射到集合对象中
        property：将关联查询到多条记录映射到cn.zhisheng.mybatis.po.Orders哪个属性
        ofType：指定映射到list集合属性中pojo的类型
         -->
        <collection property="orderdetails" ofType="cn.zhisheng.mybatis.po.Orderdetail">
        <!-- id：订单明细唯 一标识
   property:要将订单明细的唯 一标识 映射到cn.zhisheng.mybatis.po.Orderdetail的哪个属性-->
            <id column="orderdetail_id" property="id"/>
            <result column="items_id" property="itemsId"/>
            <result column="items_num" property="itemsNum"/>
            <result column="orders_id" property="ordersId"/>
        </collection>
    </resultMap>
```

```xml
 <!--查询订单及订单明细信息, 使用 resultMap-->
    <select id="findOrdersAndOrderDetailResultMap" resultMap="OrdersAndOrderDetailResultMap">
        SELECT orders.*, USER.username, USER.sex, USER.address, orderdetail.id orderdetail_id, orderdetail.items_id, orderdetail.items_num, orderdetail.orders_id
        FROM orders, USER,orderdetail WHERE orders.user_id = user.id AND orderdetail.orders_id=orders.id
    </select>
```

**Mapper 文件**

```java
public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;
```

**测试文件**

```java
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
```

**测试结果**

![](pic/Test11.jpg)

### 总结：

mybatis使用resultMap的collection对关联查询的多条记录映射到一个list集合属性中。 

使用resultType实现：将订单明细映射到orders中的orderdetails中，需要自己处理，使用双重循环遍历，去掉重复记录，将订单明细放在orderdetails中。



## 多对多查询

**需求**：查询用户及用户购买商品信息。

**SQL语句**：

查询主表是：用户表

关联表：由于用户和商品没有直接关联，通过订单和订单明细进行关联，所以关联表：

orders、orderdetail、items

```sql
SELECT   orders.*, USER.username, USER.sex, USER.address,  orderdetail.id orderdetail_id,
orderdetail.items_id, orderdetail.items_num, orderdetail.orders_id, items.name items_name,
items.detail items_detail, items.price items_price FROM orders, USER, orderdetail, items WHERE orders.user_id = user.id AND orderdetail.orders_id=orders.id AND orderdetail.items_id = items.id
```

**映射思路**：

将用户信息映射到 user 中。
在 user 类中添加订单列表属性List<Orders> orderslist，将用户创建的订单映射到orderslist
在Orders中添加订单明细列表属性List<OrderDetail>orderdetials，将订单的明细映射到orderdetials
在OrderDetail中添加Items属性，将订单明细所对应的商品映射到Items

### 定义 resultMap：

```xml
<!--定义查询用户及用户购买商品信息的 resultMap-->
    <resultMap id="UserAndItemsResultMap" type="cn.zhisheng.mybatis.po.User">
        <!--用户信息-->
        <id column="user_id" property="id"/>
        <result column="username" property="username"/>
        <result column="sex" property="sex"/>
        <result column="birthday" property="birthday"/>
        <result column="address" property="address"/>

        <!--订单信息
		    一个用户对应多个订单，使用collection映射
		-->
        <collection property="ordersList" ofType="cn.zhisheng.mybatis.po.Orders">
            <id column="id" property="id"/>
            <result column="user_id" property="userId"/>
            <result column="number" property="number"/>
            <result column="createtime" property="createtime"/>
            <result column="note" property="note"/>

            <!-- 订单明细
		        一个订单包括 多个明细
		    -->
            <collection property="orderdetails" ofType="cn.zhisheng.mybatis.po.Orderdetail">

                <id column="orderdetail_id" property="id"/>
                <result column="orders_id" property="ordersId"/>
                <result column="items_id" property="itemsId"/>
                <result column="items_num" property="itemsNum"/>

                <!-- 商品信息
                     一个订单明细对应一个商品
                -->
                <association property="items" javaType="cn.zhisheng.mybatis.po.Items">
                    <id column="items_id" property="id"/>
                    <result column="items_name" property="name"/>
                    <result column="items_price" property="price"/>
                    <result column="items_pic" property="pic"/>
                    <result column="items_createtime" property="createtime"/>
                    <result column="items_detail" property="detail"/>
                 </association>
            </collection>
        </collection>
    </resultMap>
```

### 映射文件

```xml
<!--查询用户及用户购买商品信息, 使用 resultMap-->
    <select id="findUserAndItemsResultMap" resultMap="UserAndItemsResultMap">
        SELECT orders.*, USER.username, USER.sex, USER.address, orderdetail.id orderdetail_id, orderdetail.items_id, orderdetail.items_num, orderdetail.orders_id
        FROM orders, USER,orderdetail WHERE orders.user_id = user.id AND orderdetail.orders_id=orders.id
    </select>
```

### Mapper 文件

```java
public List<User> findUserAndItemsResultMap() throws  Exception;
```

### 测试文件

```java
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
```

### 测试：

![](pic/Test12.jpg)

我去，竟然报错了，但是不要怕，通过查看报错信息可以知道我忘记在 User.java 中加入 orderlist 属性了，接下来我加上去，并加上 getter 和 setter 方法。

```java
//用户创建的订单列表
    private List<Orders> ordersList;
    public List<Orders> getOrdersList() {
        return ordersList;
    }
    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }
```

 再次测试就能成功了。

![](pic/Test13.jpg)

### 多对多查询总结

将查询用户购买的商品信息明细清单，（用户名、用户地址、购买商品名称、购买商品时间、购买商品数量）

 针对上边的需求就使用resultType将查询到的记录映射到一个扩展的pojo中，很简单实现明细清单的功能。

 一对多是多对多的特例，如下需求：

> 查询用户购买的商品信息，用户和商品的关系是多对多关系。

需求1：

> 查询字段：用户账号、用户名称、用户性别、商品名称、商品价格(最常见)
>
> 企业开发中常见明细列表，用户购买商品明细列表，
>
> 使用resultType将上边查询列映射到pojo输出。

 需求2：

> 查询字段：用户账号、用户名称、购买商品数量、商品明细（鼠标移上显示明细）
>
> 使用resultMap将用户购买的商品明细列表映射到user对象中。

 总结：

> 使用resultMap是针对那些对查询结果映射有特殊要求的功能，，比如特殊要求映射成list中包括多个list。



## ResultMap 总结

### resultType：

作用：

> 将查询结果按照sql列名pojo属性名一致性映射到pojo中。

场合：
> 常见一些明细记录的展示，比如用户购买商品明细，将关联查询信息全部展示在页面时，此时可直接使用resultType将每一条记录映射到pojo中，在前端页面遍历list（list中是pojo）即可。

### resultMap：

> 使用association和collection完成一对一和一对多高级映射（对结果有特殊的映射要求）。

### association：

作用：

> 将关联查询信息映射到一个pojo对象中。

场合：
> 为了方便查询关联信息可以使用association将关联订单信息映射为用户对象的pojo属性中，比如：查询订单及关联用户信息。
> 使用resultType无法将查询结果映射到pojo对象的pojo属性中，根据对结果集查询遍历的需要选择使用resultType还是resultMap。

### collection：

作用：

> 将关联查询信息映射到一个list集合中。

场合：
> 为了方便查询遍历关联信息可以使用collection将关联信息映射到list集合中，比如：查询用户权限范围模块及模块下的菜单，可使用collection将模块映射到模块list中，将菜单列表映射到模块对象的菜单list属性中，这样的作的目的也是方便对查询结果集进行遍历查询。如果使用resultType无法将查询结果映射到list集合中。



## 延迟加载

### 什么是延迟加载？

resultMap可以实现高级映射（使用association、collection实现一对一及一对多映射），association、collection具备延迟加载功能。
需求：
如果查询订单并且关联查询用户信息。如果先查询订单信息即可满足要求，当我们需要查询用户信息时再查询用户信息。把对用户信息的按需去查询就是延迟加载。

延迟加载：先从单表查询、需要时再从关联表去关联查询，大大提高 数据库性能，因为查询单表要比关联查询多张表速度要快。

### 打开延迟加载开关

在mybatis核心配置文件中配置：

lazyLoadingEnabled、aggressiveLazyLoading

|          设置项          |                    描述                    |      允许值      |  默认值  |
| :-------------------: | :--------------------------------------: | :-----------: | :---: |
|  lazyLoadingEnabled   |  全局性设置懒加载。如果设为‘false’，则所有相关联的都会被初始化加载。   | true \| false | false |
| aggressiveLazyLoading | 当设置为‘true’的时候，懒加载的对象可能被任何懒属性全部加载。否则，每个属性都按需加载。 | true \| false | true  |

```xml
<settings>
		<setting name="lazyLoadingEnabled" value="true"/>
		<setting name="aggressiveLazyLoading" value="false"/>
</settings>
```



### 使用 association 实现延迟加载

需求：查询订单并且关联查询用户信息

### Mapper.xml

需要定义两个 mapper 的方法对应的 statement。

1、只查询订单信息

SQL 语句： `select * from orders`

在查询订单的 statement 中使用 association 去延迟加载（执行）下边的 statement (关联查询用户信息)

```xml
<!--查询订单并且关联查询用户信息，关联用户信息需要通过 association 延迟加载-->
    <select id="findOrdersUserLazyLoading" resultMap="OrdersUserLazyLoadingResultMap">
        select * from orders
    </select>
```

2、关联查询用户信息

通过上面查询订单信息中的 user_id 来关联查询用户信息。使用 UserMapper.xml 中的 findUserById

SQL语句：`select * from user where id = user_id`

```xml
<select id="findUserById" parameterType="int" resultType="user">
        select * from user where id = #{value}
    </select>
```

上边先去执行 findOrdersUserLazyLoading，当需要去查询用户的时候再去执行 findUserById ，通过 resultMap的定义将延迟加载执行配置起来。也就是通过 resultMap 去加载 UserMapper.xml 文件中的 select = findUserById

### 延迟加载的 resultMap

```xml
<!--定义 关联用户信息（通过 association 延迟加载）的resultMap-->
    <resultMap id="OrdersUserLazyLoadingResultMap" type="cn.zhisheng.mybatis.po.Orders">
        <!--对订单信息映射-->
        <id column="id" property="id"/>
        <result column="user_id" property="userId"/>
        <result column="number" property="number"/>
        <result column="createtime" property="createtime"/>
        <result column="note" property="note"/>
        <!-- 实现对用户信息进行延迟加载
		select：指定延迟加载需要执行的statement的id（是根据user_id查询用户信息的statement）
		要使用userMapper.xml中findUserById完成根据用户id(user_id)用户信息的查询，如果findUserById不在本mapper中需要前边加namespace
		column：订单信息中关联用户信息查询的列，是user_id
		关联查询的sql理解为：
			SELECT orders.*,
	        (SELECT username FROM USER WHERE orders.user_id = user.id)username,
	        (SELECT sex FROM USER WHERE orders.user_id = user.id)sex
	        FROM orders-->
        <association property="user" javaType="cn.zhisheng.mybatis.po.User" select="cn.zhisheng.mybatis.mapper.UserMapper.findUserById" column="user_id">
        </association>
    </resultMap>
```

### OrderMapperCustom.java

```java
public List<Orders> findOrdersUserLazyLoading() throws Exception;
```

### 测试代码：

```java
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
```

### 测试结果：

![](pic/Test14.jpg)

整个延迟加载的思路：    

1、执行上边mapper方法（findOrdersUserLazyLoading），内部去调用cn.zhisheng.mybatis.mapper.OrdersMapperCustom 中的 findOrdersUserLazyLoading 只查询 orders 信息（单表）。

2、在程序中去遍历上一步骤查询出的 List<Orders>，当我们调用 Orders 中的 getUser 方法时，开始进行延迟加载。

3、延迟加载，去调用 UserMapper.xml 中 findUserbyId 这个方法获取用户信息。

### 思考：

不使用 mybatis 提供的 association 及 collection 中的延迟加载功能，如何实现延迟加载？？

实现方法如下：

定义两个mapper方法：

1、查询订单列表

2、根据用户id查询用户信息

实现思路：

先去查询第一个mapper方法，获取订单信息列表

在程序中（service），按需去调用第二个mapper方法去查询用户信息。

总之：

使用延迟加载方法，先去查询 简单的 sql（最好单表，也可以关联查询），再去按需要加载关联查询的其它信息。

### 一对多延迟加载

上面的那个案例是一对一延迟加载，那么如果我们想一对多进行延迟加载呢，其实也是很简单的。

一对多延迟加载的方法同一对一延迟加载，在collection标签中配置select内容。

### 延迟加载总结：

作用：
> 当需要查询关联信息时再去数据库查询，默认不去关联查询，提高数据库性能。
> 只有使用resultMap支持延迟加载设置。

场合：

> 当只有部分记录需要关联查询其它信息时，此时可按需延迟加载，需要关联查询时再向数据库发出sql，以提高数据库性能。
>
> 当全部需要关联查询信息时，此时不用延迟加载，直接将关联查询信息全部返回即可，可使用resultType或resultMap完成映射。



