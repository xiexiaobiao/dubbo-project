package com.biao.mall.admin.filter;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname AnyRolesAuthorizationFilter
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 22:38
 * @Version 1.0
 **/
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response){
        request.setAttribute("anyRolesAuthFilter.FILTERED", true);
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Boolean afterFiltered = (Boolean) request.getAttribute("anyRolesAuthFilter.FILTERED");
        if (BooleanUtils.isTrue(afterFiltered)){
            return true;
        }
        Subject subject = getSubject(request,response);
        String[] rolesArray = (String[]) mappedValue;
        //没有角色限制，有权限访问
        if (rolesArray == null || rolesArray.length == 0 ){
            return true;
        }
        for (String role : rolesArray
             ) {
            if (subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        return false;
    }
}
