package com.nnk.upstream.handler;

import com.nnk.upstream.entity.self.UpstreamSession;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/1
 * Time: 13:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public interface Handler {

    public void handlerRequest(UpstreamSession session);
}
