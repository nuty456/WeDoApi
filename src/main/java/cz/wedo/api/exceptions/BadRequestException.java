package cz.wedo.api.exceptions;

import java.util.HashMap;

/**
 * The BadRequestException class is an exception that is thrown when a bad request is made.
 */
public class BadRequestException extends Exception {
  /**
   * Constructs a new BadRequestException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method).
   */
  public BadRequestException(String message) {
    super(message);
  }

  /**
   * The BadRequestException class is an exception that is thrown when a bad request is made.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method).
   * @param code    error code
   */
  public BadRequestException(String message, Integer code) {
    super(String.format("%s - %d", message, code));
  }

  /**
   * The BadRequestException class is an exception that is thrown when a bad request is made.
   *
   * @param response response hash map
   */
  public BadRequestException(HashMap<Object, Object> response) {
  }
}