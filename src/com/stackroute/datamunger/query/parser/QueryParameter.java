package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;
public class QueryParameter {

	private String fileName;
	private String basequery;
	private List<String> orderby;
	private List<String> groupby;
	private List<String> fields;
	private List<String> logical;
	private List<AggregateFunction> aggregateFunctions= new ArrayList<AggregateFunction>();
	private List<Restriction>restrictions= new ArrayList<Restriction> ();

	
	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	public String getFileName() {
		return fileName;
	}

	public String getBaseQuery() {
		return basequery;
	}
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}


	public List<String> getLogicalOperators() {
		
		return logical;
	}

	public List<String> getFields() {
		
		return fields;
	}


	public void setBasequery(String basequery) {
		this.basequery = basequery;
	}

	public List<String> getOrderby() {
		return orderby;
	}

	public void setOrderby(List<String> orderby) {
		this.orderby = orderby;
	}

	public List<String> getGroupby() {
		return groupby;
	}

	public void setGroupby(List<String> groupby) {
		this.groupby = groupby;
	}

	public List<String> getLogical() {
		return logical;
	}

	public void setLogical(List<String> logical) {
		this.logical = logical;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	
	public List<String> getGroupByFields() {
		
		return groupby;
	}

	public List<String> getOrderByFields() {
		return orderby;
	}

	public String getQUERY_TYPE() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}