<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量处理数据</title>
    <script src="../../js/jquery-3.1.1.js"></script>
    <script type="text/javascript">
        function batchDeleteUserInfo() {
            console.log(11111111111);
            document.userform.action="${pageContext.request.contextPath}/user/updateUsersForDeleteUsers.action";
            document.userform.submit();
        }
        function querySingleUserInfoInJson(uid) {
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/user/querySingleUserInfoInJson.action?uid="+uid,
                success:function (result) {
                    console.log(result);
                }
            });
        }
    </script>
</head>
<body>
<input type="button" value="批量删除" onclick="batchDeleteUserInfo()">
<form name="userform">
<table>
    <tr>
        <th width="40">选择</th>
        <th width="40">序号</th>
        <th width="80">姓名</th>
        <th width="40">年龄</th>
        <th width="180">地址</th>
        <th width="40">性别</th>
        <th width="90">年级</th>
        <th width="40">修改</th>
        <th width="40">删除</th>
        <th width="40">添加</th>
        <th width="60">json查看</th>
    </tr>
    <c:forEach var="student" items="${userInfoList}" varStatus="status">
    <tr>
        <th><input type="checkbox" name ="ids" value="${student.uid}"/></th>
        <th>${status.index}</th>
        <th>${student.uname}</th>
        <th>${student.uage}</th>
        <th>${student.uaddress}</th>
        <th>${student.usex}</th>
        <th>${student.ugrade}</th>
        <th><a href="${pageContext.request.contextPath}/user/querySingleUserInfo.action?uid=${student.uid}">修改</a></th>
        <th><a href="${pageContext.request.contextPath}/user/deleteUserInfo.action?uid=${student.uid}">删除</a></th>
        <th><a href="${pageContext.request.contextPath}/user/tiaoZhuan.action?viewname=AddUserInfo">添加</a></th>
        <th><a onclick="querySingleUserInfoInJson(${student.uid});return false;" href="#">json查看</a></th>
    </tr>
    </c:forEach>
</table>

</form>
</body>
</html>
