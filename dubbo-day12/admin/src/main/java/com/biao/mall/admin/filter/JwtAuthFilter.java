package com.biao.mall.admin.filter;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;
import com.biao.mall.admin.dto.JWTToken;
import com.biao.mall.admin.dto.UserDto;
import com.biao.mall.admin.service.UserService;
import com.biao.mall.admin.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Classname JwtAuthFilter
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 20:54
 * @Version 1.0
 **/
@Slf4j
public class JwtAuthFilter extends AuthenticatingFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final int tokenRefreshInterval = 300;
    private UserService userService;

    public JwtAuthFilter(UserService userService){
        this.userService = userService;
        this.setLoginUrl("/login");
    }

    @Override
    protected boolean preHandle(ServletRequest request,ServletResponse response) throws Exception {
        logger.info("JwtAuthFilter.preHandle");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //对于OPTION请求做拦截，不做token校验
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            return false;
        }
        return super.preHandle(request,response);
    }

    /**/
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("JwtAuthFilter.createToken");
        String jwtToken = this.getAuthzHeader(request);
        if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken)){
            return new JWTToken(jwtToken);
        }
        return null;
    }

    private String getAuthzHeader(ServletRequest request) {
        logger.info("JwtAuthFilter.getAuthzHeader");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String header = httpServletRequest.getHeader("x-auth-token");
        return StringUtils.remove(header,"Bearer");
    }

    //cors 跨域设置
    private void fillCorsHeader(HttpServletRequest toHttp, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin",toHttp.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",toHttp.getHeader("Access-Control-Request-Headers"));
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request,ServletResponse response,Object mappedValue){
        logger.info(">>JwtAuthFilter.isAccessAllowed");
        if (this.isLoginRequest(request,response)){
            return true;
        }
        Boolean afterFiltered = (Boolean) request.getAttribute("anyRolesAuthFilter.FILTERED");
        if (BooleanUtils.isTrue(afterFiltered)){
            return true;
        }
        boolean allowed = false;
        try{
            allowed = executeLogin(request,response);
        }catch (IllegalStateException e){
            logger.error("Not found any token");
        }catch (Exception e){
            logger.error("Error occurs when login",e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    //isAccessAllowed返回 false进入此方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        this.fillCorsHeader(WebUtils.toHttp(request),httpServletResponse);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response){
        logger.error("Validate token fail, token:{}, error:{}",token.toString(),e.getMessage());
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response){
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JWTToken){
            JWTToken jwtToken = (JWTToken) token;
            UserDto userDto = (UserDto) subject.getPrincipals().getPrimaryPrincipal();
            boolean shouldRefresh = this.shouldTokenRefresh(JwtUtils.getIssueAt(jwtToken.getToken()));
            if (shouldRefresh){
                newToken = userService.generateJwtToken(userDto.getUsername());
            }
        }
        if (StringUtils.isNotBlank(newToken)){
            httpServletResponse.setHeader("x-auth-token",newToken);
        }
        return true;
    }

    private boolean shouldTokenRefresh(LocalDateTime issueAt) {
//        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueAt);
    }

    @Override
    protected void postHandle(ServletRequest request,ServletResponse response){
        this.fillCorsHeader(WebUtils.toHttp(request),WebUtils.toHttp(response));
        request.setAttribute("jwtShiroFilter.FILTERED", true);
    }

}
