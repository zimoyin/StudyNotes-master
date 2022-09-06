package com.zimo.BreakTheParentsAppointment;

import java.net.URL;
import java.net.URLClassLoader;

public class OA {

	public static void main(String[] args) throws Exception {
		
		Double salary =2000.00;//工资
		Double money ;//实际到手的工资
		
		//使用自定义的类加载器去加载我们的class文件
		MyJarLoader classLoader = new MyJarLoader("D:\\java类加载器\\代码\\jar\\SalaryCaler.jar");
		
		while(true) {
			money=calSalary(salary,classLoader);
			System.out.println("实际到手工资: "+ money);
			Thread.sleep(1000);
		}
	}
	
	//程序员偷偷修改工资将修改工资的代码逻辑分离出去
	private static Double calSalary(Double salary,ClassLoader classloader) throws Exception {
		//项目中的class文件
		Class<?> loadClass = classloader.loadClass("com.zimo.BreakTheParentsAppointment.SalaryCaler");
		
		System.out.println("加载器: " + loadClass.getClassLoader());
		System.out.println("父亲加载器: " + loadClass.getClassLoader().getParent());
		//加载这个class的实例
		Object newInstance = loadClass.newInstance();
		//运行这个实例里面的计算工资方法
		Double money = (Double) loadClass.getMethod("cal",Double.class).invoke(newInstance, salary);
		return money;
	}

}
