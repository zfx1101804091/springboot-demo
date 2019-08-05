package com.example.springbootshiro.ctmp_1.controller;

import com.example.springbootshiro.ctmp_1.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("usertest")
public class Usercontroller {
    
    @Autowired
    private UserService userService;
    


    /**
     * 测试thymeleaf
     * @param model
     * @return
     */
    @RequestMapping("/test")
    public String testThymeleaf(Model model){
        //把数据存入model
        model.addAttribute("name","郑泽凯");
        model.addAttribute("age","24");
        model.addAttribute("sex","男");
        //返回一个测试页面test.html
        return "test";
    }
    
    @RequestMapping("add")
    public String add(){
       
        return "/user/add";
    }
    @RequestMapping("update")
    public String update(){
        
        return "/user/update";
    }
    @RequestMapping("toLogin")
    public String toLogin(){

        return "loginshiro";
    }

    @RequestMapping("unAuth")
    public String unAuth(){

        return "/user/noAuth";
    }
    
    /*
     * 功能描述: <br>
     * 〈登陆逻辑处理〉
     * @Param: [name, password, model]
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2019/8/4 0004 22:36
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(String name,String password,Model model){
        //使用shiro编写认证操作
        
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //3.执行登陆方法
        try {
            subject.login(token);//会直接执行UserRealm里的认证逻辑
            //登陆成功
            return "redirect:/usertest/add";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //这个异常是登陆失败：用户名不存在
            model.addAttribute("msg","用户名不存在");//给一个提示
            //return "redirect:/usertest/toLogin";//重定向不能把消息带到页面，所以还是直接跳页面
            return "loginshiro";
        }catch (IncorrectCredentialsException e){
            //这个异常是登陆失败：密码错误
            model.addAttribute("msg","密码错误");
            return "loginshiro";
        }/*catch (AuthenticationException e) {
            model.addAttribute("msg","其他的登录错误");
            return "loginshiro";
//            return new ModelAndView("/admin/login",map);
        }*/

        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping("/test2")
    @ResponseBody
    public String test2(HttpServletRequest request){

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        int flag = 0;
        try {
            flag = userService.findUser(username,password);
            if(flag==1){
                return "恭喜，认证成功！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }

        return "抱歉，认证失败，请重新提交用户名和密码";
    }
}
