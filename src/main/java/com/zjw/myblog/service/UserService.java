package com.zjw.myblog.service;

import com.zjw.myblog.pojo.User;

public interface UserService {

    User checkUser(String username , String password);

}
