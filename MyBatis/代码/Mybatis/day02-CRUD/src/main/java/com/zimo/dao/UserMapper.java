package com.zimo.dao;

import com.zimo.pojo.User;

import java.util.List;
import java.util.Map;

//mapper
public interface UserMapper {
    //里面存储User对象相当于数据库中的每一条记录
    //查询所有用户
    List<User> getUserList();

    //模糊查询用户
    List<User> getLikeUseName(String value);

    //根据ID查询用户
    User getUserByID(int id);


    //插入一个用户
    int addUser(User user);

    //更新一条记录
    int updateUser(User user);

    //删除一个用户
    int deleteUser(int id);

    //通过map插入对象
    int  addUserMap(Map<String,Object> map);

}
