package com.biao.mall.admin.conf;

import com.biao.mall.admin.dto.UserDto;
import com.biao.mall.admin.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Classname DbShiroRealm
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 22:48
 * @Version 1.0
 **/
public class DbShiroRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(JWTShiroRealm.class);
    //生产环境盐值不可硬编码在代码中，注意与前面设置的一致
    private static final String encrySalt = "510fdb7f28534fb584af25697826c203";//对比登录信息的salt
    private UserService userService;

    public DbShiroRealm(UserService userService) {
        this.userService = userService;
        this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
    }

    @Override
    public boolean supports(AuthenticationToken token){
        logger.info(">>DbShiroRealm.supports");
        return token instanceof UsernamePasswordToken;
    }

    /**权限*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取主身份标识
        UserDto userDto = (UserDto) principals.getPrimaryPrincipal();
        //获取权限角色
        List<String> roles = userDto.getRoles();
        if (roles == null){
            roles = userService.getUserRoles(userDto.getUserId());
            userDto.setRoles(roles);
        }
        if (roles != null){
            simpleAuthorizationInfo.addRoles(roles);
        }
        return simpleAuthorizationInfo;
    }

    /**认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        UserDto userDto = userService.getUserInfo(userName);
        if (userDto == null){
            throw new AuthenticationException("userName or pwd error!");
        }
        return new SimpleAuthenticationInfo(userDto,userDto.getEncryptPwd(), ByteSource.Util.bytes(encrySalt),"dbRealm");
    }
}
