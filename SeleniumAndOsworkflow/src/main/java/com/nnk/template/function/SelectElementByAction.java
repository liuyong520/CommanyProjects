package com.nnk.template.function;

import com.alibaba.fastjson.JSONObject;
import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/24
 * Time: 19:01
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//根据鼠标模拟点击操作选择多选设值
public class SelectElementByAction extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(LoginFunction.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        Object object = transientVars.get(Contstant.PARAMENTS);
        if(object==null)throw  new WorkflowException(Contstant.PARAMENTS +" is not set");
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if(webDriver == null) throw  new WorkflowException("webDriver is not init");
        String method = (String) args.get(Contstant.METHODNAME);
        if(StringUtils.isEmpty(method)) throw new WorkflowException("methodName is not set");
        String dataMaps = (String)args.get("dataMaps");
        Map map = JSONObject.parseObject(dataMaps,Map.class);
        String parentElementName = (String)args.get("parentElementName");
        try {
            String value = (String) MethodUtils.invokeExactMethod(object, method,null);
            String realvalue = (String) map.get(value);
            WebElement parentElement = SeleniumUtils.findElement(webDriver,parentElementName);
            SeleniumUtils.ActionMoveToElementAndClick(webDriver,parentElement);
            WebElement element = SeleniumUtils.findElement(webDriver,realvalue);
            SeleniumUtils.ActionMoveToElementAndClick(webDriver,element);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
