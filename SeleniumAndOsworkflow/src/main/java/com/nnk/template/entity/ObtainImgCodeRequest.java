package com.nnk.template.entity;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/15
 * Time: 9:18
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ObtainImgCodeRequest {
    //获取图片验证码请求
    private String sn;
    private String commandId;
    private String merId;
    private String sendTime;
    private String timeOut;
    private String remark;
    private String imgType;
    private String codeImg;

    public ObtainImgCodeRequest(String sn, String commandId, String merId, String sendTime, String timeOut,String remark, String imgType, String codeImg) {
        this.sn = sn;
        this.commandId = commandId;
        this.merId = merId;
        this.sendTime = sendTime;
        this.timeOut = timeOut;
        this.remark = remark;
        this.imgType = imgType;
        this.codeImg = codeImg;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getCodeImg() {
        return codeImg;
    }

    public void setCodeImg(String codeImg) {
        this.codeImg = codeImg;
    }

    @Override
    public String toString() {
        return "ObtainImgCodeRequest{" +
                "sn='" + sn + '\'' +
                ", commandId='" + commandId + '\'' +
                ", merId='" + merId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", remark='" + remark + '\'' +
                ", imgType='" + imgType + '\'' +
                ", codeImg='" + codeImg + '\'' +
                '}';
    }
}
