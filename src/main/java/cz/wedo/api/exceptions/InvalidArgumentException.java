package cz.wedo.api.exceptions;

/**
 * The `InvalidArgumentException` class is an exception that is thrown when an invalid argument is passed to a method or constructor.
 * It extends the `Exception` class and provides a way to convey error information to the calling code.
 */
public class InvalidArgumentException extends Exception {
  /**
   * Creates a new instance of the `InvalidArgumentException` class with the specified error message.
   *
   * @param message the error message
   */
  public InvalidArgumentException(String message) {
    super(message);
  }
}
