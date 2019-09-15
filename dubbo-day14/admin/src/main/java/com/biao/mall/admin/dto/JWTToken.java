package com.biao.mall.admin.dto;

import lombok.Data;
import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @Classname JWTToken
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 21:02
 * @Version 1.0
 **/
//@Data
public class JWTToken implements HostAuthenticationToken {
    private static final long serialVersionUID  = 8765431346463134621L;

    private String token;
    private String host;

    public JWTToken(String token,String host){
        this.token = token;
        this.host = host;
    }

    public JWTToken(String token){
        //借用全变量构造函数
        this(token,null);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToken() {
        return this.token;
    }

    public String getHost() {
        return this.host;
    }

    /**注意这里的重写方法，后续使用中，以此处返回值为准*/
    @Override
    public Object getPrincipal() {
        return this.token;
    }

    /**注意这里的重写方法，后续使用中，以此处返回值为准*/
    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public String toString(){
        return token + ':' + host;
    }
}
