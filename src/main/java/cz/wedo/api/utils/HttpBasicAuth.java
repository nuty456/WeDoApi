package cz.wedo.api.utils;

import java.util.Base64;

/**
 * This class provides methods for creating HTTP Basic Authentication headers.
 */
public class HttpBasicAuth {
  /**
   * This class provides methods for creating HTTP Basic Authentication headers.
   */
  public static String getBasicAuthenticationHeader(String username, String password) {
    String valueToEncode = username + ":" + password;
    return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
  }
}
