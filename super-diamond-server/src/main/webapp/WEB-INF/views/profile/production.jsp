<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLineChar", "\r\n"); %>
<% pageContext.setAttribute("newLineChar2", "\n"); %>
<ol class="breadcrumb">
	<li><a href="/index">首页</a></li>
	<li class="active"><c:out value="${project.PROJ_NAME}"/> - <c:out value="${type}"/></li>
</ol>

<form class="form-inline" style="padding-bottom: 10px">
	<div class="form-group">
		<label for="sel-queryModule">模块：</label>
		<select id="sel-queryModule" class="selectpicker" data-live-search="true">
			<option value="">全部</option>
			<c:forEach items="${modules}" var="module">
				<option value='<c:out value="${module.moduleId}"/>' ${module.moduleId eq moduleId?'selected':''}><c:out value="${module.moduleName}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group" style="padding-left:5px;">
		<label for="confKey">KEY：</label>
		<input id="confKey" name="confKey" value="${confKey}" placeholder="KEY" class="typeahead form-control" style="width:300px;">
	</div>
	<button type="button" class="btn btn-primary" onclick="doSearch()">查询</button>
	<div class="btn-group">
		<button type="button" id="deploy" class="btn btn-primary">发布</button>
		<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
			<span class="caret"></span>
			<span class="sr-only">Toggle Dropdown</span>
		</button>
		<ul class="dropdown-menu" role="menu">
			<li><a id="preview">预览</a></li>
		</ul>
	</div>
</form>

<!-- <button type="button" id="queryModule" class="btn btn-primary">查询</button> -->

<jsp:include page="editConfigWin.jsp">
    <jsp:param name="version" value="${project.PRODUCTION_VERSION}"/>
</jsp:include>
<input type="hidden" id="moduleId" name="moduleId" value="${moduleId}"/>
<input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}"/>
<table class="table table-striped table-bordered table-hover">
  	<thead>
    	<tr>
			<th width="90">Module</th>
			<th width="80">Key</th>
			<th width="80">Value</th>
			<th>描述</th>
			<th>操作人</th>
			<th width="150">操作时间</th>
			<th width="50">操作</th>
    	</tr>
  	</thead>
  	<tbody>
    	<c:forEach items="${pagelist.result}" var="config">
    		<tr id='row-<c:out value="${config.configId}"/>'>
               	<td value='<c:out value="${config.moduleId}"/>'>
                  	<c:out value="${config.moduleName}"/>
               	</td>
               	<td value='<c:out value="${config.configKey}"/>'>
               		<c:out value="${config.configKey}"/>
               	</td>
               	<td>
					<a data-content=<c:out value="${config.configValue}"/> data-toggle="popover" tabindex="0" data-trigger="focus" data-placement="bottom">
                  	<c:if test="${fn:length(config.configValue)>30}">  
					    ${fn:substring(config.configValue,0,30)}...  
					</c:if> 
                  	<c:if test="${fn:length(config.configValue)<=30}">  
					   <c:out value="${config.configValue}"/>
					</c:if> 
					</a>
               	</td>
               	<td>
					<a data-content=<c:out value="${config.configDesc}"/> data-toggle="popover" tabindex="0" data-trigger="focus" data-placement="bottom">
                	<c:if test="${fn:length(config.configDesc)>15}">  
					    ${fn:substring(config.configDesc,0,15)}...  
					</c:if> 
                  	<c:if test="${fn:length(config.configDesc)<=15}">  
					   <c:out value="${config.configDesc}"/>
					</c:if> 
					</a>
               	</td>
               	<td>
                  	<c:out value="${config.productionUser}"/>
               	</td>
               	<td>
					<fmt:formatDate value="${config.productionTime}" pattern="yyyy-MM-dd HH:mm:ss" />
               	</td>
               	<td>
					<a href='javascript:updateConfig(<c:out value="${config.configId}"/>)' title="更新"><i class="glyphicon glyphicon-edit"></i></a>
					<a href='javascript:history("${config.configId}","${project.PRODUCTION_VERSION}")' title="历史记录"><i class="glyphicon glyphicon-leaf"></i></a>
				</td>
          	</tr>
     	</c:forEach>
	</tbody>
</table>
<div class="pull-right">
	<jsp:include page="../../layout/_pagination.jsp"></jsp:include>
</div>
<script type="text/javascript">
$(document).ready(function () {
	$("#preview").click(function(e) {
		window.location.href = '/profile/preview/<c:out value="${project.PROJ_CODE}"/>/<c:out value="${type}"/>?projectId=<c:out value="${projectId}"/>';
	});
	
	$("#deploy").click(function(e) {
		window.location.href = '/profile/deploy/<c:out value="${project.PROJ_CODE}"/>/<c:out value="${type}"/>?projectId=<c:out value="${projectId}"/>';
	});
	
	$("#sel-queryModule").change(function(e) {
		var moduleId = $("#sel-queryModule").val();
		var url = '/profile/<c:out value="${type}"/>/<c:out value="${projectId}"/>';
		if(moduleId)
			url = url + "?moduleId=" + moduleId;
		
		window.location.href = url;
	});
	
	$("#queryModule").click(function(e) {
		var moduleId = $("#sel-queryModule").val();
		var url = '/profile/<c:out value="${type}"/>/<c:out value="${projectId}"/>?projectId=<c:out value="${projectId}"/>';
		if(moduleId)
			url = url + "?moduleId=" + moduleId;
		
		window.location.href = url;
	});
	/*** 2.Ajax数据预读示例 ***/
// 远程数据源
	var prefetch_provinces = new Bloodhound({
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('config_key'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		// 预获取并缓存
		prefetch: '/profile/json/<c:out value="${projectId}"/>?moduleId=<c:out value="${moduleId}"/>'
	});
	prefetch_provinces.initialize();
	$('#confKey').typeahead(null, {
		name: 'confKey',
		displayKey: 'config_key',
		highlight: true,
		source: prefetch_provinces.ttAdapter()
	});
});
function togglePage(pageNumber) {
	var moduleId=$("#moduleId").val();
	var confKey=$("#confKey").val();
	var url='/profile/<c:out value="${type}"/>/<c:out value="${projectId}"/>?moduleId='+moduleId+'&confKey='+confKey+'&pageNumber='+pageNumber+'';
	window.location.href=url;
}
function doSearch(){
	var confKey=$("#confKey").val();
	var moduleId=$("#moduleId").val();
	var url='/profile/<c:out value="${type}"/>/<c:out value="${projectId}"/>?moduleId='+moduleId+'&confKey='+confKey+'';
	window.location.href=url;
}
</script>