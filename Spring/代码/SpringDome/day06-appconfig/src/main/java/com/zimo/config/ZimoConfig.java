package com.zimo.config;

import com.zimo.pojo.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//注册到Spring容器中，因为他本身就是一个@Component.
//@Configuration 代表他是一个配置类
@Configuration
//要被扫描的包
@ComponentScan("com.zimo.pojo")
//引入另一个config类
@Import(ZimoConfig2.class)
public class ZimoConfig {
    //注册一个Bean
    //这个方法名字相当于bean标签中的id
    //返回值相当于，bean标签中的class属性
    @Bean
    public User getUser(){
        return  new User();
    }
}
