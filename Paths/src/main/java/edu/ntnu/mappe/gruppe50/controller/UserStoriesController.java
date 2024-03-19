package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidGoalFormatException;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidStoryFormatException;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfo;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfoRegister;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalsRegister;
import edu.ntnu.mappe.gruppe50.model.fileutils.GameFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.GoalFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.StoryFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.UserStoryInfoFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.scenes.UserStories;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 * Controller for the {@link UserStories} view. The class is concerned with handling UserStories
 * provided by the user and provides methods for manipulating the {@link UserStoryInfoRegister} as
 * well as reading and writing them to file. The class extends {@link DialogController} for
 * displaying dialog * boxed and implements {@link SwitchSceneController} for switching scenes.
 *
 * @author Harry Linrui Xu
 * @since 10.05.2023
 */
public class UserStoriesController extends DialogController implements SwitchSceneController {

  /**
   * Empty constructor whose only purpose is to instantiate the controller.
   */
  public UserStoriesController() {

  }

  /**
   * Adds a story chosen by the user to a UserStoryInfoRegister and updates the view.
   *
   * @param storyRegister The register that the story is added to.
   * @param parent        The view class that displays a table of the stories.
   */
  public void addStory(UserStoryInfoRegister storyRegister, UserStories parent) {
    try {
      UserStoryInfo userStoryInfo = createUserStoryInfo();
      if (userStoryInfo != null) {
        storyRegister.addUserStoryInfo(userStoryInfo);
        parent.updateObservableList();
      }
    } catch (IndexOutOfBoundsException e) {
      displayErrorBox("Invalid UserStoryInfo format",
          "The UserStoryInfo needs a file name, a file path and the number of broken links");
    } catch (Exception e) {
      displayErrorBox("Could not add the file to the table", e.getMessage());
    }
  }

  /**
   * Deletes the selected entry from a table and removes it from the UserStoryInfoRegister.
   *
   * @param storyRegister The model register that the entry is removed from.
   * @param storyTable    The table in the view that the entry is removed from.
   * @param parent        The view class that displays a table of the stories.
   */
  public void deleteStory(UserStoryInfoRegister storyRegister, TableView<UserStoryInfo> storyTable,
      UserStories parent) {
    try {
      UserStoryInfo usi = storyTable.getSelectionModel().getSelectedItem();
      storyRegister.removeUserStoryInfo(usi);
      parent.updateObservableList();

      UserStoryInfoFileHandling.removeUserStoryInfoFromFile(usi.getFilePath(),
          System.getProperty("user.dir")
              + "/src/main/resources/userStoryInfoFiles/userStoryInfo.userStoryInfo");
      GoalFileHandling.deleteGoalsFile(
          System.getProperty("user.dir") + "/src/main/resources/goalFiles/" + usi.getFileName()
              .replace(".paths", ".goals"));
    } catch (Exception e) {
      displayErrorBox("An error occurred when deleting the story", e.getMessage());
    }
  }

  /**
   * Saves the selected story to file by replacing the first line in the game file, being the story
   * file.
   *
   * @param filePath The absolute path of the story file.
   */
  public void savedSelectedStory(String filePath) {
    try {
      GameFileHandling.replaceFirstLine(
          System.getProperty("user.dir") + "/src/main/resources/gameFiles/game.game", filePath);
    } catch (Exception e) {
      displayErrorBox("Could not save selected story", e.getMessage());
    }
  }

  /**
   * Creates a user story info by reading it from a file that is uploaded from the user's computer.
   * The method creates a .goals file for the .paths file.
   *
   * @return A UserStoryInfo based on the file provided by the user.
   * @throws InvalidStoryFormatException If the .paths formatting is invalid.
   * @throws IOException                 If the file does not exist.
   * @throws InvalidGoalFormatException  If the .goals file cannot be written to.
   */
  private UserStoryInfo createUserStoryInfo()
      throws InvalidStoryFormatException, IOException, InvalidGoalFormatException {
    UserStoryInfo userStoryInfo = null;

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("Paths Files", "*.paths"));
    fileChooser.setTitle("Select .paths file");
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      Story story = StoryFileHandling.readStoryFromFile(selectedFile.getPath());
      userStoryInfo = new UserStoryInfo(selectedFile.getName(),
          selectedFile.getPath(), story.getBrokenLinks().size());

      GoalsRegister gr = new GoalsRegister();
      GoalFileHandling.writeGoalsToFile(gr,
          System.getProperty("user.dir") + "/src/main/resources/goalFiles/"
              + selectedFile.getName().replace(".paths", ".goals"));
    }
    return userStoryInfo;
  }

  /**
   * Method for validating that a file exists.
   *
   * @param filePath The absolute path of the file.
   * @return True, if the file exists.
   */
  public boolean isFileExists(String filePath) {
    File tempFile = new File(filePath);

    return tempFile.exists();
  }

  /**
   * Reads an object of type T from a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return An object of type T, specified by the extending class.
   */
  public UserStoryInfoRegister readUserStoriesFromFile(String filePath) {
    try {
      return UserStoryInfoFileHandling.readUserStoryInfoFromFile(
          filePath);
    } catch (Exception e) {
      displayErrorBox("Could not read UserStoryInfoRegister from File", e.getMessage());
    }
    return new UserStoryInfoRegister();
  }

  /**
   * Reads a story from a specified file path in the parameter. Used to display the dialog box of
   * the story's broken links.
   *
   * @param filePath The absolute path of the file.
   * @return A story from the give file, if the reading is successful. Else, returns null;
   */
  public Story readStoryFromFile(String filePath) {
    try {
      return StoryFileHandling.readStoryFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not read the story that is used to display the dialog from file",
          e.getMessage());
    }
    return null;
  }

  /**
   * Switches from one scene to another.
   *
   * @param styleSheet The name of the style sheet of the next scene.
   * @param root       The root pane for the next scene
   * @param newTitle   The new title of the stage.
   */
  @Override
  public void switchScene(String styleSheet, Pane root, String newTitle) {
    Main.switchPane(styleSheet, root, newTitle);
  }

  /**
   * Writes a UserStoryInfoRegister to a file, specified by the method parameter.
   *
   * @param userStoryInfoRegister The UserStoryInfoRegister that will be written to file.
   * @param filePath              The absolute path of the file.
   */
  public void writeUserStoriesToFile(UserStoryInfoRegister userStoryInfoRegister, String filePath) {
    try {
      UserStoryInfoFileHandling.writeUserStoryInfoRegisterToFile(userStoryInfoRegister, filePath);
    } catch (IndexOutOfBoundsException e) {
      displayErrorBox("Invalid UserStoryInfo format",
          "The UserStoryInfo needs a file name, a file path and the number of broken links");
    } catch (Exception e) {
      displayErrorBox("Could not save the UserStories to file", e.getMessage());
    }
  }
}
