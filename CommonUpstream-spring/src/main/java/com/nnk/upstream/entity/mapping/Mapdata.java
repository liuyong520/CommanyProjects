package com.nnk.upstream.entity.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/19
 * Time: 15:54
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Mapdata {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String nnkname;
    @XmlAttribute
    private String defaultvalue;
    @XmlAttribute
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNnkname() {
        return nnkname;
    }

    public void setNnkname(String nnkname) {
        this.nnkname = nnkname;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Mapdata{" +
                "name='" + name + '\'' +
                ", nnkname='" + nnkname + '\'' +
                ", defaultvalue='" + defaultvalue + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
