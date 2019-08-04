package com.example.springbootshiro.shiro;

import com.example.springbootshiro.ctmp_1.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UserRealm extends AuthorizingRealm {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        return null;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        char[] password = token.getPassword();

        Map<String,String> map = userMapper.findUser(username, String.valueOf(password));

       

        //1.去查数据库看用户是否存在--判断用户名
        if(!map.get("name").equals(username)){
            //用户名不存在
            return null;//shiro底层会抛出UnknownAccountException
        }
        //2.判断密码(第1，3参数是返回给subject.login(token);)
        return new SimpleAuthenticationInfo("",map.get("password"),"");
    }
}
