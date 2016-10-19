package com.nnk.upstream.core;

import com.nnk.upstream.vo.InterfaceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/14
 * Time: 14:47
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//上下文过滤器，如果上下文没有该配置则
@Component
public class ConfigContextFilter implements Filter{

    @Autowired
    private ConfigContextManager configContextManager;

    @Override
    public int excuteFilter(String mechantNo) {

        InterfaceConfig config = configContextManager.searchConfigContext(mechantNo);
        if(null != config){
            return 1;
        }else {
            return 0;
        }
    }
}
