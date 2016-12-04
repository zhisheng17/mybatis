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







### 使用 resultType 和 resultMap 一对一查询小结

