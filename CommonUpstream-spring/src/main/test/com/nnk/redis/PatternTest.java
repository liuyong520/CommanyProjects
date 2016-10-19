package com.nnk.redis;

import com.nnk.upstream.util.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/6
 * Time: 15:22
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class PatternTest {
    @Test
    public void testPattern(){
        String resp = "{\"code\":\"606\",\"data\":{\"up_order_no\":\"20160224103952765560633\"},\"message\":\"操作成功\"}\r";
        String regex = ".*code[\\s\\S]*";
        boolean result = Pattern.matches(regex,resp);
        Assert.assertTrue("success",result);

    }
    @Test
    public void testPattern1(){
        String src = "Notify_url?client_order_no=10177916671&up_order_no=20160224112536871853313&product_type=1&phone_no=13384108771&deduction_amount=4900.0000&recharge_status=2&elecardID=0111001602221554784&sign=79e994444e6c3663e76dcf90d74a3312";

        String[] contents = src.split("[=&|~!@#$%^&*]+");
        System.out.println(contents.length);
        for (String content:contents){
            System.out.println(content);
        }
        src = "NAME!123@AGE=25$$GENT~~~TEXT^YYOU*3434";

        contents = src.split("[=&|~!@#$%^&*]+");
        System.out.println(contents.length);
        for (String content:contents){
            System.out.println(content);
        }
    }
    @Test
    public void testPhareStr(){
        String src = "Notify_url?client_order_no=10177916671&up_order_no=20160224112536871853313&product_type=1&phone_no=13384108771&deduction_amount=4900.0000&recharge_status=2&elecardID=0111001602221554784&sign=79e994444e6c3663e76dcf90d74a3312";
        Map map = MapUtils.getResultMap(src, "STR");
        System.out.println(map);
        src = "NAME!123@AGE=25$$GENT~~~TEXT^YYOU*3434";
        map = MapUtils.getResultMap(src, "STR");
        System.out.println(map);
        src = "NAME!123@AGE=25$$GENT~~~TEXT^YYOU*3434&name";
        map = MapUtils.getResultMap(src, "STR");
        System.out.println(map);
        boolean result = src.matches(".*[~!@#$%^&*]+.*");
        Assert.assertTrue("success",result);

    }
    @Test
    public void testPhareJson(){
        String src = "{" +
                "    \"code\": \"2\"," +
                "    \"data\": {" +
                "        \"up_order_no\": \"20160224103952765560633\"" +
                "    }," +
                "    \"message\": \"操作成功\"" +
                "}";
        Map map = MapUtils.getResultMap(src, "JSON");
        System.out.println(map);

    }
}
