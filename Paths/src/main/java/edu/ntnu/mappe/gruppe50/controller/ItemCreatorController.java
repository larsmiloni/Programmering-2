package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import edu.ntnu.mappe.gruppe50.model.fileutils.ItemFileHandling;
import edu.ntnu.mappe.gruppe50.view.popups.CreateItemDialogBox;
import edu.ntnu.mappe.gruppe50.view.scenes.GoalSelector;
import java.util.Optional;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

/**
 * Controller class that is used in the {@link GoalSelector} for handling the list of selectable
 * items that can be selected when creating InventoryGoals.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class ItemCreatorController extends DialogController {

  /**
   * Empty constructor that instantiates the controller.
   */
  public ItemCreatorController() {

  }

  /**
   * Displays the {@link CreateItemDialogBox} that shows the list of selectable items that can be
   * used to create InventoryGoals in the {@link GoalSelector} view.
   *
   * @param items        The list of items that stores the selectable items.
   * @param parent       The {@link GoalSelector} view that updates the CheckComboBox for selecting
   *                     items.
   * @param value        The text field in the GoalSelector view, which is cleared to prevent bugs.
   * @param itemSelector The CheckComboBox for selecting items in the GoalSelector view, which is
   *                     cleared to prevent bugs.
   * @return An instance of ItemRegister containing all selectable items for InventoryGoals.
   */
  public ItemRegister viewSelectableItems(ItemRegister items, GoalSelector parent, TextField value,
      CheckComboBox<String> itemSelector) {
    CreateItemDialogBox createItemDialog = new CreateItemDialogBox(items);

    Optional<ItemRegister> result = createItemDialog.showAndWait();

    if (result.isPresent()) {
      items = result.get();
    }

    parent.updateItemSelectorList();
    parent.clearFields(value, itemSelector);
    return items;
  }

  /**
   * Writes an ItemRegister object to a file, specified by the method parameter.
   *
   * @param items    The ItemRegister that will be written to file.
   * @param filePath The absolute path of the file.
   */
  public void writeItemRegisterToFile(ItemRegister items, String filePath) {
    try {
      ItemFileHandling.writeItemsToFile(items, filePath);
    } catch (Exception e) {
      displayErrorBox("Could not save the ItemRegister to file", e.getMessage());
    }
  }

  /**
   * Reads an ItemRegister rom a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return The ItemRegister that was read from file.
   */
  public ItemRegister readItemRegisterFromFile(String filePath) {
    try {
      return ItemFileHandling.readItemsFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not read empty ItemsRegister from file", e.getMessage());
    }
    return new ItemRegister();
  }
}
