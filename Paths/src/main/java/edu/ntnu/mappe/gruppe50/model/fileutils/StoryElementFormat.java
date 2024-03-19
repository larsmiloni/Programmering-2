package edu.ntnu.mappe.gruppe50.model.fileutils;

import java.util.regex.Pattern;

/**
 * Regex enum class for story elements. Story elements are the building blocks of a story -
 * passages, links, goals and actions. This class offers regular expressions that validate whether a
 * string input has the corrected formatting for these elements.
 *
 * @author Harry Linrui Xu
 * @since 15.05.2023
 */
public enum StoryElementFormat {

  /**
   * A passage title always starts with "::", followed by any characters. The enum only validates
   * the base formatting being the double colons.
   */
  PASSAGE_TITLE(Pattern.compile("::(.*)", Pattern.CASE_INSENSITIVE)),

  /**
   * A link has the format "[text](reference)" where text and reference can be nearly anything. The
   * enum only validates the base formatting being the bracket pairs.
   */
  LINK(Pattern.compile("\\[.*]\\(.*\\)", Pattern.CASE_INSENSITIVE)),

  /**
   * An action has the format "{type:value}". Though type and value have to be strictly letters,
   * numbers or spaces, this enum only validates the base formatting with the curly braces and
   * colon.
   */
  ACTION(Pattern.compile("\\{(.*):(.*)}", Pattern.CASE_INSENSITIVE)),

  /**
   * A goal has the format &lt;type:value&gt; with triangle brackets. Though type and value have to
   * be strictly letters, numbers, square brackets or spaces, this enum only validates the base
   * formatting with the square brackets and colon.
   */
  GOAL(Pattern.compile("<(.*):(.*)+>", Pattern.CASE_INSENSITIVE));

  private final Pattern regex;

  /**
   * Takes in a pattern which is used to validate against inputs.
   *
   * @param pattern A pattern that has the value of one of the enum values above, which will be used
   *                to validate against input values.
   */
  StoryElementFormat(Pattern pattern) {
    this.regex = pattern;
  }

  /**
   * Attempts to match the chosen pattern with the input string.
   *
   * @param text A string input string which is evaluated against the pattern.
   * @return True, if the there is a match. Else, returns false.
   */
  public Boolean matches(String text) {
    return regex.matcher(text).find();
  }

}
