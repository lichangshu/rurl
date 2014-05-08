<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/_head.jsp" %>
<ul class="breadcrumb">
    <li>
        <a href="${ROOT}/">主页</a> <span class="divider"></span>
    </li>
    <li class="active">
        应用权限列表
    </li>
</ul>
<div class="page-header">
    <h2> 应用URL配置 </h2>
</div>
<div class="tabbable tabs-left min_page">
    <ul class="nav nav-tabs app_title">
        <c:forEach items="${list}" var="item" varStatus="vs">
            <li class=""><a appid="${item.id}" href="#app_id_${item.id}" data-toggle="tab">${item.name}</a></li>
            </c:forEach>
    </ul>
    <div class="tab-content app_content">
        <c:forEach items="${list}" var="item" varStatus="vs">
            <div class="tab-pane" id="app_id_${item.id}">
                <h4>${item.name} (${item.appName})</h4>
                <p>${item.discrption}</p>
                <p>
                    所有人 <strong>${item.allLogin?"可以登录":"不可登录"}</strong> ， 除了组：${item.loginGroup.name} (${item.loginGroup.id}) , 
                    对应用中未配置权限的URL，登录用户都 <strong>${item.unknownUrl?"可以访问":"不可访问"}</strong>
                </p>
                <div class="clear dividers"></div>
                <div style="display: none;" class="select_groups">
                    <h4> 已经配置的URL 
                        <a class="btn btn-default btn-sm" href="${ROOT}/url/add-${item.id}.html">添加</a> </h4>
                    <div class="app_groups_list groups_list">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>名称</th>
                                    <th>路径</th>
                                    <th>类型</th>
                                    <th>父菜单</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>

                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        var tmp = '<tr class="{6}"><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>' +
                '<a target="_blank" class="btn btn-default btn-xs" href="${ROOT}/url/edit.html?id={1}">编辑</a> ' +
                '<button class="btn  btn-default btn-xs delete_url" uid="{1}">删除</button></td>';
        $(".app_title li a").click(function() {
            window.pageobject = window.pageobject || {};
            var appurl = window.pageobject.appurl = window.pageobject.appurl || {};
            window.pageobject.appurltyps = {0: "默认", 1: "菜单", 2: "目录"};
            var appid = $(this).attr("appid");
            var target = $(this).attr("href").split("#")[1] || "";
            var $ct = $("#" + target + " .app_groups_list table tbody");
            var $sg = $("#" + target + " .select_groups");
            if (!appurl[appid]) {
                $.getJSON("${ROOT}/url/list.ajax", {_: new Date().getTime(), appid: appid}, function(dat) {
                    appurl[appid] = parsejsonref(dat);
                    $sg.show();
                    for (var it in dat.data) {
                        var item = dat.data[it];
                        $ct.append(templetRepalce(tmp, item.id, item.name, item.webappPath,
                                window.pageobject.appurltyps[item.type] || ("其他" + item.type), item.parentUrl ? item.parentUrl.name : "", "cz"));
                    }
                });
            }
        });
    });
    //删除URL
    $(function() {
        $(".app_groups_list").off("click", ".delete_url").on("click", ".delete_url", function() {
            var the = $(this), $tr = the.closest("tr");
            $tr.addClass("alert");
            var id = the.attr("uid");
            $.getJSON("${ROOT}/url/dele.ajax", {id: id}, function(dta) {
                if (dta.success) {
                    $tr.remove();
                } else {
                    $tr.addClass("alert-danger");
                }
            });
        });
    });
</script>
<%@include file="../../templates/_foot.jsp" %>