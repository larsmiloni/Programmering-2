package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.UserStoriesController;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfo;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfoRegister;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import edu.ntnu.mappe.gruppe50.view.popups.ViewBrokenLinksDialogBox;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * View for displaying the table of all the stories that the user have created and uploaded to the
 * game engine. The table displays the file name, path and number of broken links, as well as
 * offering buttons that can upload a new story, delete a story and view the broken links of a
 * story.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.05.2023
 */
public class UserStories extends Ui {

  private final BorderPane root;
  private final UserStoriesController usc;
  private TableView<UserStoryInfo> table;

  private final ObservableList<UserStoryInfo> storyInfoObservableList;

  private final UserStoryInfoRegister storyRegister;

  /**
   * Constructor for the UserStory scene. Instantiates the {@link UserStoriesController} and loads
   * in story data from a file. Sets the controls of buttons and creates all the visual elements on
   * the screen.
   */
  public UserStories() {
    usc = new UserStoriesController();

    String userStoriesFilePath = System.getProperty("user.dir")
        + "/src/main/resources/userStoryInfoFiles/userStoryInfo.userStoryInfo";
    storyRegister = usc.readUserStoriesFromFile(userStoriesFilePath);
    //Setting observable list to story data
    storyInfoObservableList = FXCollections.observableArrayList(storyRegister.getUserStoryInfos());

    root = createAll("Your stories", HelpDialogBox.Mode.USER_STORIES);
    root.setCenter(createCenterContent());

    //Saves the selected story path and user stories before switching scene
    getNextBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      usc.writeUserStoriesToFile(storyRegister, userStoriesFilePath);
      UserStoryInfo userStoryInfo = table.getSelectionModel().getSelectedItem();
      usc.savedSelectedStory(userStoryInfo.getFilePath());

      ChooseCharacter cc = new ChooseCharacter();
      Main.setPrevPane("UserStories");
      usc.switchScene("ChooseCharacter.css", cc.getRoot(), "Choose Character");
    });

    //Prevents switching scenes if the file does not exist on the users pc.
    getNextBtn().addEventFilter(
        ActionEvent.ACTION, event -> {
          UserStoryInfo userStoryInfo = table.getSelectionModel().getSelectedItem();
          if (!usc.isFileExists(userStoryInfo.getFilePath())) {
            event.consume();

            String header = "File does not exist";
            String context = "The chosen file does not exist in your computer";
            usc.displayErrorBox(header, context);
          }
        });

    setButtonDisableProperty(getNextBtn(),
        Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));

    //Saving user stories before switching scene
    getBackBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      usc.writeUserStoriesToFile(storyRegister, userStoriesFilePath);

      SelectStoryType sst = new SelectStoryType();
      usc.switchScene("SelectStoryType.css", sst.getRoot(), "Select Story Type");
    });
  }

  /**
   * Creates the center content that will be displayed in the view.
   *
   * @return An HBox containing the center content.
   */
  private HBox createCenterContent() {
    // Create the table and columns
    table = new TableView<>(storyInfoObservableList);

    TableColumn<UserStoryInfo, String> fileNameCol = new TableColumn<>("File Name");
    fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
    fileNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
    fileNameCol.setResizable(false);

    TableColumn<UserStoryInfo, String> pathCol = new TableColumn<>("File Path");
    pathCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
    pathCol.prefWidthProperty().bind(table.widthProperty().multiply(0.60));
    pathCol.setResizable(false);

    TableColumn<UserStoryInfo, Integer> numBrokenLinksCol = new TableColumn<>(
        "Number of Broken Links");
    numBrokenLinksCol.setCellValueFactory(new PropertyValueFactory<>("numBrokenLinks"));
    numBrokenLinksCol.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
    numBrokenLinksCol.setResizable(false);

    //make columns fit entire table
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    // Add the columns to the table
    table.getColumns().add(fileNameCol);
    table.getColumns().add(pathCol);
    table.getColumns().add(numBrokenLinksCol);

    double tablePrefWidth = Screen.getPrimary().getVisualBounds().getWidth() * 0.85;
    double tableMaxHeight = 700;
    table.setMaxHeight(tableMaxHeight);
    table.setPrefWidth(tablePrefWidth);
    // Create a layout and add the table to it
    BorderPane tableBorderPane = new BorderPane();
    tableBorderPane.setCenter(table);

    Button addStoryBtn = new Button("Add Story");
    addStoryBtn.setPrefSize(200, 200);
    addStoryBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      usc.addStory(storyRegister, this);
    });

    Button removeStoryBtn = new Button("Remove Story");
    removeStoryBtn.setPrefSize(200, 200);
    setButtonDisableProperty(removeStoryBtn,
        Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
    removeStoryBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      usc.deleteStory(storyRegister, table, this);
    });

    Button viewBrokenLinksBtn = new Button("View Broken Links");
    viewBrokenLinksBtn.setPrefSize(200, 200);
    setButtonDisableProperty(viewBrokenLinksBtn,
        Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));

    //Display dialog for given story
    viewBrokenLinksBtn.setOnAction(event -> {
      Main.playSoundOnClick();
      new ViewBrokenLinksDialogBox(
          usc.readStoryFromFile(table.getSelectionModel().getSelectedItem().getFilePath()));
    });
    //Consume event if story is null - could not be read from file
    viewBrokenLinksBtn.addEventFilter(ActionEvent.ACTION, event -> {
      //Retrieve story based on story file path in table
      Story story = usc.readStoryFromFile(
          table.getSelectionModel().getSelectedItem().getFilePath());
      if (story == null) {
        event.consume();
      }
    });

    VBox buttonsVbox = new VBox(20, addStoryBtn, removeStoryBtn, viewBrokenLinksBtn);
    buttonsVbox.setAlignment(Pos.CENTER);

    HBox tableButtonContainer = new HBox(20, tableBorderPane, buttonsVbox);
    tableButtonContainer.setAlignment(Pos.CENTER);
    tableButtonContainer.setPadding(new Insets(0, 25, 0, 25));

    return tableButtonContainer;
  }

  /**
   * Synchs the UserStoryInfo observable list with the model UserInfoRegister.
   */
  public void updateObservableList() {
    this.storyInfoObservableList.setAll(storyRegister.getUserStoryInfos());
  }

  /**
   * Gets the root borderpane, on which the all visual components are placed.
   *
   * @return The root Borderpane node in the scene.
   */
  public BorderPane getRoot() {
    return this.root;
  }
}




