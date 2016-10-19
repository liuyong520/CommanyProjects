package com.nnk.upstream.entity.proxy;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/20
 * Time: 8:42
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
/**
 * @deprecated not used
 */
public class DynamicClassProxy {
    private Object object = null; //动态生成的类
    private BeanMap beanMap = null;
    private Set<String> nameset = new HashSet<String>();
    @SuppressWarnings("rawtypes")
    public DynamicClassProxy(Map propertyMap) {
        if(!nameset.isEmpty()){
            nameset.clear();
        }
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);

    }

    /**
     * 给bean属性赋值
     * @param property 属性名
     * @param value 值
     */
    public void setValue(Object property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * 通过属性名得到属性值
     * @param property 属性名
     * @return 值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }

    public Object[] getValues() {
       Object[] objects = new Object[nameset.size()];
       int n = 0;
       for(Iterator i = nameset.iterator(); i.hasNext();){
           objects[n] = beanMap.get(i.next());
           n++;
       }
        return objects;
    }

    /**
     * 得到该实体bean对象
     * @return
     */
    public Object getObject() {
        return this.object;
    }

    /**
     * @param propertyMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
//        generator.setSuperclass(Wrapper.class);
        Set keySet = propertyMap.keySet();
        nameset = keySet;
        for (Iterator i = keySet.iterator(); i.hasNext();) {
            String key = (String) i.next();
            generator.addProperty(key, (Class) propertyMap.get(key));
        }
        return generator.create();
    }
}
