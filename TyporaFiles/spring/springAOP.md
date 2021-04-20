### 一、目的

1. **提高代码的可重用性**
2.  **解耦**
3.  **便于程序的维护和拓展**

### 二、Aop的相关概念

 1. #### 切入点（Pointcut）

    ~~~java
    // 定义一个叫commonServicePointcut的切点
    @Pointcut("execution(* com.beitie.service..*(..))")
    public void commonServicePointcut(){
    
    }
    ~~~

    ~~~xml
    <!--  定义一个叫commonServicePointcut的切点
     等价于上面的定义  -->
    <aop:pointcut id="commonServiceAdvice" expression="execution(* com.beitie.service..*(..))"/>
    ~~~

    

    在哪些点，哪些方法上面切入，建立点与方法的对应关系

 2. #### 通知（Advice）

    ~~~java
    @Before("commonServicePointcut()")
    public void prepareWork(){
        System.out.println("正在进行预习功课。");
    }
    @After("commonServicePointcut()")
    public void reviewWork(){
        System.out.println("正在复习功课。");
    }
    @AfterReturning("commonServicePointcut()")
    ......
    @Around("commonServiceDoMethodPointcut()")
    public void aroundWork(ProceedingJoinPoint proceedingJoinPoint){
        ......
             proceedingJoinPoint.proceed();
        ......    
    }
    ~~~

    ```xml
    <aop:aspect  ref="logManagerV2">
        <aop:after method="reviewWork" pointcut-ref="commonServiceAdvice"></aop:after>
    </aop:aspect>
    ```

    在切入点的前面/后面/中间插入执行

 3. #### 切面（Aspect）

    ```java
    @Aspect
    @Component("logManagerV2")
    public class LogManagerV2 {
        .....
    }
    ```

    ```xml
    <aop:config>
        <aop:pointcut id="commonServiceAdvice" expression="execution(* com.beitie.service..*(..))"/>
        <aop:aspect  ref="logManagerV2">
            <aop:after method="reviewWork" pointcut-ref="commonServiceAdvice"></aop:after>
        </aop:aspect>
    </aop:config>
    ```

    切入点加上通知就是切面

 4. #### 织入（Weaving）

    把切面加入到对象中去，创建代理的对象的过程（静态aop）
    
    运用aspectJ在编译期间对代码进行织入。
    
    使用条件：
    
    1、使用aj编译器对代码进行编译，对应的需要aspectjtools-1.8.9.jar包进行编译
    
    2、编写aspect类，引入对应的依赖
    
    ```java
    @Component
    public aspect LogAspect {
        pointcut recordStudyLog():call(void IStudentService.study());
        pointcut recordDoLog():execution(* com.beitie.service..do*(..));
        before():recordStudyLog(){
            LogManagerV2 logManagerV2 = new LogManagerV2();
            logManagerV2.prepareWork();
            System.out.println("wife good morning");
        }
        after():recordDoLog(){
            LogManagerV2 logManagerV2 = new LogManagerV2();
            logManagerV2.reviewWork();
            System.out.println("wife good night");
        }
    }
    ```
    
    ```xml
    <dependency>
        <groupId>aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.5.4</version>
    </dependency>
    ```



### 三、SpringAOP动态代理的实现

#### JDK

通过反射来接收被代理的类，被代理的类必须实现一个接口

- InvocationHandler 接口
- Proxy.newProxyInstance()

cglib

通过继承来实现的，被代理的类不能被final修饰，否则无法代理

- MethodInterceptor 接口
- Enhancer 类

### 四、零碎知识随记

#### 1、call和execution

call是在切入点的调用前后织入

execution是在切入点内部进行代码的织入