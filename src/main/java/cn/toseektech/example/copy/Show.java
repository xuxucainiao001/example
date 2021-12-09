package cn.toseektech.example.copy;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.mapstruct.factory.Mappers;

public class Show {
	
	public static void main(String[] args) {
     StudentDTO studentDTO = new StudentDTO();
     studentDTO.setId(1L);
     studentDTO.setStudentName("张三");
     studentDTO.setStudentScore(new BigDecimal("100"));
     studentDTO.setCreateTime(new Date());
     StudentEntity studentEntity = Mappers.getMapper(StudentMapper.class).dtoToEntity(studentDTO);
     System.out.println(ToStringBuilder.reflectionToString(studentEntity));
	}

}
  