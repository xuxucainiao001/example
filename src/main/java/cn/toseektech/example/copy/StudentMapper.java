package cn.toseektech.example.copy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentMapper {
	
   
  
    @Mapping(source = "studentName", target = "name")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "studentScore", target = "score")
    StudentEntity dtoToEntity(StudentDTO studentDTO);


}
