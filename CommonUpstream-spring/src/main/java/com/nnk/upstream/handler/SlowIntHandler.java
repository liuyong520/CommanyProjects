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

    //�̳߳�

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
                           LOGGER.warn("[��ֵ����][����������][�µ�ʧ��][  ������Ϣ��" + session + "]; �쳣��Ϣ: ������󲢷��������뿼�ǵ���������������]");
                       }
                }else{
                    LOGGER.warn("�ô����̱��"+ merchantNo + "��������� ��������" );
                    MsgSrvResponseUtils.responseBrokeFail(session);
                }
            } else if (contents[COMMAND_ID_INDEX].equals(QUERY_COMMAND)) {
                UpstreamSession session = new UpstreamSession(request,System.currentTimeMillis(), ProtoclType.QUERY);
                if(1==filter.excuteFilter(merchantNo)) {
                    try {
                        taskExecutorQuery.execute(new MessageHandlerThread(session));
                    }catch (RejectedExecutionException e) {
                        LOGGER.warn("[��ѯ����][����������][��ѯʧ��][  ������Ϣ��" + session + "]; �쳣��Ϣ: ������󲢷��������뿼�ǵ���������������]");
                    }
                }else{
                    LOGGER.warn("�ô����̱��"+ merchantNo + "��������� ��������" );
                }
            }
        }
    }
}
