<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" value="projectId" id="pageId" />

<c:if test="${sessionScope.message != null}">
	<div class="alert alert-success alert-dismissible" role="alert" style="margin-bottom: 5px;width: 600px; padding: 2px 15px 2px 10px;">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		${sessionScope.message}
	</div>
</c:if>

<table class="table table-striped table-bordered table-hover">
  	<thead>
    	<tr>
    		<th>项目编码</th>
    		<th>项目名称</th>
      		<th>项目管理者</th>
      		<th>操作</th>
    	</tr>
  	</thead>
  	<tbody>
    	<c:forEach items="${projects}" var="project">
       		<tr>
       			<td>
                  	<c:out value="${project.projCode}"/>
               	</td>
               	<td>
                  	<c:out value="${project.projName}"/>
               	</td>
              	<td>
                  	<c:out value="${project.userName}" />
              	</td>
              	<c:url var="deleteProjectUrl" value="/project/delete" >
                  	<c:param name="id" value="${project.id}" />
              	</c:url>
              	<c:url var="addUsersUrl" value="/project/addUsers" >
                  	<c:param name="id" value="${project.id}" />
              	</c:url>
				<td>
					<c:if test="${sessionScope.sessionUser.userCode == 'admin'}">
						<a role="button" onclick="doDel('${deleteProjectUrl}')" class="btn btn-xs btn-danger">删除</a>&nbsp;&nbsp;&nbsp;
					</c:if>
					<a role="button" href="${addUsersUrl}" class="btn btn-xs btn-primary">添加项目成员</a>
				</td>
            </tr>
     	</c:forEach>
	</tbody>
</table>
<c:if test="${sessionScope.sessionUser.userCode == 'admin'}">
	<ul class="pager">
		<button class="btn btn-primary" onclick='window.location.href = "<c:url value="/project/new" />"'>新建项目</button>
	</ul>
</c:if>

<script type="text/javascript">
function doDel(url){
	bootbox.confirm("确定删除项目，删除之后不可恢复！", function(confirmed) {
		if(confirmed)
			window.location.href =url;
	});
}
</script>