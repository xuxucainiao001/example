package cn.toseektech.excel.dynamic.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.toseektech.excel.dynamic.ValidateHeaderFunction;
import cn.toseektech.excel.dynamic.ValidateResult;

public class DefaultValidateHeaderFunction implements ValidateHeaderFunction {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 验证表头数量和顺序
	 */
	@Override
	public ValidateResult validate(Map<Integer, String> excelHeaderMap, List<Header> requiredHeaders) {
		if (!Objects.equals(excelHeaderMap.size(), requiredHeaders.size())) {
			logger.error("表头数量不匹配! excelHeaders:{} , requiredHeader:{}", excelHeaderMap.values(),
					requiredHeaders.stream().map(Header::getDescription).collect(Collectors.toList()));
			return ValidateResult.error("表头数量不匹配!");
		}

		for (int i = 0; i < requiredHeaders.size(); i++) {
			String excelHeader = MapUtils.getString(excelHeaderMap, i);
			String requiredHeader = requiredHeaders.get(i).getDescription();
			if (!StringUtils.equals(excelHeader, requiredHeader)) {
				logger.error("表头顺序或表头内容不匹配! excelHeader:{}=>{}  requiredHeader:{}=>{}", i, excelHeader, i,
						requiredHeader);
				return ValidateResult.error("表头顺序或表头内容不匹配!");
			}
		}

		return ValidateResult.success();
	}

}
