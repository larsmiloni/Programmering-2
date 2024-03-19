package edu.ntnu.mappe.gruppe50.view.popups;

import edu.ntnu.mappe.gruppe50.controller.CreateItemDialogController;
import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * View for the CreateItemDialog box that appears when on wishes to add a new item to the selection
 * of items that are used to create InventoryActions in the GoalSelector GUI. The class extends the
 * Dialog class.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class CreateItemDialogBox extends Dialog<ItemRegister> {

  private final CreateItemDialogController controller;
  private ObservableList<String> observableItemList;
  private final ItemRegister itemRegister;


  /**
   * Instantiates the controller for this class and sets the list of items that is displayed in the
   * dialog.
   *
   * @param items The list of items currently can be chosen.
   */
  public CreateItemDialogBox(ItemRegister items) {
    super();
    controller = new CreateItemDialogController();
    itemRegister = items;

    createContent(itemRegister);
  }

  /**
   * Creates the entire dialog box.
   *
   * @param items The list of items currently can be chosen.
   */
  private void createContent(ItemRegister items) {
    Label titleLbl = new Label("Create item");
    titleLbl.setFont(new Font(18));

    Label errorMsg = new Label("Field cannot be empty");
    errorMsg.setOpacity(0);
    errorMsg.setTextFill(Color.RED);

    observableItemList = FXCollections.observableList(
        items.getItems());

    ListView<String> itemListView = new ListView<>();
    itemListView.setItems(observableItemList);
    itemListView.setMaxHeight(200);

    TextField itemField = new TextField();

    Button addBtn = new Button();
    addBtn.setGraphic(new ImageView(new Image("images/add.png", 15, 15, false, false)));

    //Assign event handler to addBtn
    addBtn.setOnAction(btnPress -> {
          Main.playSoundOnClick();
          controller.addItem(itemRegister, itemField, errorMsg, this);
        }
    );

    //Add menuItem which allows for deleting item by right-clicking list
    MenuItem deleteMenuItem = new MenuItem("Delete");
    deleteMenuItem.setOnAction((ActionEvent event) -> {
      Main.playSoundOnClick();
      controller.removeItem(itemRegister, itemListView.getSelectionModel().getSelectedItem(), this);
    });

    //Add the menuItem to a context menu which is added to the table
    ContextMenu delete = new ContextMenu(deleteMenuItem);
    itemListView.setContextMenu(delete);

    VBox centerContainer = new VBox();

    HBox inputContainer = new HBox(5);
    inputContainer.getChildren().addAll(itemField, addBtn);

    centerContainer.getChildren().addAll(itemListView, inputContainer, errorMsg);

    BorderPane root = new BorderPane();
    root.setTop(titleLbl);
    root.setCenter(centerContainer);

    setTitle("Add item");
    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().setContent(root);

    //Return a new item register
    setResultConverter((ButtonType button) -> {
      if (button == ButtonType.OK) {
        return itemRegister;
      }
      return null;
    });

  }

  /**
   * The method synchs the tableview up with goals register, by having the observable list (which
   * the table view listens to) set its values to the goals register.
   */
  public void updateObservableList() {
    this.observableItemList.setAll(itemRegister.getItems());
  }

  /**
   * Clears the input fields after an item is added.
   *
   * @param item The inputted item.
   */
  public void clearFields(TextField item) {
    item.setText("");
  }
}
