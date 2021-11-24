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
                url: "http://localhost:8080/getJsonString.action",
                dataType: "json",
                type: "get",
                data: {
                    key: "7486e10d3ca83a934438176cf941df0c",
                },
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
<h2>Hello World!</h2>
</body>
</html>
