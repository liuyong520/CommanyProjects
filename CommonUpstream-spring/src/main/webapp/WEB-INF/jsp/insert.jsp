<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>上游接口</title>
</head>
<body>
<c:set var="size" value="70" scope="request"/>
<form action="/updateConfig" method="post" >
    <div id="update" align="center" >
        <fieldset style="width:80%;">
            <legend>接口配置表</legend>
            <%--<input id="interfacename" name="interfacename" type="text" value="中移电子">--%>
            <table style="font-size: 14px;color: #ff9999;">
                <tr>
                    <td>接口名称：</td>
                    <td><input id="interfacename" name="interfacename" type="text" value="中移电子" size="${size}"></td>
                </tr>
                <tr>
                    <td>代理商编号：</td>
                    <td><input id="merchantno" name="merchantno" type="text" size="${size}">
                    </td>
                </tr>
                <tr>
                    <td>接口类型：</td>
                    <td><select id="rechargetype" name="rechargetype" >
                        <option value="0">话费充值</option>
                        <option value="1">流量充值</option>
                        <option value="2">加油卡充值</option>
                    </select></td>
                </tr>
                <tr>
                    <td>充值地址：</td>
                    <td> <input id="rechargeurl" name="rechargeurl" type="text" size="${size}" /></td>
                </tr>
                <tr>
                    <td>余额查询地址：</td>
                    <td>  <input id="banlanceurl" name="banlanceurl" type="text" size="${size}"/></td>
                </tr>
                <tr>
                    <td>订单查询地址：</td>
                    <td>  <input id="queryurl" name="queryurl" type="text" size="${size}"/></td>
                </tr>
                <tr>
                    <td>冲正地址：</td>
                    <td>   <input id="unrechargeurl" name="unrechargeurl" type="text" size="${size}"/></td>
                </tr>
                <tr>
                    <td>回调地址：</td>
                    <td>   <input id="backurl" name="backurl" type="text" size="${size}"/></td>
                </tr>
                <tr>
                    <td>查询"订单不存在"当失败处理：</td>
                    <td><select id="isexpire" name="isexpire" >
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select></td>
                </tr>
                <tr>
                    <td>"订单不存在"置失败时间(分钟)</td>
                    <td><input id="expiretime" name="expiretime" type="text" size="${size}"/>
                    </td>
                </tr>
                <tr>
                    <td>是否通用接口：</td>
                    <td><select id="interfacetype" name="interfacetype" type="text">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>密钥key：</td>
                    <td>   <input id="encrykey" name="encrykey" type="text" size="${size}"/>
                        <input type="submit" value="提交"/></td>
                </tr>

            </table>
        </fieldset>
    </div>
</form>

</body>
</html>
