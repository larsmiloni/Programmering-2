package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.scene.layout.Pane;

/**
 * Controller class for the SelectStory view. This class implements the SwitchSceneController
 * interface, allowing the controller to switch scenes.
 *
 * @author Harry Linrui Xu
 * @since 17.05.2023
 */
public class SelectStoryTypeController implements SwitchSceneController {

  /**
   * Empty constructor whose only purpose is to instantiate the controller.
   */
  public SelectStoryTypeController() {

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
