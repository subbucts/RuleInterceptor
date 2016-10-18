package com.rules.common.pojo;

import java.util.Date;

/**
* File  : Rule.java
* Description          : This Rule is the main class  to hold all the rule related details 
* Revision History :
* Version      Date            		Author       Reason
* 0.1          07-October-2016      595251  	Initial version
*/

public class Rule extends BaseVO{
	
	private String ruleId;
	private String ruleName;
	private String ruleDescription;
	private String msName;
	private String enabled;
	private String prePostHook;
	private String execSeq;
	private String ruleType;
	private String proceedInd;
	private String ruleContent;
	private String createdBy; 
	private Date createdTime; 
	private String modifiedBy; 
	private Date modifiedTime; 
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public String getMsName() {
		return msName;
	}
	public void setMsName(String msName) {
		this.msName = msName;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getPrePostHook() {
		return prePostHook;
	}
	public void setPrePostHook(String prePostHook) {
		this.prePostHook = prePostHook;
	}
	public String getExecSeq() {
		return execSeq;
	}
	public void setExecSeq(String execSeq) {
		this.execSeq = execSeq;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getProceedInd() {
		return proceedInd;
	}
	public void setProceedInd(String proceedInd) {
		this.proceedInd = proceedInd;
	}
	public String getRuleContent() {
		return ruleContent;
	}
	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public String toString() {
		return "Rule [ruleId=" + ruleId + ", ruleName=" + ruleName
				+ ", ruleDescription=" + ruleDescription + ", msName=" + msName
				+ ", enabled=" + enabled + ", prePostHook=" + prePostHook
				+ ", execSeq=" + execSeq + ", ruleType=" + ruleType
				+ ", proceedInd=" + proceedInd + ", ruleContent=" + ruleContent
				+ ", createdBy=" + createdBy + ", createdTime=" + createdTime
				+ ", modifiedBy=" + modifiedBy + ", modifiedTime="
				+ modifiedTime + "]";
	}


	

}
