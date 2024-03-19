package edu.ntnu.mappe.gruppe50.model.data.goals;

/**
 * Enum for the four types a goal can have. Used as a replacement for strings, making refactoring
 * easier. The class also limits the types a goal can take to four versions - Gold, Health, Score
 * and Inventory.
 *
 * @author Harry Linrui Xu
 * @since 6.4.2023
 */
public enum GoalType {

  /**
   * Is the goal of type gold.
   */
  GOLD,

  /**
   * Is the goal of type health.
   */
  HEALTH,

  /**
   * Is the goal of type inventory.
   */
  INVENTORY,

  /**
   * Is the goal of type score.
   */
  SCORE;

  /**
   * Method for finding the goal type, based on an input string. The four possibilities are the enum
   * values in this class. The method works similar to a factory.
   *
   * @param text The input text that is converted to a GoalType.
   * @return An instance of GoalType, based on if the text matches the four cases.
   * @throws IllegalArgumentException If the input string does not match the four cases.
   */
  public static GoalType of(String text) {
    return switch (text) {
      case "Gold" -> GoalType.GOLD;
      case "Health" -> GoalType.HEALTH;
      case "Inventory" -> GoalType.INVENTORY;
      case "Score" -> GoalType.SCORE;
      default -> throw new IllegalArgumentException("Goal type does not exist");
    };
  }
}
