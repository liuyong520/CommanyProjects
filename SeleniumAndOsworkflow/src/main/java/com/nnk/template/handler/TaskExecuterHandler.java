package com.nnk.template.handler;

import com.alibaba.fastjson.JSONObject;
import com.nnk.template.Appliaction;
import com.nnk.template.util.Base64Util;
import com.nnk.template.util.DateUtils;
import com.nnk.template.util.SimpleQuarzManager;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import nnk.msgsrv.server.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/26
 * Time: 17:05
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class TaskExecuterHandler {
    public static final Logger log = Logger.getLogger(TaskExecuterHandler.class);
    public void execute(Request request) throws WorkflowException {
        String[] contents = request.getContent().split(" +");
        String taskId = contents[0];
        String merId = contents[1];
        String startTime = contents[2];
        String endTime = contents[3];
        String jobName = contents[4];
        if(StringUtil.isEmpty(jobName)||"NA".equals(jobName)){
            jobName = merId + "_" + taskId;
        }
        if(StringUtil.isEmpty(startTime)||"NA".equals(startTime)){
            startTime = DateUtils.getBeforeDay(2,"yyyyMMdd");
        }
        if(StringUtil.isEmpty(endTime)||"NA".equals(endTime)){
            endTime = DateUtils.getBeforeDay(1,"yyyyMMdd");
        }
        if(StringUtils.isEmpty(merId)) return;
        BasicWorkflow basicWorkflow = Appliaction.getBasicWorkflowInstance();

        String workflowName = Appliaction.MeridMap.get(merId);
        if(workflowName==null){
            log.info("merid is not exsit");
            response(request,"0","Fail",taskId);
            return;
        }
        String jobClass = "com.nnk.template.util.SimpleQuartzJob";
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("basicWorkflow",basicWorkflow);
        dataMap.put("workflowName",workflowName);
        dataMap.put("taskId",taskId);
        dataMap.put("startTime",startTime);
        dataMap.put("endTime",endTime);
        dataMap.put("request",request);
        //受理成功
//        response(request,"1","success",taskId);
        log.info("TaskId JobName"+ jobName + "start!");
        SimpleQuarzManager.removeJob(jobName);
        SimpleQuarzManager.addJob(jobName, jobClass, "0", "0", 0, 1L, dataMap);

    }
    public void response(Request request,String status,String desc,String taskId){
        JSONObject json = new JSONObject();
        json.put("taskId",taskId);
        json.put("status",status);
        json.put("desc",desc);
        String base = Base64Util.encode(json.toJSONString());
        request.response(base);
    }

}
