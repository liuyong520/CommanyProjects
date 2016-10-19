package com.nnk.upstream.entity.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/25
 * Time: 17:50
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public enum ProctolTypeEnum {

    XML("XML",1),JSON("JSON",2),FORM("STR",3);
    private String name;
    private int index;
    private ProctolTypeEnum(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
