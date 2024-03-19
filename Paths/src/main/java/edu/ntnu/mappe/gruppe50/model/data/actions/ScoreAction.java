package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Action interface. Represents a score action that changes the score of
 * the player
 *
 * @author Harry Linrui Xu and Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class ScoreAction implements Action {

  private final int score;

  /**
   * Creates an instance of a score action.
   *
   * @param score The score value by which the player score will be changed.
   */
  public ScoreAction(int score) {
    this.score = score;
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

    if (this.score >= 0) {
      player.addScore(this.score);
    } else {
      player.removeScore(-1 * this.score);
    }
  }

  /**
   * ToString method for ScoreAction.
   *
   * @return Returns a string of the type of action plus the value of the action
   */
  @Override
  public String toString() {
    return "{Score:" + score + "}";
  }

  /**
   * Equals method that compares the content of a score action.
   *
   * @param o Object that the score action is compared against.
   * @return True, if the objects are equal. Else, returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ScoreAction that)) {
      return false;
    }
    return score == that.score;
  }

  @Override
  public int hashCode() {
    return Objects.hash(score);
  }
}
