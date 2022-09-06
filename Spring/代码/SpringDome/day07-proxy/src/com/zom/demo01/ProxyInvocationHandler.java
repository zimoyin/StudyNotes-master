package com.zom.demo01;

import java.lang.reflect.InvocationHandler;//调用处理程序
import java.lang.reflect.Method;
//Proxy提供了创建一个动态代理类和实例的静态方法
import java.lang.reflect.Proxy;//代理类

public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    //生成代理类
    public Object getProxy(){
        // Proxy.newProxyInstance 返回指定接口的代理类的实例，该接口将方法调用分派给指定的调用处理程序。
        /*
            loader - 类加载器来定义代理类
            interfaces - 代理类实现的接口列表
            InvocationHandler - 调度方法调用的调用处理函数(传一个InvocationHandler就行)
         */
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),rent.getClass().getInterfaces(),this);
    }

    //执行代理方法
    //处理代理类上的方法（处理代理实例），并返回一个结果
    /*
        Object:  代理类（调用该方法的代理实例）
        Method： 代理这个类中的哪个方法（可以是接口）
        args： 给方法里传什么参数
     */
    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        seeHouse();
        //动态代理的本质就是反射
        Object result = method.invoke(rent,args);
        fare();
        return result;
    }

   public void seeHouse(){
       System.out.println("中介带你看房子");
   }

   public  void fare(){
       System.out.println("收中介费");
   }
}
