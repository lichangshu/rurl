<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../include/env.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${title} - RURL 权限管理 - </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ROOT}/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${ROOT}/resource/css/main.css" rel="stylesheet" media="screen">
    <script src="${ROOT}/resource/js/jquery.min.js"></script>
</head>
<body>
<div id="wrap">
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${ROOT}/">权限管理系统</a>
        </div>
        <%@include file="_nav.jsp" %>
    </div>
</div><!--/.navigation-->
<div class="container warp_min_height">