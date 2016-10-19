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
 sz技术部胡中春 2016/8/10 10:16:37
 {"fullShortMsg":"彭友利，您好，您此次登录验证码为555647",
 "myPhone":"15219375376",
 "shortMsgPartFive":"",
 "shortMsgPartFour":"",
 "shortMsgPartOne":"彭友利，您好，您此次登录验证码为",
 "shortMsgPartThree":"",
 "shortMsgPartTwo":"555647",
 "sourcePhone":"10658338"}
 这段发给你的是base64编码的，
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
