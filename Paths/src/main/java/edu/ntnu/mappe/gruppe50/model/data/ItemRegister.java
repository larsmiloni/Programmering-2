package edu.ntnu.mappe.gruppe50.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an item register. The item register is used to track the items that can be
 * added in an inventory goal in the GUI. The user can create their own items, which in this program
 * are just simple strings.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class ItemRegister {

  private final List<String> items;

  /**
   * Constructor for the ItemRegister. Instantiates the list of items.
   */
  public ItemRegister() {
    items = new ArrayList<>();
  }

  /**
   * Returns a deep copied arraylist of the list of items.
   *
   * @return Deep copied arraylist, containing a list of items.
   */
  public List<String> getItems() {
    return new ArrayList<>(items);
  }

  /**
   * Adds an item to the item register.
   *
   * @param item A string that represents an item in the player inventory.
   * @throws IllegalArgumentException If the item is null, blank or has already been added to the
   *                                  item register.
   */
  public void addItem(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Cannot add item that is null");
    }
    if (item.isBlank()) {
      throw new IllegalArgumentException("Cannot add item that is is blank");
    }
    if (items.contains(item)) {
      throw new IllegalArgumentException("Item has already been added");
    }

    items.add(item);
  }

  /**
   * Removes an item from the item register.
   *
   * @param item A string that acts as an item in the inventory of the player.
   * @throws IllegalArgumentException If the goal is null, if the item is blank or if item is not
   *                                  contained in the item register.
   */
  public void removeItem(String item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Cannot remove item because it is null");
    }
    if (item.isBlank()) {
      throw new IllegalArgumentException("Cannot remove item because it is blank");
    }
    if (!items.contains(item)) {
      throw new IllegalArgumentException("Cannot remove an item that is not in the register");
    }

    items.remove(item);
  }

  /**
   * Returns a concatenated string of the item register items.
   *
   * @return A string of item strings.
   */
  @Override
  public String toString() {
    StringBuilder inventoryBuilder = new StringBuilder();
    for (String item : this.items) {
      inventoryBuilder.append(item).append("\n");
    }
    return inventoryBuilder.toString();
  }
}
