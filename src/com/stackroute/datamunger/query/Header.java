package com.stackroute.datamunger.query;

import java.util.HashMap;
import java.util.Map;


//Header class containing a Collection containing the headers
public class Header extends HashMap<String, Integer> {
	
	Map<String,Integer> headerMap = new HashMap<>();

	public Map<String,Integer> getHeaders() {
		return headerMap;
	}

	public void setHeaders(Map<String,Integer> headerMap) {
		this.headerMap = headerMap;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}