package com.zimo.ThermalLoading;

import java.net.URL;
import java.net.URLClassLoader;

public class OA {
//热加载
	public static void main(String[] args) throws Exception {
		
		Double salary =2000.00;//工资
		Double money ;//实际到手的工资
		
		
		
		while(true) {
			money=calSalary(salary);
			System.out.println("实际到手工资: "+ money);
			Thread.sleep(1000);
		}
	}
	
	/*
	 * 热加载
	 * 每次加载类的时候都创建一个新的类加载器
	 */
	private static Double calSalary(Double salary) throws Exception {
		//使用自定义的类加载器去加载我们的class文件
		MyJarLoader classloader = new MyJarLoader("D:\\java类加载器\\代码\\jar\\SalaryCaler.jar");
		//项目中的class文件
		Class<?> loadClass = classloader.loadClass("com.zimo.MyJarLoader.SalaryCaler");
		
		if(loadClass!=null) {
			//加载这个class的实例
			Object newInstance = loadClass.newInstance();
			//运行这个实例里面的计算工资方法
			Double money = (Double) loadClass.getMethod("cal",Double.class).invoke(newInstance, salary);
			return money;
			
		}
		

		return -1.00;
		
	}

}
