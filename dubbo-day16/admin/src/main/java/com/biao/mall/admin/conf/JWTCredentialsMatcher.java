package com.biao.mall.admin.conf;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.biao.mall.common.dto.UserDto;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname JWTCredentialsMatcher
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-07 12:53
 * @Version 1.0
 **/
public class JWTCredentialsMatcher implements CredentialsMatcher {
    private final Logger logger = LoggerFactory.getLogger(JWTCredentialsMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //token为JWTShiroRealm接收处理的JWT
        /**注意这里的getCredentials()为com.biao.mall.admin.dto.JWTToken中重写的方法*/
        String tokenStr = (String) token.getCredentials();
        //这里的info即为com.biao.mall.admin.conf.JWTShiroRealm中doGetAuthenticationInfo返回对象
        Object stored = info.getCredentials();
        String salt = stored.toString();//对比jwt的盐

        UserDto userDto = (UserDto) info.getPrincipals().getPrimaryPrincipal();
        try{
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",userDto.getUsername())
                    .build();
            verifier.verify(tokenStr);
            logger.debug("matcher ok: {}",tokenStr);
            return true;
        }catch (JWTVerificationException e){
            logger.error("Token Error:{}", e.getMessage());
        }
        return false;
    }
}
