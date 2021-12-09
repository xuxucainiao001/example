package cn.toseektech.excel.dynamic;

import java.util.List;
import java.util.Map;

import cn.toseektech.excel.dynamic.impl.Header;

public interface ParseResult {
	
	/**
	 * 获取表头错误提示
	 * @Title: getHeaderErrorMessage   
	 * @Description:    
	 * @Date: 2021年7月19日 下午2:57:33
	 * @return     
	 * @throws
	 */

	String getHeaderErrorMessage();
	
	/**
	 * 获取错误数据（）
	 * @Title: getErrorData   
	 * @Description:    
	 * @Date: 2021年7月19日 下午2:57:58
	 * @return     
	 * @throws
	 */
	List<Map<String,String>> getErrorData();
	
	/**
	 * 获取正确数据
	 * @Title: getSuccessData   
	 * @Description:    
	 * @Date: 2021年7月19日 下午2:58:13
	 * @return     
	 * @throws
	 */
	List<Map<String,String>> getSuccessData();
	
	/**
	 * 数据是否有错误
	 * @Title: hasDataError   
	 * @Description:    
	 * @Date: 2021年7月19日 下午2:58:25
	 * @return     
	 * @throws
	 */
	boolean hasDataError();
	
	/**
	 * 表头是否有错误
	 * @Title: hasHeaderError   
	 * @Description:    
	 * @Date: 2021年7月19日 下午2:58:35
	 * @return     
	 * @throws
	 */
	boolean hasHeaderError();
	
	/**
	 * 获取表头
	 * @Title: getHeaders   
	 * @Description:    
	 * @Date: 2021年7月19日 下午3:23:17
	 * @return     
	 * @throws
	 */
    List<Header> getHeaders();
}
