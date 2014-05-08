<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/_head.jsp" %>
<style type="text/css">
    .popover.right .arrow{
        top:2.7em;
    }
</style>
<ul class="breadcrumb">
    <li>
        <a href="#">主页</a> <span class="divider"></span>
    </li>
    <li class="active">
        用户组
    </li>
</ul>
<div class="page-header">
    <h2> 用户及权限组 <a class="btn btn-default btn-sm text-right" href="${ROOT}/groups/edit.html"> 添加 </a> </h2>
</div>
<div class="tabbable tabs-left min_page">
    <ul class="nav nav-tabs">
        <c:forEach items="${list}" var="item" varStatus="vs">
            <li class=""><a groupsid="${item.id}" href="#groups_id_${item.id}" data-toggle="tab">${item.name}</a></li>
            </c:forEach>
    </ul>

    <div class="tab-content">
        <c:forEach items="${list}" var="item" varStatus="vs">
            <div class="tab-pane" id="groups_id_${item.id}">
                <h4>${item.name} <a class="btn btn-default btn-small text-right btn-sm" 
                                    href="${ROOT}/groups/edit.html?id=${item.id}">编辑</a></h4>
                <p>${item.discrption}</p>
                <h4> 组用户 </h4>
                <div class="groups_staff_list groups_list">
                    <ul class="nav nav-pills" groupsid="${item.id}"></ul>
                </div>
                <div class="clear dividers"></div>
                <div style="display: none;" class="select_staff"></div>
                <div class="clear dividers"></div>
                <h4> 组权限 <a href="#${item.id}" class="btn btn-default btn-default btn-sm popover-close-other pop_list_webapps" data-toggle="popover" data-html="true" 
                            data-original-title="<i class='glyphicon glyphicon-remove'></i> 选择应用 ">
                        <i class="glyphicon glyphicon-circle-arrow-right"></i> 添加权限 </a></h4>
                <div style="display: none;" class="select_gourps_right groups_list">
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>名称</th>
                                <th>应用</th>
                                <th>路径</th>
                                <th>查看</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div id="department_contain"></div>

<script type="text/javascript">
    $(function() {
        window.pageobject = $.extend({groupsaccount: {}, groupsright: {}}, window.pageobject);
        window.staffhtmlTemplet = '<div class="checkbox"><label><input name="staff_account" type="checkbox" value="{1}"> {2} </label></div>';
        var tmp = '<li id="{1}" account="{3}" class="alert {4}">' +
                '<span>{2}</span><button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '</li>';
        var righttmp = "<tr class='{6}' id='group_url_{1}_{5}'><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td>" +
                "<td><a href='${ROOT}/url/edit.html?id={1}' target='_blank' class='btn btn-default btn-xs'>编辑</a></td></tr>";
        //load 组中用户.
        $(".tab-pane .groups_staff_list ul").each(function() {
            var the = $(this), gid = the.attr("groupsid");
            $.getJSON("${ROOT}/groups/list_account.ajax", {_: new Date().getTime(), groupsid: gid}, function(dta) {
                window.pageobject.groupsaccount[gid] = dta.data;
                for (var it in dta.data) {
                    var item = dta.data[it];
                    if (item)
                        the.append(templetRepalce(tmp, getstaffid(item, gid), item.name, item.account, "alert-info"));
                }
                the.closest(".tab-pane").find(".select_staff:first").show();
            });
        });
        //load 员工树
        $("#department_contain").load("${ROOT}/user/select.html", function() {
            $(".tab-content .active .select_staff:first").append($("#department_contain"));
        });
        //点击时 获取组中的权限URL
        $(".nav-tabs li a").click(function() {
            var hs = this.href.split("#")[1], the = $(this), gid = the.attr("groupsid");
            var did = the.attr("href").split("#")[1];
            var $ct = $("#" + did + " .select_gourps_right"), $tbody = $ct.find("tbody");
            $("#" + hs + " .select_staff:first").append($("#department_contain"));
            $("#department_contain .staff_list").empty();
            //=========
            if (!window.pageobject.groupsright[gid]) {
                $.getJSON("${ROOT}/url/list4groups.ajax", {_: new Date().getTime(), groupsid: gid}, function(dta) {
                    window.pageobject.groupsright[gid] = dta.data;
                    for (var it in dta.data) {
                        var item = dta.data[it];
                        $tbody.append(templetRepalce(righttmp, item.id, item.name, item.webapp.name, item.webappPath, gid));
                    }
                    $ct.show();
                });
            }
        });
        $(".nav-tabs li.active a").click();
        //添加 账户
        $(".tab-content").on("click", ".staff_list :checkbox", function() {
            var vl = this.value, $ul = $(this).closest(".tab-pane").find(".groups_staff_list ul:first"), gid = $ul.attr("groupsid");
            var staff = window.pageobject.staffes[vl], ac = getstaffid(staff, gid), $st = $("#" + ac);
            if (this.checked) {//选择
                if ($st.length <= 0) {
                    $st = $(templetRepalce(tmp, ac, staff.name, staff.account, ""));
                    $ul.append($st);
                    groupsAndStaff($st, staff, gid);
                } else {
                    $st.removeClass("alert-info alert-success").addClass("alert-warning");
                    setTimeout(function() {
                        $st.removeClass("alert-warning").addClass("alert-success");
                    }, 500);
                }
                //} else {//取消选择
                //	groupsDelStaff($st, staff, gid);
            }
        });
        function getstaffid(staff, gid) {
            //id 中不应该有特殊符号
            return ("" + staff.account).replace(/\./gi, "_").replace(/\s/gi, "_") + "_id_" + gid;
        }
        function getrightid(url, gid) {
            return "urlright_" + url.id + "_id_" + gid;
        }
        function groupsAndStaff($st, staff, gid) {
            $.post("${ROOT}/groups/add_account.ajax?groupsid=" + gid, staff, function(data) {
                if (data.success)
                    $st.addClass("alert-success");
                else
                    alert(data.errors.join(","));
            }, "json");
        }
        function groupsDelStaff($st, staff, gid, fun) {
            $st.alert('close');
        }
    });
    //组中删除员工账户
    $(function() {
        $(".groups_staff_list").on("close.bs.alert", ".alert", function() {
            var the = $(this), gid = the.closest("ul").attr("groupsid"), acc = this.getAttribute("account");
            if (!the.hasClass("alert-warning")) {//不是警告状态
                the.removeClass("alert-info").removeClass("alert-success").addClass("alert-warning");
                $.getJSON("${ROOT}/groups/del_account.ajax", {groupsid: gid, "account": acc}, function(dta) {
                    if (dta.data >= 1) {
                        the.remove();
                    } else {
                        alert("帐号＂" + acc + "＂删除失败！");
                    }
                });
            }
            return false;
        });
    });
    $(function() {
        var loadhtml = '<div class="progress progress-striped active">' +
                '<div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 75%">' +
                '<span class="sr-only">75% Complete</span></div></div>';
        var temphtml = "<li><a class='btn-sm' href='${ROOT}/url/{2}_{1}.html'>{3}</a></li>"
        $(".pop_list_webapps").popover({content: loadhtml, html: true, toggle: "popover"}).click(function() {
            var the = $(this), gid = this.href.split("#")[1];
            $.getJSON("${ROOT}/app/list.ajax", {_: new Date().getTime()}, function(dt) {
                var html = "<ul class='nav bs-sidenav'>", data = parsejsonref(dt.data);
                for (var i in data) {
                    var it = data[i];
                    html += templetRepalce(temphtml, it.id, gid, it.name);
                }
                the.next().find(".popover-content:first").html(html + "</ul>");
            });
            return false;
        });

    });
</script>
<%@include file="../../templates/_foot.jsp" %>