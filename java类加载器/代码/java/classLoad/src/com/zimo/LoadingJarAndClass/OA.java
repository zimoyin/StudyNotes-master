package com.zimo.LoadingJarAndClass;

import java.net.URL;
import java.net.URLClassLoader;

public class OA {

	public static void main(String[] args) throws Exception {
		
		Double salary =2000.00;//工资
		Double money ;//实际到手的工资
		
		//加载类
		URL[] jarPaths = new URL[1];
		jarPaths[0]=new URL("file:D:\\java类加载器\\代码\\jar\\SalaryCaler.jar");//注意这里的路径要写成URL格式
		URLClassLoader classloader = new URLClassLoader(jarPaths);//通过URL地址来加载JAR包
		
		while(true) {
			money=calSalary(salary,classloader);
			System.out.println("实际到手工资: "+ money);
			Thread.sleep(1000);
		}
	}
	
	//程序员偷偷修改工资将修改工资的代码逻辑分离出去
	private static Double calSalary(Double salary,ClassLoader classloader) throws Exception {
		//加载jar中的SalaryCaler.class 文件
		Class<?> loadClass = classloader.loadClass("com.zimo.loadingJarAndClass.SalaryCaler");
		//加载这个class的实例
		Object newInstance = loadClass.newInstance();
		//运行这个实例里面的计算工资方法
		Double money = (Double) loadClass.getMethod("cal",Double.class).invoke(newInstance, salary);
		return money;
	}

}
