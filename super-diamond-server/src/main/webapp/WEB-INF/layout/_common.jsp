<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
    	<meta charset="utf-8">
    	<title>SuperDiamond 配置管理服务器</title>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href='<c:url value="/resources/css/bootstrap/css/style.css" />' rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap/css/bootstrap-select.css">
		<link href='<c:url value="/resources/css/bootstrap/css/bootstrap.min.css" />' rel="stylesheet">
		<script type="text/javascript" src='<c:url value="/resources/js/jquery.min.js" />'></script>
    	<script type="text/javascript" src='<c:url value="/resources/js/bootstrap.min.js" />'></script>
		<script type="text/javascript" src="/resources/js/bootstrap-select.min.js"></script>
		<script type="text/javascript" src='<c:url value="/resources/js/modal.js" />'></script>
		<script type="text/javascript" src='<c:url value="/resources/js/bootbox.min.js" />'></script>
    	<style type="text/css">
	      body {
	        padding-top: 60px;
	        padding-bottom: 40px;
	      }
	      .sidebar-nav {
	        padding-top: 9px;
			padding-bottom: 9px;
			padding-left:10px;
	      }
	    </style>
	    <script type="text/javascript">
		  	$(document).ready(function () {
		  		var menuId = $("#pageId").val();
				$("#" + menuId).addClass("active");
		  	});
		</script>
	    <decorator:head/>
	</head>

  	<body>
	<nav class="navbar topnavbar ng-scope navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<div class="navbar-header">
					<a href="#" class="navbar-brand">
						<div class="brand-logo" style="color:white">
							<span class="img-responsive">SuperDiamond 配置中心</span>
						</div>
					</a>
				</div>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<div class="pull-right">
					<p class="navbar-text" style="color:white">
						欢迎：<c:out value="${sessionScope.sessionUser.userName}"></c:out>&nbsp;&nbsp;&nbsp;
						<a href='<c:url value="/logout" />'>注销</a>
					</p>
				</div>
			</div><!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">
		<div class="row-fluid">
			<div class="col-xs-2">
				<div class="well sidebar-nav">
					<ul class="nav nav-pills nav-stacked">
						<li class="nav-header"><h3>导航菜单</h3></li>
						<li id="indexId"><a href='<c:url value="/index" />'>首页</a></li>
						<c:if test="${sessionScope.sessionUser.userCode == 'admin'}">
							<li id="userId"><a href='<c:url value="/user/index" />'>用户管理</a></li>
						</c:if>
						<li id="projectId"><a href='<c:url value="/project/index" />'>项目管理</a></li>
						<li id="clientId"><a href='<c:url value="/queryClients" />'>客户端监控</a></li>
						<li id="passwordId"><a href='<c:url value="/user/password" />'>修改密码</a></li>
					</ul>
				</div><!--/.well -->
			</div><!--/span-->
			</div>
		<div class="row-fluid">
			<div class="well col-xs-10">
					<decorator:body></decorator:body>
			</div><!--/span-->
		</div><!--/row-->
	</div>
    	<% request.getSession().removeAttribute("message"); %>
    	<% request.getSession().removeAttribute("user"); %>
    	<% request.getSession().removeAttribute("project"); %>
  	</body>
</html>