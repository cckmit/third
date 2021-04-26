### 简介

#### 标识

**<!DOCTYPE html>**作为开头添加在html界面作为html5的标识

#### 注意点

~~~html
1.所有的html标签包括属性均应该小写，且所有的标签均应该封闭。包括br标签在内<br/>。
2.href 和 src属性差别
	href：引用，加载过程不影响页面的渲染。
	src：引入，加载时会停止页面的渲染。
3.class属性 class="clz1 clz2 clz3 " class属性可以添加多个值，用空格隔开；而id属性只能添加一个。
4.html中的空格（包括多个换行）在渲染时只会被识别为一个空格.
5.
~~~

#### 了解标签

##### meta

​		元素通常用于指定网页的描述，关键词，文件的最后修改时间，作者，和其他元数据。

<meta> 一般放置于 <head> 区域

~~~html
<meta name="keywords" content="HTML, CSS, XML, XHTML, JavaScript"/>
<meta name="description" content="免费 Web & 编程 教程"/>
<meta name="author" content="Runoob"/>
<meta http-equiv="refresh" content="30"/>
~~~

##### link

​	标签定义了文档与外部资源之间的关，标签通常用于链接到样式表

```html
<link rel="stylesheet" type="text/css" href="mystyle.css">
// 设置页面的图标
<link rel="shortcut icon" href="lll.png"/>
```

##### 废弃标签

font center strike

##### 废弃属性

color bgcolor

### css的使用

- 内联样式- 在HTML元素中使用"style" **属性**【级别最高】
- 内部样式表 -在HTML文档头部 <head> 区域使用<style> **元素** 来包含CSS
- 外部引用 - 使用外部 CSS 文件

最好的方式是通过外部引用CSS文件.

### 掌握标签

#### img

~~~html
<img src="url" alt="some_text" width="304" height="228"/> 
<!-- alt属性用来照片无法显示时的提示文字 -->
~~~

**注意:** 假如某个 HTML 文件包含十个图像，那么为了正确显示这个页面，需要加载 11 个文件。加载图片是需要时间的，所以我们的建议是：慎用图片。

**注意:** 加载页面时，要注意插入页面图像的路径，如果不能正确设置图像的位置，浏览器无法加载图片，图像标签就会显示一个破碎的图片。

```html
<map name="planetmap">
    <!--rect:矩形 coords:（x1,y1,x2,y2） x1,y1为左上角坐标 x2,y2为右下角坐标-->
  <area shape="rect" coords="0,0,82,126" alt="Sun" href="sun.htm">
    <!--circle:圆形 coords:（x1,y1,r） x1,y1为圆心坐标 r为圆半径-->
  <area shape="circle" coords="90,58,3" alt="Mercury" href="mercur.htm">
    <!--circle:圆形 coords:（x1,y1,r） x1,y1为圆心坐标 r为圆半径-->
  <area shape="circle" coords="124,58,8" alt="Venus" href="venus.htm">
    <!--poly:多边形 coords:（x1,y1,x2,y2） x1,y1,x2,y2....依次为各个顶点的坐标-->
  <area shape="poly" coords="x1,y1,x2,y2 ......" href=url>
</map>
```

#### table

~~~html
<table border="1">
    <caption>表格标题</caption>
    <tr>
        <!--th一般用来定义表头，大多数将表头显示为粗体居中的文本 -->
        <th>Header 1</th>
        <th>Header 2</th>
    </tr>
    <tr>
        <td>row 1, cell 1</td>
        <td>row 1, cell 2</td>
    </tr>
    <tr>
        <td>row 2, cell 1</td>
        <td>row 2, cell 2</td>
    </tr>
</table>
~~~

-  **thead ---------表格的页眉**
-  **tbody ---------表格的主体**
-  **tfoot ---------定义表格的页脚**

**thead, tbody, tfoot** 相当于三间房子，每间房子都可以用来放东西。

<tr> </tr> 这个标签就是放在三间房子里面的东西，每一个 **<tr> </tr>** 就是表格一行。

表格的每一行被分为一个个单元格。

每一个单元格就是用来存放数据的，这个数据分为两种：一，数据的名称；二，数据本身。

用 **<th></th>** 表示数据的名称(标题) ,

<td></td>

表示真正的数据内容。

**跨列：colspan**

**跨行：rowspan**

**单元格内边距：cellpadding**

**单元格外边距：cellspacing**

![](https://www.runoob.com/wp-content/uploads/2014/08/learn-html5-tables-5-638.jpg)

![](https://www.runoob.com/wp-content/uploads/2014/08/table-thead-tbody-tfoot.png)

#### video

```html
<!--
video 元素允许多个 source 元素。source 元素可以链接不同的视频文件。浏览器将使用第一个可识别的格式：
controls 属性供添加播放、暂停和音量控件。
<video> 与 </video> 之间插入的内容是供不支持 video 元素的浏览器显示的：
-->
<video width="320" height="240" controls="controls">
  <source src="movie.ogg" type="video/ogg">
  <source src="movie.mp4" type="video/mp4">
Your browser does not support the video tag.
</video>

```

| 属性                                                         | 值       | 描述                                                         |
| :----------------------------------------------------------- | :------- | :----------------------------------------------------------- |
| [autoplay](https://www.w3school.com.cn/tags/att_video_autoplay.asp) | autoplay | 如果出现该属性，则视频在就绪后马上播放。                     |
| [controls](https://www.w3school.com.cn/tags/att_video_controls.asp) | controls | 如果出现该属性，则向用户显示控件，比如播放按钮。             |
| [height](https://www.w3school.com.cn/tags/att_video_height.asp) | *pixels* | 设置视频播放器的高度。                                       |
| [loop](https://www.w3school.com.cn/tags/att_video_loop.asp)  | loop     | 如果出现该属性，则当媒介文件完成播放后再次开始播放。         |
| [preload](https://www.w3school.com.cn/tags/att_video_preload.asp) | preload  | 如果出现该属性，则视频在页面加载时进行加载，并预备播放。如果使用 "autoplay"，则忽略该属性。 |
| [src](https://www.w3school.com.cn/tags/att_video_src.asp)    | *url*    | 要播放的视频的 URL。                                         |
| [width](https://www.w3school.com.cn/tags/att_video_width.asp) | *pixels* | 设置视频播放器的宽度。                                       |

#### audio

```html

<audio src="song.ogg" controls="controls">
Your browser does not support the audio tag.
</audio>
```

| 属性                                                         | 值       | 描述                                                         |
| :----------------------------------------------------------- | :------- | :----------------------------------------------------------- |
| [autoplay](https://www.w3school.com.cn/tags/att_audio_autoplay.asp) | autoplay | 如果出现该属性，则音频在就绪后马上播放。                     |
| [controls](https://www.w3school.com.cn/tags/att_audio_controls.asp) | controls | 如果出现该属性，则向用户显示控件，比如播放按钮。             |
| [loop](https://www.w3school.com.cn/tags/att_audio_loop.asp)  | loop     | 如果出现该属性，则每当音频结束时重新开始播放。               |
| [preload](https://www.w3school.com.cn/tags/att_audio_preload.asp) | preload  | 如果出现该属性，则音频在页面加载时进行加载，并预备播放。如果使用 "autoplay"，则忽略该属性。 |
| [src](https://www.w3school.com.cn/tags/att_audio_src.asp)    | *url*    | 要播放的音频的 URL。                                         |

#### 拖拽

```html
<div  id="divDrop" ondragover="allowDrop(event)" ondrop="drop(event)">

</div>
<img src="lll.png" ondragstart="drag(event)" draggable="true" id="imgT">
<script>
    // 默认情况下时不允许放进容器中，通过取消默认执行来允许放进
    function allowDrop(e) {
        console.log("allowDrop");
        e.preventDefault();
    }
    function drop(e) {
      console.log("drop");
      e.preventDefault();
      var id ="#"+e.dataTransfer.getData("Text");
      console.log(id);
      console.log($(id)[0]);
      e.target.appendChild($(id)[0]);
    }
    function drag(e) {
        console.log("drag");
        console.log(e.target);
        e.dataTransfer.setData("Text",e.target.id);
    }
</script>
```

#### cavas

~~~
Canvas 通过 JavaScript 来绘制 2D 图形。
Canvas 是逐像素进行渲染的。
在 canvas 中，一旦图形被绘制完成，它就不会继续得到浏览器的关注。如果其位置发生变化，那么整个场景也需要重新绘制，包括任何或许已被图形覆盖的对象。
~~~



#### svg

~~~
SVG 是一种使用 XML 描述 2D 图形的语言。
SVG 基于 XML，这意味着 SVG DOM 中的每个元素都是可用的。您可以为某个元素附加 JavaScript 事件处理器。
在 SVG 中，每个被绘制的图形均被视为对象。如果 SVG 对象的属性发生变化，那么浏览器能够自动重现图形。
~~~

#### address	

~~~
<address> 标签定义文档或文章的作者/拥有者的联系信息。
如果 <address> 元素位于 <body> 元素内，则它表示文档联系信息。
    如果 <address> 元素位于 <article> 元素内，则它表示文章的联系信息。
~~~

#### article

~~~
<article>
  <h1>Internet Explorer 9</h1>
  <p>Windows Internet Explorer 9（简称 IE9）于 2011 年 3 月 14 日发布.....</p>
</article>
~~~

#### 定义列表

~~~html
<h2>一个定义列表：</h2>
<!-- 
定义和用法
<dl> 标签定义了定义列表（definition list）。

<dl> 标签用于结合 <dt> （定义列表中的项目）和 <dd> （描述列表中的项目）
-->
<dl>
   <dt>计算机</dt>
   <dd>用来计算的仪器 ... ...</dd>
   <dt>显示器</dt>
   <dd>以视觉方式显示信息的装置 ... ...</dd>
</dl>
~~~

#### 页眉和页脚

~~~html
<header></header>
<footer></footer>
~~~

引用文献

~~~html
 <cite></cite>
~~~

#### base

~~~html
<head>
    <!-- base标签为img的src(相对路径)提供了 http://www.w3school.com.cn/i/-->
    <base href="http://www.w3school.com.cn/i/" />
    <!-- base标签为a提供了target属性-->
    <base target="_blank" />
</head>	
<body>
    <img src="eg_smile.gif" />
    <a href="http://www.w3school.com.cn">W3School</a>
</body>
~~~

