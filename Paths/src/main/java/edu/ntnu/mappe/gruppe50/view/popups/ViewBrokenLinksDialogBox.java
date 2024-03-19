package edu.ntnu.mappe.gruppe50.view.popups;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View class that displays a dialog containing all broken link for a given story.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 11.05.2023
 */
public class ViewBrokenLinksDialogBox {

  private final Stage dialog;

  /**
   * Constructor for the dialog box. Creates a new stage and sets the center content based on the
   * input Story.
   *
   * @param story The input Story that the broken links displayed are based on.
   */
  public ViewBrokenLinksDialogBox(Story story) {
    dialog = new Stage();
    createContent(story);
  }

  /**
   * Creates the center content of the box, being a table of all the broken links in the input
   * Story.
   *
   * @param story The input Story whose broken links are displayed.
   */
  private void createContent(Story story) {

    int windowWidth = 800;
    int windowHeight = 800;

    dialog.setMinWidth(windowWidth);
    dialog.setMinHeight(windowHeight);
    dialog.setMaxWidth(windowWidth);
    dialog.setMaxHeight(windowHeight);

    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setTitle("View broken links");

    Label titleLabel = new Label("Broken links in\n" + story.getTitle());
    titleLabel.setFont(new Font(50));

    double tableMaxWidth = 500;

    TableColumn<Link, String> textCol = new TableColumn<>("Link Text");
    textCol.setCellValueFactory(new PropertyValueFactory<>("text"));
    textCol.setPrefWidth(tableMaxWidth / 2);

    TableColumn<Link, String> refCol = new TableColumn<>("Link Reference");
    refCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
    refCol.setPrefWidth(tableMaxWidth / 2);

    // Create table and add columns
    TableView<Link> table = new TableView<>();
    table.getColumns().add(textCol);
    table.getColumns().add(refCol);

    double tableMaxHeight = 500;
    table.setMaxHeight(tableMaxHeight);
    table.setMaxWidth(tableMaxWidth);

    // Add data to table
    ObservableList<Link> brokenLinks = FXCollections.observableArrayList();
    brokenLinks.addAll(story.getBrokenLinks());
    table.setItems(brokenLinks);

    // Create root pane and add table to it
    VBox root = new VBox(100, titleLabel, table);
    root.setAlignment(Pos.CENTER);

    // Create scene and show stage
    Scene scene = new Scene(root, windowWidth, windowHeight);

    dialog.setScene(scene);
    dialog.showAndWait();
  }
}
