package com.example.springbootshiro.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        //添加shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *      常用的过滤器：
         *          anon：无需认证（登陆）可以访问
         *          authc：必须认证才可以访问
         *          user：如果使用rememberMe的功能可以直接访问
         *          perms：该资源必须得到资源权限才可以访问
         *          role：该资源必须得到角色权限才可以访问
         */

        Map<String, String> filterMap = new LinkedHashMap<>();
        /*filterMap.put("/add","authc");
        filterMap.put("/update","authc");
        filterMap.put("/usertest/test1","anon");
        filterMap.put("/usertest/test2","anon");*/
        filterMap.put("/usertest/test","anon");
        //filterMap.put("/usertest/*","authc");//设置映射路径为/usertest/下的都必须登陆才可以访问

        //放行loginshiro.html
        filterMap.put("/usertest/login","anon");
        
        //授权过滤器
        //这里有个注意点：当前授权拦截后，shiro会自动跳转到未授权页面(当为动态授权码时，需与数据库保持一致)
        filterMap.put("/usertest/add","perms[user:add]");
        filterMap.put("/usertest/update","perms[user:update]");
        
        filterMap.put("/usertest/*","authc");//最大的认证放最后面
        
        //修改调整的登陆界面（因为authc下，默认跳转到login.jsp）
        shiroFilterFactoryBean.setLoginUrl("/usertest/toLogin");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/usertest/unAuth");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @param userRealm
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean("userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
    
    /*
     * 功能描述: <br>
     * 〈配置ShiroDialect，用于thymeleaf和shiro标签配合使用，可在页面上使用shiro标签〉
     * @Param: []
     * @Return: at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     * @Author: Administrator
     * @Date: 2019/8/5 0005 23:44
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
