package com.zimo.MyJarLoader;

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
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		//2. 获取jar文件里面的class文件的字节数组
		byte[] b = getClassByte(name);
		
		//3. defineClass()是在jvm内存中定义一个class
		try {
			return this.defineClass(name,b, 0, b.length);
		} catch (Exception e) {
			System.out.println("class文件未找到");
		}
		return null;
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
			System.out.println("class读取失败");
			e.printStackTrace();
		}
		
		
		
		return byteOut.toByteArray();
	}
	
	
}
