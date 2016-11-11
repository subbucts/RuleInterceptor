/**
* File  : RemoveSoapHeadersInterceptor.java
* Description          : This RemoveSoapHeadersInterceptor is   
* Revision History :
* Version      Date            	Author       Reason
* 0.1          Nov 8, 2016      	595251  	 Initial version
*/
package com.rules.common.util;

import java.io.IOException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/**
 * @author 595251
 *
 */
public class RemoveSoapHeadersInterceptor implements HttpRequestInterceptor{

	String contentLength = null;
	public RemoveSoapHeadersInterceptor(int length){
		
		super();
		this.contentLength = String.valueOf(length);
		
	}
	/* (non-Javadoc)
	 * @see org.apache.http.HttpRequestInterceptor#process(org.apache.http.HttpRequest, org.apache.http.protocol.HttpContext)
	 */
	public void process(HttpRequest request, HttpContext context)
			throws HttpException, IOException {
		System.out.println("request in RemoveSoapHeadersInterceptor -->" +request.getClass().getTypeName());
		if (request instanceof HttpEntityEnclosingRequest) {
			System.out.println("request in RemoveSoapHeadersInterceptor -->1");
			
            if (request.containsHeader(HTTP.TRANSFER_ENCODING)) {
            	System.out.println("request in RemoveSoapHeadersInterceptor -->2");
                request.removeHeaders(HTTP.TRANSFER_ENCODING);
            }
            if (request.containsHeader(HTTP.CONTENT_LEN)) {
            	System.out.println("request in RemoveSoapHeadersInterceptor -->3");
            	System.out.println("Content-Length in RemoveSoapHeadersInterceptor -->4" +request.getHeaders(HTTP.CONTENT_LEN));
                request.removeHeaders(HTTP.CONTENT_LEN);
                request.setHeader(HTTP.CONTENT_LEN, contentLength);
                System.out.println("Content-Length in RemoveSoapHeadersInterceptor -->5 set");
            }
        }
		
	}

}
