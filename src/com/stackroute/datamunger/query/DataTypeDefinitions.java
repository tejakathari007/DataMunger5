package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
 * Implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {
	static BufferedReader br;
	static DataTypeDefinitions dataType;

	// method stub
	public static Object getDataType(String input) {

		// checking for Integer

		// checking for floating point numbers

		// checking for date format dd/mm/yyyy

		// checking for date format mm/dd/yyyy

		// checking for date format dd-mon-yy

		// checking for date format dd-mon-yyyy

		// checking for date format dd-month-yy

		// checking for date format dd-month-yyyy

		// checking for date format yyyy-mm-dd

		String result = "";
		if (input != null) {

			if (input.matches("[+-]?[0-9][0-9]*")) {
				int i = Integer.parseInt(input);
				result = ((Object) i).getClass().getName();
			}
			// checking for floating point numbers
			else if (input.matches("[+-]?[0-9]+(\\\\.[0-9]+)?([Ee][+-]?[0-9]+)?")) {

				float f = Float.parseFloat(input);
				result = ((Object) f).getClass().getName();
			}

			else if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date date2 = null;
				try {
					date2 = formatter1.parse(input);
				} catch (ParseException e) {

					e.printStackTrace();
				}
				result = ((Object) date2).getClass().getName();

			} else if (input.matches("\\s")) {
				result = dataType.getClass().getSuperclass().getName();
			}

			else {
				result = input.getClass().getName();
			}

		}
		return result;

	}

}