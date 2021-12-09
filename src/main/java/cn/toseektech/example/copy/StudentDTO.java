package cn.toseektech.example.copy;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class StudentDTO {
	
	
	private Long id;
	
	private String studentName;
	
	private BigDecimal studentScore;
	
	private Date createTime;
	

}
