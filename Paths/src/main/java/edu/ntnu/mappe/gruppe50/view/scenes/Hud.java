package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.HudController;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.view.Main;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class that represents the in-game Head-up display. The Hud contains informative gameplay elements
 * such as the player stats (health, gold, score), inventory and the goals button in order to keep
 * the player aware of their status at all times.
 *
 * @author Harry Linrui Xu
 * @since 08.05.2023
 */
public class Hud extends Ui {

  private final BorderPane root;
  private Label nameLbl;
  private Label healthLbl;
  private Label goldLbl;
  private Label scoreLbl;
  private Label healthDiffLbl;
  private Label goldDiffLbl;
  private Label scoreDiffLbl;

  private TilePane inventory;

  private final HudController hudController;

  private final Player player;

  /**
   * Constructor for the Hud. Instantiates a borderpane which is filled with the player stats and
   * the "view goals" button.
   *
   * @param player The Player character.
   * @param goals  The list of goals that can be completed in the play through.
   */
  public Hud(Player player, List<Goal> goals) {
    hudController = new HudController();
    this.player = player;
    //Instantiate root container for top border
    root = new BorderPane();
    root.setPadding(new Insets(15, 25, 25, 25));
    root.setStyle("-fx-border-with: 4px; -fx-border-color: black; -fx-background-color: white");

    //Filling the pane with player stats and "view goals" button
    root.setLeft(createPlayerStats(player.getGold(), player.getHealth(), player.getScore(),
        player.getName()));
    root.setCenter(createInventory());
    root.setRight(createGoalBtnContainer(player, goals));

    //Set margins to create some air
    BorderPane.setMargin(root, new Insets(50, 225, 25, 225));
  }

  /**
   * Returns the root borderpane.
   *
   * @return A borderpane containing all Hud elements.
   */
  public BorderPane getHud() {
    return this.root;
  }

  /**
   * Method for creating the player stats, as well as a player image.
   *
   * @param gold   The starting gold of the Player.
   * @param health The starting health of the Player.
   * @param score  The starting score of the Player.
   * @return GridPane containing player stats.
   */
  private GridPane createPlayerStats(int gold, int health, int score, String name) {
    //Creating icons for the stats
    ImageView characterImgView = hudController.createAvatarImageView(player);
    characterImgView.setFitWidth(150);
    characterImgView.setFitHeight(150);
    characterImgView.setPreserveRatio(true);

    //Instantiate player stats
    goldLbl = createSmallLabel(String.valueOf(gold));
    healthLbl = createSmallLabel(String.valueOf(health));
    scoreLbl = createSmallLabel(String.valueOf(score));
    nameLbl = new Label(name);

    //Instantiate player stats diff
    goldDiffLbl = createSmallLabel("");
    healthDiffLbl = createSmallLabel("");
    scoreDiffLbl = createSmallLabel("");

    goldDiffLbl.setStyle("-fx-text-fill: red");
    healthDiffLbl.setStyle("-fx-text-fill: red");
    scoreDiffLbl.setStyle("-fx-text-fill: red");

    //Instantiate grid pane for holding player stats in the top left
    GridPane playerStats = new GridPane();

    //Placing player stat elements.
    //Character image has a row and column span of 3
    playerStats.add(characterImgView, 0, 0, 3, 3);
    ImageView heartImgView = createPngImageView("heart", 50, 50);
    playerStats.add(heartImgView, 3, 0);
    ImageView goldImgView = createPngImageView("gold", 50, 50);
    playerStats.add(goldImgView, 3, 1);
    ImageView xpImgView = createPngImageView("xp", 50, 50);
    playerStats.add(xpImgView, 3, 2);
    //Health bar has a row and column span of 2 and 1 respectively
    playerStats.add(healthLbl, 4, 0);
    playerStats.add(goldLbl, 4, 1);
    playerStats.add(scoreLbl, 4, 2);

    playerStats.add(healthDiffLbl, 5, 0);
    playerStats.add(goldDiffLbl, 5, 1);
    playerStats.add(scoreDiffLbl, 5, 2);

    playerStats.setAlignment(Pos.CENTER);

    return playerStats;
  }

  /**
   * Creates the inventory in the Hud. Each item is represented as a button, containing and image
   * view and some text in a scrolling pane.
   *
   * @return A VBox containing the inventory of the Player.
   */
  private VBox createInventory() {
    //The root node for the inventory
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setMaxWidth(475);

    //Label on top of the inventory box
    Label inventoryLbl = new Label(nameLbl.getText() + "'s Inventory");
    inventoryLbl.setFont(new Font(18));

    //Container for the individual items
    inventory = new TilePane();
    inventory.setHgap(10);
    inventory.setVgap(10);
    inventory.setAlignment(Pos.CENTER_LEFT);

    //Removes space limitations in the small Hud
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setContent(inventory);

    //Fill entire scrollpane with the inventory tilepane
    scrollPane.setPrefHeight(120);
    scrollPane.widthProperty().addListener(event ->
        inventory.setPrefWidth(scrollPane.getWidth()));

    scrollPane.heightProperty().addListener(event ->
        inventory.setPrefHeight(scrollPane.getHeight()));

    root.getChildren().addAll(inventoryLbl, scrollPane);

    return root;
  }

  /**
   * Creates a container that holds "view goals" button.
   *
   * @param player The Player character.
   * @param goals  The list of goals that can be completed in a play through.
   * @return A VBox containing the "view goals" button.
   */
  public VBox createGoalBtnContainer(Player player, List<Goal> goals) {
    Button viewGoalsBtn = new Button("View goals");

    viewGoalsBtn.setOnAction(btnPress -> {
      Main.playSoundOnClick();
      hudController.showGoals(player, goals);
    });

    viewGoalsBtn.setFont(new Font(24));

    VBox goalBtnContainer = new VBox(viewGoalsBtn);
    goalBtnContainer.setAlignment(Pos.CENTER);

    return goalBtnContainer;
  }

  /**
   * Gives the label the fadeTransition. The fadeTransition fades a label from visible to
   * non-visible in 3 seconds.
   *
   * @param label The label that is given the fadeTransition property.
   */
  private void fadeTransaction(Label label) {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), label);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
  }

  /**
   * Sets the current label values to the actual player stats. Also displays the difference in the
   * label values before and after they are updated.
   *
   * @param player The Player character.
   * @param stage  The window on which the GUI is displayed.
   */
  public void setPlayerStats(Player player, Stage stage) {
    hudController.setStatValue(goldLbl, goldDiffLbl, player.getGold());
    hudController.setStatValue(healthLbl, healthDiffLbl, player.getHealth());
    hudController.setStatValue(scoreLbl, scoreDiffLbl, player.getScore());
    hudController.setInventory(inventory, player.getInventory(), stage);

    fadeTransaction(healthDiffLbl);
    fadeTransaction(goldDiffLbl);
    fadeTransaction(scoreDiffLbl);
  }
}
