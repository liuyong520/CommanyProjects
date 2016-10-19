package com.nnk.upstream.exception;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/12
 * Time: 10:24
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class NNKSQLException extends Exception{

    private static final long serialVersionUID = 7116291635407812769L;

    public NNKSQLException() {
    }

    public NNKSQLException(String message) {
        super(message);
    }

    public NNKSQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
