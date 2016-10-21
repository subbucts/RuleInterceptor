/**
* File  : RuleUtil.java
* Description          : This RuleUtil is an util class for rule Interceptor.
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Oct 21, 2016      	595251  	 Initial version
*/
package com.rules.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 595251
 *
 */
public class RuleUtil {
	
	public static Map<String, String> splitToMap(String source, String entriesSeparator, String keyValueSeparator) {
	    Map<String, String> map = new HashMap<String, String>();
	    String[] entries = source.split(entriesSeparator);
	    for (String entry : entries) {
	    	if (!isEmpty(entry) && entry.contains(keyValueSeparator)) {
	            String[] keyValue = entry.split(keyValueSeparator);
	            map.put(keyValue[0], keyValue[1]);
	        }
	    }
	    return map;
	}

	/**
	 * To check an object is having empty or null value.
	 * 
	 * @param @param entry
	 * @param @return 
	 * @return boolean
	 *
	 */
	public static boolean isEmpty(Object value) {
		if (value == null || (value instanceof String && value.equals("")) )
			return true;
		
		return false;
				
		
	}
	
	/**
	 * To get the list of values from the input string with the specified seprator.
	 * 
	 * @param @param value
	 * @param @param entriesSeparator
	 * @param @return 
	 * @return List
	 *
	 */
	public static List getList(String value, String entriesSeparator) {
		
		List <String> list = null;
		String[] tokens = value.split(entriesSeparator);
		
		if (!isEmpty(tokens) && tokens.length > 0)
			list = new ArrayList(Arrays.asList(tokens));
		return list;
	}
	
	
	/*
	public static void main (String arg[]){
		
		String value = "COUNT=3;FIELDS=A,B,C;";
		System.out.println( " Map---> "+ splitToMap(value, ";", "="));
		
		System.out.println( " Fields---> "+ getList(splitToMap(value, ";", "=").get("FIELDS"), ","));
	}
	*/
}
