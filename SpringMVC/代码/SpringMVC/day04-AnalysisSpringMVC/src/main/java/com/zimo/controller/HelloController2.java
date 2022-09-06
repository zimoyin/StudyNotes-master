package com.zimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/c2")
public class HelloController2 {

//    @RequestMapping(value = "/add/{a}/{b}",method = RequestMethod.GET)
    @GetMapping("/add/{a}/{b}")
    public String add(@PathVariable int a, @PathVariable int b, Model model){
        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg","通过get方法访问成功!"+" 结果为: "+a+b);
        return "hello";
    }

    @PostMapping("/add/{a}/{b}")
    public String add2(@PathVariable int a, @PathVariable int b, Model model){
        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg","通过post方法访问成功!"+" 结果为: "+a+b);
        return "hello";
    }



}
