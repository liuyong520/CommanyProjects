package com.nnk.template.register;

import com.nnk.template.Appliaction;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Register;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.WorkflowEntry;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/17
 * Time: 16:17
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationRegister implements Register{
    @Override
    public Object registerVariable(WorkflowContext workflowContext, WorkflowEntry entry, Map args, PropertySet propertySet) throws WorkflowException {
        String workflowname = "unknown";
        String configname = "unknown";
        long workflow_id = -1;

        if (entry != null) {
            workflowname = entry.getWorkflowName();
            workflow_id = entry.getId();
        }

        configname = workflowname + "_" + workflow_id;

        ApplicationConfig config = new ApplicationConfig(configname,args);
        return config;
    }
}
