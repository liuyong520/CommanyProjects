package com.nnk.upstream.handler;

import com.nnk.upstream.anotation.MethodAttribute;
import com.nnk.upstream.convert.ProtoclConverter;
import com.nnk.upstream.core.ConfigContextFilter;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.self.ProtoclType;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.service.imp.HandlerServiceCustomize;
import com.nnk.upstream.service.imp.HandlerServiceImp;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.utils.http.exception.NetWorkException;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 18:23
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@MethodAttribute(methodName = "banlance")
public class BalanceHandler{

    public static final Logger LOGGER = Logger.getLogger(BalanceHandler.class);

    @Autowired
    private ProtoclConverter converter;

    @Autowired
    private HandlerServiceImp handlerService;

    @Autowired
    private ConfigContextFilter configContextFilter;

    @Autowired
    private HandlerServiceCustomize customizehandlerService;
    @Autowired
    private ConfigContextManager configContextManager;
    public void banlance(Request request){
        String[] context = request.getContent().split(" +");

        if(0 == configContextFilter.excuteFilter(context[1])){
            LOGGER.warn("该代理商编号"+ context[1] + "发起的请求 不予受理" );
            return;
        }
        InterfaceConfig config = configContextManager.searchConfigContext(context[1]);
        UpstreamSession session = new UpstreamSession(request,System.currentTimeMillis(), ProtoclType.BALANCE);
        com.nnk.upstream.entity.parterner.Request balanceRequest = (com.nnk.upstream.entity.parterner.Request) converter.toRequest(session);
        boolean common = config.getInterfacetype();
        if(common){
            handlerService.queryBalance(session,balanceRequest);
        }else{
            try {
                customizehandlerService.queryBalance(session,balanceRequest);
            } catch (NetWorkException e) {
                e.printStackTrace();
            }
        }

    }
}
