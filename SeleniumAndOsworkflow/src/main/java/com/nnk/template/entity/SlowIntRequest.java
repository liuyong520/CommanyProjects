package com.nnk.template.entity;


import com.nnk.template.util.DateUtils;
import nnk.msgsrv.server.Request;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/19
 * Time: 9:15
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SlowIntRequest {
    private String result;// 结果状态码 非1就是失败
    private String sendorderid;// 007订单号
    private String merid;// 代理商编号
    private String meraccount;// 代理商账户
    private String orderid;// 代理商订单号
    private int cardtype;// 卡类别
    private long value;// 卡面值（申请面值），单位分
    private long timeout;// 允许的超时时间 , 以秒为单位
    private String province;// 省份
    private String command;// 交易命令（11.查询；22.充值申请及同步申请响应结果（分为：接受订单结果（不代表充值结果）；充值结果。）;33.充值结果）
    private String cardsn;// 充值卡序列号
    private String cdkey;// 充值卡密码
    private String mob;// 充值手机号码
    private Date ordertime;// 本指令发出时的发送时间
    private String url;// Web方式回调地址
    private String attach;// 自定义信息, 007ka方直接返回.不能包含 & ? 等特别字符

    private String chgTime;//充值操作时间

    private Request request;
    public SlowIntRequest(Request request) {
        String[] contents = request.getContent().split(" +");
        this.result = contents[0];
        this.sendorderid = contents[1];
        this.merid = contents[2];
        this.meraccount = contents[3];
        this.orderid = contents[4];
        this.cardtype = Integer.parseInt(contents[5]);
        this.value = Long.parseLong(contents[6]);
        this.timeout = Long.parseLong(contents[7]);
        this.province = contents[8];
        this.command = contents[9];
        this.cardsn = contents[10];
        this.cdkey = contents[11];
        this.mob = contents[12];
        this.ordertime = DateUtils.dateStringToDate(contents[13],"yyyyMMddHHmmss");
        this.url = contents[14];
        this.attach = contents[15];
        this.request= request;
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

    public int getCardtype() {
        return cardtype;
    }

    public void setCardtype(int cardtype) {
        this.cardtype = cardtype;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
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

    @Override
    public String toString() {
        return "SlowIntRechargeRequest{" +
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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
