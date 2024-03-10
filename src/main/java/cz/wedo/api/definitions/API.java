package cz.wedo.api.definitions;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * The API class provides an enumeration of different API environments and their corresponding URLs.
 */
@Slf4j
public enum API {
  /**
   * Production environment.
   */
  PROD("PROD"),

  /**
   * Production environment v2.
   */
  PRODV2("PRODV2"),

  /**
   * TEST environment.
   */
  TEST("TEST"),

  /**
   * TEST environment v2.
   */
  TESTV2("TESTV2");

  public final String label;

  /**
   * Represents an API environment with a corresponding label.
   * The label is used to identify different API environments.
   */
  API(String label) {
    this.label = label;
  }

  /**
   * Retrieves the API environment corresponding to the given label.
   *
   * @param label the label used to identify the API environment
   * @return the API environment corresponding to the label, or null if no match found
   */
  public static API valueOfLabel(String label) {
    for (API e : values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }

  /**
   * API URL.
   */
  public static final HashMap<API, URL> URL = new HashMap<>() {{
    try {
      put(PROD, new URL("https://bridge.intime.cz/its-rapi/v1/"));
      put(TEST, new URL("https://bridge.intime.cz/api-test/v1/"));
      put(PRODV2, new URL("https://api.wedo.cz/v2/"));
      put(TESTV2, new URL("https://api.test.wedo.cz/v2/"));
    } catch (MalformedURLException e) {
      log.error(String.format("Exception getting URL: %s", e.getMessage()), e);
    }
  }};

  /**
   * Resolves the hostname for the given API version and returns the corresponding URL.
   *
   * @param version the API version to resolve the hostname for
   * @return the URL corresponding to the API version, or the URL for the PROD version if the specified version is not found
   */
  public static URL resolveHostName(API version) {
    final URL url = API.URL.get(version);
    return url != null ? url : API.URL.get(API.PROD);
  }

}
