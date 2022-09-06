package com.zom.demo01;

/**
 * 动态代理
 */
public class Client {
    public static void main(String[] args) {
        Host host = new Host();  //真实角色(将要被代理)
        //代理角色
        ProxyInvocationHandler p = new ProxyInvocationHandler();
        p.setRent(host);//通过调用程序处理角色来处理我们要调用的接口对象
        Rent proxy = (Rent) p.getProxy();//这里的proxy是动态生成的

        proxy.rent();

    }
}
