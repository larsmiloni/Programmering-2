package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.SelectStoryTypeController;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * View for selecting the type of story to play - {@link PredefinedStories} which the developers
 * have created or {@link UserStories} that the user has uploaded.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 06.05.2023
 */
public class SelectStoryType {

  private final StackPane root;

  private final SelectStoryTypeController controller;

  /**
   * Instantiates the {@link SelectStoryTypeController} and the root node for the scene. Calls on
   * the method that creates the child nodes in the scene.
   */
  public SelectStoryType() {
    controller = new SelectStoryTypeController();
    root = new StackPane();
    createCenterContent();
  }

  /**
   * Creates all the visual elements on top of the root StackPane, being the buttons on the screen,
   * as well as the styling.
   */
  private void createCenterContent() {
    //Root node that contains both title and buttons in the main menu
    VBox textBtnContainer = new VBox(10);
    textBtnContainer.setAlignment(Pos.CENTER);

    root.setPrefSize(800, 1200);

    //Spacing between title and buttons
    Region spacing = new Region();
    spacing.setPrefHeight(40);

    Button predefinedStories = new Button("Predefined Stories");
    predefinedStories.setOnAction(e -> {
      Main.playSoundOnClick();
      PredefinedStories ss = new PredefinedStories();
      controller.switchScene("PredefinedStories.css", ss.getRoot(), "Predefined Stories");
    });

    Button yourStories = new Button("Your Stories");
    yourStories.setOnAction(e -> {
      Main.playSoundOnClick();
      UserStories us = new UserStories();
      controller.switchScene("UserStories.css", us.getRoot(), "User Stories");
    });

    Button backToMainMenu = new Button("Back to Main Menu");
    backToMainMenu.setOnAction(e -> {
      Main.playSoundOnClick();
      MainMenu mm = new MainMenu();
      controller.switchScene("MainMenu.css", mm.getRoot(), "Main Menu");
    });

    Label title = new Label("Story type");
    textBtnContainer.getChildren().addAll(title, spacing, predefinedStories,
        yourStories, backToMainMenu);

    BackgroundImage backgroundImg = new BackgroundImage(
        new Image("images/pixel-scroll.png", 900, 924, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT);
    //then you set to your node
    textBtnContainer.setBackground(new Background(backgroundImg));

    root.getChildren().add(textBtnContainer);
    root.setStyle("-fx-background-color: black");
  }

  /**
   * Returns the rood node of the scene.
   *
   * @return The root StackPane of the scene.
   */
  public StackPane getRoot() {
    return this.root;
  }
}
