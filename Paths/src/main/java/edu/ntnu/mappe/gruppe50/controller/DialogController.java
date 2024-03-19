package edu.ntnu.mappe.gruppe50.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Abstract class for displaying dialog boxes. Controllers usually have the responsibility of
 * displaying alerts to notify the user of invalid inputs, as this relates to user input. The
 * methods therefore come in handy for extending classes.
 *
 * @author Harry Linrui Xu
 * @since 16.05.2023
 */
public abstract class DialogController {

  /**
   * Shows an error box. Warns the player that an error in the program has occurred.
   *
   * @param header  The header and title text of the dialog box.
   * @param context The context of the dialog error.
   */
  public void displayErrorBox(String header, String context) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(header);
    alert.setHeaderText(header);
    alert.setContentText(context);

    alert.show();
  }

}
