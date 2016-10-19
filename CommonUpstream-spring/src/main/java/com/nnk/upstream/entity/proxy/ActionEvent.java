package com.nnk.upstream.entity.proxy;

import com.thoughtworks.xstream.XStream;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/23
 * Time: 10:09
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
/**
 * @deprecated
 */
public class ActionEvent {
    private Object obj;
    private XStream xStream;

    public ActionEvent(XStream xStream,Object obj) {
        this.xStream = xStream;
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public XStream getxStream() {
        return xStream;
    }

    public void setxStream(XStream xStream) {
        this.xStream = xStream;
    }
}
