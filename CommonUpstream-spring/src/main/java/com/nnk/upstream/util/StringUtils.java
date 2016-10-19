package com.nnk.upstream.util;

import com.nnk.interfacetemplate.common.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/26
 * Time: 12:57
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {
    /**
     * ½ØÈ¡×Ö·û´®ÏÔÊ¾
     * @param str
     * @param end
     * @return string
     */
    public static String subString(String str,int end){

        if(StringUtil.isEmpty(str)||str.length()<="https://".length()){
            return str;
        }else {
            String substr = "";
            if(str.startsWith("http://")){
                str = str.replace("http://","");
            }else if(str.startsWith("https://")){
                str = str.replace("https://","");
            }else if(str.startsWith("<")){
                substr = "ÒÑÅäÖÃ";
                return substr;
            }
            if(end>str.length()){
                substr = str.substring(0, str.length());
                return substr;
            }else {
                substr = str.substring(0, end);
            }
            return substr.concat("...");

        }
    }

    /**
     *
     * @param src to be split source string
     * @param split split char or string
     * @return List of string
     */
    public static List splitToList(String src, String split){
        if(src==null||"".equals(src))
            return  null;
        if(split==null)
            return Arrays.asList(new String[]{src});
        String[] array = src.split(split);
        return Arrays.asList(array);
    }
}
