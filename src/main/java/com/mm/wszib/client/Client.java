/**
 * @(#)Client.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes how the 
 * 				 REST Service client exchanges data 
 * 				 with the REST Service server
 */

package com.mm.wszib.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;

public class Client {
	
    private final String host;
    private final int port;
    private String loginPath;
    private String logoutPath;
    private String registerPath;
    private String apiPath;
    private final String usernameField = "username";
    private final String passwordField = "password";
    private String username;
    private CustomRestTemplate template = new CustomRestTemplate();
    
    public Client() {
    	host = "localhost";
    	port = 9999;
    	loginPath = "/login";
    	logoutPath = "/logout";
    	registerPath = "/register";
    	apiPath = "/users";
    	
    }
    
    public Client(String host, int port) {
    	this.host = host;
    	this.port = port;
    	loginPath = "/login";
    	logoutPath = "/logout";
    	registerPath = "/register";
    	apiPath = "/users";
    }
    
    /**
     * Checks if the service is online
     * 
     * @return
     */
    public boolean checkHostAccessability() {
    	try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 1000); //1 second
            socket.close();
            configureBasicURIs();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Configures basic URis returned from the server
     */
    private void configureBasicURIs() {
    	CustomUri[] cuTable = template.getForObject(serveUrl()+"/", CustomUri[].class);
    	
    	for(int i=0; i < cuTable.length; i++) {
    		
    		switch(cuTable[i].getAction()) {
    		case "login": {
    			loginPath = cuTable[i].getUri();
    		};
    		break;
    		case "logout": {
    			logoutPath = cuTable[i].getUri();
    		};
    		break;
    		case "register": {
    			registerPath = cuTable[i].getUri();
    		};
    		break;
    		}
    	}
    	
    }
    
    /**
     * Method which execution allows to login 
     *  to the server by providing
     *  username and password parameters
     * 
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
    	
    	String response = "";
    	
    	MultipartBodyBuilder builder = new MultipartBodyBuilder();
    	builder.part(usernameField, username);
    	builder.part(passwordField, password);
    	
        URI location = template.postForLocation(serveUrl(loginPath), builder.build());
        
        if(location != null) {
        	if(!location.toString().equals("Unauthorized")) {
            	apiPath = location.toString();
            	this.username = username;
            	response = location.toString();
            }
        } else {
        	response = "Unauthorized";
        }
        
        return response;
    }
    
    /**
     * Sends the User object to the server
     * 
     * @param userName
     * @param password
     * @param email
     * @param phoneNumber
     * @return
     */
    public ResponseEntity<String> register(String userName, String password, String email, String phoneNumber) {
    	return template.postForEntity(serveUrl(registerPath), new User(userName, password, email, phoneNumber), String.class);
    }
    
    /**
     * Sends edit request to the server
     * (both email and phoneNumber)
     * 
     * @param email
     * @param phoneNumber
     * @return
     */
    public String editUser(String email, String phoneNumber) {
    	return template.patchForObject(serveUrl(apiPath+"/"+username), new User(username,email,phoneNumber), String.class);
    }
    
    /**
     * Sends edit request to the server
     * (only phoneNumber)
     * 
     * @param phoneNumber
     * @return
     */
    public String editUserPhoneNumber(String phoneNumber) {
    	User u = new User(username);
    	u.setPhoneNumber(phoneNumber);
    	return template.patchForObject(serveUrl(apiPath+"/"+username), u, String.class);
    }
    
    /**
     * Sends edit request to the server
     * (only email)
     * 
     * @param email
     * @return
     */
    public String editUserEmail(String email) {
    	User u = new User(username);
    	u.setEmail(email);
    	return template.patchForObject(serveUrl(apiPath+"/"+username), u, String.class);
    }
    
    /**
     * Sends logout request to the server
     * 
     * @return
     */
    public ResponseEntity<String> logout() {
        return this.template.getForEntity(serveUrl(logoutPath), String.class);
    }

    
    //GETTERS AND SETTERS
    public CustomRestTemplate getTemplate() {
        return template;
    }
    
    public String serveUrl() {
        return "http://" + host + ":" + port;
    }
    
    public String serveUrl(String relativePath) {
    	return "http://" + host + ":" + port + relativePath;
    }

    public String getHost() {
        return host;
    }
    
    public int getPort() {
    	return port;
    }
    
    public String getLoginPath() {
        return loginPath;
    }

    public String getLogoutPath() {
        return logoutPath;
    }

	public String getRegisterPath() {
		return registerPath;
	}

	public String getApiPath() {
		return apiPath;
	}
}
