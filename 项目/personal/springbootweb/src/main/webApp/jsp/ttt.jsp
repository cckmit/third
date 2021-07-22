<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2021/4/26
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="../js/jquery.min.js"></script>
</head>
<body>
<script>
    $(function () {
        $("#finishedTask").click(function(){
            $.post("/finishedT",
                {

                },
            function (data,status) {
                console.log(data);
                var vdata=JSON.stringify(data);
                $("#content").html(vdata);
            }) ;
        });
        $("#unFinishedTask").click(function(){
            $.post("/unFinishedT",
                {

                },
                function (data,status) {
                    console.log(data);
                    var vdata=JSON.stringify(data);
                    $("#content").html(vdata);
                }) ;
        });
        $("#executeTask").click(function(){
            $.post("/execT",
                {

                },
                function (data,status) {
                    console.log(data);
                    var vdata=JSON.stringify(data);
                    $("#content").html(vdata);
                }) ;
        });
        $("#nextTask").click(function(){
            $.post("/nextT",
                {

                },
                function (data,status) {
                    console.log(data);
                    var vdata=JSON.stringify(data);
                    $("#content").html(vdata);
                }) ;
        });
    });
</script>
<h1>欢迎访问jsp页面进行测试</h1>

<button id="finishedTask" >已完成任务</button>
<button id="unFinishedTask" >待完成任务</button>
<button id="executeTask" >执行任务</button>
<button id="nextTask" >下一个任务</button>
<div id="content">

</div>

</body>
</html>
