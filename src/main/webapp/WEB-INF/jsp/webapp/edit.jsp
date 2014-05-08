<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/_head.jsp" %>
<ul class="breadcrumb">
    <li>
        <a href="${ROOT}/">主页</a> <span class="divider"></span>
    </li>
    <li class="">
        <a href="${ROOT}/app/">应用列表</a> <span class="divider"></span>
    </li>
    <li class="active">
        添加应用
    </li>
</ul>
<div class="page-header">
    <h2>${empty webapp ? "添加应用" : "编辑应用"}   ${webapp.name}</h2>
</div>
<div class="row-fluid min_page">
    <div class="span12">
        <form class="form-horizontal" id="edit_webapp_form" method="post" action="${ROOT}/app/edit.ajax" onsubmit="return false;"role="form">
            <input type="hidden" name="id" value="${empty webapp ? 0 : webapp.id}">
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_appId">应用ID</label>
                <div class="controls col-sm-7">
                    <input id="input_appId" ${empty webapp.appName?"":"readonly"} class="form-control validate[required,custom[accountnameval]]" 
                           type="text" name="appName" value="${webapp.appName}" placeholder="应用的英文名字" />
                    <span class="help-block">建议英文名字,不要使用中文,创建后不可修改，不可重复</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_name">应用名称</label>
                <div class="controls col-sm-7">
                    <input id="input_name" class="form-control validate[required]" type="text" name="name" value="${webapp.name}" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_allLogin">所有人可登录</label>
                <div class="controls col-sm-2">
                    <select class="input-mini form-control" id="input_allLogin" name="allLogin"><option value="0">否</option> <option ${webapp.allLogin ? 'selected':''} value="1">是</option></select>
                </div>
            </div>
            <div class="form-group" id="changed_group_div">
                <label class="control-label col-sm-3" for="input_loginGroup">登录组</label>
                <input id="input_loginGroup" type="hidden" name="loginGroupId" 
                       value="${empty webapp.loginGroup.id ? '0': webapp.loginGroup.id}" />
                <div class="controls col-sm-3">
                    <div class="input-group">
                        <input type="text" name="loginGroupName" readonly class="form-control uneditable-input" value="${webapp.loginGroup.name}" />
                        <span class="input-group-btn">
                            <button data-target="#add_group_model_bt" class="btn btn-default" data-toggle="modal"><i class="glyphicon glyphicon-plus-sign"></i></button>
                            <a id='pop_list_groups' href="javascript:void(0)" class="btn btn-default" data-toggle="popover" data-html="true" data-original-title="组列表">
                                <i class="glyphicon glyphicon-circle-arrow-right"></i></a>
                        </span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_unknownUrl">未定权限的URL</label>
                <div class="controls col-sm-3">
                    <select class="input-mini form-control" id="input_unknownUrl" name="unknownUrl"> <option value="1">可以访问</option> <option ${ !empty webapp ? webapp.unknownUrl ? '':'selected' : ""} value="0">不可以访问</option></select>
                    <span class="help-block">不明觉厉，请用默认</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="input_desc">描述</label>
                <div class="controls col-sm-7">
                    <textarea class="form-control" id="input_desc" type="text" name="discrption">${webapp.discrption}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="controls col-sm-2 col-sm-offset-3">
                    <button type="submit" class="form-control btn btn-primary">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div id="add_group_model_bt" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3>添加组</h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="add_groups_form" method="post" action="${ROOT}/groups/edit.ajax" onsubmit="return false;">
                    <br>
                    <input type="hidden" name="id" value="0">
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="group_input_name">组名</label>
                        <div class="controls col-sm-6">
                            <input id="group_input_name" class="form-control validate[required]" type="text" name="name" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class=" control-label col-sm-3" for="group_input_desc">描述</label>
                        <div class="controls col-sm-6">
                            <input id="group_input_desc" class="form-control" type="text" name="discrption" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class=" control-label col-sm-3" for="group_input_data">数据</label>
                        <div class="controls col-sm-6">
                            <textarea id="group_input_data" class="form-control" name="data" placeholder="不明觉厉请留空"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="controls col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-primary">提交</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" data-dismiss="modal" class="btn btn-primary">关闭</a>
            </div>
        </div></div>
</div>
<script type="text/javascript" src="${ROOT}/resource/js/jw-common.js"></script>
<script type="text/javascript">
//提交
                    $(function() {
                        $.request($("#edit_webapp_form"), function(data) {
                            if (data.success) {
                                alert("保存成功");
                                var ep = "${webapp.id}";
                                if (ep) {
                                    location = location.href;
                                } else {
                                    location = "${ROOT}/app/index.html";
                                }
                            }
                        });
                    });
//添加组
                    $(function() {
                        $.request($("#add_groups_form"), function(data) {
                            if (data.success) {
                                $('#add_group_model_bt').modal('hide');
                                setformgroups(data.data.id, data.data.name);
                            }
                        });
                    });
//获取组列表
                    $(function() {
                        $.getJSON("${ROOT}/groups/list.ajax", function(dt) {
                            var html = "", tmpet = '<div class="radio"><label><input type="radio" name="_groups_id" value="{1}"/>{2}</label></div>';
                            if (dt.success) {
                                for (var i in dt.data) {
                                    var item = dt.data[i];
                                    html += templetRepalce(tmpet, item.id, item.name);
                                }
                            } else {
                                html += templetRepalce(tmpet, 0, "");
                            }
                            html += "";
                            var $pop = $("#pop_list_groups").popover({html: true, content: html});
                            $(document).on("click", "#changed_group_div .popover-content label input", function() {
                                setformgroups(this.value, $(this).closest("label").text());
                                $pop.popover("hide");
                            });
                        });
                    });
//为应用 设置组
                    function setformgroups(id, name) {
                        var fm = $("#edit_webapp_form")[0];
                        fm.loginGroupId.value = id;
                        fm.loginGroupName.value = name;
                    }
</script>
<link href="${ROOT}/resource/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="${ROOT}/resource/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="${ROOT}/resource/js/jquery.validationEngine.min.js" type="text/javascript"></script>
<%@include file="../../templates/_foot.jsp" %>