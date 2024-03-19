package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.util.Objects;

/**
 * Class that implements the Action interface. Represents a gold action that changes the gold of the
 * player
 *
 * @author Harry Linrui Xu and Lars Mikkel Lødeng Nilsen
 * @since 10.02.2023
 */
public class GoldAction implements Action {

  private final int gold;

  /**
   * Creates an instance of gold action.
   *
   * @param gold The gold value by which the player gold will be changed.
   */
  public GoldAction(int gold) {
    this.gold = gold;
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

    if (this.gold >= 0) {
      player.addGold(this.gold);
    } else {
      player.removeGold(-1 * this.gold);
    }
  }

  /**
   * ToString method for GoldAction.
   *
   * @return Returns a string of the type of action plus the value of the action
   */
  @Override
  public String toString() {
    return "{Gold:" + gold + "}";
  }


  /**
   * Equals method that compares the content of a gold action.
   *
   * @param o Object that the gold action is compared against.
   * @return True, if the objects are equal. Else, returns false.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GoldAction that)) {
      return false;
    }
    return gold == that.gold;
  }

  @Override
  public int hashCode() {
    return Objects.hash(gold);
  }
}
