package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;

/**
 * Interface for a goal. A goal represents a desired result that is tied to the player state. While
 * an action changes the state of the player throughout the game, the goal makes it possible to
 * check if the player has fulfilled an expected result.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public interface Goal {

  /**
   * Method that checks if the player has fulfilled a goal.
   *
   * @param player A player whose state will be checked.
   * @return True, if the goal is satisfied by the player.
   * @throws IllegalArgumentException If the player is null.
   */
  boolean isFulfilled(Player player) throws IllegalArgumentException;
}
