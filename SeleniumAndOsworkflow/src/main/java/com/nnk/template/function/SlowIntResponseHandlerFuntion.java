package com.nnk.template.function;

import com.nnk.template.entity.SlowIntRequest;
import com.nnk.template.service.MsgSrvService;
import com.nnk.template.util.DateUtils;
import com.nnk.template.util.MsgSrvResponseUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.MsgSrvLongConnector;
import nnk.msgsrv.server.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/23
 * Time: 10:50
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SlowIntResponseHandlerFuntion extends FunctionProviderAdaptor{
    Logger logger = Logger.getLogger(SlowIntResponseHandlerFuntion.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String resultkey = (String) args.get("result");

        logger.info("result:" + resultkey);
        String SuccessCode = (String) args.get("SuccessCode");
        String FailCode = (String) args.get("FailCode");
        Object object = transientVars.get("paraments");
        if(StringUtils.isEmpty(resultkey)) throw  new WorkflowException("result is not set");
        if(StringUtils.isEmpty(SuccessCode)) throw  new WorkflowException("SuccessCode is not set");
        if(StringUtils.isEmpty(FailCode)) throw  new WorkflowException("FailCode is not set");
        if(object == null) throw  new WorkflowException("paraments is not set in transientVars");
        if(object instanceof SlowIntRequest) {
            SlowIntRequest request = (SlowIntRequest) object;
            if (transientVars.containsKey(resultkey)) {
                String result = (String) transientVars.get(resultkey);
                String msgSrvName = (String) propertySet.getString("MsgSrvName");
                if (StringUtils.isEmpty(msgSrvName)) throw new WorkflowException("MsgSrvName is not init");
                MsgSrvLongConnector connector = MsgSrvService.get(msgSrvName);
                if(connector==null){
                    logger.info("MsgSrvName is error! the connector is null");
                    throw new WorkflowException("MsgSrvName is error! the connector is null");
                }
                if (SuccessCode.contains(result)) {
                    MsgSrvResponseUtils responseUtils = new MsgSrvResponseUtils(connector);
                    responseUtils.responseBrokeSuccess(request.getRequest(), "NA");
                    responseUtils.responseCallbackSuccess(request.getRequest(), "NA", "NA", "NA", DateUtils.getNowTime("yyyyMMddHHmmss"));
                }else if(FailCode.contains(result)){
                    MsgSrvResponseUtils responseUtils = new MsgSrvResponseUtils(connector);
                    responseUtils.responseBrokeFail(request.getRequest());
                    responseUtils.responseCallbackFail(request.getRequest(),"NA","NA","NA", DateUtils.getNowTime("yyyyMMddHHmmss"));
                }
            } else {
                logger.info("result is not passed in last step");
            }
        }else{
            logger.info("object is not Request");
        }
    }
}
