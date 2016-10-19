package com.nnk.template.util;

import com.nnk.template.Appliaction;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.basic.BasicWorkflow;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/20
 * Time: 11:55
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SimpleQuartzJob implements Job {
    public static final Logger log = Logger.getLogger(SimpleQuartzJob.class);
    public SimpleQuartzJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        BasicWorkflow basicWorkflow = (BasicWorkflow) dataMap.get("basicWorkflow");
        String workflowName = dataMap.getString("workflowName");
        String taskId = dataMap.getString("taskId");
        String startTime = dataMap.getString("startTime");
        String endTime = dataMap.getString("endTime");
        Request request = (Request) dataMap.get("request");
        try {
            Long id = basicWorkflow.initialize(workflowName, 1, Collections.EMPTY_MAP);
            PropertySet propertySet = basicWorkflow.getPropertySet(id);
            propertySet.setString("startTime",startTime);
            propertySet.setString("endTime",endTime);
            propertySet.setObject("request", request);
            propertySet.setString("taskId",taskId);
            synchronized (Appliaction.WORKfLOWSTARTDATEMAP) {
                Appliaction.WORKfLOWSTARTDATEMAP.put(workflowName, new Date());
                Appliaction.WORKFLOWINMAP.put(workflowName, id);
            }

            int[] availableActions = basicWorkflow.getAvailableActions(id, Collections.EMPTY_MAP);
            log.info("availableAction:" + Arrays.toString(availableActions));
            for (int i:availableActions) {
                basicWorkflow.doAction(id, i, Collections.EMPTY_MAP);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
        }
    }
}
