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



git remote -v

git branch -a

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

覆盖本地文件

~~~
$ git fetch --all
$ git reset --hard origin/master 
$ git pull

或者
$ git checkout  #操作比上面简单多了
~~~





### 新机器的操作步骤Git

1、安装git客户端

2、在磁盘上新建一个文件夹 ，并执行 git init 命令，初始化数据

~~~git
git init
~~~

3、生成公钥和私钥 ssh-keygen -t rsa -C "896755700@qq.com"

~~~git
ssh-keygen -t rsa -C "896755700@qq.com"
~~~

4、在github服务器上面配置生成的ssh密匙

~~~
a. 打开你的 git bash 窗口

b. 进入 .ssh 目录：cd ~/.ssh

c. 找到 id_rsa.pub 文件：ls

d. 查看公钥：cat id_rsa.pub 或者 vim id_rsa.pub

e. 将对应的公钥添加到ssh库中
~~~



4、配置全局用户名和邮箱（便于提交文件，显示提交人的信息）

~~~git
git config --global user.name 'beitieforerver'
git config --global user.email '896755700@qq.com'
git config --global --list 
~~~

5、配置远程服务名

~~~git
git remote add orgin git@github.com:beitieforerver/third
~~~

6、从远程服务器拉取数据

~~~git
git pull orgin master
~~~

## 