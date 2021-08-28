## SpringCloud

### SpringCloud与Dubbo

SpringCloud是一种微服务的服务框架，是rest的服务框架

微服务框架是一种架构模式或者一种风格，他提倡将单一的应用程序分割为若干个单独的服务，每个服务独立的运行在自己的进程中，微服务之间相互配合相互协调，为用户提供一个功能丰富、全面的服务。可以有一个非常轻量级的微服务框架来集中管理这些微服务，可以使用不同的语言编写，也可以使用不同的数据存储。

Dubbo是一种rpc（远程程序调用）框架

### SpringCloud与SpringBoot

SpringCloud是微服务的框架，而springBoot只是一种微服务的实现

### 参考api中文版

https://www.springcloud.cc/spring-cloud-dalston.html

### SpringCloud官网 

https://spring.io/projects/spring-cloud

### @RestController、@ResponseBody

@RestController是@ResponseBody和@Controller的合体，作用于类

@ResponseBody作用于方法，还可作用于参数（接收json的参数）



### 约定规则

约定  》  配置   》 编码

## 注册中心

### Eureka 尤瑞克

核心功能是注册和发现服务，是一种基于rest风格访问的框架，一种cs框架，分为服务端和客户端

####  EurekaServer和EurekaClient

##### EurekaServer

1.pom文件中引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka-server</artifactId>
    <version>1.4.7.RELEASE</version>
</dependency>
 <dependency>
     <groupId>org.springframework</groupId>
     <artifactId>springloaded</artifactId>
     <version>1.2.8.RELEASE</version>
</dependency>
```

升级

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
~~~



2.配置application.yml

```yml
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false  #不想注册中心注册自己
    fetch-registry: false         #表明自己就是注册中心，负责维护服务实例，并不需要检索本服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #服务注册需要连接的路径（接口）	
```

 defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/不能少了/eureka/的后缀

3.启动类中添加@EnableEurekaServer

##### **EurekaClient**

1.pom文件中引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
    <version>1.4.3.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
    <version>2.3.4.RELEASE</version>
</dependency>
```
升级之后

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
~~~



2.配置application.yml

```yml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka
  instance:
    instance-id: microservicecloud-dept-provider8080
    prefer-ip-address: true
```

3.启动类中加入@EnableEurekaClient注解

#### 自我保护

”好死不如赖活着“来形容：某时刻某一个服务不可以用了，Eureka不会立即清理，依旧会对该服务的信息进行保存

#### 服务发现

#### 集群配置

##### 集群和分布式

分布式：通过网络连接多个组件，通过交换信息而形成的系统。

集群：同一个组件的多个实例，形成逻辑上的整体。

#### AP原则

#### 传统ACID

代表数据库（mysql/oracle/sqlserver）

A:（Atomicity）原子性

C:（Consistency）一致性

I:（Isolation） 独立性

D:（Durability） 持久性

#### CAP(三进二）

代表数据库(mogdb/redis)(NOSQL)，cap模式只能三选二，但是分区容错性基本山是必选项，所以可用性和一致性要二选一，分为AP和CP

C:(Consistency)一致性

A:(Availability )可用性

p:（Partition Tolerance ）分区容错性

#### Eureka和Zookeeper

Eureka保证了ap 原则

某个服务器在宕机或服务网络故障时，Eureka不会像ZooKeeper那样重新选举leader，客户端会自动切断新的服务器节点，当宕机的服务器重新恢复时，又会重新纳入集群管理之中，对于它来说，只不过需要同步宕机时注册的一些服务而已。除此之外，还有自我保护机制：1.不再移除长期没有活动的服务。2.仍然接收服务的注册和查询，但是不会同步，仍然保持当前节点可用。3.网络稳定时，同步新注册的服务

Zookeeper保证了cp原则

当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30 ~ 120s, 且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的。

区别总结

Eureka可以很好的应对因网络故障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪。Eureka作为单纯的服务注册中心来说要比zookeeper更加“专业”，因为注册服务更重要的是可用性，我们可以接受短期内达不到一致性的状况。

### Consul 康素偶

作为服务的注册和发现中心

- 服务发现
- 健康检查
- Key/Value 存储
- 多数据中心

使用：

1、官网下载consul的可执行文件

2、在cmd命令下执行，执行成功后访问http://localhost:8500，看到访问界面，证明服务启动成功

```yml
consul agent -dev  # -dev表示开发模式运行，另外还有-server表示服务模式运行
```

3、开发服务端server

引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

配置yml文件

```yml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: microservicecloud-dept-provider-consul
```

启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulProducerApplication.class, args);
	}
}
```

## 负载均衡LB

#### Ribbon瑞本

基于netfix的客户端的负载均衡工具，基于http和tcp协议的客户端负载均衡工具。

pom.xml引入依赖，并在RestTemplate类前加注解@LoadBalanced

此处展示的ribbon与Eureka配合的负载均衡

第一步：添加ribbon的依赖关系

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-eureka -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-ribbon</artifactId>
</dependency>
```

```java
@Bean
@LoadBalanced // ribbon基于netfix的一种客户端开发工具，负责负载均衡
public RestTemplate getRestTemplate(){
    return new RestTemplate();
}
@RibbonClient(name = "MICROSERVICECLOUD-DEPT-PROVIDER",configuration = RandomRule.class)
public class DeptController_Consumer_80 {
    public static void main(String[] args) {
        SpringApplication.run(DeptController_Consumer_80.class,args);
    }
}
```

##### 自定义Irule

​		1.AbstractLoadBalancerRule是每个负载均衡策略需要直接继承的类，Ribbon提供的几个负载均衡策略，都继承了这个抽象类。同理，我们如果需要自定义负载均衡策略，也要继承这个抽象类。

​		2.不能放在@ComponentScan所扫描的当前包和子包下（springboot的主启动类@SpringBootApplication中引入了@ComponentScan，相当于扫描了当前包机器自包）

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
```

​		3.在主启动类添加@RibbonClient(name="微服务名称", configuration="自定义配置类.class")

**轮询策略**   

轮询策略理解起来比较简单，就是拿到所有的server集合，然后根据id进行遍历。这里的id是ip+端口，Server实体类中定义的id属性如下

```java
RoundRobinRule
```

**随机策略**

使用jdk自带的随机数生成工具，生成一个随机数，然后去可用服务列表中拉取服务节点Server。如果当前节点不可用，则进入下一轮随机策略，直到选到可用服务节点为止

```
RandomRule
```

**可用过滤策略**

~~~
AvailabilityFilteringRule
~~~

过滤掉连接失败的服务节点，并且过滤掉高并发的服务节点，然后从健康的服务节点中，使用轮询策略选出一个节点返回

**响应时间权重策略**

~~~java
WeightedResponseTimeRule
~~~

根据响应时间，分配一个权重weight，响应时间越长，weight越小，被选中的可能性越低

**并发量最小可用策略**

~~~
BestAvailableRule
~~~

选择一个并发量最小的server返回。如何判断并发量最小呢？ServerStats有个属性activeRequestCount，这个属性记录的就是server的并发量。轮询所有的server，选择其中activeRequestCount最小的那个server，就是并发量最小的服务节点

#### Feign费恩

声明式web服务客户端	，便于编写web服务客户端，feign继承了Ribbon，只需要定义接口且以声明式的方法，优雅而又简单的实现了服务调用

##### 负载均衡配置

引入pom文件

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

替代

~~~xml
 <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
~~~







yml文件配置

~~~yml
#    配置ribbon
stu-provide:
  ribbon:
 
#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #配置规则 随机
#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule #配置规则 轮询
#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RetryRule #配置规则 重试
#    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.WeightedResponseTimeRule #配置规则 响应时间权重
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.BestAvailableRule #配置规则 最空闲连接策略
    ConnectTimeout: 500 #请求连接超时时间
    ReadTimeout: 1000 #请求处理的超时时间
    OkToRetryOnAllOperations: true #对所有请求都进行重试
    MaxAutoRetriesNextServer: 2 #切换实例的重试次数
    MaxAutoRetries: 1 #对当前实例的重试次数
~~~

##### 使用方法

创建公共的service，用来定义映射到服务提供者的映射，如下，通过服务名和@RequestMapping映射到服务的提供者，这样，消费者可以通过service直接来进行服务提供者相关服务的访问，就像使用普通的service一样，非常方便

```java
@FeignClient(value = "MICROSERVICECLOUD-DEPT-PROVIDER")
public interface DeptClientService {
    @RequestMapping("/dept/add")
    void addDept(Dept dept);
    @RequestMapping("/dept/delte/{id}")
    void deleteDept(@PathVariable("id") Long id);
    @RequestMapping("/dept/update")
    void updateDept(Dept dept);
    @RequestMapping("/dept/list")
    List<Dept> findAll();
    @RequestMapping("/dept/get/{id}")
    Dept getDeptById(@PathVariable("id") Long id);
}
```

@PathVariable

识别url中携带的参数

@RequestParam

识别request请求中携带的参数

@PathParam

### Hystrix断路器	

对服务进行熔断、降级

扇出：像扇子展开一样，环环相扣（A调用B和C，B和C调用其他），一旦其中一个服务出现故障，当用户又访问此服务过多时，就会占用过多的资源

超时机制
通过网络请求其他服务器时，都必须设置超时。正常情况下，一个远程调用一般在几十毫秒内就返回了。当依赖的服务不可用，或者因为网络问题，响应时间将会变得很长（几十秒）。而通常情况下，一次远程调用对应了一个线程/进程，如果响应太慢，那个线程/进程就会得不到释放。而线程/进程都对应了系统资源，如果大量的线程/进程得不到释放，并且越积越多，服务资源就会被耗尽，从而导致自身服务不可用。

hystrix的功能：当某个服务出现异常之时，向调用方返回一个fallback（用以处理异常之时的返回提示信息）

![img](https://img-blog.csdnimg.cn/20190830100649185.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTM1MTc3OTc=,size_16,color_FFFFFF,t_70)

在Spring Cloud中，只要Hystrix在项目的classpath中，Feign就会用断路器包裹Feign客户端的所有方法，如果要禁用Hystrix则可以通过自定义feign的配置来解决。

**feign禁用Hystrix**

在Spring Cloud中，只要Hystrix在项目的classpath中，Feign就会用断路器包裹Feign客户端的所有方法，如果要禁用Hystrix则可以通过自定义feign的配置来解决。

要禁用Hystrix的接口引用该配置即可

```java
@Configuration
public class FeignConfiguration{
     @Bean
     @Scope("prototype")
     public Feign Builder feignBuilder(){
           return Feign.builder();
     }
}
```

要禁用Hystrix的接口引用该配置即可

```java
@FeignClient(name="hello",configuration=FeignConfiguration.class)
public interface HelloService{
    ......
}
```

pom文件的引入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
```

@HystrixCommand

```java
@RequestMapping("/get/{id}")
@HystrixCommand(fallbackMethod="hystrixProcess_get")
public Dept get(@PathVariable Long id){
    Dept dept=deptService.getDeptById(id);
    if(dept == null){
        throw new RuntimeException("数据不存在");
    }
    return deptService.getDeptById(id);
}
 public Dept hystrixProcess_get(@PathVariable Long id) {
        return new Dept("该部门不存在或者当前库没有该数据", id, "unknown");
 }
```



**feign禁用Hystrix**

​	**批量处理服务异常情况**FallbackFactory<T>

1、定义一个实现了FallBackFactory接口的类

```java
@Component
public class DeptClientServiceFallBackFactory implements FallbackFactory<DeptClientService> {

  @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept getDeptById(Long id) {
                return new Dept("该部门不存在或者当前库没有该数据factory", id, "unknown");
            }
.......
}
```

在对应的通用serviceClient接口处，将实现了FallbackFactory的类加入配置@FeignClient

```java
@FeignClient(value = "MICROSERVICECLOUD-DEPT-PROVIDER",fallbackFactory= DeptClientServiceFallBackFactory.class)
public interface DeptClientService {
     @RequestMapping("/dept/add")
    void addDept(Dept dept);
    @RequestMapping("/dept/delte/{id}")
    void deleteDept(@PathVariable("id") Long id);
    @RequestMapping("/dept/update")
    void updateDept(Dept dept);
    @RequestMapping("/dept/list")
    List<Dept> findAll();
    @RequestMapping("/dept/get/{id}")
    Dept getDeptById(@PathVariable("id") Long id);
}	
```



#### 服务熔断

一旦某个服务出现故障（异常条件被触发），及时熔断这个服务

#### 服务降级

为了整体负荷的考虑，当某个服务负荷过重时，应当降低符合较轻的服务等级（熔断服务），服务将不会再次调用

此时返回一个fallback，用来返回对熔断服务的处理信息。

#### hystrixDashboard

监视器、仪表盘

实心圆颜色 	绿色 》 黄色 》 橙色  》 红色	，大小代表了流量的大小

Success 成功数目  Short-Circuited 熔断数目  Bad Request 错误请求数目

Timeout 超时数	Rejected线程池拒绝数目  Failure 失败异常数

circuit  

pom文件引入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
</dependency>
```

yml文件只需要引入端口号即可

```yml
server:
  port: 9001
```

主启动类配置

```java
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class Hystrix_DashBoard_9001 {
    public static void main(String[] args) {
        SpringApplication.run(Hystrix_DashBoard_9001.class,args);
    }
}
```

##### 被监视的客户端

pom文件引入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

yml文件引入

```yml
feign:
  hystrix:
    enabled: true
```

主启动类配置

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class DeptController_Consumer_feign80 {
    public static void main(String[] args) {
        SpringApplication.run(DeptController_Consumer_feign80.class,args);
    }
}
```

注意此处必须要有@HystrixCommand(fallbackMethod="hystrixProcess_get")对服务进行处理或者同价于@HystrixCommand的批处理异常情况，否则无法监听显示

### ZUUL

zull是基于Eureka的对请求的路由【**URL**到**函数**的**映射**】和过滤，注册在Eureka集群里面，然后再访问集群中的服务。

- 统一管理微服务请求，权限控制、负载均衡、路由转发、监控、安全控制黑名单和白名单等

路由功能是代理外部请求访问到对应的微服务上面，是外部访问的统一入口

过滤功能是负责对请求的过程进行干预，实现请求验证	、服务聚合等功能。

1、简化客户端调用复杂度

使用：

pom文件中引入依赖

因为zuul要和eureka配合在集群中使用

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zuul</artifactId>
</dependency>
```

yml中的配置

```yml
zuul:
  routes:
    aaa.serviceId: microservicecloud-dept-provider # 当前需要路由映射配置的服务id
    aaa.path:  /mydept/**	# 服务映射实际应该访问的路径
    aaa.url: http://localhost/
  ignored-services: microservicecloud-dept-provider #"*"代表忽略所有的微服务 不能通过服务名字对服务进行调用
  prefix: /beitie	# 为所有访问该集群中的服务的请求统一带上前缀（显示标志）
```

启动类的配置

添加@EnableZuulProxy注解

```java
@SpringBootApplication
@EnableZuulProxy
public class ZuulAppStart_9527 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulAppStart_9527.class,args);
    }
}
```

有prefix的话，访问http://localhost:9527/beitie/tie/mydept/consumer/list，实际访问地址http://localhost/consumer/list

无prefix情况下，访问http://localhost:9527/tie/mydept/consumer/list，实际访问地址http://localhost/consumer/list

**网关的默认路由规则**

但是如果后端服务多达十几个的时候，每一个都这样配置也挺麻烦的，spring cloud zuul已经帮我们做了默认配置。默认情况下，Zuul会代理所有注册到Eureka Server的微服务，并且Zuul的路由规则如下：**`http://ZUUL_HOST:ZUUL_PORT/微服务在Eureka上的serviceId/**`会被转发到serviceId对应的微服务。

```yml
zuul:
  routes:
#    aaa.serviceId: microservicecloud-dept-provider
#    aaa.path:  /tie/mydept/**
#    aaa.url: http://localhost/
#  ignored-services: microservicecloud-dept-provider # "*"代表忽略所有的微服务
```

​		访问http://localhost:9527/microservicecloud-dept-provider/dept/list时，会自动跳转到http://()[微服务对应的ip地址或者域]):（[微服务对应的端口号]）/dept/list

#### Zuul的核心

Filter时zull的核心，用来实现对外部服务的控制。Filter的生命周期有4个，分别“pre”，“routing”，“post”，“error”，整个生命周期用下图所示：

![img](http://favorites.ren/assets/images/2018/springcloud/zuul-core.png)

##### filter类型

- **PRE：** 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
- **ROUTING：**这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
- **POST：**这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
- **ERROR：**在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。

##### Zuul中默认实现的Filter

| 类型  | 顺序 | 过滤器                  | 功能                       |
| :---- | :--- | :---------------------- | :------------------------- |
| pre   | -3   | ServletDetectionFilter  | 标记处理Servlet的类型      |
| pre   | -2   | Servlet30WrapperFilter  | 包装HttpServletRequest请求 |
| pre   | -1   | FormBodyWrapperFilter   | 包装请求体                 |
| route | 1    | DebugFilter             | 标记调试标志               |
| route | 5    | PreDecorationFilter     | 处理请求上下文供后续使用   |
| route | 10   | RibbonRoutingFilter     | serviceId请求转发          |
| route | 100  | SimpleHostRoutingFilter | url请求转发                |
| route | 500  | SendForwardFilter       | forward请求转发            |
| post  | 0    | SendErrorFilter         | 处理有错误的请求响应       |
| post  | 1000 | SendResponseFilter      | 处理正常的请求响应         |

**禁用指定的Filter**

可以在application.yml中配置需要禁用的filter，格式：

```yml
zuul:
	FormBodyWrapperFilter:
		pre:
			disable: true
```

##### 自定义Filter

实现自定义Filter，需要继承ZuulFilter的类，并覆盖其中的4个方法。

```java
public class MyFilter extends ZuulFilter {
    @Override
    String filterType() {
        return "pre"; //定义filter的类型，有pre、route、post、error四种
    }

    @Override
    int filterOrder() {
        return 10; //定义filter的顺序，数字越小表示顺序越高，越先执行
    }

    @Override
    boolean shouldFilter() {
        return true; //表示是否需要执行该filter，true表示执行，false表示不执行
    }

    @Override
    Object run() {
        return null; //filter需要执行的具体操作
    }
}
```

```java
package com.betie.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();
        String token = request.getParameter("tk");
        if(StringUtils.isBlank(token)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.set("isSuccess",true);
            ctx.setResponseBody("没有携带token访问不成功");
            ctx.getResponse().setContentType("text/html;charset=utf-8");
        }else{
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess",true);
        }
        return null;
    }
}
```

以上事例实现了访问时必须携带tk，否则系统无法进入

ctx.getResponse().setContentType("text/html;charset=utf-8");设置防止中文乱码

##### 路由熔断

当我们的后端服务出现异常的时候，我们不希望将异常抛出给最外层，期望服务可以自动进行一降级。Zuul给我们提供了这样的支持。当某个服务出现异常时，直接返回我们预设的信息。

我们通过自定义的fallback方法，并且将其指定给某个route来实现该route访问出问题的熔断处理。主要继承ZuulFallbackProvider接口来实现，ZuulFallbackProvider默认有两个方法，一个用来指明熔断拦截哪个服务，一个定制返回内容。

```java
public interface ZuulFallbackProvider {
   /**
	 * The route this fallback will be used for.
	 * @return The route the fallback will be used for.
	 */
	public String getRoute();

	/**
	 * Provides a fallback response.
	 * @return The fallback response.
	 */
	public ClientHttpResponse fallbackResponse();
}
```

后来Spring又扩展了此类，丰富了返回方式，在返回的内容中添加了异常信息，因此最新版本建议直接继承类`FallbackProvider` 

```java
package com.betie.fusing;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
@Component
public class ProducerFallback implements ZuulFallbackProvider {
    //指定拦截的服务
    @Override
    public String getRoute() {
        return "microservicecloud-dept-provider";
    }
	
    //遇到异常自动降级，怎样处理
    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "好的";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("this service unvailable".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
```

目前的zuul只支持服务级别的熔断，不支持具体url的熔断

访问需要通过服务名访问http://localhost:9527/microservicecloud-dept-provider/dept/get/11?tk=1

而不是http://localhost:9527/beitie/dept/get/11?tk=1，只支持服务级别的熔断

##### 路由重试

​	1、添加依赖

```java
<dependency>
	<groupId>org.springframework.retry</groupId>
	<artifactId>spring-retry</artifactId>
</dependency>
```

​	2、开启Zuul Retry

```yml
#是否开启重试功能
zuul.retryable=true
#对当前服务的重试次数
ribbon.MaxAutoRetries=2
#切换相同Server的次数
ribbon.MaxAutoRetriesNextServer=0
```

### Gateway

Spring Cloud Gateway 是 Spring Cloud 的一个全新项目，该项目是基于 **Spring 5.0**，**Spring Boot 2.0** 和 **Project Reactor** 等技术开发的网关，它旨在为微服务架构提供一种简单有效的统一的 API 路由管理方式。

#### 相关概念

+ 路由 route

  这是网关的基本构建块。它由一个 ID，一个目标 URI，一组断言和一组过滤器定义。如果断言为真，则路由匹配。

+ 预言 predicate

   输入类型是一个 ServerWebExchange。我们可以使用它来匹配来自 HTTP 请求的任何内容，从而来实现路由的匹配规则，例如 headers 或参数。

  ```yml
  spring:
    cloud:
      gateway:
        routes:
         - id: host_foo_path_headers_to_httpbin
          uri: http://ityouknow.com
          predicates:
          - Host=**.foo.org
          - Path=/headers
          - Method=GET
          - Header=X-Request-Id, \d+
          - Query=foo, ba.
          - Query=baz
          - Cookie=chocolate, ch.p
          - After=2018-01-20T06:06:06+08:00[Asia/Shanghai]
  ```

  

  当多个条件共存时，需要满足所有的条件才能够成功的路由

+ 过滤器 filter

  这是`org.springframework.cloud.gateway.filter.GatewayFilter`的实例，我们可以使用它修改请求和响应。

#### 预言用法

见上面 参见 http://www.ityouknow.com/springcloud/2019/01/19/spring-cloud-gateway-service.html

#### filter用法

在pre类型中的过滤器中可以处理参数校验、权限验证、流量监控、日志输出、协议转换（http转https）

在post类型的过滤器中可以做内容响应，响应header，日志输出、流量监控

```yml
server:
  port: 8888
spring:
  application:
    name: cloud-gateway-eureka
  cloud:
    gateway:
     discovery:
        locator:
         enabled: true
     routes:
     - id: add_request_parameter_route
       uri: http://localhost:9000
     #  uri: lb://MICROSERVICECLOUD-DEPT-PROVIDER 配置为这种情况时，所有的请求不用再添加服务名，直接用负载均衡策略，在MICROSERVICECLOUD-DEPT-PROVIDER服务的提供者之间进行切换。
       filters:
       - AddRequestParameter=foo, bar #为所有经过网关的请求添加参数foo=bar
       predicates:
         - Method=GET
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8000/eureka/
logging:
  level:
    org.springframework.cloud.gateway: debug
```

​	这样就会在使用网关进行调用服务时为每个请求添加foo=bar的参数

eg：http://localhost:8080/dept/list 不会进行添加，因为没有经过网关调用

http://localhost:6060/MICROSERVICECLOUD-DEPT-PROVIDER/dept/list 通过网关进行调用，会添加参数



**StripPrefix** Filter:修改请求路径的过滤器，移除

```yml
spring:
  cloud:
    gateway:
      routes:
      - id: nameRoot
        uri: http://nameservice
        predicates:
        - Path=/name/**
        filters:
        - StripPrefix=2 # 截取后面两个字符串，eg：如下面截取掉name和bar两个分割开来的路径
```

当请求**/name/bar/foo**后端匹配到的请求路径就会变成**http://nameservice/foo**

**PrefixPath** Filter：修改请求路径的过滤器，添加

**Hystrix** Filter：熔断过滤器

```yml
server:
  port: 8089

spring:
  application:
    name: spring-cloud-gateway
  cloud:
    gateway:
      routes:
        - id: service_customer
          #下游服务地址
          uri: http://127.0.0.1:8083/
          order: 0
          #网关断言匹配
          predicates:
            - Path=/gateway/**
          filters:
            #熔断过滤器
            - name: Hystrix
              args:
                name: fallbackcmd 
                fallbackUri: forward:/defaultfallback #熔断时，回调此请求http://127.0.0.1:8083/defaultfallback
            - StripPrefix=1

#熔断器配置
hystrix:
  command:
    default:
      execution:
        isolation:
          strategy: SEMAPHORE
          thread:
            timeoutInMilliseconds: 3000
  shareSecurityContext: true

#网关日志输出
logging:
  level:
    org.springframework.cloud.gateway: TRACE
    org.springframework.http.server.reactive: DEBUG
    org.springframework.web.reactive: DEBUG
    reactor.ipc.netty: DEBUG
```

**RequestRateLimiter**：限速过滤器（redis）

添加依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
        </dependency>
```

配置redis地址

```yml
server:
  redis:
    host: localhost
```

配置yml文件

```yml
spring:
  application:
    name: spring-cloud-gateway
  cloud:
    gateway:
      routes:
        - id: service_customer
          #下游服务地址
          uri: http://127.0.0.1:8083/
          order: 0
          #网关断言匹配
          predicates:
            - Path=/gateway/**
          filters:
            #熔断过滤器
            - name: Hystrix
              args:
                name: fallbackcmd
                fallbackUri: forward:/defaultfallback
            - StripPrefix=1
            #限流过滤器
            - name: RequestRateLimiter
              args:
                key-resolver: '#{@remoteAddKeyResolver}'
                # 每秒最大访问次数（放令牌桶的速率）
                redis-rate-limiter.replenishRate: 10
                # 令牌桶最大容量（令牌桶的大小）
                redis-rate-limiter.burstCapacity: 10
```

redis限流

redis-rate-limiter.replenishRate：每秒钟最大的投放量

redis-rate-limiter.burstCapacity：当前容器的最大容量，超过容量时，请求无法进入，发生异常情况，执行熔断机制，调用请求http://127.0.0.1:8083/defaultfallback

#### 使用

1、pom文件引入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

网关gateway配置两种方式yml和硬编码

yml

```yml
server:
  port: 8888
spring:
  application:
    name: cloud-gateway-eureka
  cloud:
    gateway:
     discovery:
        locator:
         enabled: true #是否与服务注册于发现组件进行结合，通过 serviceId 转发到具体的服务实例。默认为 false，设为 true 便开启通过服务中心的自动根据 serviceId 创建路由的功能
      routes: #路由配置
        - id: provider_service #无固定格式，但是要有意义，名字要唯一
          uri: http://localhost:8080 #实际上要访问的uri
          predicates:	#预言规则
            - Path=/product/* #只有匹配了,才会转向路由
          filters:
            - AddRequestParameter=foo,bar
        - id: consumer_service 
          uri: http://localhost
          predicates:
            - Path=/consumer/*
          filters:
            - AddRequestParameter=foo,bar
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8000/eureka/
logging:
  level:
    org.springframework.cloud.gateway: debug	#调整相 gateway 包的 log 级别，以便排查问题
```

硬编码javabean

```java
@Configuration
public class RouteSelfConfig {
    @Bean
    public RouteLocator buildRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("baiduSelfConfig",r -> r.path("/{guonei}").uri("http://news.baidu.com")).build();
        return routes.build();
    }
}
```

http://localhost:11000/guoji

http://localhost:11000/guonei

http://localhost:11000/game 

均可以匹配然后分别跳入

http://news.baidu.com/guoji

http://news.baidu.com/guonei

http://news.baidu.com/game

负载均衡【动态路由】

~~~yml
spring:
  application:
    name: gateway_server
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true  #开启从注册中心动态创建路由功能，利用服务名进行路由
      routes:
        - id: provider-service
#          uri: http://localhost:8080
          uri: lb://microservicecloud-dept-provider 
          # lb://微服务名称 这是动态路由，满足预言条件时自动跳入该服务，需要注意的是服务名不能使用下划线，但是可以使用数字【首字母不可以】，可以使用‘-’分隔符
          predicates:
            - Path=/product/*
          filters:
            - AddRequestParameter=foo,bar
        - id: consumer-service
#          uri: http://localhost
          uri: lb://microservice-consumer
          predicates:
            - Path=/consumer/*
          filters:
            - AddRequestParameter=foo,bar

~~~



#### 熔断、限流、重试

~~~yml
filters:
# 熔断降级配置
- name: Hystrix
  args:
    name : default
    fallbackUri: 'forward:/defaultfallback'

# hystrix 信号量隔离，3秒后自动超时
hystrix:
  command:
    default:
      execution:
        isolation:
          strategy: SEMAPHORE
          thread:
            timeoutInMilliseconds: 3000
  shareSecurityContext: true
~~~



#### WEBFLUX

#### 集成hystrix

### Sleuth 司璐思

用来跟踪一条完整的http链条，记录服务到服务间的调用，每次调用所耗用的时间

从H版起，不用配置zipkin，直接下载jar运行

1、下载并启动zipkin 参见https://www.itmuch.com/spring-cloud/zipkin-server-install/，访问http://localhost:9411

2、

a、在微服务中添加依赖支持

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
<!-- 
	zipkin中引入了sleuth，相当于整合了zipkin和sleuth
-->
~~~

b、配置yml文件

~~~yml
spring:
  application:
    name: microservice_consumer
  zipkin:
    base-url: http://localhost:9411/  #注册人zipkin的服务器中
    sleuth:		#并加入链路控制当中
      sampler:
        probablity: 1  # 抽取样品的比例
~~~

另外一种专门的配置zipkin的服务器然后在其他的微服务中进行配置

添加pom依赖

```xml
<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-sleuth</artifactId>
 </dependency>
```

配置yml文件

```yml
spring:
  application:
    name: server-provider1
server:
  port: 9000

eureka:
  client:
    serviceUrl:
      defaultZone: http://mrbird:123456@peer1:8080/eureka/,http://mrbird:123456@peer2:8081/eureka/
```

启动类加入@EnableDiscoverClient

```java
@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {
......
}
```

如此服务间的调用就会产生额外的信息

~~~tex
2018-06-25 10:13:40.921  INFO [server-provider1,939ca3c1d060ed40,939ca3c1d060ed40,false] 12516 --- [nio-9000-exec-6] c.e.demo.controller.HelloController      : 调用server-provider1的hello接口

~~~

1. `server-provider2`微服务的名称，与`spring.application.name`对应；
2. `939ca3c1d060ed40`称为**Trace ID**，在一条完整的请求链路中，这个值是固定的。观察上面的日志即可证实这一点；
3. `3f31114e88154074`称为**Span ID**，它表示一个基本的工作单元；
4. `false`表示是否要将该信息输出到Zipkin等服务中来收集和展示，这里我们还没有集成Zipkin，所以为false。

### Zipkin

分布式的任务追踪，用于狙击来自各个异构系统的实时监控数据，和微服务架构下的链路以及延时和依赖分析等问题。

引入依赖

```xml
 <dependency>
        <groupId>io.zipkin.java</groupId>
        <artifactId>zipkin-server</artifactId>
    </dependency>
    <dependency>
        <groupId>io.zipkin.java</groupId>
        <artifactId>zipkin-autoconfigure-ui</artifactId>
    </dependency>

```

### 微服务引入Zipkin

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```

微服务中yml文件添加配置

```yml
spring:
  zipkin:
    base-url: http://localhost:9100
```



### Config

分为服务端和客户端，默认使用git来存储配置文件

config为微服务框架的微服务提供集中化的外部配置支持，为不同的微服务应用提供一个中心化的外部配置

作用：

1、根据分布式的环境不同，为微服务提供不同的配置信息

2、运行期间调整配置，不需要为每个服务提供配置文件，服务会向配置中心统一拉取自己的配置信息

3、配置发生改变时，无须重启服务器就可应用新的配置

4、将配置信息以rest接口的形式暴露出去

三种读取格式：

[http://config-3344.com:3344](http://config-3344.com:3344/application-dev.yml)/**{application}-{profile}.yml**

eg：http://config-3344.com:3344/application-dev.yml

​		http://config-3344.com:3344/application-test.yml

​		http://config-3344.com:3344/application-prod.yml



***/{application}/{profile}[/{label}.yml***

http://config-8537.com:8537/microservicecloud-config-provider/test/master.yml

***/label/{application}-{profile}.yml***

http://config-8537.com:8537/master/microservicecloud-config-provider-test.yml

git@github.com:beitieforerver/second.git

https://blog.csdn.net/keyue0459/article/details/105042063

springcloud不支持openssh的私钥匙



客户端

bootstrap.yml 系统级别的，优先级别高

application.yml 用户级别的，优先级别相对较低

springcloud会创建一个BootStrap Context，作为Application Context的父级，初始化的时候BootStrap Context负责从外部加载配置并进行解析，这两个context共享一个environment。	

### Bus

​		将分布式系统节点与轻量级的消息代理框架（中间件）结合起来的框架，实现全局消息通知，程序自动刷新。由于所有的消息是被系统中所有的服务实例所共享和消费（故此称为消息总线）。在该系统中的各个实例，都可以广播一些让其他实例知道的消息。

配合config，进行程序的自动刷新功能

支持RabbitMQ和kafaka两种消息代理

![img](SpringCloud/configbus1.jpg)

一般情况下，有两种刷新，一种访问客户端进行刷新，一种访问服务端进行刷新（合理较为推荐）

原因：

破坏微服务的单一性

破坏了微服务的对等性

使用：

1、RabbitMQ环境配置

​		a.安装erlang   otp_win64_24.0

​		b.安装Rabbit

​		c.进入rabbitMq的安装目录sbin，执行如下命令,启动管理功能

~~~cmd
rabbitmq-server start
~~~

​		d.访问http://localhost:15762	默认账户名 密码 guest guest

2、添加pom.xml文件依赖

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
~~~

server端yml配置

```yml
rabbitmq:
    username: qingfeng
    password: bestbnf
    host: localhost
    port: 5672
management:
  endpoints:
    web:
      exposure:
        include: 'bus-refresh'
```

client端yml配置

~~~yml
rabbitmq: #此配置可以省略{涵盖下面的子配置}
    username: qingfeng
    password: bestbnf
    host: localhost
    port: 5672
management:
  endpoints:
    web:
      exposure:
        include: '*'
~~~

3.配置启动类启动

4.运行curl命令，用来触发 事件来提示消息总线所在系统各个实例进行刷新

~~~yml
curl -X POST http://config-8537.com:8537/actuator/bus-refresh
# config-8537.com 配置中心地址
# 8537 配置中心端口号
~~~

此时实现一处通知，处处修改

下面实现定点通知：

~~~yml
# 此处只修改了3355端口好的微服务config-client
curl -X POST http://config-8537.com:8537/actuator/bus-refresh/config-client:3355
# config-8537.com 配置中心地址
# 8537 配置中心端口号
# config-client 服务名字
# 3355 服务实例对应的端口号
~~~

### Stream

消息驱动：屏蔽底层消息中间件的差异，无须过多关注，降低切换成本，统一编程模型

通过定义bingder的这个中间层，屏蔽了不同的消息中间件的差异性，统一化处理，更加专注于业务处理

通信方式遵循发布--订阅模式	

channel：在消息通讯中实现存储和转发的媒介，通过channel对队列进行配置

Middleware:消息中间件，目前只支持RabbitMQ和kafka

Binder：应用与消息中间件的封装。	通过binder，应用可以很轻松的连接消息中间件。

@Input：通过该消息通道接受的消息进入到应用程序

@Output：发布的消息将通过该消息通道

@StringListener：监听队列，用于消费者消息的接收

@EnableBinding：信道channel和交换机exchange绑定在一起



#### 使用

pom文件

**消息提供者：**

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
~~~

**消息消费者：**其实消息提供者的依赖（spring-cloud-starter-stream-rabbit）已经涵盖了消息消费者（spring-cloud-stream-binder-rabbit）

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```

**1、RabbitMQ环境配置**

​		a.安装erlang   otp_win64_24.0

​		b.安装Rabbit

​		c.进入rabbitMq的安装目录sbin，执行如下命令,启动管理功能

~~~cmd
rabbitmq-server start
~~~

​	**消息提供者**：

```yml
server:
  port: 10001

spring:
  application:
    name: stream-rabbit-producer
  cloud:
    stream:
      binders:
        defaultRabbit:
          type: rabbit
          environment:
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings:
        output: #消息提供者
          destination: studyExchange #目的地交换机
          content-type: application/json #输出格式
          binder: defaultRabbit	#绑定给指定的rabbitMQ
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
  instance:
    instance-id: stream-rabbit-producer10001
    prefer-ip-address: true
```

**消息消费者**

```yml
server:
  port: 10002

spring:
  application:
    name: stream-rabbit-consumer
  cloud:
    stream:
      binders:
        defaultRabbit: #配置一个启用的消息代理和队列服务器
          type: rabbit
          environment:
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings:
        input:	#消息接收端
          destination: studyExchange	#放到指定的交换机上面
          content-type: application/json #数据返回格式
          binder: defaultRabbit #绑定到对应的配置好的一个消息代理和队列服务器
          group: qingfeng	#设置分组
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
  instance:
    instance-id: stream-rabbit-consumer10002
    prefer-ip-address: true
```

**消息提供者service**



```java
@EnableBinding(Source.class) //需要将消息绑定到 channel对应的exchange上面
public class JMessageSenderImpl implements JMessageSender{
    @Resource
    private MessageChannel output;
    @Override
    public String send() {
        String msg= UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(msg).build());
        System.out.println("---msg-----"+msg);
        return msg;
    }
}
```

消息消费者consumer

```java
@EnableBinding(Sink.class)	//需要将消息接受者绑定在exchange上面
public class ReceivedMsgController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)	//添加接收消息的监听，用来接收消息
    public void receiveMsg(String msg){
        System.out.println("---consumer received msg---"+msg+"\t端口号"+serverPort);
    }

}
```

这样就可以实现消息的发送和接收通过rabbitmq这个消息中间件进行了。

#### 重复消费

不同组可以重复消费，同组是竞争关系，只能有一个进行消费，可以通过分组来解决这个问题

#### **分组**：

​		默认的分组是位于不同的组

​		自定义分组：

~~~yml
bindings:
    input: 
    destination: studyExchange
    content-type: application/json
    binder: defaultRabbit
    group: qingfeng #分组 消息消费者使用分组
~~~



#### **持久化**

添加了分组的消费者，即使服务关闭，也不会错过（在宕机期间，包括服务器关闭）服务生产者已经发送的消息

