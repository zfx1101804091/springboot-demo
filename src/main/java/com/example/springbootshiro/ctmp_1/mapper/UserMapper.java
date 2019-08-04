package com.example.springbootshiro.ctmp_1.mapper;

import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface UserMapper {

    int findUser(@Param("username")String username, @Param("password") String password);
    
    int insertUser1(Map<String,String> map);

    String insert(@Param("jsonObject") String jsonObject);
}
