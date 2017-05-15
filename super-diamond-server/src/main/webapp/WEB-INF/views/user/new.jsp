<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" value="userId" id="pageId" />

<c:if test="${sessionScope.message != null}">
	<div class="alert alert-error clearfix" style="margin-bottom: 5px;width: 400px; padding: 2px 15px 2px 10px;">
		${sessionScope.message}
	</div>
</c:if>

<div class="panel panel-default">
	<div class="panel-heading">新建用户</div>
	<div class="panel-body">
<form class="form-horizontal" id="userForm" style="width: 70%" method="post" action='<c:url value="/user/save" />' autocomplete="off" >
	<div class="form-group">
		<label class="col-sm-2 control-label">登录账号：</label>
		<div class="col-sm-9">
		<input type="text" class="form-control" id="userCode" name="userCode" value='<c:out value="${user.userCode}"/>' >
		<span id="userCodeTip" style="color: red"></span></div>
   	</div>
   	<div class="form-group">
		<label class="col-sm-2 control-label">用户名：</label>
		<div class="col-sm-9">
		<input type="text" class="form-control" id="userName" name="userName" value='<c:out value="${user.userName}"/>' >
		<span id="userNameTip" style="color: red"></span></div>
   	</div>
   	<div class="form-group">
		<label class="col-sm-2 control-label">密码：</label>
		<div class="col-sm-9">
		<input type="password" class="form-control" id="password" name="password" value='<c:out value="${user.password}"/>' >
		<span id="passwordTip" style="color: red"></span></div>
   	</div>
   	<div class="form-group">
		<label class="col-sm-2 control-label">密码确认：</label>
		<div class="col-sm-9">
		<input type="password" class="form-control" id="repassword" name="repassword" value='<c:out value="${user.password}"/>' >
		<span id="repasswordTip" style="color: red"></span></div>
   	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"></label>
		<div class="col-sm-9">
			<button class="btn btn-lg btn-primary" id="save" type="button">保存</button>
		</div>
	</div>
</form>
</div></div>
<script type="text/javascript">
$(document).ready(function () {
	$("#save").click(function(e) {
		$("#userCodeTip, #userNameTips, #passwordTip, #repasswordTip").text("");
		
		if(!$("#userCode").val()) {
			$("#userCodeTip").text("登录账号不能为空");
		} else if (!$("#userName").val()) {
			$("#userNameTip").text("用户名不能为空");
		} else if (!$("#password").val()) {
			$("#passwordTip").text("密码不能为空");
		} else if ($("#password").val().length<6) {
			$("#passwordTip").text("密码长度不能小于6");
		} else if (!$("#repassword").val()) {
			$("#repasswordTip").text("密码确认不能为空");
		} else if ($("#repassword").val() != $("#password").val()) {
			$("#repasswordTip").text("两次输入密码不一致");
		} else {
			$("#userForm")[0].submit();
		}
	});
});
</script>