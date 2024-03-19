package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;

/**
 * Interface for an Action object. An action is an event that represents a future change in the
 * player state. This includes the player's health, gold, score and inventory.
 *
 * @author Harry Linrui Xu and Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public interface Action {

  /**
   * Method that changes the state of the player.
   *
   * @param player The player whose state will be influenced by the action.
   * @throws IllegalArgumentException If the player is null.
   */
  void execute(Player player) throws IllegalArgumentException;
}
