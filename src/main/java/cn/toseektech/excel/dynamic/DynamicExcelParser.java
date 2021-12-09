package cn.toseektech.excel.dynamic;

import java.util.List;

import cn.toseektech.excel.dynamic.impl.Header;

public interface DynamicExcelParser {
	
	/**
	 * 表头占用行数
	 * @Title: headRowNumber   
	 * @Description:    
	 * @Date: 2021年7月19日 上午10:49:33
	 * @param headRowNumber
	 * @return     
	 * @throws
	 */
	DynamicExcelParser headRowNumber(Integer headRowNumber);

	/**
	 * 添加表头 ( 添加的headers必须是有序的！！！)
	 * @Title: addHeaders   
	 * @Description:    
	 * @Date: 2021年7月19日 上午10:17:44
	 * @param heades
	 * @return     
	 * @throws
	 */
	DynamicExcelParser addHeaders(List<Header> headers);
	
	
	/**
	 * 是否校验表头 默认true
	 * @Title: validateHeader   
	 * @Description:    
	 * @Date: 2021年7月19日 上午10:17:35
	 * @param validate
	 * @return     
	 * @throws
	 */
	DynamicExcelParser validateHeader(boolean validate);
	
	
	/**
	 * 是否校验每行数据  默认true
	 * @Title: validateData   
	 * @Description:    
	 * @Date: 2021年7月19日 上午10:13:51
	 * @param validate
	 * @return     
	 * @throws
	 */
	DynamicExcelParser validateData(boolean validate);
	
	/**
	 * 解析excel
	 * @Title: parse   
	 * @Description:    
	 * @Date: 2021年7月19日 上午9:54:46
	 * @return     
	 * @throws
	 */
	ParseResult parse();
	
	
	/**
	 * 校验数据方法
	 * @Title: validateFunction   
	 * @Description:    
	 * @Date: 2021年7月19日 上午10:26:42
	 * @param validateFunction
	 * @return     
	 * @throws
	 */
	DynamicExcelParser validateDateFunction(ValidateDateFunction validateDateFunction);
	
	
	
	/**
	 * 校验数据方法
	 * @Title: validateFunction   
	 * @Description:    
	 * @Date: 2021年7月19日 上午10:26:42
	 * @param validateFunction
	 * @return     
	 * @throws
	 */
	DynamicExcelParser validateHeaderFunction(ValidateHeaderFunction validateHeaderFunction);

}
