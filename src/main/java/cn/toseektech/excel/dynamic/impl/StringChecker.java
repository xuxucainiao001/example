package cn.toseektech.excel.dynamic.impl;

import cn.toseektech.excel.dynamic.Checker;
import cn.toseektech.excel.dynamic.ValidateResult;

public class StringChecker implements Checker{
    
	/**
	 * 
	 */
	@Override
	public ValidateResult check(String data) {
	     return ValidateResult.success();
	}

}
