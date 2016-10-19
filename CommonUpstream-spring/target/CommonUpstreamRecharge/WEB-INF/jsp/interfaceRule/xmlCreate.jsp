<%--
  Created by IntelliJ IDEA.
  User: y
  Date: 2016/5/23
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>xml 生成工具页面</title>
    <c:import url="../template/include-script.jsp"/>
    <script type="text/javascript">
        //删除行的函数，必须要放domready函数外面
        function deltr(delbtn) {
            console.error("del");
            $(delbtn).parents("tr").remove();
        }
        ;
        jQuery(function ($) {
            //添加行
            var index = 0;
            var trNumber = $('#table2>tbody>tr').length - 11;
            $("#add1").click(function () {
                $("#table2>tbody").append('<tr><td>字段名称:</td>' +
                        '<td><input id="mapdata[' + trNumber + '].name" name="mapdata[' + trNumber + '].name" type="text"/></td>' +
                        '<td>字段类型：</td>' +
                        '<td><select id="mapdata[' + trNumber + '].type" name="mapdata[' + trNumber + '].type">' +
                        '<option value="String">String</option>' +
                        '<option value="Constant">Contant</option>' +
                        '<option value="Date">Date</option>' +
                        '<option value="Map">Map</option>' +
                        '<option value="Password">Password</option>' +
                        '<option value="Sign">Sign</option>' +
                        '</select>' +
                        '</td>' +
                        '<td>默认值为:</td>' +
                        '<td><input id="mapdata[' + trNumber + '].defaultvalue" name="mapdata[' + trNumber + '].defaultvalue" type="text"/></td>' +
                        '<td>对应我方协议字段:</td>' +
                        '<td><input id="mapdata[' + trNumber + '].nnkname" name="mapdata[' + trNumber + '].nnkname" type="text"/></td>' +
                        '<td><button onclick="deltr(this)">删除</button></td></tr>')
                trNumber++;
                index++;
            });

        });
    </script>

</head>
<body>
<c:set var="size" value="20" scope="request"/>


<div id="update" align="center">

    <fieldset style="width:90%;">
        <legend>接口配置表</legend>
        <%--<input id="interfacename" name="interfacename" type="text" value="中移电子">--%>
        <form action="/xmlCreate" method="post">
            <table style="font-size: 14px;color: #ff9999;" id="table2">
                <tbody>
                <tr>
                    <td colspan="9" align="center"><input type="button" value="新增一行" id="add1"/>&nbsp;&nbsp;<input
                            type="submit" value="提交"/></td>
                </tr>
                <tr>
                    <td>Http请求方式：</td>
                    <td>
                        <select type="text" id="method" name="method">
                            <option value="POST">POST</option>
                            <option value="GET">GET</option>
                        </select>
                    </td>
                    <td>协议类型：</td>
                    <td>
                        <select type="text" id="protoclType" name="protoclType">
                            <option value="JSON">JSON</option>
                            <option value="STR">STR</option>
                            <option value="XML">XML</option>
                        </select>
                    </td>
                    <td>异常协议类型：</td>
                    <td>
                        <select type="text" id="exceptionProtoclType" name="exceptionProtoclType">
                            <option value="JSON">JSON</option>
                            <option value="STR">STR</option>
                            <option value="XML">XML</option>
                        </select>
                    </td>
                    <td>签名算法：</td>
                    <td>
                        <select type="text" id="encryptionType" name="encryptionType">
                            <option value="MD5">MD5</option>
                            <option value="DES">DES</option>
                            <option value="SHA1">SHA1</option>
                            <option value="BASE64">BASE64</option>
                            <option value="AES">AES</option>
                        </select>
                    </td>
                    <td>签名大小写：</td>
                    <td>
                        <select type="text" id="encryptionCase" name="encryptionCase">
                            <option value="lowcase">lowcase</option>
                            <option value="capital">capital</option>
                            <option value="uppercase">uppercase</option>
                        </select>
                    </td>
                    <td>是否需要签名或者验签：</td>
                    <td>
                        <select type="text" id="encryOrCheckSign" name="encryOrCheckSign">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                    <td>字符编码：</td>
                    <td>
                        <select type="text" id="charset" name="charset">
                            <option value="utf-8">utf-8</option>
                            <option value="GBK">GBK</option>
                        </select>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        签名顺序，逗号隔开：<input type="text" id="signSquence" name="signSquence" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                       签名内容：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="signFormart" name="signFormart" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        协议成功返回正则表达式：<input type="text" id="regexSuccess" name="regexSuccess" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        协议失败或者异常返回正则表达式：<input type="text" id="regexFail" name="regexFail" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        响应成功结果集：&nbsp;&nbsp;<input type="text" id="responseSuccessCodeSet" name="responseSuccessCodeSet" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        响应失败结果结果集：<input type="text" id="responseFailCodeSet" name="responseFailCodeSet" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        成功结果集：&nbsp;&nbsp;<input type="text" id="successCodeSet" name="successCodeSet" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        失败结果集：&nbsp;&nbsp;<input type="text" id="failCodeSet" name="failCodeSet" size="120"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="9" align="left">
                        不确定结果集：&nbsp;&nbsp;<input type="text" id="uncertainCodeSet" name="uncertainCodeSet" size="120"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <table id="table1" style="font-size: 14px;color: #ff9999;" align="left">
            <tr>
                <td>xml 生成结果：</td>
                <td><textarea id="xml" name="xml" cols="200" rows="15">${requestScope.result}</textarea></td>
            </tr>
        </table>
    </fieldset>
</div>

<div>
    详细说明：
    <ul>
        <li>字段名称：为对方协议文档上的协议字段</li>
        <li>字段类型：有String,Constant,Date,Map
            注意: Map时，配置为json格式
        </li>
        <li>对应我方协议字段：该协议对应我方协议哪个字段：<br>
            result;// 结果状态码 非1就是失败<br>
            sendorderid;// 007订单号<br>
            merid;// 代理商编号<br>
            meraccount;// 代理商账户<br>
            orderid;// 代理商订单号<br>
            cardtype;// 卡类别<br>
            value;// 卡面值（申请面值），单位分<br>
            timeout;// 允许的超时时间 , 以秒为单位<br>
            province;// 省份<br>
            command;// 交易命令（11.查询；22.充值申请及同步申请响应结果（分为：接受订单结果（不代表充值结果）；充值结果。）;33.充值结果）<br>
            cardsn;// 充值卡序列号<br>
            cdkey;// 充值卡密码<br>
            mob;// 充值手机号码<br>
            ordertime;// 本指令发出时的发送时间<br>
            url;// Web方式回调地址<br>
            attach;// 自定义信息, 007ka方直接返回.不能包含 & ? 等特别字符<br>
            orgCommand;//原订单交易命令<br>
            realValue;//实际充值金额<br>
            chgTime;//充值操作时间<br>
            cfmTime;//确认时间<br>
            cbRetry;//第几次尝试回调<br>
            checkTime;//对账时间<br>
            tranInfo;//交易时间
            merchantNo //商户id(代理商编号)<br>
            meridAccount //代理商平台配置<br>
            interfaceName //接口名称<br>
            rechargeType //接口类型 0:话费充值，1流量充值，2:加油卡充值<br>
            rechargeUrl //充值地址<br>
            banlanceUrl //余额查询地址<br>
            queryUrl //订单查询地址<br>
            unrechargeUrl //冲正地址<br>
            backUrl //回调地址<br>
            isExpire //是否开启多少时间之后不确定订单当成失败处理 0：关闭，1开启<br>
            expireTime //多少分钟之后，订单不存在的订单，当成失败处理。<br>
            encrykey //分配的密钥<br>
            interfaceType //是否为通用接口<br>
        </li>
    <ul>
</div>

</body>
</html>
