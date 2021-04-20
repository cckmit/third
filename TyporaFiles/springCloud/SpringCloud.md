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

### 负载均衡LB

#### Ribbon瑞本

基于netfix的客户端的负载均衡工具

pom.xml引入依赖，并在RestTemplate类前加注解@LoadBalanced

此处展示的ribbon与Eureka配合的负载均衡

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
    <version>1.4.3.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-ribbon</artifactId>
    <version>1.4.7.RELEASE</version>
</dependency>
```

```java
@Bean
@LoadBalanced // ribbon基于netfix的一种客户端开发工具，负责负载均衡
public RestTemplate getRestTemplate(){
    return new RestTemplate();
}
```

自定义Irule



#### Feign费恩

声明式web服务客户端	，便于编写web服务客户端，feign继承了Ribbon，只需要定义接口且以声明式的方法，优雅而又简单的实现了服务调用

引入pom文件

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

yml文件配置

无须改变

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



### Hystrix断路器	

对服务进行熔断、降级

扇出：像扇子展开一样，环环相扣（A调用B和C，B和C调用其他），一旦其中一个服务出现故障，当用户又访问此服务过多时，就会占用过多的资源

hystrix的功能：当某个服务出现异常之时，向调用方返回一个fallback（用以处理异常之时的返回提示信息）

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

批量处理服务异常情况

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

一旦某个服务出现故障（异常条件被触发），积极熔断这个服务

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

zull是基于Eureka的对请求的路由和过滤，注册在Eureka集群里面，然后在访问集群中的服务。

路由功能是代理外部请求访问到对应的微服务上面，是外部访问的统一入口

过滤功能是负责对请求的过程进行干预，实现请求验证	、服务聚合等功能。

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



### git的常用命令

​	git是分布式版本管理工具，可以管理一个开发项目的源码历史记录

​	github是围绕git工具构建的云平台

​	git是一个开发者在本地按照的工具

​	github是一个在线服务，它可以存储并运行计算机推送的代码（git推送）



命令 

git config --list

git config --global --list

git init

touch a.txt	新建a.txt文件

cat a.txt 查看文件的内容

git log 查看日志

git status 查看当前工作空间的状态

git commit -m "提交信息"

git config --global user.name = "qingfeng"

git conifg --global user.email = "8967557002qq.com"

git remote add origin git@github.com:beitieforerver/myfirstRepository  添加服务器源

git remote rm origin  移除服务器源

git push -u origin master 向服务端提交本地数据库master

ls 列出当前目录所有的文件和文件夹

git reset --hard head^2回退两个版本

git reset --hard [版本号] 回退到某个版本号（版本号的前四位）

pwd 查看当前所在的目录位置

步骤：

1、创建一个目录，并进入目录

2、执行git init命令，在当前目录中生成一个git仓库

3、创建文件或者修改文件

4、git add . [application.xml] 添加所有的或者某个文件到index

git  remote add origin "github上面的ssh访问路径"

5、git commit -m"本次提交的信息备注"

6、git  push 推送到github对应的库

访问github服务器上文件内容格式

/{application}/ {profile}[/{labe1}]
1 {application}-{profile} .yml
/{1abe1}/{application}- {profiley. yml
/ {application}- {profile} .properties
/ {labe1}/ {application}- {profile}. properties

### Springcloud Config

分为服务端和客户端，默认使用git来存储配置文件

config为微服务框架的微服务提供集中化的外部配置支持，为不同的微服务应用提供一个中心化的外部配置

作用：

1、根据分布式的环境不同，为微服务提供不同的配置信息

2、运行期间调整配置，不需要为每个服务提供配置文件，服务会向配置中心统一拉取自己的配置信息

3、配置发生改变时，无须重启服务器就可应用新的配置

4、将配置信息以rest接口的形式暴露出去

git@github.com:beitieforerver/second.git

https://blog.csdn.net/keyue0459/article/details/105042063

springcloud不支持openssh的私钥匙



客户端

bootstrap.yml 系统级别的，优先级别高

application.yml 用户级别的，优先级别相对较低

springcloud会创建一个BootStrap Context，作为Application Context的父级，初始化的时候BootStrap Context负责从外部加载配置并进行解析，这两个context共享一个environment。	