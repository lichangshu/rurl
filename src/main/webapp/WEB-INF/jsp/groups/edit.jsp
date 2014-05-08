<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/_head.jsp" %>
<ul class="breadcrumb">
    <li>
        <a href="#">主页</a> <span class="divider"></span>
    </li>
    <li class="">
        <a href="#">用户组列表</a> <span class="divider"></span>
    </li>
    <li class="active">
        添加用户组
    </li>
</ul>
<div class="page-header">
    <h2>${empty groups ? "添加组" : "编辑组"}   ${groups.name}</h2>
</div>
<div class="row-fluid min_page">
    <div class="span12">
        <form class="form-horizontal" id="edit_groups_form" method="post" action="${ROOT}/groups/edit.ajax" onsubmit="return false;">
            <input type="hidden" name="id" value="${empty groups ? 0 : groups.id}">
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_name">组名</label>
                <div class="controls col-sm-7">
                    <input id="input_name" class="form-control validate[required]" type="text" name="name" value="${groups.name}" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_desc">描述</label>
                <div class="controls col-sm-7">
                    <input id="input_desc" class="form-control" type="text" name="discrption" value="${groups.discrption}" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_data">数据</label>
                <div class="controls col-sm-7">
                    <textarea id="input_data" class="form-control" name="data">${groups.data}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="controls col-sm-offset-3 col-sm-2">
                    <button type="submit" class="form-control btn btn-primary">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="http://news.play67.com/templets/platform/js/jw-common.js"></script>
<script type="text/javascript">
            $(function() {
                $.request($("#edit_groups_form"), function(data) {
                    if (data.success) {
                        alert("保存成功");
                        var ep = "${groups.id}";
                        if (ep) {
                            location = location.href;
                        } else {
                            location = "${ROOT}/groups/index.html";
                        }
                    }
                });
            });
</script>
<link href="http://news.play67.com/templets/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="http://news.play67.com/templets/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="http://news.play67.com/templets/js/jquery.validationEngine.min.js" type="text/javascript"></script>
<%@include file="../../templates/_foot.jsp" %>