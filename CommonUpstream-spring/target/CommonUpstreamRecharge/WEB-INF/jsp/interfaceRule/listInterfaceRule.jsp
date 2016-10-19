<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/5/23
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/jsp/template/include.jsp" %>
<html>
<head>
    <title>查询接口协议配置</title>
</head>
<body>
<div id="listAllRulepage" align="center">
    <form action="/findRule" method="post">
        <fieldset style="width:95%">
            <legend>查询条件</legend>


            代理商编号:<input type="text" id="merchantno" name="merchantno">
            <input type="submit"  value="查询">
        </fieldset>
    </form>
</div>
<div id="list" align="center" >
    <fieldset style="width:95%">
        <legend>接口配置表</legend>
        <table width="100%" style="font-size: 14px;color: #ff9999;">
            <thead>
            <tr>
                <td >序号</td>
                <td hidden="hidden">ID</td>
                <td>代理商编号</td>
                <td>充值请求报文</td>
                <td>充值响应报文</td>
                <td>订单查询请求报文</td>
                <td>订单查询响应报文</td>
                <td>余额查询请求报文</td>
                <td>余额查询响应报文</td>
                <td>冲正请求报文</td>
                <td>冲正响应报文</td>
                <td>回调请求报文</td>
                <td>回调响应报文</td>
                <td>操作</td>
            </tr>
            </thead>
            <%int i=1;%>
            <c:forEach var="row" items="${requestScope.pageResult.list}">

                <tr>
                    <td width="1%"><%=i++%></td>
                    <td hidden="hidden">${row.id}</td>
                    <td title="${row.merchantno}" width="5%">${row.merchantno}</td>
                    <td width="8%">${nnk:SubStr(row.rechargerequest,10)}</td>
                    <td width="8%">${nnk:SubStr(row.rechargeresponse,10)}</td>
                    <td width="8%">${nnk:SubStr(row.queryrequest,10)}</td>
                    <td width="8%">${nnk:SubStr(row.queryresponse,10)}</td>
                    <td width="8%">${nnk:SubStr(row.banlancerequest,10)}</td>
                    <td width="8%">${nnk:SubStr(row.balanceresponse,10)}</td>
                    <td width="8%">${nnk:SubStr(row.unrechargerequest,10)}</td>
                    <td width="8%">${nnk:SubStr(row.unrechargeresponse,10)}</td>
                    <td width="8%">${nnk:SubStr(row.callbackrequest,10)}</td>
                    <td width="8%">${nnk:SubStr(row.callbackresponse,10)}</td>

                    <td ><a href='/editrule?cmd=remove&id=${row.id}'>删除</a>
                        <a href='/editrule?cmd=edit&id=${row.id}'>修改</a>
                    </td>

                </tr>
            </c:forEach>

        </table>
    </fieldset>
    <tr>

        <th ><a href="/listInterfaceRule?currentPage=${pageResult.firstPage}">首页</a>${pageResult.firstPage}</th>

        <th><a href="/listInterfaceRule?currentPage=${pageResult.prePage}">上一页</a>${pageResult.prePage}</th>

        <th><a href="/listInterfaceRule?currentPage=${pageResult.nextPage}">下一页</a>${pageResult.nextPage}</th>

        <th><a href="/listInterfaceRule?currentPage=${pageResult.totalPage}">尾页</a>${pageResult.totalPage}</th>

        <th>当前${pageResult.currentPage}/${pageResult.totalPage}页</th>

        <th>总条数:${pageResult.count}</th>

    </tr>
</div>
</body>
</html>
