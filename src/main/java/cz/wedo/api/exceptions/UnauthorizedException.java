package cz.wedo.api.exceptions;

/**
 * Exception that is thrown when a user is not authorized to perform a certain action.
 */
public class UnauthorizedException extends Exception {
  /**
   * Creates a new instance of the UnauthorizedException class with the specified message.
   *
   * @param message The detail message.
   */
  public UnauthorizedException(String message) {
    super(message);
  }

  /**
   * Exception that is thrown when a user is not authorized to perform a certain action.
   *
   * @param message    The detail message.
   * @param statusCode status code
   */
  public UnauthorizedException(String message, int statusCode) {
    super(message + statusCode);
  }
}