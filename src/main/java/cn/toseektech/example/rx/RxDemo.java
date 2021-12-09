package cn.toseektech.example.rx;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RxDemo {

	public static void main(String[] args) {

		Stream.of(new Student()).collect(Collectors.groupingBy(e -> 
		
		
			e.getName()==null?"2":e.getName()
		

		));

	}

}

class Student {

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
