package com.nnk.template.condition;

import com.nnk.template.exeception.CommonExceptionHandler;
import com.nnk.template.exeception.Exceptionhandler;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/22
 * Time: 9:31
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConditionAdaptor implements Condition {
    private Exceptionhandler exceptionhandler;

    public Exceptionhandler getExceptionhandler() {
        return exceptionhandler;
    }

    public void setExceptionhandler(Exceptionhandler exceptionhandler) {
        this.exceptionhandler = exceptionhandler;
    }

    @Override
    public boolean passesCondition(Map map, Map map2, PropertySet propertySet) throws WorkflowException {
        try {
            return passesConditionCathch(map,map2,propertySet);
        }catch (Exception e){
            if(exceptionhandler==null){
                setExceptionhandler(new CommonExceptionHandler());
            }
            exceptionhandler.HandlerException(map,map2,propertySet,e);
        }
        return false;
    }
    abstract public boolean passesConditionCathch(Map map, Map map2, PropertySet propertySet) throws WorkflowException;
}
