/*
 * Copyright 1999-29 Nov 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.osworkflow;

import com.opensymphony.workflow.*;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.WorkflowDescriptor;

import java.util.HashMap;
import java.util.List;

/**
 * @author liuy 29 Nov 2015 4:17:44 pm
 */
public class OsWorkflow {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws InvalidActionException, InvalidRoleException, InvalidInputException, InvalidEntryStateException, WorkflowException {
        String caller = "testUser";
        String params1 = "params1";
        String docTitle = "docTitle";
        long workflowId = 1;
        HashMap inputs = new HashMap();

        Workflow workflow = new BasicWorkflow(caller);
        inputs.put("params1", params1);
        inputs.put("docTitle", docTitle);
        workflowId = workflow.initialize("mytest", 100, inputs);
        
        //执行第1步动作
        workflow.doAction(workflowId, 1, inputs);
        WorkflowDescriptor wd = workflow.getWorkflowDescriptor(workflow.getWorkflowName(1));

        System.out.println(wd);
        List list= workflow.getCurrentSteps(1);
        System.out.println(list);
        System.out.println("finished");
    }
}
