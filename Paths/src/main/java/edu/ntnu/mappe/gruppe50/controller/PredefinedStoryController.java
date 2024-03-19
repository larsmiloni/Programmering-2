package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.fileutils.GameFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.scene.layout.Pane;

/**
 * Controller for story selector view. In a MVC-structure, the controller is the communication
 * middleman for the view (interface) and the model (business logic).
 *
 * @author Harry Linrui Xu Since 9.4.2023
 */
public class PredefinedStoryController extends DialogController implements SwitchSceneController {

  /**
   * Empty constructor for the controller. Only used as a means for instantiating the controller.
   */
  public PredefinedStoryController() {

  }

  /**
   * Method for writing the selected story to a file, specified by the parameter. The story is
   * written into the first line of a .game file.
   *
   * @param filePath The absolute path of the file.
   */
  public void writeSelectedStoryToFile(String filePath) {
    try {
      GameFileHandling.replaceFirstLine(
          System.getProperty("user.dir") + "/src/main/resources/gameFiles/game.game", filePath);
    } catch (Exception e) {
      displayErrorBox("Could not write the selected story to file", e.getMessage());
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
