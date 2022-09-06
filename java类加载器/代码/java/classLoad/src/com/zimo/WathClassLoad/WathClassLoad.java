package com.zimo.WathClassLoad;

public class WathClassLoad {

	public static void main(String[] args) {
		//获取 WathClassLoad.class的类加载器
		ClassLoader wathClassLoad= WathClassLoad.class.getClassLoader();
		//打印 WathClassLoad.class 的类加载器(AppClassLoader)
		System.out.println("WathClassLoad.class 的类加载器:  "+wathClassLoad);

		//获取ClassLoader的父类，也就是他的父加载器(ExtClassLoader)
		System.out.println("WathClassLoad.class 的父加载器:  "+wathClassLoad.getParent());

		//获取ClassLoader的父类的父类，也就是他的爷爷加载器(BootStrapClassLoader)
		System.out.println("WathClassLoad.class 的爷爷加载器:  "+wathClassLoad.getParent().getParent());

		//获取String的类加载器(BootStrapClassLoader)
		System.out.println("String 的类加载器: "+ String.class.getClassLoader());
		
		
		//获取类加载器的加载目录
		//java的指令可以通过-verbose:class -verbose:gc  参数在启动时打印出类的加载情况
		System.out.println("BootStrapClassLoader 加载目录： "+System.getProperty("sun.boot.class.path"));
		System.out.println("ExtClassLoader 加载目录： "+System.getProperty("java.ext.dirs"));
		System.out.println("AppClassLoader 加载目录："+System.getProperty("java.class.path"));

	}
	
	
}
