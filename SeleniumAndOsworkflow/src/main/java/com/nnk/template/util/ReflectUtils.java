package com.nnk.template.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/8
 * Time: 11:25
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ReflectUtils {

    /**
     * 获取键值对的字符串值
     * @param obj
     * @param keysplit
     * @param valueSplit
     * @param removedkeys
     * @return
     */
    public static String getkeyValueString(Object obj,String keysplit,String valueSplit,String... removedkeys){
        if(null == obj){
            return null;
        }
        Map<String,Object> map =sortFieldMap(obj);
        if(removedkeys != null) {
            for (String key : removedkeys) {
                if (map.containsKey(key)) {
                    map.remove(key);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String,Object> entry : map.entrySet()){
            stringBuilder.append(entry.getKey()).append(keysplit)
                    .append(entry.getValue()).append(valueSplit);
        }
        String str = stringBuilder.toString();
        return str.substring(0,str.length()-1);
    }
    /**
     * 获取排序后的键值map对
     */
    public static Map<String, Object> sortFieldMap(Object obj){
        Map<String,Object> map = new TreeMap<String, Object>();
        Class<?> clazz= obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field  field :fields){
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 获取anotation的值
     * @param obj
     * @param anotationClazz
     * @return
     */
    public static Annotation getAnotiation(Object obj,Class anotationClazz){
        Class<?> clazz = obj.getClass();
        return clazz.getAnnotation(anotationClazz);
    }


    public static void Base64Encord(Object obj,List<String> ingorelist) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String str =  field.getName();
            if(ingorelist!=null&&ingorelist.contains(str)){
               continue;
            }else {
                Object value = field.get(obj);
                Class<?> type = field.getType();
                if(type.isAssignableFrom(String.class)){
                    String value1 = (String) value;
                    if(value1==null||value1.length()==0){
                        continue;
                    }
                    String base64str = Base64Util.encode(value1);
                    System.out.println(base64str);
                    field.set(obj, base64str);
                }else{
                    field.set(obj, value);
                }
            }
        }
    }



    public static Object invokeMethodName(Object targetObject,
                                          String methodName,
                                          Class<?>[] targetParameterTypes,
                                          Object... targetParameters)  {
        try {
            Class<?> targetCLass = targetObject.getClass();
            if(targetParameterTypes == null || targetParameters == null){

                Method method = targetCLass.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return method.invoke(targetObject);
            }else {
                Method method = targetCLass.getDeclaredMethod(methodName, targetParameterTypes);
                method.setAccessible(true);
                return method.invoke(targetObject, targetParameters);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Object invokeMethodNameStatic(Class<?> targetCLass,
                                          String methodName,
                                          Class<?>[] targetParameterTypes,
                                          Object... targetParameters)  {
        try {
            if(targetParameterTypes == null || targetParameters == null){
                Method method = targetCLass.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return method.invoke(null);
            }else {
                Method method = targetCLass.getDeclaredMethod(methodName, targetParameterTypes);
                method.setAccessible(true);
                return method.invoke(null, targetParameters);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
