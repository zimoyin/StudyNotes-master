package com.zimo.dao;


import com.zimo.pojo.User;

import java.util.List;
import java.util.Map;

//mapper
public interface UserMapper {


    //Limit分页查询
    List<User> getLimit(Map<String,Object> map);


    //RowBounds分页查询
    List<User> getRowBounds(Map<String,Object> map);

}
