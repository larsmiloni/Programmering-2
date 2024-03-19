package edu.ntnu.mappe.gruppe50.model.data.goals;

import java.util.Arrays;

/**
 * Factory for different goal types. Builds a specific goal, depending on its input parameters. The
 * benefit of this factory is the loose coupling it introduces between the Ui and the object itself,
 * because it abstracts the instantiation details. The class calling the factory has no idea how the
 * goal is built
 *
 * @author Harry Linrui Xu
 * @since 11.04.2023
 */
public class GoalFactory {

  /**
   * Builds a goal of a given type and value. The type is taken is as GoalType. The value is taken
   * in as a string, as it can be converted to both an integer and a list.
   *
   * @param type  The type of the goal.
   * @param value The value of the goal as a string.
   * @return A goal of a type and value, specified by the input parameters.
   * @throws IllegalArgumentException If the goal type does not exist, is null, or if the value
   *                                  cannot be converted to a valid integer
   */
  public static Goal buildGoal(GoalType type, String value) throws IllegalArgumentException {
    if (!validateInput(type, value)) {
      throw new IllegalArgumentException("Type cannot be null and value cannot be blank");
    }

    int val = 0;
    if (type != GoalType.INVENTORY) {
      val = parseValue(value);
    }

    return switch (type) {
      case GOLD -> new GoldGoal(val);
      case HEALTH -> new HealthGoal(val);
      case SCORE -> new ScoreGoal(val);
      case INVENTORY ->
          new InventoryGoal(Arrays.asList(value.substring(1, value.length() - 1).split(", ")));
    };
  }

  /**
   * Method for quickly validating that the goal type is not null and that the value is not blank.
   *
   * @param type  The type of the goal - Gold, Health, Score or Inventory.
   * @param value The value of the goal.
   * @return True, if the fields are valid - not null or blank.
   */
  private static boolean validateInput(GoalType type, String value) {
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
