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
flushall #清空所有数据库的数据
flushdb #清空当前数据库的数据

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

4、key的命令不区分大小写，但是key的值区分大小写

### 支持类型

String、hash、List、Set、zset

#### String

String类型是二进制安全：

1、编译、解码发生在客户端，执行效率高

2、不要频繁的编解码，不会出现乱码

~~~redis
get key	#查询当前值
getrange key startindex endindex #截取当前key值
set key value #设置key值，key值不存在无法设置成功
setnx key value #设置key值，key值存在时无法设置，当key不存在时，设置当前值  
getset key value #先取出a的值然后再赋值
strlen key #获取当前key的长度	
del key #删除key
incr key #自增key，如果key不存在，则新建key，并初始化key值为0，然后再自增1
decr key #自减key,
incrby key increment #按照指定值增长
decrby key decrement #按照指定值减少
~~~

setnx key value

分布式锁的解决方案之一

incrby key increment

decrby key decrement

##### 应用场景

1、string通常用于保存单个字符或者json格式数据

2、string二进制安全。因此完全可以存入照片

3、计数器（微博数、粉丝数）

​	incr操作具有原子性，无论有多少用户来同时进行读写，都不会出现写入数据的错误。

#### Hash(哈希)

非常相像于一个javabean对象，适合存储javabean对象

~~~
#取值语法
hget key field
hmget key field1 field2 field3
hgetall key

#赋值语法
hset key field value
hmset key field1 value1 field2 value2 field3 value3

#其他
hkeys key #获取当前key的所有字段
hlen key #获取当前key的字段数量

#删除
hdel key field field2 field3 #删除一个或多个字段

hincrby key field increment#使对象中的整形字段增加某个值

hexists key field 判断某个字段是否存在
	
~~~

##### 应用场景

存储java对象

string存储对象可能问题：

1.序列化和反序列化的问题，转换为java对象的问题

2、修改值得问题，并发的问题。



#### List

类似于java中的LinkedList

##### 常用命令

~~~
lpush key a b c d e
rpush key 1 2 3 4 5	
lpushx key a b c d e
rpushx key 1 2 3 4 5	

llen key #获取列表长度
lindex key index #获取列表中的元素
lrange key start end
#start 和 end 表示开始和结束 start的0 1 2.  end 用-1 -2 -3

lpop key #弹出左侧第一个元素
rpop key #弹出右侧第一个元素
blpop key [key2] timeout	#弹出左侧第一个元素，如果没有则阻塞或者等待时间超时
brpop key [key2] timeout	#弹出右侧第一个元素，如果没有则阻塞或者等待时间超时
ltrim key start end 	#start 和 end 表示开始和结束 start的0 1 2.  end 用-1 -2 -3


lset key index value	#修改某个索引对应元素的值
linsert key before/after word value #在第一个word元素前后插入某个值value
~~~

##### 应用场景

1. 数据量超级大的集合删减，使用lrange命令实现分页的功能

   关注列表、粉丝列表、留言评价、热点新闻等

2. 

### java连接redis

Jedis

RedisTemplate存入数据时，会进行默认的序列化



