## SPRINGMVC  AND  MYBATIS

### 一、springmvc开发

~~~reStructuredText
springmvc是spring的 一个模块，与spring不需要进行整合，是基于mvc的web框架。

执行步骤：
	1、发起请求到前端控制器（DispatherServlet）
	2、DispatherServlet调用处理器映射器来返回查找对应的handler
	3、DispatherServlet调用处理器适配器来执行对应的handler
	4、handler执行业务逻辑，返回ModelAndView给处理器适配器
	5、处理器适配器返回ModelAndView给DispatherServlet
	6、DispatherServlet调用视图解析器对ModelAndView进行解析（此时view仅仅为逻辑视图）
	7、视图解析器将逻辑视图转为整整的视图，并向DispatherServlet返回真正的视图
	8、DispatherServlet对真正视图进行渲染，将模型数据填充入view的request域中
	9、DispatherServlet向用户进行相应
~~~

​	总结：

##### 	1）前端控制器 DispatherServlet

​			接收请求，处理请求，相应结果

```xml
 <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring/spring-mvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <!--
      1、配置*.action，只解析后缀名为*.action的请求，不会解析静态资源
      2、配置/*，解析一切请求，需要特殊处理不解析静态资源，符合RESTful风格
      3、配置/,错误配置
    -->
  	<url-pattern>*.action</url-pattern>
</servlet-mapping>
```

##### 	2）处理器映射器 HandlerMapping

​			负责查找handler

```xml
<!-- 将beanName中name的名字以"/"开头的beanName加入到请求映射对中 -->
<bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"></bean>
<!-- 集中将beanName与请求建立映射关系，bean对应的类型不适合于@RequestMapping注解的handler -->
 <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
        <props>
            <prop key="/queryUserInfo1.action">userInfoController</prop>
            <prop key="/queryUserInfo2.action">userInfoController</prop>
            <prop key="/queryUserInfo3.action">userInfoController2</prop>
            <prop key="/queryUserInfo4.action">userInfoController2</prop>
        </props>
    </property>
    </bean>
<!--@RequestMapping映射器 专用于@RequestMapping注解的handler-->
   <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"></bean>
```

##### 	3）处理器适配器 HandlerAdapter

```xml
<!-- 控制器适配器-->
<bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter" ></bean>
```

```java
public class SimpleControllerHandlerAdapter implements HandlerAdapter {

   @Override
   public boolean supports(Object handler) {
      return (handler instanceof Controller);
   }
}
```

​		所有的是配置均需要实现**HandlerAdapter**接口，不同的适配器到要实现**supports(Object handler)**方法，用来判断当前的是配置支持哪一类的handler。然后根据不同的handler来调用不同的适配器来执行handler	

常见的适配器有：

```xml
<!-- 实现Controller接口适配器-->
<bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter" ></bean>
<!-- 实现HttpRequestHandler接口适配器-->
<bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"></bean>
<!--@RequestMapping注解适配器 -->
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"></bean>
```

##### 4）处理器	Handler

​			==**核心业务处理逻辑，需要程序员进行开发**==

```java
public class UserInfoController implements Controller {
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) {
        List<User> list=new ArrayList<User>();
        User user=new User();
        user.setUid(1);
        user.setUage(22);
        ......
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userInfoList", list);
        modelAndView.setViewName("UserInfo");
        return modelAndView;
}
```

```java
public class UserInfoController2 implements HttpRequestHandler {

    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        List<User> list=new ArrayList<User>();
        User user=new User();
        user.setUid(1);
        user.setUage(22);
        user.setUname("张三");
        ......
}
```

```java
@Controller
@RequestMapping("/user")
public class UserInfoController3{
    @Autowired
    private UserService userService;
    @RequestMapping(value = {"/queryUserInfoFreeStyle"})
    public ModelAndView queryUserInfoFreeStyle(Integer a) throws ServletException, IOException {
        List<User> list=userService.findAllUsers();
        System.out.println(System.getProperty("file.encoding"));
        User user=new User();
        user.setUname("毛主席");
        user.setUage(88);
        user.setSex("男");
        list.add(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userInfoList", list);
        modelAndView.setViewName("UserInfo");
        return modelAndView;
    }
}
```

handler的编写可以实现**Controller**接口、	**HttpRequestHandler**接口，还可以用**@RequestMapping**进行注解，不过这几种不同的handler需要不同的适配器进行解析执行。其中**@RequestMapping**使用较为方便，因为耦合性非常低，又可以在同一个类里面配置多个handler

##### 5）视图解析器	ViewResolver

​			进行视图解析，将逻辑视图解析为真正的视图

```xml
<!-- 视图解析的配置 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/views/"></property>
    <property name="suffix" value=".jsp"></property>
</bean>
```

##### 6）视图 view

​			==**展现层（jsp，php，html，pdf等），需要程序员开发**==

#### 1、常用注解学习

```xml
<!-- 启用注解驱动 -->
<!-- 加载了控制器映射器、控制器适配器、以及启动注解-->
<mvc:annotation-driven />
<!-- 启用注解 -->
<context:annotation-config></context:annotation-config>
<!-- 上下文组件扫描，此项操作包含了context:annotation-config的启用注解功能，有此项可以不用context:annotation-config -->
<context:component-scan base-package="com.beitie.controller,com.beitie.util.exception"></context:component-scan>

```

**@RequestMapping**：窄化请求映射，可以配置在handler类以及其方法上面，作为请求的路径

```java
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getUserInfoById")
    public String getUserInfoById(){
        return "singleUserInfo";
    }
}
```

#### 2、参数绑定

默认支持Model（ModelMap）、HttpServletRequest、HttpServletResponse、HttpSession

此外还支持简单类型、pojo、集合类型、map、数组等

简单类型int、String、Integer等

```java
   /**
   	*  http://localhost:8090/user/updateUserInfoByIdBefore.action?id=10
	*  @RequestParam主要用来当传递的参数和定义的形参不一致时使用
	*/
@RequestMapping("/deleteUser")
public String deleteUser(@RequestParam("id") int uid) throws ServletException, IOException {
    User user = new User();
    user.setUid(uid);
    userService.deleteUserById(user);
    return "redirect:findAllUserInfo.action";
}
```

pojo的参数绑定(简单的和包装类的pojo)

```java
   /**
   	*  http://localhost:8090/user/updateUserInfoByIdBefore.action?uid=10&uname=zhangsan&age=20
	*  当前url访问时形参中uid和user的uid以及user的uname、age都会有值
	*/
@RequestMapping("/updateUserInfoByIdBefore")
public ModelAndView updateUserInfoByIdBefore(int uid,User user){
    User user1 = userService.getUserByUid(user.getUid());
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("singleUserInfo");
    modelAndView.addObject("userInfo",user1);
    return modelAndView;
}

	/**
     *  UserVO包装类型的pojo接收参数格式
     *   http://localhost:8090/user/updateUserInfoByIdBefore.action?user.uid=10&user.uname=zhangsan&user.age=20
     * 	user.uid=10中前缀要和包装类的变量名保持一致（相同）
     */
 @RequestMapping("/updateUserInfoByIdBefore")
    public ModelAndView updateUserInfoByIdBeforeV2(UserVO userVO){
        User user1 = userService.getUserByUid(userVO.getUser().getUid());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("singleUserInfo");
        modelAndView.addObject("userInfo",user1);
        return modelAndView;
    }

public class UserVO implements Serializable{
    private List<User> userList;
    private UserCustom userCustom;
    private User user;

    public User getUser() {
        return user;
    }
    .......
}
```

在参数绑定时，有事类型转换器可能不太够用或者不太合适，我们需要添加类型转换器

```xml
<mvc:annotation-driven conversion-service="conversionService"></mvc:annotation-driven>
<bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
        <property name="converters">
            <!--DateTimeConvert为自定义的转换器 -->
            <bean class="com.beitie.handler.convert.DateTimeConvert"></bean>
        </property>
</bean>
```

```java
// 是所有的类型转换器都需要实现Converter接口，然后加入converts中即可
public class DateTimeConvert  implements Converter<String,Date>{
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public Date convert(String s) {
        try {
            Date date = sdf.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

数组绑定

```java
@RequestMapping("/deleteUsersByIds")
public String deleteUsersByIds(Integer[] ids){
    userService.deleteUsersByIds(ids);
    return "redirect:findAllUserInfo.action";
}
```



#### 3、返回值类型

**ModelAndView、 String、void**

```java
@RequestMapping("/getUserInfoById")
public String getUserInfoById(Model model){
    User user = userService.getUserByUid(1);
    model.addAttribute("userInfo",user);
//    return "singleUserInfo";
//    return "forward:getUserInfoById";  //请求转发
    return "redirect:getUserInfoById";		//重定向
}
```

```java
@RequestMapping("/updateUserInfoById")
public ModelAndView updateUserInfoById(User user){
    userService.updateUserById(user);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("singleUserInfo");
    modelAndView.addObject("userInfo",user);
    return modelAndView;
}
```

```java
@RequestMapping("/addUserInfo")
public void addUserInfo(HttpServletRequest request, HttpServletResponse response,User user) throws ServletException, IOException {
    userService.saveUserInfo(user);
    request.setAttribute("id",user.getUid());
    request.getRequestDispatcher("/jsp/singleUserInfo.jsp").forward(request,response);
    // response.sendRedirect("/jsp/singleUserInfo.jsp");
}
```

#### 4、数据回显

#### 5、上传图片

#### 6、json数据交互

#### 7、RESTful支持

### 二、springmvc 和struts的区别

1.分别基于方法（springmvc）和基于类（struts）进行开发的，因此springmvc的bean可以设置为单例，也应该设置为单例，但是struts就必须设置为非单例

2.struts2速度慢在于struts标签的使用。

相比较而言，二者都可以，但是struts相对漏洞较多



### 三、springmvc和mybatis的整合

### 四、Mybatis开发

### 1、配置文件mybatis.xml

configuration（配置）

- [properties（属性）](https://mybatis.org/mybatis-3/zh/configuration.html#properties)

- [settings（设置）](https://mybatis.org/mybatis-3/zh/configuration.html#settings)

- [typeAliases（类型别名）](https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases)

- [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)

- [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)

- [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)

- environments（环境配置）

  - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）

- [databaseIdProvider（数据库厂商标识）](https://mybatis.org/mybatis-3/zh/configuration.html#databaseIdProvider)

- [mappers（映射器）](https://mybatis.org/mybatis-3/zh/configuration.html#mappers)

  

##### 属性配置

  ~~~xml
  <properties resource="org/mybatis/example/config.properties">
    <property name="username" value="dev_user"/>
    <property name="password" value="F2Fa3!33TYyg"/>
    <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- 启用默认值特性 配合${driver:defaultDriver}配置使用-->
  </properties>
  <!--属性的使用 -->
  <dataSource type="POOLED">
    <property name="driver" value="${driver:defaultDriver}"/>
    <property name="url" value="${url}"/>
    <property name="username" value="${username}"/>
    <property name="password" value="${password}"/>
  </dataSource>
  <!--${driver:defaultDriver} 的意思是如果没有配置driver属性，name就会使用defaultDriver默认值 -->
  ~~~

##### setting配置

  ```xml
  <settings>
      <setting name="logImpl" value="STDOUT_LOGGING" />
  </settings>
  ```

##### typeAliases别名配置

```xml
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
</typeAliases>
```

或者

```java
@Alias("author")
public class Author {
    ...
}
```

当然还可以通过mapper的扫描器来扫描指定的包，默认的扫描是类的首字母小写。

##### typeHandlers

MyBatis 在设置预处理语句（PreparedStatement）中的参数（将参数转换为JdbcType）或从结果集中取出一个值时， 都会用类型处理器将获取到的值以合适的方式转换成 Java 类型,需要实现org.apache.ibatis.type.TypeHandler EnumTypeHandler用来捶enum的转换

##### environments

```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

##### mappers

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```

```xml
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
```

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

```xml
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

##### 动态sql

- if

- choose (when, otherwise)

- trim (where, set)

- foreach

```xml
<sql id="restrictionSql">
    <!--where关键字使用：当条件一个都没有时，where关键字会自动不加入；当and关键字位于where关键字内部，会自动判定当前的and是否需要加入-->
     <where>
         <!--uname是传递过来的参数，或者当前直接参数对象属性-->
        <if test="uname != null" >
            <!--and 位于where语句中时会自动判定当前是否为第一个，进而来判断是否确定and 或者or使用-->
            AND uname = #{uname}
        </if>
        <if test="age != null">
            AND age = #{age}
        </if>
    </where>
</sql>
<select id="findUsersByRestriction" resultType="user" >
    select * from Users
    <include refid="restrictionSql"></include>
</select>
```

```xml
<sql id="chooseWhenRestriction">
    <where>
        <!-- 只选择其中一个条件 -->
        <choose>
            <when test="uname != null">
                AND  uname = #{uname}
            </when>
            <when test="age != null">
                AND age = #{age}
            </when>
            <otherwise>
                AND uname = '张倍'
            </otherwise>
        </choose>
    </where>
</sql>
<select id="findUsersByRestriction" resultType="user" >
    select * from Users
    <include refid="chooseWhenRestriction"></include>
</select>
```

```xml
<insert id="saveUserInfos">
    INSERT INTO users(uname,age,grade,sex) VALUES
    <foreach collection="userList" item="user" separator=",">
        (#{user.uname},#{user.age},#{user.grade},#{user.sex})
    </foreach>
</insert>
<!-- 
	你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 foreach。当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值 
-->
<!--
    如果传入的是单参数且参数类型是一个List的时候，collection属性值为list .
    如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array .
    如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可以封装成map，实际上如果你在传入参数的时候，在MyBatis里面也是会把它封装成一个Map的，map的key就是参数名，所以这个时候collection属性值就是传入的List或array对象在自己封装的map里面的key.
-->

<!--
 注意 你可以将一个 List 实例或者数组作为参数对象传给 MyBatis，当你这么做的时候，MyBatis 会自动将它包装在一个 Map 中并以名称为键。List 实例将会以“list”作为键，而数组实例的键将是“array”。
-->

 	<sql id="idsRestriction">
        <where>
            and uid in
            <foreach collection="array" item="id" open="(" close=")" separator=",">
              #{id}
            </foreach>

        </where>
    </sql>
	<delete id="deleteUsersByIds">
        delete from users
        <include refid="idsRestriction"/>
    </delete>
```

