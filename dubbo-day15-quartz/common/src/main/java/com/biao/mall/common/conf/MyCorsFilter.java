package com.biao.mall.common.conf;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname MyCorsFilter
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-12 21:54
 * @Version 1.0
 **/

//使用@WebFilter注解方式注入，省去了写configuration类进行注入，更为方便
@WebFilter(filterName = "corsFilter",urlPatterns = "/*")
public class MyCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**1，跨域 实际上springboot JDK1.8以上中，只需要在controller上加注解 @CrossOrigin即可解决跨域
    * 2.使用 interceptor 也可以
     * 3，使用 CorsFilter，也ok*/
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        chain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
