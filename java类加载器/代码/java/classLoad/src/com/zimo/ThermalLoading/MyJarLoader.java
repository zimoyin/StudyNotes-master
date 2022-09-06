package com.zimo.ThermalLoading;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
		byte[] b ;
		
		String classPath = name.replace(".", "/").concat(".class"); //全限定名换成路径写法
		URL url;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();//字节数组输出流在内存中创建一个字节数组缓冲区,用来存放从磁盘读取的class文件的内容
		
		InputStream in=null;
		int k;
		try {
			//通过url来获取输入流
			url = new URL("jar:file:\\"+jarPath+"!/"+classPath);
			in = url.openStream();
			
			//从文件每次读取一个字节放入缓存区
			int code=0;
			
			System.out.println(url);
			System.out.println(in);
			 k =in.read();
			
			byteOut.write(k);
			while((code=in.read())!=-1) {
				byteOut.write(code);
			}
			b=byteOut.toByteArray();
		
			//3
			return this.defineClass(name,b, 0, b.length);
		}catch (Exception e) {
			
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					
				}
			}
			
			System.gc();
			
			System.out.println("文件未加载");
			return null;
		}
		
	}

}
