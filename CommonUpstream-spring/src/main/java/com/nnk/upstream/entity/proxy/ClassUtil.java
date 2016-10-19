package com.nnk.upstream.entity.proxy;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/20
 * Time: 8:53
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
/**
 * @deprecated
 */
public class ClassUtil {

    @SuppressWarnings({ "rawtypes","unchecked"})
    public static Object dynamicClass(Object object,Map attrMap) throws Exception{
        HashMap returnMap = new HashMap();
        HashMap typeMap=new HashMap();
//读取配置文件
        String sourcepackage = object.getClass().getName();
        String classname = sourcepackage.substring(sourcepackage.lastIndexOf(".")+1);

        Set<String> keylist=attrMap.keySet();

        Class type=object.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(int i=0;i<propertyDescriptors.length;i++){
            PropertyDescriptor descriptor=propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if(!propertyName.equals("class")){
                Method readMethod=descriptor.getReadMethod();

                Object result = readMethod.invoke(object, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
                typeMap.put(propertyName, descriptor.getPropertyType());
            }
        }
//加载配置文件中的属性
        Iterator<String> iterator=keylist.iterator();
        while(iterator.hasNext()){
            String key=iterator.next();
            returnMap.put(key, attrMap.get(key));
            Object obj = attrMap.get(key);
            typeMap.put(key, obj.getClass());
        }
//map转换成实体对象
        DynamicClassProxy bean = new DynamicClassProxy(typeMap);
//赋值
        Set keys=typeMap.keySet();
        for(Iterator it=keys.iterator();it.hasNext();){
            String key = (String) it.next();
            bean.setValue(key, returnMap.get(key));
        }

        Object obj=bean.getObject();

        return obj;
    }
    public static void main(String[] args) throws Exception{


    }
}
