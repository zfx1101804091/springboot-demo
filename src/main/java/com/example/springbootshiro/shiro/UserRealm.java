package com.example.springbootshiro.shiro;

import com.example.springbootshiro.ctmp_1.domain.User;
import com.example.springbootshiro.ctmp_1.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    
    @Autowired
    private UserService userService;
    
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //添加资源的授权字符串---即shiroconfig类中的perms中的值filterMap.put("/usertest/add","perms[user:add]");
        //authorizationInfo.addStringPermission("user:add");
        
        //到数据库查询当前登陆用户的授权字符串（改造成动态的）
        //获取当前登陆用户
        Subject subject = SecurityUtils.getSubject();
        // return new SimpleAuthenticationInfo(user,user.getPassword(),"");----认证逻辑传过来当前用户
        User user = (User) subject.getPrincipal();
        //通过当前用户id查到数据库该用户列表
        User dbUser = userService.findById(user.getId());
        //进行授权
        authorizationInfo.addStringPermission(dbUser.getPerms());
        

        return authorizationInfo;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        System.out.println("执行认证逻辑");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userService.findByName(token.getUsername());

        //1.去查数据库看用户是否存在--判断用户名
        if(user==null){
            //用户名不存在
            return null;//shiro底层会抛出UnknownAccountException
        }
        //2.判断密码(第1，3参数是返回给subject.login(token);)
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
        
      
    }
}
