package com.nnk.upstream.handler;

import com.nnk.interfacetemplate.handler.RechargeDict;
import com.nnk.interfacetemplate.handler.SlowIntDict;
import com.nnk.upstream.anotation.MethodAttribute;
import com.nnk.upstream.core.ConfigContextFilter;
import com.nnk.upstream.entity.self.ProtoclType;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.util.MsgSrvResponseUtils;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.RejectedExecutionException;


/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/1
 * Time: 11:54
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@MethodAttribute(methodName = "slowInt")
public class SlowIntHandler implements SlowIntDict,RechargeDict {

    public static final Logger LOGGER = Logger.getLogger(SlowIntHandler.class);

    //线程池

    private TaskExecutor taskExecutorRecharge;
    private TaskExecutor taskExecutorQuery;

    public void setTaskExecutorRecharge(TaskExecutor taskExecutorRecharge) {
        this.taskExecutorRecharge = taskExecutorRecharge;
    }

    public void setTaskExecutorQuery(TaskExecutor taskExecutorQuery) {
        this.taskExecutorQuery = taskExecutorQuery;
    }

    @Autowired
    private ConfigContextFilter filter;

    public synchronized void slowInt(Request request) {
        String[] contents = request.getContent().split(" +");
        String merchantNo = contents[MER_ID_INDEX];
        if(contents.length >= BROKER_PROTO_LENGTH) {
            if(contents[RESULT_INDEX].equals("1")||contents[RESULT_INDEX].equals("9")||contents[RESULT_INDEX].equals("18")){
                return;
            }
            if (contents[COMMAND_ID_INDEX].equals(RECHARGE_COMMAND)) {
                UpstreamSession session = new UpstreamSession(request,System.currentTimeMillis(), ProtoclType.RECHARGE);
                if(1 == filter.excuteFilter(merchantNo)) {
                       try {
                           taskExecutorRecharge.execute(new MessageHandlerThread(session));
                       }catch (RejectedExecutionException e) {
                           MsgSrvResponseUtils.responseBrokeFail(session);
                           LOGGER.warn("[充值请求][超过请求负载][下单失败][  请求信息：" + session + "]; 异常信息: 超过最大并发数量，请考虑调整并发数量限制]");
                       }
                }else{
                    LOGGER.warn("该代理商编号"+ merchantNo + "发起的请求 不予受理" );
                    MsgSrvResponseUtils.responseBrokeFail(session);
                }
            } else if (contents[COMMAND_ID_INDEX].equals(QUERY_COMMAND)) {
                UpstreamSession session = new UpstreamSession(request,System.currentTimeMillis(), ProtoclType.QUERY);
                if(1==filter.excuteFilter(merchantNo)) {
                    try {
                        taskExecutorQuery.execute(new MessageHandlerThread(session));
                    }catch (RejectedExecutionException e) {
                        LOGGER.warn("[查询请求][超过请求负载][查询失败][  请求信息：" + session + "]; 异常信息: 超过最大并发数量，请考虑调整并发数量限制]");
                    }
                }else{
                    LOGGER.warn("该代理商编号"+ merchantNo + "发起的请求 不予受理" );
                }
            }
        }
    }
}
