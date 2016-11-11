/**
* File  : RuleParams.java
* Description          : This RuleParams is   
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Nov 8, 2016      	595251  	 Initial version
*/
package com.rules.common.pojo;

/**
 * @author 595251
 *
 */
public class RuleParams {
	
	//microservice name
	private String msName;
	//fields to sort in the order provided by comma separated list
	private String sortFields;
	//sort order - either DESC or ASC
	private String sortOrder;
	//fields to select from the result by comma separated list
	private String selectFields;
	//size of the result
	private String size;
	//search field name
	private String searchField;
	//search expression - provided as a regular expression
	private String expression;
	//hook indicator either prehook or posthook
	private String hookInd;
	
	public String getMsName() {
		return msName;
	}
	public void setMsName(String msName) {
		this.msName = msName;
	}
	public String getSortFields() {
		return sortFields;
	}
	public void setSortFields(String sortFields) {
		this.sortFields = sortFields;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSelectFields() {
		return selectFields;
	}
	public void setSelectFields(String selectFields) {
		this.selectFields = selectFields;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public String getHookInd() {
		return hookInd;
	}
	public void setHookInd(String hookInd) {
		this.hookInd = hookInd;
	}
	
	@Override
	public String toString() {
		return "RuleParams [msName=" + msName + ", sortFields=" + sortFields
				+ ", sortOrder=" + sortOrder + ", selectFields=" + selectFields
				+ ", size=" + size + ", searchField=" + searchField
				+ ", expression=" + expression + ", hookInd=" + hookInd + "]";
	}
	
	
	
}
