/**
* File  : RuleUtil.java
* Description          : This RuleUtil is an util class for rule Interceptor.
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Oct 21, 2016      	595251  	 Initial version
*/
package com.rules.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;










//import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rules.common.constants.RuleConstants;
import com.rules.common.pojo.Rule;
import com.rules.common.pojo.RuleParams;
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
	public static boolean isDBRulesAvailable(String microserviceName, String hookInd){
		
		RuleMetadataPGDAO ruleMetadataDAO;
		try {
			ruleMetadataDAO = (RuleMetadataPGDAO) new DAOFactory().getDAO(
					new DBFactory().getDBService(RuleConstants.POSTGRESQL)
					.getService(), RuleConstants.RULE_METADATA);
			List <Rule> ruleList = null;
			
			if(hookInd == null || hookInd.equals("")){
				ruleList = ruleMetadataDAO.getRules(microserviceName); 
			}else {
				if (RuleConstants.PREHOOK.equalsIgnoreCase(hookInd) || 
						RuleConstants.PREHOOK_REST.equalsIgnoreCase(hookInd))
					hookInd = RuleConstants.PREHOOK;
				else
					hookInd = RuleConstants.POSTHOOK;
				ruleList = ruleMetadataDAO.getRules(microserviceName, hookInd);
			}
			
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
	public static boolean isDBRulesAvailable(String microserviceName){		
		
		return isDBRulesAvailable(microserviceName, "");
	}
	
	/**
	 * Returns true if any rules available for the micro service.
	 * 
	 * @param @param microserviceName
	 * @param @return 
	 * @return boolean
	 *
	 */
	public static boolean isRulesAvailable(RuleParams ruleParams){		
		
		if (isDBRulesAvailable(ruleParams.getMsName(), ruleParams.getHookInd()) || isQueryParamsRuleAvailable(ruleParams))
			return true;
		return false;
	}
	
	
	/**
	 * @param @param ruleParams
	 * @param @return 
	 * @return boolean
	 *
	 */
	private static boolean isQueryParamsRuleAvailable(RuleParams ruleParams) {
		boolean isAvailable = false;
		
		if (RuleUtil.isEmpty(ruleParams))
			return isAvailable;
		//any of the below one static rule is available in the query param
		if ( (!RuleUtil.isEmpty(ruleParams.getSearchField()) && !RuleUtil.isEmpty(ruleParams.getExpression()))
				|| !RuleUtil.isEmpty(ruleParams.getSortFields())
				|| !RuleUtil.isEmpty(ruleParams.getSelectFields())  
				|| !RuleUtil.isEmpty(ruleParams.getSize()) )
			isAvailable = true;
		return isAvailable;
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
	 * Call post hook rules for the given input as json string and microservice.
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
	 * Call pre hook rules for the given input as java object and and microservice.
	 * 
	 * @param @param request
	 * @param @param microserviceName
	 * @param @return 
	 * @return Object
	 *
	 */
	public static Object executePrehookRule(Object requestObject, String microserviceName) throws Exception{
		Gson gson = new Gson();
		String request =  gson.toJson(requestObject);
		request = executeRule(request, microserviceName, RuleConstants.PREHOOK);
		return gson.fromJson(request, (requestObject!=null)?requestObject.getClass():Object.class);
	}
	
	/**
	 * 
	 * Call post hook rules for the given input as java object and microservice.
	 * 
	 * @param @param requestObject
	 * @param @param microserviceName
	 * @param @return
	 * @param @throws Exception 
	 * @return Object
	 *
	 */
	public static Object executePosthookRule(Object requestObject, String microserviceName) throws Exception{
		
		System.out.println("requestObject-->" +requestObject.toString());
		Gson gson = new Gson();
		String request =  gson.toJson(requestObject);
		System.out.println("request (before)-->" +request);
		request = executeRule(request, microserviceName, RuleConstants.POSTHOOK);
		System.out.println("request (after)-->" +request);
		return gson.fromJson(request, (requestObject!=null)?requestObject.getClass():Object.class);
	}
	
	/**
	 * 
	 * @param @param ruleParams
	 * @param @return 
	 * @return String
	 * @throws Exception 
	 *
	 */
	public static String executeRule(String request, RuleParams ruleParams) throws Exception{
		if(!isRulesAvailable(ruleParams))
			  throw new Exception("No Rules Available for :"+ruleParams.getMsName());
		  
		return execute(getQueryString(request, ruleParams));
	}
	
	
	/**
	 * 
	 * @param @param requestObject
	 * @param @param ruleParams
	 * @param @return
	 * @param @throws Exception 
	 * @return Object
	 *
	 */
	public static Object executeRule(Object requestObject, RuleParams ruleParams) throws Exception{
		if(!isRulesAvailable(ruleParams))
			  throw new Exception("No Rules Available for :"+ruleParams.getMsName());
		 
		Gson gson = new Gson();
		String request =  gson.toJson(requestObject);
		request = execute(getQueryString(request, ruleParams));
		return gson.fromJson(request, (requestObject!=null)?requestObject.getClass():Object.class);

	}
	
	/**
	 * @param @param ruleParams 
	 * @return void
	 *
	 */
	private static String getQueryString(String request, RuleParams ruleParams) {
		
		  List nameValuePairs = new ArrayList();
		  if (!RuleUtil.isEmpty(ruleParams.getMsName()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.MS_NAME_PARAM, ruleParams.getMsName()));
		  if (!RuleUtil.isEmpty(request))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.INPUT_PARAM, request));
		  if (!RuleUtil.isEmpty(ruleParams.getSortFields()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.SORT_FIELDS, ruleParams.getSortFields()));
		  if (!RuleUtil.isEmpty(ruleParams.getSortOrder()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.SORT_ORDER, ruleParams.getSortOrder()));
		  if (!RuleUtil.isEmpty(ruleParams.getSearchField()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.SEARCH_FIELD, ruleParams.getSearchField()));
		  if (!RuleUtil.isEmpty(ruleParams.getExpression()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.EXPRESSION, ruleParams.getExpression()));
		  if (!RuleUtil.isEmpty(ruleParams.getSize()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.SIZE, ruleParams.getSize()));
		  if (!RuleUtil.isEmpty(ruleParams.getHookInd()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.HOOK_IND, ruleParams.getHookInd()));
		  if (!RuleUtil.isEmpty(ruleParams.getSelectFields()))
			  nameValuePairs.add(new BasicNameValuePair(RuleConstants.SELECT_FIELDS, ruleParams.getSelectFields()));
		  String queryString = URLEncodedUtils.format(nameValuePairs, RuleConstants.UTF_8);
		  return queryString;
	}

	private static String execute(String queryString) throws ClientProtocolException, IOException{
	  String url = getServiceURL();
      DefaultHttpClient client = new DefaultHttpClient();
	  client.addRequestInterceptor(new RemoveSoapHeadersInterceptor(new ByteArrayInputStream(queryString.getBytes()).available()), 0);
	  
      HttpPost httpPost = new HttpPost(url + "?" + queryString);
		
      System.out.println("Content-Length 1--->" +httpPost.getFirstHeader("Content-Length"));
      
      //httpPost.setHeader("Content-Length", ""+new ByteArrayInputStream(queryString.getBytes()).available());
      
      //System.out.println("Content-Length--->" +httpPost.getFirstHeader("Content-Length"));
      
      //httpPost.addHeader(HttpHeaders.CONTENT_LENGTH, "");
      //Header header = new Header();
      HttpResponse response = client.execute(httpPost);
      
	  BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	
	  String line = "";
	  StringBuilder sb = new StringBuilder();
	  while ((line = rd.readLine()) != null) {
	
		  	sb.append(line);
		  	
	  }
	  return sb.toString();
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
		
		  if(!isDBRulesAvailable(microserviceName, hookInd))
			  throw new Exception("No Rules Available for :"+microserviceName);
		  
		  RuleParams ruleParams = new RuleParams();
		  ruleParams.setMsName(microserviceName);
		  ruleParams.setHookInd(hookInd);
		  
		  return executeRule(request, ruleParams);
	
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
