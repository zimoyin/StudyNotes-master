package com.zimo.spi;

public class SalaryCal implements SalaryCalService{
	
	public SalaryCal() {
		System.out.println("[INFO] SalaryCal实例类空参构造: SalaryCal类以加载");
	}

	@Override
	public double cal(double salary) {
		return salary*1.2;
	}

}
