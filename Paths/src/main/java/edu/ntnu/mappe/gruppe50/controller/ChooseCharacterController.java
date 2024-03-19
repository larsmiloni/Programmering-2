package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.fileutils.GameFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.PlayerFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.scenes.ChooseCharacter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 * Controller class for the {@link ChooseCharacter} view. The controller handles user input related
 * to deleting a player and saving the chosen player. The class extends {@link DialogController} for
 * displaying dialog boxed and implements {@link SwitchSceneController} for switching scenes.
 *
 * @author Harry Linrui Xu
 * @since 17.05.2023.
 */
public class ChooseCharacterController extends DialogController implements SwitchSceneController {

  /**
   * Empty constructor that instantiates the controller.
   */
  public ChooseCharacterController() {

  }

  /**
   * Reads a list of players from a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return A list of players.
   */
  public List<Player> readPlayersFromFile(String filePath) {
    try {
      return PlayerFileHandling.readAllPlayersFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not read player from file", e.getMessage());
    }
    return new ArrayList<>();
  }

  /**
   * Save the selected player name to file.
   *
   * @param playerName The name of the chosen player.
   */
  public void saveSelectedPlayer(String playerName) {
    try {
      GameFileHandling.replaceSecondLine(
          System.getProperty("user.dir") + "/src/main/resources/gameFiles/game.game", playerName);
    } catch (Exception e) {
      displayErrorBox("Could not save selected player to file", e.getMessage());
    }
  }

  /**
   * Deletes a player from the .players file that contains all the player entries.
   *
   * @param players     The list of players that stores all players. A player is removed from this
   *                    class.
   * @param playerTable The table which display the players. A player is also removed from this
   *                    table.
   * @param parent      The parent class that handles updating the table.
   */
  public void deletePlayer(List<Player> players, TableView<Player> playerTable,
      ChooseCharacter parent) {
    try {
      Player player = playerTable.getSelectionModel().getSelectedItem();
      players.remove(player);
      parent.updateObservableList();
      PlayerFileHandling.removePlayerFromFile(player.getName(),
          System.getProperty("user.dir") + "/src/main/resources/playerFiles/players.players");
    } catch (Exception e) {
      displayErrorBox("Could not delete player from file", e.getMessage());
    }
  }

  /**
   * Switches from one scene to another.
   *
   * @param styleSheet The name of the style sheet of the next scene.
   * @param root       The root pane for the next scene.
   * @param newTitle   The new title of the stage.
   */
  @Override
  public void switchScene(String styleSheet, Pane root, String newTitle) {
    Main.switchPane(styleSheet, root, newTitle);
  }
}
