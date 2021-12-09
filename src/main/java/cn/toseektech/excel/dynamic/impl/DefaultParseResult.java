package cn.toseektech.excel.dynamic.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.hbase.thirdparty.com.google.common.collect.Lists;

import cn.toseektech.excel.dynamic.ParseResult;

public class DefaultParseResult implements ParseResult{
	
	protected String headerErrorMessage;
	
	protected List<Map<String,String>> dataList = Lists.newArrayList();
	
	protected List<Map<String,String>> errorDataList = Lists.newArrayList();
		
	protected boolean hasDataError = false;
		
	protected boolean hasHeaderError =false ;
	
	protected List<Header> headers = Lists.newArrayList();
	

	@Override
	public String getHeaderErrorMessage() {
		return headerErrorMessage;
	}

	@Override
	public List<Map<String, String>> getErrorData() {
         return errorDataList;
	}

	@Override
	public List<Map<String, String>> getSuccessData() {
         return dataList;
	}

	@Override
	public boolean hasDataError() {
		return hasDataError;
	}

	@Override
	public boolean  hasHeaderError() {
		return hasHeaderError;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public List<Header> getHeaders() {
		return headers;
	}
	

}
