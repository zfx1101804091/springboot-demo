package com.example.springbootshiro.ctmp_1.mapper;

import com.example.springbootshiro.ctmp_1.domain.User;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.Map;

@Resource
public interface UserMapper {

    Map<String,String> findUser(@Param("username")String username, @Param("password") String password);
    
    int insertUser1(Map<String,String> map);

    String insert(@Param("jsonObject") String jsonObject);

    int findUser1(String username, String password);

    User findByName(String username);

    User findById(Integer id);
}
