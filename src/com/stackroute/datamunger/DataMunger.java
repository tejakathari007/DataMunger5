package com.stackroute.datamunger;

import java.io.IOException;

import com.stackroute.datamunger.query.Query;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.reader.QueryProcessingEngine;

public class DataMunger {

	public static Query query = null;
	public static QueryProcessingEngine queryProcessor = null;
	public static QueryParameter queryParameter = null;

	public static void main(String[] args) throws IOException {

		// Read the query from the user
		System.out.println("Enter the Query");
		String queryString = "select * from data/ipl.csv";
		/*
		 * Instantiate Query class. This class is responsible for: 1. Parsing the query
		 * 2. Select the appropriate type of query processor 3. Get the resultSet which
		 * is populated by the Query Processor
		 */
		query = new Query();

		/*
		 * Instantiate JsonWriter class. This class is responsible for writing the
		 * ResultSet into a JSON file
		 */

		/*
		 * call executeQuery() method of Query class to get the resultSet. Pass this
		 * resultSet as parameter to writeToJson() method of JsonWriter class to write
		 * the resultSet into a JSON file
		 */
	
			query.executeQuery(queryString);
		
	}
}