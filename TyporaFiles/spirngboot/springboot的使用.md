## springboot的使用

### spring优缺点

优点：spring代码属于轻量级的，通过依赖注入、ioc以及aop技术实现了代码级的轻量级开发。

缺点：代码轻量级，但是配置却是重量级的。大量的xml配置、注解配置，以及pom文件中的依赖关系以及版本控制

springboot解决了这些问题

### springboot

约定 》 配置 》 代码

提供了一种快速进行spring开发的方式

必配父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.9.RELEASE</version>
</parent>
```

### 热部署配置

pom文件中引入依赖

```xml
<!-- 热部署依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

由于idea自身的原因，需要修改project字段编译，并且alt+shift+ctrl+/ 点击注册registery

![image-20210422171920924](image-20210422171920924.png)

然后选择红色框选内容

![image-20210422172029681](springboot的使用/image-20210422172029681.png)

### 配置文件的加载

```xml
<!--默认加载src下面的三种文件，最后的properties会覆盖之前配置文件定义的内容 -->
<resource>
  <directory>${basedir}/src/main/resources</directory>
  <filtering>true</filtering>
  <includes>
    <include>**/application*.yml</include>
    <include>**/application*.yaml</include>
    <include>**/application*.properties</include>
  </includes>
</resource>
```

### application.yml文件

#### 配置以及规则

```yml
#普通数据的配置
name: zhangsan
#java对象的配置
person:
  name: zhangsan
  age: 11
  sex: 男

#行内对象配置
student: {name: lisi,age: 22,sex: 女}

# 集合的配置（普通数据配置）
cities:
  - 北京
  - 南京
  - 天京

# 集合行内数据配置
countries: [中国,美国,日本,俄罗斯]

# 集合（Java对象）
bird:
  - color: red
    size: 11
  - color: blue
    size: 12
  - color: green
  - size: 13

# 集合（java对象）行内
weather: [{wind: 3,name: rain},{wind: 4,name: sunshine}]

#Map配置
map_data:
  key1: value1
  key2: value2
```

值得获取

~~~java
@Value("${server.port}")
private String server_port;
~~~

~~~java
@ConfigurationProperties(prefix="student")
@RestController
public class TestController{
    private String name;
    private String age;
    private String sex;
    
}
~~~

### junit的使用

pom文件引入依赖

```xml
<!-- junit版本至少4.12以及以上 -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

测试类配置test

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootQuick001Application.class) // 配置启动类
public class DeptServiceTest {
    @Resource
    private DeptService deptService;
    @Test
    public void findAllTest(){
        List<Dept> list=deptService.findAll();
        System.out.println(list);
    }
}
```