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
    private  String[] contents = new String[CALLBACK_PROTO_LENGTH];// Э������
    private  MsgSrvLongConnector context;

    public MsgSrvResponseUtils(MsgSrvLongConnector context) {
        this.context = context;
    }

    /**
     * �첽������ͬ��������������ʧ�ܵ���������<p>
     * @param session
     */
    public  void responseBrokeFail(Request session){
        String[] cloneContents = session.getContent().split(" +");
        cloneContents[RESULT_INDEX] = BROKER2_FAILURE;
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() + " "+ StringUtil.transitionProtocol(cloneContents));
    }
    /**
     * �첽������ͬ��������������ʧ�ܵ���������<p>
     * @param session
     * @param oemorderid �����̶�����
     */
    public void responseBrokeSuccess(Request session,String oemorderid){
        String[] cloneContents = session.getContent().split(" +");
        cloneContents[RESULT_INDEX] = BROKER2_SUCCEE;
        cloneContents[OEM_ORDER_ID_INDEX]= oemorderid;
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() + " "+ StringUtil.transitionProtocol(cloneContents));
    }
    /**
     * �첽������ͬ�����ش�������ʧ�ܵĴ������������ص�<p>
     * @param contents
     * @param oemorderid �����̶�����
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
     * �첽������ͬ�����ش��������ɹ��Ĵ�������<p>
     * @param contents
     * @param oemorderid �����̶�����
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
     * �첽������ͬ�����ش�������ʧ�ܵĴ������������ص�<p>
     * @param session ˭����Э��ظ�˭
     * @param oemorderid �����̶�����
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
     * �첽������ͬ�����ش��������ɹ��Ĵ�������<p>
     * @param session ˭���Ĳ�ѯ�ظ�˭
     * @param oemorderid �����̶�����
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
     * �������س����ɹ�
     * @param session
     */
    public  void responseUnslowIntSuccess(Request session){
        String[] contents = session.getContent().split(" +");
        contents[RESULT_INDEX]=CALLBACK_SUCCEE;
        String msg = StringUtil.transitionProtocol(contents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }

    /**
     * �������س���ʧ��
     * @param session
     */
    public  void responseUnslowIntFail(Request session){
        String[] contents = session.getContent().split(" +");
        contents[RESULT_INDEX]=CALLBACK_FAILURE;
        String msg = StringUtil.transitionProtocol(contents);
        context.getConnector().send(session.getAppName() + " "+ session.getCmdName() +" "+msg);
    }
    /**
     * ��ѯ���ɹ�
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
     * ��ѯ���ɹ�
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
