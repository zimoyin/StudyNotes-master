package com.zimo.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//等价于相当于配置文件中 <bean id="user" class="当前注解的类"/>
//组件  @Component
@Component
//设置对象作用域
@Scope("prototype")
public class User {
    //注入值
    @Value("子墨")
    public String name;
}
