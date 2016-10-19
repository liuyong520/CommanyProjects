package com.nnk.upstream.convert;


import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.utils.http.interfaces.HttpData;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/8
 * Time: 11:59
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public interface ProtoclConverter {

    public HttpData toRequest(UpstreamSession session);

}
