## MYSQL

### 一、MySQL 连接

#### 1.启动及关闭 MySQL 服务器


```mysql
# 初始化 MySQL：
mysqld --initialize
# 启动 MySQL：
systemctl start mysqld #暂时不知道如何使用
net start mysql80
# 查看 MySQL 运行状态：
systemctl status mysqld
# 停止MySQL
net stop mysql80
```

**mariadb数据库的相关命令是：**

```mariadb
systemctl start mariadb  #启动MariaDB
systemctl stop mariadb  #停止MariaDB
systemctl restart mariadb  #重启MariaDB
systemctl enable mariadb  #设置开机启动
```

#### 2.MySQL 用户设置

~~~mysql
# 初次设置root用户的密码
mysqladmin -u root password "new_password";
~~~

#### 3.其他常用命令

```mysql
# 查看mysql的版本号
mysqladmin --version
# 初次设置root用户的密码
mysqladmin -u root password "new_password";
# 登录数据库
mysql -uroot -pbeitie
# 登录数据库，目的和上面一样，但是此处隐藏了密码（输入密码时显示***）
mysql -uroot -p[然后回车] 
# 退出登录
exit; && quit ;
```

### 二、操作数据库

~~~mysql
# 使用 create 命令创建数据库
CREATE DATABASE 数据库名;
# 使用 mysqladmin 创建数据库
mysqladmin -u root -p create DEMO Enter password:******
# 使用 drop  命令删除数据库
drop DATABASE 数据库名;
# 使用 mysqladmin 删除数据库
mysqladmin -u root -p drop DEMO Enter password:******
# 选取数据库
user DEMO;
~~~

### 三、数据类型

​		大致可分为数值、日期/时间和字符串（字符）类型

#### 1.数值型

- [ ] | 类型           | 大小  | 范围（有符号）                                          | 用途                 |
  | :------------- | ----- | ------------------------------------------------------- | -------------------- |
  | TINYINT        | 1字节 | (-128，127)                                             | 小整数值             |
  | SMALLINT       | 2字节 | (-32 768，32 767)                                       | 大整数值             |
  | MEDIUMINT      | 3字节 | (-8 388 608，8 388 607)                                 | 大整数值             |
  | INT或者INTEGER | 4字节 | (-2 147 483 648，2 147 483 647)                         | 大整数值             |
  | BIGINT         | 8字节 | (-9,223,372,036,854,775,808，9 223 372 036 854 775 807) | 大整数值             |
  | FLOAT          | 4字节 |                                                         | 单精度浮点型         |
  | DOUBLE         | 8字节 |                                                         | 双精度浮点型         |
  | DECIMAL        |       |                                                         | 定点数（相对浮点数） |

#### 2.日期和时间类型

| 类型      | 大小  | 范围 | 格式                | 用途                     |
| --------- | ----- | ---- | ------------------- | ------------------------ |
| DATE      | 3字节 |      | YYYY-MM-DD          | 日期值                   |
| TIME      | 3字节 |      | HH:MM:SS            | 时间值或持续时间         |
| YEAR      | 1字节 |      | YYYY                | 年份值                   |
| DATETIME  | 8字节 |      | YYYY-MM-DD HH:MM:SS | 混合日期和时间值         |
| TIMESTAMP | 4字节 |      | YYYYMMDD HHMMSS     | 混合日期和时间值，时间戳 |

#### 3.字符串类型

| 类型       | 大小 | 用途                          | 备注 |
| ---------- | ---- | ----------------------------- | ---- |
| CHAR       |      | 定字符串                      |      |
| VARCHAR    |      | 可变字符串                    |      |
| TINYBLOB   |      | 不超过255个字符的二进制字符串 |      |
| TINYTEXT   |      | 短文本字符串                  |      |
| BOLB       |      | 二进制长字符串                |      |
| TEXT       |      | 长文本数据                    |      |
| MEDIUMBLOB |      | 二进制中等长度字符串          |      |
| MEDIUMTEXT |      | 中等文本数据                  |      |
| LONGBLOB   |      | 二进制极大字符串              |      |
| LONGTEXT   |      | 极大文本数据                  |      |

### 四、数据表

#### 1.创建数据表

```mysql
# 创建数据表
CREATE TABLE table_name (column_name column_type);
# 例如：
CREATE TABLE IF NOT EXISTS `user`(
   `user_id` INT UNSIGNED AUTO_INCREMENT,
   `user_title` VARCHAR(100) NOT NULL,
   `user_author` VARCHAR(40) NOT NULL,
   `submission_date` DATE,
   PRIMARY KEY ( `user_id` ))ENGINE=InnoDB DEFAULT CHARSET=utf8;
 # AUTO_INCREMENT定义列为自增的属性，一般用于主键，数值会自动加1
 # 如果你不想字段为 NULL 可以设置字段的属性为 NOT NULL
 # PRIMARY KEY关键字用于定义列为主键
 # ENGINE 设置存储引擎，CHARSET 设置编码
 
  # 删除数据表
 DROP TABLE table_name ;
```

#### 2.操作数据表

~~~mysql
 # 插入数据
 INSERT INTO table_name ( field1, field2,...fieldN ) VALUES ( value1, value2,...valueN );
 # 查询数据
 select *from table_name t where t.name like '' ;
 # 升/降序
 select * from table_name t order by t.name desc,t.sex asc; 	#desc降序/asc升序
  # 删除数据
 delete from table_name t where t.name like '%trump%' ;
 # 分组
 select t.sex from table_name t where t.name like '' group t.sex;
 # 在分组的列上我们可以使用 COUNT, SUM, AVG等函数。
 select avg(t.score),sum(t.score),t.name,count(*) fromm table_name t group t.name;
 
 # coalesce函数中如果a==null,则选择b；如果b==null,则选择c；如果a!=null,则选择a；如果a b c 都为null ，则返回为null（没意义）   【扣儿莱斯】
 select coalesce(a,b,c);
    SELECT coalesce(name, '总数'), SUM(singin) as singin_count FROM  employee_tbl GROUP BY name WITH ROLLUP;
    +--------------------------+--------------+| 
    coalesce(name, '总数') | singin_count |
    +--------------------------+--------------+
    | 小丽                   |            2 |
    | 小明                   |            7 |
    | 小王                   |            7 |
    | 总数                   |           16 |
    +--------------------------+--------------+
  # 上面例子中name为null时，就会取值总数。
  # with rollup在分组统计数据的基础上再进行统计汇总，即用来得到group by的汇总信息
~~~

#### 3.表间的连接

**JOIN 按照功能大致分为如下三类：**

- INNER JOIN（内连接,或等值连接）：获取两个表中字段匹配关系的记录。

- LEFT JOIN（左连接）：获取左表所有记录，即使右表没有对应匹配的记录。

- RIGHT JOIN（右连接）： 与 LEFT JOIN 相反，用于获取右表所有记录，即使左表没有对应匹配的记录。

  ```mysql
   # 内连接
   SELECT a.php_id, a.user_author, b.user_count
   FROM user_tbl a INNER JOIN tcount_tbl b ON a.user_author = b.user_author;
   # 等价于
   select a.php_id, a.user_author, b.user_count
   from user_tbl a ,tcount_tbl b where a.user_author = b.user_author;
   # 左连接
   SELECT a.php_id, a.user_author, b.user_count
   FROM user_tbl a left JOIN tcount_tbl b ON a.user_author = b.user_author;
   # 右连接
   SELECT a.php_id, a.user_author, b.user_count
   FROM user_tbl a right JOIN tcount_tbl b ON a.user_author = b.user_author;
  ```

#### 4.null值处理

**为了处理这种情况，MySQL提供了三大运算符:**

- IS NULL: 当列的值是 NULL,此运算符返回 true。

- IS NOT NULL: 当列的值不为 NULL, 运算符返回 true。

- <=>: 比较操作符（不同于=运算符），当比较的的两个值为 NULL 时返回 true。

- | 运算符              | 作用                         |
| ------------------- | ---------------------------- |
  | =                   | 等于                         |
  | <=>                 | 安全的等于                   |
  | <> 或者 !=          | 不等于                       |
  | <=                  | 小于等于                     |
  | >=                  | 大于等于                     |
| >                   | 大于                         |
  | IS NULL 或者 ISNULL | 判断一个值是否为空           |
| IS NOT NULL         | 判断一个值是否不为空         |
  | BETWEEN AND         | 判断一个值是否落在两个值之间 |
  
- 

  ~~~mysql
  # null值比较
  null = null #返回false
  null <=> null	#返回true
  ~~~

  ifnull(columName,defaultValue):

  ```mysql
  ifnull(columnName2,0) # 如果columnName2的值为null，则该函数返回值为0
  ```

### 五、正则表达式

下表中的正则模式可应用于 REGEXP 操作符中。

|    模式    | 描述                                                         |
| :--------: | ------------------------------------------------------------ |
|     ^      | 匹配输入字符串的开始位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。 |
|     $      | 匹配输入字符串的结束位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。 |
|     .      | 匹配除 "\n" 之外的任何单个字符。要匹配包括 '\n' 在内的任何字符，请使用象 '[.\n]' 的模式。 |
|   [...]    | 字符集合。匹配所包含的任意一个字符。例如， '[abc]' 可以匹配 "plain" 中的 'a'。 |
|   [^...]   | 负值字符集合。匹配未包含的任意字符。例如， '[^abc]' 可以匹配 "plain" 中的'p'。 |
| p1\|p2\|p3 | 匹配 p1 或 p2 或 p3。例如，'z                                |
|     *      | 匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。* 等价于{0,}。 |
|     +      | 匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。+ 等价于 {1,}。 |
|    {n}     | n 是一个非负整数。匹配确定的 n 次。例如，'o{2}' 不能匹配 "Bob" 中的 'o'，但是能匹配 "food" 中的两个 o。 |
|   {n,m}    | m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。 |

查找name字段中以'st'为开头的所有数据：

```mysql
SELECT name FROM person_tbl WHERE name REGEXP '^st';
```

查找name字段中以'ok'为结尾的所有数据：

```mysql
SELECT name FROM person_tbl WHERE name REGEXP 'ok$';
```

查找name字段中包含'mar'字符串的所有数据：

```mysql
SELECT name FROM person_tbl WHERE name REGEXP 'mar';
```

查找name字段中以元音字符开头且以'ok'字符串结尾的所有数据：

```mysql
SELECT name FROM person_tbl WHERE name REGEXP '^[aeiou]|ok$';
```

### 六、修改表结构

#### 1.添加字段

~~~mysql
# 添加单个字段
ALTER TABLE user_info ADD aa CHAR FIRST DEFAULT '1';
ALTER TABLE user_info ADD tt CHAR AFTER ss;
# 添加多个字段
ALTER TABLE user_info ADD (
     ss CHAR,
     tt CHAR
);
# FIRST 和 AFTER 关键字只适用于 ADD 子句，所以如果你想重置数据表字段的位置就需要先使用 DROP 删除字段然后使用 ADD 来添加字段并设置位置。
~~~

#### 2.修改字段

~~~mysql
  # 修改字段类型
  ALTER TABLE user_info MODIFY tt INT;
  # 修改字段名字和类型
  ALTER TABLE user_info CHANGE tt tt1 CHAR;
  # 修改字段类型和默认值
  ALTER TABLE user_info MODIFY tt1 CHAR DEFAULT '1';
  # 修改字段默认值
  ALTER TABLE user_info ALTER tt1 SET DEFAULT '2';
  # 删除字段默认值
  ALTER TABLE user_info ALTER tt1 DROP DEFAULT;
~~~

#### 3.修改表名rename

~~~mysql
# 对表名进行重命名
ALTER TABLE user_info RENAME TO user_infoV2;
~~~

### 七、Mysql索引

创建索引时，需要确保创建的索引时应用在SQL查询语句的条件（一般作为WHERE字句的条件）

#### 普通索引

```mysql
# 创建索引
CREATE INDEX indexName ON mytable(username(length));
# 修改表结构添加索引
ALTER mytable ADD INDEX [indexName] ON (username(length));
# 创建表时添加索引
CREATE TABLE mytable(  
ID INT NOT NULL,   
username VARCHAR(16) NOT NULL,  
INDEX [indexName] (username(length))  
);
# 删除索引
DROP INDEX [indexName] ON mytable;
```

#### 唯一索引

```mysql

# 创建索引
CREATE UNIQUE INDEX indexName ON mytable(username(length));
# 修改表结构添加索引
ALTER mytable ADD UNIQUE [indexName] ON (username(length));
# 创建表时添加索引
CREATE TABLE mytable(  
ID INT NOT NULL, 
username VARCHAR(16) NOT NULL,  
UNIQUE [indexName] (username(length)) 
);

```

~~~mysql
ALTER TABLE tbl_name ADD PRIMARY KEY (column_list); # 该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL。
ALTER TABLE tbl_name ADD UNIQUE index_name (column_list); # 这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次）。
ALTER TABLE tbl_name ADD INDEX index_name (column_list); # 添加普通索引，索引值可出现多次。
ALTER TABLE tbl_name ADD FULLTEXT index_name (column_list);#该语句指定了索引为  FULLTEXT ，用于全文索引。

# 删除索引
ALTER TABLE testalter_tbl DROP INDEX (c);
ALTER TABLE testalter_tbl DROP PRIMARY KEY;
~~~

显示索引信息

```mysql
SHOW INDEX FROM table_name\G;
```



***通俗理解：***
利用索引中的附加列，您可以缩小搜索的范围，但使用**一个具有两列的索引 不同于使用两个单独的索引**。复合索引的结构与**电话簿**类似，人名由姓和名构成，电话簿首先按姓氏对进行排序，然后按名字对有相同姓氏的人进行排序。**如果您知道姓，电话簿将非常有用；如果您知道姓和名，电话簿则更为有用，但如果您只知道名不姓，电话簿将没有用处**。

所以说创建复合索引时，应该仔细考虑**列的顺序**。对索引中的**所有列**执行搜索或仅对**前几列**执行搜索时，**复合索引非常有用**；**仅对后面的任意列**执行搜索时，**复合索引则没有用处。**

***重点：***

**多个单列索引**在**多条件查询**时优化器会选择**最优索引策略**，**可能只用一个索引，也可能将多个索引全用上！** 但多个单列索引底层会建立多个B+索引树，比较占用空间，也会浪费一定搜索效率，故如果只有**多条件联合查询时最好建联合索引！**

***最左前缀原则：***

顾名思义是最左优先，以**最左边的为起点**任何连续的索引都能匹配上，
注：如果第一个字段是**范围查询需要单独建一个索引**
注：在创建联合索引时，要根据业务需求，where子句中**使用最频繁的一列放在最左边**。这样的话扩展性较好，比如 `userid` 经常需要作为查询条件，而 `mobile` 不常常用，则需要把 `userid` 放在联合索引的**第一位置，即最左边**

**同时存在联合索引和单列索引（字段有重复的），这个时候查询mysql会怎么用索引呢？**

这个涉及到mysql本身的**查询优化器策略**了，当一个表有多条索引可走时, Mysql 根据**查询语句的成本**来选择走哪条索引；

有人说where查询是按照从左到右的顺序，所以筛选力度大的条件尽量放前面。网上百度过，很多都是这种说法，但是据我研究，**mysql执行优化器会对其进行优化**，**当不考虑索引时，where条件顺序对效率没有影响**，**真正有影响的是是否用到了索引**！

***其他知识点：***

1、需要加索引的字段，**要在where条件中**
2、**数据量少的字段不需要加索引**；因为**建索引有一定开销**，如果数据量小则没必要建索引（速度反而慢）
3、避免在where子句中使用**or**来连接条件,因为如果**俩个字段中有一个没有索引**的话,引擎会放弃索引而产生全表扫描
4、**联合索引比对每个列分别建索引更有优势**，因为索引建立得越多就越占磁盘空间，在更新数据的时候速度会更慢。另外建立多列索引时，顺序也是需要注意的，**应该将严格的索引放在前面，这样筛选的力度会更大，效率更高**。

### 八、表的复制

**MySQL 复制表**

如果我们需要完全的复制MySQL的数据表，包括表的结构，索引，默认值等。 如果仅仅使用**CREATE TABLE ... SELECT** 命令，是无法实现的。

本章节将为大家介绍如何完整的复制MySQL数据表，步骤如下：

- 使用 **SHOW CREATE TABLE** 命令获取创建数据表(**CREATE TABLE**) 语句，该语句包含了原数据表的结构，索引等。
- 复制以下命令显示的SQL语句，修改数据表名，并执行SQL语句，通过以上命令 将完全的复制数据表结构。
- 如果你想复制表的内容，你就可以使用 **INSERT INTO ... SELECT** 语句来实现。

~~~mysql
# 复制表结构和表内容（不包含索引）
CREATE TABLE userinfo SELECT * FROM user_info ;
#复制表结构（但是不包括索引）
CREATE TABLE userinfoV2 SELECT * FROM user_info WHERE 1=2;

# 查询user_info的表内容然后将结果赋值给userinfoV2（快速导入数据）
INSERT INTO userinfoV2 SELECT * FROM user_info;
# 查询当前表的建表语句，包含了了表的索引
SHOW CREATE TABLE userinfoV2;
# 上面语句的执行结果createtable列内容，对其表名进行修改就可以实现标的完整复制
CREATE TABLE `glossary` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NOT NULL,
  `CODE` INT(11) NOT NULL,
  `STATUS` SMALLINT(6) DEFAULT NULL,
  `category` INT(11) NOT NULL,
  `display_order` SMALLINT(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8

~~~

