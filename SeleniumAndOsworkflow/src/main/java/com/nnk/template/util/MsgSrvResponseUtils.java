package com.nnk.template.util;

import com.nnk.interfacetemplate.common.DateUtil;
import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.interfacetemplate.handler.RechargeDict;
import com.nnk.interfacetemplate.handler.SlowIntDict;
import nnk.msgsrv.server.MsgSrvLongConnector;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Date;

public class MsgSrvResponseUtils implements SlowIntDict,RechargeDict {
    private  final String SPACE = "NA";
    private  final int END_TIME_INDEX = 3;
    private  final int STATE_INDEX = 4;
    private  final int MONEY_INDEX = 2;
    private  final String QUERY_STATE_SUCCEE = "1";
    private  final String QUERY_STATE_FAILURE = "2";
    private  final int BALANCE_PROTO_LENGTH = 5;
    private  Logger log= Logger.getLogger(MsgSrvResponseUtils.class);
    private  String[] contents = new String[CALLBACK_PROTO_LENGTH];// 协议内容
    private  MsgSrvLongConnector context;

    public MsgSrvResponseUtils(MsgSrvLongConnector context) {
        this.context = context;
    }

    /**
     * 异步处理订单同步返回受理结果，失败的受理结果。<p>
     * @param session
     */
    public  void responseBrokeFail(Request session){
        String[] cloneContents = session.getContent().split(" +");
        cloneContents[RESULT_INDEX] = BROKER2_FAILURE;
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() + " "+ StringUtil.transitionProtocol(cloneContents));
    }
    /**
     * 异步处理订单同步返回受理结果，失败的受理结果。<p>
     * @param session
     * @param oemorderid 代理商订单号
     */
    public void responseBrokeSuccess(Request session,String oemorderid){
        String[] cloneContents = session.getContent().split(" +");
        cloneContents[RESULT_INDEX] = BROKER2_SUCCEE;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() + " "+ StringUtil.transitionProtocol(cloneContents));
    }
    /**
     * 异步处理订单同步返回处理结果，失败的处理结果。主动回调<p>
     * @param contents
     * @param oemorderid 代理商订单号
     */
    public void responseCallbackFail(String[] contents,String realvalue,String oemorderid,String cftime,String transinfo){
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
    public  void responseCallbackSuccess(String[] contents,String realvalue,String oemorderid,String cftime,String transinfo){
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
    public  void responseCallbackSuccess(String sendorderid,String merid ,String realvalue,String oemorderid,String cftime,String transinfo){
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
    public  void responseCallbackFail(String sendorderid,String merid , String realvalue,String oemorderid,String cftime,String transinfo){
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
    public void responseCallbackFail(Request session,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] contents = session.getContent().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, CALLBACK_PROTO_LENGTH);
        cloneContents[RESULT_INDEX] = CALLBACK_FAILURE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }
    /**
     * 异步处理订单同步返回处理结果，成功的处理结果。<p>
     * @param session 谁发的查询回复谁
     * @param oemorderid 代理商订单号
     */
    public void responseCallbackSuccess(Request session,String realvalue,String oemorderid,String cftime,String transinfo){
        String[] contents = session.getContent().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, CALLBACK_PROTO_LENGTH);
        cloneContents[RESULT_INDEX] = CALLBACK_SUCCEE;
        cloneContents[COMMAND_ID_INDEX]= RESULT_COMMAND;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        cloneContents[CFM_TIME_INDEX] = cftime;
        cloneContents[TRAN_INFO_INDEX] = transinfo;
        cloneContents[REAL_VALUE_INDEX] = realvalue;
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }

    /**
     * 冲正返回冲正成功
     * @param session
     */
    public  void responseUnslowIntSuccess(Request session){
        String[] contents = session.getContent().split(" +");
        contents[RESULT_INDEX]=CALLBACK_SUCCEE;
        String msg = StringUtil.transitionProtocol(contents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }

    /**
     * 冲正返回冲正失败
     * @param session
     */
    public  void responseUnslowIntFail(Request session){
        String[] contents = session.getContent().split(" +");
        contents[RESULT_INDEX]=CALLBACK_FAILURE;
        String msg = StringUtil.transitionProtocol(contents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }
    /**
     * 查询余额成功
     * @param session
     */
    public  void responseBalanceSuccess(Request session,String money){
        String[] contents = session.getContent().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, BALANCE_PROTO_LENGTH);
        cloneContents[STATE_INDEX] = QUERY_STATE_SUCCEE;
        cloneContents[END_TIME_INDEX] = DateUtil.format(new Date());
        cloneContents[MONEY_INDEX] = money;
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }
    /**
     * 查询余额成功
     * @param session
     * @param
     */
    public  void responseBalanceFail(Request session){
        String[] contents = session.getContent().split(" +");
        String[] cloneContents = Arrays.copyOfRange(contents, 0, BALANCE_PROTO_LENGTH);
        cloneContents[STATE_INDEX]=QUERY_STATE_FAILURE;
        cloneContents[END_TIME_INDEX] = SPACE;
        cloneContents[MONEY_INDEX] = "0";
        String msg = StringUtil.transitionProtocol(cloneContents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }

}
