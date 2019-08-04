package com.example.springbootshiro.ctmp_1.controller;

import com.example.springbootshiro.ctmp_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("usertest")
public class Usercontroller {
    
    @Autowired
    private UserService userService;

    /**
     * 测试
     * @return
     */
    @RequestMapping("/he")
    @ResponseBody
    public String hello(){
        System.out.println("hello");
        return "ok";
    }


    /**
     * 测试thymeleaf
     * @param model
     * @return
     */
    @RequestMapping("/test1")
    public String testThymeleaf(Model model){
        //把数据存入model
        model.addAttribute("name","传智播客");
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

        return "index";
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
