package cn.toseektech.excel.dynamic.impl;

import cn.hutool.core.util.NumberUtil;
import cn.toseektech.excel.dynamic.Checker;
import cn.toseektech.excel.dynamic.ValidateResult;

public class NumberChecker implements Checker{


	@Override
	public ValidateResult check(String data) {
		boolean flag = NumberUtil.isNumber(data);
		if(flag) {
			return ValidateResult.success();
		}
		return ValidateResult.error(data+":非法数字");
	}

}
