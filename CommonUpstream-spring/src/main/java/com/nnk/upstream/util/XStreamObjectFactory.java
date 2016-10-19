package com.nnk.upstream.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/20
 * Time: 17:15
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class XStreamObjectFactory {
    private static final XStream XSTREAM_INSTANCE = null;

    public static XStream getStaxDriverInstance(){
        if(XSTREAM_INSTANCE==null) {
            return new XStream(new StaxDriver());
        }else{
            return XSTREAM_INSTANCE;
        }
    }

    public static XStream getDomDriverInstance(){
        if(XSTREAM_INSTANCE==null){
            return new XStream(new DomDriver());
        }else{
            return XSTREAM_INSTANCE;
        }
    }

    public static XStream getJettisonDriverInstance(){
        if(XSTREAM_INSTANCE==null){
            return new XStream(new JettisonMappedXmlDriver());
        }  else {
            return XSTREAM_INSTANCE;
        }
    }
}
