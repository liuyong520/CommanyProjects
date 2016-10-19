<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<%@ page import="com.nnk.interfacetemplate.common.StringUtil" %>
<%@ include file="/WEB-INF/jsp/template/include.jsp" %>
<html>
<head>
    <title>查询页面</title>
</head>
<body>
    <div id="listAllpage" align="center">
        <form action="/find" method="post">
            <fieldset style="width:95%">
                <legend>查询条件</legend>

            接口名称:<input type="text" id="interfacename" name="interfacename">
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
                        <td hidden="hidden">数据ID</td>
                        <td>接口名称</td>
                        <td hidden="hidden">接口类型</td>
                        <td>代理商编号</td>
                        <td>充值地址</td>
                        <td>查询地址</td>
                        <td>余额查询地址</td>
                        <td>冲正地址</td>
                        <td>回调地址</td>
                        <td>密钥</td>
                        <td title="说明：订单不存在(当失败处理)">是否置失败</td>
                        <td title="说明：(当失败处理)时间">置失败时间</td>
                        <td>操作</td>
                    </tr>
                </thead>
                    <%int i=1;%>
                    <c:forEach var="row" items="${requestScope.pageResult.list}">

                        <tr>
                            <td width="1%"><%=i++%></td>
                            <td hidden="hidden">${row.id}</td>
                            <td width="5%">${row.interfacename}</td>
                            <td hidden="hidden">${row.rechargetype}</td>
                            <td width="7%">${row.merchantno}</td>
                            <td title="${row.rechargeurl}" width="12%">${nnk:SubStr(row.rechargeurl,25)}</td>
                            <td title="${row.queryurl}" width="12%">${nnk:SubStr(row.queryurl,25)}</td>
                            <td title="${row.banlanceurl}" width="12%">${nnk:SubStr(row.banlanceurl,25)}</td>
                            <td title="${row.unrechargeurl}" width="12%">${nnk:SubStr(row.unrechargeurl,25)}</td>
                            <td title="${row.backurl}" width="12%">${nnk:SubStr(row.backurl,25)}</td>
                            <td width="8%">${nnk:SubStr(row.encrykey,20)}</td>
                            <td width="5%">${row.isexpire}</td>
                            <td width="5%">${row.expiretime}</td>
                            <td><a href='/edit?cmd=remove&id=${row.id}'>删除</a>
                                <a href='/edit?cmd=edit&id=${row.id}'>修改</a>
                                <a href='/edit?cmd=view&id=${row.id}'>详情</a>
                            </td>

                        </tr>
                </c:forEach>

            </table>
        </fieldset>
        <tr>

            <th ><a href="/listAll?currentPage=${pageResult.firstPage}">首页</a>${pageResult.firstPage}</th>

            <th><a href="/listAll?currentPage=${pageResult.prePage}">上一页</a>${pageResult.prePage}</th>

            <th><a href="/listAll?currentPage=${pageResult.nextPage}">下一页</a>${pageResult.nextPage}</th>

            <th><a href="/listAll?currentPage=${pageResult.totalPage}">尾页</a>${pageResult.totalPage}</th>

            <th>当前${pageResult.currentPage}/${pageResult.totalPage}页</th>

            <th>总条数:${pageResult.count}</th>

        </tr>
    </div>
</body>
</html>