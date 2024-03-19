package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Class that implements the Goal interface. Represents mandatory items that the player need to
 * fulfill this goal.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class InventoryGoal implements Goal {

  private final List<String> mandatoryItems;

  /**
   * Creates an instance of inventory goal.
   *
   * @param mandatoryItems The mandatory items for the player.
   * @throws IllegalArgumentException if mandatoryItems is null or empty.
   */
  public InventoryGoal(List<String> mandatoryItems) throws IllegalArgumentException {
    if (mandatoryItems == null) {
      throw new IllegalArgumentException("The list of mandatory items cannot be null.");
    }
    if (mandatoryItems.isEmpty()) {
      throw new IllegalArgumentException("The list of mandatory items cannot be empty");
    }
    this.mandatoryItems = mandatoryItems;
  }

  /**
   * Returns the goal type enum of the goal. In this case, the type is INVENTORY.
   *
   * @return An instance of teh
   */
  public GoalType getType() {
    return GoalType.INVENTORY;
  }

  /**
   * Returns a list containing strings, which represent the mandatory items.
   *
   * @return A list of strings, representing the mandatory items.
   */
  public List<String> getValue() {
    return this.mandatoryItems;
  }

  /**
   * Method that checks if the player has fulfilled a goal.
   *
   * @param player A player whose state will be checked.
   * @throws IllegalArgumentException If the player is null.
   */
  @Override
  public boolean isFulfilled(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    return new HashSet<>(player.getInventory()).containsAll(this.mandatoryItems);
  }

  /**
   * Returns a gold goal as a formatted string, as opposed to its reference.
   *
   * @return Inventory goal as string.
   */
  @Override
  public String toString() {
    return "<Inventory:" + mandatoryItems + ">";
  }

  /**
   * Checks if the goal calling the method is equal to the argument goal. They are equal if the item
   * contents of both goals are equal. This means that the item contents in both instances are the
   * same, regardless of order.
   *
   * @param o The goal that is compared against the method caller.
   * @return True if the set of items are identical, else returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InventoryGoal that)) {
      return false;
    }
    return new HashSet<>(mandatoryItems).containsAll(that.mandatoryItems) && new HashSet<>(
        that.mandatoryItems).containsAll(mandatoryItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mandatoryItems);
  }
}
