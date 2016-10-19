package com.nnk.redis;

import com.nnk.upstream.util.DateUtil;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/7
 * Time: 9:16
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MsgSenderTest {
    @Test
    public void testRecharge() throws Exception {
        MsgSrvShortConnector srvShortConnector = new MsgSrvShortConnector("config/msgsrv-short.xml");
        for (int i = 0; i < 1; i++) {
            String date= DateUtil.format(new java.util.Date());
            srvShortConnector.send("CommonUpStream SlowInt 0 5454544654654523 2000000210 200000000000013 2010052016304839 1 1000 105 广东 22 NA NA 13750956294 " +date+ " NA NA");
        }

    }

    @Test
    public void testQuery() throws Exception {
        MsgSrvShortConnector srvShortConnector = new MsgSrvShortConnector("config/msgsrv-short.xml");
        srvShortConnector.send("CommonUpStream SlowInt 0 5454544654654523 2000000210 200000000000013 2010052016304839 1 100 105 广东 11 NA NA 13750956294 20160607092005 NA NA");

    }

    @Test
    public void testBalance() throws Exception {
        MsgSrvShortConnector srvShortConnector = new MsgSrvShortConnector("config/msgsrv-short.xml");
        srvShortConnector.send("CommonUpStream GetAccount 20130711 2000000210 20130711112522");

    }

    @Test
    public void testUnrecharge() throws Exception {
        MsgSrvShortConnector srvShortConnector = new MsgSrvShortConnector("config/msgsrv-short.xml");
        srvShortConnector.send("CommonUpStream UnSlowInt 0 UN5454544654654544 2000000008 200000000000013 2010052016304839 1 100 105 广东 22 NA NA 13267191379 20160607092005 NA NA 5454544654654544");

    }
}
