package com.biao.mall.common.conf;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname MyWebFilter
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-12 21:59
 * @Version 1.0
 **/
//使用@WebFilter注解方式注入，省去了写configuration类进行注入，更为方便
@WebFilter(filterName = "webFilter",urlPatterns = "/*")
public class MyWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        switch (httpServletResponse.getStatus()) {
            case 200:
            {break;}
            case 500:
            {break;}
            case 404:
            {break;}
            default:
            {}
        }
        chain.doFilter(httpServletRequest,httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
