package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.fileutils.PlayerFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 * Controller class for the CreateCharacter view. The class validates the inputs of the user and
 * builds a valid player that it returns to the view. The class extends {@link DialogController} for
 * displaying dialog * boxed and implements {@link SwitchSceneController} for switching scenes.
 *
 * @author Harry Linrui Xu
 * @since 02.05.2023
 */
public class CreateCharacterController extends DialogController implements SwitchSceneController {

  /**
   * Returns the file path of the avatar image that the user gives to its Player avatar.
   *
   * @return A string that represents the file path of the avatar image.
   */
  public String addAvatarImage() {
    String filePath = null;

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif"));
    fileChooser.setTitle("Select image file");
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      filePath = selectedFile.toURI().toString();
    }
    return filePath;
  }

  /**
   * Validates that the inputted player name is valid - not blank.
   *
   * @param text     The inputted text for the name.
   * @param errorLbl Label for displaying error message.
   * @return True if the name is valid.
   */
  public boolean isValidName(String text, Label errorLbl) {
    errorLbl.setOpacity(0);

    if (text.isBlank()) {
      formatErrorMsg(errorLbl, "Name cannot be null or blank");
      return false;
    }
    return true;
  }

  /**
   * Validates that the inputted player health is valid - integer above zero. A blank string is also
   * valid, as a player object does need a health argument.
   *
   * @param text      The inputted text for the health.
   * @param errorLbl  Label for displaying error message.
   * @param errorText Message that will be displayed if the health is invalid.
   * @return True, if the parsed string is an integer above zero or if the string is blank.
   */
  public boolean isValidHealth(String text, Label errorLbl, String errorText) {
    if (text.isBlank()) {
      return true;
    }

    errorLbl.setOpacity(0);
    int value;

    try {
      value = parseValue(text);
      if (value < 1) {
        formatErrorMsg(errorLbl, errorText);
        return false;
      }
    } catch (NumberFormatException nfe) {
      formatErrorMsg(errorLbl, errorText);
      return false;
    }
    return true;
  }

  /**
   * Validates that the inputted player gold is valid - integer above negative one. A blank string
   * is also valid, as a player object does need a gold argument.
   *
   * @param text      The inputted text for the gold.
   * @param errorLbl  Label for displaying error message.
   * @param errorText Message that will be displayed if the gold is invalid.
   * @return True, if the parsed string is an integer above negative one, or if the string is blank.
   */
  public boolean isValidGold(String text, Label errorLbl, String errorText) {
    if (text.isBlank()) {
      return true;
    }

    errorLbl.setOpacity(0);
    int value;

    try {
      value = parseValue(text);
      if (value < 0) {
        formatErrorMsg(errorLbl, errorText);
        return false;
      }
    } catch (NumberFormatException nfe) {
      formatErrorMsg(errorLbl, errorText);
      return false;
    }
    return true;
  }

  /**
   * Method that attempts to parse a string.
   *
   * @param text The parsed string.
   * @return An integer from the parsed string.
   * @throws IllegalArgumentException If the string cannot be parsed.
   */
  private int parseValue(String text) throws NumberFormatException {
    return Integer.parseInt(text);
  }

  /**
   * Creates a player from the input parameters.
   *
   * @param name   The player name.
   * @param health The player health.
   * @param gold   The player gold.
   * @return A player object, based on the input parameters.
   */
  public Player createPlayer(String name, String health, String gold) {
    Player.Builder builder = new Player.Builder(name);
    if (!health.isBlank()) {
      builder.health(parseValue(health));
    }

    if (!gold.isBlank()) {
      builder.gold(parseValue(gold));
    }
    return builder.build();
  }

  /**
   * Checks if a player with the same name already exists in the list of players.
   *
   * @param name     The name of the created player.
   * @param filePath The absolute path of the file.
   * @param label    Label used for displaying error message.
   * @param text     Message in label.
   * @return True, if a player of the same name as the parameter already exists in the register.
   */
  public boolean isDuplicateName(String name, String filePath, Label label, String text) {
    label.setOpacity(0);
    try {
      List<Player> players = PlayerFileHandling.readAllPlayersFromFile(filePath);
      if (players.contains(new Player.Builder(name).build())) {
        formatErrorMsg(label, text);
        return true;
      }
      return false;
    } catch (Exception e) {
      displayErrorBox("Could not get the list of players from file", e.getMessage());
    }
    return false;
  }

  /**
   * Writes a player to the player file.
   *
   * @param data     The object that will be written to file.
   * @param filePath The file path of the player avatar image.
   */
  public void writeCharacterToFile(Player data, String filePath) {
    try {
      PlayerFileHandling.writePlayerToFile(data, filePath,
          System.getProperty("user.dir") + "/src/main/resources/playerFiles/players.players");
    } catch (IOException ioe) {
      displayErrorBox("Could not write player to file", ioe.getMessage());
    } catch (Exception e) {
      displayErrorBox("Cannot write player to file",
          "The player may be null or the file format is invalid");
    }
  }

  /**
   * Formats an error message. The text is set to the parameter value. The labels opacity is then
   * set to 1 (previously 0 - transparent) to display the message.
   *
   * @param errorMsg Label displaying the error message.
   * @param text     The text that the label will take
   */
  private void formatErrorMsg(Label errorMsg, String text) {
    errorMsg.setText(text);
    errorMsg.setOpacity(1);
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
