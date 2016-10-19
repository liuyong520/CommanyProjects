package com.nnk.upstream.entity.self;

import nnk.msgsrv.server.Request;

import java.io.Serializable;
import java.util.UUID;


/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/7
 * Time: 17:03
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class UpstreamSession implements Serializable {

    private static final long serialVersionUID = -5653241790168204955L;
//    private Request request
    private long time;
    private String appname;
    private String cmd;
    private String contents;
    private ProtoclType protoclType;
    private String sessionId;


    public UpstreamSession(Request request,long time,ProtoclType protoclType) {
        this.appname = request.getAppName();
        this.cmd = request.getCmdName();
        this.contents = request.getContent();
        this.time = time;
        this.protoclType = protoclType;
        this.sessionId = UUID.randomUUID().toString().replace("-","");
    }
    public UpstreamSession(String appname,String cmd,String contents,long time,ProtoclType protoclType) {
        this.appname = appname;
        this.cmd = cmd;
        this.contents = contents;
        this.time = time;
        this.protoclType = protoclType;
        this.sessionId = UUID.randomUUID().toString().replace("-","");
    }
    public ProtoclType getProtoclType() {
        return protoclType;
    }

    public void setProtoclType(ProtoclType protoclType) {
        this.protoclType = protoclType;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UpstreamSession{" +
                "time=" + time +
                ", appname='" + appname + '\'' +
                ", cmd='" + cmd + '\'' +
                ", contents='" + contents + '\'' +
                ", protoclType=" + protoclType +
                '}';
    }
}
