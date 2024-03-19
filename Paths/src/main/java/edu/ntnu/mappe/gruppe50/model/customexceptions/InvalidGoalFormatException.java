package edu.ntnu.mappe.gruppe50.model.customexceptions;

/**
 * The InvalidGoalFormatException extends Exception and is a throwable that is thrown whenever one
 * tries to read or write incorrectly formatted .goals files. The exception is checked and must thus
 * be handled or rethrown by the calling methods.
 *
 * @author Harry Linrui Xu
 * @since 15.05.2023
 */
public class InvalidGoalFormatException extends Exception {

  /**
   * Creates a new InvalidGoalFormatException with a detailed message.
   *
   * @param message The detailed message.
   */
  public InvalidGoalFormatException(String message) {
    super(message);
  }

  /**
   * Creates a new InvalidGoalFormatException with a detailed message and a cause.
   *
   * @param message The detailed message.
   * @param cause   The exception that triggered the creation of the InvalidGoalFormatException.
   */
  public InvalidGoalFormatException(String message, Throwable cause) {
    super(message, cause);
  }

}
