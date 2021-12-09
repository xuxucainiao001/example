package cn.toseektech.excel.dynamic.impl;

import java.util.Map;

import org.apache.hbase.thirdparty.com.google.common.collect.Maps;

import cn.toseektech.excel.dynamic.Checker;

public class Checkers {
	
	static {
		checkers = Maps.newHashMap();
		register(0, new StringChecker());
		register(1, new NumberChecker());
	}

	private Checkers() {
	}

	private static  Map<Integer, Checker> checkers;
    
	/**
	 * 注册检查器
	 * @Title: register   
	 * @Description:    
	 * @Date: 2021年7月19日 下午3:09:32
	 * @param num
	 * @param checker     
	 * @throws
	 */
	public static void register(Integer num, Checker checker) {
		checkers.put(num, checker);
	}
	
	/**
	 * 获取检查器
	 * @Title: getChecker   
	 * @Description:    
	 * @Date: 2021年7月19日 下午3:10:38
	 * @param num
	 * @return     
	 * @throws
	 */
	public static Checker getChecker(Integer num) {
		return checkers.get(num);
	}

}
