package com.nnk.template.function;

import com.alibaba.fastjson.JSONObject;
import com.nnk.interfacetemplate.common.DateUtil;
import com.nnk.template.util.Base64Util;
import com.nnk.template.util.DateUtils;
import com.nnk.utils.encry.MD5Util;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.Request;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/30
 * Time: 8:45
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MsgsrvResponseFunction extends FunctionProviderAdaptor {
    @Override
    public void executeCatched(Map transvalues, Map args, PropertySet propertySet) throws WorkflowException {
        String status = (String) args.get("status");
        String desc = (String) args.get("desc");
        Request request = (Request) propertySet.getObject("request");
        String fileName = propertySet.getString("fileName");
        String taskId = propertySet.getString("taskId");
        String filePath = propertySet.getString("remotePath");
        String startDate = propertySet.getString("startTime");
        String endDate = propertySet.getString("endTime");
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(fileName)){
            jsonObject.put("fileName","NA");
        }else {
            jsonObject.put("fileName",fileName);
        }

        jsonObject.put("md5sum", MD5Util.getMD5String(UUID.randomUUID().toString()));
        jsonObject.put("taskId",taskId);
        if(StringUtils.isEmpty(status)){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status",status);

        }
        if(StringUtils.isEmpty(desc)){
            jsonObject.put("desc","³É¹¦");
        }else {
            jsonObject.put("desc",desc);

        }
        if(StringUtils.isEmpty(filePath)){
            jsonObject.put("filePath","NA");
        }else {
            jsonObject.put("filePath",filePath);
        }
        jsonObject.put("finishTime", DateUtils.getNowTime("yyyyMMddHHmmss"));
        jsonObject.put("finishDate", DateUtils.getNowTime("yyyyMMdd"));
        jsonObject.put("billDate",startDate);
        jsonObject.put("billEnd",endDate);
        String json = jsonObject.toJSONString();
        String jsonstr = Base64Util.encode(json);
        request.response(jsonstr);
    }
}
