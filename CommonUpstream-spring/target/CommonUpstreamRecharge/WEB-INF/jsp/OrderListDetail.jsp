<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/4/20
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>等待详情表</title>
    <c:import url="template/include-script.jsp"/>
    <script type="text/javascript">
        $(function() {
            $('#list1').on('change','#checkAll',function(){
                console.log(111);
                if (this.checked) { // 全选
                    $("input[name='subBox']").prop("checked", true);

                } else { // 取消全选
                    $("input[name='subBox']").prop("checked", false);
                }
            })

            var sendordids = "";
            function sendorderidArr(){
                $('#list1').find('input[name="subBox"]:checked').each(function(i){
                    var id=$(this).parents('tr').find('.sendorderid').html();
                    sendordids +=id+",";
                });
            }
            $('#sumitOK1').click(function(){
                sendorderidArr();
                console.log(sendordids);
                if(sendordids.length==0){
                    alert("未选择任何订单，请选择订单！");
                    return;
                }
                var target="/backtoSystem";
                $.post(target,
                        {'sendorderids':sendordids},
                        function(data){
                            checkSubmitFlg = false;
                            if(data.status == 1){
                                alert(data.content);
                                window.location.reload();
                            }else{
                                alert(data.content);
                            }
                        },
                        'json'
                );
            })
            var checkSubmitFlg = false;
            $('#sumitOK2').click(function(){
                sendorderidArr();
                console.log(sendordids);
//                window.location.href='https://www.baidu.com?id='+sendorderidArrTemp;
                if(sendordids.length==0){
                    alert("未选择任何订单，请选择订单！");
                    return;
                }
                var target="/notifyQuery";
                $.post(target,
                        {'sendorderids':sendordids},
                        function(data){
                            checkSubmitFlg = false;
                            if(data.status == 1){
                                alert(data.content);
                                window.location.reload();
                            }else{
                                alert(data.content);
                            }
                        },
                        'json'
                );

            })
            $('#sumitOK3').click(function(){
                sendorderidArr();
                console.log(sendordids);
                if(sendordids.length==0){
                    alert("未选择任何订单，请选择订单！");
                    return;
                }
//                window.location.href='https://www.baidu.com?id='+sendorderidArrTemp;
                var target="/deleteOrders";
                $.post(target,
                        {'sendorderids':sendordids},
                        function(data){
                            if(data.status == 1){
                                alert(data.content);
                                window.location.reload();
                            }else{
                                alert(data.content);
                            }
                        },
                        'json'
                );

            })
            $('#sumitOK4').click(function(){
                sendorderidArr();
                console.log(sendordids);
                if(sendordids.length==0){
                    alert("未选择任何订单，请选择订单！");
                    return;
                }
//                window.location.href='https://www.baidu.com?id='+sendorderidArrTemp;
                var target="/setFail";
                $.post(target,
                        {'sendorderids':sendordids},
                        function(data){
                            if(data.status == 1){
                                alert(data.content);
                                window.location.reload();
                            }else{
                                alert(data.content);
                            }
                        },
                        'json'
                );

            })
        });
    </script>
</head>
<body style="background-color:#EEEEEE">
<div id="list1" align="center" >
    <fieldset style="width:90%">
        <legend>等待订单详情表</legend>

        <table width="100%" style="font-size: 14px;color: #ff9999;">
            <thead>
            <tr>
                <td><input type="checkbox" id="checkAll"/></td>
                <td>序号</td>
                <td>代理商编号</td>
                <td>发送订单号</td>
                <td>充值号码</td>
                <td>充值金额</td>
                <td>发送状态</td>
                <td>发送时间</td>
            </tr>
            </thead>
            <%int i=1;%>
            <c:forEach var="row" items="${requestScope.pageResult}">
                <tr>
                    <td><input type="checkbox" name="subBox"/></td>
                    <td><%=i++%></td>
                    <td>${row.merid}</td>
                    <td class="sendorderid">${row.sendorderid}</td>
                    <td>${row.mob}</td>
                    <td>${row.value}</td>
                    <td>${row.sendStatus.status}</td>
                    <td>${row.ordertime}</td>
                </tr>
            </c:forEach>

        </table>
        <div align="left">
            <input type="button" id="sumitOK2" name="" value="批量触发查询"/>
            <input type="button" id="sumitOK1" name="" value="批量转回系统"/>
            <input type="button" id="sumitOK3" name="" value="批量删除"/>
            <input type="button" id="sumitOK4" name="" value="批量置失败"/>

        </div>
    </fieldset>
    </div>

</body>
</html>
