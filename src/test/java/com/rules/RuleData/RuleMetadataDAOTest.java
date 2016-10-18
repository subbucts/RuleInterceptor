/**
* File  : RuleMetadataDAOTest.java
* Description          : This RuleMetadataDAOTest is   
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Oct 13, 2016      	595251  	 Initial version
*/
package com.rules.RuleData;

import java.util.List;

import com.rules.common.constants.RuleConstants;
import com.rules.common.pojo.Rule;
import com.rules.data.dao.postgres.RuleMetadataPGDAO;
import com.rules.data.factory.DAOFactory;
import com.rules.data.factory.DBFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author 595251
 *
 */
public class RuleMetadataDAOTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RuleMetadataDAOTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RuleMetadataDAOTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
        RuleMetadataPGDAO ruleMetadataDAO;
		try {
			ruleMetadataDAO = (RuleMetadataPGDAO) new DAOFactory().getDAO(
					new DBFactory().getDBService(RuleConstants.POSTGRESQL)
					.getService(), RuleConstants.RULE_METADATA);
		
		
			List <Rule> ruleList  = 	ruleMetadataDAO.getRules("Microservice1");
			
			assertNotNull(ruleList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
