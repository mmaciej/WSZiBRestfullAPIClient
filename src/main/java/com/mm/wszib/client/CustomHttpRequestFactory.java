/**
 * @(#)CustomHttpRequestFactory.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes how requestFactory should work
 */

package com.mm.wszib.client;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class CustomHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
	
	private final HttpContext httpContext;

    public CustomHttpRequestFactory(HttpClient httpClient, HttpContext httpContext)
    {
        super(httpClient);
        this.httpContext = httpContext;
    }

    @Override
    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri)
    {
        return httpContext;
    }
	
}
