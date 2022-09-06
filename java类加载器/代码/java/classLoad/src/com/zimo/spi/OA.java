package com.zimo.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

//多版本共存
public class OA {

	public static void main(String[] args) throws Exception {
		
		Double salary =2000.00;//工资
		Double money ;//实际到手的工资
		
		//使用自定义的类加载器去加载我们的class文件
		MyJarLoader classLoader = new MyJarLoader("D:\\java类加载器\\代码\\jar\\test.jar");
		
		//获取SalaryCalService的实现类列表
		//通过订制的类加载器去加载SalaryCalService.class
		ServiceLoader<SalaryCalService> sl =ServiceLoader.load(SalaryCalService.class,classLoader);
		for (SalaryCalService salaryCal : sl) {
			System.out.println(salaryCal.cal(salary));
		}
		
	}
}
