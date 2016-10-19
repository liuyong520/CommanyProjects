<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/4/19
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>订单等待列表</title>
    <c:import url="template/include-script.jsp"/>
</head>
<body style="background-color:#666666">
<div id="listAllpage" align="center">
    <form action="/findwait" method="post">
        <fieldset style="width:90%">
            <legend>查询条件</legend>
            <table width="80%" style="font-size: 14px;color: #ff9999;">
                <tr>
                    <td> 代理商编号:<input type="text" id="merchantno" name="merchantno"></td>
                    <td> 发送订单号:<input type="text" id="sendorderid" name="sendorderid"></td>
                    <td> 发送状态:<select id="status" name="status">
                        <%--<option value=""></option>--%>
                        <option value="0">未发送上游接口</option>
                        <option value="1">已发送，却无返回结果</option>
                        <option value="2">已发送，返回结果异常</option>
                    </select></td>
                </tr>
                <tr>
                    <td> 订单发送开始时间:<input class="laydate-icon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" id="starttime" name="starttime"></td>
                    <td>
                        订单发送截止时间:<input class="laydate-icon" id="endtime" name="endtime" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                        <input type="submit"  value="查询">
                    </td>
                </tr>
               </table>
            <%--接口名称:<input type="text" id="interfacename" name="interfacename">--%>


           <br/><br/>


        </fieldset>
    </form>
</div>
<div id="list" align="center" >
    <fieldset style="width:90%">
        <legend>等待订单详情表</legend>
        <table width="100%" style="font-size: 14px;color: #ff9999;">
            <thead>
            <tr>
                <td>序号</td>
                <td>代理商编号</td>
                <td>接口类型</td>
                <td>发送订单号</td>
                <td>充值号码</td>
                <td>充值金额(分)</td>
                <td>发送状态</td>
                <td>发送时间</td>
                <td>操作</td>
            </tr>
            </thead>
            <%int i=1;%>
            <c:forEach var="row" items="${requestScope.pageResult.list}">
                <tr>
                    <td><%=i++%></td>
                    <td>${row.merid}</td>
                    <td>${row.rechargeType.name}</td>
                    <td>${row.sendorderid}</td>
                    <td>${row.mob}</td>
                    <td>${row.value}</td>
                    <td>${row.sendStatus.status}</td>
                    <td>${row.ordertime}</td>
                    <td><a href="/deleteOrder?sendorderids=${row.sendorderid}" />删除</td>
                </tr>
            </c:forEach>
            <tr>

                <th><a href="/findwaiting?currentPage=${pageResult.firstPage}">首页</a>${pageResult.firstPage}</th>

                <th><a href="/findwaiting?currentPage=${pageResult.prePage}">上一页</a>${pageResult.prePage}</th>

                <th><a href="/findwaiting?currentPage=${pageResult.nextPage}">下一页</a>${pageResult.nextPage}</th>

                <th><a href="/findwaiting?currentPage=${pageResult.totalPage}">尾页</a>${pageResult.totalPage}</th>

                <th>当前${pageResult.currentPage}/${pageResult.totalPage}页</th>

                <th>总条数:${pageResult.count}</th>

            </tr>
        </table>
    </fieldset>
</div>
</body>
</html>
