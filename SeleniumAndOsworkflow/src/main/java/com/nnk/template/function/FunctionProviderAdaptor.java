package com.nnk.template.function;

import com.nnk.template.Appliaction;
import com.nnk.template.exeception.CommonExceptionHandler;
import com.nnk.template.exeception.Exceptionhandler;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.basic.BasicWorkflowContext;
import com.opensymphony.workflow.config.Configuration;
import com.opensymphony.workflow.config.DefaultConfiguration;
import com.opensymphony.workflow.spi.WorkflowEntry;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/5
 * Time: 10:11
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public abstract class FunctionProviderAdaptor implements FunctionProvider {
    private Exceptionhandler exceptionhandler;

    public Exceptionhandler getExceptionhandler() {
        return exceptionhandler;
    }

    public void setExceptionhandler(Exceptionhandler exceptionhandler) {
        this.exceptionhandler = exceptionhandler;
    }

    public static final Logger log = Logger.getLogger(FunctionProviderAdaptor.class);
    public void execute(Map transvalues, Map args, PropertySet propertySet) throws WorkflowException {
        try{
            executeCatched(transvalues, args, propertySet);
        }catch (Exception e){
            if(exceptionhandler==null){
                setExceptionhandler(new CommonExceptionHandler());
            }
            exceptionhandler.HandlerException(transvalues,args,propertySet,e);
        }
    }
    public abstract void executeCatched(Map transvalues, Map args, PropertySet propertySet)throws WorkflowException;
}
