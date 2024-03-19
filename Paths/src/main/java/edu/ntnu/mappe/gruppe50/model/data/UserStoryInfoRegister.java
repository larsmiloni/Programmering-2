package edu.ntnu.mappe.gruppe50.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a register for UserStoryInfo. This class allows for saving all the user
 * created stories to a model class which can be saved using file persistence.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.05.2023
 */
public class UserStoryInfoRegister {

  private final List<UserStoryInfo> userStoryInfos;

  /**
   * Constructor that instantiates the userStoryInfo List. This list stores all the userStoryInfo.
   */
  public UserStoryInfoRegister() {
    this.userStoryInfos = new ArrayList<>();
  }

  /**
   * Returns a deep copied list of the class's UserStoryInfos.
   *
   * @return A deep copied list of UserStoryInfo objects.
   */
  public List<UserStoryInfo> getUserStoryInfos() {
    return new ArrayList<>(userStoryInfos);
  }

  /**
   * Returns a list of the file paths of all the added user created stories in the register.
   *
   * @return A list containing the user created story file paths as strings.
   */
  public List<String> getUserStoryInfoPaths() {
    List<String> userStoryInfoPaths = new ArrayList<>();
    for (UserStoryInfo userStoryInfo : getUserStoryInfos()) {
      userStoryInfoPaths.add(userStoryInfo.getFilePath());
    }
    return userStoryInfoPaths;
  }

  /**
   * Adds a UserStoryInfo instance to the register.
   *
   * @param userStoryInfo The userStoryInfo object that is being added to the register.
   * @return True, if the userStoryInfo is added. Else returns false.
   * @throws IllegalArgumentException If the UserStoryInfo is null or has already been added.
   */
  public boolean addUserStoryInfo(UserStoryInfo userStoryInfo) throws IllegalArgumentException {
    if (userStoryInfo == null) {
      throw new IllegalArgumentException("Cannot add userStoryInfo because it is null");
    }
    if (userStoryInfos.contains(userStoryInfo)) {
      throw new IllegalArgumentException("A Story with the same file path has already been added");
    }
    return userStoryInfos.add(userStoryInfo);
  }

  /**
   * Removes a UserStoryInfo instance from the register.
   *
   * @param userStoryInfo The userStoryInfo object that is being removed from the register.
   * @return True, if the userStoryInfo is removed. Else returns false.
   * @throws IllegalArgumentException If the userStoryInfo is null or is not inside the register.
   */
  public boolean removeUserStoryInfo(UserStoryInfo userStoryInfo) throws IllegalArgumentException {
    if (!userStoryInfos.contains(userStoryInfo)) {
      throw new IllegalArgumentException("UserStoryInfo is not in UserStoryInfoRegister");
    }
    if (userStoryInfo == null) {
      throw new IllegalArgumentException("UserStoryInfo cannot be null");
    }
    return userStoryInfos.remove(userStoryInfo);
  }


  /**
   * Method that checks if the register contains a userStoryInfo equal to the parameter
   * userStoryInfo. The result is indicated by the boolean value.
   *
   * @param userStoryInfo The register checks if this userStoryInfo is already added.
   * @return True, if the userStoryInfo already exists in the register. Else, returns false.
   */
  public boolean contains(UserStoryInfo userStoryInfo) {
    return userStoryInfos.contains(userStoryInfo);
  }

  /**
   * Checks if the register is empty.
   *
   * @return True, if the list size is zero.
   */
  public boolean isEmpty() {
    return this.userStoryInfos.size() == 0;
  }
}

