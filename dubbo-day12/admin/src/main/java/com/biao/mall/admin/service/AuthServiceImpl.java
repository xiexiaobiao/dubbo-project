package com.biao.mall.admin.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.biao.mall.admin.dto.JWTToken;
import com.biao.mall.admin.dto.UserDto;
import com.biao.mall.common.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Classname AuthService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-08 10:05
 * @Version 1.0
 **/
@Service(version = "1.0.0",group = "Auth")
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private UserService userService;

    public AuthServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public String loginAuth(UserDto loginInfo) {
        Subject subject = SecurityUtils.getSubject();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(),loginInfo.getPassword());
            subject.login(token);
            UserDto userDto = (UserDto) subject.getPrincipals().getPrimaryPrincipal();
            String newToken = userService.generateJwtToken(userDto.getUsername());
            return newToken;
        } catch (AuthenticationException e) {
            logger.error("User {} loginAuth fail, Reason:{}", loginInfo.getUsername(), e.getMessage());
        } catch (Exception e) {
            logger.error("User {} loginAuth fail, Reason:{}", loginInfo.getUsername(), e.getMessage());
        }
        return null;
    }

}
