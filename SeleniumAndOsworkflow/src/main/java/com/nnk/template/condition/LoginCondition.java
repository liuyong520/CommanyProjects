package com.nnk.template.condition;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/16
 * Time: 12:39
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//判断登录成功的条件
public class LoginCondition extends ConditionAdaptor{
    public static final Logger log = Logger.getLogger(LoginCondition.class);
    @Override
    public boolean passesConditionCathch(Map map, Map map2, PropertySet propertySet) throws WorkflowException {
        String loginResult = propertySet.getString("loginResult");
        if("success".equals(loginResult)){
            log.info("login success");
            return true;
        }else return false;
    }
}
