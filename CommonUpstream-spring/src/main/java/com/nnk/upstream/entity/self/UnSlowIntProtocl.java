package com.nnk.upstream.entity.self;


/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/1/6
 * Time: 9:57
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class UnSlowIntProtocl {
    private String result;// 结果状态码 非1就是失败
    private String unsloworderid;//冲正订单号，原来的sendorderid
    private String merid;// 代理商编号
    private String meraccount;// 代理商账户
    private String sendorderid;// 原发送订单号
    private String cardtype;// 卡类别
    private String value;// 卡面值（申请面值），单位分
    private String timeout;// 允许的超时时间 , 以秒为单位
    private String province;// 省份
    private String command;// 交易命令（11.查询；22.充值申请及同步申请响应结果（分为：接受订单结果（不代表充值结果）；充值结果。）;33.充值结果）
    private String cardsn;// 充值卡序列号
    private String cdkey;// 充值卡密码
    private String mob;// 充值手机号码
    private String ordertime;// 本指令发出时的发送时间
    private String url;// Web方式回调地址
    private String attach;// 自定义信息, 007ka方直接返回.不能包含 & ? 等特别字符
    private String orderid;//代理商订单号
    public UnSlowIntProtocl(UpstreamSession session) {
        String[] contents = session.getContents().split(" +");
        this.result = contents[0];
        this.unsloworderid = contents[1];
        this.merid = contents[2];
        this.meraccount = contents[3];
        this.sendorderid = contents[4];
        this.cardtype = contents[5];
        this.value = contents[6];
        this.timeout = contents[7];
        this.province = contents[8];
        this.command = contents[9];
        this.cardsn = contents[10];
        this.cdkey = contents[11];
        this.mob = contents[12];
        this.ordertime = contents[13];
        this.url = contents[14];
        this.attach = contents[15];
        this.orderid=contents[16];//代理商订单号
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUnsloworderid() {
        return unsloworderid;
    }

    public void setUnsloworderid(String unsloworderid) {
        this.unsloworderid = unsloworderid;
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

    public String getSendorderid() {
        return sendorderid;
    }

    public void setSendorderid(String sendorderid) {
        this.sendorderid = sendorderid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getOrdertime() {
        return ordertime;
    }
    public void setOrdertime(String ordertime) {
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

    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }


}
