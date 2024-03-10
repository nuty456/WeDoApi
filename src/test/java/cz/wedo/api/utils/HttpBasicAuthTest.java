package cz.wedo.api.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;

/**
 * The `HttpBasicAuthTest` class contains test methods for the `HttpBasicAuth` class.
 */
public class HttpBasicAuthTest {
  
    /**
     * Test for the `getBasicAuthenticationHeader` method.
     * It checks if the method generates correct basic authentication header.
     */
    @Test
    public void testGetBasicAuthenticationHeader() {
        String username = "test_user";
        String password = "test_password";
        
        String expectedAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        String actualAuthHeader = HttpBasicAuth.getBasicAuthenticationHeader(username, password);
        
        Assert.assertEquals(expectedAuthHeader, actualAuthHeader);
    }
  
    /**
     * Test for the `getBasicAuthenticationHeader` method with empty username and password.
     * It checks if the method generates correct basic authentication header with empty username and password.
     */
    @Test
    public void testGetBasicAuthenticationHeaderWithEmptyCredentials() {
        String username = "";
        String password = "";

        String expectedAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        String actualAuthHeader = HttpBasicAuth.getBasicAuthenticationHeader(username, password);

        Assert.assertEquals(expectedAuthHeader, actualAuthHeader);
    }
}