package com.nnk.upstream.anotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/1
 * Time: 11:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAttribute {
    public String methodName() default "*";
//    public String
}
