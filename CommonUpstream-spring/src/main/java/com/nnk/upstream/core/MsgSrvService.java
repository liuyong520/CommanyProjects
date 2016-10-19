package com.nnk.upstream.core;

import nnk.msgsrv.server.MsgSrvLongConnector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/8
 * Time: 14:33
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MsgSrvService {
    private static Map<String,MsgSrvLongConnector> MSGCONTEXTMAP = new HashMap<String, MsgSrvLongConnector>();

    private static Map<String, MsgSrvLongConnector> getMSGCONTEXTMAP() {
        return MSGCONTEXTMAP;
    }
    public static MsgSrvLongConnector put(String key, MsgSrvLongConnector value) {
        return getMSGCONTEXTMAP().put(key, value);
    }

    public static MsgSrvLongConnector get(Object key) {
        return getMSGCONTEXTMAP().get(key);
    }
}
