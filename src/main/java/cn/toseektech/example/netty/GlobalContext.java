package cn.toseektech.example.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.Channel;

public class GlobalContext {
	
	private GlobalContext() {}
	
	public static final ConcurrentMap<Device, Channel> channelMaps = new ConcurrentHashMap<>();
	
	public static final AtomicInteger totalLinks = new AtomicInteger(0);

}
