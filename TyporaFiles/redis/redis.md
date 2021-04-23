### 简介

redis是nosql的key-value的数据库，是一个简单的、高效的、分布式的、基于内存的缓存工具。

搭设好服务器后，通过网络连接，提供key-value的缓存服务。

在面对大规模和高并发的环境下，关系型数据库显得力不从心，暴露出很多问题。	

#### 区别与联系

关系型数据库：表和表之间的关联关系

nosql数据库：数据之间彼此没有关系

选择nosql数据库的原因：

1、数据模型比较简单，没有那么多的字段值

2、对数据库性能要求比较高

3、不需要高度的数据一致性

4、对于给定key，比较容易映射复杂值的环境

#### 数据库特点

redis以及key-value数据库特点：

1、数据的持久化，将内存中的数据保存在磁盘中，	重启时可以重新加载使用

2、redis支持String、List、HashSet、Set以及Ordered Set数据类型

3、原子性  单个操作是原子性，多个操作也支持事务

4、支持集群，redis支持16个库

5、消息队列



### 配置信息

~~~conf
daemonize yes #设置为守护线程
port  6357  #端口号
bind 127.0.0.1 ::1 #绑定只能某些ip地址可以访问
save [seconds] [changes]
dbfilename dump.rdb #指定数据库的文件名
dir ./  #指定本地数据库存放目录
requirepass foobared #设置连接密码，客户端访问需要通过AUTH(password)授权访问
maxclients #最大允许客户端访问数
maxmemory #最大的默认存储内存
~~~

### 内存方案

redis内存占用较大解决方案：

1、为数据设置超时时间

2、配置内存为1/4到1/2之间

3、采用LRU算法进行数据的处理

![image-20210423201440372](redis/image-20210423201440372.png)



### 常用命令

~~~
del key	 #删除key值
dump key #序列化key，并返回序列化key的值
exists key 	#判断key是否存在
expire key #seconds 对key设置过期时间(单位秒)：
ttl key #检查key剩余时间(-1代表永久有效，-2代表已经失效)
pexpire key milliseconds #对key设置过期时间(单位毫秒)
pttl key 
persist key #将某key设置为永久有效
keys pattern  #keys * 查询所有的key | keys dept?  查询以dept开头后面只有一个字符的key值
randomkey #随机key
rename oldkey newkey 	#更改key的名字
move key db #将某key移入某序号的数据库，redis支持16个数据库
type key #返回某key的数据类型

~~~

#### expire key

1、用于限时优惠活动

2、数据排行榜

3、手机的验证码

4、限制访客的访问次数（每分钟访问10次）

#### key的命名

单个key存入最大的内存为512m

1、命名不要太长，否则占内存，并且查找效率低

2、命名不要太短，否则可读性太差

3、在一个项目中，key使用统一的命名格式：user:123:password

