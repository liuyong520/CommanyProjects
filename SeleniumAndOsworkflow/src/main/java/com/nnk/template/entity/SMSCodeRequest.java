package com.nnk.template.entity;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/17
 * Time: 15:10
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

/**
 *
 sz���������д� 2016/8/10 10:16:37
 {"fullShortMsg":"�����������ã����˴ε�¼��֤��Ϊ555647",
 "myPhone":"15219375376",
 "shortMsgPartFive":"",
 "shortMsgPartFour":"",
 "shortMsgPartOne":"�����������ã����˴ε�¼��֤��Ϊ",
 "shortMsgPartThree":"",
 "shortMsgPartTwo":"555647",
 "sourcePhone":"10658338"}
 ��η��������base64����ģ�
 */
public class SMSCodeRequest {
    private String fullShortMsg;
    private String myPhone;
    private String shortMsgPartFive;
    private String shortMsgPartFour;
    private String shortMsgPartOne;
    private String shortMsgPartThree;
    private String shortMsgPartTwo;
    private String sourcePhone;

    public String getFullShortMsg() {
        return fullShortMsg;
    }

    public String getMyPhone() {
        return myPhone;
    }

    public String getShortMsgPartFive() {
        return shortMsgPartFive;
    }

    public String getShortMsgPartFour() {
        return shortMsgPartFour;
    }

    public String getShortMsgPartOne() {
        return shortMsgPartOne;
    }

    public String getShortMsgPartThree() {
        return shortMsgPartThree;
    }

    public String getShortMsgPartTwo() {
        return shortMsgPartTwo;
    }

    public String getSourcePhone() {
        return sourcePhone;
    }

    public void setFullShortMsg(String fullShortMsg) {
        this.fullShortMsg = fullShortMsg;
    }

    public void setMyPhone(String myPhone) {
        this.myPhone = myPhone;
    }

    public void setShortMsgPartFive(String shortMsgPartFive) {
        this.shortMsgPartFive = shortMsgPartFive;
    }

    public void setShortMsgPartFour(String shortMsgPartFour) {
        this.shortMsgPartFour = shortMsgPartFour;
    }

    public void setShortMsgPartOne(String shortMsgPartOne) {
        this.shortMsgPartOne = shortMsgPartOne;
    }

    public void setShortMsgPartThree(String shortMsgPartThree) {
        this.shortMsgPartThree = shortMsgPartThree;
    }

    public void setShortMsgPartTwo(String shortMsgPartTwo) {
        this.shortMsgPartTwo = shortMsgPartTwo;
    }

    public void setSourcePhone(String sourcePhone) {
        this.sourcePhone = sourcePhone;
    }
}
