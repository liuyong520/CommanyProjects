package com.nnk.template;

import com.nnk.template.entity.SMSCodeRequest;
import com.nnk.template.service.MsgSrvService;
import com.nnk.template.util.DateUtils;
import com.nnk.template.util.MultiThreadExecutor;
import com.nnk.template.util.SchedureThread;
import com.nnk.template.util.TaskTimeOutThread;
import com.nnk.utils.encry.MD5Util;
import com.opensymphony.module.propertyset.PropertySet;

import com.opensymphony.workflow.*;
import com.opensymphony.workflow.basic.BasicWorkflow;
import nnk.msgsrv.server.MsgSrvLongConnector;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/17
 * Time: 15:18
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class Appliaction{

    private static final Logger logger = Logger.getLogger(Appliaction.class);
    private static BasicWorkflow basicWorkflowInstance;
    //短信验证码任务执行数
    public static ConcurrentHashMap<String,Integer> SMSTASKCACHE = new ConcurrentHashMap<String, Integer>();
    //短信验证码存储
    public static ConcurrentHashMap<String,List<SMSCodeRequest>> SMSCODECACHE = new ConcurrentHashMap<String, List<SMSCodeRequest>>();
    //workflowName 和id map
    public static ConcurrentHashMap<String,Long> WORKFLOWINMAP = new ConcurrentHashMap<String, Long>();
    //每个工作流开始执行的时间
    public static ConcurrentHashMap<String,Date> WORKfLOWSTARTDATEMAP = new ConcurrentHashMap<String, Date>();
    //去掉重复读取到的文件防止读到多个同样的文件
    public static Map<String,String> fileMap = new HashMap<String, String>();
    //代理编号和workFlowName 关系
    public static Map<String,String> MeridMap = new HashMap<String, String>();
    //全局的配置：
    public static Properties properties;

    public static BasicWorkflow getBasicWorkflowInstance(String callername){
        synchronized (Appliaction.class) {
            if (basicWorkflowInstance == null) {
                basicWorkflowInstance = new BasicWorkflow(callername);

                return basicWorkflowInstance;

            }else return basicWorkflowInstance;
        }
    }
    public static BasicWorkflow getBasicWorkflowInstance(){
        return getBasicWorkflowInstance("unknow");
    }
    public static Properties loadProperties(String configpath,String fillename){
        File path = new File(configpath);
        Collection<File> filenames = FileUtils.listFiles(path, new String[]{"properties"}, true);
        logger.info("filenames:" + filenames);
        Properties properties = new Properties();
        for(File file:filenames){
            if(!file.exists()){
                logger.info("file:"+file.getName() + "is not exist");
                continue;
            }else if(!file.canRead()){
                logger.info("file:"+file.getName() + "is not can read");
                continue;
            }
            if(file.getName().contains("log4j")||file.getName().contains("quartz")){
                continue;
            }
            FileInputStream in = null;
            try{
                if(file.getName().startsWith("global")){//先加载全局变量
                    in = new FileInputStream(file);
                    properties.load(in);
                }else if(file.getName().contains(fillename)){//在加载局部变量
                    in = new FileInputStream(file);
                    properties.load(in);
                }
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
                logger.error(ex.getMessage(),ex);
            }catch (IOException e){
                e.printStackTrace();
                logger.error(e.getMessage(),e);
            }
        }
        return properties;
    }
    public static void initflow(String workflowName,int initActionId,Map input) throws WorkflowException {
        if(basicWorkflowInstance==null) throw new WorkflowException("workflow is not init,please invoke getBasicWorkflowInstance");
        if(basicWorkflowInstance.canInitialize(workflowName,initActionId,input)){
            long workid = basicWorkflowInstance.initialize(workflowName, initActionId, input);
            WORKFLOWINMAP.put(workflowName,workid);
        }else {
            logger.error("init workflow action error");
        }
    }
    public static void initWorkflow(String workflowName,int initActionId,Map input){
        getBasicWorkflowInstance();
        try {
            initflow(workflowName,initActionId,input);
        } catch (WorkflowException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
    }

    public static Long getWorkid(Object key) {
        if(WORKFLOWINMAP.containsKey(key)) {
            return WORKFLOWINMAP.get(key);
        }else return null;

    }

    public static void main(String[] args) throws WorkflowException {
        //初始化所有的工作流
//        BasicWorkflow basicWorkflow = getBasicWorkflowInstance();
//        String[] workflowNames = basicWorkflow.getWorkflowNames();
//        for(String worlflowName:workflowNames){
//            initWorkflow(worlflowName,1, Collections.EMPTY_MAP);
//        }
        //加载msgsrv配置
//        String pathname = Thread.currentThread().getClass().getResource("/").getPath();
        String pathname = System.getProperty("user.dir");
        logger.info("path:" + pathname);
        File path = new File(pathname);
        Collection<File> filenames = FileUtils.listFiles(path, new String[]{"xml"}, true);
        logger.info("File list:" + filenames.toString());
        for (File file : filenames) {
            if (!file.exists()) {
                logger.info("file:" + file.getName() + "is not exist");
                continue;
            } else if (!file.canRead()) {
                logger.info("file:" + file.getName() + "is not can read");
                continue;
            }
            if (file.getName().startsWith("msgsrv")) {
                MsgSrvLongConnector connector = new MsgSrvLongConnector(file.getPath());
                connector.start();
                MsgSrvService.put(connector.getConnector().getConf().getServerName(), connector);
            }
            if(file.getName().startsWith("wf")&&!fileMap.containsKey(file.getName())){
                String workflowName = file.toURI().toString();
                String fileName = file.getName();

                fileMap.put(fileName,workflowName);

                String filename = fileName.substring(0,file.getName().indexOf('.'));
                MeridMap.put(filename,workflowName);
                properties = loadProperties(pathname,filename);
                String startime = properties.getProperty("threadStartTime");
                long delay = 0;
                if(startime.contains(":")){
                    delay = DateUtils.getAutoTime(startime);
                }else {
                    delay = Long.parseLong(startime);
                }
                String interval = properties.getProperty("threadInterval");
                logger.info("starttime:" + startime + "interval:" + interval);
                logger.info("fileName:"+workflowName);
                BasicWorkflow basicWorkflow = getBasicWorkflowInstance();
//                SchedureThread thread = new SchedureThread(basicWorkflow,workflowName,properties);
//                MultiThreadExecutor.scheduleWithFixedDelay(thread, delay, Long.parseLong(interval), TimeUnit.MINUTES);
                new Thread(new TaskTimeOutThread()).start();
            }
        }
    }


}
