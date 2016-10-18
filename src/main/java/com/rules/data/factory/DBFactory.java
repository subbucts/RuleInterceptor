package com.rules.data.factory;

import com.rules.common.constants.RuleConstants;
import com.rules.data.service.DBService;
import com.rules.data.service.PostgreSQLDBService;

/**
* File  : DBFactory.java
* Description          : This DBFactory is a factory class to create DB services for specific data store. 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public class DBFactory {
	
	/**
	 * return db service class based on db name
	 * 
	 * @param @param dbName
	 * @param @return 
	 * @return DBService
	 *
	 */
	public DBService getDBService(String dbName) {
		
		DBService dbService = null;
		
		if (dbName == null || dbName.equals("")){
			return dbService;
		}
		
		if (RuleConstants.POSTGRESQL.equalsIgnoreCase(dbName)){
			dbService = new PostgreSQLDBService();
		}

		return dbService;
	}

}
