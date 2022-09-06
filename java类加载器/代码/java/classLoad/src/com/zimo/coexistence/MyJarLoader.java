package com.zimo.coexistence;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureClassLoader;

//1. 继承SecureClassLoader，覆盖findClass方法
public class MyJarLoader extends SecureClassLoader{

	private String jarPath;	//jar的路径 如: D://hello.jar
	public MyJarLoader(String jarPath) {
		this.jarPath=jarPath;
	}
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> c =null;
	    synchronized (getClassLoadingLock(name)) {
	        //先去MyJarLoader缓存查找
	        c = findLoadedClass(name);

	        //缓存没有就去加载
	        if(c==null) {
	            //先去我的类加载器所负责的范围加载
	            try {
	                c = this.findClass(name); 
	            } catch (Exception e) {
	                // TODO: handle exception
	            }

	            //如果我的类加载器没有加载到就让父加载器去加载
	            if(c==null) c = super.loadClass(name, resolve);
	        }

	    }

	    return c;
	}

	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		//2. 获取jar文件里面的class文件的字节数组
		byte[] b = getClassByte(name);
		
		//3. defineClass()是在jvm内存中定义一个class

		
		return this.defineClass(name,b, 0, b.length);
	}
	
	private byte[] getClassByte(String name) {
		String classPath = name.replace(".", "/").concat(".class"); //全限定名换成路径写法
		URL url;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();//字节数组输出流在内存中创建一个字节数组缓冲区,用来存放从磁盘读取的class文件的内容
		
		try {
			//通过url来获取输入流
			url = new URL("jar:file:\\"+jarPath+"!/"+classPath);
			InputStream in = url.openStream();
			
			//从文件每次读取一个字节放入缓存区
			int code;
			while((code=in.read())!=-1) {
				byteOut.write(code);
			}
			
		} catch (Exception e) {
		}
		
		
		
		return byteOut.toByteArray();
	}
	
	
}
