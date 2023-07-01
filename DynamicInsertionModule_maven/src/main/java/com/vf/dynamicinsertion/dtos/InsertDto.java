package com.vf.dynamicinsertion.dtos;

import java.util.Map;

import lombok.Data;
/**
*
* @author Vasanth
*
*/
@Data
public class InsertDto {

	private String driver;
	private String userName;
	private String password;
	private String url;
	private String tableName;
	private Integer noOfRecords;
	private String colNameTypeInfo;
	private String query;
	private Map<String, String> map;	

}
