package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Goal interface. Represents a health goal that compares the
 * minimumHealth variable to the player's health.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class HealthGoal implements Goal {

  private final int minimumHealth;

  /**
   * Creates an instance of health goal.
   *
   * @param minimumHealth The health value that is compared to the player's health.
   * @throws IllegalArgumentException if minimumHealth is less than zero.
   */
  public HealthGoal(int minimumHealth) throws IllegalArgumentException {
    if (minimumHealth < 0) {
      throw new IllegalArgumentException("The minimum health cannot be less than zero.");
    }
    this.minimumHealth = minimumHealth;
  }

  /**
   * Returns the goal type enum of the goal. In this case, the type is HEALTH.
   *
   * @return An instance of GoalType.
   */
  public GoalType getType() {
    return GoalType.HEALTH;
  }

  /**
   * Returns the minimum health value of the goal.
   *
   * @return An integer representing the minimum health value.
   */
  public int getValue() {
    return this.minimumHealth;
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
    return player.getHealth() >= minimumHealth;
  }

  /**
   * Returns a health goal as a formatted string, as opposed to its reference.
   *
   * @return Health goal as string.
   */
  @Override
  public String toString() {
    return "<Health:" + minimumHealth + ">";
  }

  /**
   * Checks if the goal calling the method is equal to the argument goal. They are equal if both
   * minimumHealth values are identical.
   *
   * @param o The goal that is compared against the method caller.
   * @return True if the values are equal, else returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HealthGoal that)) {
      return false;
    }
    return minimumHealth == that.minimumHealth;
  }

  @Override
  public int hashCode() {
    return Objects.hash(minimumHealth);
  }
}
