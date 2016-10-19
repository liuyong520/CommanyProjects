package com.nnk.upstream.handler;

import com.nnk.upstream.anotation.MethodAttribute;
import com.nnk.upstream.convert.ProtoclConverter;
import com.nnk.upstream.core.ConfigContextFilter;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.self.ProtoclType;
import com.nnk.upstream.entity.self.UnSlowIntProtocl;
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
 * Time: 18:05
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@MethodAttribute(methodName = "unSlowInt")
public class UnSlowIntHanlder {

    public static final Logger LOGGER = Logger.getLogger(UnSlowIntHanlder.class);

    @Autowired
    private HandlerServiceImp handlerService;
    @Autowired
    private HandlerServiceCustomize customizehandlerService;
    @Autowired
    private ProtoclConverter converter;

    @Autowired
    private ConfigContextFilter configContextFilter;
    @Autowired
    private ConfigContextManager configContextManager;
    public void unSlowInt(Request request){
        UpstreamSession session = new UpstreamSession(request,System.currentTimeMillis(), ProtoclType.UNRECHARGE);
        UnSlowIntProtocl unSlowIntProtocl = new UnSlowIntProtocl(session);
        InterfaceConfig config = configContextManager.searchConfigContext(unSlowIntProtocl.getMerid());
        if(0 == configContextFilter.excuteFilter(unSlowIntProtocl.getMerid())){
            LOGGER.warn("该代理商编号"+ unSlowIntProtocl.getMerid() + "发起的请求 不予受理" );
            return;
        }
        com.nnk.upstream.entity.parterner.Request unRechargeRequest = (com.nnk.upstream.entity.parterner.Request) converter.toRequest(session);
        boolean common = config.getInterfacetype();
        if(common){
            handlerService.unRecharge(session, unRechargeRequest);
        }else {
            try {
                customizehandlerService.unRecharge(session,unRechargeRequest);
            } catch (NetWorkException e) {
                e.printStackTrace();
            }
        }

    }
}
