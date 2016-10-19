package com.nnk.template.function;

import com.nnk.utils.encry.MD5Util;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/26
 * Time: 17:42
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//对文件进行操作
public class FileRename extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(FileRename.class);
    @Override
    public void executeCatched(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        String filename = (String) propertySet.getString("fileName");
        String rename =  (String) args.get("rename");
        String localPath =  (String) args.get("localPath");
        String remotePath = (String) args.get("remotePath");
        String fullName = null;
        String remotFullName = null;
        if(StringUtils.isEmpty(rename)) throw new WorkflowException("rename is not set");
        if(StringUtils.isEmpty(filename)) throw new WorkflowException("filename is not set");
        if(StringUtils.isEmpty(localPath)) throw new WorkflowException("localPath is not set");
        if(StringUtils.isEmpty(remotePath)) throw new WorkflowException("remotePath is not set");
        if(localPath.endsWith("/")){
            fullName = localPath + filename;
        }else {
            fullName = localPath +"/"+ filename;
        }
        if(remotePath.endsWith("/")){
            remotFullName = remotePath + rename;
        }else {
            remotFullName = remotePath +"/"+ rename;
        }

        try {
            File localfile = new File(fullName);
            while (!localfile.exists()){
               Thread.sleep(1000);
            }
            File remotfile = new File(remotFullName);
            if (remotfile.exists()){
                remotfile.delete();
            }
            log.info("localfile:" +localfile +",remotfile:" + remotfile);
            FileUtils.copyFile(localfile,new File(remotFullName));

            FileUtils.deleteQuietly(localfile);
            propertySet.setString("fileName",rename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
