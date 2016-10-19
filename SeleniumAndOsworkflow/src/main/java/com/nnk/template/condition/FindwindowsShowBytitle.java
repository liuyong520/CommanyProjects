package com.nnk.template.condition;

import com.nnk.template.win.WindowsOperators;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/8
 * Time: 11:57
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//²éÕÒwindow´°¿Ú
public class FindwindowsShowBytitle extends ConditionAdaptor{
    public static final Logger log = Logger.getLogger(FindwindowsShowBytitle.class);
    @Override
    public boolean passesConditionCathch(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        String windowsTiltle = (String) args.get("windowsTiltle");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        if(webDriver==null){
            log.error("browse is closed");
            return false;
        }
        String browserClassName = (String) args.get("browserClassName");
        if (StringUtils.isEmpty(browserClassName))throw new WorkflowException("browserClassName is not set");
        String windowsClassName = (String) args.get("windowsClassName");
        if (StringUtils.isEmpty(windowsClassName))throw new WorkflowException("windowsClassName is not set");
        String windowsTitleName = WindowsOperators.getWindowsTitleName(browserClassName, windowsTiltle, windowsClassName);
        if(StringUtils.isNotEmpty(windowsTitleName)){
            return true;
        }else return false;
    }
}
