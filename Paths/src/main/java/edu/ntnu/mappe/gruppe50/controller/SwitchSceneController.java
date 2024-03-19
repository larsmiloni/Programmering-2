package edu.ntnu.mappe.gruppe50.controller;

import javafx.scene.layout.Pane;

/**
 * Interface for controllers that offers method for scene switching that implementing classes
 * declare on their own.
 *
 * @author Harry Linrui Xu
 * @since 16.05.2023
 */
public interface SwitchSceneController {

  /**
   * Switches from one scene to another.
   *
   * @param styleSheet The name of the style sheet of the next scene.
   * @param root       The root pane for the next scene
   * @param newTitle   The new title of the stage
   */
  void switchScene(String styleSheet, Pane root, String newTitle);
}
