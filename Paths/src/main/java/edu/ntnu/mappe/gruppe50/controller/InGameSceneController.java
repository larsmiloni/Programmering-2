package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.customexceptions.NoMatchingPassageException;
import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.fileutils.GameFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.LinkFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

/**
 * Controller class that switches the center story box in response to user input. It retrieves the
 * referenced passage based up on the link chosen and returns it to the caller method in the view.
 * The class extends {@link DialogController} for displaying dialog * boxed and implements
 * {@link SwitchSceneController} for switching scenes.
 *
 * @author Harry Linrui Xu
 * @since 02.05.2023
 */
public class InGameSceneController extends DialogController implements SwitchSceneController {

  /**
   * Empty constructor for the controller.
   */
  public InGameSceneController() {

  }


  /**
   * Reads a Game object from a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return The Game read from file.
   */
  public Game readGameFromFile(String filePath) {
    try {
      return GameFileHandling.readGameFromFile(filePath, true);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Resets the progress of a game by clearing a file, specified by the parameter. This method is
   * used on the links.links file that tracks visited links.
   *
   * @param filePath The absolute path of the file.
   */
  public void clearProgress(String filePath) {
    try {
      LinkFileHandling.removeAllLinesFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not clear the links.links file",
          System.getProperty("user.dir") + "/src/main/resources/linkFiles/links.links");
    }
  }

  /**
   * Gets the first passage in the game. The first passage varies depending on if the game is a new
   * game or a saved game.
   *
   * @param game         The game that is currently played.
   * @param gameFilePath The absolute file path to the game.game file where games are saved.
   * @param linkFilePath The absolute file path to the links.links file where visited links are
   *                     saved.
   * @return The first passage if the game is a new play through, or if the method cannot match the
   *     link to the saved passage with a link in the game. The link of the saved passage
   *     if this is a saved game and the method manages to match the saved
   *     link with a link in the game.
   */
  public Passage getFirstPassage(Game game, String gameFilePath, String linkFilePath) {
    try {
      if (game.isNewPlayThrough()) {
        return game.getStory().getOpeningPassage();
      } else {
        return game.getSavedPassage();
      }
    } catch (Exception e) {
      clearProgress(linkFilePath);
      displayErrorBox("Could not read the saved passage - you will start from the opening passage",
          e.getMessage());
      game = readGameFromFile(gameFilePath);
    }
    return game.getStory().getOpeningPassage();
  }

  /**
   * Selects the link, given by user input and returns the corresponding passage from the game
   * object.
   *
   * @param game The game in the current play through.
   * @param link The chosen link to a passage.
   * @return The passage referenced by the link.
   */
  public Passage selectLink(Game game, Link link) {
    try {
      return game.go(link);
    } catch (NoMatchingPassageException nfe) {
      displayNoMatchingPassageDialogBox();
    }
    return null;
  }

  /**
   * Shows an error box on top of the screen, if there is no passage referenced by the chosen link.
   */
  public void displayNoMatchingPassageDialogBox() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Broken link");
    alert.setHeaderText("This is a broken link");
    alert.setContentText("The link does not reference any passage in the story");

    alert.show();
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
   * Returns a passage that appears if the player health reaches zero during the play through.
   *
   * @return A passage that appears whenever the player health reaches zero
   */
  public Passage createDeathPassage() {
    return new Passage("You died", "Your health reaches zero. You died");
  }
}
