package com.stackroute.datamunger.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


//This class contains methods to evaluate expressions
public class Filter {
	
	Filter filter;
	RowDataTypeDefinitions rowDataType;
	DataTypeDefinitions dataType;
	String datatype;
	boolean bool;

	/*
	 * The evaluateExpression() method of this class is responsible for evaluating
	 * the expressions mentioned in the query. It has to be noted that the process
	 * of evaluating expressions will be different for different data types. there
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method
	 * should be able to evaluate all of them. Note: while evaluating string
	 * expressions, please handle uppercase and lowercase
	 * 
	 */

	@SuppressWarnings("unchecked")
	public boolean evaluateExpression(String lineArray[], String value, String condition, int index) throws ParseException {
		
		HashMap<Integer, String> rowData = new HashMap<Integer, String>();
		rowData =  (HashMap<Integer, String>) ((HashMap<Integer, String>) rowDataType.getRowDataType()).clone();

		if (rowData.containsKey(index)) {
			datatype = rowData.get(index);
		}
		switch (condition) {
		
		case "=":
			bool = equalTo(lineArray[index], value, datatype);

			break;
		case ">":
			bool = greaterThan(lineArray[index], value, datatype);

			break;

		case "<":
			bool = lessThan(lineArray[index], value, datatype);

			break;

		case "<=":
			bool = lessThanEqualTo(lineArray[index], value, datatype);

			break;
		case ">=":
			bool = greaterThanEqualTo(lineArray[index], value, datatype);

			break;
		case "!=":
			bool = notEqualTo(lineArray[index], value, datatype);

			break;
		default:
			bool = false;
		}
		return bool;
	}

// Method containing implementation of equalTo operator

	private boolean equalTo(String string, String value, String datatype2) throws ParseException {
		// TODO Auto-generated method stub
		{
			boolean result = false;
	        if (datatype2.matches("java.lang.Integer"))
	        {
	            int resValue = 0;
	            int rowValue = 0;
	            resValue = Integer.parseInt(value);
	            rowValue = Integer.parseInt(string);
	            if (resValue == rowValue)
	                result = true;
	   }
	        if (datatype2.matches("java.lang.Double"))
	        {
	            double resValue = 0;
	            double rowValue = 0;
	            resValue = Double.parseDouble(value);
	            rowValue = Double.parseDouble(string);
	            if (resValue == rowValue)
	                result = true;
	   }
	        if (datatype2.matches("java.lang.String"))
	        {
	        	if(string.matches(value))
	        	{
	        		result=true;
	        	}
	        }
	   
	  if(datatype2.matches("java.lang.Date"))
	  {
		  Date rowDate = new SimpleDateFormat("yyyy-mm-dd").parse(value);
          Date restrictionDate = new SimpleDateFormat("yyyy-mm-dd").parse(string);
          if (rowDate.compareTo(restrictionDate) == 0)
              result = true;
    }
	return result;
	}
}
	

// Method containing implementation of notEqualTo operator
	private boolean notEqualTo(String string, String value, String datatype2) throws ParseException {
		{
	}
		 boolean result = false;
	       if (datatype2.matches("java.lang.Integer"))
	       {
	           int resValue = 0;
	           int rowValue = 0;
	           resValue = Integer.parseInt(value);
	           rowValue = Integer.parseInt(string);
	           if (resValue != rowValue)
	               result = true;
	  }
	       if (datatype2.matches("java.lang.Double"))
	       {
	           double resValue = 0;
	           double rowValue = 0;
	           resValue = Double.parseDouble(value);
	           rowValue = Double.parseDouble(string);
	           if (resValue != rowValue)
	               result = true;
	  }
	       if (datatype2.matches("java.lang.String"))
	       {
	       	if(string.matches(value))
	       	{
	       		return false;
	       	}
	       }
	  
	 if(datatype2.matches("java.lang.Date"))
	 {
		  Date rowDate = new SimpleDateFormat("yyyy-mm-dd").parse(value);
	       Date restrictionDate = new SimpleDateFormat("yyyy-mm-dd").parse(string);
	       if (rowDate.compareTo(restrictionDate) == 0)
	           result = false;
	 }
	return result;
	 
	  }
	

// Method containing implementation of greaterThan operator
	private boolean greaterThan(String string, String value, String datatype2)  throws ParseException 
	  {
		  
		  boolean result = false;
	      if (datatype2.matches("java.lang.Integer"))
	      {
	          int resValue = 0;
	          int rowValue = 0;
	          resValue = Integer.parseInt(value);
	          rowValue = Integer.parseInt(string);
	          if (resValue>rowValue)
	              result = true;
	 }
	      if (datatype2.matches("java.lang.Double"))
	      {
	          double resValue = 0;
	          double rowValue = 0;
	          resValue = Double.parseDouble(value);
	          rowValue = Double.parseDouble(string);
	          if (resValue >rowValue)
	              result = true;
	 }  
	      
	      if(datatype2.matches("java.lang.Date"))
	      {
	     	  Date rowDate = new SimpleDateFormat("yyyy-mm-dd").parse(value);
	            Date restrictionDate = new SimpleDateFormat("yyyy-mm-dd").parse(string);
	            if (rowDate.compareTo(restrictionDate) > 0)
	                result = true;
	      }
	     return result;
	  }
	

// Method containing implementation of greaterThanOrEqualTo operator

	private boolean greaterThanEqualTo(String string, String value, String datatype2)  throws ParseException {		
		
		  boolean result = false;
	      if (datatype2.matches("java.lang.Integer"))
	      {
	          int resValue = 0;
	          int rowValue = 0;
	          resValue = Integer.parseInt(value);
	          rowValue = Integer.parseInt(string);
	          if (resValue>rowValue)
	              result = true;
	 }
	      if (datatype2.matches("java.lang.Double"))
	      {
	          double resValue = 0;
	          double rowValue = 0;
	          resValue = Double.parseDouble(value);
	          rowValue = Double.parseDouble(string);
	          if (resValue >rowValue)
	              result = true;
	 }  
	      
	      if(datatype2.matches("java.lang.Date"))
	      {
	     	  Date rowDate = new SimpleDateFormat("yyyy-mm-dd").parse(value);
	            Date restrictionDate = new SimpleDateFormat("yyyy-mm-dd").parse(string);
	            if (rowDate.compareTo(restrictionDate) > 0)
	                result = true;
	      }
	     return result;
	}

// Method containing implementation of lessThan operator
	private boolean lessThan(String string, String value, String datatype2)  throws ParseException 
	  {
		  
		  boolean result = false;
	      if (datatype2.matches("java.lang.Integer"))
	      {
	          int resValue = 0;
	          int rowValue = 0;
	          resValue = Integer.parseInt(value);
	          rowValue = Integer.parseInt(string);
	          if (resValue<rowValue)
	              result = true;
	 }
	      if (datatype2.matches("java.lang.Double"))
	      {
	          double resValue = 0;
	          double rowValue = 0;
	          resValue = Double.parseDouble(value);
	          rowValue = Double.parseDouble(string);
	          if (resValue <rowValue)
	              result = true;
	 }  
	      
	      if(datatype2.matches("java.lang.Date"))
	      {
	     	  Date rowDate = new SimpleDateFormat("yyyy-mm-dd").parse(value);
	            Date restrictionDate = new SimpleDateFormat("yyyy-mm-dd").parse(string);
	            if (rowDate.compareTo(restrictionDate) < 0)
	                result = true;
	      }
	     return result;
	}

// Method containing implementation of lessThanOrEqualTo operator
	private boolean lessThanEqualTo(String string, String value, String datatype2)  throws ParseException {		
		{
			   boolean result = false;
		        if (datatype2.matches("java.lang.Integer"))
		        {
		            int resValue = 0;
		            int rowValue = 0;
		            resValue = Integer.parseInt(value);
		            rowValue = Integer.parseInt(string);
		            if (resValue <= rowValue)
		                result = true;
		   }
		        if (datatype2.matches("java.lang.Double"))
		        {
		            double resValue = 0;
		            double rowValue = 0;
		            resValue = Double.parseDouble(value);
		            rowValue = Double.parseDouble(string);
		            if (resValue <= rowValue)
		                result = true;
		   }
		        
		   
		  if(datatype2.matches("java.lang.Date"))
		  {
			  Date rowDate = new SimpleDateFormat("yyyy-mm-dd").parse(value);
	            Date restrictionDate = new SimpleDateFormat("yyyy-mm-dd").parse(string);
	            if (rowDate.compareTo(restrictionDate) <= 0)
	                result = true;
	      }
		return result;
	
	}

}}