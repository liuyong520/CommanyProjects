package com.nnk.upstream.core;

import com.nnk.upstream.vo.InterfaceConfig;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/14
 * Time: 10:42
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ConfigContext {


    private static Map<String,InterfaceConfig> InterfaceConfigMap = new ConcurrentHashMap<String, InterfaceConfig>();

    public static int size() {
        return InterfaceConfigMap.size();
    }

    public static InterfaceConfig put(String key, InterfaceConfig value) {
        return InterfaceConfigMap.put(key, value);
    }

    public static Collection<InterfaceConfig> values() {
        return InterfaceConfigMap.values();
    }

    public static boolean containsValue(Object value) {
        return InterfaceConfigMap.containsValue(value);
    }

    public static boolean containsKey(Object key) {
        return InterfaceConfigMap.containsKey(key);
    }

    public static boolean isEmpty() {
        return InterfaceConfigMap.isEmpty();
    }

    public static void clear() {
        InterfaceConfigMap.clear();
    }

    public static InterfaceConfig get(Object key) {
        return InterfaceConfigMap.get(key);
    }

    public static Set<String> keySet() {
        return InterfaceConfigMap.keySet();
    }

    public static void putAll(Map<? extends String, ? extends InterfaceConfig> m) {
        InterfaceConfigMap.putAll(m);
    }

    public static InterfaceConfig remove(Object key) {
        return InterfaceConfigMap.remove(key);
    }

    public static Set<Map.Entry<String, InterfaceConfig>> entrySet() {
        return InterfaceConfigMap.entrySet();
    }

}
