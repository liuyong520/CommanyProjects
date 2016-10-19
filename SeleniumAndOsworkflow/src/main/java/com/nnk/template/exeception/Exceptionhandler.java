package com.nnk.template.exeception;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.WorkflowException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/22
 * Time: 9:35
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public interface Exceptionhandler {
    /**
     * “Ï≥£¥¶¿Ì
     * @param transvalues
     * @param args
     * @param propertySet
     * @param throwable
     * @throws WorkflowException
     */
    public void HandlerException(Map transvalues, Map args, PropertySet propertySet,Throwable throwable) throws WorkflowException;
}
