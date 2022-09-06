package com.zimo.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//注册类到Spring容器中
@Component
public class User {
    private String name;

    public String getName() {
        return name;
    }

    @Value("子墨")//注入值
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
