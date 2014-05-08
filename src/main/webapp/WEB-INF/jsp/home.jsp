<%-- 
    Document   : index
    Created on : 2014-4-30, 15:43:31
    Author     : changshu.li
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../templates/_head.jsp" %>

<div class="jumbotron">
    <h1>Rurl权限管理系统</h1>
    <p>该系统是基于URL对权限访问进行控制，通过用户管理系统的对接，实现用户管理跟权限的的分离。</p>
    <p>
        <a class="btn btn-lg btn-primary" href="#" role="button">doc</a>
    </p>
</div>
<div class="page-header">
    <h2>应用列表</h2>
</div>

<table class="table table-bordered table-hover">
    <thead>
        <tr><th>序号</th><th>名称</th><th>应用名</th><th>登录组</th><th>操作</th></tr></thead>
    <tbody>
        <c:forEach var="item" items="${webapplist}" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>${item.name}</td>
                <td>${item.appName}</td>
                <td><a href="${ROOT}/groups/detail.html?id=${item.loginGroup.id}">${item.loginGroup.name}</a></td>
                <td><a href="${ROOT}/app/detail/${item.id}.html"> 查看 </a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@include file="../templates/_foot.jsp" %>
