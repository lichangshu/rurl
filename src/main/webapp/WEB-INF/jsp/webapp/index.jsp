<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/_head.jsp" %>
<ul class="breadcrumb">
    <li>
        <a href="${ROOT}/">主页</a> <span class="divider"></span>
    </li>
    <li class="active">
        查看应用
    </li>
</ul>
<div class="page-header">
    <h2> 查看应用 <a class="btn btn-default btn-sm" href="${ROOT}/app/edit.html"><i class="icon-plus-sign"></i> 添加 </a> </h2>
</div>
<div class="min_page">
    <div class="tabbable tabs-left min_page">
        <ul class="nav nav-tabs app_title">
            <c:forEach items="${list}" var="item" varStatus="vs">
                <li class=""><a appid="${item.id}" href="#app_id_${item.id}" data-toggle="tab">${item.name}</a></li>
                </c:forEach>
        </ul>

        <div class="tab-content app_content">
            <c:forEach items="${list}" var="item" varStatus="vs">
                <div class="tab-pane" id="app_id_${item.id}">
                    <h3>${item.name} (${item.appName})<a class="btn btn-default btn-sm" href="${ROOT}/app/edit/${item.id}.html"><i class="icon-edit"></i> 编辑 </a></h3>
                    <hr>
                    <dl class="dl-horizontal">
                        <dt>名称</dt>
                        <dd>${item.name} (${item.appName})</dd>
                        <dt>描述</dt>
                        <dd>${empty item.discrption?"<i>暂无</i>":""}${item.discrption}</dd>
                        <dt>权限</dt>
                        <dd>
                            <c:if test="${item.allLogin}">
                                所有员工 <code><i class='icon-ok-circle'></i> 可以登录 </code>
                                <c:if test="${!empty item.loginGroup}">
                                    ，可以设置 不可登录 的员工 ${item.loginGroup.name} (${item.loginGroup.id})
                                </c:if>
                            </c:if>
                            <c:if test="${!item.allLogin}">
                                <c:if test="${!empty item.loginGroup}">
                                    组 <a href="${ROOT}/groups/detail.html?id=${item.loginGroup.id}">${item.loginGroup.name}</a> 中的员工可以登录 
                                </c:if>
                                <c:if test="${empty item.loginGroup}">
                                    暂未设置登录组，你可以 <a href="${ROOT}/groups/edit.html" class="btn btn-default btn-mini">添加组</a>
                                    或者<a href="${ROOT}/app/edit/${item.id}.html" class="btn btn-default btn-mini">编辑应用</a>
                                </c:if>
                            </c:if>
                        </dd>
                        <div class="alert"><i class="icon-warning-sign"></i> 所有用户 <code>${item.unknownUrl?"可以访问":"不能访问"}</code> 未配置的链接</div>
                    </dl>
                    <div class="clear dividers"></div>
                    <div style="display: none;" class="select_groups">
                        <h4>
                            已经配置权限的组
                            <span class="btn-group groups-right-ch">
                                <button webappid="${item.id}" data-target="#add_group_model_bt" class="add-groups-btn btn btn-default btn-sm" 
                                        data-toggle="modal"><i class="glyphicon glyphicon-plus-sign"></i> 新增组并添加权限 </button>
                                <a webappid="${item.id}" href="javascript:void(0)" class="btn btn-default btn-sm pop-groups-list"
                                   data-toggle="popover" data-html="true" data-original-title="组列表">
                                    <i class="glyphicon glyphicon-circle-arrow-right"></i> 选择组并添加权限 </a>
                            </span>
                        </h4>
                        <div class="app_groups_list" gourpsid="${item.id}">
                            <c:if test="${!empty item.loginGroup}">
                                <div id="app_${item.id}_group_${item.loginGroup.id}" class="btn-group">
                                    <a class="btn btn-default" href="${ROOT}/groups/detail.html?id=${item.loginGroup.id}">登录权限组</a>
                                    <button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                    <ul class="dropdown-menu">
                                        <li><a href="${ROOT}/groups/detail.html?id=${item.loginGroup.id}"> <i class="icon-comment"></i> 查看组</a></li>
                                        <li><a href="${ROOT}/url/${item.loginGroup.id}_${item.id}.html"> <i class="icon-edit"></i> 编辑权限</a></li>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${!empty item.loginGroup}">
                        <div>
                            <H4>${item.allLogin ? "禁止登录":"登录组"}用户管理</H4>
                            <div class="groups_staff_list">
                                <ul class="nav nav-pills" groupsid="${item.loginGroup.id}"></ul>
                            </div>
                        </div>
                        <div style="display: none;" class="select_staff clearfix"></div>
                    </c:if>

                    <div class="app_url_content clearfix">
                        <h4> 已经配置的URL 
                            <a class="btn btn-default btn-sm" href="${ROOT}/url/add-${item.id}.html">添加</a> </h4>
                        <div class="app_url_list">
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
</div>
<div id="department_contain">tree</div>
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
<br/>
<br/>
<br/>
<br/><!--防止滚动条-->
<script type="text/javascript">
    $(function() {
        var tmp = '<div id="app_{1}_group_{2}" class="btn-group"    >' +
                '<a class="btn btn-default" href="${ROOT}/groups/detail.html?id={2}">{3}</a>' +
                '<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>' +
                '<ul class="dropdown-menu"    >' +
                '<li><a href="${ROOT}/groups/detail.html?id={2}"> <i class="icon-comment"></i> 查看组</a></li    >' +
                '<li><a href="${ROOT}/url/{2}_{1}.html"> <i class="icon-edit"></i> 编辑权限</a></li>' +
                '</ul></div>';
        $(".app_title li a").click(function() {
            window.pageobject = window.pageobject || {};
            var appg = window.pageobject.appgroups = window.pageobject.appgroups || {};
            var appid = $(this).attr("appid");
            var target = $(this).attr("href").split("#")[1] || "";
            var $ct = $("#" + target + " .app_groups_list");
            var $sg = $("#" + target + " .select_groups");
            if (!appg[appid]) {
                $.getJSON("${ROOT}/groups/appglist.ajax", {_: new Date().getTime(), appid: appid}, function(dat) {
                    appg[appid] = dat;
                    $sg.show();
                    for (var it in dat.data) {
                        var item = dat.data[it];
                        var $cr = $(templetRepalce(tmp, appid, item.id, item.name));
                        if ($("#" + $cr.attr("id")).length === 0) {
                            $ct.append($cr);
                        }
                    }//debugger;
                    if ($ct.find("div").length === 0) {
                        $ct.html("<div class='alert'>没有配置组</div>");
                    }
                });
            }
        });
        $(".app_title li.active a").click();
    });
    $(function() {
        window.pageobject = $.extend({groupsaccount: {}, groupsright: {}}, window.pageobject);
        window.staffhtmlTemplet = '<div class="checkbox"><label><input name="staff_account" type="checkbox" value="{1}"> {2} </label></div>';
        var tmp = '<li id="{1}" account="{3}" class="alert {4}">' +
                '<span>{2}</span><button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '</li>';
        var righttmp = "<tr class='{6}' id='group_url_{1}_{5}'><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td>" +
                "<td><a href='${ROOT}/url/edit.html?id={1}' target='_blank' class='btn btn-primary btn-xs'>编辑</a></td></tr>";
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
            var hs = this.href.split("#")[1];
            $("#" + hs + " .select_staff:first").append($("#department_contain"));
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
    //新增组
    $(function() {
        $(".add-groups-btn").click(function() {
            var wid = $(this).attr("webappid");
            $.request($("#add_groups_form"), function(data, _, xhr) {
                if (data.success) {
                    $('#add_group_model_bt').modal('hide');
                    location.href = templetRepalce("${ROOT}/url/{2}_{1}.html", wid, data.data.id);
                }
            });
        });
    });
    //组列表
    $(function() {
        $.getJSON("${ROOT}/groups/list.ajax", function(dt) {
            var html = "", tmpet = '<div class="radio"><label><input type="radio" name="_groups_id" value="{1}" />{2}</label></div>';
            if (dt.success) {
                for (var i in dt.data) {
                    var item = dt.data[i];
                    html += templetRepalce(tmpet, item.id, item.name);
                }
            } else {
                html += templetRepalce(tmpet, 0, "");
            }
            html += "";
            $(".pop-groups-list").popover({html: true, content: html});
            $(".groups-right-ch").on("click", ".popover-content label input", function() {
                var the = $(this), gid = the.val();
                var wid = the.closest(".groups-right-ch").find(".pop-groups-list").popover("hide").attr("webappid");
                location.href = templetRepalce("${ROOT}/url/{2}_{1}.html", wid, gid);
            });
        });
    });

    //获取应用的 URL
    $(function() {
        var tmp = '<tr class="{6}"><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>' +
                '<a target="_blank" class="btn btn-default btn-xs" href="${ROOT}/url/edit.html?id={1}">编辑</a></td>';
        $(".app_title li a").click(function() {
            window.pageobject = window.pageobject || {};
            var appurl = window.pageobject.appurl = window.pageobject.appurl || {};
            window.pageobject.appurltyps = {0: "默认", 1: "菜单", 2: "目录"};
            var appid = $(this).attr("appid");
            var target = $(this).attr("href").split("#")[1] || "";
            var $ct = $("#" + target + " .app_url_list table tbody");
            var $sg = $("#" + target + " .app_url_content");
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
</script>
<link href="${ROOT}/resource/css/validationEngine.jquery.css" rel="stylesheet" />
<script src="${ROOT}/resource/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="${ROOT}/resource/js/jquery.validationEngine.min.js" type="text/javascript"></script>
<%@include file="../../templates/_foot.jsp" %>