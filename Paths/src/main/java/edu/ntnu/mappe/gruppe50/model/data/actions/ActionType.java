package edu.ntnu.mappe.gruppe50.model.data.actions;

/**
 * Enum for the four types an action can have. Used as a replacement for strings, making refactoring
 * easier. The class also limits the types an action can take on to four versions - Gold, Health,
 * Score and Inventory.
 *
 * @author Harry Linrui Xu
 * @since 18.04.2023
 */
public enum ActionType {

  /**
   * Is the action of type gold.
   */
  GOLD,

  /**
   * Is the action of type gold.
   */
  HEALTH,

  /**
   * Is the action of type gold.
   */
  SCORE,

  /**
   * Is the action of type gold.
   */
  INVENTORY;

  /**
   * Method for finding the action type, based on an input string. The four possibilities are the
   * enum values in this class. The method works similar to a factory.
   *
   * @param text The input text that is converted to an ActionType.
   * @return An instance of ActionType, based on if the text matches the four cases.
   * @throws IllegalArgumentException If the input string does not match the four cases.
   */
  public static ActionType of(String text) {
    return switch (text) {
      case "Gold" -> ActionType.GOLD;
      case "Health" -> ActionType.HEALTH;
      case "Inventory" -> ActionType.INVENTORY;
      case "Score" -> ActionType.SCORE;
      default -> throw new IllegalArgumentException("Action type does not exist");
    };
  }
}
