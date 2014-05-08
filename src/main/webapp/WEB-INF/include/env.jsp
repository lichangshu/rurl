<%@page contentType="text/html" pageEncoding="UTF-8"%><%--
--%><%--

改页面 不允许有 回车换行 和 多余的空格

--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
--%><c:set scope="request" var="ROOT" value="http://${header.Host}${pageContext.request.contextPath}"/><%--




--%>