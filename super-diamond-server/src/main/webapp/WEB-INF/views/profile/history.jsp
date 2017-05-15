<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="modal-header">
    <button type="button" class="close"
            data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title" id="modalLabel">
        更新历史记录
    </h4>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>KEY</th>
            <th>VALUE</th>
            <th>操作人</th>
            <th>操作时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hisList}" var="his">
        <tr>
            <td>
                <a data-content='${his.configKey}' data-toggle="popover" tabindex="0" data-trigger="focus" data-placement="bottom">
                    <c:choose>
                        <c:when test="${fn:length(his.configKey)>20}">
                            ${fn:substring(his.configKey, 0, 20)} ...
                        </c:when>
                        <c:otherwise>
                            ${his.configKey}
                        </c:otherwise>
                    </c:choose>
                </a></td>
            <td>
                <a data-content='${his.configValue}' data-toggle="popover" tabindex="0" data-trigger="focus" data-placement="bottom">
                    <c:choose>
                        <c:when test="${fn:length(his.configValue)>30}">
                            ${fn:substring(his.configValue, 0, 30)} ...
                        </c:when>
                        <c:otherwise>
                            ${his.configValue}
                        </c:otherwise>
                    </c:choose>
                </a>
            </td>
            <td>${his.optUser}</td>
            <td>
                <fmt:formatDate value="${his.optTime}" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
            <td>
                <a href="javascript:rollback('${his.configId}','${his.hisId}','${version}','${his.configValue}','${projectId}')" title="回滚到当前版本"><i class="glyphicon glyphicon-refresh"></i></a>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default"
            data-dismiss="modal">关闭
    </button>
</div>
<script language="JavaScript">
    $(function(){
        $("[data-toggle='popover']").popover();
    })
    function rollback(configId,hisId,version,hisValue,projectId){
        var pgNum=$("#pageNumber").val();
        var qryKey=$("#confKey").val();
        var moduleId=$("#moduleId").val();
        var type='<c:out value="${type}"/>';
        bootbox.confirm({
            message: "请确认是否要回滚到当前配置项?",
            callback: function (result) {
                if (result) {
                    var url="/config/rollback/"+hisId+"/"+configId+"?moduleId="+moduleId+"&projectId="+projectId+"&type="+type+"&qryKey="+qryKey+"&pgNum="+pgNum+"&version="+version+"&hisValue="+hisValue;
                    window.location.href = url;
                }
            }
        });
    }
</script>

