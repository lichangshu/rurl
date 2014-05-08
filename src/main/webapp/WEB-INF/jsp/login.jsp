<%-- 
    Document   : login
    Created on : 2014-5-7, 17:42:04
    Author     : changshu.li
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><%@include file="../include/env.jsp" %>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>登录</title>
        <link href="${ROOT}/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
          <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>

        <div class="container">
            <div class="col-sm-6 col-sm-offset-3">
                <form class="form-signin" role="form" method="POST" action="${ROOT}/login.html">
                    <h2 class="form-signin-heading">登录</h2>
                    <c:if test="${!empty msg}"><div class="alert alert-danger">${msg}</div></c:if>
                    <input type="text" name="account" class="form-control" placeholder="account" required autofocus>
                    <input type="password" name="password" class="form-control" placeholder="Password" required>
                    <hr/>
                    <div class="col-sm-6 col-sm-offset-3">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                    </div>
                </form>
            </div>
        </div> <!-- /container -->


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>
