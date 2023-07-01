package com.vf.dynamicinsertion.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vf.dynamicinsertion.dtos.InsertDto;
import com.vf.dynamicinsertion.utils.DBConnection;
/**
*
* @author Vasanth
*
*/
public class InsertRecords {
	private static final Logger logger = LogManager.getLogger(InsertRecords.class);
	private static long INITIAL_MOBILE_NUMBER = 919600000000L;
	private static final String[] STR_VALUES = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine","ten"};

	
	public static void insert(InsertDto insertDto) {
		try {
			generateInsertQuery(insertDto);

			String query = "";
			int recordsInserted = 0, noOfRecords = insertDto.getNoOfRecords();
			logger.info("Inserting Records...");
			Instant start = Instant.now();
			for (int i = 0; i < noOfRecords; i++) {// Insertion based on the no of records
				query = insertIntoTheUsers(insertDto);// Insertion
				recordsInserted++;
			}
			Instant stop = Instant.now();
			logger.info("Query: " + query);
			logger.info(recordsInserted + " records inserted into the " + insertDto.getTableName() + " table within "
					+ Duration.between(start, stop).toMillis() + "ms");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static PreparedStatement getPrepareStatement(PreparedStatement prepStmt, InsertDto insertDto)
			throws SQLException {// prepare statement based on DataType

		Map<String, String> map = insertDto.getMap();
		int random = new Random().nextInt(10), i = 0;
		for (Entry<String, String> entry : map.entrySet()) {
			String dataType = entry.getValue();
			int mins = 0;
			LocalDateTime currentDateTime = LocalDateTime.now();
			LocalDateTime dateTime = currentDateTime;

			if (dataType.contains("+")) {
				String[] arr = dataType.split("\\+");
				dataType = arr[0];
				mins = Integer.parseInt(arr[1]);
				dateTime = currentDateTime.plusMinutes(mins);
			} else if (dataType.contains("-")) {
				String[] arr = dataType.split("\\-");
				dataType = arr[0];
				mins = Integer.parseInt(arr[1]);
				dateTime = currentDateTime.minusMinutes(mins);
			}

			switch (dataType) {
			case "String":
				prepStmt.setString(++i, STR_VALUES[random]);
				break;
			case "Integer":
				prepStmt.setInt(++i, random);
				break;
			case "Date":
				prepStmt.setTimestamp(++i, Timestamp.valueOf(dateTime));
				break;
			case "Mobile":
				prepStmt.setLong(++i, INITIAL_MOBILE_NUMBER++);
				break;
			}
		}
		return prepStmt;
	}

	private static String insertIntoTheUsers(InsertDto insertDto) throws SQLException {

		String query="";
		try {
			query = "INSERT INTO " + insertDto.getQuery();
			Connection conn = DBConnection.getConnection(insertDto);// Getting the Connection

			PreparedStatement prepStmt = getPrepareStatement(conn.prepareStatement(query), insertDto);

			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return query;

	}
	private static void generateInsertQuery(InsertDto insertDto) {// Query Generation

		try {
			String query = insertDto.getTableName().concat(" (");// tableName concatenation
			Map<String, String> map = new LinkedHashMap<String, String>();

			String[] listOfCol = insertDto.getColNameTypeInfo().split(";");
			Arrays.asList(listOfCol).forEach(str -> {
				String[] strArr = str.split(":");
				map.put(strArr[0], strArr[1]);;
			});	
			insertDto.setMap(map);
			int mapSize = map.size();
			for (Entry<String, String> entry : map.entrySet()) {
				query = query.concat(entry.getKey().concat(","));// columnName concatenation
			}
			query = query.substring(0, query.length() - 1).concat(") VALUES(");

			for (int temp = 1; temp < mapSize; temp++) {
				query = query.concat("?,");
			}
			query = query.concat("?)");// placeholder concatenation
			insertDto.setQuery(query);
		} catch (NullPointerException e) {
			logger.error("Config File is missing or incorrect");
			e.printStackTrace();
		}
	}
	

}
