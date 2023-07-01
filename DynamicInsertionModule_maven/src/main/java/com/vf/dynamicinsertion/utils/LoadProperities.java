package com.vf.dynamicinsertion.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vf.dynamicinsertion.DynamicInsertion;
import com.vf.dynamicinsertion.dtos.InsertDto;
/**
*
* @author Vasanth
*
*/
public class LoadProperities extends DynamicInsertion {
	
	private static final String CONFIG_FILENAME = "config.properties";
	private static final String CURRENT_DIR = new File("").getAbsolutePath();
	private static final Logger logger = LogManager.getLogger(LoadProperities.class);
	public static InsertDto load(InsertDto insertDto) {
	Properties properties = new Properties();
	try {
		File configfile = new File(CURRENT_DIR + File.separator + CONFIG_FILENAME);

		if (configfile.exists()) {
			properties.load(new FileInputStream(configfile));
			logger.info("Config path :" + configfile);
		} // Loading the config file
		else {
			properties.load(DynamicInsertion.class.getResourceAsStream("/config.properties"));
		}
		logger.info(properties);
		//storing the parameters in the insert bean
		insertDto.setDriver((String) properties.get("driver"));
		insertDto.setUrl((String) properties.get("url"));// Setting the .properties parameters into the insertDto
		insertDto.setUserName((String) properties.get("username"));
		insertDto.setPassword((String) properties.get("password"));
		insertDto.setTableName((String) properties.get("tableName"));
		insertDto.setNoOfRecords(Integer.parseInt((String) properties.get("noOfRecords")));
		insertDto.setColNameTypeInfo((String) properties.get("colNameTypeInfo"));
		logger.info("Properties Loaded...");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return insertDto;
	}
}
