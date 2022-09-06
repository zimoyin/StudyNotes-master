package com.zimo.MyClassLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureClassLoader;



/**
 * 自定义的ClassLoader ，由他去加载我们的class
 * @author Administrator
 *
 */
//1. 继承SecureClassLoader，并覆盖findClass方法
public class MyClassLoader  extends SecureClassLoader{
	
		// 获取项目地址下的src具体路径
		private String projectPath = System.getProperty("user.dir")+"bin\\";
		
		public MyClassLoader() {
		}
		
		/**
		 * @param classPath
		 * 获取项目地址下的src具体路径
		 */
		public MyClassLoader(String projectPath) {
			this.projectPath=projectPath;
		}
	
		
		
	/**
	 * @param name
	 * class的名称(全限定名)
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		//2. 获取class文件的字节数组
		byte[] b = getClassBytes(projectPath,name);
		System.out.println(name);
		//3. 在jvm内存中定义一个class
		try {
			return this.defineClass(name,b, 0, b.length);
		} catch (Exception e) {
			System.out.println("class文件未找到");
		}
		return null;
	}
	
	
	//2.0 读取class文件
	private byte[] getClassBytes(String path,String className) {
		//拼接class文件在磁盘中的全路径
		String filePath = path+ className.replace(".", "/").concat(".class");
		FileInputStream in = null;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();//字节数组输出流在内存中创建一个字节数组缓冲区,用来存放从磁盘读取的class文件的内容
		
		try {
			//2.1 获取文件输出流
			in = new FileInputStream(new File(filePath));
			
			//从文件每次读取一个字节放入缓存区
			int code;
			while((code=in.read())!=-1) {
				byteOut.write(code);
			}
			
		} catch (Exception e) {
			System.out.println("class文件读取失败");
			e.printStackTrace();
		}
		
		//关闭文件输入流
		finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//将缓存区变为byte数组并返回
		return byteOut.toByteArray();
	}
}
