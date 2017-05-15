<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="/cache/index" method="post" id="cacheForm" class="form-inline" style="padding-bottom: 10px">
    <div class="form-group">
        <label for="selEnv">环境：</label>
        <select id="selEnv" name="selEnv" class="selectpicker" data-live-search="true">
            <c:forEach items="${envMap}" var="item">
                <option value="${item.key}" ${item.key eq env?'selected':''}>${item.value}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group" style="padding-left:5px;">
        <label for="selType">缓存类型：</label>
        <select id="selType" name="selType" class="selectpicker" data-live-search="true">
            <option value="CUSTOM">自定义</option>
            <c:forEach items="${typeMap}" var="item">
                <option value="${item.key}" ${item.key eq selType?'selected':''}>${item.value}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group" style="padding-left:5px;">
        <label for="cacheKey">KEY
            <a href="#" id="helpBtn" data-toggle="tooltip" data-placement="bottom"><i class="glyphicon glyphicon-question-sign"></i></a>
            ：</label>
        <input id="cacheKey" name="cacheKey" value="${cacheKey}" placeholder="缓存KEY" class="typeahead form-control" style="width:200px">
    </div>
    <button type="button" class="btn btn-primary" onclick="doSearch()">查询</button>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>缓存KEY</th>
        <th>MD5(KEY)</th>
        <th>MD5Value</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${modelCache eq null}"> <tr><td colspan="4">暂无数据</td></tr></c:when>
        <c:otherwise>
            <tr>
                <td>${modelCache.cacheKey}</td>
                <td>${modelCache.md5Key}</td>
                <td>${modelCache.md5Value}</td>
                <td>
                    <a href='javascript:delCache("${modelCache.cacheKey}")' title="删除"><i class="glyphicon glyphicon-remove"></i></a>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<script>
    $(function(){
        $('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });
        $("[data-toggle='tooltip']").tooltip({
            title:"1.实名认证KEY:姓名+身份证号码<br/>" +
                  "2.三/四要素验证Key:姓名+身份证+银行ID+卡号+(手机号)",
            html:true
        });
    });
    function doSearch(){
        $("#cacheForm")[0].submit();
    }

    function delCache(md5Value){
        var env="${env}";
        window.location.href="/cache/delete/"+encodeURI(encodeURI(md5Value))+"?env="+env;
    }
</script>
