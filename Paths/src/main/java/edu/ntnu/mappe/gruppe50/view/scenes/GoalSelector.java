package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.GoalSelectorController;
import edu.ntnu.mappe.gruppe50.controller.ItemCreatorController;
import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalsRegister;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

/**
 * This is the view(the interface) of the goal selector that the player interacts with. In this
 * interface, the player can add goals to a table that they wish to include in their play through of
 * a Paths story. This class, similar to all the scenes that precede the actual gameplay inherited
 * from the Ui class.
 *
 * @author Harry Linrui Xu
 * @since 29.3.2023
 */
public class GoalSelector extends Ui {

  private final GoalsRegister selectedGoals;
  private final ObservableList<Goal> goalsList;
  private final BorderPane root;
  private final GoalSelectorController gsc;
  private final ItemCreatorController icg;

  private ItemRegister items;
  private final ObservableList<String> itemList;

  /**
   * Constructor of the GoalSelector class. Creates all the visual elements of the scene, meaning
   * the title, the help and settings buttons, the tableview, buttons, input fields and the next and
   * back buttons.
   */
  public GoalSelector() {
    gsc = new GoalSelectorController();
    icg = new ItemCreatorController();

    //Load goalsregister from file
    String goalFilePath = gsc.readGoalFilePath();
    selectedGoals = gsc.readGoalsFromFile(goalFilePath);

    //Load item register from file
    String itemFilePath =
        System.getProperty("user.dir") + "/src/main/resources/itemFiles/items.items";
    items = icg.readItemRegisterFromFile(itemFilePath);

    //Setting observable lists
    itemList = FXCollections.observableArrayList(items.getItems());
    goalsList = FXCollections.observableArrayList(selectedGoals.getGoals());

    root = createAll("Select Goals", HelpDialogBox.Mode.GOAL_SELECTOR);
    root.setCenter(createCentreContent());

    //No condition need to be met in GoalSelector
    getNextBtn().setDisable(false);

    //Write changes to file and load new scene.
    getNextBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      gsc.writeGoalsToFile(selectedGoals, gsc.readGoalFilePath());
      icg.writeItemRegisterToFile(items, itemFilePath);

      InGameScene igc = new InGameScene(new Stage());
      gsc.switchScene("InGameScene.css", igc.getRoot(), "In Game");
    });

    //Write changes to file and load new scene.
    getBackBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      gsc.writeGoalsToFile(selectedGoals, gsc.readGoalFilePath());
      icg.writeItemRegisterToFile(items, itemFilePath);

      ChooseCharacter cc = new ChooseCharacter();
      gsc.switchScene("ChooseCharacter.css", cc.getRoot(), "Choose Character");
    });
  }

  /**
   * Gets the root node of the scene.
   *
   * @return The root BorderPane of the scene.
   */
  public BorderPane getRoot() {
    return this.root;
  }

  /**
   * Creates the centre content of the view, which are tableview, the input fields and the add
   * button.
   *
   * @return VBox containing the centre content.
   */
  private VBox createCentreContent() {
    VBox centreContent = new VBox(5);
    centreContent.setPadding(new Insets(100, 200, 10, 200));

    TableView<Goal> goalsTable = new TableView<>();

    //Synching the table to the observable list
    goalsTable.setItems(goalsList);

    goalsTable.setMaxHeight(500);
    goalsTable.getColumns().add(createTypeColumn());
    goalsTable.getColumns().add(createValueColumn());

    //Make columns fit the entire table
    goalsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    //Add menuItem which allows for deleting goals by right clicking the table
    MenuItem deleteMenuItem = new MenuItem("Delete");
    deleteMenuItem.setOnAction((ActionEvent event) -> {
      Main.playSoundOnClick();
      gsc.deleteGoal(selectedGoals, goalsTable, this);
    });

    //Add the menuItem to a context menu which is added to the table
    ContextMenu delete = new ContextMenu(deleteMenuItem);
    goalsTable.setContextMenu(delete);

    //Contains the error message
    HBox errorMsgContainer = new HBox();
    //Instantiate input fields, add and clear buttons and the error message
    TextField valueField = new TextField();
    addTextLimiter(valueField, 9);
    Label errorMsg = createWarningLabel("");
    //error message is by default transparent
    errorMsg.setOpacity(0);

    errorMsgContainer.getChildren().add(errorMsg);
    errorMsgContainer.setStyle(
        "-fx-border-width: 1px; -fx-border-color: black; -fx-background-color: white");

    CheckComboBox<String> itemSelector = createItemMultiSelect();
    ComboBox<GoalType> typeField = new ComboBox<>();
    typeField.setPrefWidth(goalsTable.getColumns().get(0).getMaxWidth());
    valueField.setPrefWidth(goalsTable.getColumns().get(0).getMaxWidth() - 40);
    itemSelector.setPrefWidth(goalsTable.getColumns().get(0).getMaxWidth() - 40);

    //Instantiate hbox that holds the input fields to add goals
    HBox inputFields = new HBox(5);
    //HBox used to contain the clear button and the items button
    typeField.setPromptText("Select type");
    typeField.setItems(FXCollections.observableArrayList(GoalType.values()));
    //Adding listener to typeField in order to recognize when inventory goal is chosen
    typeField.valueProperty().addListener((observableValue, oldGoalType, newGoalType) ->
        gsc.switchValueMode(inputFields, oldGoalType, newGoalType, valueField, itemSelector)
    );

    addTextLimiter(valueField, 9);
    valueField.setPromptText("Value");

    itemSelector.setTitle("Select items");

    Button addBtn = new Button();
    Button clearBtn = new Button("Clear table");

    //Assign event handler to clearBtn that clears table
    clearBtn.setOnAction(btnPress -> {
      Main.playSoundOnClick();
      gsc.clearAll(selectedGoals, this);
    });

    addBtn.setGraphic(new ImageView(new Image("images/add.png", 15, 15, false, false)));

    //Assign event handler to addBtn that adds goal
    addBtn.setOnAction(btnPress -> {
      Main.playSoundOnClick();
      gsc.addGoal(selectedGoals, this, typeField, valueField, itemSelector, errorMsg);
    });

    Button viewItemsBtn = new Button("Add selectable items");
    viewItemsBtn.setOnAction(btnPress -> {
      Main.playSoundOnClick();
      items = icg.viewSelectableItems(items, this, valueField, itemSelector);
    });

    HBox upperButtonContainer = new HBox(5);
    upperButtonContainer.getChildren().addAll(clearBtn, viewItemsBtn);

    inputFields.getChildren().addAll(typeField, valueField, addBtn);
    centreContent.getChildren()
        .addAll(upperButtonContainer, goalsTable, inputFields, errorMsgContainer);

    return centreContent;
  }

  /**
   * Creates a table column that represents the goal type.
   *
   * @return Table column containing strings.
   */
  private TableColumn<Goal, GoalType> createTypeColumn() {
    TableColumn<Goal, GoalType> typeColumn = new TableColumn<>("Type");
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    return typeColumn;
  }

  /**
   * Creates a table column that represents the goal value.
   *
   * @return Table column containing strings.
   */
  private TableColumn<Goal, String> createValueColumn() {
    TableColumn<Goal, String> valueColumn = new TableColumn<>("Value");
    valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    return valueColumn;
  }

  /**
   * Creates the CheckComboBox for the item multiselect.
   *
   * @return CheckComboBox for the item selection.
   */
  private CheckComboBox<String> createItemMultiSelect() {
    itemList.setAll(this.items.getItems());
    return new CheckComboBox<>(itemList);
  }

  /**
   * Updates the pool of items that can be chosen in the item selector CheckComboBox.
   */
  public void updateItemSelectorList() {
    this.itemList.setAll(items.getItems());
  }

  /**
   * The method synchs the tableview up with goals register, by having the observable list (which
   * the table view listens to) set its values to the goals register.
   */
  public void updateObservableGoalList() {
    this.goalsList.setAll(selectedGoals.getGoals());
  }

  /**
   * Clears the input fields after an item is added.
   *
   * @param value The inputted goal  value.
   * @param items The chosen items.
   */
  public void clearFields(TextField value, CheckComboBox<String> items) {
    value.clear();
    items.getCheckModel().clearChecks();
  }
}
