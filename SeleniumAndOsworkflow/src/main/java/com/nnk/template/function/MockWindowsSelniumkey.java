package com.nnk.template.function;

import com.nnk.template.win.WindowsOperators;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import com.sun.jna.platform.win32.User32;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/26
 * Time: 10:40
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//Ä£Äâwindows ¿ì½Ý¼ü ¼üÅÌ²Ù×÷£º
public class MockWindowsSelniumkey extends FunctionProviderAdaptor{

    public static final Logger log = Logger.getLogger(MockWindowsSelniumkey.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String browserClassName = (String) args.get("browserClassName");
        String windowsClassName = (String) args.get("windowsClassName");
        String waitTime = (String) args.get("waitTime");
        String regex = (String) args.get("fileRegex");
        String windowsTiltle = (String)args.get("windowsTiltle");
        //[18,83]|[18,65]|[13]
        String keybination = (String)args.get("keyCombination");
        if(StringUtils.isEmpty(windowsClassName)) throw new WorkflowException("windowsClassName is not set");
        if(StringUtils.isEmpty(browserClassName)) throw new WorkflowException("browserClassName is not set");
        if(StringUtils.isEmpty(waitTime)) throw new WorkflowException("waitTime is not set");
        if(StringUtils.isEmpty(windowsTiltle)) throw new WorkflowException("windowsTiltle is not set");
        if(StringUtils.isEmpty(regex)) throw new WorkflowException("regex is not set");
        log.info("windowsClassName:"+windowsClassName);

        int time =StringUtils.isEmpty(waitTime)?5000:Integer.parseInt(waitTime);

        keybination = keybination.replace("[","").replace("]","");

        String[] array = keybination.split("\\|");
        int[][] keybinationArray = new int[array.length][2];
        for(int i = 0;i<array.length;i++){
            String content = array[i];
            String[] pair = content.split(",");
            if(pair.length>=2) {
                keybinationArray[i][0] = Integer.parseInt(pair[0]);
                keybinationArray[i][1] = Integer.parseInt(pair[1]);
            }else {
                keybinationArray[i][0] = Integer.parseInt(pair[0]);
            }
        }
        try {
            Thread.sleep(time);
            String windowsTitleName = WindowsOperators.getWindowsTitleName(browserClassName, windowsTiltle, windowsClassName);
            log.info("²éÕÒ windowsTitleName:" + windowsTitleName);
            String filename = null;
            if(StringUtil.isEmpty(windowsTitleName)){
                filename = windowsTiltle;
            }
            Pattern pattern =Pattern.compile(regex);
            Matcher matcher = pattern.matcher(windowsTitleName);
            if(matcher.find()&&matcher.groupCount()>=1){
                filename = matcher.group(1);
                log.info("filename:" + filename);
            }
            propertySet.setString("fileName",filename);
            transientVars.put("fileName",filename);
            System.out.println(transientVars);
            Thread.sleep(3000);
            WindowsOperators.keyDown(browserClassName,windowsTiltle,windowsClassName,keybinationArray);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
