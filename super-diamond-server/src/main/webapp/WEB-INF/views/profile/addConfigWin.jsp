<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="addConfigWin" class="modal fade" tabindex="-1" data-dismiss='modal' role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>参数配置</h3>
    </div>
    <div class="modal-body">
        <form id="addConfigForm" class="form-horizontal" action='<c:url value="/config/save" />' method="post">
            <div class="form-group">
                <label class="col-sm-2 control-label">模块：</label>
                <div class="col-sm-9">
                    <select class="form-control" name="moduleId">
                        <option value="">请选择....</option>
                        <c:forEach items="${modules}" var="module">
                            <option value='${module.moduleId}' ${module.moduleId eq param.moduleId?'selected':''}>
                             ${module.moduleName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Config Key：</label>
                <div class="col-sm-9">
                    <input type="hidden" name="configId"/>
                    <input type="hidden" name="projectId" value='<c:out value="${projectId}"/>'/>
                    <input type="hidden" name="type" value='<c:out value="${type}"/>'/>
                    <input type="hidden" name="selModuleId" value='<c:out value="${moduleId}"/>'/>
                    <input type="hidden" name="version" value='<c:out value="${param.version}"/>'/>
                    <input type="hidden" name="qryKey" value="${param.confKey}">
                    <input type="text" id="configKey" name="configKey" class="form-control">
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Config Value：</label>
                <div class="col-sm-9">
                    <textarea rows="3" id="configValue" name="configValue" class="form-control"></textarea>
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">描述：</label>
                <div class="col-sm-9">
                    <textarea rows="2" class="form-control" name="configDesc"></textarea>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary" id="saveAddConfig">保存</button>
    </div>
            </div>
        </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#saveAddConfig").click(function(e) {
            if($("#configKey").val().trim() == '' || $("#configValue").val().trim() == ''){
                alert("Key 或者 Value 不能为空!");
                return;
            }
            $("#addConfigForm")[0].submit();
        });
    })
</script>