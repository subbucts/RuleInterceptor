package com.rules.data.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.rules.common.constants.RuleConstants;
import com.rules.data.dao.IDAO;
import com.rules.data.dao.postgres.RuleMetadataPGDAO;
import com.rules.exception.RuleInterceptorBaseException;

/**
* File  : DAOFactory.java
* Description          : This DAOFactory is a factory class to create DAO classes and inject connection 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public class DAOFactory {
	
	/**
	 * return data store specific DAO and inject connection details.
	 * 
	 * @param @param connection
	 * @param @param name
	 * @param @return 
	 * @return BaseDAO
	 * @throws RuleInterceptorBaseException 
	 *
	 */
	public IDAO getDAO(Connection connection, String name) throws RuleInterceptorBaseException {
		
		IDAO baseDAO = null;
		
		if (name == null || name.equals("")){
			return baseDAO;
		}
		
		if (connection == null)
			throw new RuleInterceptorBaseException("Can't create DAO because no data store Connection available");
		try {
			System.out.println("DB Product Name : " + connection.getMetaData().getDatabaseProductName());
			
			//TODO add code for checking db name
			if (RuleConstants.RULE_METADATA.equalsIgnoreCase(name)){
				baseDAO = new RuleMetadataPGDAO(connection);
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuleInterceptorBaseException(e.getMessage());
		}
		
		
		return baseDAO;
	}

}