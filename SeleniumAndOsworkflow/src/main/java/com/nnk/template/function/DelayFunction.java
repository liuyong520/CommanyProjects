package com.nnk.template.function;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/30
 * Time: 16:28
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class DelayFunction extends FunctionProviderAdaptor {
    @Override
    public void executeCatched(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        String time = (String) args.get("delay");
        if (StringUtils.isEmpty(time)) throw new WorkflowException("delay is not set");
        try {
            Thread.sleep(Integer.parseInt(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
