package com.nnk.redis;

import com.nnk.interfacetemplate.common.XmlUtil;
import com.nnk.upstream.entity.mapping.XmlModel;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/8
 * Time: 10:25
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class XmlUtilTest {
    @Test
    public void testToBean() throws Exception {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" +
                "<xsdl>" +
                "    <method>POST</method>" +
                "    <protoclType>STR</protoclType>" +
                "    <exceptionProtoclType>JSON</exceptionProtoclType>" +
                "    <protocl>AgentID=$AgentID@Telephone=$Telephone@TelephoneType=$TelephoneType@Money=$Money@ChargeMode=$ChargeMode@RequestID=$RequestID@ExtendInfo=$ExtendInfo@CallBackUrl=$CallBackUrl@SubTime=$SubTime@Hmac=$Hmac</protocl>" +
                "    <encryptionType>MD5</encryptionType>" +
                "    <signSquence>AgentID,Telephone,TelephoneType,Money,ChargeMode,RequestID,ExtendInfo,CallBackUrl,SubTime,Key</signSquence>" +
                "    <signFormart>AgentID=$AgentID@Telephone=$Telephone@TelephoneType=$TelephoneType@Money=$Money@ChargeMode=$ChargeMode@RequestID=$RequestID@ExtendInfo=$ExtendInfo@CallBackUrl@$CallBackUrl@SubTime=$SubTime@Key=$Key</signFormart>" +
                "    <charset>utf-8</charset>" +
                "    <isEncryOrCheckSign>true</isEncryOrCheckSign>" +
                "    <mappings>" +
                "        <mapping name=\"AgentID\" nnkname=\"\" defaultvalue=\"0851fx23\" type=\"Constant\"/>" +
                "        <mapping name=\"Telephone\" nnkname=\"mob\" defaultvalue=\"\" type=\"String\"/>" +
                "        <mapping name=\"TelephoneType\" nnkname=\"\" defaultvalue=\"0\" type=\"Constant\"/>" +
                "        <mapping name=\"Money\" nnkname=\"value\" defaultvalue=\"\" type=\"String\"/>" +
                "        <mapping name=\"ChargeMode\" nnkname=\"\" defaultvalue=\"0\" type=\"Constant\"/>" +
                "        <mapping name=\"RequestID\" nnkname=\"sendorderid\" defaultvalue=\"\" type=\"String\"/>" +
                "        <mapping name=\"ExtendInfo\" nnkname=\"\" defaultvalue=\"话费充值\" type=\"Constant\"/>" +
                "        <mapping name=\"CallBackUrl\" nnkname=\"\" defaultvalue=\"http://localhost:8099/xmlCreate\" type=\"Constant\"/>" +
                "        <mapping name=\"SubTime\" nnkname=\"\" defaultvalue=\"\" type=\"Date\"/>" +
                "        <mapping name=\"Hmac\" nnkname=\"encrykey \" defaultvalue=\"\" type=\"Sign\"/>" +
                "    </mappings>" +
                "</xsdl>";
        XmlModel xmlModel = XmlUtil.parseXml(XmlModel.class,str);

    }
}
