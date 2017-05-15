<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<ol class="breadcrumb">
    <li><a href="/index">首页</a></li>
    <li><a href="/profile/<c:out value="${type}"/>/<c:out value="${project.ID}"/>"><c:out value="${project.PROJ_NAME}"/> - <c:out value="${type}"/></a></li>
    <li class="active">发布</li>
</ol>
<textarea style="width: 100%; height: 500px; font-size: 12px; line-height: 16px;"><c:out value="${message}"/></textarea>
<br/>