package edu.ntnu.mappe.gruppe50.model.customexceptions;

/**
 * The NoMatchingPassageException extends Exception and is a throwable that is thrown whenever it is
 * attempted to retrieve a passage with an invalid link - the link references nothing. The exception
 * is checked and must thus be handled or rethrown by the calling methods.
 *
 * @author Harry Linrui Xu
 * @since 11.03.2023
 */
public class NoMatchingPassageException extends Exception {

  /**
   * Creates a new NoMatchingPassageException with a detailed message.
   *
   * @param message The detailed message.
   */
  public NoMatchingPassageException(String message) {
    super(message);
  }


  /**
   * Creates a new NoMatchingPassageException with a detailed message and a cause.
   *
   * @param message The detailed message.
   * @param cause   The exception that triggered the creation of the NoMatchingPassageException.
   */
  public NoMatchingPassageException(String message, Throwable cause) {
    super(message, cause);
  }
}
