<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ include file="../common/common_tags.jsp" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>任务管理>任务列表</title>
    <script src="/js/jquery-3.1.1.js"></script>
</head>
<body>
<script>
    function addOrUpdate(jobId){
        $.ajax({
            url:"/taskController/addJob.action?id="+jobId,
            type:"get",
            dataType:"json",
            async: false,
            complete: function(data){
                alert(data);
                //定义一个变量，用于保存结果
                var str = "";
                //插入数据
                console.log(data);
                $("div").html(str);
            },
            error: function (e){
                alert("error");
            }
        })
    }
</script>
<div id="accordion" class="accordion">
    <div class="accordion-group">
        <div class="accordion-heading">
            <div class="title">任务管理>任务列表</div>
        </div>
        <div id="addAccordion" class="accordion-body in">
            <div class="accordion-inner">
                <div style="border: 0px solid red; height: 33px;">
                    <form:form action="${path}/taskController/list.do" method="post" modelAttribute="job" cssClass="form-inline"></form:form>
                    <lt:img menuName="任务列表" moduleName="运营管理"></lt:img>
                </div>
                <table class="table table-hover table-condensed">
                    <thead>
                    <tr>
                        <th width="4%"><input id="checkAll" name="checkAll" type="checkbox" style="margin-top: 0px;">全选</th>
                        <th>任务名称</th>
                        <th>任务分组</th>
                        <th>任务描述</th>
                        <th>创建时间</th>
                        <th>更新时间</th>
                        <th>任务表达式</th>
                        <th>执行类</th>
                        <th>执行方法</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pb}" var="job" varStatus="status">
                        <tr>
                            <td><input name="checkItem" type="checkbox" value="${job.jobId}" style="margin-top: 0px;"></td>
                            <td>${job.jobName}</td>
                            <td>${job.jobGroup}</td>
                            <td>${job.jobDesc}</td>
                            <td>${job.createTime}</td>
                            <td>${job.updateTime}</td>
                            <td>${job.cronExpression}</td>
                            <td>${job.beanClass}</td>
                            <td>${job.executeMethod}</td>
                            <td>
                                <a onclick="addOrUpdate(${job.jobId})" href="javascript:void(0);">新增/更新</a>
                                <a href="${path}/taskController/itemJob.action?id=${job.jobId}">暂停</a>
                                <a href="${path}/taskController/itemJob.action?id=${job.jobId}">立即执行</a>
                                <a href="${path}/taskController/itemJob.action?id=${job.jobId}">重新运行</a>
<%--                                <lt:img menuName="任务列表" moduleName="运营管理" privilegeName="执行定时任务" clickName="立即执行"--%>
<%--                                        clickMethod="executeJob('${job.jobName}','${job.jobGroup}','${job.jobId}');"></lt:img>--%>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="10" form-id="job" class="paginationPanel"><ltPage:page pageBean="${pb}" /></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    function executeJob(name,group,id){
        $.ajax({
            type: "POST",
            url: "${path}/taskController/executeJob.do",
            data: "jobName="+name+"&jobGroup="+group+"&jobId"+id,
            success:function(data){
                showSimpleMessage("定时任务已执行，执行结果请查看详情！");
            }
        });
    }

</script>
</body>
</html>