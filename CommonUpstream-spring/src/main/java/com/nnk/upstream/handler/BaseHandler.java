package com.nnk.upstream.handler;

import com.nnk.upstream.anotation.MethodAttribute;
import com.nnk.upstream.util.SpringContextUtils;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/1
 * Time: 11:35
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

public class BaseHandler {
    private static final Logger LOGGER = Logger.getLogger(BaseHandler.class);

    public void handler(Request request) {
        MethodAttribute methodAttribute = null;
        try {
            Object object = SpringContextUtils.getBean(request.getCmdName());
            Class<?> clazz = object.getClass();
            methodAttribute = clazz.getAnnotation(MethodAttribute.class);
            Method method = clazz.getMethod(methodAttribute.methodName(), Request.class);
            if (method != null) {
                method.invoke(object, request);
            } else {
                LOGGER.error(request.getCmdName() + "Э�鲻֧�ִ���");
            }
        } catch (NoSuchMethodException e) {
            LOGGER.error(methodAttribute.methodName() + "����������");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            LOGGER.error(request.getCmdName() + "Э�鲻֧�ִ���");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
