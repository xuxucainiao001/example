package cn.toseektech.excel.dynamic.impl;

import java.util.List;
import java.util.Map;

import cn.toseektech.excel.dynamic.Checker;
import cn.toseektech.excel.dynamic.ValidateDateFunction;
import cn.toseektech.excel.dynamic.ValidateResult;

public class DefaultValidateDateFunction implements ValidateDateFunction {

	@Override
	public ValidateResult validate(Map<String, String> dataMap, List<Header> headers) {
		for (int i = 0; i < headers.size(); i++) {
			Header header = headers.get(i);
			String data = dataMap.getOrDefault(header.getCode(), "");
			Checker checker = header.getChecker();
			if (checker != null) {
				ValidateResult validateResult = checker.check(data);
				if (!validateResult.isSuccess()) {
					return validateResult;
				}
			}
		}
		return ValidateResult.success();
	}

}
