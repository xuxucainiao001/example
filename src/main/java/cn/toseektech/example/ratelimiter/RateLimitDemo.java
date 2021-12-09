package cn.toseektech.example.ratelimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.hutool.core.lang.Console;

public class RateLimitDemo {
	
	private static int count=0;
	
	private static Long start;

	public static void main(String[] args) throws InterruptedException{
		ExecutorService executor=Executors.newFixedThreadPool(1000);
        RateLimiter rateLimiter = new RateLimiterImpl(10);	
        start = System.currentTimeMillis();
        while(true) {
        	Thread.sleep(1);
        	executor.submit(()->{
        		rateLimiter.limit();
        		invkeMethod();
        	});
        }
	}

	public static void invkeMethod() {
		Console.log((System.currentTimeMillis()-start)/1000+1+"秒调用了"+(++count)+"次");
	}
	
	
}
