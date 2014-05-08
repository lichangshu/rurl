<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="net.microwww.rurl.domain.Webappurl"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%
    List<Webappurl> list = (List) request.getAttribute("rightlist");
    request.setAttribute("jsonlist", JSONObject.toJSONString(list));
%>
<%@include file="../../templates/_head.jsp" %>
<ul class="breadcrumb">
    <li>
        <a href="${ROOT}/">主页</a> <span class="divider"></span>
    </li>
    <li class="active">
        组应用权限列表
    </li>
</ul>
<div class="page-header">
    <h2>组 
        <i data-toggle="tooltip" data-placement="top" title="" data-original-title="ID = [${groups.id}]" title="">${groups.name}</i> 
        在应用 
        <i data-toggle="tooltip" data-placement="top" title="" data-original-title="ID = [${webapp.id},${webapp.appName}]" title="">${webapp.name}</i> 中的权限 </h2>
</div>
<div class="tabbable tabs-left min_page">
    <div>
        <h4>${webapp.name} (${webapp.appName}，${webapp.id})</h4>
        <p>${webapp.discrption}</p>
        <p>
            所有人 <strong>${webapp.allLogin?"可以登录":"不可登录"}</strong> ，
            <c:if test="${!empty webapp.loginGroup}">
                除了组：${webapp.loginGroup.name} (${webapp.loginGroup.id}) ，
            </c:if>
            对应用中未配置权限的URL登录用户都 <strong>${webapp.unknownUrl?"可以访问":"不可访问"}</strong>
        </p>
        <div class="clear dividers"></div>
        <h4>${groups.name} (${groups.id})</h4>
        <p>${groups.discrption}</p>
        <div class="clear dividers"></div>
        <h4>应用(${webapp.name})中的所有权限 <small>蓝色行表示存在的权限</small></h4>
        <div class="select_groups">
            <div class="app_groups_list groups_list">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>名称</th>
                            <th>路径</th>
                            <th>类型</th>
                            <th>父菜单</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${appurllist}" var="item" varStatus="vs">
                            <tr>
                                <td>${vs.count}</td><td>${item.name}</td>
                                <td>${item.webappPath}</td><td>${item.type}</td>
                                <td>${item.parentUrl.name}(${item.parentUrl.id})</td>
                                <td><a urlid="${item.id}" class="btn btn-primary btn-xs url_operation"></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        var gid = parseInt("${groups.id}"), wid = parseInt("${webapp.id}");
        var jlist = window.jsonlist = ${jsonlist};
        var jsonmap = {};
        for (var k in jlist) {
            var item = jlist[k];
            if (item) {
                jsonmap[item.id + ""] = item;
            }
        }
        $(".url_operation").each(function() {
            var the = $(this), uid = the.attr("urlid"), $tr = the.closest("tr");
            if (jsonmap[uid]) {
                the.html("<i class='glyphicon glyphicon-check'></i> 删除");
                $tr.addClass("alert alert-info");
            } else {
                the.html("添加");
            }
        }).click(function() {
            var the = $(this), uid = the.attr("urlid"), $tr = the.closest("tr");
            if ($tr.hasClass("alert-warn")) {
                alert("正在提交！请稍候");
                return;
            }
            the.html("稍等");
            $tr.addClass("alert alert-warn");
            if (!jsonmap[uid]) {//不存在 添加...
                $.getJSON("${ROOT}/url/addgroupurl.ajax", {groupsid: gid, appurls: uid}, function(dta) {
                    if (dta.success) {
                        jsonmap[uid] = {};
                        the.closest("tr").removeClass("alert-warn").addClass("alert-info");
                        the.html("<i class='glyphicon glyphicon-check'></i> 删除");
                    }
                });
            } else {//删除
                $.getJSON("${ROOT}/url/delegroupurl.ajax", {groupsid: gid, appurls: uid}, function(dta) {
                    if (dta.success) {
                        delete jsonmap[uid];
                        the.closest("tr").removeClass("alert alert-info alert-warn");
                        the.html("添加");
                    }
                });
            }
        });
    });
    $(function() {
        $('.page-header i').tooltip({});
    });
</script>
<%@include file="../../templates/_foot.jsp" %>