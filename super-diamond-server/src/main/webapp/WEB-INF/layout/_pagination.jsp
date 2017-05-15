<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="pagination">
	<c:choose>
		<c:when test="${pagelist.firstPage}">
			<li class="disabled"><a href="javascript:void(0);">首页</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="javascript:togglePage(1);">首页</a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pagelist.hasPreviousPage}">
			<li><a href="javascript:togglePage(${pagelist.previousPageNumber});">上一页</a></li>
		</c:when>
		<c:otherwise>
			<li class="disabled"><a href="javascript:void(0);">上一页</a></li>
		</c:otherwise>
	</c:choose>
	<c:forEach var="item" items="${pagelist.linkPageNumbers}">
		<c:choose>
			<c:when test="${item==pagelist.thisPageNumber}">
				<li class="active"><a href="#">${item}</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="javascript:togglePage(${item});">${item}</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:choose>
		<c:when test="${pagelist.hasNextPage}">
			<li><a href="javascript:togglePage(${pagelist.nextPageNumber});">下一页</a></li>
		</c:when>
		<c:otherwise>
			<li class="disabled"><a href="javascript:void(0);">下一页</a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pagelist.lastPage}">
			<li class="disabled"><a href="javascript:void(0);">尾页</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="javascript:togglePage(${pagelist.lastPageNumber});">尾页</a></li>
		</c:otherwise>
	</c:choose>
</ul>