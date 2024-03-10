package cz.wedo.api.services;

import com.google.gson.Gson;
import cz.wedo.api.exceptions.BadRequestException;
import cz.wedo.api.exceptions.UnauthorizedException;
import cz.wedo.api.definitions.API;
import cz.wedo.api.definitions.RequestType;
import cz.wedo.api.models.errors.ErrorMessageException;
import cz.wedo.api.services.commons.RequesterBase;
import cz.wedo.api.utils.ErrorUtils;
import cz.wedo.api.utils.GsonUtils;
import cz.wedo.api.utils.HttpBasicAuth;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;

import cz.wedo.api.utils.FileUtils;

/**
 * Requester class is used to make HTTP requests to an API.
 *
 * @param <T> The type of the request body.
 * @param <K> The type of the response body.
 */
@Slf4j
public class Requester<T, K> extends RequesterBase {
  /**
   * Constructs a new Requester object with the provided API credentials and SSL verification settings.
   *
   * @param apiUser   the API username to be used for authentication
   * @param apiKey    the API key to be used for authentication
   * @param sslVerify specifies whether SSL verification should be performed
   */
  public Requester(String apiUser, String apiKey, Boolean sslVerify) {
    this.apiUser = apiUser;
    this.apiKey = apiKey;
    this.sslVerify = sslVerify;

    this.validator = new Validator();
  }

  /**
   * Call API GET.
   * Makes a method call with the specified parameters.
   *
   * @param version          the API version to use for the method call
   * @param request          the request string for the method call
   * @param data             the data to be sent with the method call
   * @param shouldHaveStatus whether the method call should have a status
   * @param gzip             whether gzip compression should be used for the method call
   * @param responseType     the Type of the method call
   * @param gsonInstance     the Gson instance to use for serialization and deserialization
   * @param customer         the customer name for authorization
   * @param department       the department name for authorization
   * @return the result of the method call
   * @throws UnauthorizedException if the method call is unauthorized
   * @throws BadRequestException   if the method call is bad request
   * @throws ErrorMessageException if there is an error message returned from the method call
   */
  public K callGet(
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      Gson gsonInstance,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callWithParams(RequestType.GET, version, request, data, shouldHaveStatus, gzip, responseType, gsonInstance, customer, department);
  }

  /**
   * Call API GET.
   * Calls the API with the given request parameters.
   *
   * @param version          The API version to use.
   * @param request          The request endpoint to call.
   * @param data             The data to include in the request.
   * @param shouldHaveStatus A boolean value indicating whether the response should have a status.
   * @param gzip             A boolean value indicating whether the request should be gzip-encoded.
   * @param responseType     The type of the request.
   * @param customer         The customer associated with the request.
   * @param department       The department associated with the request.
   * @return The result of the API call.
   * @throws UnauthorizedException If the API call is unauthorized.
   * @throws BadRequestException   If the API call is bad request.
   * @throws ErrorMessageException If the API call encounters an error.
   */
  public K callGet(
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callWithParams(RequestType.GET, version, request, data, shouldHaveStatus, gzip, responseType, GsonUtils.getGsonStandardDate(), customer, department);
  }

  /**
   * Call API GET.
   * Saves the given data to a file.
   *
   * @param version          The API version to use for the request.
   * @param request          The request to be sent.
   * @param data             The data to be saved.
   * @param shouldHaveStatus A boolean indicating whether the response should have a status.
   * @param gzip             A boolean indicating whether to gzip the data before saving.
   * @param targetFile       The file to save the data to.
   * @return true if the data was successfully saved to the file, false otherwise.
   * @throws UnauthorizedException If the request is unauthorized.
   * @throws BadRequestException   If the request is invalid.
   * @throws ErrorMessageException If an error message is received.
   */
  public boolean callGetSaveResponseToFile(
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      String targetFile
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callSaveToFile(RequestType.GET, version, request, data, shouldHaveStatus, gzip, targetFile);
  }

  /**
   * Makes a POST request using Gson to deserialize the response into an object of the specified type, with a specified date format.
   *
   * @param version          The API version to use.
   * @param request          The URL path for the request.
   * @param data             The data to be sent in the request body.
   * @param shouldHaveStatus Whether the response should have a HTTP status code.
   * @param gzip             Whether to compress the request body using gzip.
   * @param responseType     The type of the object to deserialize the response into.
   * @param customer         The customer associated with the request.
   * @param department       The department associated with the request.
   * @return An object of type K representing the response.
   * @throws UnauthorizedException If the request is unauthorized.
   * @throws BadRequestException   If the request is invalid.
   * @throws ErrorMessageException If an error occurs during the request.
   */
  public K callPostGsonTDate(
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callWithParams(RequestType.POST, version, request, data, shouldHaveStatus, gzip, responseType, GsonUtils.getGsonTDate(), customer, department);
  }

  /**
   * Makes a POST request to a specified API endpoint.
   *
   * @param version          The API version to use.
   * @param request          The API endpoint to send the POST request to.
   * @param requestBodyClass The data to be sent in the request body.
   * @param shouldHaveStatus A flag indicating whether the response should have a specific status.
   * @param gzip             A flag indicating whether the request body should be gzipped.
   * @param responseType     The type of the data in the request body.
   * @param customer         The customer identifier.
   * @param department       The department identifier.
   * @return The response from the API.
   * @throws UnauthorizedException If the request is unauthorized.
   * @throws BadRequestException   If the request is malformed.
   * @throws ErrorMessageException If an error occurs while making the request.
   */
  public K callPostObject(
      API version,
      String request,
      T requestBodyClass,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callPostObject(RequestType.POST, version, request, requestBodyClass, shouldHaveStatus, gzip, responseType, customer, department);
  }

  /**
   * Executes an HTTP PUT request to the specified API endpoint.
   *
   * @param version          The version of the API.
   * @param request          The API endpoint to call.
   * @param data             The data to be sent with the request.
   * @param shouldHaveStatus Indicates whether the response status should be checked.
   * @param gzip             Indicates whether the request should be compressed using gzip.
   * @param responseType     The responseType value for the request.
   * @param customer         The customer value for the request.
   * @param department       The department value for the request.
   * @return The response from the server.
   * @throws UnauthorizedException If the request is unauthorized.
   * @throws BadRequestException   If the request is malformed.
   * @throws ErrorMessageException If an error message is returned by the server.
   */
  public K callPut(
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callWithParams(RequestType.PUT, version, request, data, shouldHaveStatus, gzip, responseType, GsonUtils.getGsonStandardDate(), customer, department);
  }

  /**
   * Makes a DELETE request to the specified API endpoint with the given parameters.
   *
   * @param version          The version of the API to use.
   * @param request          The URL path of the API endpoint to make the request to.
   * @param data             The additional data to pass with the request.
   * @param shouldHaveStatus A boolean indicating whether the response should have a valid status.
   * @param gzip             A boolean indicating whether to enable gzip compression for the request.
   * @param responseType     The type of the data in the response.
   * @param customer         The customer name to include in the request.
   * @param department       The department name to include in the request.
   * @return The response data of the DELETE request.
   * @throws UnauthorizedException If the request is unauthorized.
   * @throws BadRequestException   If the request is invalid or malformed.
   * @throws ErrorMessageException If the request returns an error message.
   */
  public K callDelete(
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    return callWithParams(RequestType.DELETE, version, request, data, shouldHaveStatus, gzip, responseType, GsonUtils.getGsonStandardDate(), customer, department);
  }

  /**
   * Makes a call to the API server with the given parameters and returns the result.
   *
   * @param type             The type of the request (GET, POST, PUT, DELETE).
   * @param version          The version of the API.
   * @param request          The endpoint of the API.
   * @param data             The data to be sent with the request.
   * @param shouldHaveStatus A flag indicating whether the response should have a status code.
   * @param gzip             A flag indicating whether the request should be gzipped.
   * @param responseType     The type of the response.
   * @param gsonInstance     An instance of Gson to be used for JSON serialization and deserialization.
   * @param customer         The customer information.
   * @param department       The department information.
   * @return The result of the API call.
   * @throws UnauthorizedException If the API call fails due to authorization issues.
   * @throws BadRequestException   If the API call fails due to incorrect request parameters.
   * @throws ErrorMessageException If the API call returns an error message.
   */
  public K callWithParams(
      RequestType type,
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      Gson gsonInstance,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    Gson gson = gsonInstance != null ? gsonInstance : GsonUtils.getGsonStandardDate();
    try {
      ResultHostPath resultUrlPath = getResultHostPath(version, request);
      // call API server and get response
      HttpResponse response = this.requestWithParams(resultUrlPath.host, resultUrlPath.path, data, type, customer, department);
      // get status code and content
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode < 300) {
        return processGsonResponse(responseType, response, gson);
      } else {
        throw ErrorUtils.getErrorMessageException(response.getEntity(), statusCode, gsonInstance);
      }
    } catch (ErrorMessageException e) {
      log.error(String.format("Error Message Exception: %s", e.getMessage()), e);
      throw e;
    } catch (Exception e) {
      log.error(String.format("Exception: %s", e.getMessage()), e);
    }
    return null;
  }

  /**
   * Makes a call to the API server with the given parameters and returns the response.
   *
   * @param type             The type of the request (GET, POST, PUT, DELETE).
   * @param version          The version of the API to call.
   * @param request          The request path.
   * @param requestBodyClass The body of the request.
   * @param shouldHaveStatus A boolean indicating if the response should have status (statusCode < 300) or not.
   * @param gzip             A boolean indicating if the request should be gzipped.
   * @param responseType     The expected response type.
   * @param customer         The customer name.
   * @param department       The department name.
   * @return The response from the API server.
   * @throws UnauthorizedException If the API returns an unauthorized error.
   * @throws BadRequestException   If the API returns a bad request error.
   * @throws ErrorMessageException If the API returns an error message in the response.
   */
  public K callPostObject(
      RequestType type,
      API version,
      String request,
      T requestBodyClass,
      Boolean shouldHaveStatus,
      Boolean gzip,
      Type responseType,
      String customer,
      String department
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    Gson gson = GsonUtils.getGsonStandardDate();
    try {
      ResultHostPath resultUrlPath = getResultHostPath(version, request);
      // call API server and get response
      HttpResponse response = this.requestPostObject(resultUrlPath.host, resultUrlPath.path, requestBodyClass, type, customer, department);
      // get status code and content
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode < 300) {
        return processGsonResponse(responseType, response, gson);
      } else {
        throw ErrorUtils.getErrorMessageException(response.getEntity(), statusCode);
      }
    } catch (ErrorMessageException e) {
      log.error(String.format("Error Message Exception: %s", e.getMessage()), e);
      throw e;
    } catch (Exception e) {
      log.error(String.format("Exception: %s", e.getMessage()), e);
    }
    return null;
  }

  /**
   * Saves the response from an API call to a file.
   *
   * @param type             The type of request (e.g., GET, POST).
   * @param version          The API version.
   * @param request          The API request endpoint.
   * @param data             The data to send in the request. Use a HashMap to represent key-value pairs.
   * @param shouldHaveStatus A flag indicating whether the response should have a status code.
   * @param gzip             A flag indicating whether the response should be gzipped.
   * @param targetFile       The file path to save the response to.
   * @return true if the response is saved successfully, false otherwise.
   * @throws UnauthorizedException if the API request is unauthorized.
   * @throws BadRequestException   if the API request is invalid.
   * @throws ErrorMessageException if an error message is returned from the API.
   */
  public boolean callSaveToFile(
      RequestType type,
      API version,
      String request,
      HashMap<Object, Object> data,
      Boolean shouldHaveStatus,
      Boolean gzip,
      String targetFile
  ) throws UnauthorizedException, BadRequestException, ErrorMessageException {
    try {
      ResultHostPath resultUrlPath = getResultHostPath(version, request);
      // call API server and get response
      HttpResponse response = this.requestWithParams(resultUrlPath.host, resultUrlPath.path, data, type, null, null);
      // get status code and content
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode < 300) {
        log.info(String.format("Returned response from API data size: %d", response.getEntity().getContentLength()));
        FileUtils.saveResponseEntityToFile(targetFile, response);
        return true;
      } else {
        throw ErrorUtils.getErrorMessageException(response.getEntity(), statusCode);
      }
    } catch (ErrorMessageException e) {
      log.error(String.format("Error Message Exception: %s", e.getMessage()), e);
      throw e;
    } catch (Exception e) {
      log.error(String.format("Exception: %s", e.getMessage()), e);
    }
    return false;
  }

  /**
   * Sends an HTTP request to the specified URL.
   *
   * @param url        The URL to send the request to.
   * @param path       The path of the URL.
   * @param data       The data to be sent with the request.
   * @param type       The type of request (GET, POST, PUT, DELETE).
   * @param customer   The customer associated with the request.
   * @param department The department associated with the request.
   * @return The response received from the server.
   */
  public HttpResponse requestWithParams(URL url, String path, HashMap<Object, Object> data, RequestType type, String customer, String department) {
    try {
      CloseableHttpClient httpClient = prepareHttpClient();
      switch (type) {
        case GET:
          return processGet(url, path, data, httpClient, customer, department);
        case POST:
          return processPost(url, path, data, httpClient, customer, department);
        case PUT:
          return processPut(url, path, data, httpClient);
        case DELETE:
          return processDelete(url, path, data, httpClient);
      }
    } catch (Exception e) {
      log.error(String.format("Exception: %s", e.getMessage()), e);
    }

    return null;
  }

  /**
   * Sends a POST request to the specified URL with the given parameters.
   *
   * @param url        the URL to send the request to
   * @param path       the path of the request
   * @param requestBody          the data to be sent with the request
   * @param type       the type of the request (POST, GET, PUT, DELETE)
   * @param customer   the customer name
   * @param department the department name
   * @return the HTTP response received from the server
   * @throws BadRequestException if the request type is not implemented
   */
  public HttpResponse requestPostObject(URL url, String path, T requestBody, RequestType type, String customer, String department) {
    try {
      CloseableHttpClient httpClient = prepareHttpClient();
      switch (type) {
        case POST:
          return processPost(url, path, requestBody, httpClient, customer, department);
        case GET:
        case PUT:
        case DELETE:
        default:
          throw new BadRequestException("Not implemented");
      }
    } catch (Exception e) {
      log.error(String.format("Exception: %s", e.getMessage()), e);
    }

    return null;
  }

  /**
   * Processes a GET request to the specified URL with the provided parameters.
   *
   * @param url         the base URL of the request
   * @param path        the endpoint path of the request
   * @param data        the query parameters to be appended to the URL
   * @param httpClient the HTTP client used to execute the request
   * @param customer    the customer header value (optional)
   * @param department  the department header value (optional)
   * @return the HTTP response for the GET request
   * @throws IOException if an I/O error occurs during the request execution
   */
  private HttpResponse processGet(URL url, String path, HashMap<Object, Object> data, CloseableHttpClient httpClient, String customer, String department) throws IOException {
    String urlStr = assembleGetUrl(url, path, data);
    log.debug(String.format("Sending GET request to %s", urlStr));
    HttpGet request = new HttpGet(urlStr);
    if (customer != null) {
      // todo pro V1: request.setHeader("X-WEDO-Customer", customer);
    }
    addHeaders(department, request);

    return httpClient.execute(request);
  }

  /**
   * Process a POST request to the specified URL with the given data using the provided HTTP client.
   *
   * @param url        the URL to send the POST request to
   * @param path       the path to append to the URL
   * @param data       the data to include in the request body as JSON
   * @param httpClient the HTTP client to use for making the request
   * @param customer   the customer to include in the request header (optional)
   * @param department the department to include in the request header (optional)
   * @return the HTTP response received from the server
   * @throws IOException if an I/O error occurs during the request
   */
  private HttpResponse processPost(URL url, String path, HashMap<Object, Object> data, CloseableHttpClient httpClient, String customer, String department) throws IOException {
    final HttpPost request = preparePostRequest(url, path);

    addPostEntity(data, request);
    if (customer != null) {
      // todo V1: request.setHeader("X-InTime-Customer", customer);
      // todo V2: request.setHeader("X-WEDO-Customer", customer);
    }
    addHeaders(department, request);

    return httpClient.execute(request);
  }

  /**
   * Sends a POST request to the specified URL with the given data.
   *
   * @param url The base URL to send the request to
   * @param path The path to append to the base URL
   * @param requestBody The data to send in the request body
   * @param httpClient The instance of CloseableHttpClient to use for the request
   * @param customer The customer identifier
   * @param department The department identifier
   * @return The HTTP response received from the server
   * @throws IOException if an I/O error occurs while sending the request
   */
  private HttpResponse processPost(URL url, String path, T requestBody, CloseableHttpClient httpClient, String customer, String department) throws IOException {
    final HttpPost request = preparePostRequest(url, path);

    addPostEntity(requestBody, request);
    if (customer != null) {
      // todo V1: request.setHeader("X-InTime-Customer", customer);
      // todo V2: request.setHeader("X-WEDO-Customer", customer);
    }
    addHeaders(department, request);
    request.setHeader("X-WEDO-Auto-Complete", "true");

    return httpClient.execute(request);
  }

  /**
   * todo Process a PUT request to the specified URL with the given data using the provided HTTP client.
   *
   * @param url        The URL to send the request to.
   * @param path       The path of the request.
   * @param data       The data to send in the request body.
   * @param httpClient The HTTP client to use for the request.
   * @return The HTTP response received from the server.
   * @throws IOException If an I/O error occurs while sending the request or receiving the response.
   */
  private HttpResponse processPut(URL url, String path, HashMap<Object, Object> data, CloseableHttpClient httpClient) throws IOException {
    return null;
  }

  /**
   * Processes a DELETE request to the specified URL with the given data using the provided HttpClient.
   *
   * @param url The base URL of the request.
   * @param path The path of the request to be appended to the base URL.
   * @param data The data to be sent with the request.
   * @param httpClient The HttpClient instance to perform the request.
   * @return The HttpResponse object representing the response from the DELETE request.
   * @throws IOException If an I/O error occurs while executing the request.
   */
  private HttpResponse processDelete(URL url, String path, HashMap<Object, Object> data, CloseableHttpClient httpClient) throws IOException {
    String urlStr = assembleGetUrl(url, path, data);
    log.info(String.format("Sending DELETE request to %s", urlStr));
    HttpDelete request = new HttpDelete(urlStr);
    request.setHeader("Authorization", HttpBasicAuth.getBasicAuthenticationHeader(apiUser, apiKey));

    return httpClient.execute(request);
  }
}