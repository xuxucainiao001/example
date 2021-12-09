package cn.toseektech.excel.dynamic.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.hbase.thirdparty.com.google.common.collect.Lists;
import org.apache.hbase.thirdparty.com.google.common.collect.Maps;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import cn.toseektech.excel.dynamic.ParseResult;
import cn.toseektech.excel.dynamic.ValidateDateFunction;
import cn.toseektech.excel.dynamic.ValidateHeaderFunction;
import cn.toseektech.excel.dynamic.ValidateResult;

public class SimpleAnalysisEventListener extends AnalysisEventListener<Map<Integer, String>> {

	private ValidateDateFunction validateDateFunction;

	private ValidateHeaderFunction validateHeaderFunction;

	private List<Header> headers;

	private DefaultParseResult pr = new DefaultParseResult();

	private List<Map<String, String>> tempList = Lists.newArrayList();

	private static final String ERROR_HAEDER_CODE = "dataError";

	private static final String ERROR_HAEDER_DESCRIPTION = "数据错误描述";

	public SimpleAnalysisEventListener(ValidateDateFunction validateDateFunction,
			ValidateHeaderFunction validateHeaderFunction, List<Header> headers) {
		this.validateDateFunction = validateDateFunction;
		this.validateHeaderFunction = validateHeaderFunction;
		this.headers = headers;
	}

	@Override
	public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
		//移除 代表错误描述的表头，该表头不参与校验
		Set<Entry<Integer, String>> entrySet = headMap.entrySet();
		for(Entry<Integer, String> entry:entrySet) {
			if(ERROR_HAEDER_DESCRIPTION.equals(entry.getValue())) {
				headMap.remove(entry.getKey());
			}
		}
		// validateHeaderFunction 为null无需校验
		if(validateHeaderFunction==null) {
			pr.hasHeaderError = false;
			return;
		}
		//校验表头
		ValidateResult validateResult = validateHeaderFunction.validate(headMap, headers);
		if (!validateResult.isSuccess()) {
			//校验表头失败
			pr.hasHeaderError = true;
			pr.headerErrorMessage = validateResult.getErrorMessage();
		}
	}

	@Override
	public void invoke(Map<Integer, String> data, AnalysisContext context) {
		//表头校验失败，数据无需继续校验
		if (pr.hasHeaderError) {
			return;
		}
		// 转换数据替换原map中的序号
		// [{"0":"xuxu"},{1:15}] => [{"name","xuxu"},{"age":28}]
		Map<String, String> dataMap = Maps.newHashMap();
        for(int i =0 ;i<headers.size();i++) {
        	String value = MapUtils.getString(data, i,"");
        	dataMap.put(headers.get(i).getCode(), value);
        }
        // validateDateFunction为空。无需校验
		if (validateDateFunction == null) {
			tempList.add(dataMap);
			return;
		}
		
        // 校验该行数据
		ValidateResult validateResult = validateDateFunction.validate(dataMap,headers);
		if (!validateResult.isSuccess()) {
			//校验失败增加错误列
			pr.hasDataError = true;
			// 如果不包含errorData表头。则添加。包含无需重复添加
			if (!ERROR_HAEDER_CODE.equals(getLastHeader().getCode())) {
				Header errorHeader = new Header(ERROR_HAEDER_CODE, ERROR_HAEDER_DESCRIPTION,new StringChecker());
				headers.add(errorHeader);
			}
			dataMap.put(ERROR_HAEDER_CODE, validateResult.getErrorMessage());
		}
		tempList.add(dataMap);

	}

	private Header getLastHeader() {
		return headers.get(headers.size() - 1);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		pr.headers.addAll(headers);
		// 表头错了
		if(pr.hasHeaderError) {
			return ;
		}
		// 数据错了
		if(pr.hasDataError) {
			pr.errorDataList.addAll(tempList);
			return;
		}
		//没有表头错误，也没有数据错误
		pr.dataList.addAll(tempList);
	}
	
	/**
	 * 返回结果
	 * @Title: getParseResult   
	 * @Description:    
	 * @Date: 2021年7月19日 下午3:25:27
	 * @return     
	 * @throws
	 */
	public ParseResult  getParseResult() {
		return pr;
	}


}
