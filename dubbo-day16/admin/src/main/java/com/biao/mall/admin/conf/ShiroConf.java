package com.biao.mall.admin.conf;

import com.biao.mall.admin.filter.AnyRolesAuthorizationFilter;
import com.biao.mall.admin.filter.JwtAuthFilter;
import com.biao.mall.admin.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.*;

/**
 * @Classname ShiroConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 21:47
 * @Version 1.0
 **/
@Configuration
public class ShiroConf {

    /**注册shiro的Filter 拦截请求*/
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager, UserService userService) throws Exception {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter((Filter) Objects.requireNonNull(this.shiroFilter(securityManager, userService).getObject()));
        filterRegistrationBean.addInitParameter("targetFilterLifecycle","true");
        //bean注入开启异步方式
        filterRegistrationBean.setAsyncSupported(true);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistrationBean;
    }

    /**设置过滤器，将自定义的Filter加入*/
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserService userService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //必需属性，指定一个SecurityManager的实例，
        factoryBean.setSecurityManager(securityManager);
        Map<String,Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken",this.createAuthFilter(userService));
        filterMap.put("anyRole",this.createRolesFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(this.shiroFilterChainDefinition().getFilterChainMap());
        return  factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/image/**", "anon");
        //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken");
        return chainDefinition;
    }

    /**注意不要加@Bean注解，不然spring会自动注册成filter*/
    private AnyRolesAuthorizationFilter createRolesFilter() {
        return new AnyRolesAuthorizationFilter();
    }

    /**注意不要加@Bean注解，不然spring会自动注册成filter*/
    private JwtAuthFilter createAuthFilter(UserService userService) {
        return new JwtAuthFilter(userService);
    }

    /**初始化authenticator*/
    @Bean
    public Authenticator authenticator(UserService userService){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(this.jwtShiroRealm(userService),this.dbShiroRealm(userService)));
        //如果有多个Realms才需要指定realm匹配策略
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**DB认证的realm*/
    @Bean("dbRealm")
    public Realm dbShiroRealm(UserService userService){
        DbShiroRealm dbShiroRealm = new DbShiroRealm(userService);
        return dbShiroRealm;
    }

    /**JWT 认证的realm*/
    @Bean("jwtRealm")
    public Realm jwtShiroRealm(UserService userService) {
        JWTShiroRealm  myShiroRealm = new JWTShiroRealm(userService);
        return  myShiroRealm;
    }

    /**禁用session，不保存用户状态，每次请求都重新认证，
     * 要完全禁用session，需使用下面的filter来实现*/
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

}
