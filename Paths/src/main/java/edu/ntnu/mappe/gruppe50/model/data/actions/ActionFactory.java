package edu.ntnu.mappe.gruppe50.model.data.actions;

/**
 * Factory for different action types. Builds a specific action, depending on its input parameters.
 * The benefit of this factory is the loose coupling it introduces between the Ui and the object
 * itself, because it abstracts the instantiation details. The class calling the factory has no idea
 * how the action is built.
 *
 * @author Harry Linrui Xu
 * @since 18.04.2023
 */
public class ActionFactory {

  /**
   * Builds an action of a given type and value. The type is taken is as an ActionType enum and the
   * value is taken in as a string. The added benefit of taking in the value as a string is that it
   * can be converted to both an integer and a list.
   *
   * @param type  The type of the action as an enum.
   * @param value The value of the action as a string.
   * @return An action of a type and value, specified by the input parameters.
   * @throws IllegalArgumentException If the action type does not exist or if the value cannot be
   *                                  converted to a valid integer.
   */
  public static Action buildAction(ActionType type, String value) throws IllegalArgumentException {
    if (!validateInput(type, value)) {
      throw new IllegalArgumentException("Type cannot be null and value cannot be blank");
    }

    int val = 0;
    if (type != ActionType.INVENTORY) {
      val = parseValue(value);
    }

    return switch (type) {
      case GOLD -> new GoldAction(val);
      case HEALTH -> new HealthAction(val);
      case SCORE -> new ScoreAction(val);
      case INVENTORY -> new InventoryAction(value);
    };
  }

  /**
   * Method for quickly validating that the action type is not null and that the value is not
   * blank.
   *
   * @param type  The type of the action - Gold, Health, Score or Inventory.
   * @param value The value of the action.
   * @return True, if the fields are valid - not null or blank.
   */
  private static boolean validateInput(ActionType type, String value) {
    return type != null && !value.isBlank();
  }

  /**
   * Method for parsing an input string and returning it. Checks if the string is parsable.
   *
   * @param value The string input that is parsed.
   * @return Integer value of the string input if it can be parsed.
   * @throws IllegalArgumentException If the string cannot be parsed.
   */
  private static int parseValue(String value) {
    int val;
    try {
      val = Integer.parseInt(value);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Value is not a positive integer");
    }
    return val;
  }
}
