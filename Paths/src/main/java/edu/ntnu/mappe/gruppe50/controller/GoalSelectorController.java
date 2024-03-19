package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalFactory;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalsRegister;
import edu.ntnu.mappe.gruppe50.model.fileutils.GameFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.GoalFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.scenes.GoalSelector;
import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

/**
 * Controller for the GoalSelector view. In a MVC-structure, the controller is the communication
 * middleman for the view (interface) and the model (business logic). The class extends
 * {@link DialogController} for displaying dialog * boxed and implements
 * {@link SwitchSceneController} for switching scenes.
 *
 * @author Harry Linrui Xu Since 29.03.2023
 */

public class GoalSelectorController extends DialogController implements SwitchSceneController {

  /**
   * Empty constructor for the controller. Only used as a means for instantiating the controller.
   */
  public GoalSelectorController() {

  }


  /**
   * Writes a GoalsRegister object a file, specified by the method parameter.
   *
   * @param goals    The GoalsRegister object that is written to file.
   * @param filePath The absolute path of the file.
   */
  public void writeGoalsToFile(GoalsRegister goals, String filePath) {
    try {
      GoalFileHandling.writeGoalsToFile(goals, filePath);
    } catch (Exception e) {
      displayErrorBox("Could not write GoalsRegister to file", e.getMessage());
    }
  }

  /**
   * Reads a GoalsRegister from a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return The GoalsRegister object read from file.
   */
  public GoalsRegister readGoalsFromFile(String filePath) {
    GoalsRegister goalsRegister;

    try {
      if (new File(filePath).exists()) {
        goalsRegister = GoalFileHandling.readGoalsFromFile(filePath);
      } else {
        goalsRegister = new GoalsRegister();
      }
      return goalsRegister;
    } catch (Exception e) {
      displayErrorBox("Could not read GoalsRegister from file", e.getMessage());
      return new GoalsRegister();
    }
  }

  /**
   * Reads the first line from a game file, whose location is specified by the parameter, which is
   * then split into a string array. Thi is used to locate the goal filepath.
   *
   * @return The goal file filepath as a string.
   */
  public String readGoalFilePath() {
    try {
      String[] gameFileArray = GameFileHandling.readFirstLineFromFile(
              System.getProperty("user.dir") + "/src/main/resources/gameFiles/game.game")
          .split("[\\\\/]");
      return System.getProperty("user.dir") + "/src/main/resources/goalFiles/" + gameFileArray[
          gameFileArray.length - 1].replace(".paths", ".goals");
    } catch (Exception e) {
      displayErrorBox(e.getMessage(), "You will be granted an empty goal register");
    }
    return null;
  }

  /**
   * The method attempts to add a goal to the goalsregister. To indicate if this was successful or
   * not, a boolean value is returned.
   *
   * @param goals  The GoalsRegister that the goal is being added to.
   * @param parent The view object for goal selection.
   * @param type   ComboBox containing goal types
   * @param value  TextField containing the inputted value (if the type is not inventory)
   * @param items  CheckComboBox containing Selected items (if the type is inventory)
   * @param msg    Label for displaying error message
   */
  public void addGoal(GoalsRegister goals, GoalSelector parent,
      ComboBox<GoalType> type, TextField value, CheckComboBox<String> items, Label msg) {

    msg.setOpacity(0);
    Goal goal;

    try {
      if (!items.getCheckModel().getCheckedItems().isEmpty()) {
        String itemsAsString = items.getCheckModel().getCheckedItems().toString();
        goal = GoalFactory.buildGoal(type.getValue(), itemsAsString);
        goals.addGoal(goal);
      } else if (!value.getText().isBlank()) {
        goal = GoalFactory.buildGoal(type.getValue(), value.getText());
        goals.addGoal(goal);
      } else if (!type.getSelectionModel().isEmpty()) {
        formatErrorMsg(msg,
            "The selected list of items for a inventory goal cannot be empty "
                + "and the input field for value cannot be blank");
      } else {
        formatErrorMsg(msg, "Both type and value fields must be filled in");
        return;
      }
    } catch (NumberFormatException nfe) {
      formatErrorMsg(msg, "Input must be a positive integer or zero");
    } catch (IllegalArgumentException iae) {
      formatErrorMsg(msg, iae.getMessage());
    } catch (StringIndexOutOfBoundsException e) {
      displayErrorBox("The created goal has an index issue",
          "Inventory goal cannot have inventory with just [");
    }

    parent.updateObservableGoalList();
    parent.clearFields(value, items);
  }

  /**
   * Method for deleting a goal from the GoalsRegister. Thereafter, the tableview is updated.
   *
   * @param goals         The GoalsRegister that the goal is being added to.
   * @param goalTableView The tableview that displays all selected goals.
   * @param parent        The view object for goal selection.
   */
  public void deleteGoal(GoalsRegister goals, TableView<Goal> goalTableView, GoalSelector parent) {
    try {
      Goal selectedGoal = goalTableView.getSelectionModel().getSelectedItem();
      goals.removeGoal(selectedGoal);
      parent.updateObservableGoalList();
    } catch (IllegalArgumentException iae) {
      displayErrorBox("Could not delete goal from GoalsRegister", iae.getMessage());
    }
  }

  /**
   * Deletes all entries in the goals tableview. Goals persist through play throughs, so this method
   * is meant for a swift reset of the table in case players wants to create goals from scratch.
   *
   * @param goals  The GoalsRegister that contains all the goals.
   * @param parent The view object for goal selection.
   */
  public void clearAll(GoalsRegister goals, GoalSelector parent) {
    Optional<ButtonType> isConfirmed = showConfirmationDialog();
    if (isConfirmed.isPresent() && isConfirmed.get() == ButtonType.OK) {
      goals.clearRegister();
      parent.updateObservableGoalList();
    }
  }

  /**
   * Toggles between the value input TextField and the items CheckComboBox, depending on the value
   * of the type ComboBox.
   *
   * @param inputs  HBox containing all input fields.
   * @param oldType The goal type before another type was chosen.
   * @param newType The current goal type.
   * @param value   TextField containing the inputted value (if the type is not inventory)
   * @param items   CheckComboBox containing Selected items (if the type is inventory)
   */
  public void switchValueMode(HBox inputs, GoalType oldType, GoalType newType,
      TextField value, CheckComboBox<String> items) {
    //Switching to inventory mode
    if (newType != null && newType.equals(GoalType.INVENTORY)) {
      inputs.getChildren().remove(1);
      inputs.getChildren().add(1, items);
    } else if (oldType != null && oldType.equals(GoalType.INVENTORY)) {
      inputs.getChildren().remove(1);
      inputs.getChildren().add(1, value);
    }
  }

  /**
   * Formats an error message. The text is set to the parameter value. The labels opacity is then
   * set to 1 (previously 0 - transparent) to display the message.
   *
   * @param errorMsg Label displaying the error message.
   * @param text     The text that the label will take
   */
  public void formatErrorMsg(Label errorMsg, String text) {
    errorMsg.setText(text);
    errorMsg.setOpacity(1);
  }

  /**
   * Displays an alert dialog box that ask for a delete confirmation.
   *
   * @return An Optional of type ButtonType.
   */
  private Optional<ButtonType> showConfirmationDialog() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Delete");
    alert.setHeaderText("Delete Confirmation");
    alert.setContentText("Are you sure you want delete all entries from the table?");

    return alert.showAndWait();
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
