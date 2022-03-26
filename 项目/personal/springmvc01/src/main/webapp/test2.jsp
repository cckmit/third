<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script type="text/javascript">
        function showAdress()
        {
            $.ajax
            ({
                url: "http://localhost:8080/msg.action",
                dataType: "json",
                type: "post",
                data: {"name":"zhangsan"},
                success:function(res){
                    console.log(res);  //在console中查看数据
                },
                error:function(){
                    alert('failed!');
                },
            });
        }
    </script>

</head>
<body>
<button onclick="showAdress()">点击我</button>
<img src="/getImage2.action">
<h2>Hello World!</h2>
</body>
</html>
