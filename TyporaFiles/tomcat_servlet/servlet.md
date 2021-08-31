**jsp将java编译器运行之后实际上就是一个servlet**

## tomcat

tomcat是一个web和sevlet的容器

web容器：初始化项目，容器加载完成之后，就可以通过某一个url来访问计算上面的资源了

servlet容器：tomcat本身就是一个sevlet容器，用来接收请求，将请求转换为ServletRequest，ServletResponse

然后将请求交给web容器进行

Servlet

Servlet中包含了各种信息：请求的信息（请求方式，携带参数），web.xml配置的初始化参数等等。

Servlet -->> GenericServlet -->> HttpServlet

我们只需要实现HttpServlet的方法既可（doGet,doPost等等）

