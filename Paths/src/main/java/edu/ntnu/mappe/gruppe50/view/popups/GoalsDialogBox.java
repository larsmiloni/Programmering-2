package edu.ntnu.mappe.gruppe50.view.popups;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**
 * Represents the dialog window that pops up when pressing the "View goals" button in-game. The view
 * goals window informs the player of the goals that can be completed during the game and whether
 * they have been completed. The data is always synched up to the player's current stats. The
 * GoalsDialog inherits from the Dialog class, allowing it to use some very handy methods.
 *
 * @author Harry Linrui Xu
 * @since 08.05.2023
 */
public class GoalsDialogBox extends Dialog<Goal> {

  /**
   * Constructor for the GoalsDialog window. It calls on the createCenterContent() method that
   * spawns the content of the dialog box.
   *
   * @param player The Player character.
   * @param goals  The list of goals that can be completed in the play through.
   */
  public GoalsDialogBox(Player player, List<Goal> goals) {
    super();
    createContent(player, goals);
  }

  /**
   * Method for creating the pseudo-table of goals and their completions.
   *
   * @param player The Player character in-game.
   * @param goals  The set of goals to be completed.
   */
  private void createContent(Player player, List<Goal> goals) {
    VBox root = new VBox(5);

    //Creating the title for the two columns
    BorderPane columnNames = new BorderPane();
    columnNames.setLeft(createLabel("Goal", 24));
    columnNames.setRight(createLabel("Completed", 24));

    //Used to create spacing between column names and entries
    Line line = new Line();

    root.getChildren().addAll(columnNames, line);
    root.setPrefWidth(400);
    root.setPadding(new Insets(30, 50, 30, 50));

    //Creating a borderpane for each goal
    goals.forEach(goal ->
        root.getChildren().addAll(createGoalEntry(player, goal))
    );

    setTitle("Goals");
    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().setContent(root);
  }

  /**
   * Method for creating a label with a custom text and size.
   *
   * @param text The content of the label.
   * @param size The font size of the label.
   * @return A label with customized text and size.
   */
  private Label createLabel(String text, int size) {
    Label label = new Label(text);
    label.setFont(new Font(size));

    return label;
  }

  /**
   * Creates a borderpane, representing an individual entry of a goal and its respective
   * completion.
   *
   * @param player The Player character in the game.
   * @param goal   An individual goal in the story.
   * @return A borderpane, housing a goal entry.
   */
  private BorderPane createGoalEntry(Player player, Goal goal) {
    boolean isFulfilled = goal.isFulfilled(player);
    String isFulfilledString = convertBoolToString(isFulfilled);

    BorderPane goalContainer = new BorderPane();
    goalContainer.setLeft(
        createLabel(goal.toString().substring(1, goal.toString().length() - 1), 14));
    goalContainer.setRight(createLabel(isFulfilledString, 14));

    return goalContainer;
  }

  /**
   * Converts a boolean value to a formatted yes/no string.
   *
   * @param isFulfilled The boolean that is evaluated.
   * @return Yes, if the bool is true. No, if the bool is false.
   */
  private String convertBoolToString(boolean isFulfilled) {
    return isFulfilled ? "Yes" : "No";
  }
}
