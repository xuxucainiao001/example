package cn.toseektech.excel.dynamic.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.hbase.thirdparty.com.google.common.collect.Lists;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;

import cn.toseektech.excel.dynamic.DynamicExcelParser;
import cn.toseektech.excel.dynamic.ParseResult;
import cn.toseektech.excel.dynamic.ValidateDateFunction;
import cn.toseektech.excel.dynamic.ValidateHeaderFunction;

public class DefaultDynamicExcelParser implements DynamicExcelParser {

	private List<Header> headers = Lists.newLinkedList();

	private InputStream inputStream;

	private boolean validateHeader = true;

	private boolean validateData = true;

	private Integer sheetNum = 0;

	private String sheetName = null;

	private Integer headRowNumber = 1;

	private ValidateDateFunction validateDateFunction = new DefaultValidateDateFunction();

	private ValidateHeaderFunction validateHeaderFunction = new DefaultValidateHeaderFunction();

	public DefaultDynamicExcelParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public DefaultDynamicExcelParser(InputStream inputStream, Integer sheetNum, String sheetName) {
		this(inputStream);
		this.sheetNum = sheetNum;
		this.sheetName = sheetName;
	}

	@Override
	public DynamicExcelParser addHeaders(List<Header> headers) {
		this.headers.addAll(headers);
		return this;
	}

	@Override
	public DynamicExcelParser validateHeader(boolean validate) {
		validateHeader = validate;
		if(!validateHeader) {
			this.validateHeaderFunction = null;
		}
		return this;
	}

	@Override
	public DynamicExcelParser validateData(boolean validate) {
		validateData = validate;
		if(!validateData) {
			this.validateDateFunction = null;
		}		
		return this;
	}

	@Override
	public DynamicExcelParser validateDateFunction(ValidateDateFunction validateDateFunction) {
		this.validateDateFunction = validateDateFunction;
		return this;
	}

	@Override
	public DynamicExcelParser validateHeaderFunction(ValidateHeaderFunction validateHeaderFunction) {
		this.validateHeaderFunction = validateHeaderFunction;
		return this;
	}
	
	@Override
	public DynamicExcelParser headRowNumber(Integer headRowNumber) {
		this.headRowNumber = headRowNumber;
		return this;
	}

	@Override
	public ParseResult parse() {
		check();
		SimpleAnalysisEventListener listener = new SimpleAnalysisEventListener(validateDateFunction,
				validateHeaderFunction, headers);
		readExecl(listener);
		// 返回解析结果
		return listener.getParseResult();
	}

	/**
	 * 构建并解析
	 * 
	 * @Title: buildReader
	 * @Description:
	 * @Date: 2021年7月19日 上午10:52:38
	 * @return @throws
	 */
	private void readExecl(AnalysisEventListener<Map<Integer, String>> listener) {

		ExcelReader excelReader = EasyExcelFactory.read(this.inputStream).build();

		ReadSheet readSheet = EasyExcelFactory.readSheet(this.sheetNum, this.sheetName).registerReadListener(listener)
				.autoTrim(true).headRowNumber(this.headRowNumber).build();

		excelReader.read(readSheet);
	}

	/**
	 * 验证参数
	 *  @Title: check 
	 *  @Description: 
	 *  @Date: 2021年7月19日 上午10:38:27 
	 *  @throws
	 */
	private void check() {
		if (inputStream == null) {
			throw new RuntimeException("DynamicExcelParser流不可以为空");
		}
		if (CollectionUtils.isEmpty(headers)) {
			throw new RuntimeException("DynamicExcelParser表头不可以为空");
		}
		if (validateHeader && validateHeaderFunction == null) {
			throw new RuntimeException("DynamicExcelParser表头校验函数validateHeaderFunction不可以为空");
		}

		if (validateData && validateDateFunction == null) {
			throw new RuntimeException("DynamicExcelParser数据校验函数validateHeaderFunction不可以为空");
		}
	}
    
	

}
