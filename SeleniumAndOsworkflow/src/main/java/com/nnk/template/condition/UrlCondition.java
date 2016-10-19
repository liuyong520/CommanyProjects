package com.nnk.template.condition;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflowContext;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.loader.WorkflowFactory;
import com.opensymphony.workflow.query.WorkflowQuery;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.opensymphony.workflow.spi.hibernate.WorkflowName;
import com.opensymphony.workflow.spi.memory.MemoryWorkflowStore;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/16
 * Time: 14:18
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//判断URL是否一致的条件
public class UrlCondition extends ConditionAdaptor {
    @Override
    public boolean passesConditionCathch(Map transvarMap, Map args, PropertySet propertySet) throws WorkflowException {
        String url = (String) args.get("url");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        if(url.equals(webDriver.getCurrentUrl())){
            return true;
        }else {
            return  false;
        }
    }
}
