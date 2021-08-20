## SPRINGMVC  AND  MYBATIS

### 一、springmvc开发

~~~reStructuredText
springmvc是spring的 一个模块，与spring不需要进行整合，是基于mvc的web框架。

执行步骤：
	1、发起请求到前端控制器（DispatherServlet）
	2、DispatherServlet调用处理器映射器chain来返回查找对应的mappedHandler
		HandlerExecutionChain mappedHandler = getHandler(processedRequest);
	3、根据mappedHandlerChain来找到合适的适配器
		HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
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

#### 1、常用xml配置

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

pojo的参数绑定

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
```



Map绑定

~~~java
class User{
    private String name;
    private int age;
    ......
        
}
~~~



users["name"]='zhangsan'

users["age"]='11'

map.put("name","zhangsan");

map.put("age","11");

map的size为2，保存入两个键值对

其实如果后面用user来接收，user的属性中也是会进行赋值的

users["a"].name=zhangsan;

users["a"].age=11;

users["b"].name="lisi";

users["b"].age=12;

map的size为2，保存入两个键值对，分别保存了一个user对象（值），对应的键分别a和b

listt和set绑定

传递参数时一样

user[1].name="zhangsan";

user[1].age="11";

user[2].name="lisi";

user[2].age="12";

list或者set的集合尺寸为2

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

#### 3、返回值类型（ModelAndView、 String、void）

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

#### 拦截器

在Spring MVC中定义一个拦截器有两种方法：实现HandlerInterceptor接口，实现WebRequestInterceptor接口.

HandlerInterceptor接口

**preHandle()方法**：执行handler前执行，用于用户的登录权限的验证

**postHandle()方法**：执行handler后执行，在渲染view前执行，用于改变要渲染的内容

**afterCompletion()**：handler执行完成后执行，用于日志、垃圾的清理

```java
/**
 *  登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    
    /**
        在执行Controller方法前拦截，判断用户是否已经登录，
        登录了就放行，还没登录就重定向到登录页面
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user");
        if (user == null){
            //还没登录，重定向到登录页面
            response.sendRedirect("/toLogin");
        }else {
            //已经登录，放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {}
}
```

```xml
<!--配置拦截器-->
<mvc:interceptors>
    <!--此处的配置拦截所有经过dispatcherservlet的请求-->
     <bean id="loginInterceptor" class="cn.zwq.springmvc.interceptor.LoginInterceptor"/>
    <mvc:interceptor>
        <!--
            mvc:mapping：拦截的路径
            /**：是指所有文件夹及其子孙文件夹
            /*：是指所有文件夹，但不包含子孙文件夹
            /：Web项目的根目录
        -->
        <mvc:mapping path="/**"/>
        <!--
            mvc:exclude-mapping：不拦截的路径,不拦截登录路径
            /toLogin：跳转到登录页面
            /login：登录操作
        -->
        <mvc:exclude-mapping path="/toLogin"/>
        <mvc:exclude-mapping path="/login"/>
        <!--class属性就是我们自定义的拦截器-->
        <bean id="loginInterceptor" class="cn.zwq.springmvc.interceptor.LoginInterceptor"/>
    </mvc:interceptor>
    <!--如果还有其他拦截器，那么还是按照上面的拦截器配置-->
</mvc:interceptors>
```


![img](SPRINGMVC  AND  MYBATIS/1460000024464170)

在上面的配置中，可在mvc:interceptors标签下配置多个拦截器其子元素 bean 定义的是全局拦截器，它会拦截所有的请求；而mvc:interceptor元素中定义的是指定元素的拦截器，它会对指定路径下的请求生效，其子元素必须按照mvc:mapping --> mvc:exclude-mapping --> bean的顺序，否则文件会报错。

WebRequestInterceptor接口

#### 异常处理

**@ControllerAdvice**

​		@ControllerAdvice标识一个类是全局异常处理类。

**@ExceptionHandler**

​		@ExceptionHandler标识一个方法为全局异常处理的方法。

**1. 使用@ExceptionHandler注解**

作用范围：在当前的Controller里面

```java
@Controller
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    public String exception(Exception e){
        e.printStackTrace();
        return "exception";
        // 可以返回静态页面，有错误信息页面（ModelAndView中添加数据），也可以直接返回json数据（@ResponseBody）
    }

    @RequestMapping("/exception")
    public void exception(){
        int i = 5/0;
    }
}
```

**2.实现HandlerExceptionResolver接口**

1. 这种方式可以实现全局的异常控制，只要在系统运行中发生异常，它都会捕获到。
2. 实现该接口，必须重写resolveException方法，该方法就是异常处理逻辑，只能返回ModelAndView 对象。

```java
@Component
public class MyGlobalException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg",e.getMessage());
        mv.setViewName("/exception");
        e.printStackTrace();
        return mv;
    }
}
```

**3.使用@ControllerAdvice注解+@ExceptionHandler注解**

​		上面说到@ExceptionHandler**需要**进行异常处理的方法必须与出错的方法在同一个Controller里面。那么当代码加入了 @ControllerAdvice，则**不需要**必须在同一个controller中了。

​		从名字上可以看出大体意思是控制器增强。 也就是说，@controlleradvice+@ExceptionHandler也可以实现全局的异常捕捉。

```java
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {

    /**
     *  捕获404异常
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ExceptionResponse notFoundException(NoHandlerFoundException e){
        log.error("资源未找到",e);
        return new ExceptionResponse<>("你好，你要的资源找不到！");
    }

    /**
     * 400——Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
     public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException  e) {
        log.error("参数解析失败", e);
        return new ExceptionResponse<>("bad request");
    }

    /**
     *  405——Method Not Allowed
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponse<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException  e){
        log.error("不支持当前请求方法",e);
        return new ExceptionResponse<>("request_method_not_supported");
    }

    /**
     * 415——Unsupported Media Type
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ExceptionResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        log.error("不支持当前媒体",e);
        return new ExceptionResponse("content_type_not_supported");
    }

    /**
     * 500：服务器内部异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//匹配对应的转台
    @ExceptionHandler
    public ExceptionResponse internalServerError(Exception e){
        log.error("服务器内部异常",e);
        return new ExceptionResponse("你好，请稍等会...");
    }
}
```

404异常的捕捉需要添加下面的配置才行

```properties
#出现错误时, 直接抛出异常
spring.mvc.throw-exception-if-no-handler-found=true
#不要为我们工程中的资源文件建立映射
spring.resources.add-mappings=false
```

#### 常用注解

**@Controller**

​		标注该类为控制器类

**@RequestMapping**

​		标注请求的地址，映射到该方法。	

**@ResponseBody**

​		把Java对象转化为json对象，这种方式用于Ajax异步请求，返回的不是一个页面而是JSON格式的数据。

**@PathVariable**

​		@PathVariable用于接收uri地址传过来的参数，Url中可以通过一个或多个{Xxx}占位符映射，通过@PathVariable可以绑定占位符参数到方法参数中，在RestFul接口风格中经常使用

```java
// 使用了多个占位符
@RequestMapping("/user/{userId}/{userName}/query")
public User query(@PathVariable("userId") Long userId, @PathVariable("userName") String userName){

}
```

**@RequestParam**

​		用于将请求中的参数传递给变量（当传递的参数和方法中定义的形参不一致时使用）



#### 返回值类型

1. 使用Map、Model和ModelMap的方式，这种方式存储的数据是在request域中

```java
@RequestMapping("/getUser")
public String getUser(Map<String,Object> map,Model model,ModelMap modelMap){
    //1.放在map里  
    map.put("name", "xq");
    //2.放在model里，一般是使用这个
    model.addAttribute("habbit", "Play");
    //3.放在modelMap中 
    modelMap.addAttribute("city", "gd");
    modelMap.put("gender", "male");
    return "userDetail";
}
```

​	2.使用request的方式

```java
@RequestMapping("/getUser")
public String getUser(Map<String,Object> map,Model model,ModelMap modelMap,HttpServletRequest request){
    //放在request里  
    request.setAttribute("user", userService.getUser());
    return "userDetail";
}
```

​	3.使用ModelAndView

```java
@RequestMapping("/getUser")  
public ModelAndView getUser(ModelAndView modelAndView) {
    mav.addObject("user", userService.getUser());  
    mav.setViewName("userDetail");  
    return modelAndView;  
}  
```

#### Session关注点

​		怎么样把ModelMap里面的数据放入session里面？

​		在类上添加`@SessionAttributes`注解将指定的Model数据存储到session中

eg：value={"currentUser","saveTime"},types={User.class,Date.class}

将key值为"currentUser","saveTime"放入modelMap中的参数，放入session中去

@ModelAttribute("currentUser")User u：将model中key值为currentUser的赋值给User



```java

@Controller  
@SessionAttributes(value={"currentUser","saveTime"},types={User.class,Date.class})  
public class SessionAttributesController { 

    @RequestMapping("/session/attributes/{id}/{name}")  
    public ModelAndView sessionAttributes(@PathVariable Integer id,@PathVariable String 	name){  
        ModelAndView mav = new ModelAndView("session");  
        mav.addObject("currentUser", new User(id,name));  
        mav.addObject("saveTime", new Date());  
        return mav;  
    }  
    @RequestMapping("/session/attributes/test")  
    public ModelAndView sessionAttributesage(@ModelAttribute("currentUser") User 		u,@ModelAttribute("saveTime") Date d){  
        System.out.println(u.getUsername());  
        System.out.println(d);  
        ModelAndView mav = new ModelAndView("session");  
        return mav;  
    }  
```



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