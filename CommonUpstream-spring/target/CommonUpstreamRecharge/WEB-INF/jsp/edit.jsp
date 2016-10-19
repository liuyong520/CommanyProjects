<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/4/12
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>编辑页面</title>
</head>
<body>

<c:choose>
    <c:when test="${requestScope.show == 'edit'}">
        <c:set value="/update" var="url" scope="page"/>
    </c:when>
    <c:when test="${requestScope.show == 'readonly'}">
        <c:set value="/edit?cmd=edit" var="url" scope="page"/>
        <c:set var="readyStyle" value="disabled='disabled'"/>

    </c:when>
</c:choose>
<form action="${url}" method="post" >
    <div id="update" align="center" >
    <fieldset style="width:80%">
        <legend>接口配置表</legend>
        <%--<input id="interfacename" name="interfacename" type="text" value="中移电子">--%>

        <table style="font-size: 14px;color: #ff9999;">
            <tr>
                <td><input id="id" name="id" type="text" hidden="hidden" value="${config.id}"  ></td>
            </tr>
            <tr>
                <td>接口名称：</td>
                <td><input id="interfacename" name="interfacename" type="text" value="${config.interfacename}" size="50" ${readyStyle} ></td>
            </tr>
            <tr>
                <td>代理商编号：</td>
                <td><input id="merchantno" name="merchantno" type="text" value="${config.merchantno}"size="50" ${readyStyle} >
                </td>
            </tr>
            <tr>
                <td>接口类型：</td>
                <td><input id="rechargetype" name="rechargetype" type="text" value="${config.rechargetype}"size="50"  ${readyStyle} >
                </td>
            </tr>
            <tr>
                <td>充值地址：</td>
                <td> <input id="rechargeurl" name="rechargeurl" type="text" value="${config.rechargeurl}" size="50"  ${readyStyle} /></td>
            </tr>
            <tr>
                <td>余额查询地址：</td>
                <td>  <input id="banlanceurl" name="banlanceurl" type="text" value="${config.banlanceurl}" size="50" ${readyStyle} /></td>
            </tr>
            <tr>
                <td>订单查询地址：</td>
                <td>  <input id="queryurl" name="queryurl" type="text" value="${config.queryurl}" size="50"  ${readyStyle} /></td>
            </tr>
            <tr>
                <td>冲正地址：</td>
                <td>   <input id="unrechargeurl" name="unrechargeurl" type="text" value="${config.unrechargeurl}" size="50"  ${readyStyle} /></td>
            </tr>
            <tr>
                <td>回调地址：</td>
                <td>   <input id="backurl" name="backurl" type="text" value="${config.backurl}" size="50"  ${readyStyle} /></td>
            </tr>
            <tr>
                <td>是否开启"订单不存在"置失败处理：</td>
                <td>   <input id="isexpire" name="isexpire" type="text" value="${config.isexpire}" size="50" ${readyStyle} /></td>
            </tr>
            <tr>
                <td>"订单不存在"置失败处理时间（分钟）：</td>
                <td>   <input id="expiretime" name="expiretime" type="text" value="${config.expiretime}" size="50" ${readyStyle} /></td>
            </tr>
            <tr>
                <td>"是否通用接口：</td>
                <td>   <input id="interfacetype" name="interfacetype" type="text" value="${config.interfacetype}" size="50" ${readyStyle} /></td>
            </tr>
            <tr>
                <td>密钥key：</td>
                <td>   <input id="encrykey" name="encrykey" type="text" value="${config.encrykey}" size="50" ${readyStyle} />
                    <c:if test="${show == 'readonly'}">
                         <input type="submit" value="编辑"/></td>
                    </c:if>
                    <c:if test="${show == 'edit'}">
                        <input type="submit" value="提交"/></td>
                    </c:if>
            </tr>
            </table>
        </fieldset>
    </div>
    </form>
</body>
</html>
