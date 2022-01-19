<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>任务管理>任务列表</title>

</head>
<body>
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
                    <c:forEach items="${pb.dataList}" var="job" varStatus="status">
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
                                <img src="${path}/resources/img/zengjian.png">
                                <a href="${path}/taskController/itemJob.do?jobId=${job.jobId}" >详细</a> 
                                <lt:img menuName="任务列表" moduleName="运营管理" privilegeName="执行定时任务" clickName="立即执行"
                                        clickMethod="executeJob('${job.jobName}','${job.jobGroup}','${job.jobId}');"></lt:img>
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