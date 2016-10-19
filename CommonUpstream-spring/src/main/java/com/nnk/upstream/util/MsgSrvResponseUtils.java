package com.nnk.upstream.util;

import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.interfacetemplate.handler.RechargeDict;
import com.nnk.interfacetemplate.handler.SlowIntDict;
import com.nnk.upstream.core.MsgSrvService;
import com.nnk.upstream.entity.self.UpstreamSession;
import nnk.msgsrv.server.MsgSrvLongConnector;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Date;

public class MsgSrvResponseUtils implements SlowIntDict,RechargeDict {
    private static final String SPACE = "NA";
    private static final int END_TIME_INDEX = 3;
    private static final int STATE_INDEX = 4;
    private static final int MONEY_INDEX = 2;
    private static final String QUERY_STATE_SUCCEE = "1";
    private static final String QUERY_STATE_FAILURE = "2";
    private static final int BALANCE_PROTO_LENGTH = 5;
    private static Logger log= Logger.getLogger(MsgSrvResponseUtils.class);
    private static String[] contents = new String[CALLBACK_PROTO_LENGTH];// 协议内容
    private static MsgSrvLongConnector context  = MsgSrvService.get("start");


    /**
     * 异步处理订单同步返回受理结果，失败的受理结果。<p>
     * @param session
     */
    public static void responseBrokeFail(UpstreamSession session){
        String[] cloneContents = session.getContents().split(" +");
        cloneContents[RESULT_INDEX] = BROKER2_FAILURE;
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() + " "+ StringUtil.transitionProtocol(cloneContents));
    }
    /**
     * 异步处理订单同步返回受理结果，失败的受理结果。<p>
     * @param session
     * @param oemorderid 代理商订单号
     */
    public static void responseBrokeSuccess(UpstreamSession session,String oemorderid){
        String[] cloneContents = session.getContents().split(" +");
        cloneContents[RESULT_INDEX] = BROKER2_SUCCEE;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() + " "+ StringUtil.transitionProtocol(cloneContents));
    }
    /**
     * 异步处理订单同步返回处理结果，失败的处理结果。主动回调<p>
     * @param contents
     * @param oemorderid 代理商订单号
     */
    public static void responseCallbackFail(String[] contents,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] cloneContents = Arrays.copyOfRange(contents, 0, CALLBACK_PROTO_LENGTH);
        cloneContents[RESULT_INDEX] = CALLBACK_FAILURE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String callname = "IFTran";
        String callbackcmd = "SlowInt";
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(callname + " "+ callbackcmd +" "+msg);
    }
    /**
     * 异步处理订单同步返回处理结果，成功的处理结果。<p>
     * @param contents
     * @param oemorderid 代理商订单号
     */
    public static void responseCallbackSuccess(String[] contents,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] cloneContents = Arrays.copyOfRange(contents, 0, CALLBACK_PROTO_LENGTH);
        cloneContents[RESULT_INDEX] = CALLBACK_SUCCEE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String callname = "IFTran";
        String callbackcmd = "SlowInt";
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(callname + " "+ callbackcmd +" "+msg);
    }
    public static void responseCallbackSuccess(String sendorderid,String merid ,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] cloneContents = new String [CALLBACK_PROTO_LENGTH];
        cloneContents[RESULT_INDEX] = CALLBACK_SUCCEE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[MER_ID_INDEX]= merid ;
        cloneContents[IN_ORDER_ID_INDEX]=sendorderid;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String callname = "IFTran";
        String callbackcmd = "SlowInt";
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(callname + " "+ callbackcmd +" "+msg);
    }
    public static void responseCallbackFail(String sendorderid,String merid , String realvalue,String oemorderid,String cftime,String transinfo){
        String[] cloneContents = new String [CALLBACK_PROTO_LENGTH];
        cloneContents[RESULT_INDEX] = CALLBACK_FAILURE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[MER_ID_INDEX] = merid;
        cloneContents[IN_ORDER_ID_INDEX]=sendorderid;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String callname = "IFTran";
        String callbackcmd = "SlowInt";
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(callname + " "+ callbackcmd +" "+msg);
    }
    /**
     * 异步处理订单同步返回处理结果，失败的处理结果。主动回调<p>
     * @param session 谁发的协议回给谁
     * @param oemorderid 代理商订单号
     */
    public static void responseCallbackFail(UpstreamSession session,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] contents = session.getContents().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, CALLBACK_PROTO_LENGTH);
        cloneContents[RESULT_INDEX] = CALLBACK_FAILURE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() +" "+msg);
    }
    /**
     * 异步处理订单同步返回处理结果，成功的处理结果。<p>
     * @param session 谁发的查询回复谁
     * @param oemorderid 代理商订单号
     */
    public static void responseCallbackSuccess(UpstreamSession session,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] contents = session.getContents().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, CALLBACK_PROTO_LENGTH);
        cloneContents[RESULT_INDEX] = CALLBACK_SUCCEE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() +" "+msg);
    }

    /**
     * 冲正返回冲正成功
     * @param session
     */
    public static void responseUnslowIntSuccess(UpstreamSession session){
        String[] contents = session.getContents().split(" +");
        contents[RESULT_INDEX]=CALLBACK_SUCCEE;
        String msg = StringUtil.transitionProtocol(contents);
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() +" "+msg);
    }

    /**
     * 冲正返回冲正失败
     * @param session
     */
    public static void responseUnslowIntFail(UpstreamSession session){
        String[] contents = session.getContents().split(" +");
        contents[RESULT_INDEX]=CALLBACK_FAILURE;
        String msg = StringUtil.transitionProtocol(contents);
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() +" "+msg);
    }
    /**
     * 查询余额成功
     * @param session
     */
    public static void responseBalanceSuccess(UpstreamSession session,String money){
        String[] contents = session.getContents().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, BALANCE_PROTO_LENGTH);
        cloneContents[STATE_INDEX] = QUERY_STATE_SUCCEE;
        cloneContents[END_TIME_INDEX] = DateUtil.format(new Date());
        cloneContents[MONEY_INDEX] = money;
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() +" "+msg);
    }
    /**
     * 查询余额成功
     * @param session
     * @param
     */
    public static void responseBalanceFail(UpstreamSession session){
        String[] contents = session.getContents().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, BALANCE_PROTO_LENGTH);
        cloneContents[STATE_INDEX]=QUERY_STATE_FAILURE;
        cloneContents[END_TIME_INDEX] = SPACE;
        cloneContents[MONEY_INDEX] = "0";
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppname() + " "+ session.getCmd() +" "+msg);
    }

}
