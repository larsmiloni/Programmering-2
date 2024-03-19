package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.actions.ActionFactory;
import edu.ntnu.mappe.gruppe50.model.data.actions.ActionType;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalFactory;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Base class for file handling related to story elements. A story element is story and its
 * constituent parts, being passages, links, goals and actions. The class provides methods for
 * reading and validating these elements.
 *
 * @author Harry Linrui Xu
 * @since 15.05.2023
 */
public abstract class BaseStoryFileHandling {

  /**
   * Checks if the input string is a passage title.
   *
   * @param input The input string.
   * @return True, if the string is a passage title. Else, returns false.
   */
  public static boolean isPassageTitle(final String input) {
    return StoryElementFormat.PASSAGE_TITLE.matches(input);
  }

  /**
   * Checks if the string input is passage content.
   *
   * @param line     The line before the possible passage content, that should be a passage title.
   * @param nextLine The line of the possible passage content.
   * @return True, if the previous line is a passage title AND that the nextLine is not a passage
   *     title OR a link OR an action. Else, returns false.
   */
  public static boolean isPassageContent(String line, String nextLine) {
    return isPassageTitle(line) && !isPassageTitle(nextLine) && !isLink(nextLine) && !isAction(
        nextLine);
  }

  /**
   * Checks if the input string is a link.
   *
   * @param input The input string.
   * @return True, if the string is a link. Else, returns false.
   */
  public static boolean isLink(final String input) {
    return StoryElementFormat.LINK.matches(input);
  }

  /**
   * Checks if the input string is an action.
   *
   * @param input The input string.
   * @return True, if the string is an action. Else, returns false.
   */
  public static boolean isAction(final String input) {
    return StoryElementFormat.ACTION.matches(input);

  }

  /**
   * Checks if the input string is a goal.
   *
   * @param input The input string.
   * @return True, if the string is a goal. Else, returns false.
   */
  public static boolean isGoal(final String input) {
    return StoryElementFormat.GOAL.matches(input);

  }

  /**
   * Method for making a Passage from a .paths file. The method replaces the starting :: with an
   * empty string.
   *
   * @param line     first line of a passage in the .paths file. Should be a passage title.
   * @param nextLine the line that comes after the passage title in the file.
   * @return a Passage from the .paths file.
   */
  public static Passage readPassageFromFile(String line, String nextLine) {
    String passageTitle = line.replace("::", "");
    return new Passage(passageTitle, nextLine);
  }

  /**
   * Method for making a Link from a .paths file. The method extracts a text and reference from the
   * string and creates a link.
   *
   * @param line line of a link in the .paths file.
   * @return a Link from the .paths file.
   */
  public static Link readLinkFromFile(String line) {
    return new Link(line.substring(1, line.indexOf("]")),
        line.substring(line.indexOf("(") + 1, line.length() - 1));
  }

  /**
   * Method for reading an action from a .paths file. The method extracts a type and a value from
   * the string and creates an action.
   *
   * @param line The line that the action is read from.
   * @return An action using the substrings created from the line.
   * @throws IllegalArgumentException If the type or value parameters are invalid.
   */
  public static Action readActionFromFile(String line) throws IllegalArgumentException {
    String typeAsString = line.substring(1, line.indexOf(":"));
    ActionType type = ActionType.of(typeAsString);
    String value = line.substring(line.indexOf(":") + 1, line.indexOf("}"));

    return ActionFactory.buildAction(type, value);
  }

  /**
   * Method for reading a goal from a .paths file. The method extracts a type and value which are
   * used to create a goal.
   *
   * @param line The line that the goal is read from.
   * @return A goal using the substrings created from the line.
   * @throws IllegalArgumentException If the type or value parameters are invalid.
   */
  public static Goal readGoalFromFile(String line) throws IllegalArgumentException {
    String typeAsString = line.substring(1, line.indexOf(":"));
    GoalType type = GoalType.of(typeAsString);
    String value = line.substring(line.indexOf(":") + 1, line.indexOf(">"));

    return GoalFactory.buildGoal(type, value);
  }

  /**
   * Clears an entire file, specified by the parameter.
   *
   * @param filePath The absolute file path of the file.
   * @throws IOException If the file cannot be written to file.
   */
  public static void removeAllLinesFromFile(String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath)) {
      writer.write("");
    } catch (IOException e) {
      throw new IOException("Failed to empty file: " + filePath, e);
    }
  }
}
