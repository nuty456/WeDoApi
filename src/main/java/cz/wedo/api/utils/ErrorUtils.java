package cz.wedo.api.utils;

import com.google.gson.Gson;
import cz.wedo.api.models.errors.ErrorMessageException;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * The ErrorUtils class provides utility methods for handling error messages.
 */
public class ErrorUtils {

  /**
   * Retrieves an ErrorMessageException object from the given JSON content using Gson library.
   *
   * @param content the JSON content containing the error message exception details
   * @return an ErrorMessageException object
   */
  public static ErrorMessageException getErrorMessageException(String content) {
    Gson gson = GsonUtils.getGsonStandardDate();
    return gson.fromJson(content, ErrorMessageException.class);
  }

  /**
   * Retrieves an ErrorMessageException object from the given HTTP entity and status code using Gson library.
   *
   * @param httpEntity the HTTP entity containing the error message exception details
   * @param statusCode the status code associated with the error response
   * @return an ErrorMessageException object populated with the error message details
   */
  public static ErrorMessageException getErrorMessageException(HttpEntity httpEntity, int statusCode) {
    try {
      String content = EntityUtils.toString(httpEntity);
      Gson gson = GsonUtils.getGsonStandardDate();
      ErrorMessageException errorMessageException = gson.fromJson(content, ErrorMessageException.class);
      errorMessageException.setStatusCode(statusCode);
      return errorMessageException;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieves an ErrorMessageException object from the given HTTP entity, status code, and Gson object.
   *
   * @param httpEntity The HTTP entity containing the error message exception details
   * @param statusCode The status code associated with the error response
   * @param gson The Gson object used to parse the JSON content
   * @return An ErrorMessageException object populated with the error message details
   * @throws RuntimeException if an IOException occurs while reading the HTTP entity
   */
  public static ErrorMessageException getErrorMessageException(HttpEntity httpEntity, int statusCode, Gson gson) {
    try {
      String content = EntityUtils.toString(httpEntity);
      ErrorMessageException errorMessageException = gson.fromJson(content, ErrorMessageException.class);
      errorMessageException.setStatusCode(statusCode);
      return errorMessageException;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
