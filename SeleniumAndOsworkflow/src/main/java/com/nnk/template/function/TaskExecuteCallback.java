package com.nnk.template.function;

import com.alibaba.fastjson.JSONObject;
import com.nnk.template.util.Base64Util;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.MsgSrvLongConnector;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/22
 * Time: 14:19
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//任务执行完成后回调给调度服务程序
public class TaskExecuteCallback extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(TaskExecuteCallback.class);
    @Override
    public void executeCatched(Map transvalues, Map args, PropertySet propertySet) throws WorkflowException {
        MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-CodeTran.xml");
        String appname = (String) args.get("appName");
        String appCmd = (String) args.get("appCmd");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","1");
        jsonObject.put("desc","success");
        String json = jsonObject.toJSONString();
        connector.send(appname+" " + appCmd +" " + Base64Util.encode(json));
    }
}
