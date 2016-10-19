package com.nnk.template.util;

import com.nnk.template.Appliaction;
import com.nnk.template.function.Contstant;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.module.propertyset.PropertySetCloner;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.SimpleStep;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/27
 * Time: 8:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class TaskTimeOutThread implements Runnable {
    public static final Logger log = Logger.getLogger(TaskTimeOutThread.class);
    public static Map<String,Integer> TimesMap = new ConcurrentHashMap<String,Integer>();
    public static ExecutorService threadPool = Executors.newCachedThreadPool();
    @Override
    public void run() {
        while(true){
            log.info("size:" + Appliaction.WORKfLOWSTARTDATEMAP.size());
            synchronized (Appliaction.WORKfLOWSTARTDATEMAP) {
                for (Map.Entry<String, Date> entry : Appliaction.WORKfLOWSTARTDATEMAP.entrySet()) {
                    String workflowName = entry.getKey();
                    Date Date = entry.getValue();
                     if (DateUtils.isTimeout(Date,180)) {
                        log.info("workflowName:" + workflowName + "timeOut");
                        BasicWorkflow workflow = Appliaction.getBasicWorkflowInstance();
                        //取出保存好的workId
                        Long workId = Appliaction.WORKFLOWINMAP.get(workflowName);
                        log.info("workId:"+workId);
                        WorkflowDescriptor descriptor = workflow.getWorkflowDescriptor(workflowName);
                        int[] availableActions = workflow.getAvailableActions(workId, Collections.EMPTY_MAP);
                        if(ArrayUtils.isEmpty(availableActions)) {//没有即为已经处理完了
                            Appliaction.WORKfLOWSTARTDATEMAP.remove(entry.getKey());
                            continue;
                        }
                        log.info("availableAction:" + Arrays.toString(availableActions));
                        boolean quit = false;
                        boolean retry = false;
                        Future<Boolean> future = null;
                        for (int i : availableActions) {
                            try {
                                //获取action的描述
                                ActionDescriptor action = descriptor.getAction(i);
                                String name = action.getName().toLowerCase();
                                log.info("Action name:" + action.getName());
                                //如果是第一步操作//退出浏览器
                                if (name.equals("open") || name.equals("start")
                                        || name.equals("first") || name.equals("opennternet")) {
                                    QuitInternet quitInternet = new QuitInternet(workflow,workId);
                                    future= threadPool.submit(quitInternet);
                                    log.info("start close browser thread...");
                                } else {
                                    //别的操作重试一次
                                    workflow.doAction(workId, i, null);
                                    Integer times = TimesMap.get(workflowName) == null ? 0 : TimesMap.get(workflowName);
                                    if (times >= 1) {
                                        QuitInternet quitInternet = new QuitInternet(workflow,workId);
                                        future = threadPool.submit(quitInternet);
                                        TimesMap.remove(workflowName);
                                        retry = false;
                                    } else {
                                        times++;
                                        TimesMap.put(workflowName, times);
                                        retry = true;
                                    }
                                }
                            } catch (WorkflowException e) {
                                e.printStackTrace();
                                //执行异常退出浏览器
                                quitInternet(workflow,workId);
                                log.error(e.getMessage(),e);
                            }
                        }
                         try {
                             quit = future.get(5000,TimeUnit.SECONDS);
                         }catch (Exception e){
                             log.error(e);
                         }
                        log.info("retry:"+retry + "quit:"+quit);
                        if(!retry) {
                            //如果不重试就移除该流程
                            Appliaction.WORKfLOWSTARTDATEMAP.remove(entry.getKey());
                        }
                    }
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private class QuitInternet implements Callable {
        private BasicWorkflow workflow;
        private Long workId;

        private QuitInternet(BasicWorkflow workflow, Long workId) {
            this.workflow = workflow;
            this.workId = workId;
        }

        @Override
        public Boolean call() throws Exception {
            return quitInternet(workflow, workId);
        }
    }

    private boolean quitInternet(BasicWorkflow workflow, Long workId) {
        PropertySet propertySet =  workflow.getPropertySet(workId);
        log.info("propertySet:"+ propertySet.getKeys());
        WebDriver webdriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        FirefoxDriver fd = (FirefoxDriver)webdriver;
        if (webdriver != null) {
            //只能close掉 不能quit quit会导致 数据全部丢失 The FirefoxDriver cannot be used after quit() was called.
//            webdriver.close();
            //webdriver.quit();//close有时候关不掉这个浏览器，quit会丢失
            fd.kill();
//            fd.close();;
            log.info("quit webdriver");
            return true;
        }else return false;
    }

    private void restartWorkflow(String workflowName, BasicWorkflow basicWorkflow,Long workId) throws WorkflowException {
        PropertySet src = basicWorkflow.getPropertySet(workId);

        long id = basicWorkflow.initialize(workflowName, 1, Collections.EMPTY_MAP);
        PropertySet target = basicWorkflow.getPropertySet(id);
        PropertySetCopy(src,target);
        Appliaction.WORKFLOWINMAP.put(workflowName, id);
        Appliaction.WORKfLOWSTARTDATEMAP.put(workflowName,new Date());
        int[] availableActions = basicWorkflow.getAvailableActions(id, Collections.EMPTY_MAP);
        System.out.println("availableAction:" + Arrays.toString(availableActions));
        for (int i:availableActions) {
            basicWorkflow.doAction(id, i, null);
        }
    }
    private void PropertySetCopy(PropertySet src,PropertySet target){
        log.info("collection:" + src.getKeys());
        PropertySetCloner cloner = new PropertySetCloner();
        cloner.setSource(src);
        cloner.setDestination(target);
        cloner.cloneProperties();
    }
}
