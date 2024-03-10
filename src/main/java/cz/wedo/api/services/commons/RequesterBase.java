package cz.wedo.api.services.commons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import cz.wedo.api.definitions.API;
import cz.wedo.api.exceptions.BadRequestException;
import cz.wedo.api.exceptions.UnauthorizedException;
import cz.wedo.api.services.Validator;
import cz.wedo.api.utils.HttpBasicAuth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.json.JsonException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * The RequesterBase class is a base class for making API requests.
 */
@Slf4j
public class RequesterBase {
  /**
   * API User
   */
  protected String apiUser;

  /**
   * API key
   */
  protected String apiKey;

  /**
   * SSL verification enabled
   */
  protected Boolean sslVerify;

  /**
   * Response validator
   */
  protected Validator validator;

  /**
   * Processes the Gson response by converting the content of the response to the specified type using Gson.
   *
   * @param <K>           the type to convert the response content to
   * @param responseType the type representing the desired response type
   * @param response     the HttpResponse object containing the response from the API
   * @param gson         the Gson object used for deserialization
   * @return the deserialized response content of the specified type, or null if the response content cannot be deserialized
   * @throws IOException if an error occurs while processing the response content
   */
  protected static <K> K processGsonResponse(Type responseType, HttpResponse response, Gson gson) throws IOException {
    String content = EntityUtils.toString(response.getEntity());
    log.info(String.format("Returned response from API: %s", content));
    return gson.fromJson(content, responseType);
  }

  /**
   * Retrieves the ResultHostPath object for the given API version and request.
   *
   * @param version the API version to use
   * @param request the request to be processed
   * @return the ResultHostPath object containing the resolved host URL and path
   */
  protected static ResultHostPath getResultHostPath(API version, String request) {
    // resolve url
    URL host = API.resolveHostName(version);

    String path = (String.format("%s", request)).trim();
    path = path.replace("//", "/");

    return new ResultHostPath(host, path);
  }

  /**
   * The ResultHostPath class is a data holder class that represents the resolved host URL and path.
   */
  @AllArgsConstructor
  protected static class ResultHostPath {
    public final URL host;
    public final String path;
  }

  /**
   * Prepares a POST request to the specified URL and path.
   *
   * @param url The URL object representing the target URL.
   * @param path The path that will be appended to the URL.
   * @return The prepared HttpPost request object.
   * @throws IllegalArgumentException if url is null.
   */
  protected static HttpPost preparePostRequest(URL url, String path) {
    final String urlStr = String.format("%s://%s%s", url.getProtocol(), url.getHost(), url.getPath() + path);
    log.debug(String.format("Sending POST request to %s", urlStr));
    return new HttpPost(urlStr);
  }

  /**
   * Adds headers to the given HttpGet request.
   *
   * @param department the department value to be set as the header "X-WEDO-PickupPlaceCode".
   *                   Can be null if the header is not required.
   * @param request    the HttpGet request to which headers will be added.
   */
  protected void addHeaders(String department,HttpGet request) {
    if (department != null) {
      // todo V1: request.setHeader("X-InTime-Department", department);
      request.setHeader("X-WEDO-PickupPlaceCode", department);
    }
    request.setHeader("Authorization", HttpBasicAuth.getBasicAuthenticationHeader(apiUser, apiKey));
  }

  /**
   * Adds headers to the given HttpPost request.
   *
   * @param department the department code to be set in the header. Can be null.
   * @param request    the HttpPost request to add headers to.
   */
  protected void addHeaders(String department, HttpPost request) {
    if (department != null) {
      // todo V1: request.setHeader("X-InTime-Department", department);
      request.setHeader("X-WEDO-PickupPlaceCode", department);
    }
    request.setHeader("Authorization", HttpBasicAuth.getBasicAuthenticationHeader(apiUser, apiKey));
  }

  /**
   * Adds a JSON representation of the given HashMap to the entity of an HttpPost request.
   *
   * @param data     the HashMap containing the data to be added to the request entity
   * @param request  the HttpPost request to which the data will be added
   */
  protected static void addPostEntity(HashMap<Object, Object> data, HttpPost request) {
    Gson gson = new Gson();
    String json = gson.toJson(data);
    log.debug(String.format("Sending JSON data: %s", json));

    StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
    request.setEntity(requestEntity);
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-type", "application/json");
  }

  protected static <T> void addPostEntity(T t, HttpPost request) {
    Gson gson = new Gson();
    String json = gson.toJson(t);
    log.debug(String.format("Sending JSON data: %s", json));
    StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
    request.setEntity(requestEntity);
  }

  /**
   * Assembles a GET URL string using the given URL, path, and data parameters.
   *
   * @param url  The base URL to be used in the assembled URL.
   * @param path The path to be appended to the base URL.
   * @param data The data parameters to be included in the assembled URL.
   * @return The assembled GET URL as a String.
   */
  protected String assembleGetUrl(URL url, String path, HashMap<Object, Object> data) {
    String urlStr = String.format("%s://%s%s", url.getProtocol(), url.getHost(), url.getPath() + path);
    if (data != null && !data.isEmpty()) {
      urlStr += "?" + data.entrySet().stream()
          .map(p -> urlEncodeUTF8(p.getKey().toString()) + "=" + urlEncodeUTF8(p.getValue()))
          .collect(Collectors.joining("&"));
    }
    return urlStr;
  }

  /**
   * URL encodes a given object using UTF-8 encoding.
   *
   * @param s the object to be URL encoded
   * @return the URL encoded string representation of the given object
   * @throws UnsupportedOperationException if the UTF-8 encoding is not supported
   */
  protected String urlEncodeUTF8(Object s) {
    if (s instanceof Integer || s instanceof Long || s instanceof Double || s instanceof Float) {
      return URLEncoder.encode(String.valueOf(s), StandardCharsets.UTF_8);
    } else if (s != null) {
      return URLEncoder.encode(String.valueOf(s), StandardCharsets.UTF_8);
    } else {
      return "null";
    }
  }

  /**
   * Parses the given content and decodes it into a HashMap.
   * Decode API response JSON to HashMap.
   *
   * @param content the content to be parsed and decoded
   * @param throwOnError indicates whether to throw an error if the decoding fails
   * @return a HashMap containing the decoded content, or null if an error occurs and throwOnError is false
   */
  protected static HashMap<Object, Object> parseContents(String content, Boolean throwOnError) {
    try {
      return decode(content);
    } catch (JsonException exception) {
      log.error(String.format("Exception: %s", exception), exception);
    }
    return null;
  }

  /**
   * Decodes a JSON string into a HashMap<Object, Object>.
   *
   * @param content the JSON string to decode
   * @return a HashMap<Object, Object> representing the decoded JSON, or null if decoding fails
   */
  protected static HashMap<Object, Object> decode(String content) {
    try {
      TypeReference<HashMap<Object, Object>> typeRef = new TypeReference<>() {
      };
      return new ObjectMapper().readValue(content, typeRef);
    } catch (IOException e) {
      log.error(String.format("Exception: %s", e.getMessage()), e);
    }
    return null;
  }

  /**
   * Validates the response with the given status code and response body.
   *
   * @param statusCode        The HTTP status code of the response
   * @param response          The response body in the form of a HashMap
   * @param shouldHaveStatus  Determines whether the response should have a specific status
   * @throws UnauthorizedException  if the status code indicates an unauthorized request
   * @throws BadRequestException   if the status code indicates a bad request
   */
  @SneakyThrows
  protected void validateResponse(int statusCode, HashMap<Object, Object> response, Boolean shouldHaveStatus) throws UnauthorizedException, BadRequestException {
    validator.validateStatus(statusCode, response);

    validator.validateResponseStatus(response, null, shouldHaveStatus);
  }

  /**
   * Prepares and returns an HttpClient with the specified API user and key for making API requests.
   *
   * @return a CloseableHttpClient configured with the specified API user and key
   * @throws NoSuchAlgorithmException  if the SSL algorithm is not available
   * @throws KeyStoreException         if there is an issue with the keystore
   * @throws KeyManagementException    if there is an issue with the SSL key management
   */
  protected CloseableHttpClient prepareHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    CredentialsProvider provider = new BasicCredentialsProvider();
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiUser, apiKey);
    provider.setCredentials(AuthScope.ANY, credentials);

    SSLContextBuilder builder = new SSLContextBuilder();
    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        builder.build());
    return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCredentialsProvider(provider).build();
  }

}
