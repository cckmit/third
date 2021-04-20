### 一、语法

声明用{}括起来，每个样式以；结束，CSS注释以 **/\*** 开始, 以 ***/** 结束。

~~~css
/*这是个注释*/
p
{
    text-align:center;
    /*这是另一个注释*/
    color:black;
    font-family:arial;
}
~~~

### 二、样式

#### 背景

- background-color	背景颜色
- background-image  背景图片
- background-repeat
- background-attachment
- background-position

background-image：会在页面的水平或者垂直方向平铺

~~~css
body
{
	background-image:url('gradient2.png');
    background-color:#b0c4de;
    /* 不平铺 */
    background-repeat:no-repeat; 
     /* 水平平铺 */
    background-repeat:repeat-x;
     /* 垂直平铺 */
    background-repeat:repeat-y;
    /* 第一个参数 left/center/right 第二个参数 top/center/bottom */
    /* 只填写一个参数之时，默认的参数为center */
    background-position:left top;
}
~~~

#### TEXT文本

~~~css
body{
	color : red;
	/* 常用来去掉超级链接的下划线 overline/line-through/underline*/
	text-decoration : none;
    /* 文本转换 uppercase/lowercase/capitalize*/
    /* capitalize为每个单词首字母大写*/
    text-transform:uppercase;
    /* 文本的第一行缩进50px */
    text-indent:50px;
    /* ltr左到右 rtl右到左 inherit继承父元素	*/
    direction:ltr;	
    /* 字符间距设置 */
    letter-spacing:	2px;
    /* 设置英文单词间的间距 */
    word-spacing : 2px;
    /* 设置行高，90%,10px,5*/
    /* 单设数字时行高为字的高度乘以数字的值*/
    line-height;10px;
    /* 设置字体阴影 v h color*/
    text-shadow:2px 2px #ff0000;
}
~~~

#### FONT文本

~~~css
p{
    /*
    	设置几个字体名称作为一种"后备"机制，如果浏览器不支持第一种字体，将尝试下一种字体
    */
    font-family:"Times New Roman", Times, serif;
    /* normal/italic/oblique*/
    font-style:normal;
    /*
    	40px;em;100%;
    	em和px的切换为1em = 16px; 1em和字体为16px的大小一致
    */
    font-size:40px;
    /* normal/bold/bolder/数字
    为数字时400位normal，700位bold
    */
    
    font-weight:normal;
}
~~~

#### 链接a

~~~css
a:link - 正常，未访问过的链接
a:visited - 用户已访问过的链接
a:hover - 当用户鼠标放在链接上时
a:active - 链接被点击的那一刻
/*使用实例*/
a:link {text-decoration:none;}
a:visited {text-decoration:none;}
a:hover {text-decoration:underline;}
a:active {text-decoration:underline;}
~~~

#### 表格table

~~~css
table
{
    /*
    表格线合并为一条线
    */
    border-collapse:collapse;
}

table,th, td
{
    /*宽度 形状 颜色*/
    border: 1px solid black;
}
td{
    /* 布局 */
    vertical-align : top
}
~~~

#### 盒子模型

![盒子模型.gif](https://www.runoob.com/images/box-model.gif)

##### 元素的宽度和高度

![Remark](CSS/lamp.gif)**重要:** 当您指定一个 CSS 元素的宽度和高度属性时，你只是设置内容区域的宽度和高度。要知道，完整大小的元素，你还必须添加内边距，边框和边距。

下面的例子中的元素的总宽度为300

~~~css
div {
    width: 300px;
    border: 25px solid green;
    padding: 25px;
    margin: 25px;
}
~~~

最终元素的总宽度计算公式是这样的：

总元素的宽度=宽度+左填充+右填充+左边框+右边框+左边距+右边距

元素的总高度最终计算公式是这样的：

总元素的高度=高度+顶部填充+底部填充+上边框+下边框+上边距+下边距

#### 边框属性

~~~css
p.one
{
    border-style:solid;
    border-width:5px;
}
p.two
{
    border-style:solid;
    border-width:medium;
}
~~~

