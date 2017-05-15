<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" value="indexId" id="pageId" />

<table class="table table-bordered table-striped table-hover">
  	<thead>
    	<tr>
    		<th>项目编码</th>
    		<th>项目名称</th>
    		<th>Profiles</th>
    	</tr>
  	</thead>
  	<tbody>
    	<c:forEach items="${projects}" var="project">
       		<tr>
       			<td style="width:30%;">
                  	<c:out value="${project.projCode}"/>
               	</td>
               	<td style="width:30%;">
                  	<c:out value="${project.projName}"/>
               	</td>
               	<td style="width:40%;">
               		<c:if test="${sessionScope.sessionUser.userCode == 'admin'}">
						<button type="button" class="btn btn-sm btn-default" onclick="doBtn('./profile/development/<c:out value="${project.id}"/>')">development</button>
						&nbsp;&nbsp;<button type="button" class="btn btn-sm btn-primary" onclick="doBtn('./profile/test/<c:out value="${project.id}"/>')">test</button>
						&nbsp;&nbsp;<button type="button" class="btn btn-sm btn-warning" onclick="doBtn('./profile/staging/<c:out value="${project.id}"/>')">staging</button>
						&nbsp;&nbsp;<button type="button" class="btn btn-sm btn-danger" onclick="doBtn('./profile/production/<c:out value="${project.id}"/>')">production</button>
					</c:if>
               		<c:if test="${sessionScope.sessionUser.userCode != 'admin'}">
	               		<c:forEach items="${project.roles}" var="role">
							<c:if test="${role.roleCode == 'development'}">
								<button type="button" class="btn btn-sm btn-default" onclick="doBtn('./profile/development/<c:out value="${project.id}"/>')">development</button>
							</c:if>
	                  	</c:forEach>
						<c:forEach items="${project.roles}" var="role">
							<c:if test="${role.roleCode == 'test'}">
								&nbsp;&nbsp;<button type="button" class="btn btn-sm btn-primary" onclick="doBtn('./profile/test/<c:out value="${project.id}"/>')">test</button>
							</c:if>
						</c:forEach>
						<c:forEach items="${project.roles}" var="role">
							<c:if test="${role.roleCode == 'staging'}">
								&nbsp;&nbsp;<button type="button" class="btn btn-sm btn-warning" onclick="doBtn('./profile/staging/<c:out value="${project.id}"/>')">staging</button>
							</c:if>
						</c:forEach>
						<c:forEach items="${project.roles}" var="role">
							<c:if test="${role.roleCode == 'production'}">
								&nbsp;&nbsp;<button type="button" class="btn btn-sm btn-danger" onclick="doBtn('./profile/production/<c:out value="${project.id}"/>')">production</button>
							</c:if>
						</c:forEach>
                  	</c:if>
               	</td>
            </tr>
     	</c:forEach>
	</tbody>
</table>
<script language="JavaScript">
	function doBtn(url){
		window.location.href=url;
	}
</script>