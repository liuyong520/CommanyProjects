package com.nnk.template.handler;

import com.alibaba.fastjson.JSONObject;
import com.nnk.template.Appliaction;
import com.nnk.template.util.Base64Util;
import com.nnk.template.util.SimpleQuarzManager;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/20
 * Time: 9:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//���ض��˵�����
public class TaskExecuterSchedureHandler {
    public static final Logger log = Logger.getLogger(TaskExecuterSchedureHandler.class);
    public void execute(Request request) throws WorkflowException {
        String content = request.getContent();
        String jsonstr = Base64Util.decode(content);
        Map map = JSONObject.parseObject(jsonstr,Map.class);
        //����id��
        String taskId = (String) map.get("taskId");
        //������
        String merid = (String) map.get("merId");
        //��һ��ִ�е�ʱ��
        String starttime = (String) map.get("startTime");
        //��������
        String jobName = (String) map.get("jobName");
        //�ظ�����
        String repeat = (String) map.get("repeat");
        int repeattime =0;
        if(StringUtil.isNotEmpty(repeat)){
            repeattime = Integer.parseInt(repeat);
        }
        //�ظ����
        String repeatDelay = (String) map.get("repeatDelay");
        long interval =0;
        if(StringUtil.isNotEmpty(repeatDelay)){
            interval = Long.parseLong(repeatDelay);
        }
        //
        String endTime = (String) map.get("endTime");
        BasicWorkflow basicWorkflow = Appliaction.getBasicWorkflowInstance();

        String workflowName = Appliaction.MeridMap.get(merid);
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
        //����ɹ�
        response(request,"1","success",taskId);
        log.info("TaskId JobName"+ jobName + "start!");
        SimpleQuarzManager.removeJob(jobName);
        SimpleQuarzManager.addJob(jobName,jobClass,starttime,endTime,repeattime,interval,dataMap);

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
