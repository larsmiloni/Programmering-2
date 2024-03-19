package edu.ntnu.mappe.gruppe50.model.data;

import java.util.Objects;

/**
 * Class that represents UserStoryInfo. UserStoryInfo are representations of a user created story,
 * identified by the story file's file name, file path and number of broken links.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.05.2023.
 */
public class UserStoryInfo {

  private final String fileName;
  private final String filePath;
  private final int numBrokenLinks;

  /**
   * Instantiates an instance of the UserStoryInfo class.
   *
   * @param fileName       The name of the user created story file.
   * @param filePath       The absolute file path of the user created story file.
   * @param numBrokenLinks The number of broken links in the user created story.
   * @throws IllegalArgumentException If the file name or file path are null or blank or if the
   *                                  number of broken links is negative.
   */
  public UserStoryInfo(String fileName, String filePath, int numBrokenLinks) {
    if (fileName == null) {
      throw new IllegalArgumentException("The UserStoryInfo file name cannot be null");
    }
    if (fileName.isBlank()) {
      throw new IllegalArgumentException("The UserStoryInfo file name cannot be blank");
    }
    if (filePath == null) {
      throw new IllegalArgumentException("The UserStoryInfo file path cannot be null");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("The UserStoryInfo file path cannot be blank");
    }
    if (numBrokenLinks < 0) {
      throw new IllegalArgumentException(
          "The UserStoryInfo's number of broken links cannot be negative");
    }

    this.fileName = fileName;
    this.filePath = filePath;
    this.numBrokenLinks = numBrokenLinks;
  }

  /**
   * Gets the file name of the story file.
   *
   * @return File name of UserStoryInfo instance as a string.
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Gets the file path of the story file.
   *
   * @return File path of UserStoryInfo instance as a string.
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Gets the number of broken links in the story file.
   *
   * @return Number of broken links as an integer.
   */
  public int getNumBrokenLinks() {
    return numBrokenLinks;
  }

  /**
   * Equals method. Checks if two UserStoryInfos are equivalent. Equivalence is achieved if the file
   * paths are identical.
   *
   * @param o The object that the caller is compared to.
   * @return True, if the file paths are identical.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserStoryInfo that)) {
      return false;
    }
    return getFilePath().equals(that.getFilePath());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFilePath());
  }
}
