<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>接口主界面</title>
    <c:import url="template/include-css.jsp"/>
    <c:import url="template/include-script.jsp"/>
</head>
<%--<body style="background-color:#bbbbbb" >--%>

    <%--<div align="center">--%>
        <%--<a href="/insert">新增接口配置</a>--%>
    <%--</div>--%>
    <%--<div align="center">--%>
        <%--<a href="/listAll?currentPage=1">查询接口配置</a>--%>
    <%--</div>--%>
    <%--<div align="center">--%>
        <%--<a href="/findwaiting?currentPage=1">等待订单查询页面</a>--%>
    <%--</div>--%>
<%--</body>--%>
<body>

<div id="container">

    <div id="header">
        <h1>通用接口后台主页</h1>
    </div>

    <div id="menu">
        <h2>菜单</h2>
        <ul>
            <li><a href="/insert">新增接口配置</a></li>
            <li><a href="/listAll?currentPage=1">查询接口配置</a></li>
            <li><a href="/insertInterfaceRule">新增接口协议规则配置</a></li>
            <li><a href="/listInterfaceRule?currentPage=1">查询接口协议</a></li>
            <li><a href="/findwaiting?currentPage=1">等待订单查询页面</a></li>
            <li><a href="/xmlUtils">协议工具生成页面</a></li>
            <%
               String test = request.getParameter("test");
               if("test".equals(test)){
                 out.write("<li><a href=\"/interfaceTest?test=test\">测试</a></li>");
               }
            %>


        </ul>
    </div>

    <div id="content">
        <c:choose>
            <c:when test="${requestScope.pageType == 'list'}">
                <c:import url="list.jsp"/>

            </c:when>
            <c:when test="${requestScope.pageType == 'insert'}">
                <c:import url="insert.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'edit'}">
                <c:import url="edit.jsp"/>
            </c:when>

            <c:when test="${requestScope.pageType == 'success'}">
                <c:import url="success.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'waitlist'}">
                <c:import url="Orderlist.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'waitlistdetail'}">
                <c:import url="OrderListDetail.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'test'}">
                <c:import url="rechargeTest.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'editrule'}">
                <c:import url="interfaceRule/editInterfaceRule.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'insertInterfaceRule'}">
                <c:import url="interfaceRule/insertInterfaceRule.jsp"/>
            </c:when>
            <c:when test="${requestScope.pageType == 'listAllRules'}">
                <c:import url="interfaceRule/listInterfaceRule.jsp"/>
            </c:when>
            <c:otherwise>
                <c:import url="list.jsp"/>
            </c:otherwise>
        </c:choose>

        </div>
</div>
<c:import url="template/inculde-foot.jsp"/>
</body>
</html>
