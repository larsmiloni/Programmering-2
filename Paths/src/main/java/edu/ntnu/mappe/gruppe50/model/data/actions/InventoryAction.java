package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Action interface. Represents an inventory action that changes the
 * inventory of the player
 *
 * @author Harry Linrui Xu and Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class InventoryAction implements Action {

  private final String item;

  /**
   * Creates an instance of an inventory action.
   *
   * @param item An item which will be added or removed from the player inventory.
   * @throws IllegalArgumentException if item is null or blank.
   */
  public InventoryAction(String item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    if (item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be blank");
    }
    this.item = item;
  }

  /**
   * Changes the inventory of the player by adding or removing the item attribute from the player
   * inventory.
   *
   * @param player The player whose state will be influenced by the action.
   * @throws IllegalArgumentException if player is null.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    player.addToInventory(this.item);
  }

  /**
   * ToString method for InventoryAction.
   *
   * @return Returns a string of the type of action plus the value of the action
   */
  @Override
  public String toString() {
    return "{Inventory:" + item + "}";
  }

  /**
   * Equals method that compares the content of an inventory action.
   *
   * @param o Object that the inventory action is compared against.
   * @return True, if the objects are equal. Else, returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InventoryAction that)) {
      return false;
    }
    return Objects.equals(item.toLowerCase(), that.item.toLowerCase());
  }

  @Override
  public int hashCode() {
    return Objects.hash(item);
  }
}

