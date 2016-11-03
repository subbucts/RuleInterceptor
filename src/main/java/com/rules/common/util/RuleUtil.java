/**
* File  : RuleUtil.java
* Description          : This RuleUtil is an util class for rule Interceptor.
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Oct 21, 2016      	595251  	 Initial version
*/
package com.rules.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.rules.common.constants.RuleConstants;
import com.rules.common.pojo.Rule;
import com.rules.data.dao.postgres.RuleMetadataPGDAO;
import com.rules.data.factory.DAOFactory;
import com.rules.data.factory.DBFactory;

/**
 * @author 595251
 *
 */
public class RuleUtil {
	
	/**
	 * To split the optional field map and set the key/value
	 * 
	 * @param @param source
	 * @param @param entriesSeparator
	 * @param @param keyValueSeparator
	 * @param @return 
	 * @return Map<String,String>
	 *
	 */
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
	
	/**
	 * returns true if any rules available for the micro service with the specified hook condition.
	 * if no hook variable, returns all the valid rules.
	 * 
	 * @param @param microserviceName
	 * @param @param hookInd
	 * @param @return 
	 * @return boolean
	 *
	 */
	public static boolean isRulesAvailable(String microserviceName, String hookInd){
		
		RuleMetadataPGDAO ruleMetadataDAO;
		try {
			ruleMetadataDAO = (RuleMetadataPGDAO) new DAOFactory().getDAO(
					new DBFactory().getDBService(RuleConstants.POSTGRESQL)
					.getService(), RuleConstants.RULE_METADATA);
			List <Rule> ruleList = null;
			
			if(hookInd == null || hookInd.equals(""))
				ruleList = ruleMetadataDAO.getRules(microserviceName); 
			else
				ruleList = ruleMetadataDAO.getRules(microserviceName, hookInd);
			
			if (ruleList != null && ruleList.size() > 0)
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Returns true if any rules available for the micro service.
	 * 
	 * @param @param microserviceName
	 * @param @return 
	 * @return boolean
	 *
	 */
	public static boolean isRulesAvailable(String microserviceName){		
		
		return isRulesAvailable(microserviceName, "");
	}
	
	
	/**
	 * Call pre hook rules for the given input and microservice.
	 * 
	 * @param @param request
	 * @param @param microserviceName
	 * @param @return 
	 * @return String
	 *
	 */
	public static String executePrehookRule(String request, String microserviceName) throws Exception{
		return executeRule(request, microserviceName, RuleConstants.PREHOOK);
	}
	
	/**
	 * Call post hook rules for the given input and microservice.
	 * 
	 * @param @param request
	 * @param @param microserviceName
	 * @param @return 
	 * @return String
	 *
	 */
	public static String executePosthookRule(String request, String microserviceName) throws Exception{
		return executeRule(request, microserviceName, RuleConstants.POSTHOOK);
	}
	
	/**
	 * Execute rules by calling Rule Invoker microservice
	 * 
	 * @param @param request
	 * @param @param microserviceName
	 * @param @param hookInd
	 * @param @return 
	 * @return String
	 *
	 */
	private static String executeRule(String request, String microserviceName, String hookInd) throws Exception{
		
		  if(!isRulesAvailable(microserviceName, hookInd))
			  throw new Exception("No Rules Available for :"+microserviceName);
		  
		  HttpClient client = new DefaultHttpClient();
		
		  String url = getServiceURL();
		  
		  if (!isEmpty(hookInd) && hookInd.equalsIgnoreCase(RuleConstants.PREHOOK))
				  url += RuleConstants.PREHOOK_REST;
		  else
			  	url += RuleConstants.POSTHOOK_REST;
	  
		  List nameValuePairs = new ArrayList(1);
		  nameValuePairs.add(new BasicNameValuePair(RuleConstants.MS_NAME_PARAM, microserviceName));
		  nameValuePairs.add(new BasicNameValuePair(RuleConstants.INPUT_PARAM, request));
		  String paramsString = URLEncodedUtils.format(nameValuePairs, RuleConstants.UTF_8);
		  HttpGet httpGet = new HttpGet(url + "?" + paramsString);
          HttpResponse response = client.execute(httpGet);
		  BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		
		  String line = "";
		  StringBuilder sb = new StringBuilder();
		  while ((line = rd.readLine()) != null) {
		
			  	sb.append(line);
			  	
		  }
		  request = sb.toString();
		  return request;
	
	}

	/**
	 * get service URL from environment variable
	 * 
	 * @param @return 
	 * @return String
	 *
	 */
	private static String getServiceURL(){
		
		Map<String, String> env = System.getenv(); 
		String url = null;
		
		if (env.containsKey(RuleConstants.SERVICE_URL)) 
			url = env.get(RuleConstants.SERVICE_URL)==null? url : env.get(RuleConstants.SERVICE_URL).toString();
			
			
		return url;
	}

	
}
