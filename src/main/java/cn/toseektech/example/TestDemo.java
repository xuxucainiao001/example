package cn.toseektech.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.hbase.thirdparty.com.google.common.collect.Lists;

import cn.toseektech.excel.dynamic.DynamicExcelParser;
import cn.toseektech.excel.dynamic.impl.Checkers;
import cn.toseektech.excel.dynamic.impl.DefaultDynamicExcelParser;
import cn.toseektech.excel.dynamic.impl.Header;

public class TestDemo {

	public static void main(String[] args) throws FileNotFoundException{
		
		
		DynamicExcelParser parser = new DefaultDynamicExcelParser(new FileInputStream(new File("D:/demo.xlsx")));
		parser.validateData(true);
		parser.addHeaders(
				Lists.newArrayList(
				new Header("name","姓名",Checkers.getChecker(0)),
				new Header("age","年龄",Checkers.getChecker(1)),
				new Header("sex","性别",Checkers.getChecker(0)),
				new Header("address","住址",Checkers.getChecker(0))
				)
				);
        System.out.println(parser.parse());
	}

}



