package com.nnk.template.function;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.MsgSrvLongConnector;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/23
 * Time: 14:20
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class InitMsgSrvFunction extends FunctionProviderAdaptor{

    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String path = (String) args.get("path");
        if(StringUtils.isEmpty(path))throw new WorkflowException("msgsrv path is not set");
        MsgSrvLongConnector connector = new MsgSrvLongConnector(path);
        connector.start();
    }
}
