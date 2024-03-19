package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Goal interface. Represents a gold goal that compares the minimumGold
 * variable to the players gold.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class GoldGoal implements Goal {

  private final int minimumGold;

  /**
   * Creates an instance of gold goal.
   *
   * @param minimumGold The gold value that is compared to the player's gold.
   * @throws IllegalArgumentException if minimumGold is less than zero.
   */
  public GoldGoal(int minimumGold) throws IllegalArgumentException {
    if (minimumGold < 0) {
      throw new IllegalArgumentException("The minimum gold cannot be less than zero.");
    }
    this.minimumGold = minimumGold;
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
    return player.getGold() >= minimumGold;
  }

  /**
   * Returns the goal type enum of the goal. In this case, the type is GOLD.
   *
   * @return An instance of Goal type.
   */
  public GoalType getType() {
    return GoalType.GOLD;
  }

  /**
   * Returns the minimum gold value of the goal.
   *
   * @return An integer representing the minimum gold value.
   */
  public int getValue() {
    return this.minimumGold;
  }

  /**
   * Returns a gold goal as a formatted string, as opposed to its reference.
   *
   * @return Gold goal as string.
   */
  @Override
  public String toString() {
    return "<Gold:" + minimumGold + ">";
  }

  /**
   * Checks if the goal calling the method is equal to the argument goal. They are equal if both
   * minimumGoal values are identical.
   *
   * @param o The goal that is compared against the method caller.
   * @return True if the values are equal, else returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GoldGoal goldGoal)) {
      return false;
    }
    return minimumGold == goldGoal.minimumGold;
  }

  @Override
  public int hashCode() {
    return Objects.hash(minimumGold);
  }
}
