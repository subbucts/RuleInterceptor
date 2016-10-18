/**
* File  : RuleInterceptorBaseException.java
* Description          : This RuleInterceptorBaseException is   
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Oct 14, 2016      	595251  	 Initial version
*/
package com.rules.exception;

/**
 * @author 595251
 *
 */
public class RuleInterceptorBaseException extends Exception{

	public RuleInterceptorBaseException (){
		super("Some Exception in RuleInterceptor");
	}
	public RuleInterceptorBaseException (String message){
		super(message);
	}
}
