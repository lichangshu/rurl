<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/_head.jsp" %>
<ul class="breadcrumb">
    <li>
        <a href="${ROOT}/">主页</a> <span class="divider"></span>
    </li>
    <li class="">
        <a href="${ROOT}/url/">URL列表</a> <span class="divider"></span>
    </li>
    <li class="active">
        添加URL
    </li>
</ul>
<div class="page-header">
    <h2>${empty webappurl ? "添加URL" : "编辑URL"}   ${webappurl.name}</h2>
</div>
<div class="row-fluid min_page">
    <div class="span12">
        <form class="form-horizontal" id="edit_webappurl_form" method="post" action="${ROOT}/url/edit.ajax" onsubmit="return false;">
            <input type="hidden" name="id" value="${empty webappurl ? 0 : webappurl.id}">
            <input type="hidden" name="webappId" value="${webapp.id}">
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_appId">应用ID</label>
                <div class="controls col-sm-2">
                    <input id="input_appId" class="form-control validate[required,custom[accountnameval]]"
                           disabled type="text" name="appname" value="${webapp.appName}" placeholder="应用的ID" />
                </div>
                <span class="col-sm-3 help-block">${webapp.name}</span>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_name">名称</label>
                <div class="controls col-sm-7">
                    <input id="input_name" class="form-control validate[required]" type="text" 
                           name="name" value="${webappurl.name}" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_allLogin">路径</label>
                <div class="controls col-sm-7">
                    <input id="input_allLogin" class="form-control validate[required]" type="text" 
                           name="webappPath" value="${webappurl.webappPath}" />
                    <span class="help-block">以斜杠"/"开始,如果为目录内容可以随意</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_unknownUrl">类型</label>
                <div class="controls col-sm-2">
                    <select class="form-control" name="type">
                        <option <c:if test="${webappurl.type==1}">checked</c:if> value="0">默认</option>
                        <option <c:if test="${webappurl.type==2}">checked</c:if> value="1">菜单</option>
                        <option <c:if test="${webappurl.type==3}">checked</c:if> value="2">目录</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-3" for="input_unknownUrl">排序</label>
                    <div class="controls col-sm-2">
                        <input id="input_name" class="form-control validate[required,custom[integer]]" 
                               type="text" name="sort" value="${empty webappurl ? '0':webappurl.sort}" />
                </div>
                <span class="col-sm-5 help-block">在应用中的排序，一般只用在菜单列表上使用</span>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_parent_id">父菜单</label>
                <div class="controls col-sm-2">
                    <input id="input_parent_id" class="form-control validate[required,custom[integer]]" 
                           type="text" name="parentId" value="${empty webappurl.parentUrl ? '0': webappurl.parentUrl.id}" />
                </div>
                <span class="col-sm-3 help-block">无父菜单请填0, 类型应该为目录</span>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_loginGroup">其他数据</label>
                <div class="controls col-sm-7">
                    <input id="input_loginGroup" class="form-control" type="text" name="data" value="${webappurl.data}" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_desc">描述</label>
                <div class="controls col-sm-7">
                    <textarea id="input_desc" class="form-control" type="text" 
                              name="discrption">${webappurl.discrption}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="controls col-sm-offset-3 col-sm-2">
                    <div class="btn-group">
                        <button type="submit" class="btn btn-primary">提交</button>
                        <c:if test="${empty webappurl}">
                            <button type="button" id="submit-goon" class="btn btn-primary">提交并继续</button>
                        </c:if>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="http://news.play67.com/templets/platform/js/jw-common.js"></script>
<script type="text/javascript">
            $(function() {
                var goon = false;
                $("#submit-goon").click(function() {
                    goon = true;
                    $(this.form).submit();
                });
                $.request($("#edit_webappurl_form"), function(data) {
                    if (data.success) {
                        alert("保存成功");
                        var ep = "${webappurl.id}";
                        if (goon || ep) {
                            location = location.href;
                        } else {
                            location = "${ROOT}/url/";
                        }
                    }
                });
            });
</script>
<link href="http://news.play67.com/templets/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="http://news.play67.com/templets/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="http://news.play67.com/templets/js/jquery.validationEngine.min.js" type="text/javascript"></script>
<%@include file="../../templates/_foot.jsp" %>