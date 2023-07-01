package com.vf.dynamicinsertion.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.vf.dynamicinsertion.dtos.InsertDto;
/**
*
* @author Vasanth
*
*/
public class DBConnection {
	public static Connection getConnection(InsertDto insertDto) throws SQLException, ClassNotFoundException {
		Class.forName(insertDto.getDriver());
		Connection conn = DriverManager.getConnection(insertDto.getUrl(), insertDto.getUserName(),
				insertDto.getPassword());
		return conn;
	}

}
