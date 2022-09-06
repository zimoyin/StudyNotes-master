package com.zimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VarController {

//    @RequestMapping("/name")
//    public String name(String name, Model model){
//        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
//        model.addAttribute("msg",name);
//        System.out.println(name);
//        return "hello";//   WEB-INF/jsp/hello.jsp
//    }

    @RequestMapping("/name")
    public String name(@RequestParam("username") String name, Model model){
        //向模型中添加属性msg与值，可以在JSP页面中取出并渲染
        model.addAttribute("msg",name);
        System.out.println(name);
        return "hello";//   WEB-INF/jsp/hello.jsp
    }
}
