/**
 * @(#)CustomRestTemplate.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/22
 * @description: Class which describes the custom 
 * 				 error handling used by CustomRestTemplate
 */

package com.mm.wszib.client;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class CustomRestTemplateErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return (response.getStatusCode().series() == Series.CLIENT_ERROR || 
				response.getStatusCode().series() == Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		 if (response.getStatusCode()
		          .series() == HttpStatus.Series.SERVER_ERROR) {
		            // handle SERVER_ERROR
		        } else if (response.getStatusCode()
		          .series() == HttpStatus.Series.CLIENT_ERROR) {
		            // handle CLIENT_ERROR
		            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
		                throw new IOException("not found");
		            }
		        }
	}

}
