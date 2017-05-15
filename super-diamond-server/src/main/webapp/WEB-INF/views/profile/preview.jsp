<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ol class="breadcrumb">
    <li><a href="/index">首页</a></li>
    <li><a href="/profile/<c:out value="${type}"/>/<c:out value="${project.ID}"/>"><c:out value="${project.PROJ_NAME}"/> - <c:out value="${type}"/></a></li>
    <li class="active">预览</li>
</ol>
<c:out value="${message}"/>
<br/>