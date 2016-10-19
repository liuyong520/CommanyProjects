package com.nnk.upstream.filter;

import com.nnk.upstream.entity.proxy.MyHttpServletRequestWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/12
 * Time: 9:35
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@WebFilter(filterName = "HttpReplaceFilter",urlPatterns="/*")
public class HttpReplaceFilter implements Filter {
    private Logger logger = Logger.getLogger(HttpReplaceFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;

        if(request instanceof HttpServletRequest) {
            requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if(null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
