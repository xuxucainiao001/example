package cn.toseektech.excel.dynamic.impl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.toseektech.excel.dynamic.Checker;

public class Header {

	private String code;

	private String description;
	
	private Checker checker;
	
	public Header() {
		
	}
	
	/**
	 * 创建动态excel Header	
	 * @param code
	 * @param description
	 * @param checker
	 */
    public Header(String code,String description,Checker checker) {
		this.code=code;
		this.description=description;
		this.checker=checker;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public Checker getChecker() {
		return checker;
	}

	public void setChecker(Checker checker) {
		this.checker = checker;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
