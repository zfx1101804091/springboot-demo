package com.example.springbootshiro.ctmp_1.service;


import com.example.springbootshiro.ctmp_1.domain.User;

public interface UserService {
    
    int findUser(String username, String password);

    User findByName(String username);
    
    User findById(Integer id);
}
