/**
 * @(#)CustomRestTemplate.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes the custom 
 * 				 implementation of RestTemplate CLass
 */

package com.mm.wszib.client;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.web.client.RestTemplate;

public class CustomRestTemplate extends RestTemplate {
	
	private final HttpClient httpClient;
    private final CookieStore cookieStore;
    private final HttpContext httpContext;
    private final CustomHttpRequestFactory statefullHttpComponentsClientHttpRequestFactory;

    public CustomRestTemplate() {
        super();
        httpClient = HttpClientBuilder.create().build();
        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, getCookieStore());
        statefullHttpComponentsClientHttpRequestFactory = new CustomHttpRequestFactory(httpClient, httpContext);
        super.setErrorHandler(new CustomRestTemplateErrorHandler());
        super.setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public CustomHttpRequestFactory getStatefulHttpClientRequestFactory() {
        return statefullHttpComponentsClientHttpRequestFactory;
    }
	
}
