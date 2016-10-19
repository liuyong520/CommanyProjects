<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/5/17
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>测试页面</title>
    <c:import url="template/include-css.jsp"/>
    <c:import url="template/include-script.jsp"/>
    <script type="application/javascript">
        $('body').on('click','#submit1',function() {
            if($('#merchantno').val()==''){
                alert('请输入代理商编号！');
                //console.error("dsds");
                return false;
            }

        });
        $('body').on('click','#cancer1',function(){

            $.post("/cancerTest",{cancer:"all"},
            function(data) {
                if (data.status == 1) {
                    alert(data.content);
                    window.location.reload();
                } else {
                    alert(data.content);
                }
            },'json'
            );
        });
        $('body').on('click','#cancer2',function(){

            $.post("/cancerTest",{cancer:"all"},
                    function(data) {
                        if (data.status == 1) {
                            alert(data.content);
                            window.location.reload();
                        } else {
                            alert(data.content);
                        }
                    },'json'
            );
        });
        $('body').on('click','#cancer3',function(){

            $.post("/cancerTest",{cancer:"all"},
                    function(data) {
                        if (data.status == 1) {
                            alert(data.content);
                            window.location.reload();
                        } else {
                            alert(data.content);
                        }
                    },'json'
            );
        });
        $('body').on('click','#submit2',function() {
            if ($('#merchantno1').val()=='') {
                alert("请填代理商编号！");
                return false;
            }
            if ($('#sendorderId').val()=='') {
                alert("请填发送订单号！");
                return false;
            }
            if ($('#mob').val()=='') {
                alert("请填手机号！");
                return false;
            }
            if ($('#value').val()=='') {
                alert("请填面值！");
                return false;
            }
        });
    </script>
</head>
<body>
    <div align="center" >
        <form action="/RechargeTestAll" method="post">
            <fieldset class="filset1">
                <legend>所有代理商接口测试</legend>
                <table class="table1">
                    <tr>
                        <td><input type="submit" value="全部代理商接口随机测试">
                            <input id="cancer1" type="button" value="停止测试"></td></td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
    <div align="center">
        <form action="/RechargeTest" method="post">
            <fieldset class="filset1">
                <legend>代理商测试</legend>
                <table class="table1">
                    <tr>
                     <td>代理商编号：</td>
                     <td><input id="merchantno" name="merchantno" type="text"></td></td>
                    </tr>
                    <tr>
                        <td><input id="submit1" type="submit" value="代理商随机发送订单测试">
                        <input id="cancer2" type="button" value="停止测试"></td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
    <div align="center">
        <form action="/RechargeTestOnly" method="post">
            <fieldset class="filset1">
                <legend>代理商单笔订单测试</legend>
                <table class="table1">
                    <tr>
                        <td>代理商编号：</td>
                        <td><input id="merchantno1" name="merchantno1" type="text"></td></td>
                    </tr>
                    <tr>
                        <td>发送订单号：</td>
                        <td><input id="sendorderId" name="sendorderId" type="text"></td></td>
                    </tr>
                    <tr>
                        <td>发送手机号：</td>
                        <td><input id="mob" name="mob" type="text"></td></td>
                    </tr>
                    <tr>
                        <td>面值：</td>
                        <td><input id="value" name="value" type="text">
                        </td></td>
                    </tr>
                    <tr>
                        <td>
                            <input id="submit2" type="submit" value="代理商单笔订单测试">
                            <input id="cancer3" type="button" value="停止测试">
                        </td></td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>

</body>
</html>
