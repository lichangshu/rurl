<%-- 
    Document   : poplist
    Created on : 2014-2-12, 14:56:05
    Author     : changshu.li
--%><%@page import="net.microwww.rurl.domain.Groups"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%
    List<Groups> list = (List) request.getAttribute("list");
    request.setAttribute("listjson", JSONObject.toJSONString(list));
%><%@page contentType="text/html" pageEncoding="UTF-8"%>
<table class="table table-bordered table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>名称</th>
            <th>描述</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${list}" var="item" varStatus="vs">
            <tr>
                <td><input type="checkbox" value="${item.id}" /> ${item.id}</td>
                <td>${item.name}</td>
                <td>${item.discrption}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script type="text/javascript">
    $(function() {
        window.pageobject = window.pageobject || {};
        window.pageobject.groups = ${listjson};
    });
</script>