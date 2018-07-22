package com.mm.wszib.client.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import com.mm.wszib.client.Client;
import com.mm.wszib.client.CustomRestTemplate;
import com.mm.wszib.client.CustomValidation;
import com.mm.wszib.client.User;

public class RestTemplateClientTest {
	
	@Test
    public void testCustomRestTemplate() {
		CustomRestTemplate crt = new CustomRestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(crt);
        mockServer.expect(requestTo("http://localhost:1234/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("SUCCESS", MediaType.APPLICATION_JSON));
        
        ResponseEntity<String> result = 
        		crt.postForEntity("http://localhost:1234/register", 
        				new User("test", "test", "test@gmail.com", "12312321"), String.class);

        mockServer.verify();
        assertThat(result.getBody(),containsString("SUCCESS"));
        assertEquals(result.getStatusCode(),HttpStatus.OK);
    }
	
	@Test
	public void testLogin() {
		Client client = new Client();
		client.login("test", "test");
		client.login("admin", "a");
	}
	
	@Test
	public void testValidation() {
		assertTrue(CustomValidation.validateEmail("maciekm92@gmail.com"));
		assertTrue(CustomValidation.validatePassword("abc#AbA123123"));
		assertTrue(CustomValidation.validateUsername("maciej"));
		
		assertFalse(CustomValidation.validateEmail("asdasdsadsa.com"));
		assertFalse(CustomValidation.validatePassword("abc"));
		assertFalse(CustomValidation.validatePassword("abc#########"));
		assertFalse(CustomValidation.validatePassword("abc12312321321A"));
		assertFalse(CustomValidation.validateUsername("m"));
	}
}
