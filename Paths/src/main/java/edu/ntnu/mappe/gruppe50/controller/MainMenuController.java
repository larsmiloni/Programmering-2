package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.fileutils.BaseStoryFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.GameFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.scene.layout.Pane;

/**
 * Controller for the {@link edu.ntnu.mappe.gruppe50.view.scenes.MainMenu} view. The controller
 * implements the {@link SwitchSceneController}.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 17.05.2023
 */
public class MainMenuController extends DialogController implements SwitchSceneController {

  /**
   * Method for removing all visited links from file. This is used whenever the user chooses the
   * "New Game" option, so that when the game starts the program attempts to create a new game,
   * rather than loading an old one.
   *
   * @param filePath The absolute file path of the .links file.
   */
  public void removeAllLinksFromFile(String filePath) {
    try {
      BaseStoryFileHandling.removeAllLinesFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Cannot remove links from file", e.getMessage());
    }
  }

  /**
   * Method for validating that a loaded game is valid and can be loaded.
   *
   * @return True, if the game can be loaded.
   */
  public boolean canLoadGame() {
    try {
      GameFileHandling.readGameFromFile(
          System.getProperty("user.dir") + "/src/main/resources/gameFiles/game.game", true);
      return true;
    } catch (Exception e) {
      displayErrorBox("Game cannot be loaded", "There is no saved game");
      return false;
    }
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
}
