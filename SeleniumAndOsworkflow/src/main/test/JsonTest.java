import com.alibaba.fastjson.JSONObject;
import com.nnk.template.util.Base64Util;
import com.nnk.template.util.DateUtils;
import com.nnk.utils.http.utils.StringUtil;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/20
 * Time: 17:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

public class JsonTest {
    private String basestr;
    @Test
    public void testCreatJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merId","wf_2-90");
        jsonObject.put("startTime","1000");
        jsonObject.put("jobName","2-90");
        jsonObject.put("repeat","1");
        jsonObject.put("repeatDelay","168000888");
        jsonObject.put("endTime","3000");
        jsonObject.put("taskId","dsafdajei23213213213");
        String json = jsonObject.toJSONString();
        String base = Base64Util.encode(json);
        basestr = base;
        System.out.println(base);
        Date deadline = DateUtils.dateStringToDate("2016-10-1","yyyy-MM-dd");
        Date now = DateUtils.dateStringToDate(DateUtils.getNowTime("yyyy-MM-dd"),"yyyy-MM-dd");
        System.out.println(now);
//        Assert.assertTrue(deadline.after(new Date()));
//        Assert.assertTrue(deadline.before(now));
        Assert.assertTrue(deadline.getTime()>=now.getTime());
    }

    @Test
    public void testSendTaskExecuteMsg() throws Exception {
        testCreatJson();
        MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-CodeTran.xml");
        connector.send("SeleniumApp taskExecute " + basestr);
    }

    @Test
    public void testSendTaskExecuteMsg1() throws Exception {
        testCreatJson();
        MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-CodeTran.xml");
        connector.send("SeleniumApp downLoad " + "232332323 wf_2-90 20160924000000 20160925000000 2-90");
    }
}
