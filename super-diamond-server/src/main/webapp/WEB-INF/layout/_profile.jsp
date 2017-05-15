<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
    	<meta charset="utf-8">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<title>SuperDiamond 配置管理服务器</title>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href='<c:url value="/resources/css/bootstrap/css/style.css" />' rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap/css/bootstrap-select.css">
		<link href='<c:url value="/resources/css/bootstrap/css/bootstrap.min.css" />' rel="stylesheet">
		<script type="text/javascript" src='<c:url value="/resources/js/jquery.min.js" />'></script>
		<script type="text/javascript" src="/resources/js/bootstrap-select.min.js"></script>
		<script type="text/javascript" src='<c:url value="/resources/js/bootstrap.min.js" />'></script>
		<script type="text/javascript" src='<c:url value="/resources/js/modal.js" />'></script>
		<script type="text/javascript" src='<c:url value="/resources/js/bootbox.min.js" />'></script>
		<script type="text/javascript" src='<c:url value="/resources/js/typeahead.bundle.js" />'></script>
    	<style type="text/css">
	      	body {
	        	padding-top: 60px;
	       	 	padding-bottom: 40px;
	      	}
	      	.sidebar-nav {
	       		padding: 9px 0;
	      	}
	      	.table td {
			    line-height: 16px;
			    font-size: 12px;
			    padding: 4px;
			}
			.form-horizontal .control-label {
			 	width: 100px
			}
			
			.form-horizontal .controls {
			    margin-left: 120px;
			}
			.input-xlarge {
				width: 360px
			}
	    </style>
	    <script type="text/javascript">
		  	$(document).ready(function () {
		  		var menuId = $("#pageId").val();
				$("#" + menuId).addClass("active");

				$('.selectpicker').selectpicker({
					'selectedText': 'cat'
				});
				$("[data-toggle='popover']").popover();
				$('#myModal').on('hidden.bs.modal',function(e){
					$(this).removeData('bs.modal');
				});
		  	});
			function history(configId,version){
				$('#myModal').modal({
					toggle:true,
					remote:'/config/history/<c:out value="${type}"/>/'+configId+'?version='+version+'&projectId=<c:out value="${projectId}"/>'
				});
			}
		</script>
	    <decorator:head/>
	</head>

  	<body>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		 aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

			</div>
		</div>
	</div>
	<nav class="navbar topnavbar ng-scope navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a href="#" class="navbar-brand">
					<div class="brand-logo" style="color:white">
						<span class="img-responsive">SuperDiamond 配置中心</span>
					</div>
				</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li id="indexId"><a href="<c:url value="/index" />">首页</a></li>
					<c:if test="${sessionScope.sessionUser.userCode == 'admin'}">
						<li id="userId"><a href="<c:url value="/user/index" />">用户管理</a></li>
					</c:if>
					<li id="projectId"><a href="<c:url value="/project/index" />">项目管理</a></li>
					<li id="clientId"><a href="<c:url value="/queryClients" />">客户端监控</a></li>
					<li id="passwordId"><a href="<c:url value="/user/password" />">修改密码</a></li>
				</ul>
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
			<decorator:body></decorator:body>
			<span class="label label-success">Versions:
				<c:if test="${type=='development'}">
					<c:out value="${project.DEVELOPMENT_VERSION}" />
				</c:if>
				<c:if test="${type=='production'}">
					<c:out value="${project.PRODUCTION_VERSION}" />
				</c:if>
				<c:if test="${type=='test'}">
					<c:out value="${project.TEST_VERSION}" />
				</c:if>
				<c:if test="${type=='staging'}">
					<c:out value="${project.PRE_VERSION}" />
				</c:if>
			</span>
    	</div>
    	<% request.getSession().removeAttribute("message"); %>
  	</body>
</html>