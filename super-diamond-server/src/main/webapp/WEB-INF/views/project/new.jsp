<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" value="projectId" id="pageId" />

<c:if test="${sessionScope.message != null}">
	<div class="alert alert-warning alert-dismissible" role="alert" style="margin-bottom: 5px;width: 400px; padding: 2px 15px 2px 10px;">
			${sessionScope.message}
	</div>
</c:if>
<div class="panel panel-default">
	<div class="panel-heading">添加项目</div>
	<div class="panel-body">
<form class="form-horizontal" method="post" style="width:70%" action='<c:url value="/project/save" />' autocomplete="off" >
	<div class="form-group">
		<label class="col-sm-2 control-label">项目编码：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" name="projCode" value='<c:out value="${project.projCode}"/>' >
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">项目名称：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" name="projName" value='<c:out value="${project.projName}"/>' >
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">项目管理者：</label>
		<div class="col-sm-9">
			<input type="text" class="form-control" name="userCode" value='<c:out value="${project.userCode}"/>'>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"></label>
		<div class="col-sm-9">
			<button class="btn btn-primary" type="submit">保存</button>
		</div>
	</div>
</form>
		</div></div>