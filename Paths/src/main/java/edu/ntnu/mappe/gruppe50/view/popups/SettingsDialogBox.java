package edu.ntnu.mappe.gruppe50.view.popups;

import edu.ntnu.mappe.gruppe50.controller.InGameSettingsController;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View for the SettingsDialogBox that is displayed whenever the settings button is pressed in a
 * scene that is not the InGameScene. Allows the user to adjust the volume of both music and sound
 * effects.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 28.03.2023
 */
public class SettingsDialogBox {

  private final InGameSettingsController controller;
  private Stage dialog;

  /**
   * Instantiates the {@link edu.ntnu.mappe.gruppe50.controller.InGameSceneController} and calls
   * upon the method that creates the center content.
   */
  public SettingsDialogBox() {
    controller = new InGameSettingsController();
    createContent();
  }

  /**
   * Creates the center content of the dialog box, being the entire content of the dialog box -
   * labels, sliders and buttons.
   */
  private void createContent() {
    dialog = new Stage();

    int windowWidth = 800;
    int windowHeight = 475;

    dialog.setMinWidth(windowWidth);
    dialog.setMinHeight(windowHeight);
    dialog.setMaxWidth(windowWidth);
    dialog.setMaxHeight(windowHeight);

    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setTitle("Settings");

    Label title = new Label("Settings");
    title.setFont(new Font(50));

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
    int buttonWidth = 300;
    int buttonHeight = 80;
    Font buttonFont = new Font(40);

    Button okButton = new Button("Save");
    okButton.setMinSize(buttonWidth, buttonHeight);
    okButton.setFont(buttonFont);

    okButton.setOnAction(event -> {
      Main.playSoundOnClick();
      controller.saveVolumeSettings(musicVolumeSlider.getValue(), soundVolumeSlider.getValue(),
          volumeFilePath);
      dialog.close();
    });

    Button cancelButton = new Button("Cancel");
    cancelButton.setMinSize(buttonWidth, buttonHeight);
    cancelButton.setFont(buttonFont);
    cancelButton.setOnAction(event -> {
      Main.playSoundOnClick();
      dialog.close();
    });

    HBox buttonHbox = new HBox(cancelButton, okButton);
    buttonHbox.setAlignment(Pos.CENTER);
    buttonHbox.setSpacing(50);

    VBox vbox = new VBox(title, musicVolumeVbox, soundVolumeVbox, buttonHbox);

    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setSpacing(50);

    Scene scene = new Scene(vbox);

    dialog.setScene(scene);
    dialog.showAndWait();
  }
}
