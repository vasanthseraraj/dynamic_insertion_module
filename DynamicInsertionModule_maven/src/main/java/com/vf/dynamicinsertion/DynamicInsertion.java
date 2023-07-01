package com.vf.dynamicinsertion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vf.dynamicinsertion.dtos.InsertDto;
import com.vf.dynamicinsertion.insert.InsertRecords;
import com.vf.dynamicinsertion.utils.LoadProperities;

/**
 *
 * @author Vasanth
 *
 */
public class DynamicInsertion {
	
	public static void main(String[] args) {

		InsertDto insertDto = new InsertDto();
		LoadProperities.load(insertDto);// Read and store the parameters
		InsertRecords.insert(insertDto);// Inserting the Records
	}
}
