package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.fileutils.PlayerFileHandling;
import edu.ntnu.mappe.gruppe50.view.popups.GoalsDialogBox;
import edu.ntnu.mappe.gruppe50.view.scenes.Hud;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller class for the {@link Hud} view. Is responsible for updating the Hud contents, based on
 * user input. The class extends {@link DialogController}.
 *
 * @author Harry Linrui Xu
 * @since 08.05.2023
 */
public class HudController extends DialogController {

  /**
   * Empty constructor that instantiates the controller.
   */
  public HudController() {

  }

  /**
   * Method for retrieving the player avatar image.
   *
   * @param player The Player object that the avatar image belongs to.
   * @return An ImageView of the avatar image of a player.
   */
  public ImageView createAvatarImageView(Player player) {
    try {
      return new ImageView(
          PlayerFileHandling.readPlayerAvatarImagePath(player.getName(),
              System.getProperty("user.dir") + "/src/main/resources/playerFiles/players.players"));
    } catch (Exception e) {
      displayErrorBox("Could not read the player avatar", e.getMessage());
    }
    return new ImageView("images/unknown.png");
  }

  /**
   * Displays a dialog box that shows the completion of goals that were set before the play
   * through.
   *
   * @param player The Player object whose stats are used to check for the goal completions.
   * @param goals  The list of goals that the player stats are checked against.
   */
  public void showGoals(Player player, List<Goal> goals) {
    GoalsDialogBox goalsDialogBox = new GoalsDialogBox(player, goals);
    goalsDialogBox.showAndWait();
  }


  /**
   * Sets a label in the Hud to the player stats. Also validates if the difference between the new
   * gold and the old gold is a positive or negative number, and gives the goldDiffLbl color based
   * on that.
   *
   * @param valueLbl   The label value that displays gold in the Hud.
   * @param diffLbl    The difference that displays how much the value has changed.
   * @param playerStat The player stat that the label is set to.
   */
  public void setStatValue(Label valueLbl, Label diffLbl, int playerStat) {
    int diffValue = playerStat - Integer.parseInt(valueLbl.getText());
    String diffText;

    if (diffValue > 0) {
      diffText = "+" + diffValue;
      diffLbl.setStyle("-fx-text-fill: green");
    } else if (diffValue < 0) {
      diffText = String.valueOf(diffValue);
      diffLbl.setStyle("-fx-text-fill: red");
    } else {
      diffText = "";
    }

    diffLbl.setText(diffText);
    valueLbl.setText(String.valueOf(playerStat));
  }

  /**
   * Sets the inventory box in the Hud to the inventory of the player. Each item is represented by a
   * button, containing an image and an item name.
   *
   * @param inventory A TilePane that represents player inventory in the Hud.
   * @param items     The list of items in the player inventory.
   * @param stage     The stage that the current scene is displayed on.
   */
  public void setInventory(TilePane inventory, List<String> items, Stage stage) {
    //Clear inventory
    inventory.getChildren().clear();

    //Create buttons for each item in the inventory list
    items.forEach(item -> {
          Button button = new Button(item);
          String imageName = convertNameToFileName(item);
          ImageView imageView;

          //Display default image if the file does not exist
          try {
            imageView = new ImageView(new Image("images/" + imageName + ".png", 75,
                75, false, false));
          } catch (IllegalArgumentException iae) {
            imageView = new ImageView(new Image("images/unknown.png", 75,
                75, false, false));
            imageName = "unknown";
          }
          button.setGraphic(imageView);
          button.setContentDisplay(ContentDisplay.TOP);

          //Sets a hover effect on the items
          createStageHover(stage, item, imageName, button);

          inventory.getChildren().add(button);
        }
    );
  }

  /**
   * Method for creating a hover effect on the inventory. Hovering the mouse on an item wll display
   * a sudo-dialog box that displays the item, just bigger..
   *
   * @param stage     The current stage that owns the new stage that contains the popup.
   * @param item      The item name.
   * @param imageName The image name of the image that is displayed in the dialog.
   * @param button    The button that represents an item in the inventory.
   */
  private void createStageHover(Stage stage, String item, String imageName, Button button) {
    //Instantiate the stage that will be displayed on top of current stage
    Stage itemStage = new Stage();
    itemStage.initOwner(stage);
    itemStage.initStyle(StageStyle.UNDECORATED);

    //Pane that item is displayed on
    VBox itemDisplayer = new VBox(20);
    itemDisplayer.setAlignment(Pos.TOP_CENTER);
    itemDisplayer.setStyle("-fx-border-with: 4px; -fx-border-color: black;");
    itemDisplayer.setPrefSize(400, 400);

    Label itemName = new Label(item);
    itemName.setFont(new Font(18));
    ImageView imageView1 = new ImageView(
        new Image("images/" + imageName + ".png", 300, 300,
            false, false));
    itemDisplayer.getChildren().addAll(itemName, imageView1, new Label("Item description"));

    itemStage.setScene(new Scene(itemDisplayer));

    //Setting hover property on button
    button.setOnMouseEntered(event -> {
      itemStage.setAlwaysOnTop(true);
      itemStage.show();
    });
    button.setOnMouseExited(event -> itemStage.close());
  }

  /**
   * Converts a regular string to a snake case string, akin to a file name.
   *
   * @param name The input string that is converted.
   * @return A string converted to snake case.
   */
  private String convertNameToFileName(String name) {
    return name.toLowerCase().replace(" ", "_");
  }
}
