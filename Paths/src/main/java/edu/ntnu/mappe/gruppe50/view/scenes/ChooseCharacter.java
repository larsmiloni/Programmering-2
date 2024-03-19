package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.ChooseCharacterController;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import java.util.List;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for the ChooseCharacter scene. The user can choose an existing character, create a new
 * character or remove a character from a table that displays a table of players. The user selects a
 * player for their Paths play through. The class extends Ui and inherit methods for creating help,
 * settings, next and back buttons.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 11.05.2023
 */
public class ChooseCharacter extends Ui {

  private final BorderPane root;
  private final ChooseCharacterController ccc;

  private TableView<Player> playerTableView;

  private final ObservableList<Player> playerObservableList;

  private final List<Player> playerData;

  /**
   * Instantiates the visual elements on the scene and load elements such as the player data, a
   * controller, button actions and observable lists.
   */
  public ChooseCharacter() {
    ccc = new ChooseCharacterController();

    String playerFilePath =
        System.getProperty("user.dir") + "/src/main/resources/playerFiles/players.players";
    playerData = ccc.readPlayersFromFile(playerFilePath);
    //Setting observable list to player data
    playerObservableList = FXCollections.observableArrayList(playerData);

    root = createAll("Choose Your Character", HelpDialogBox.Mode.CHOOSE_CHARACTER);
    root.setCenter(createCenterContent());

    //Save the selected player and switch scene
    getNextBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      Player player = playerTableView.getSelectionModel().getSelectedItem();
      ccc.saveSelectedPlayer(player.getName());

      GoalSelector gs = new GoalSelector();
      ccc.switchScene("GoalSelector.css", gs.getRoot(), "Goal Selector");
    });

    //Disable next button until an entry has been selected
    setButtonDisableProperty(getNextBtn(),
        Bindings.isEmpty(playerTableView.getSelectionModel().getSelectedItems()));

    //Depending on which scene was selected before, the back button navigates to that previous pane.
    getBackBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      if (Objects.equals(Main.getPrevPane(), "StorySelector")) {
        PredefinedStories ss = new PredefinedStories();
        ccc.switchScene("PredefinedStories.css", ss.getRoot(), "Predefined Stories");
      } else {
        UserStories us = new UserStories();
        ccc.switchScene("UserStories.css", us.getRoot(), "User Stories");
      }
    });
  }

  /**
   * Creates the centre table and buttons in the scene.
   *
   * @return An Hbox containing the center contents.
   */
  private HBox createCenterContent() {
    //Create the table and columns
    playerTableView = new TableView<>(playerObservableList);

    TableColumn<Player, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Player, Integer> healthCol = new TableColumn<>("Health");
    healthCol.setCellValueFactory(new PropertyValueFactory<>("health"));

    TableColumn<Player, Integer> goldCol = new TableColumn<>("Gold");
    goldCol.setCellValueFactory(new PropertyValueFactory<>("gold"));

    // Add the columns to the table
    playerTableView.getColumns().add(nameCol);
    playerTableView.getColumns().add(healthCol);
    playerTableView.getColumns().add(goldCol);

    double tableMaxHeight = 700;
    double tableMaxWidth = 1400;
    playerTableView.setPrefHeight(tableMaxHeight);
    playerTableView.setPrefWidth(tableMaxWidth);
    //Make columns fit the entire table
    playerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // Create a layout and add the table to it
    BorderPane tableBorderPane = new BorderPane();
    tableBorderPane.setCenter(playerTableView);

    Button newPlayerBtn = new Button("New Player");
    newPlayerBtn.setMinSize(200, 200);
    newPlayerBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      CreateCharacter cc = new CreateCharacter();
      ccc.switchScene("CreateCharacter.css", cc.getRoot(), "Create Character");
    });

    Button removePlayer = new Button("Remove Player");
    removePlayer.setMinSize(200, 200);
    removePlayer.setOnAction(e -> {
      Main.playSoundOnClick();
      ccc.deletePlayer(playerData, playerTableView, this);
    });

    //Disable remove button until entry is selected
    setButtonDisableProperty(removePlayer,
        Bindings.isEmpty(playerTableView.getSelectionModel().getSelectedItems()));

    VBox buttonsVbox = new VBox(20, newPlayerBtn, removePlayer);
    buttonsVbox.setAlignment(Pos.CENTER);

    HBox tableButtonContainer = new HBox(20, tableBorderPane, buttonsVbox);
    tableButtonContainer.setAlignment(Pos.CENTER);
    tableButtonContainer.setPadding(new Insets(0, 25, 0, 75));

    return tableButtonContainer;
  }

  /**
   * Synchs the player observable list with the list of players.
   */
  public void updateObservableList() {
    this.playerObservableList.setAll(playerData);
  }

  /**
   * Gets the root borderpane that houses all the GUI elements in the scene.
   *
   * @return The scene's root borderpane.
   */
  public BorderPane getRoot() {
    return this.root;
  }
}
