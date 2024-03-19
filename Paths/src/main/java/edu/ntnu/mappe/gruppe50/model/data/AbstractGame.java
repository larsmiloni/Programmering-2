package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.util.List;

/**
 * This class is an abstract game, which is the "publisher" in an observer pattern that classes can
 * inherit from or extend. The class has a list of goals of the goal interface that are its
 * subscribers.
 *
 * @author Harry Linrui Xu
 * @since 18.04.2023
 */
public abstract class AbstractGame {

  List<Goal> goals;

  /**
   * Takes in a list of goals that are used to instantiate classes that inherit from this class, and
   * the local goals variable that contain the goal subscribers.
   *
   * @param goals List of goals that are assigned to the local goals variable.
   */
  public AbstractGame(List<Goal> goals) {
    if (goals == null) {
      throw new IllegalArgumentException("Goals cannot be null");
    }

    this.goals = goals;
  }

  /**
   * Adds a goal to the list of goal subscribers.
   *
   * @param goal An object used to change the state of a player.
   * @throws IllegalArgumentException If the goal is null or has already been added.
   */
  public void addGoal(Goal goal) throws IllegalArgumentException {
    if (goal == null) {
      throw new IllegalArgumentException("Goal cannot be null");
    }
    if (goals.contains(goal)) {
      throw new IllegalArgumentException("The same goal has already been added");
    }

    goals.add(goal);
  }

  /**
   * Removes a goal from the list of goal subscribers.
   *
   * @param goal An object used to change the state of a player.
   * @throws IllegalArgumentException If the goal is null or is not in the register.
   */
  public void removeGoal(Goal goal) throws IllegalArgumentException {
    if (goal == null) {
      throw new IllegalArgumentException("Goal cannot be null");
    }
    if (!goals.contains(goal)) {
      throw new IllegalArgumentException(
          "Cannot remove the goal, because it is not in the goals list");
    }

    goals.remove(goal);
  }

  /**
   * Updates all the listeners, by notifying them using a player.
   *
   * @param player The player character that the goal states can be changed by.
   * @throws IllegalArgumentException If the player is null.
   */
  public void update(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    goals.forEach(goal -> goal.isFulfilled(player));
  }
}
