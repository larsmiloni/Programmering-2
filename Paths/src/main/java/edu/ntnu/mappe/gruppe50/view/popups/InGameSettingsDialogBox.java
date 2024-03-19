package edu.ntnu.mappe.gruppe50.view.popups;

import edu.ntnu.mappe.gruppe50.controller.InGameSettingsController;
import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.scenes.InGameScene;
import edu.ntnu.mappe.gruppe50.view.scenes.MainMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The view for the settings dialog when the settings button is pressed in-game. The dialog box
 * allows for changing audio levels, resuming, restarting and saving the game.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 29.03.2023
 */
public class InGameSettingsDialogBox {

  private Stage dialog;
  private final InGameSettingsController controller;

  /**
   * Constructor for the class. Instantiates the controller and creates the center content.
   *
   * @param game The game that is saved if the "save and exit" button is pressed.
   */
  public InGameSettingsDialogBox(Game game) {
    controller = new InGameSettingsController();
    createContent(game);
  }

  private void createContent(Game game) {
    dialog = new Stage();

    int windowWidth = 800;
    int windowHeight = 750;

    dialog.setMinWidth(windowWidth);
    dialog.setMinHeight(windowHeight);
    dialog.setMaxWidth(windowWidth);
    dialog.setMaxHeight(windowHeight);

    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setTitle("Settings");

    //Title
    Label title = new Label("Settings");
    title.setFont(new Font(50));

    //Sliders
    Font lableFont = new Font(30);
    Label musicVolumeLabel = new Label("Music Volume");
    musicVolumeLabel.setFont(lableFont);

    Slider musicVolumeSlider = new Slider();
    String volumeFilePath =
        System.getProperty("user.dir") + "/src/main/resources/settings/volume.txt";
    double musicVolume = controller.readMusicVolumeFromFile(volumeFilePath);
    musicVolumeSlider.setValue(musicVolume);

    musicVolumeSlider.setMaxWidth(500);
    VBox musicVolumeVbox = new VBox(musicVolumeLabel, musicVolumeSlider);
    musicVolumeVbox.setAlignment(Pos.CENTER);

    Label soundVolumeLabel = new Label("Sound Volume");
    soundVolumeLabel.setFont(lableFont);
    Slider soundVolumeSlider = new Slider();

    double soundVolume = controller.readSoundVolumeFromFile(volumeFilePath);
    soundVolumeSlider.setValue(soundVolume);

    soundVolumeSlider.setMaxWidth(500);
    VBox soundVolumeVbox = new VBox(soundVolumeLabel, soundVolumeSlider);
    soundVolumeVbox.setAlignment(Pos.CENTER);

    //Buttons
    int buttonWidth = 600;
    int buttonHeight = 80;
    Font buttonFont = new Font(40);

    Button resumeButton = new Button("Resume");
    resumeButton.setMinSize(buttonWidth, buttonHeight);
    resumeButton.setFont(buttonFont);

    resumeButton.setOnAction(event -> {
      Main.playSoundOnClick();
      controller.saveVolumeSettings(musicVolumeSlider.getValue(), soundVolumeSlider.getValue(),
          volumeFilePath);
      dialog.close();
    });

    Button restartButton = new Button("Restart story");
    restartButton.setOnAction(event -> {
      Main.playSoundOnClick();
      String linksFilePath =
          System.getProperty("user.dir") + "/src/main/resources/linkFiles/links.links";
      controller.clearProgress(linksFilePath);
      InGameScene igc = new InGameScene(new Stage());

      Main.switchPane("InGameScene.css", igc.getRoot(), "In Game");
      dialog.close();
    });

    restartButton.setFont(buttonFont);
    restartButton.setMinSize(buttonWidth, buttonHeight);

    Button saveAndExitButton = new Button("Save and exit");
    saveAndExitButton.setFont(buttonFont);
    saveAndExitButton.setMinSize(buttonWidth, buttonHeight);

    saveAndExitButton.setOnAction(event -> {
      Main.playSoundOnClick();
      String linksFilePath =
          System.getProperty("user.dir") + "/src/main/resources/linkFiles/links.links";
      controller.saveLinksToFile(game.getVisitedLinks(), linksFilePath);

      MainMenu mm = new MainMenu();
      controller.switchScene("MainMenu.css", mm.getRoot(), "Main Menu");
      dialog.close();
    });

    VBox vbox = new VBox(title, musicVolumeVbox, soundVolumeVbox, resumeButton, restartButton,
        saveAndExitButton);

    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setSpacing(50);

    Scene scene = new Scene(vbox);

    dialog.setScene(scene);
    dialog.showAndWait();
  }

}
