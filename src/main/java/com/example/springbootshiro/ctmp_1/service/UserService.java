package com.example.springbootshiro.ctmp_1.service;

import org.springframework.stereotype.Service;

public interface UserService {
    
    int findUser(String username, String password);
}
