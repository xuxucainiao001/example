package cn.toseektech.excel.dynamic;

import java.util.List;
import java.util.Map;

import cn.toseektech.excel.dynamic.impl.Header;

public interface ValidateHeaderFunction {
	  
	  /**
	   * 表头校验方法
	   * @Title: validate   
	   * @Description:    
	   * @Date: 2021年7月19日 上午10:30:09
	   * @param headerMap
	   * @return     
	   * @throws
	   */
	  ValidateResult validate(Map<Integer,String> excelHeaderMap,List<Header> realHeaders);

}
