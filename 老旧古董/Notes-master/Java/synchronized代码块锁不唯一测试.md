
测试synchronized锁不唯一情况

主类：
```java
public class Main
{
	public static void main(String[] args) throws IOException
	{
		
		for(int i  = 0 ; i<5;i++){
			new Thread (new j()).start();
		}
		
	}
}

```


线程类
```java
class j implements Runnable
{

@Override
public void run()
{
	
	

 	synchronized(this){
		try
		{
			Thread.sleep(100);
			System.out.println(Thread.currentThread().getName()+":一号开始执行......");
		}
		catch (InterruptedException e)
		{}
		
		System.out.println(Thread.currentThread().getName()+":一号执行完毕");
	}
	
	synchronized(this){
		try
		{
			Thread.sleep(100);
			System.out.println(Thread.currentThread().getName()+":2号开始执行......");
		}
		catch (InterruptedException e)
		{}

		System.out.println(Thread.currentThread().getName()+":2号执行完毕");
		
	}
}

	
}

```
结果：
```
	Thread-15:一号开始执行......
	 Thread-16:一号开始执行......
	 Thread-18:一号开始执行......
	 Thread-15:一号执行完毕
	 Thread-16:一号执行完毕
	 Thread-18:一号执行完毕
	 Thread-19:一号开始执行......
	 Thread-19:一号执行完毕
	 Thread-17:一号开始执行......
	 Thread-17:一号执行完毕
	 Thread-15:2号开始执行......
	 Thread-15:2号执行完毕
	 Thread-16:2号开始执行......
	 Thread-16:2号执行完毕
	 Thread-18:2号开始执行......
	 Thread-18:2号执行完毕
	 Thread-19:2号开始执行......
	 Thread-19:2号执行完毕
	 Thread-17:2号开始执行......
	 Thread-17:2号执行完毕
	
```

在synchronized中要保证锁的唯一性否则就会锁不住代码。下面是这次代码的执行结果
可以看到对代码上锁的情况下线程15开始执行1号代码块还没有执行完线程16就又开始执行了一号代码块,这说明synchronized代码块锁不住代码
	 ？？？？？？？？？？
