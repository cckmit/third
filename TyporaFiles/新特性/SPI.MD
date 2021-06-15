# SPI

SPI 全称为 Service Provider Interface，是一种服务发现机制，有java的spi和dubbo的spi机制；本质是将实现类配置进配置文件之中，然后动态的加载改变实现类。

~~~java
private static final String SERVICES_DIRECTORY = "META-INF/services/";

private static final String DUBBO_DIRECTORY = "META-INF/dubbo/";

private static final String DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";
~~~

## java的spi

1、定义一个接口，添加接口的多个实现类

~~~~java
package com.beitie.service;
public interface Robot {
    void sayHello();
}
class OptimusPrime implements Robot {
    
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}

class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
~~~~

2、添加配置文件

在resource目录下面添加META-INF/services目录并且创建以接口的全限定名为名字的文件，例如com.beitie.service.Robot，在文件中添加下面内容

~~~
com.beitie.service.Bumblebee
com.beitie.service.OptimusPrime
~~~

3、进行测试

```java
public class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
```

## dubbo的spi

1、定义一个接口，添加接口的多个实现类

~~~java
package com.beitie.service;
public interface Robot {
    void sayHello();
}
class OptimusPrime implements Robot {
    
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}

class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
~~~

2、添加配置文件

在resource目录下面添加META-INF/dubbo目录并且创建以接口的全限定名为名字的文件，例如com.beitie.service.Robot，在文件中添加下面内容

~~~
optimusPrime = com.beitie.service.OptimusPrime
bumblebee = com.beitie.service.Bumblebee
~~~

3、进行测试

```java
public class DubboSpiTest {
    @Test
    public  void spiTest(){
        ExtensionLoader<OrderService> loader =ExtensionLoader.getExtensionLoader(OrderService.class);
        Robot optimusPrime=loader.getExtension("optimusPrime");
        Robot bumblebee=loader.getExtension("bumblebee");
        optimusPrime.sayHello();
        bumblebee.sayHello();
    }
}
```