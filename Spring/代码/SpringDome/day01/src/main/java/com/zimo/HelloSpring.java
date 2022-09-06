package com.zimo;

import java.io.File;

public class HelloSpring {
    private String str;

    HelloSpring(String name){
        setStr(name);
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "Spring： "+getStr();
    }
}
