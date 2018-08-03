package com.stackroute.datamunger.query;

import java.util.LinkedHashMap;
import java.util.Map;

//This class will be acting as the DataSet containing multiple rows
public class DataSet extends LinkedHashMap<Long, Row> {
	Map<Long,Row> dataSet=new LinkedHashMap<Long,Row>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Map<Long, Row> getDataSet() {
		return dataSet;
	}

	public void setDataSet(Map<Long, Row> dataSet) {
		this.dataSet = dataSet;
	}
	
}