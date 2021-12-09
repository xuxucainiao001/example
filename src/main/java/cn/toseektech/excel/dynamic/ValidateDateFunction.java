package cn.toseektech.excel.dynamic;

import java.util.List;
import java.util.Map;

import cn.toseektech.excel.dynamic.impl.Header;

public interface ValidateDateFunction {
	  
	  /**
	   * 每行数据校验逻辑
	   * @Title: validate   
	   * @Description:    
	   * @Date: 2021年7月19日 下午12:41:05
	   * @param dataMap 数据
	   * @param headers dataMap的key
	   * @return     
	   * @throws
	   */
	  ValidateResult validate(Map<String,String> dateMap,List<Header> headers);

}
