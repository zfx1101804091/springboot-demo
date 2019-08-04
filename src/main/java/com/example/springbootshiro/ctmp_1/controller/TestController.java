package com.example.springbootshiro.ctmp_1.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.springbootshiro.ctmp_1.mapper.UserMapper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {
    
    private static Logger logger = Logger.getLogger(TestController.class);
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 重定向实例
     * @return
     */
    @RequestMapping("/ceshi")
    public String ceshi(){
        
        return "redirect:/usertest/toLogin";
    } 
    
    @RequestMapping("/cs")
    public String cs(){
        
        return "login1";
    }

  
    @RequestMapping("/insertUser1")
    @ResponseBody
    public String insertUser1(HttpServletRequest request){

        
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String Email_a = request.getParameter("Email_a");
        String Administrator_a = request.getParameter("Administrator_a");
        String k_time = getStringDate();
        String k_state = "1";
        

        HashMap<String, String> map = new HashMap<>();
        map.put("name",name);
        map.put("password",password);
        map.put("phone",phone);
        map.put("Email_a",Email_a);
        map.put("Administrator_a",Administrator_a);

        logger.error("map的json化----"+ JSONUtils.toJSONString(map));
        int num = 0;
        try {
            num = userMapper.insertUser1(map);
            if (num==1){
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "";
    }


    @RequestMapping("/insertUser")
    @ResponseBody
    public String insertUser(HttpServletRequest request){


        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String Email_a = request.getParameter("Email_a");
        String Administrator_a = request.getParameter("Administrator_a");
     

        Map<String, String> map = new HashMap<>();
        map.put("name",name);
        map.put("password",password);
        map.put("phone",phone);
        map.put("Email_a",Email_a);
        map.put("Administrator_a",Administrator_a);

        logger.debug("封装的数据---"+map.toString());
        

        JSONObject jsonObject = JSONObject.fromObject(map);

        logger.debug("转换的json数据---"+jsonObject);

        String json = null;
        
        try {
            json = userMapper.insert(jsonObject.toString());
            logger.debug("数据库返回的数据---"+json);
        } catch (Exception e) {
            e.printStackTrace();
        }
          
        return json;
    }
    
    
    

    /**
     * 获取当前时间
     * 返回字符串格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getStringDate(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
