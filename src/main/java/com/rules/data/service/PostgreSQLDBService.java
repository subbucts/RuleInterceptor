package com.rules.data.service;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rules.common.constants.RuleConstants;

/**
* File  : PostgreSQLDBService.java
* Description          : This PostgreSQLDBService is a service class to create connection for postgres. 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public class PostgreSQLDBService extends AbstractDBService{

	private static final Logger logger = LoggerFactory.getLogger(PostgreSQLDBService.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.rules.data.service.DBService#getService()
	 */
	 	public Connection getService() throws Exception {
		Map<String, String> env = System.getenv(); 
		
		JSONParser parser = new JSONParser();
		try{
			Class.forName("org.postgresql.Driver");
		}catch (Exception ex){
			logger.info("Class Load error --> " +ex.getMessage());
		}
		System.out.println("Class loaded...");//
		
		Map mapParamas = getDBParams();
		
		String url = "jdbc:postgresql://" + mapParamas.get(RuleConstants.HOST) + ":" + mapParamas.get(RuleConstants.PORT) + mapParamas.get(RuleConstants.PATH);
				
		return DriverManager.getConnection(url, (String)mapParamas.get(RuleConstants.RULE_DB_USER_NAME), (String) mapParamas.get(RuleConstants.RULE_DB_PASSWORD));

	}

}
