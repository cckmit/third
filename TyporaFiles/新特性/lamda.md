# Lamda

## 使用条件

Lambda 表达式的使用前提:

- 必须有接口（不能是抽象类），接口中有且仅有一个需要被重写的抽象方法。
-  必须支持上下文推导，要能够推导出来 Lambda 表达式表示的是哪个接口中的内容。

譬如：new Thread（（） -> System out println("重写run方法");）这个地方实现的Runnable接口

~~~java
public class LamdaTest {
    public interface MathOperation{
        void operate(int a,int b);
    };
    public interface GreteService{
        void say();
    }
    @Test
    public static void main(String[] args) {
        MathOperation mathOperation=(a,b)  -> System.out.println(a*b);;
        mathOperation.operate(1,2);
        GreteService greteService=() -> System.out.println("hello,world");
        greteService.say();
    }
}
~~~

### 省略规则

- 1. 小括号中的参数类型可以省略。
- 2. 如果小括号中只有一个参数，那么可以省略小括号。
- 3. 如果大括号中只有一条语句，那么可以省略大括号，return，分号。
-    4.如果结果中有return字眼，大括号不可以省略【但是单条语句时不用写return，所以括号也可以省略】

### **注意点**

​	表达式中可以使用局部变量和成员变量，并且使用局部变量时不能修改局部变量的值，相当于局部变量被隐形的添加了final；

### 使用用途

#### **延迟执行**

~~~java
public class Demo01Logger {
    private static void log(int level, String msg) {
        if (level == 1) {
            System.out.println(msg);
        }
     }
    public static void main(String[] args) {
        String msgA = "Hello";
        String msgB = "World";
        String msgC = "Java";
        log(1, msgA + msgB + msgC);//级别1 不一定能够满足 但是 字符串连接操作还是执行了 那么字符串的拼接操作就白做了，存在性能浪费
    }
}
~~~

使用lamda

~~~java
@FunctionalInterface
public interface MessageBuilder {
    String buildMessage();
}
public class Demo02LoggerLambda {
    private static void log(int level, MessageBuilder builder) {
        if (level == 1) {
            System.out.println(builder.buildMessage());// 实际上利用内部类 延迟的原理,代码不相关 无需进入到启动代理执行
        }
    }
    public static void main(String[] args) {
        String msgA = "Hello";
        String msgB = "World";
        String msgC = "Java";
       log(2,()->{
                System.out.println("lambda 是否执行了");
                return msgA + msgB + msgC;
        });
    }
}
~~~

