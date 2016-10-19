package com.nnk.upstream.handler;

import com.nnk.upstream.anotation.MethodAttribute;
import com.nnk.upstream.core.ConfigContextManager;
import nnk.msgsrv.server.Request;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/25
 * Time: 18:36
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@MethodAttribute(methodName = "update")
public class ConfigUpdateHandler {
    @Autowired
    private ConfigContextManager configContextManager;

    public synchronized void update(Request request) {
        try {
            request.response(request.getContent());
            configContextManager.updateConfigContext(request.getContent());
            configContextManager.updateRuleContext(request.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
