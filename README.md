# android_shopping
# 安卓Android商品购物系统app设计

系统开发环境: Windows + Myclipse(服务器端) + Eclipse(手机客户端) + mysql数据库

服务器也可以用Eclipse或者idea等工具，客户端也可以采用android studio工具！

系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！

服务器和客户端数据通信格式：json格式,采用servlet方式

【服务器端采用SSH框架，请自己启动tomcat服务器，hibernate会自动生成数据库表的哈！】

hibernate生成数据库表后，只需要在admin管理员表中加个测试账号密码就可以登录后台了哈！

下面是数据库的字段说明：

商品类别: 类别编号,类别名称

商品信息: 商品编号,商品类别,商品名称,商品图片,商品单价,商品库存,是否推荐,人气值,上架日期

是否信息: 是否编号,是否信息

会员信息: 会员用户名,登录密码,真实姓名,性别,出生日期,联系电话,联系邮箱,联系qq,家庭地址,会员照片

订单信息: 订单编号,下单会员,下单时间,订单总金额,订单状态,付款方式,收货人姓名,收货人电话,邮政编码,收货地址,附加信息

订单状态信息: 状态编号,订单状态名称

订单明细信息: 记录编号,定单编号,商品名称,商品单价,订购数量

商品购物车: 记录编号,用户名,商品名称,商品单价,商品数量

商品评价: 评价编号,商品名称,用户名,评价内容,评价时间

系统公告: 记录编号,公告标题,公告内容,发布日期
