package com.rules.data.dao;

import java.sql.Connection;

/**
* File  : IDAO.java
* Description          : This IDAO is the interface for all the DAO's and has the connection 
*                        getter and setter methods. 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public interface IDAO {
	
	
	public Connection getConnection();

	public void setConnection(Connection connection);
	


}
