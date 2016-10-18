package com.rules.data.service;

import java.sql.Connection;

/**
* File  : DBService.java
* Description          : This DBService is an interface to provide connection for specific data store. 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public interface DBService {
	/*
	 * get data store connection by using the environment variables
	 */
	public Connection getService() throws Exception;

}
