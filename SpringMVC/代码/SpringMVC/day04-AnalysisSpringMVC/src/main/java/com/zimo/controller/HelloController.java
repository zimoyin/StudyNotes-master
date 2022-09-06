package com.zimo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    //原版风格
    // 接收前端的参数，并返回结果
    @RequestMapping("/add")
    public String hello(int a,int b,Model model){
        int r = a+b;

        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg","结果为： "+r);

        return "hello";//   WEB-INF/jsp/hello.jsp
    }

    //RestFul风格
    // 接收前端的参数，并返回结果
    @RequestMapping("/add2/{a}/{b}")
    public String hello2(@PathVariable int a,@PathVariable int b, Model model){

        int r = a+b;

        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg","结果为： "+r);

        return "hello";//   WEB-INF/jsp/hello.jsp
    }
}
