package com.nnk.template.util;

import com.nnk.template.Appliaction;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.basic.BasicWorkflow;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/5
 * Time: 14:28
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SchedureThread implements Runnable {
    public static final Logger log = Logger.getLogger(SchedureThread.class);
    private BasicWorkflow basicWorkflow;
    private String workflowName;
    private Properties properties;
    public SchedureThread(BasicWorkflow basicWorkflow, String workflowName,Properties properties) {
        this.basicWorkflow = basicWorkflow;
        this.workflowName = workflowName;
        this.properties = properties;
    }

    @Override
    public void run() {
        try {
            Long id = basicWorkflow.initialize(workflowName, 1, Collections.EMPTY_MAP);
            Appliaction.WORKFLOWINMAP.put(workflowName, id);
            int[] availableActions = basicWorkflow.getAvailableActions(id, null);
            System.out.println("availableAction:" + Arrays.toString(availableActions));
            for (int i:availableActions) {
                basicWorkflow.doAction(id, i, null);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
        }
    }
}
