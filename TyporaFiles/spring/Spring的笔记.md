## spring的三大要点：

### 1.IOC  Inverse of Control 控制反转

控制反转是一种思想，一种不用程序员来进行操作bean而是有spring来管理bean（生成、销毁、装配等）

### 2.DI Dependency Injection 依赖注入

依赖注入是控制反转的一种实现

### 3.Aop 

Aspect Oriented Programming（面向切面编程）



-----------------------------------------------------------------------------------------------------------------------------------------------------------

### 4.spring中几种配置方式

#### 4.1.注解配置

**@Componen**t 一般性的标记一个bean

```java
@Componet
public class VideoPlayerServiceImpl implements VideoPlayerService {
......
}
```

**@Service**	service类的标记

```java
@Service
public class VideoPlayerServiceImpl implements VideoPlayerService {
......
}
```

**@Repository** 持久层dao的标记

**@Controller** 控制层的标记

**@Autowired** 自动装配，首先按照类型进行装配，如果发现多个bean，然后再按照name进行装配

```java
@Service
public class VideoPlayerServiceImpl implements VideoPlayerService {

    @Autowired
    @Qualifier("videoPlayerDaoNormal")
    private VideoPlayerDao videoPlayerDao;
    ......
}
```

这四个功能是一样的，不同的标记更加清晰的表明当前的bean属于什么类型

#### 4.2.java类配置	 javaConfig配置

**@Configuration**  表明该类是一个配置类

**@ComponentScan** 用来扫描所需扫描的包

```java
@Configuration
@ComponentScan(basePackages = "com.beitie")
public class AppConfig {
.......
}
```



**@Bean**	在配置类中的方法前使用，该方法的返回值为需要的bean

**@Scope**  常见的singleton和prototype两个值（分别为单例和多例）

**@Qulifier** 在声明或者装配一个类的时候使用，常常和@Autowired一起使用



#### 4.3.xml类配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.beitie"></context:component-scan>
    <bean id="videoPlayerDaoNormal2" class="com.beitie.dao.impl.VideoPlayerDaoNormal"></bean>
</beans>
```

**c名称空间** 

​		用于构造器参数的注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:c="http://www.springframework.org/schema/c"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.beitie"></context:component-scan>
    <bean id="videoPlayerDaoNormal" class="com.beitie.dao.impl.VideoPlayerDaoNormal"></bean>
    <bean id="videoPlayerDaoLight" class="com.beitie.dao.impl.VideoPlayerDaoLight"></bean>
    <bean id="videoPlayerServiceNormal" class="com.beitie.service.impl.VideoPlayerServiceNormal" c:videoPlayerDao-ref="videoPlayerDaoNormal"></bean>
    <bean id="videoPlayerServiceLight" class="com.beitie.service.impl.VideoPlayerServiceNormal" c:videoPlayerDao-ref="videoPlayerDaoLight"></bean>
</beans>
```

**p名称空间**

​		用于属性的注入，通过属性的set方法进行注入（必须要有set方法）



```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:c="http://www.springframework.org/schema/c"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">
    <context:component-scan base-package="com.beitie"></context:component-scan>
    <bean id="videoPlayerDaoNormal" class="com.beitie.dao.impl.VideoPlayerDaoNormal"></bean>
    <bean id="videoPlayerDaoLight" class="com.beitie.dao.impl.VideoPlayerDaoLight"></bean>
    <bean id="videoPlayerServiceNormal" class="com.beitie.service.impl.VideoPlayerServiceNormal" c:videoPlayerDao-ref="videoPlayerDaoNormal"></bean>
    <bean id="videoPlayerServiceLight" class="com.beitie.service.impl.VideoPlayerServiceNormal" c:videoPlayerDao-ref="videoPlayerDaoLight"></bean>
    <bean id="music1" class="com.beitie.bean.Music" p:author="周杰伦" p:name="东风破" p:time="270"></bean>
    <bean id="music2" class="com.beitie.bean.Music" p:author="周杰伦" p:name="稻香" p:time="300"></bean>
    <bean id="music3" class="com.beitie.bean.Music" p:author="周杰伦" p:name="千里之外" p:time="240"></bean>
	<!-- uitl空间可以进行集合的注入，将一个集合作为一个引用对象，被其他bean所引用 -->
    <util:list id = "musicList">
        <ref bean="music1"></ref>
        <ref bean="music2"></ref>
        <ref bean="music3"></ref>
    </util:list>
    <bean id="videoPlayer" class="com.beitie.bean.VideoPlayer" p:musics-ref="musicList"/>

</beans>
```

##### **工厂模式bean配置方式**

**1.静态方法模式**

**2.普通方法模式**

```java
public class BeanFactoryLocal {
    public static Music createStaticMusic(){
        return new Music();
    }
    public Music createNormalMusic(){
        return new Music();
    }
}
```

```xml
<bean id="staticMusic" class="com.beitie.factory.BeanFactoryLocal" factory-method="createStaticMusic"></bean>
<bean id="normalMusic" factory-bean="beanFactoryLocal" factory-method="createNormalMusic"></bean>
<bean id="beanFactoryLocal" class="com.beitie.factory.BeanFactoryLocal" ></bean>
```

以上涵盖了**集合的注入**、**p空间**和**c空间**以及**uitl空间**的使用，

其中uitl空间可以进行集合的注入，将一个集合作为一个引用对象，被其他bean所引用



**@Primary** 注释在bean上面可以作为装配bean的首选项





### JUNIT测试

```java
@RunWith(SpringJUnit4ClassRunner.class)//spring的主启动类
//@ContextConfiguration(classes = AppConfig.class)  //加载java类配置
@ContextConfiguration("classpath:spring-simpleConfig.xml")//加载spring的xml配置
```



### id和name的区别联系

1.id和name都可以通过ApplicationContext来进行获取

2.name中可以指定多个别名，而id不能，id中指定的只能是一个整体，单独的一个

**xml中的配置**

```xml-dtd
<bean id="music1" class="com.beitie.bean.Music" p:author="周杰伦" p:name="东风破" p:time="270"></bean>
<bean name="music2" class="com.beitie.bean.Music" p:author="周杰伦" p:name="稻香" p:time="300"></bean>
<bean name="music4 music5 music3" class="com.beitie.bean.Music" p:author="周杰伦" p:name="千里之外" p:time="240"></bean>
```

**junit中的测试方法**

```java
@Test
public void getApplicationContextTest(){
    ApplicationContext applicationContextLocal = BeanFactoryLocal.applicationContextLocal;
    Music music1=(Music)applicationContextLocal.getBean("music1");
    System.out.println("歌曲名："+music1.getName()+"--作者是"+music1.getAuthor());
    Music music2=(Music)applicationContextLocal.getBean("music2");
    System.out.println("歌曲名："+music2.getName()+"--作者是"+music2.getAuthor());
    Music music4=(Music)applicationContextLocal.getBean("music4");
    System.out.println("歌曲名："+music4.getName()+"--作者是"+music4.getAuthor());
    Music music5=(Music)applicationContextLocal.getBean("music5");
    System.out.println("歌曲名："+music5.getName()+"--作者是"+music5.getAuthor());
}
```

输出的结果为：

music1--歌曲名：东风破--作者是周杰伦
music2--歌曲名：稻香--作者是周杰伦
music4--歌曲名：千里之外--作者是周杰伦
music5--歌曲名：千里之外--作者是周杰伦

music4和music5指向的是同一个bean，但是用的不同的 别名，其中不同的别名之间可以用空格、逗号（,）、分号(;)来分隔开来

## bean生命周期

~~~java
public class MyBeanFactoryPostprocessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostprocessor() {
        super();
        System.out.println("这是BeanFactoryPostProcessor实现类构造器！！");
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor调用postProcessBeanFactory方法");
        BeanDefinition bd = beanFactory.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("phone", "110");
    }
}

public class MyBeanPostprocessor implements BeanPostProcessor {

    public MyBeanPostprocessor() {
        super();
        System.out.println("这是BeanPostProcessor实现类构造器！！");
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改");
        return bean;
    }
}

public class MyInstantiationProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    public MyInstantiationProcessor() {
        super();
        System.out.println("调用MyInstantiationProcessor构造器");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用MyInstantiationProcessor的postProcessBeforeInitialization方法");
        return super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用MyInstantiationProcessor的postProcessAfterInitialization方法");
        return super.postProcessAfterInitialization(bean, beanName);
    }
}



public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {
    private String name;
    private int age;
    private String phone;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
        super();
        System.out.println("调用Person的构造器");
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("调用Person的setBeanFactory方法");
    }

    public void setBeanName(String name) {
        System.out.println("调用Person的setBeanName方法");
    }

    public void destroy() throws Exception {
        System.out.println("调用Person的destroy方法");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("调用Person的afterPropertiesSet方法");
    }

    public void myInit(){
        System.out.println("调用Person的myInit方法");
    }
    public void myDestroy(){
        System.out.println("调用Person的myDestroy方法");
    }
}

package com.beitie.spring;

public class Student {
    private String name;

}


~~~

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="myBeanFactoryPostprocessor" class="com.beitie.spring.MyBeanFactoryPostprocessor"></bean>
    <bean id="myBeanPostprocessor" class="com.beitie.spring.MyBeanPostprocessor"></bean>
    <bean id="myInstantiationProcessor" class="com.beitie.spring.MyInstantiationProcessor"></bean>
    <bean id="person" class="com.beitie.spring.Person" init-method="myInit" destroy-method="myDestroy">
        <property name="name" value="张三"></property>
        <property name="age" value="22"></property>
    </bean>
    <bean id="student" class="com.beitie.spring.Student" lazy-init="true"></bean>
    <bean id="studentPrototype" class="com.beitie.spring.Student" scope="prototype"></bean>
</beans>
```

~~~
----容器启动----
这是BeanFactoryPostProcessor实现类构造器！！
BeanFactoryPostProcessor调用postProcessBeanFactory方法
这是BeanPostProcessor实现类构造器！！
调用MyInstantiationProcessor构造器
调用Person的构造器
调用Person的setBeanName方法
调用Person的setBeanFactory方法
BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改
调用MyInstantiationProcessor的postProcessBeforeInitialization方法
调用Person的afterPropertiesSet方法
调用Person的myInit方法
BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改
调用MyInstantiationProcessor的postProcessAfterInitialization方法
----启动完成----
获取延迟加载对象student
BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改
调用MyInstantiationProcessor的postProcessBeforeInitialization方法
BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改
调用MyInstantiationProcessor的postProcessAfterInitialization方法
获取加载对象studentPrototype
BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改
调用MyInstantiationProcessor的postProcessBeforeInitialization方法
BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改
调用MyInstantiationProcessor的postProcessAfterInitialization方法
---准备关闭spring容器-----
调用Person的destroy方法
调用Person的myDestroy方法

~~~

因此我们可以看到

1、首先进入BeanFactoryPostProcessor的构造器，创建实例，然后执行其postProcessBeanFactory方法

2、然后进入BeanPostProcessor的构造器，创建实例

3、接下来进入InstantiationPostProcessor[构造器

4、实例化bean对象

5、调用person的setBeanName方法

6、调用person的setBeanFactory的方法

7、执行BeanPostProcessor的BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改

8、执行InstantiationPostProcessor的调用MyInstantiationProcessor的postProcessBeforeInitialization方法

9、执行person的afterProperties方法，设置属性

