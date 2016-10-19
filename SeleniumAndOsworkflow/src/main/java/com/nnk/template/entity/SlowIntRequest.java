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
    private String result;// ���״̬�� ��1����ʧ��
    private String sendorderid;// 007������
    private String merid;// �����̱��
    private String meraccount;// �������˻�
    private String orderid;// �����̶�����
    private int cardtype;// �����
    private long value;// ����ֵ��������ֵ������λ��
    private long timeout;// ����ĳ�ʱʱ�� , ����Ϊ��λ
    private String province;// ʡ��
    private String command;// �������11.��ѯ��22.��ֵ���뼰ͬ��������Ӧ�������Ϊ�����ܶ���������������ֵ���������ֵ�������;33.��ֵ�����
    private String cardsn;// ��ֵ�����к�
    private String cdkey;// ��ֵ������
    private String mob;// ��ֵ�ֻ�����
    private Date ordertime;// ��ָ���ʱ�ķ���ʱ��
    private String url;// Web��ʽ�ص���ַ
    private String attach;// �Զ�����Ϣ, 007ka��ֱ�ӷ���.���ܰ��� & ? ���ر��ַ�

    private String chgTime;//��ֵ����ʱ��

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
