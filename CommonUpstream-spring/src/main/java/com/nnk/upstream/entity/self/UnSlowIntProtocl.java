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
    private String result;// ���״̬�� ��1����ʧ��
    private String unsloworderid;//���������ţ�ԭ����sendorderid
    private String merid;// �����̱��
    private String meraccount;// �������˻�
    private String sendorderid;// ԭ���Ͷ�����
    private String cardtype;// �����
    private String value;// ����ֵ��������ֵ������λ��
    private String timeout;// ����ĳ�ʱʱ�� , ����Ϊ��λ
    private String province;// ʡ��
    private String command;// �������11.��ѯ��22.��ֵ���뼰ͬ��������Ӧ�������Ϊ�����ܶ���������������ֵ���������ֵ�������;33.��ֵ�����
    private String cardsn;// ��ֵ�����к�
    private String cdkey;// ��ֵ������
    private String mob;// ��ֵ�ֻ�����
    private String ordertime;// ��ָ���ʱ�ķ���ʱ��
    private String url;// Web��ʽ�ص���ַ
    private String attach;// �Զ�����Ϣ, 007ka��ֱ�ӷ���.���ܰ��� & ? ���ر��ַ�
    private String orderid;//�����̶�����
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
        this.orderid=contents[16];//�����̶�����
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
