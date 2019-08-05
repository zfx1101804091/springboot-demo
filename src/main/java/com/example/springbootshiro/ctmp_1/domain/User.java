package com.example.springbootshiro.ctmp_1.domain;

import java.util.Date;

/**
 * @description:
 * @author: zheng-fx
 * @time: 2019/8/5 0005 20:20
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String perms;

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    private Date edit_time;

    public Date getEdit_time() {
        return edit_time;
    }

   
    public void setEdit_time(Date edit_time) {
        this.edit_time = edit_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", perms='" + perms + '\'' +
                ", edit_time=" + edit_time +
                '}';
    }

}
