Method

```java
method.isAnnotationPresent(HystrixCommand.class)  // 判断某个方法是否有Hystrix的注解
Method method = AopUtils.getMethodFromTarget(joinPoint)//从当前连接点获取当前的方法
```

