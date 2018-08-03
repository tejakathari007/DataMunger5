package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;



public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setBasequery(getBaseQuery(queryString));
		queryParameter.setOrderby(getOrderByFields(queryString));
		queryParameter.setGroupby(getGroupByFields(queryString));
		queryParameter.setFields(getFields(queryString));
		queryParameter.setLogical(getLogicalOperators(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParameter.setRestrictions(getRestrictions(queryString));
		return queryParameter;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */public String getFileName(String queryString) {
		String result = "";
		String[] queryParts = queryString.split(" ");
		for (int i = 0; i < queryParts.length; i++) {
			if (queryParts[i].equals("from")) {
				result = queryParts[i + 1];
			}
		}
		return result;
	}

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	public String getBaseQuery(String queryString) {
		String[] baseQuery = queryString.toLowerCase().trim().split(" where | order by | group by ");
		return baseQuery[0];
	}

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public List<String> getOrderByFields(String queryString) {
		String[] part;
		List<String> result = new ArrayList<String>();
		if (queryString.contains("order by ")) {
			String parts[] = queryString.split("order by ");
			part = parts[1].split(",");
			if (part != null) {
				for (int i = 0; i < part.length; i++) {
					result.add(part[i]);
				}
			}
		}
		return result;
	}

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public List<String> getGroupByFields(String queryString) {
		String[] part;
		List<String> result = new ArrayList<String>();
		if (queryString.contains("group by")) {
			String parts[] = queryString.split("group by | order by");
			part = parts[1].split(",");
			if (part != null) {
				for (int i = 0; i < part.length; i++) {
					result.add(part[i]);
				}
			}
		}
		return result;
	}

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public List<String> getFields(String queryString) {
		List<String> result = new ArrayList<String>();
		String[] part = queryString.split("select | from");
		String[] fieldNames = part[1].split(", |,");
		if (fieldNames != null) {
			for (int i = 0; i < fieldNames.length; i++) {
				result.add(fieldNames[i]);
			}
		}
		return result;
	}
	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	public List<String> getLogicalOperators(String queryString) {
		List<String> result = null;
		String str = "";
		if (queryString.contains(" where "))  {
			String[] queryelements = queryString.split("\\s");
			for (int i = 0; i < queryelements.length; i++) {
				if (queryelements[i].matches("and|or|not")) {
					str += queryelements[i] + " ";
				}
			}
			String[] part = str.split(" ");
			if (part != null) {
				result=new ArrayList<String>();
				for (int i = 0; i < part.length; i++) {
					result.add(part[i]);
				}
			}
		}
		return result;
	}

	/*
	 * } Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public List<Restriction> getRestrictions(String queryString) {
        List<Restriction> liresult=null;
        String lowerQuery = queryString;
        String[] result=null;
        if (lowerQuery.contains(" where ")) {
            String[] conditions = lowerQuery.trim().split(" where | group by | order by ");
            result = conditions[1].trim().split(" and | or |;");
        }
               if(result!=null) {
                   liresult=new ArrayList<Restriction>();
                   for(int i=0;i<result.length;i++) {
                       if(result[i].contains("=")) {
                           String[] res = result[i].split("[\\W]+");
                           Restriction restriction = new Restriction(res[0].trim(),res[1].trim(),"=");
                           liresult.add(restriction);
                       }
                       else if(result[i].contains(">")) {
                           String[] res = result[i].split("[\\W]+");
                           Restriction restriction = new Restriction(res[0].trim(),res[1].trim(),">");
                           liresult.add(restriction);
                       }
                       else if(result[i].contains(">=")) {
                           String[] res = result[i].split("[\\W]+");
                           Restriction restriction = new Restriction(res[0].trim(),res[1].trim(),">=");
                           liresult.add(restriction);
                       }
                       else if(result[i].contains("<")) {
                           String[] res = result[i].split("[\\W]+");
                           Restriction restriction = new Restriction(res[0].trim(),res[1].trim(),"<");
                           liresult.add(restriction);
                       }
                       else if(result[i].contains("<=")) {
                           String[] res = result[i].split("[\\W]+");
                           Restriction restriction = new Restriction(res[0].trim(),res[1].trim(),"<=");
                           liresult.add(restriction);
                       }
                   }
               }
          
        
           return liresult;
       }

    
	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		List<AggregateFunction> result = new ArrayList<AggregateFunction>();
		String[] query1 = queryString.split("select | from ");
		String[] query = query1[1].split(",");
		for (int i = 0; i < query.length; i++) {
			if (query[i].startsWith("max(")
					|| query[i].startsWith("min(")
					|| query[i].startsWith("count(")
					|| query[i].startsWith("avg(")
					|| query[i].startsWith("sum(") && query[i].endsWith(")")) {
				String out[] = query[i].split("[)|(]+");
				AggregateFunction aggregateFunction = new AggregateFunction(out[1], out[0]);
				result.add(aggregateFunction);
			}
		}
		return result;
	}
}