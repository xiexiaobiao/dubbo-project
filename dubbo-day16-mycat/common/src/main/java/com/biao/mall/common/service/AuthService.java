package com.biao.mall.common.service;

import com.biao.mall.common.dto.UserDto;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Classname AuthService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-08 10:07
 * @Version 1.0
 **/
public interface AuthService {

    String loginAuth(UserDto loginInfo);

}
