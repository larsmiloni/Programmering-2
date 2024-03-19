package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import edu.ntnu.mappe.gruppe50.view.popups.CreateItemDialogBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class for the {@link CreateItemDialogBox} view. Is responsible for handling adding and
 * deleting items in response to user input. The class extends {@link DialogController} which
 * provides methods for displaying alert boxes.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class CreateItemDialogController extends DialogController {

  /**
   * Empty constructor that instantiates the controller.
   */
  public CreateItemDialogController() {

  }

  /**
   * Adds an item to the ItemRegister that stores all selectable items in the
   * {@link edu.ntnu.mappe.gruppe50.view.scenes.GoalSelector} view.
   *
   * @param items     The ItemRegister that stores all selectable items.
   * @param itemField The text field containing the text for creating a new item.
   * @param errorMsg  The label that is displayed if the item cannot be added.
   * @param parent    The {@link CreateItemDialogBox} view class that updates the list view of
   *                  selectable items.
   */
  public void addItem(ItemRegister items, TextField itemField, Label errorMsg,
      CreateItemDialogBox parent) {
    errorMsg.setOpacity(0);
    String item = itemField.getText();
    try {
      items.addItem(item);
    } catch (IllegalArgumentException iae) {
      formatErrorMsg(errorMsg, iae.getMessage());
    }

    parent.updateObservableList();
    parent.clearFields(itemField);
  }

  /**
   * Removes an item from the ItemRegister that stores all selectable items in the
   * {@link edu.ntnu.mappe.gruppe50.view.scenes.GoalSelector} view.
   *
   * @param items  The ItemRegister that stores all selectable items.
   * @param item   The item - string that is removed from items.
   * @param parent The {@link CreateItemDialogBox} view class that updates the list view of
   *               selectable items.
   */
  public void removeItem(ItemRegister items, String item, CreateItemDialogBox parent) {
    try {
      items.removeItem(item);
    } catch (Exception e) {
      displayErrorBox("Could not delete item", e.getMessage());
    }
    parent.updateObservableList();
  }

  /**
   * Formats an error message. The text is set to the parameter value. The labels opacity is then
   * set to 1 (previously 0 - transparent) to display the message.
   *
   * @param errorMsg Label displaying the error message.
   * @param text     The text that the label will take
   */
  public void formatErrorMsg(Label errorMsg, String text) {
    errorMsg.setText(text);
    errorMsg.setOpacity(1);
  }
}
