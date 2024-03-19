package edu.ntnu.mappe.gruppe50.model.data.goals;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a register for storing goals. The register was primarily created for its
 * ease of writing to file thanks to its handy toString method. As all goal types inherit from an
 * interface, the register can store several types of goals.
 *
 * @author Harry Linrui Xu
 * @since 8.4.2023
 */
public class GoalsRegister {

  private final List<Goal> goals;

  /**
   * Constructor that instantiates the goals List. This list stores all the goals.
   */
  public GoalsRegister() {
    this.goals = new ArrayList<>();
  }

  /**
   * Gets a deep copied list of the goals in the register.
   *
   * @return A deep copied list of goals.
   */
  public List<Goal> getGoals() {
    return new ArrayList<>(goals);
  }

  /**
   * Adds a goal to the register.
   *
   * @param goal The goal object that is being added to the register.
   * @throws IllegalArgumentException If the goal is null or if the goal is a duplicate entry.
   */
  public void addGoal(Goal goal) throws IllegalArgumentException {
    if (goal == null) {
      throw new IllegalArgumentException("Cannot add null to the register");
    }
    if (goals.contains(goal)) {
      throw new IllegalArgumentException("Cannot add duplicate goal to the register");
    }

    goals.add(goal);
  }

  /**
   * Removes a goal from the Goals register.
   *
   * @param goal The goal object that is being added to the register.
   * @throws IllegalArgumentException If the goal is null.
   */
  public void removeGoal(Goal goal) throws IllegalArgumentException {
    if (goal == null) {
      throw new IllegalArgumentException("Goal cannot remove null");
    }

    goals.remove(goal);
  }

  /**
   * Clears the register. All goals are removed from the list.
   */
  public void clearRegister() {
    this.goals.clear();
  }

  /**
   * Method that checks if the register contains a goal equal to the parameter goal. The result is
   * indicated by the boolean value.
   *
   * @param goal The register checks if this goal is already added.
   * @return True, if the goal already exists in the register. Else, returns false.
   */
  public boolean contains(Goal goal) {
    return goals.contains(goal);
  }

  /**
   * Checks if the register is empty.
   *
   * @return True, if the list size is zero.
   */
  public boolean isEmpty() {
    return this.goals.size() == 0;
  }

  /**
   * Creates and String that represents the goalsregister. The string consists of all the goals that
   * make up the register
   *
   * @return A string version of the goal register.
   */
  @Override
  public String toString() {
    StringBuilder goalsAsString = new StringBuilder();
    for (Goal goal : this.goals) {
      goalsAsString.append(goal.toString()).append("\n");
    }
    return goalsAsString.toString();
  }
}
