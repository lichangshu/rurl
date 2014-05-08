<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object g = request.getAttribute("groups");
    List list = new ArrayList();
    list.add(g);
    request.setAttribute("list", list);
%>
<%@include file="list.jsp" %>
<script type="text/javascript">
    $(function() {
        $(".tabbable ul li a:first").click();
    });
</script>
