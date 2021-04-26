 

### 一、语法

内容来源于https://www.runoob.com/jquery/jquery-install.html

#### 基本语法

$(selector).action();

~~~javascript
$(this).hide() // 隐藏当前元素
$("p").hide() // 隐藏所有 <p> 元素
$("p.test").hide() // 隐藏所有 class="test" 的 <p> 元素
$("#test").hide() // 隐藏 id="test" 的元素
~~~

#### 入口函数

~~~javascript
$(document).ready(function(){
    // 开始写 jQuery 代码...
 });

// 简洁写法
$(function(){
    // 开始写 jQuery 代码...
});

//javascript入口函数
window.onload = function () {
    // 执行代码
}
~~~

jQuery 入口函数与 JavaScript 入口函数的区别：

-  jQuery 的入口函数是在 html 所有标签(DOM)都加载之后，就会去执行。
-  JavaScript 的 window.onload 事件是等到所有内容，包括外部图片之类的文件加载完后，才会执行。

### 二、选择器

#### 元素选择器

用法：$("p")。eg：

~~~javascript
$(document).ready(function(){
  $("button").click(function(){
    $("p").hide();
  });
});
~~~

#### id选择器

用法：$("#test")。eg：

~~~javascript
$(document).ready(function(){
  $("button").click(function(){
    $("#test").hide();
  });
});
~~~

#### 类选择器

用法：$("#test")。eg：

~~~javascript
$(document).ready(function(){
  $("button").click(function(){
    $(".test").hide();
  });
});
~~~

j

| 语法                     | 描述                                             |
| ------------------------ | ------------------------------------------------ |
| $("*")                   | 选取所有元素                                     |
| $("this")                | 选取当前html元素                                 |
| $("p.intro")             | 选取class为intro的<p>元素                        |
| $("p:first")             | 选取符合条件的第一个元素                         |
| $("ul li:first")         | 选取第一个ul下面的第一个li元素                   |
| $("ul li:first-child")   | 选取每个ul标签下面的第一个元素                   |
| $("[href]")              | 选取带有href属性的元素                           |
| $("a[target='_blank']")  | 选取所有属性target=’_blank‘的a标签元素           |
| $("a[target!='_blank']") | 选取所有属性target!=’_blank‘的a标签元素          |
| $(":button")             | 选取所有type="button"的<input>元素和<button>元素 |
| $("tr:even")             | 选取偶数位置的元素<tr>                           |
| $("tr:odd")              | 选取奇数位置的元素<tr>                           |

**关于 : 和 [] 这两个符号的理解**
**：可以理解为种类的意思，如：p:first，p 的种类为第一个。**
**[] 很自然的可以理解为属性的意思，如：[href] 选取带有 href 属性的元素。**

~~~javascript
$("#id", ".class")  复合选择器
$("div p span")       层级选择器 //div下的p元素中的span元素
$("div>p")            父子选择器 //div下的所有p元素
$("div+p")            相邻元素选择器 //div后面的p元素(仅一个p且同级别)
$("div~p")            兄弟选择器  //div后面的所有p元素(同级别)
$(".p:last")          类选择器 加 过滤选择器  第一个和最后一个（first 或者 last）
$("#mytable td:odd")      层级选择 加 过滤选择器 奇偶（odd 或者 even）
$("div p:eq(2)")    索引选择器 div下的第三个p元素（索引是从0开始）
$("a[href='www.baidu.com']")  属性选择器
$("p:contains(test)")        // 内容过滤选择器，包含text内容的p元素
$(":empty")        //内容过滤选择器，所有空标签（不包含子标签和内容的标签）parent 相反
$(":hidden")       //所有隐藏元素 visible 
$("input:enabled") //选取所有启用的表单元素
$(":disabled")     //所有不可用的元素
$("input:checked") //获取所有选中的复选框单选按钮等
$("select option:selected") //获取选中的选项元素
~~~

```javascript
$("p").eq(N) // N 是索引号，从 0 开始 
```

### 三、事件

#### 常见事件

| 鼠标事件   | 键盘事件 | 表单事件 | 文档/窗口事件 |
| ---------- | -------- | -------- | ------------- |
| click      | keypress | submit   | load          |
| dbclick    | keydown  | change   | resize        |
| mouseenter | keyup    | focus    | scroll        |
| mouseleave |          | blur     | unload        |
| hover      |          |          |               |
| mousedown  |          |          |               |
| mouseup    |          |          |               |

#### 事件语法

用法：$("p").click(); eg:

~~~javascript
$("p").click(function(){
    // 动作触发后执行的代码!!
});
 click：单击某个元素
 dbclick：双击某个元素
 mouseenter:鼠标进入某个元素
 mouseleave：鼠标离开某个元素
 hover：鼠标悬停时间 hover事件会触发mouseenter函数和mouseleave函数
$("#p1").hover(
    function(){
        alert("你进入了 p1!");
    },
    function(){
        alert("拜拜! 现在你离开了 p1!");
    }
);
 mousedown:鼠标在元素上方时（hover）按下鼠标左键或者右键
 mouseup：鼠标在元素上方时（hover）松开鼠标左键时或者右键
 focus:当元素失去焦点时
 blur当元素失去焦点时
 $(window).keydown( function(event){
   // 通过event.which可以拿到按键代码.  如果是keypress事件中,则拿到ASCII码.
    // event.key 返回的具体的按键值
	alert(event.which);
    alert(event.key);
} );
 
 keypress 返回ascii代码65~90


 keydown	返回键盘代码
 keyup
~~~

#### 其他

event.stopPropagation：停止冒泡，也就是其父层容器事件的触发

event.stopImmediatePropagation ： 不仅停止了父层容器的事件执行，停止自身的其他事件的执行，

###  

e.preventDefault()：阻止当前事件的默认行为，比如超级链接的地址将不会进行跳转

~~~html

<div id='div' onclick='alert("div");' style="background-color: antiquewhite">
    <ul onclick='alert("ul");'>
        <li onclick='alert("li");'>test</li>
    </ul>
</div>
<a href="http://www.365mini.com">删除</a>
~~~

~~~javascript
$("li").bind({
    click : function (event) {
        alert(222);
        event.stopPropagation();
        $(this).css("width","100px");	
    }
});
 $("li").click(function (event) {
   alert(1111);
 });

$("a").click(function (e) {
     e.preventDefault();
});
~~~

注意：事件只要不进行移除，就会都存在。也就是绑定多个同一种类型的事件，都是会触发的。例如上面的alert("li")、alert(222)以及alert(1111)均会执行

### 四、效果

#### 隐藏/显示

**语法：**

~~~JavaScript
$(selector).hide(speed,callback);
$(selector).show(speed,callback);
$(selector).toggle(speed,callback);//显示时隐藏，隐藏时显示
//可选的 speed 参数规定隐藏/显示的速度，可以取以下值："slow"、"fast" 或毫秒。
//可选的 callback 参数是隐藏或显示完成后所执行的函数名称。
//下面可见中间还可以有第二个参数
~~~

~~~javascript
// eg:
$("#hide").click(function(){
  $("p").hide();
});
 
$("#show").click(function(){
  $("p").show();
});
$(document).ready(function(){
  $(".hidebtn").click(function(){
    $("div").hide(1000,"linear",function(){
      alert("Hide() 方法已完成!");
    });
  });
});
// 第二个参数是一个字符串，表示过渡使用哪种缓动函数。（译者注：jQuery自身提供"linear" 和 "swing"，其他可以使用相关的插件）。


~~~

#### 淡入淡出

语法：

~~~javascript
$(selector).fadeIn(speed,callback);
$(selector).fadeOut(speed,callback);
$(selector).fadeToggle(speed,callback);
$(selector).fadeTo(speed,opacity,callback);
//可选的 speed 参数规定隐藏/显示的速度，可以取以下值："slow"、"fast" 或毫秒。
//可选的 callback 参数是隐藏或显示完成后所执行的函数名称。
//下面可见中间还可以有第二个参数
//opacity设置为要到达的透明度

$("button").click(function(){
  $("#div1").fadeIn();
  $("#div2").fadeIn("slow");
  $("#div3").fadeIn(3000);
});
$("button").click(function(){
  $("#div1").fadeOut();
  $("#div2").fadeOut("slow");
  $("#div3").fadeOut(3000);
});
$("button").click(function(){
  $("#div1").fadeToggle();
  $("#div2").fadeToggle("slow");
  $("#div3").fadeToggle(3000);
});
~~~

​	**1.注意大小写，fadeIn()  fadeOut()  fadeToggle()  fadeTo() 大小写不能变。**

​	**2.fadeTo() 没有默认参数，必须加上 slow/fast/Time** 

#### 滑动

~~~javascript
$(selector).slideDown(speed,callback);
$(selector).slideUp(speed,callback);
$(selector).slideToggle(speed,callback);
//可选的 speed 参数规定隐藏/显示的速度，可以取以下值："slow"、"fast" 或毫秒。
//可选的 callback 参数是隐藏或显示完成后所执行的函数名称。
$("#flip").click(function(){
  $("#panel").slideDown();
});
$("#flip").click(function(){
  $("#panel").slideUp();
});
$("#flip").click(function(){
  $("#panel").slideToggle();
});
~~~



#### 动画/停止动画

~~~javascript
$(selector).animate({params},speed,callback);
// {params} 需要指定形成动画的最终的css参数
//可选的 speed 参数规定隐藏/显示的速度，可以取以下值："slow"、"fast" 或毫秒。
//可选的 callback 参数是隐藏或显示完成后所执行的函数名称。
//默认地，jQuery 提供针对动画的队列功能。
//这意味着如果您在彼此之后编写多个 animate() 调用，jQuery 会创建包含这些方法调用的"内部"队列。然后逐一运行这些 animate 调用。
$(selector).stop(stopAll,goToEnd);
//可选的 stopAll 参数规定是否应该清除动画队列。默认是 false，即仅停止活动的动画，允许任何排入队列的动画向后执行。
//可选的 goToEnd 参数规定是否立即完成当前动画。默认是 false。
$("button").click(function(){
  var div=$("div");
  div.animate({height:'300px',opacity:'0.4'},"slow");
  div.animate({width:'300px',opacity:'0.8'},"slow");
  div.animate({height:'100px',opacity:'0.4'},"slow");
  div.animate({width:'100px',opacity:'0.8'},"slow");
});

$(document).ready(function(){
  $("#flip").click(function(){
    $("#panel").slideDown(5000);
    $("#panel").slideUp(5000);
  });
  $("#stop").click(function(){
    $("#panel").stop();
  });
});
~~~



#### 链（序列）

~~~javascript
// 就是我们执行时是有先后顺序的，不会因为异步的操作而导致出现程序的关系错乱
$("#p1").css("color","red")
  .slideUp(2000)
  .slideDown(2000);
~~~

### 五、HTML

#### 捕获/设置

- text() - 设置或返回所选元素的文本内容
- html() - 设置或返回所选元素的内容（包括 HTML 标记）还有innerHTML，innerHTML为属性，二者功能一样
- val() - 设置或返回表单字段的值
- attr() -方法用于获取属性值（个性化属性）。
- removeAttr()-移除属性
- prop() -方法用于获取属性值(固有属性)。
- removeProp()-移除属性

~~~javascript
$("#btn1").click(function(){
  alert("Text: " + $("#test").text());
});
$("#btn2").click(function(){
  alert("HTML: " + $("#test").html());
});
$("#btn1").click(function(){
  alert("值为: " + $("#test").val());
});
$("button").click(function(){
    $("#runoob").attr({
        "href" : "http://www.runoob.com/jquery",
        "title" : "jQuery 教程"
    });
});


//	带有回调函数的text和html的执行
//	i：表示索引 origText：表示当前元素的文本值或者html值
$("#btn1").click(function(){
    $("#test1").text(function(i,origText){
        return "旧文本: " + origText + " 新文本: Hello world! (index: " + i + ")"; 
    });
});
 
$("#btn2").click(function(){
    $("#test2").html(function(i,origText){
        return "旧 html: " + origText + " 新 html: Hello <b>world!</b> (index: " + i + ")"; 
    });
});
// 第一个参数：属性名字
// 第二个参数：回调函数 function : i：表示索引 origText：表示当前元素的文本值或者html值
$("button").click(function(){
        $("#runoob").attr("href", function(i, origValue){
			alert(i);
            return origValue + "/jquery";
        });
});
~~~

**prop()函数的结果:**

   1.如果有相应的属性，返回指定属性值。

   2.如果没有相应的属性，返回值是空字符串。

**attr()函数的结果:**

   1.如果有相应的属性，返回指定属性值。

   2.如果没有相应的属性，返回值是 undefined。

对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。

对于HTML元素我们自己自定义的DOM属性，在处理时，使用 attr 方法。

具有 true 和 false 两个属性的属性，如 checked, selected 或者 disabled 使用prop()

#### 添加元素

- append() - 在被选元素的结尾插入内容  可以追加文本、或者某个dom元素【内部】

- prepend() - 在被选元素的开头插入内容【内部】

- after() - 在被选元素之后插入内容【同级兄弟元素】

- before() - 在被选元素之前插入内容【同级兄弟元素】

  append、prepend和before、after分别是在元素之内和元素之外进行的操作

~~~javascript
$("p").append("追加文本");
$("p").prepend("在开头追加文本");
function appendText(){
    var txt1="<p>文本-1。</p>";              // 使用 HTML 标签创建文本
    var txt2=$("<p></p>").text("文本-2。");  // 使用 jQuery 创建文本
    var txt3=document.createElement("p");
    txt3.innerHTML="文本-3。";               // 使用 DOM 创建文本 text with DOM
    $("body").append(txt1,txt2,txt3);        // 追加新元素
}
~~~

#### 删除元素

- remove() - 删除被选元素（及其子元素）
- empty() - 从被选元素中删除子元素

~~~javascript
$("#div1").remove(); //移除id为div1的元素
$("p").remove(".italic"); //移除p标签元素，限制条件为p标签的类型class=".italic"
$("#div1").empty(); //置空id为div1的元素
~~~

#### CSS类

- addClass() - 向被选元素添加一个或多个类

- removeClass() - 从被选元素删除一个或多个类

- toggleClass() - 对被选元素进行添加/删除类的切换操作

- css() - 设置或返回样式属性

~~~scss
.important
{
        font-weight:bold;
        font-size:xx-large;
}
 
.blue
{
        color:blue;
}
~~~

~~~javascript
$("button").click(function(){
  $("body div:first").addClass("important blue"); //可以同时添加多个类
});
$("button").click(function(){
  $("h1,h2,p").removeClass("blue");
});
$("button").click(function(){
  $("h1,h2,p").toggleClass("blue");
});
~~~

#### css()

~~~javascript
css("propertyname");  // 获取某个元素对象的“propertyname”的css属性值
css("propertyname","value"); //设置元素对象的css样式属性值
css({"propertyname":"value","propertyname":"value",...}); //设置多个样式
~~~

~~~javascript
$("p").css("background-color");  // 获取p元素对象的“background-color”的css属性值
$("p").css("background-color","yellow");
$("p").css({"background-color":"yellow","font-size":"200%"});
~~~

#### 尺寸

- width()
- height()
- innerWidth()
- innerHeight()
- outerWidth()
- outerHeight()

### 六、遍历

#### 祖先

- parent()

- parents()

- parentsUntil()

~~~javascript
// 获取直接父元素
$(document).ready(function(){
  $("span").parent();
});
// 获取所有父元素
$(document).ready(function(){
  $("span").parents();
});
// 获取搜所有父元素中符合过滤条件
$(document).ready(function(){
  $("span").parents("ul"); //获取所有父元素，其中父元素为ul的元素对象
});
// 获取介于两个元素之间的所有父元素
$(document).ready(function(){
  $("span").parentsUntil("div");
});

~~~

#### 后代

- children()

- find()

~~~javascript
// 返回其所有直接子元素
$(document).ready(function(){
  $("div").children();
});
// 返回其所有直接子元素且满足过滤条件的
$(document).ready(function(){
  $("div").children("p.1");
});

// 返回其所有子元素且满足过滤条件的
$(document).ready(function(){
  $("div").find("span");
});
// 返回其所有子元素
$(document).ready(function(){
  $("div").find("*");
});
~~~

#### 同胞

- siblings()
- next()
- nextAll()
- nextUntil()
- prev()
- prevAll()
- prevUntil()

~~~javascript
// 返回 <h2> 的所有同胞元素
$(document).ready(function(){
  $("h2").siblings();
});
// 返回 <h2> 的所有同胞元素中的p标签元素
$(document).ready(function(){
  $("h2").siblings("p");
});
// 返回 <h2> 的下一个同胞元素：
$(document).ready(function(){
  $("h2").next();
});
// 返回 <h2> 的所有跟随的同胞元素：
$(document).ready(function(){
  $("h2").nextAll();
});
// 返回介于 <h2> 与 <h6> 元素之间的所有同胞元素
$(document).ready(function(){
  $("h2").nextUntil("h6");
});
prev(), prevAll() & prevUntil() 和next(),preAll())以及preUntil()一样使用
~~~

#### 过滤

- first()
- last()
- eq()
- filter()
- not()

~~~javascript
// 选取符合条件的第一个元素
$(document).ready(function(){
  $("div p").first();
});
// 选取符合条件的最后一个元素
$(document).ready(function(){
  $("div p").last();
});
// 选取符合条件的第n个元素
$(document).ready(function(){
  $("p").eq(n);
});
// 选取符合筛选条件的所有元素
$(document).ready(function(){
  $("p").filter(".url");
});
// 选取不符合条件的所有元素
$(document).ready(function(){
  $("p").not(".url");
});
~~~

~~~javascript
// 二者效果一样
$("p").filter(".url").css("background-color","yellow"); 和 $("p.url").css("background-color","yellow"); 
// 二者效果一样
$("p").last().css("background-color","yellow"); 和 $("p:last").css("background-color","yellow");
// 二者效果一样
$("p").not(".url");和$("p:not(.url)");
// 实现效果一样
$("p").not(":eq(1)").css("background-color","yellow");
~~~

### 七、AJAX

#### load()

从服务器加载数据，并把返回的数据放入被选元素中。

~~~javascript
// 必需的 URL 参数规定您希望加载的 URL。
// 可选的 data 参数规定与请求一同发送的查询字符串键/值对集合。
// 可选的 callback 参数是 load() 方法完成后所执行的函数名称。
/**
* responseTxt - 包含调用成功时的结果内容
* statusTXT - 包含调用的状态
* xhr - 包含 XMLHttpRequest 对象
*
*/
$(selector).load(URL,data,callback);

$("#div1").load("demo_test.txt");
$("#div1").load("demo_test.txt #p1");
$("button").click(function(){
  $("#div1").load("demo_test.txt",function(responseTxt,statusTxt,xhr){
    if(statusTxt=="success")
      alert("外部内容加载成功!");
    if(statusTxt=="error")
      alert("Error: "+xhr.status+": "+xhr.statusText);
  });
});
~~~

#### 

#### get()/post()/ajax()/getJson()/getScript()

$.get(*URL*,data,*callback*);

$.post(*URL,data,callback*);

~~~javascript
$("button").click(function(){
  $.get("demo_test.php",function(data,status){
    alert("数据: " + data + "\n状态: " + status);
  });
});
$("button").click(function(){
    $.post("/try/ajax/demo_test_post.php",
    {
        name:"菜鸟教程",
        url:"http://www.runoob.com"
    },
    function(data,status){
        alert("数据: \n" + data + "\n状态: " + status);
    });
});
~~~

#### ajax事件

- [ajaxComplete(callback)](https://jqueryapi.net/api/88.html)

  ajax请求完成时执行

- [ajaxError(callback)](https://jqueryapi.net/api/89.html)

  ajax请求发生错误时执行

- [ajaxSend(callback)](https://jqueryapi.net/api/90.html)

  ajax请求发送前执行

- [ajaxStart(callback)](https://jqueryapi.net/api/91.html)

  ajax请求开始执行

- [ajaxStop(callback)](https://jqueryapi.net/api/92.html)

- ajax请求执行结束之时执行

- [ajaxSuccess(callback)](https://jqueryapi.net/api/93.html)

  ajax请求成功时执行

#### 其他

- [serialize()](https://jqueryapi.net/api/97.html)

序列化表格内容为字符串

~~~javascript
var data = $("#formT").serialize();
console.log(data); // name=Trump&address=WashingTon
~~~

- [serializeArray()](https://jqueryapi.net/api/98.html)

~~~javascript
var data = $("select,:text").serializeArray();
console.log(data);
/**
{name: "name", value: "Trump"}
{name: "address", value: "WashingTon"}
{name: "single", value: "Single"}
*/
~~~





### 八、其他

#### noConflict() 

防止其他的脚本也是用$作为jquery的简写

~~~javascript
$.noConflict(); // 释放对$符号的控制
jQuery(document).ready(function(){
  jQuery("button").click(function(){
    jQuery("p").text("jQuery 仍然在工作!");
  });
});

var jq = $.noConflict(); //返回值可以作为jquery的简写
jq(document).ready(function(){
  jq("button").click(function(){
    jq("p").text("jQuery 仍然在工作!");
  });
});

/**
*
*如果你的 jQuery 代码块使用 $ 简写，并且您不愿意改变这个快捷方式，那么您可以把 $ 符号作为变量传递给 ready 方法。这样就可以在函数* 内使用 $ 符号了 - 而在函数外，依旧不得不使用 "jQuery"：
*
*/
$.noConflict();
jQuery(document).ready(function($){
  $("button").click(function(){
    $("p").text("jQuery 仍然在工作!");
  });
});
~~~

### 九、JSON

#### **语法规则：**

- **数据在名称/值对中**
- **数据由逗号分隔**
- **大括号 {} 保存对象**
- **中括号 [] 保存数组，数组可以包含多个对象**

~~~javascript
// json单字段
{ "age":30 }
// json多字段
{ "name":"菜鸟教程" , "url":"www.runoob.com" }
// 混合型json（内含多个json对象）
// 访问：sitesJson.sites[0].name && sitesJson.sites[0].url
var sitesJson = {
    "sites": [
        { "name":"菜鸟教程" , "url":"www.runoob.com" }, 
        { "name":"google" , "url":"www.google.com" }, 
        { "name":"微博" , "url":"www.weibo.com" }
    ]
}
// json数组
// 访问：sitesArray[0].name && sitesArray[0].url
var sitesArray = [
    { "name":"菜鸟教程" , "url":"www.runoob.com" }, 
    { "name":"google" , "url":"www.google.com" }, 
    { "name":"微博" , "url":"www.weibo.com" }
]

// 布尔值
{ "flag":true }
// null值
{ "runoob":null }

~~~

#### 访问

可以使用“.”和“[]”来访问json对象的属性

~~~javascript
// 使用“.”
var myObj, x;
myObj = { "name":"runoob", "alexa":10000, "site":null };
x = myObj.name;
// 使用“[]”
var myObj, x;
myObj = { "name":"runoob", "alexa":10000, "site":null };
x = myObj["name"];
~~~

使用for-in来循环对象,遍历对象的属性值

~~~javascript
var myObj = { "name":"runoob", "alexa":10000, "site":null };
for (x in myObj) {
    // x为myObj的属性名name,alxea,site
    console.log("myObj--for-in--name:::"+x+"--value::::"+myObj[x]);
}
~~~

#### 重新赋值/添加属性/删除属性

~~~javascript
 var myObj = { "name":"runoob", "alexa":10000, "site":null };
                for (x in myObj) {
                    console.log("myObj--for-in--name:::"+x+"--value::::"+myObj[x]);
                    var value = myObj[x]+"01";
                    // 重新赋值
                    myObj[x] = value ;
                    console.log("myObj--for-in--name:::"+x+"--value::::"+myObj[x]);
                }
                // 添加age的属性值
                myObj["age"] = 20;
                console.log("add prop age::::"+myObj.age);

 }
~~~

#### JSON.parse()

将json对象转换为JavaScript对象

```javascript
var text ='{"name":"beibei","date":"2020-12-03"}';
var objT= JSON.parse(text,function (key, value) {
    if(key == 'date'){
        return new Date(value);
    }else{
        return value;
    }
});

console.log(text);
console.log(objT);
```

#### JSON.stringify()

将javascript对象转为json对象（json格式的字符串）

~~~javascript
 var objT = {name:"trump",age:72};
 var objJson = JSON.stringify(objT); 
 console.log(objJson);  // 返回值 (index):89 {"name":"trump","age":72}
~~~

#### getJSON

```javascript
$.getJSON(url,data,callback) // 自我感觉和get()post()方法一致，不过getJson更倾向于处理返回的json字符串
```

### 十、常用其他函数

```javascript
/**
	jquery的遍历函数
*/
$.each(items,function (i,item) {
    console.log(item)
});
```