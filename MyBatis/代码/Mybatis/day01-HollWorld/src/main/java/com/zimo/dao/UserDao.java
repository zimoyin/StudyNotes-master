package com.zimo.dao;

import com.zimo.pojo.User;

import java.util.List;

//mapper
public interface UserDao {
    //里面存储User对象相当于数据库中的每一条记录
    List<User> getUserList();
}
