package com.rules.data.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rules.common.pojo.BaseVO;
import com.rules.common.pojo.Rule;
import com.rules.data.dao.AbstractDAO;

/**
* File  : RuleMetadataPGDAO.java
* Description          : This RuleMetadataPGDAO is the main class for Rule detail manipulations with 
*                        postgres DB. 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/


public class RuleMetadataPGDAO extends AbstractDAO{

	private static final Logger logger = LoggerFactory.getLogger(RuleMetadataPGDAO.class);
	
	public RuleMetadataPGDAO(Connection connection){
		super(connection);
	}
	
	@Override
	public boolean add(BaseVO baseVO) throws Exception {
		String sql = "INSERT INTO rule_metadata (RULE_ID, RULE_NAME, RULE_DESCRIPTION, MICRO_SERVICE_NAME, ENABLED, PRE_POST_HOOK, EXEC_SEQ, RULE_TYPE, PROCEED_IND, OPTIONAL_FIELDS, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)"
				+ "					 VALUES (nextval('public.SEQ_RM_RULE_ID'),?,?,?,?,?,?,?,?,?,'ADMIN',current_timestamp,'ADMIN',current_timestamp)";
		PreparedStatement statement = null;
		Rule rule = (Rule) baseVO;
		boolean isAdded = false;
		logger.info("RULE_METADATA - ADD ---> " +rule.toString());
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1, rule.getRuleName());
			statement.setString(2, rule.getRuleDescription());
			statement.setString(3, rule.getMsName());
			statement.setString(4, rule.getEnabled());
			statement.setString(5, rule.getPrePostHook().equalsIgnoreCase("Pre Hook")? "PRE": "POST");
			statement.setInt(6, Integer.parseInt(rule.getExecSeq()));
			statement.setString(7, rule.getRuleType());
			statement.setString(8, rule.getProceedInd());
			statement.setString(9, rule.getOptionalfields());
			
			isAdded = statement.execute();
			getConnection().commit();
			
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				//connection.close();
			}
		}
		return isAdded;
	}

	@Override
	public boolean update(BaseVO baseVO) throws Exception {
		String sql = "UPDATE rule_metadata set RULE_NAME = ?, RULE_DESCRIPTION = ?, MICRO_SERVICE_NAME = ?, ENABLED = ?"
				+ ", PRE_POST_HOOK = ?, EXEC_SEQ = ?, RULE_TYPE = ?, PROCEED_IND = ?, OPTIONAL_FIELDS = ?, MODIFIED_BY = ?, MODIFIED_TIME = current_timestamp "
				+ "	 Where RULE_ID = ? " ;
		PreparedStatement statement = null;
		Rule rule = (Rule) baseVO;
		int isUpdated = 0;
		logger.info("RULE_METADATA - UPDATE ---> " +rule.toString());
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1, rule.getRuleName());
			statement.setString(2, rule.getRuleDescription());
			statement.setString(3, rule.getMsName());
			statement.setString(4, rule.getEnabled());
			statement.setString(5, rule.getPrePostHook().equalsIgnoreCase("Pre Hook")? "PRE": "POST");
			statement.setInt(6, Integer.parseInt(rule.getExecSeq()));
			statement.setString(7, rule.getRuleType());
			statement.setString(8, rule.getProceedInd());
			statement.setString(9, rule.getOptionalfields());			
			statement.setString(10, rule.getModifiedBy());
			statement.setString(11, rule.getRuleId());
			isUpdated = statement.executeUpdate();
			getConnection().commit();
			
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				//connection.close();
			}
		}
		return (isUpdated == 0 ? false : true) ;
	}

	@Override
	public boolean delete(BaseVO baseVO) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Rule> getRules() throws Exception{
		List ruleList = null;
		
		String sql = "SELECT * FROM rule_metadata;";
		Statement statement = null;
		boolean isAdded = false;
		logger.info("RULE_METADATA - GET ---> " );
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ruleList = getRuleList(rs);
					
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	
		System.out.println("RULE_METADATA - GET ---> ");
		return ruleList;
	}
	
	public List<Rule> getRules(String microServiceName) throws Exception{
		
		List ruleList = null;
		String sql = "SELECT * FROM rule_metadata where MICRO_SERVICE_NAME = '"+microServiceName +"' and ENABLED = 'Y'"; //add order by EXEC_SEQ
		Statement statement = null;
		logger.info("RULE_METADATA - GET (MS)---> " );
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			ruleList =  getRuleList(rs);					
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	
		System.out.println("RULE_METADATA - GET (MS) ---> ");
		return ruleList;
	}
	
	
public List<Rule> getRules(String microServiceName, String hookInd) throws Exception{
		
		List ruleList = null;
		String sql = "SELECT * FROM rule_metadata where MICRO_SERVICE_NAME = '"+microServiceName +"' and ENABLED = 'Y' " + " and PRE_POST_HOOK = '" +hookInd + "'"; //add order by EXEC_SEQ
		Statement statement = null;
		logger.info("RULE_METADATA - GET (MS, HI)---> " );
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			ruleList = getRuleList(rs);
					
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	
		System.out.println("RULE_METADATA - GET (MS, HI) ---> ");
		return ruleList;
	}

   private List <Rule> getRuleList(ResultSet rs) throws SQLException{
	   List ruleList = new ArrayList <Rule>();
	   Rule rule = null;
	   while(rs.next()){
			rule = new Rule();
			rule.setRuleId(rs.getInt("RULE_ID") +"");
			rule.setRuleName(rs.getString("RULE_NAME"));
			rule.setRuleDescription(rs.getString("RULE_DESCRIPTION"));
			rule.setMsName(rs.getString("MICRO_SERVICE_NAME"));
			rule.setEnabled(rs.getString("ENABLED"));
			rule.setPrePostHook(rs.getString("PRE_POST_HOOK"));
			rule.setExecSeq(rs.getString("EXEC_SEQ"));
			rule.setRuleType(rs.getString("RULE_TYPE"));
			rule.setProceedInd(rs.getString("PROCEED_IND"));
			rule.setOptionalfields(rs.getString("OPTIONAL_FIELDS"));
			rule.setCreatedBy(rs.getString("CREATED_BY"));
			rule.setCreatedTime(rs.getDate("CREATED_TIME"));
			rule.setModifiedBy(rs.getString("MODIFIED_BY"));
			rule.setModifiedTime(rs.getDate("CREATED_TIME"));
			ruleList.add(rule);
		}
	   
	   return ruleList;
   }


	


}
