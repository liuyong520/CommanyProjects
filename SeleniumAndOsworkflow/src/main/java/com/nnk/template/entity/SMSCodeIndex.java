package com.nnk.template.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/18
 * Time: 9:01
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
/**
 *
 sz���������д� 2016/8/10 10:16:37
 {"fullShortMsg":"�����������ã����˴ε�¼��֤��Ϊ555647",
 "myPhone":"15219375376",
 "shortMsgPartFive":"",
 "shortMsgPartFour":"",
 "shortMsgPartOne":"�����������ã����˴ε�¼��֤��Ϊ",
 "shortMsgPartThree":"",
 "shortMsgPartTwo":"555647",
 "sourcePhone":"10658338"}
 ��η��������base64����ģ�
 */
public class SMSCodeIndex {
    public static final HashMap<String,String> indexMap = new HashMap<String, String>();

    /**
     *  public String getFullShortMsg() {
     return fullShortMsg;
     }

     public String getMyPhone() {
     return myPhone;
     }

     public String getShortMsgPartFive() {
     return shortMsgPartFive;
     }

     public String getShortMsgPartFour() {
     return shortMsgPartFour;
     }

     public String getShortMsgPartOne() {
     return shortMsgPartOne;
     }

     public String getShortMsgPartThree() {
     return shortMsgPartThree;
     }

     public String getShortMsgPartTwo() {
     return shortMsgPartTwo;
     }

     public String getSourcePhone() {
     return sourcePhone;
     }
     */
    static {
        indexMap.put("1","getFullShortMsg");
        indexMap.put("2","getMyPhone");
        indexMap.put("3","getSourcePhone");
        indexMap.put("4","getShortMsgPartOne");
        indexMap.put("5","getShortMsgPartTwo");
        indexMap.put("6","getShortMsgPartThree");
        indexMap.put("7","getShortMsgPartFour");
        indexMap.put("8","getShortMsgPartFive");
    }

    public static int size() {
        return indexMap.size();
    }

    public static boolean containsValue(String value) {
        return indexMap.containsValue(value);
    }

    public static boolean containsKey(String key) {
        return indexMap.containsKey(key);
    }

    public static Set<String> keySet() {
        return indexMap.keySet();
    }

    public static Collection<String> values() {
        return indexMap.values();
    }

    public static boolean isEmpty() {
        return indexMap.isEmpty();
    }

    public static Set<Map.Entry<String, String>> entrySet() {
        return indexMap.entrySet();
    }

    public static String get(String key) {
        return indexMap.get(key);
    }

    public static void putAll(Map<? extends String, ? extends String> m) {
        indexMap.putAll(m);
    }

    public static String put(String key, String value) {
        return indexMap.put(key, value);
    }

    public static void clear() {
        indexMap.clear();
    }

    public static String remove(String key) {
        return indexMap.remove(key);
    }
}
