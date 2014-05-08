<%-- 
Document   : _nav
Created on : 2014-2-22, 21:49:39
Author     : changshu.li
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="w-head-nav" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">
        <li><a href="${ROOT}/">首页</a></li>
        <li><a href="${ROOT}/app/">应用</a></li>
        <li><a href="${ROOT}/url/">URL</a></li>
        <li><a href="${ROOT}/groups/">组</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><i class="glyphicon glyphicon-user"></i> 欢迎 ${empty LOGIN_EMPLOYE ? '<i>未登录用户</i>': LOGIN_EMPLOYE.name}</a></li>
        <li><a href="${ROOT}/logout.html"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
    </ul>
</div><!--/.nav-collapse -->
<script type="text/javascript">
    $(function(){
        $("#w-head-nav li a").each(function(){
            var u = this.href.replace("${ROOT}","").split("/")[1];
            var h = location.href.replace("${ROOT}","").split("/")[1]
            if(u === h){
                $(this).parent().addClass("active");
                return false;
            }
        }); 
    });
</script>