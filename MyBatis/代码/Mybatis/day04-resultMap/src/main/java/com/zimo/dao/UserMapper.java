package com.zimo.dao;


import com.zimo.pojo.User;

import java.util.List;
import java.util.Map;

//mapper
public interface UserMapper {


    //根据ID查用户
    User getUserByID(int id);

    User getUserMapByID(int id);

}
