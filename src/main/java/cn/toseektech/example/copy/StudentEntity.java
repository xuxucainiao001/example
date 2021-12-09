package cn.toseektech.example.copy;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;


@Data
public class StudentEntity {
	
	private Long id;
	
	private String name;
	
	private BigDecimal score;
	
	private Date createTime;
	
	

}
