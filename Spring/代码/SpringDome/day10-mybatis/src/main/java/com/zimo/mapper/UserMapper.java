package com.zimo.mapper;

import com.zimo.pojo.User;
import java.util.List;


//mapper
public interface UserMapper {
    List<User> getUserList();

    int add(User user);
}
