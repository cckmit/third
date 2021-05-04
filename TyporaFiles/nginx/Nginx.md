## 简介

nginx是一个高性能的http和反向代理的web服务器。占用内存少，并发性能好，支持50000个并发的连接数

用途：

1、正向代理和反向代理

2、静态文件处理

3、负载均衡

4、动静分离

5、高可用

## 反向代理

正向代理：客户端需要配置代理服务器

反向代理：客户端无须配置代理服务器，不会暴露真正的服务器

## nginx安装

### 一、安装编译工具及库文件

~~~c#
yum -y install make zlib zlib-devel gcc-c++ libtool  openssl openssl-devel
~~~

### 二、首先要安装 PCRE

#### 1、下载

```c#
[root@bogon src]# cd /usr/local/src/
[root@bogon src]# wget http://downloads.sourceforge.net/project/pcre/pcre/8.35/pcre-8.35.tar.gz
```

#### 2、解压安装包

~~~c#
[root@bogon src]# tar zxvf pcre-8.35.tar.gz
~~~

#### 3、进入安装包目录

```c#
[root@bogon src]# cd pcre-8.35
```

#### 4、编译安装 

```
[root@bogon pcre-8.35]# ./configure
[root@bogon pcre-8.35]# make && make install
```

#### 5、查看pcre版本

~~~c#
[root@bogon pcre-8.35]# pcre-config --version
~~~

### 三、安装nginx

#### 1、下载nginx

```c#
[root@bogon src]# cd /usr/local/src/
[root@bogon src]# wget http://nginx.org/download/nginx-1.6.2.tar.gz
```

2、解压安装包

```c#
[root@bogon src]# tar zxvf nginx-1.6.2.tar.gz
```

3、进入安装包目录

```c#
[root@bogon src]# cd nginx-1.6.2
```

4、编译安装

```c#
[root@bogon nginx-1.6.2]# ./configure --prefix=/usr/local/webserver/nginx --with-http_stub_status_module --with-http_ssl_module --with-pcre=/usr/local/src/pcre-8.35
[root@bogon nginx-1.6.2]# make
[root@bogon nginx-1.6.2]# make install
```

5、查看nginx版本

```c#
[root@bogon nginx-1.6.2]# /usr/local/webserver/nginx/sbin/nginx -v
```