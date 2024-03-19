package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Action interface. Represents a health action that changes the health of
 * the player.
 *
 * @author Harry Linrui Xu and Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class HealthAction implements Action {

  private final int health;

  /**
   * Creates an instance of health action.
   *
   * @param health The health value by which the player health will be changed.
   */
  public HealthAction(int health) {
    this.health = health;
  }

  /**
   * Method that changes the state of the player.
   *
   * @param player The player whose state will be influenced by the action.
   * @throws IllegalArgumentException If the player is null.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    if (this.health >= 0) {
      player.addHealth(this.health);
    } else {
      player.removeHealth(-1 * this.health);
    }
  }

  /**
   * ToString method for HealthAction.
   *
   * @return Returns a string of the type of action plus the value of the action
   */
  @Override
  public String toString() {
    return "{Health:" + health + "}";
  }

  /**
   * Equals method that compares the content of a health action.
   *
   * @param o Object that the health action is compared against.
   * @return True, if the objects are equal. Else, returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HealthAction that)) {
      return false;
    }
    return health == that.health;
  }

  @Override
  public int hashCode() {
    return Objects.hash(health);
  }
}

