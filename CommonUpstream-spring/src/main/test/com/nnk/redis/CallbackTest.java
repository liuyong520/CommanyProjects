package com.nnk.redis;

import com.nnk.interfacetemplate.common.MD5Util;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.parterner.CallbackRequest;
import com.nnk.upstream.util.PostFormUtils;
import com.nnk.upstream.util.ReflectUtils;
import com.nnk.upstream.util.SignSecurityUtlis;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.utils.http.exception.NetWorkException;
import com.nnk.utils.http.interfaces.HttpData;
import com.nnk.utils.http.utils.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/25
 * Time: 9:49
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:context/bean.xml" })
public class CallbackTest {
    private Logger logger = Logger.getLogger(CallbackTest.class);
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private ConfigContextManager configContextManager;
    @Test
    public void testCallbackSuccess() throws NetWorkException {

        CallbackRequest request = new CallbackRequest();
        request.setOrderStatus("SUCCESS");
        request.setRespCode("0000");
        request.setRealValue("100");
        request.setOemorderId("oemH89898709909");
        request.setSendorderId("29038297439284320");
        request.setMerchantNo("2000000011");
        InterfaceConfig config = configContextManager.searchConfigContext("20000009");
        request.setRespMsg("成功");
        String signStr = ReflectUtils.getkeyValueString(request,"=","&","sign");
        String sign = SignSecurityUtlis.sign(signStr,"123456");
        request.setSign(sign);
        String Resp = httpClientUtils.doPost("http://localhost:8099/Upstream/coustomizeCallback?merid=200000009",request);

    }
    @Test
    public void testCallbackSuccess1() throws NetWorkException {


        String Resp = httpClientUtils.doPost("http://localhost:8099/Upstream/coustomizeCallback?merid=2000000002",new HttpData() {
            @Override
            public HttpEntity getPostdata() {
                return new StringEntity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<SIGN>1115FA9EA37248E9D286A41EB74FDE0B</SIGN>" +
                        "<YOLLYFLOW>2222222222</YOLLYFLOW>" +
                        "<YOLLYTIME>20130909113520</YOLLYTIME>" +
                        "<DATA>" +
                        "<STATUS>1</STATUS>" +
                        "<STATE>1</STATE>" +
                        "<007orderid>201606290001</007orderid>" +
                        "</DATA>", Charset.defaultCharset());
            }

            @Override
            public String getGetdata() {
                return null;
            }
        });

    }

    @Test
    public void testCallbackSuccess2() throws NetWorkException {


        String Resp = httpClientUtils.doPost("http://112.95.172.89:11001/Interface/Upstream/Oufei.php",new HttpData() {
            @Override
            public HttpEntity getPostdata() {
                HashMap parametersMap = new HashMap();

                parametersMap.put("OrderNO","23254343554354");
                parametersMap.put("Result","99");
                parametersMap.put("Ret_URL","http://112.95.172.89:19801/Upstream/coustomizeCallback?merid=2000000210");
//                parametersMap.put("AgentID","32432432");
//                parametersMap.put("Telephone","13267191379");
//                parametersMap.put("TelephoneType","2");
//                parametersMap.put("Money","50");
//                parametersMap.put("ChargeMode","2");
//                parametersMap.put("RequestID","123234444322");
//                parametersMap.put("ExtendInfo","话费充值");
//                parametersMap.put("InTime","20160612112700");
//                parametersMap.put("OrderID","oemdsdsd212323");
//                parametersMap.put("Result","SUCCESS");
//                parametersMap.put("Hmac", MD5Util.getMD5String("AgentID=32432432&Telephone=13267191379&TelephoneType=2&Money=50&ChargeMode=2&RequestID=123234444322&ExtendInfo=话费充值&OrderID=oemdsdsd212323&Result=SUCCESS&InTime=20160612112700&Key=cDQwf33RZzADOuDzjUgZvcYLccGPsh9eKuQV8co0lcQ0tYqaxsihh6CsbZl2RRr5").toUpperCase());
                parametersMap.put("sign", MD5Util.getMD5String("23254343554354&E1D4F7B1655A4CB41011E44B61F36F5E").toUpperCase());
                return PostFormUtils.buildEntity(parametersMap,false,"utf-8");
            }

            @Override
            public String getGetdata() {
                return null;
            }
        });
        System.out.println("response:"+Resp);
    }
}
