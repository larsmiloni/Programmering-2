package edu.ntnu.mappe.gruppe50.model.customexceptions;

/**
 * The InvalidLinkFormatException extends Exception and is a throwable that is thrown whenever one
 * tries to read or write incorrectly formatted .links files. The exception is checked and must thus
 * be handled or rethrown by the calling methods.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class InvalidLinkFormatException extends Exception {

  /**
   * Creates a new InvalidLinkFormatException with a detailed message.
   *
   * @param message The detailed message.
   */
  public InvalidLinkFormatException(String message) {
    super(message);
  }

  /**
   * Creates a new InvalidLinkFormatException with a detailed message and a cause.
   *
   * @param message The detailed message.
   * @param cause   The exception that triggered the creation of the InvalidLinkFormatException.
   */
  public InvalidLinkFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
