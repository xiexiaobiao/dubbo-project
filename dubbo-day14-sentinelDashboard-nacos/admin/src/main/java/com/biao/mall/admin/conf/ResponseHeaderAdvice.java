package com.biao.mall.admin.conf;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname ResponseHeaderAdvice
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-06 17:37
 * @Version 1.0
 **/
@ControllerAdvice
public class ResponseHeaderAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        ServletServerHttpResponse serverHttpResponse = (ServletServerHttpResponse) response;
        if (serverHttpRequest == null || serverHttpResponse == null
                || serverHttpRequest.getServletRequest() == null || serverHttpResponse.getServletResponse() == null){
            return body;
        }

        //对于未添加跨域消息头的响应进行处理
        HttpServletRequest request1 = serverHttpRequest.getServletRequest();
        HttpServletResponse response1 = serverHttpResponse.getServletResponse();
        String originHeader = "Access-Control-Allow-Origin";
        if ( !response1.containsHeader(originHeader)){
            String origin = request1.getHeader("Origin");
            if (origin == null){
                String referer = request1.getHeader("Referer");
                if (referer !=null){
                    origin = referer.substring(0,referer.indexOf("/",7));
                }
            }
            response1.setHeader("Access-Control-Allow-Origin",origin);
        }

        String allowHeaders = "Access-Control-Allow-Headers";
        if ( response1.containsHeader(allowHeaders)){
            response1.setHeader(allowHeaders,request1.getHeader(allowHeaders));
        }

        String allowMethods = "Access-Control-Allow-Methods";
        if ( response1.containsHeader(allowMethods)){
            response1.setHeader(allowMethods,"GET,POST,OPTIONS,HEAD");
        }

        String exposeHeaders = "access-control-expose-headers";
        if ( response1.containsHeader(exposeHeaders)){
            response1.setHeader(exposeHeaders,"x-auth-token");
        }

        return body;
    }
}
