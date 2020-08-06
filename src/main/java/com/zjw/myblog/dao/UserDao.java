package com.zjw.myblog.dao;

import com.zjw.myblog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User , Long> {

    User getByUsernameAndPassword(String username , String password);

}
