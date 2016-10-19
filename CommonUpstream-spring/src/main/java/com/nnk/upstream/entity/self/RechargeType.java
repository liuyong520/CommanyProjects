package com.nnk.upstream.entity.self;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/14
 * Time: 9:56
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public enum RechargeType {

    CHARG_MOBLIE(0,"CHARGE_MOBLIE"),CHARGE_FLOW(1,"CHARGE_FLOW"),CHARGE_OILE(2,"CHARGE_OILE");
    private String name;
    private int index;
    private RechargeType(int index,String name){
        this.index = index;
        this.name = name;
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

    public static RechargeType getname(int index){
        switch (index){
            case 0:return CHARG_MOBLIE;
            case 1:return CHARGE_FLOW;
            case 2:return CHARGE_OILE;
        }
        return CHARG_MOBLIE;
    }
}
