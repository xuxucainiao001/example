package cn.toseektech.example.ratelimiter;

import java.util.concurrent.locks.LockSupport;

public class RateLimiterImpl implements RateLimiter {

	private int speed;

	private volatile long nextAllowVisitTime;

	public RateLimiterImpl(int speed) {
		// 每隔多少毫秒允许下次访问
		this.speed = 1000 / speed;
		this.nextAllowVisitTime = System.currentTimeMillis();
	}

	@Override
	public synchronized void limit() {
		while (System.currentTimeMillis() < this.nextAllowVisitTime) {
			LockSupport.parkUntil(1);
		}
		this.nextAllowVisitTime = System.currentTimeMillis() + this.speed;
	}

}
