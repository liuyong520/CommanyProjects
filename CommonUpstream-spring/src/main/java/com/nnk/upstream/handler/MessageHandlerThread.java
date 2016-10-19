package com.nnk.upstream.handler;

import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.util.SpringContextUtils;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/16
 * Time: 9:47
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

public class MessageHandlerThread implements Runnable{
    private final static Logger LOGGER = Logger.getLogger(MessageHandlerThread.class);
    private UpstreamSession session;

    private Handler messageHandler = (Handler) SpringContextUtils.getBean("messageHandler");

    public MessageHandlerThread(UpstreamSession session) {
        this.session = session;

    }

    @Override
    public void run() {
        try {
            LOGGER.info("this thread id"+ Thread.currentThread().getName());
            messageHandler.handlerRequest(session);

        }catch (Exception e){
            LOGGER.error("œﬂ≥Ã“Ï≥££°");
            e.printStackTrace();
        }
    }
}
