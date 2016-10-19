package com.nnk.redis;

import com.nnk.upstream.util.MapUtils;
import com.nnk.upstream.util.XmlMapPharser;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/27
 * Time: 17:13
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MapPharserUtils {
    private XmlMapPharser pharser;
    @Test
    public void testPharseXml() throws Exception {

        String xml = "<root><student><name>BurceLiu</name><age>18</age></student></root>";
        Map<String, Object> result = pharser.parse(xml);
        System.out.println(result);
        xml = "<root><student><name>BurceLiu</name><age>18</age></student><student><name>BurceLi</name><age>28</age></student></root>";
        result = pharser.parse(xml);
        System.out.println(result);
        xml = "<root><str>str1</str><str>str2</str><str>str3</str></root>";
        result = pharser.parse(xml);
        System.out.println(result);
        xml = "<root><students><student><name>BurceLiu</name><age>18</age></student><student><name>BurceLi</name><age>28</age></student></students></root>";
        result = pharser.parse(xml);
        System.out.println(result.get("students"));
        System.out.println(MapUtils.getObject(result,"students.student"));


        xml = "<root><name>BurceLiu</name><age>18</age></root>";
        result = pharser.parse(xml);
        System.out.println(result);
        xml = "<?xml version=\'1.0\' encoding=\'UTF-8\'?>" +
                "<PayPlatRequestParameter>" +
                "<CTRL-INFO WEBSVRNAME=\'充值卡预订\' WEBSVRCODE=\'B00002\' APPFROM=\'0990000010\' SERNUM=\'987654321\' APPDATE=\'20111203\'/>" +
                "<PARAMETERS>" +
                "<BILLORGID>9999009999019001</BILLORGID>" +
                "<ORDERPRICESUM>990</ORDERPRICESUM>" +
                "<ORDERREALPRICESUM>990</ORDERREALPRICESUM>" +
                "<ORDERS>" +
                "<CARDSEQ>3</CARDSEQ>" +
                "<CARDPRICESEQ>310001</CARDPRICESEQ>" +
                "<ORDERNUM>1</ORDERNUM>" +
                "<CARDAMT>990</CARDAMT>" +
                "<ORDERPRICE>990</ORDERPRICE>" +
                "<RANDOMRATE>1.0</RANDOMRATE>" +
                "<TEXT1></TEXT1>" +
                "<TEXT2></TEXT2>" +
                "<TEXT3></TEXT3>" +
                "<TEXT4></TEXT4>" +
                "<TEXT5></TEXT5>" +
                "</ORDERS>" +
                "<TEXT1></TEXT1>" +
                "<TEXT2></TEXT2>" +
                "<TEXT3></TEXT3>" +
                "<TEXT4></TEXT4>" +
                "<TEXT5></TEXT5>" +
                "</PARAMETERS>" +
                "<MAC>669e15fbc5341d4a021d0d725bddc234</MAC>" +
                "</PayPlatRequestParameter>";
        result = pharser.parse(xml);
        System.out.println(result);
        System.out.println(MapUtils.getObject(result,"PayPlatRequestParameter.PARAMETERS.ORDERS.RANDOMRATE"));
        System.out.println(MapUtils.getObject(result,"CTRL-INFO.attribute-WEBSVRNAME"));

        xml="<?xml version=\"1.0\" encoding=\"GB2312\"?>" +
                "<response>" +
                "<result>true</result>" +
                "<code>100</code>" +
                "<msg>恭喜，提交成功</msg>" +
                "<pno> TOPUP10000013610</pno>" +
                "<data>" +
                "<sid>110922133648293754</sid>" +
                "<ste>0</ste>" +
                "<cid>yibao1228</cid>" +
                "<pid>0103</pid>" +
                "<oid>TOPUP10000013610</oid>" +
                "<pn>13426200000</pn>" +
                "<fm>30.00</fm>" +
                "<dm>29.00</dm>" +
                "<info1></info1>" +
                "<info2></info2>" +
                "<info3></info3>" +
                "<ft></ft>" +
                "<error></error>" +
                "<msg></msg>" +
                "</data>" +
                "</response>";
        result = pharser.parse(xml);
        System.out.println(result);
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<test>" +
                "<SIGN>1115FA9EA37248E9D286A41EB74FDE0B</SIGN>" +
                "<YOLLYFLOW>2222222222</YOLLYFLOW>" +
                "<YOLLYTIME>20130909113520</YOLLYTIME>" +
                "<DATA>" +
                "<STATUS>1</STATUS>" +
                "<STATE>1</STATE>" +
                "<orderid>201606290001</orderid>" +
                "</DATA>" +
                "</test>";

        result = pharser.parse(xml);
        System.out.println(result);
        System.out.println(MapUtils.getObject(result,"item.attribute-name"));
    }
    @Test
    public void testPharseXml1() throws Exception {
        String xml = "<root><student version=\"1.0\" name=\"liuy\"><name agent=\"ddddd\">BurceLiu</name><age>18</age></student></root>";
        Map<String, Object> result = pharser.parse(xml);
        System.out.println(result);
    }
    @Before
    public void setUp() throws Exception {
        pharser = new XmlMapPharser();
    }
}
