package com.nnk.template.function;

import com.nnk.utils.ftp.FtpConfig;
import com.nnk.utils.ftp.FtpUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/26
 * Time: 15:46
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//文件上传方法
public class FileftpUpoad extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(FileftpUpoad.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String tempfilename = (String) transientVars.get("fileName");
//        log.info("temp filename :" +  tempfilename);
        String filename = (String) propertySet.getString("fileName");
        log.info("temp1 filename :" +  filename);
        String localpath = (String) args.get("localPath");
        String remotePath = (String) args.get("remotePath");

        String host = (String) args.get("host");
        String port = (String) args.get("port");
        String userName = (String) args.get("userName");
        String userPwd = (String) args.get("userPwd");
        String connectTimeout =  (String) args.get("connectTimeout");
        if(StringUtils.isEmpty(filename)) throw new WorkflowException("fileName is not pass in last step");
        if(StringUtils.isEmpty(localpath)) throw new WorkflowException("localPath is not set");
        if(StringUtils.isEmpty(remotePath)) throw new WorkflowException("remotePath is not set");
        if(StringUtils.isEmpty(host)) throw new WorkflowException("host is not set");
        if(StringUtils.isEmpty(port)) throw new WorkflowException("port is not set");
        if(StringUtils.isEmpty(userName)) throw new WorkflowException("userName is not set");
        if(StringUtils.isEmpty(userPwd)) throw new WorkflowException("userPwd is not set");
        if(StringUtils.isEmpty(connectTimeout)) throw new WorkflowException("connectTimeout is not set");
        propertySet.setString("remotePath",remotePath);
        FtpConfig config = new FtpConfig();
        config.setIpAddress(host);
        config.setIpPort(Integer.parseInt(port));
        config.setConnectTimeout(Integer.parseInt(connectTimeout));
        config.setUserName(userName);
        config.setPassword(userPwd);
        FtpUtils ftpUtils = new FtpUtils(config);
        String filePathAndName = null;
        if(localpath.endsWith("/")){
            filePathAndName = localpath + filename;
        }else {
            filePathAndName = localpath + "/" + filename;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePathAndName));
            boolean ret = ftpUtils.upload(remotePath,filename,fileInputStream);
            if(ret){
                args.put("uploadResult","success");
                log.info("upload success");
            }else{
                log.info("upload fail");
                args.put("uploadResult","fail");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
