package com.rules.data.dao;

import java.sql.Connection;

import com.rules.common.pojo.BaseVO;

/**
* File  : AbstractDAO.java
* Description          : This AbstractDAO is the abstract DAO class for all the DAO's and has the 
*                        basic method definitions. 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public abstract class AbstractDAO implements IDAO{

	
	protected Connection connection = null;
	
	public AbstractDAO (Connection connection){
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public abstract boolean add(BaseVO baseVO) throws Exception;
	public abstract boolean update(BaseVO baseVO) throws Exception;
	public abstract boolean delete(BaseVO baseVO) throws Exception;
}
