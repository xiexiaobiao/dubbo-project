package com.biao.mall.admin.conf;

import com.biao.mall.admin.dto.JWTToken;
import com.biao.mall.common.dto.UserDto;
import com.biao.mall.admin.service.UserService;
import com.biao.mall.admin.util.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Classname JWTShiroRealm  自定义身份认证
 *  * 基于HMAC（ 散列消息认证码）的控制域
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 22:48
 * @Version 1.0
 **/
public class JWTShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(JWTShiroRealm.class);
    private UserService userService;

    public JWTShiroRealm(UserService userService) {
        this.userService = userService;
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token){
        logger.debug("token instanceof JWTToken >> {}", (token instanceof JWTToken));
        return (token instanceof JWTToken);
    }

    //首次登录已经处理权限角色，故这里不需处理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    //
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) token;
        String tokenStr = jwtToken.getToken();
        UserDto userDto = userService.getJwtToken(JwtUtils.getUsername(tokenStr));
        if (userDto == null){
            throw new  AuthenticationException("token expired ,please login");
        }
        return new SimpleAuthenticationInfo(userDto,userDto.getSalt(),"jwtRealm");
    }
}
