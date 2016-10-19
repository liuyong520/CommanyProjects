package com.nnk.template.register;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/17
 * Time: 16:23
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationConfig {
    private String name;
    private Map globalValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getGlobalValues() {
        return globalValues;
    }

    public void setGlobalValues(Map globalValues) {
        this.globalValues = globalValues;
    }

    public ApplicationConfig(String name, Map globalValues) {
        this.name = name;
        this.globalValues = globalValues;
    }
}
