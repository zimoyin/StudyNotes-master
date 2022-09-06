package com.zimo.utils;

import java.util.UUID;

public class IDUtils {
//    获取一个随机的ID
    public static String getID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
