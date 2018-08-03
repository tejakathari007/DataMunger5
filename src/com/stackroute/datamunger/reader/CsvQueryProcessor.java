package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;

public class CsvQueryProcessor implements QueryProcessingEngine {

	// String fileName;
	BufferedReader br = null;
	DataTypeDefinitions dataType = null;
	Header headerMap = null;
	RowDataTypeDefinitions rowDataTypeDefinitionMap = null;

	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) throws IOException {

		boolean result = true;

		DataSet last = new DataSet();
		Row rowMap = null;
		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */

		br = new BufferedReader(new FileReader(queryParameter.getFileName()));

		/*
		 * read the first line which contains the header. Please note that the headers
		 * can contain spaces in between them. For eg: city, winner
		 */
		br.mark(1);
		String[] headerArray = br.readLine().split("\\s*,\\s*");
		
		/*
		 * read the next line which contains the first row of data. We are reading this
		 * line so that we can determine the data types of all the fields. Please note
		 * that ipl.csv file contains null value in the last column. If you do not
		 * consider this while spliting setRowIndex = 1;, this might cause exceptions
		 * later
		 */
		String[] fieldsArray = br.readLine().split("\\s*,\\s*", headerArray.length);

		/*
		 * populate the header Map object from the header array. header map is having
		 * data type <String,Integer> to contain the header and it's index.
		 */
		headerMap = new Header();
		for (int i = 0; i < headerArray.length; i++) {
			headerMap.put(headerArray[i].trim(), i);
		}
		// System.out.println(headerMap);

		/*
		 * We have read the first line of text already and kept it in an array. Now, we
		 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map
		 * is having data type <Integer,String> to contain the index of the field and
		 * it's data type. To find the dataType by the field value, we will use
		 * getDataType() method of DataTypeDefinitions class
		 */
		rowDataTypeDefinitionMap = new RowDataTypeDefinitions();
		for (int i = 0; i < fieldsArray.length; i++) {
			rowDataTypeDefinitionMap.put(i, (String) DataTypeDefinitions.getDataType(fieldsArray[i]));
		}
		// syso
		/*
		 * once we have the header and dataTypeDefinitions maps populated, we can start
		 * reading from the first line. We will read one line at a time, then check
		 * whether the field values satisfy the conditions mentioned in the query,if
		 * yes, then we will add it to the resultSet. Otherwise, we will continue to
		 * read the next line. We will continue this till we have read till the last
		 * line of the CSV file.
		 */

		/* reset the buffered reader so that it can start reading from the first line */

		br.reset();

		/*
		 * skip the first line as it is already read earlier which contained the header
		 */
		@SuppressWarnings("unused")
		String headLine = br.readLine();

		/* read one line at a time from the CSV file till we have any lines left */
		String line;
		long rowIndex = 0;
		while ((line = br.readLine()) != null) {
//			line += " ,";

			/*
			 * once we have read one line, we will split it into a String Array. This array
			 * will continue all the fields of the row. Please note that fields might
			 * contain spaces in between. Also, few fields might be empty.
			 */
			@SuppressWarnings("unused")
			String[] lineArray = line.trim().split("\\s*,\\s*");
			for (String s : lineArray) {
				System.out.println(s);
			}

			/*
			 * if there are where condition(s) in the query, test the row fields against
			 * those conditions to check whether the selected row satifies the conditions
			 */
			// String[] lineArray1 = line1.split(",");
			Filter f = new Filter();

			if (queryParameter.getRestrictions() != null) {
				boolean[] conditionsResult = new boolean[queryParameter.getRestrictions().size()];
				Iterator<Restriction> it = queryParameter.getRestrictions().iterator();
				int i = 0;
				int index;
				try {
					while (it.hasNext()) {
						Iterator<Map.Entry<String, Integer>> iter = headerMap.getHeaders().entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry<String, Integer> entry = iter.next();
							if (entry.getKey().equals(it.next().getPropertyName())) {
								index = entry.getValue();

								conditionsResult[i] = f.evaluateExpression(lineArray, it.next().getPropertyValue(),
										it.next().getCondition(), index);
								i++;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * from QueryParameter object, read one condition at a time and evaluate the
				 * same. For evaluating the conditions, we will use evaluateExpressions() method
				 * of Filter class. Please note that evaluation of expression will be done
				 * differently based on the data type of the field. In case the query is having
				 * multiple conditions, you need to evaluate the overall expression i.e. if we
				 * have OR operator between two conditions, then the row will be selected if any
				 * of the condition is satisfied. However, in case of AND operator, the row will
				 * be selected only if both of them are satisfied.
				 */
				if (queryParameter.getLogicalOperators() != null) {
					String[] logicalOps = new String[queryParameter.getLogicalOperators().size()];
					logicalOps = queryParameter.getLogicalOperators().toArray(logicalOps);
					result = conditionsResult[0];
					for (int k = 0, j = 1; k < logicalOps.length; k++, j++) {
						if (logicalOps[k].matches("and")) {
							result = result & conditionsResult[j];
						}
						if (logicalOps[k].matches("or"))
							result = result | conditionsResult[j];
						if (logicalOps[k].matches("not"))
							result = !result;
					}
				}

			}

			/*
			 * check for multiple conditions in where clause for eg: where salary>20000 and
			 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
			 */

			/*
			 * if the overall condition expression evaluates to true, then we need to check
			 * if all columns are to be selected(select *) or few columns are to be
			 * selected(select col1,col2). In either of the cases, we will have to populate
			 * the row map object. Row Map object is having type <String,String> to contain
			 * field Index and field value for the selected fields. Once the row object is
			 * populated, add it to DataSet Map Object. DataSet Map object is having type
			 * <Long,Row> to hold the rowId (to be manually generated by incrementing a Long
			 * variable) and it's corresponding Row Object.
			 */

			if (result) {
				rowMap = new Row();
				for (int i = 0; i < queryParameter.getFields().size(); i++) {
					if (queryParameter.getFields().get(i).equals("*")) {
						for (int j = 0; j < lineArray.length; j++) {
							rowMap.put(headerArray[j].trim(), lineArray[j]);
						}
					} else {
						rowMap.put(queryParameter.getFields().get(i),
								lineArray[headerMap.get(queryParameter.getFields().get(i))]);
					}
				}
				last.put(rowIndex++, rowMap);

			}

		}
		//System.out.println(last);
		/* return data set object */
		return last;

	}
}