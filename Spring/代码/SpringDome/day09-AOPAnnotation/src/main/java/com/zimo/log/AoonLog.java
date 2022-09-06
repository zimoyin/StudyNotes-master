package com.zimo.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
@Aspect
public class AoonLog {
    final  String execution="execution(* com.zimo.service.UserServerImp.*(..))";
    //目标方法执行前执行
    //注解里面的表达式必须是常亮
    @Before(execution)
    public void before(){
        System.out.println("【LOG】   Before执行");
    }

    //目标方法执行后执行
    //注解里面的表达式必须是常亮
    @After(execution)
    public void after(){
        System.out.println("【LOG】   after执行");
    }

    //环绕
    @Around(execution)
    public void Around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("【LOG】   环绕前");

        jp.getSignature();//获得签名(这个方法的信息)
        jp.getSourceLocation();//获得资源的位置
        jp.getTarget();//获得目标对象
        jp.getThis();//获取代理对象本身
        jp.toLongString();//获得这个方法的信息
        jp.getArgs();//获取连接点方法运行时的入参列表


        //执行方法,使用这个注解之后必须执行写这条语句，否则目标方法不会执行
        Object proceed = jp.proceed();




        System.out.println("【LOG】   环绕后");
    }
}
