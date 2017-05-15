<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="editConfigWin" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">参数配置</h3>
    </div>
    <div class="modal-body">
        <form id="configForm" class="form-horizontal" action='<c:url value="/config/save" />' method="post">
            <div class="form-group">
                <label class="col-sm-2 control-label">模块：</label>
                <div class="col-sm-9">
                    <select class="form-control" name="moduleId_show" id="config-moduleId-show" disabled="true">
                        <option value="">请选择...</option>
                        <c:forEach items="${modules}" var="module">
                            <option value='<c:out value="${module.moduleId}"/>'><c:out value="${module.moduleName}"/></option>
                        </c:forEach>
                    </select>
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Config Key：</label>
                <div class="col-sm-9">
                    <input type="hidden" name="moduleId" id="config-moduleId" />
                    <input type="hidden" name="configId" id="config-configId" />
                    <input type="hidden" name="projectId" value='<c:out value="${projectId}"/>'/>
                    <input type="hidden" name="type" value='<c:out value="${type}"/>'/>
                    <input type="hidden" name="selModuleId" value='<c:out value="${moduleId}"/>'/>
                    <input type="hidden" name="version" value='<c:out value="${param.version}"/>'/>
                    <input type="hidden" name="oldValue" id="oldValue"/>
                    <input type="hidden" name="qryKey" id="qryKey"/>
                    <input type="hidden" name="pgNum" id="pgNum"/>
                    <input type="text" name="configKey" class="form-control" id="config-configKey" readonly="true">
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Config Value：</label>
                <div class="col-sm-9">
                    <textarea rows="3" name="configValue" class="form-control" id="config-configValue"></textarea>
                </div>
                </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">描述：</label>
                <div class="col-sm-9">
                    <textarea rows="2" class="form-control" name="configDesc" id="config-configDesc"></textarea>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary" id="saveConfig">保存</button>
    </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function updateConfig(id) {
        var tds = $("#row-" + id + " > td");
        $("#config-moduleId").val($(tds.get(0)).attr("value"));
        $("#config-moduleId-show").val($(tds.get(0)).attr("value"));
        $("#config-configKey").val($(tds.get(1)).attr("value"));
        $("#config-configValue").val($(tds.eq(2).find("a")).attr("data-content"));
        $("#oldValue").val($(tds.eq(2).find("a")).attr("data-content"));
        $("#config-configDesc").val($(tds.eq(3).find("a")).attr("data-content"));
        $("#config-configId").val(id);
        $("#pgNum").val($("#pageNumber").val());
        $("#qryKey").val($("#confKey").val());
        $('#editConfigWin').modal('toggle');
    }
    $(function(){
        $("#saveConfig").click(function(e) {
            var type=$("input[name='type']").val();
            if(type=="production") {
                bootbox.confirm({
                    message: "请确认是否修改正式环境配置项?",
                    callback: function (result) {
                        if (result) {
                            $("#configForm")[0].submit();
                        }
                    }
                });
            }else{
                if($("#config-configKey").val().trim() == '' || $("#config-configValue").val().trim() == ''){
                    alert("Key 或者 Value 不能为空!");
                    return;
                }
                $("#configForm")[0].submit();
            }
        });
    })
</script>