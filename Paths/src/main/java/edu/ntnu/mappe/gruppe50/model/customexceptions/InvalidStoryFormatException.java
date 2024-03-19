package edu.ntnu.mappe.gruppe50.model.customexceptions;

/**
 * The InvalidStoryFormatException extends Exception and is a throwable that is thrown whenever one
 * tries to read or write incorrectly formatted .paths files. The exception is checked and must thus
 * be handled or rethrown by the calling methods.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class InvalidStoryFormatException extends Exception {

  /**
   * Creates a new InvalidStoryFormatException with a detailed message.
   *
   * @param message The detailed message.
   */
  public InvalidStoryFormatException(String message) {
    super(message);
  }

  /**
   * Creates a new InvalidStoryFormatException with a detailed message and a cause.
   *
   * @param message The detailed message.
   * @param cause   The exception that triggered the creation of the InvalidStoryFormatException.
   */
  public InvalidStoryFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
