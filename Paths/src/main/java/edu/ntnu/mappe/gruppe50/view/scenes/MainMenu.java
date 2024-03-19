package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.MainMenuController;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import edu.ntnu.mappe.gruppe50.view.popups.SettingsDialogBox;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;


/**
 * Main menu class for Paths. The player always starts here each time the application is loaded.
 * From here, one can start a game, load a game, go to settings, go to help or exit the
 * application.
 *
 * @author Harry Linrui Xu
 * @since 9.2.2023
 */

public class MainMenu {

  private final StackPane root;
  private final MainMenuController mmc;

  /**
   * Constructor for the MainMenu object. Creates a title for the game, along with several buttons.
   */
  public MainMenu() {
    mmc = new MainMenuController();
    //Root node that contains both title and buttons in the main menu
    VBox titleBtnContainer = new VBox(10);
    titleBtnContainer.setAlignment(Pos.CENTER);

    root = new StackPane();
    root.setPrefSize(800, 1200);

    //Spacing between title and buttons
    Region spacing = new Region();
    spacing.setPrefHeight(40);

    Button newGameBtn = new Button("New game"); //Uniform button sizes
    newGameBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      mmc.removeAllLinksFromFile(
          System.getProperty("user.dir") + "/src/main/resources/linkFiles/links.links");
      SelectStoryType sst = new SelectStoryType();
      mmc.switchScene("SelectStoryType.css", sst.getRoot(), "Select Story Type");
    });

    Button loadBtn = new Button("Load game");
    loadBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      InGameScene igc = new InGameScene(new Stage());
      mmc.switchScene("InGameScene.css", igc.getRoot(), "In Game");
    });

    loadBtn.addEventFilter(
        ActionEvent.ACTION, event -> {
          if (!mmc.canLoadGame()) {
            event.consume();
          }
        });

    Button settingsBtn = new Button("Settings"); //Dialog box
    settingsBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      new SettingsDialogBox();
    });

    Button aboutBtn = new Button("About"); //Dialog box
    aboutBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      new HelpDialogBox(HelpDialogBox.Mode.MAIN_MENU);
    });

    Button exitBtn = new Button("Exit");
    exitBtn.setOnAction(e -> Platform.exit());

    //Formatting buttons
    Button[] buttons = new Button[]{newGameBtn, loadBtn, settingsBtn, aboutBtn, exitBtn};
    setButtonWidth(buttons);

    Label title = new Label("P A T H S");
    titleBtnContainer.getChildren()
        .addAll(title, spacing, newGameBtn, loadBtn, settingsBtn, aboutBtn, exitBtn);

    BackgroundImage backgroundImage = new BackgroundImage(
        new Image("images/pixel-scroll.png", 900, 924, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT);
    //then you set to your node
    titleBtnContainer.setBackground(new Background(backgroundImage));

    root.getChildren().add(titleBtnContainer);
    root.setStyle("-fx-background-color: black");
  }

  /**
   * Gets the root node of the scene.
   *
   * @return The root StackPane of the scene.
   */
  public StackPane getRoot() {
    return this.root;
  }


  /**
   * Iterates over all the buttons in a given list and sets their widths to the width given as an
   * argument.
   *
   * @param buttons An array of buttons.
   */
  private void setButtonWidth(Button[] buttons) {
    Arrays.stream(buttons).forEach(button -> button.setPrefWidth(300));
  }
}