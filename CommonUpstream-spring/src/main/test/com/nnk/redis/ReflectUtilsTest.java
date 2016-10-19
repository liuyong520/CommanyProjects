package com.nnk.redis;

import com.nnk.upstream.util.ReflectUtils;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/6
 * Time: 16:02
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ReflectUtilsTest {
    @Test
    public void testReflect(){
        ReflectUtils.invokeMethodName(this,"say",new Class[]{String.class,String.class},"hello","name");
    }
    public void say(String str,String name){
        System.out.println(str + name);
    }

    @Test
    public void replaceFisrst(){
        String s = "aabc";

        s = s.replace("a","d");

        System.out.println(s);
    }
}
