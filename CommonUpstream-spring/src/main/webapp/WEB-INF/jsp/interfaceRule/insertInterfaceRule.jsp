<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/5/23
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>新增接口协议配置</title>
</head>
<body>
<c:set var="size" value="70" scope="request"/>
<form action="/insertProtcol" method="post" >
    <div id="update" align="center" >
        <fieldset class="filset1">
            <legend>接口配置表</legend>
            <%--<input id="interfacename" name="interfacename" type="text" value="中移电子">--%>
            <table style="font-size: 14px;color: #ff9999;">

                <tr>
                    <td>代理商编号：</td>
                    <td><input id="merchantno" name="merchantno" type="text" size="${size}">
                    </td>
                </tr>

                <tr>
                    <td>充值请求：</td>
                    <td> <textarea id="rechargerequest" name="rechargerequest" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>充值响应：</td>
                    <td> <textarea id="rechargeresponse" name="rechargeresponse" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>订单查询请求：</td>
                    <td> <textarea id="queryrequest" name="queryrequest" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>订单查询响应：</td>
                    <td> <textarea id="queryresponse" name="queryresponse" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>余额查询请求：</td>
                    <td> <textarea id="banlancerequest" name="banlancerequest" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>余额查询响应：</td>
                    <td> <textarea id="balanceresponse" name="balanceresponse" type="text" class="textarea"></textarea></td>
                </tr>

                <tr>
                    <td>冲正请求：</td>
                    <td> <textarea id="unrechargerequest" name="unrechargerequest" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>冲正响应：</td>
                    <td> <textarea id="unrechargeresponse" name="unrechargeresponse" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>回调请求：</td>
                    <td> <textarea id="callbackrequest" name="callbackrequest" type="text" class="textarea"></textarea></td>
                </tr>
                <tr>
                    <td>回调响应：</td>
                    <td> <textarea id="callbackresponse" name="callbackresponse" type="text" class="textarea"></textarea>
                         <input type="submit" value=" 提  交 " />
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
</form>
</body>
</html>
