/**
* File  : AbstractDBService.java
* Description          : This AbstractDBService is   
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Oct 17, 2016      	595251  	 Initial version
*/
package com.rules.data.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rules.common.constants.RuleConstants;

/**
 * @author 595251
 *
 */
public abstract class AbstractDBService implements DBService{

	private static final Logger logger = LoggerFactory.getLogger(AbstractDBService.class);
	
	public Map <String, String> getDBParams() throws Exception{
		
		Map<String, String> env = System.getenv(); 
		
		JSONParser parser = new JSONParser();
		Map envVar = new  HashMap<String, String>();
		
		if (env.containsKey(RuleConstants.RULE_DB_CONFIG)) {//from user defined
			System.out.println("vcap --> " +env.get(RuleConstants.RULE_DB_CONFIG).toString());
			//get from user defined.
			JSONArray vcap = (JSONArray) parser.parse(env.get(RuleConstants.RULE_DB_CONFIG));
			
			if (vcap != null) {

				JSONObject service = (JSONObject) vcap.get(0);
				System.out.println("service --> " +service.toString());
									
				for (Object key : service.keySet()) {
					String keyStr = (String) key;
					if (keyStr.toLowerCase().contains(RuleConstants.RULE_DB_PRODUCT_NAME)) {
						envVar.put(RuleConstants.RULE_DB_PRODUCT_NAME, (String) service.get(keyStr));
						
					}
					if (keyStr.toLowerCase().contains(RuleConstants.CREDENTIALS)) {
						JSONObject creds =  (JSONObject) service.get(keyStr);
						System.out.println("creds --> " +creds.toString());
						
						if (creds != null) {
							URI uri = URI.create((String) creds.get(RuleConstants.URI));
							String username = uri.getUserInfo().split(":")[0];
							String password = uri.getUserInfo().split(":")[1];
							envVar.put(RuleConstants.HOST, uri.getHost());
							envVar.put(RuleConstants.PORT, uri.getPort());
							envVar.put(RuleConstants.PATH, uri.getPath());
							envVar.put(RuleConstants.RULE_DB_USER_NAME, username);
							envVar.put(RuleConstants.RULE_DB_PASSWORD, password);
							System.out.println("username --> " + username + "  , password -->" +password);
						}
					}
				}
			}else{
			
				throw new Exception("No DB Configuration found. Make sure you have bound the correct environment variables to your app.");
			}
		}
		System.out.println("envVar ---> "+ envVar.toString());
		logger.info("envVar ---> "+ envVar.toString());
		return envVar;
	}
	
	

}
