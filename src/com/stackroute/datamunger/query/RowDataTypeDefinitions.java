package com.stackroute.datamunger.query;

import java.util.HashMap;
import java.util.Map;

//This class will be used to store the column data types as columnIndex/DataType
public class RowDataTypeDefinitions extends HashMap<Integer, String>{
	
	Map<Integer,String> rowDataTypeMap = new HashMap<>();
	
	public Map<Integer,String> getRowDataType() {
		return rowDataTypeMap;
	}

	public void setRowDataType(Map<Integer, String> rowDataTypeMap2) {
		this.rowDataTypeMap = rowDataTypeMap2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}