package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Goal interface. Represents a score goal that compares the minimumScore
 * variable to the players score.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class ScoreGoal implements Goal {

  private final int minimumScore;

  /**
   * Creates an instance of score goal.
   *
   * @param minimumScore The score value that is compared to the player's score.
   * @throws IllegalArgumentException if minimumScore is less than zero.
   */
  public ScoreGoal(int minimumScore) throws IllegalArgumentException {
    if (minimumScore < 0) {
      throw new IllegalArgumentException("The minimum score cannot be less than zero.");
    }
    this.minimumScore = minimumScore;
  }

  /**
   * Returns the goal type enum of the goal. In this case, the type is SCORE.
   *
   * @return An instance of GoalType
   */
  public GoalType getType() {
    return GoalType.SCORE;
  }

  /**
   * Returns the minimum score value of the goal.
   *
   * @return An integer representing the minimum score value.
   */
  public int getValue() {
    return this.minimumScore;
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
    return player.getScore() >= minimumScore;
  }

  /**
   * Returns a score goal as a formatted string, as opposed to its reference.
   *
   * @return Score goal as string.
   */
  @Override
  public String toString() {
    return "<Score:" + minimumScore + ">";
  }

  /**
   * Checks if the goal calling the method is equal to the argument goal. They are equal if both
   * minimumScore values are identical.
   *
   * @param o The goal that is compared against the method caller.
   * @return True if the values are equal, else returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ScoreGoal scoreGoal)) {
      return false;
    }
    return minimumScore == scoreGoal.minimumScore;
  }

  @Override
  public int hashCode() {
    return Objects.hash(minimumScore);
  }
}
