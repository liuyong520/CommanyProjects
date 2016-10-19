package com.nnk.template.function;

import com.nnk.template.win.WindowsOperators;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/30
 * Time: 11:39
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//windows 的窗口或者是文本框设值
public class MockWindowsSetInput extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(NavicationFunction.class);
    @Override
    public void executeCatched(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        //浏览器的窗口
        String browserClassName = (String) args.get("browserClassName");
        //windows窗口
        String windowsClassName = (String) args.get("windowsClassName");
        //等待时间
        String waitTime = (String) args.get("waitTime");
        //输入的key
        String keys = (String) args.get(Contstant.KEYS);
        //widow窗口的名字
        String windowsTiltle = (String)args.get("windowsTiltle");
        //[18,83]|[18,65]|[13]
        if(StringUtils.isEmpty(windowsClassName)) throw new WorkflowException("windowsClassName is not set");
        if(StringUtils.isEmpty(browserClassName)) throw new WorkflowException("browserClassName is not set");
        if(StringUtils.isEmpty(waitTime)) throw new WorkflowException("waitTime is not set");
        if(StringUtils.isEmpty(windowsTiltle)) throw new WorkflowException("windowsTiltle is not set");
        if(StringUtils.isEmpty(keys)) throw new WorkflowException("keys is not set");
        log.info("windowsClassName:"+windowsClassName);

        int time =StringUtils.isEmpty(waitTime)?5000:Integer.parseInt(waitTime);
        try {
            Thread.sleep(time);
            WindowsOperators.setInput(browserClassName,windowsTiltle,windowsClassName,keys);
            propertySet.setString("fileName",keys);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
