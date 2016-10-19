package com.nnk.upstream.entity.self;


import com.nnk.upstream.util.DateUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/8
 * Time: 14:17
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SlowIntProctol implements Serializable {
    public static final String CALLBACK ="Callback";
    public static enum SendStatus{
        NOTSEND("0","未发送"), SENDED("1","已发送,未返回结果"),SENDEDANDRECVED("2","已发送，返回结果异常");
        private String status;
        private String index;
        private SendStatus(String index,String status){
            this.index = index;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }


    private static final long serialVersionUID = 2781092463178212879L;
    private String result;// 结果状态码 非1就是失败
    private String sendorderid;// 007订单号
    private String merid;// 代理商编号
    private String meraccount;// 代理商账户
    private String orderid;// 代理商订单号
    private String cardtype;// 卡类别
    private String value;// 卡面值（申请面值），单位分
    private String timeout;// 允许的超时时间 , 以秒为单位
    private String province;// 省份
    private String command;// 交易命令（11.查询；22.充值申请及同步申请响应结果（分为：接受订单结果（不代表充值结果）；充值结果。）;33.充值结果）
    private String cardsn;// 充值卡序列号
    private String cdkey;// 充值卡密码
    private String mob;// 充值手机号码
    private Date ordertime;// 本指令发出时的发送时间
    private String url;// Web方式回调地址
    private String attach;// 自定义信息, 007ka方直接返回.不能包含 & ? 等特别字符
    private String orgCommand;//原订单交易命令
    private String realValue;//实际充值金额
    private String chgTime;//充值操作时间
    private String cfmTime;//确认时间
    private String cbRetry;//第几次尝试回调
    private String checkTime;//对账时间
    private String tranInfo;//交易时间
    private String[] contents;
    private SendStatus sendStatus;
    private RechargeType rechargeType;



    private String sendedApp;
    public SlowIntProctol(String contents){
        this(contents.split(" +"));
    }
    public SlowIntProctol(String[] contents) {

        try {
            if(contents.length>=23) {
                this.orgCommand = contents[16];
                this.realValue = contents[17];
                this.chgTime = contents[18];
                this.cfmTime = contents[19];
                this.cbRetry = contents[20];
                this.checkTime = contents[21];
                this.tranInfo = contents[22];
                this.sendedApp = CALLBACK;
            }else if(contents.length>=16) {
                this.result = contents[0];
                this.sendorderid = contents[1];
                this.merid = contents[2];
                this.meraccount = contents[3];
                this.orderid = contents[4];
                this.cardtype =(contents[5]);
                this.value = (contents[6]);
                this.timeout = (contents[7]);
                this.province = contents[8];
                this.command = contents[9];
                this.cardsn = contents[10];
                this.cdkey = contents[11];
                this.mob = contents[12];
                this.ordertime = DateUtil.parse(contents[13]);
                this.url = contents[14];
                this.attach = contents[15];
                this.contents = contents;
            }else{
                throw new RuntimeException("protocl lenth is error");
            }

            sendStatus = SendStatus.NOTSEND;
        } catch (ParseException e) {
            e.printStackTrace();

        }catch (Exception e){
            throw new RuntimeException("protocl phares error");
        }


    }
    public RechargeType getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(RechargeType rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getOrgCommand() {
        return orgCommand;
    }

    public void setOrgCommand(String orgCommand) {
        this.orgCommand = orgCommand;
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public String getCfmTime() {
        return cfmTime;
    }

    public void setCfmTime(String cfmTime) {
        this.cfmTime = cfmTime;
    }

    public String getCbRetry() {
        return cbRetry;
    }

    public void setCbRetry(String cbRetry) {
        this.cbRetry = cbRetry;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getTranInfo() {
        return tranInfo;
    }

    public void setTranInfo(String tranInfo) {
        this.tranInfo = tranInfo;
    }

    public String getSendedApp() {
        return sendedApp;
    }

    public void setSendedApp(String sendedApp) {
        this.sendedApp = sendedApp;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSendorderid() {
        return sendorderid;
    }

    public void setSendorderid(String sendorderid) {
        this.sendorderid = sendorderid;
    }

    public String getMerid() {
        return merid;
    }

    public void setMerid(String merid) {
        this.merid = merid;
    }

    public String getMeraccount() {
        return meraccount;
    }

    public void setMeraccount(String meraccount) {
        this.meraccount = meraccount;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCardsn() {
        return cardsn;
    }

    public void setCardsn(String cardsn) {
        this.cardsn = cardsn;
    }

    public String getCdkey() {
        return cdkey;
    }

    public void setCdkey(String cdkey) {
        this.cdkey = cdkey;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
    public String getChgTime() {
        return chgTime;
    }

    public void setChgTime(String chgTime) {
        this.chgTime = chgTime;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "SlowInt{" +
                "result='" + result + '\'' +
                ", sendorderid='" + sendorderid + '\'' +
                ", merid='" + merid + '\'' +
                ", meraccount='" + meraccount + '\'' +
                ", orderid='" + orderid + '\'' +
                ", cardtype=" + cardtype +
                ", value=" + value +
                ", timeout=" + timeout +
                ", province='" + province + '\'' +
                ", command='" + command + '\'' +
                ", cardsn='" + cardsn + '\'' +
                ", cdkey='" + cdkey + '\'' +
                ", mob='" + mob + '\'' +
                ", ordertime=" + ordertime +
                ", url='" + url + '\'' +
                ", attach='" + attach + '\'' +
                ", chgTime='" + chgTime + '\'' +
                "";
    }
}

