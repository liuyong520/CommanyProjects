package com.nnk.template.handler;

import com.alibaba.fastjson.JSON;
import com.nnk.template.Appliaction;
import com.nnk.template.entity.SMSCodeIndex;
import com.nnk.template.entity.SMSCodeRequest;
import com.nnk.template.util.Base64Util;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Workflow;
import net.sf.cglib.core.ReflectUtils;
import nnk.msgsrv.server.Request;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.log4j.Logger;
import sun.reflect.misc.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/17
 * Time: 15:05
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//短信接收消息类
public class SMSMsgHandler {

    private Logger log = Logger.getLogger(SMSMsgHandler.class);
    public void recieve(Request request){
        System.out.println(request.getContent());
        String[] contents = request.getContent().split(" +");
        String json = Base64Util.decode(contents[1]);
        log.debug("json:" + json);
        SMSCodeRequest message = JSON.parseObject(json, SMSCodeRequest.class);
        request.response("1");
        if(Appliaction.SMSCODECACHE.containsKey(message.getMyPhone())){
            Appliaction.SMSCODECACHE.get(message.getMyPhone()).add(message);
        }else{
            List<SMSCodeRequest> list = new ArrayList<SMSCodeRequest>();
            list.add(message);
            Appliaction.SMSCODECACHE.put(message.getMyPhone(), list);
        }
        log.info("["+message.getMyPhone()+"]new Recieved SMSMSG>>>"+message.getFullShortMsg()+"Store in stackMap ");
    }
}
