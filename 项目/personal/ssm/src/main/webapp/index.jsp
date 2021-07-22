<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <script src="jquery.js"></script>
    <script>
        $(function () {
            $("#fn1").click(function () {
                // alert($("p:first").text());
                // alert($("p:first").html());
                alert($("#text1").val("husband"));
                $("li").css("width","100px");
            });

            // $("li").bind({
            //     click : function (event) {
            //         event.stopPropagation();
            //         $(this).css("width","100px");
            //     }
            // });
            // $("li").one({
            //     click : function () {
            //     }
            // });
            $("li").click(function(e){
                console.log("li");
                console.log(e.target);
                console.log(e.currentTarget)
            });
            $("ul").click(function(e){
                console.log("ul");
                console.log(e.target);
                console.log(e.currentTarget)
            });
            $("div").click(function(e){
                console.log("div");
                console.log(e.target);
                console.log(e.currentTarget)
            });

            $("a").click(function (e) {
                e.preventDefault();
            });
        });
    </script>
    <style>
        li {
            background-color:cyan;
        }
    </style>
</head>
<body>
<header>
    这里是页眉
</header>
<div id='div'  style="background-color: antiquewhite">
    <ul >
        <li >test</li>
    </ul>
</div>

<a href="http://www.365mini.com">删除</a>

<article>
    <h1>Internet Explorer 9</h1>
    <p>Windows Internet Explorer 9（简称 IE9）于 2011 年 3 月 14 日发布.....</p>
    <address>
        Written by <a href="mailto:webmaster@example.com">Donald Duck</a>.<br>
        Visit us at:<br>
        Example.com<br>
        Box 564, Disneyland<br>
        USA
    </address>
</article>

<footer>
    这里是页脚
</footer>
</body>
</html>
