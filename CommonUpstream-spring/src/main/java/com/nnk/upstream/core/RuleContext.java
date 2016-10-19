package com.nnk.upstream.core;

import com.nnk.upstream.vo.InterfaceRule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/25
 * Time: 9:11
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class RuleContext {
    private static Map<String,InterfaceRule> InterfaceRuleMap = new ConcurrentHashMap<String, InterfaceRule>();

    public static int size() {
        return InterfaceRuleMap.size();
    }

    public static boolean isEmpty() {
        return InterfaceRuleMap.isEmpty();
    }

    public static void putAll(Map<? extends String, ? extends InterfaceRule> m) {
        InterfaceRuleMap.putAll(m);
    }

    public static Collection<InterfaceRule> values() {
        return InterfaceRuleMap.values();
    }

    public static boolean containsKey(Object key) {
        return InterfaceRuleMap.containsKey(key);
    }

    public static InterfaceRule get(Object key) {
        return InterfaceRuleMap.get(key);
    }

    public static Set<String> keySet() {
        return InterfaceRuleMap.keySet();
    }

    public static boolean containsValue(Object value) {
        return InterfaceRuleMap.containsValue(value);
    }

    public static Set<Map.Entry<String, InterfaceRule>> entrySet() {
        return InterfaceRuleMap.entrySet();
    }

    public static InterfaceRule put(String key, InterfaceRule value) {
        return InterfaceRuleMap.put(key, value);
    }

    public static void clear() {
        InterfaceRuleMap.clear();
    }

    public static InterfaceRule remove(Object key) {
        return InterfaceRuleMap.remove(key);
    }
}
