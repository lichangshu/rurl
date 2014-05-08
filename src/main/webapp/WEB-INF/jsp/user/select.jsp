<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../include/env.jsp" %>
<style type="text/css">
    .tree {
        min-height:20px;
    }
    .tree li.deperment-node {
        list-style-type:none;
        margin:0;
        padding:10px 5px 0 5px;
        position:relative
    }
    .tree li li.deperment-node::before, .tree li li.deperment-node::after {
        content:'';
        left:-20px;
        position:absolute;
        right:auto
    }
    .tree li.deperment-node::before {
        border-left:1px solid #999;
        bottom:50px;
        height:100%;
        top:0;
        width:1px
    }
    .tree li.deperment-node::after {
        border-top:1px solid #999;
        height:20px;
        top:25px;
        width:25px
    }
    .tree li.deperment-node span {
        -moz-border-radius:5px;
        -webkit-border-radius:5px;
        border:1px solid #999;
        border-radius:5px;
        display:inline-block;
        padding:3px 8px;
        text-decoration:none
    }
    .tree li.parent_li>span {
        cursor:pointer
    }
    .tree>ul>li::before, .tree>ul>li::after {
        border:0
    }
    .tree li:last-child::before {
        height:30px
    }
    .tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span {
        background:#eee;
        border:1px solid #94a0b4;
        color:#000
    }
    .tree .popover.right .arrow{
        top: 3.3em;
    }
    .tree .btn-group, .input-prepend{
        margin-bottom: 0;
    }
    #pop_account_seach_form{
        position: relative;
    }
    #pop_account_seach_form .popover.bottom .arrow{
        left: 2em;
    }
    #pop_account_seach_form .popover .popover-title .glyphicon-remove{
        margin: -6px; 
    }
</style>
<div class="tree-content clearfix">
    <div class="col-md-8">
        <div class="tree" id="department-tree">
            <div class="progress progress-striped active">
                <div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 75%">
                    <span class="sr-only">75% Complete</span></div></div>
        </div>
    </div>
    <div class="col-md-4">
        <form id="pop_account_seach_form" action="${ROOT}/user/get_account.ajax" class="form-search" onsubmit="return false;">
            <div class="input-group">
                <input type="text" name="account" class="pop_ssl form-control" placeholder="英文账户" data-trigger="manual" data-original-title="<i class='pull-right glyphicon glyphicon-remove'></i>"
                       data-content="暂未找到数据...." data-toggle="popover" data-placement="bottom" data-content="稍等会自动查询"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn btn-default">搜索</button>
                </span>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        window.pageobject = window.pageobject || {};
        //获取的员工信息回放到该全局变量中!id 为 属性,的对象中 
        window.pageobject.staffes = window.pageobject.staffes || {};
        var loadhtml = '<div class="progress progress-striped active">' +
                '<div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 75%">' +
                '<span class="sr-only">75% Complete</span></div></div>';
        $.requestCacheJson("user_department_ajax", "${ROOT}/user/department.ajax", function(json) {
            var dt = json.data, dpmap = {}, root = [];
            //数组转 map 方便查找
            for (var i in dt) {
                var item = dt[i];// {id, name, pid}
                item.chnodes = [];
                dpmap[item.id] = item;
            }
            //插入子节点
            for (var i in dt) {
                var item = dt[i], pnode = dpmap[item.pid];
                if (pnode) {
                    pnode.chnodes.push(item);
                } else {
                    root.push(item);
                }
            }
            //获取根节点
            window.pageobject = $.extend({}, window.pageobject, {treenodes: root});
            window.pageobject = $.extend(window.pageobject, {nodes: dpmap});
            $(".tree").html(depart_tree_html(root));//创建树..
            $('.tree > ul > li > .warp > .next > i').removeClass("glyphicon-folder-close").addClass("glyphicon-folder-open");
            $('.tree ul ul ul li').hide();
            $('.tree li:has(ul)').addClass('parent_li').find('> .warp > .next').attr('title', '点击展开');
            $('.tree li a.show-account').popover({content: loadhtml, html: true, toggle: "popover"});
        });
        //生成节点的HTML dp 递归深度.. 超出 10 层的数据被丢弃
        function depart_tree_html(nArray, dp) {
            var ul = "<ul>";
            dp = dp || 0;
            for (var i in nArray) {
                var node = nArray[i];
                ul += "<li class='deperment-node'>";
                if (node.chnodes && node.chnodes.length > 0 && dp < 10) {
                    ul += createnode(node.name, "glyphicon-folder-close", node.id);
                    ul += depart_tree_html(node.chnodes, dp + 1);
                } else {
                    ul += createnode(node.name, "glyphicon-leaf", node.id);
                }
                ul += "</li>";
            }
            ul += "</ul>";
            return ul;
        }
        function createnode(name, clzz, html) {
            return templetRepalce('<div class="warp btn-group"><a class="next btn btn-default"><i class="glyphicon {2}"></i> {1} </a>' +
                    '<a class="btn btn-default popover-close-other show-account" data-original-title="<i class=\'glyphicon glyphicon-remove\'></i>' +
                    ' {1} 直属员工" data-nodeid="{3}" href="">' +
                    '<i class="glyphicon glyphicon-circle-arrow-right"></i></a></div>', name, clzz, html || "");
        }
    });
    $(function() {
        $(document).on("click", '.tree li.parent_li > .warp .next', function(e) {
            var children = $(this).closest('li.parent_li').find(' > ul > li');
            if (children.is(":visible")) {
                children.hide('fast');
                $(this).attr('title', '点击展开').find(' > i').addClass('glyphicon-folder-close').removeClass('glyphicon-folder-open');
            } else {
                children.show('fast');
                $(this).attr('title', '点击收起').find(' > i').addClass('glyphicon-folder-open').removeClass('glyphicon-folder-close');
            }
            e.stopPropagation();
        });
    });
    //弹窗..获取部门的员工
    $(function() {
        var templet = "<p><i class='glyphicon glyphicon-user'></i> {2} </p>";
        $(document).on("click", ".tree ul li .show-account", function() {
            var the = $(this), id = the.attr("data-nodeid"), nodes = (window.pageobject || {}).nodes || [], dnode = nodes[id], did = dnode.id;
            $.requestCacheJson("ladpuser.ajax_dn" + dnode.id, "${ROOT}/user/ladpuser.ajax", {did: did}, function(json) {
                var popover = the.next(), pg = window.pageobject.staffes;
                if (popover.hasClass("popover")) {
                    var dt = json.data, html = "<div class='staff_list'>";
                    for (var i in dt) {
                        var it = dt[i];
                        pg[it.account] = it;
                        // 弹窗员工的 模版可以被替换..
                        html += templetRepalce(window.staffhtmlTemplet || templet, it.account, it.name, did);
                    }
                    html += "</div>"
                    if (dt.length === 0) {
                        html = "<div>暂未找到数据....</div>";
                    }
                    popover.find(".popover-content:first").html(html);
                }
            });
            return false;
        });
    });
    //搜索
    $(function() {
        var loadhtml = '<div class="progress progress-striped active">' +
                '<div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 75%">' +
                '<span class="sr-only">75% Complete</span></div></div>';
        $("#pop_account_seach_form .pop_ssl").popover({html: true});
        var __time_out = false, __ajax = false;
        $("#pop_account_seach_form input[name='account']").keyup(function() {
            var val = $.trim(this.value), fm = $(this.form);
            if (val) {
                if (__time_out)
                    clearTimeout(__time_out);
                __time_out = setTimeout(function() {
                    fm.submit();
                }, 500);
            }
        });
        $("#pop_account_seach_form").submit(function() {
            if (__ajax) {
                try {
                    return;
                } catch (e) {
                }
            }
            var the = $(this), val = this.account.value, $acc = $(this.account);
            if (the.find(".popover").length === 0) {
                $acc.popover("show");
            }
            if (!val) {
                return;
            }
            var templet = "<p><i class='glyphicon glyphicon-user'></i>{2}</p>";
            __ajax = $.requestCacheJson("pop_account_seach_form_" + val, this.action, the.serialize(), function(data) {
                if (data.success) {
                    __ajax = false;
                    var dt = data.data, html = "<div class='staff_list'>", pg = window.pageobject.staffes;
                    for (var i in dt) {
                        var it = dt[i];
                        pg[it.id] = it;
                        html += templetRepalce(window.staffhtmlTemplet || templet, it.id, it.name, it.did);
                    }
                    if (dt.length === 0) {
                        html += templetRepalce("<div>未找到关于【{1}】的数据</div>", val);
                    }
                    html += "</div>";
                    the.find(".popover .popover-content:first").html(html);
                }
            });
            the.find(".popover .popover-content:first").html(loadhtml);
            return false;
        });
    });
</script>