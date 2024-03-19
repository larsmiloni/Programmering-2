package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.InGameSceneController;
import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.view.Main;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The InGameScene represents a gameplay scene in paths. Its responsibility is displaying the
 * current in the story that the player finds themselves in. The root is assembled by the Ui
 * elements inherited from the Ui class (help/settings) as well as the Hud from the Hud class and
 * the center box used to display the story.
 *
 * @author Harry Linrui Xu
 * @since 28.3.2023
 */
public class InGameScene extends Ui {

  private final InGameSceneController igsc;

  private final Stage window;

  private Game game;

  private final BorderPane root;

  private final Hud hud;

  private final String GAME_FILE_PATH = System.getProperty("user.dir")
      + "/src/main/resources/gameFiles/game.game";
  private final String LINK_FILE_PATH = System.getProperty("user.dir")
      + "/src/main/resources/linkFiles/links.links";

  /**
   * Constructor for the all the InGameScene. The root borderpane is filled is assembled by Ui
   * elements (help/settings and Hud) as well as the background image and the center Vbox used for
   * displaying the story.
   *
   * @param stage The window on which the GUI is displayed.
   */
  public InGameScene(Stage stage) {
    igsc = new InGameSceneController();
    root = new BorderPane();
    window = stage;
    game = igsc.readGameFromFile(GAME_FILE_PATH);
    //Return to main menu if game cannot be read properly
    // Instantiates the Hud
    hud = new Hud(game.getPlayer(), game.getGoals());

    //Add Ui related elements (help/settings and Hud)
    //and background image and center vbox
    root.setTop(createTitleHelpSettingsInGame(game.getStory().getTitle(), game));
    root.setBottom(hud.getHud());

    VBox storyBox = createCenterContainer();
    root.setCenter(storyBox);

    displayBackground("pixel-mountains.jpg");

    Passage firstPassage = igsc.getFirstPassage(game, GAME_FILE_PATH, LINK_FILE_PATH);
    setCenterContent(firstPassage, game.getPlayer(), storyBox);
  }


  /**
   * Returns the root borderpane that contains all GUI related elements in the class.
   *
   * @return Root borderpane.
   */
  public BorderPane getRoot() {
    return this.root;
  }

  /**
   * Creates the centre vbox that holds the passage title, its contents and the link buttons.
   *
   * @return VBox containing the story text and the link buttons.
   */
  private VBox createCenterContainer() {
    VBox centerContainer = new VBox(10);
    centerContainer.setStyle(
        "-fx-border-width: 4px; -fx-border-color: black; -fx-background-color: white;");
    centerContainer.setAlignment(Pos.CENTER);
    BorderPane.setMargin(centerContainer, new Insets(100, 350, 10, 350));

    return centerContainer;
  }

  /**
   * Displays the background image of a particular passage. Changes as the story progresses.
   *
   * @param fileName The name of the image file.
   */
  public void displayBackground(String fileName) {
    BackgroundImage backgroundImg = getBackGroundImage(fileName);
    root.setBackground(new Background(backgroundImg));
  }

  /**
   * Sets the passage that is currently displayed. The method calls upon itself, making it
   * recursive.
   *
   * @param passage  The passage to which the center content will be set.
   * @param player   The player character in the game play through
   * @param storyBox The root vbox containing the passage contents and links
   */
  public void setCenterContent(Passage passage, Player player, VBox storyBox) {
    //Set player stats
    hud.setPlayerStats(player, window);

    //Sends player to death passage if health reaches zero
    if (player.getHealth() == 0) {
      passage = new Passage(igsc.createDeathPassage().getTitle(),
          igsc.createDeathPassage().getContent());
    }

    Label content = new Label(passage.getContent());
    content.setFont(new Font(18));

    //Use stackpane to centralize content text
    StackPane contentDisplayer = new StackPane(content);
    contentDisplayer.setAlignment(Pos.CENTER);

    //Allows for scrolling in case there is a lot of text
    ScrollPane contentScrollPane = new ScrollPane();
    contentScrollPane.setContent(contentDisplayer);

    //Formatting scrollpane
    contentScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    contentScrollPane.setStyle(
        "-fx-border-width: 4px 0; -fx-border-color: black; -fx-background-color: white;");
    contentScrollPane.setPrefHeight(400);

    //Make stackpane fill entire parent's width
    contentDisplayer.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
            contentScrollPane.getViewportBounds().getWidth(),
        contentScrollPane.viewportBoundsProperty()));

    content.setWrapText(true);
    // Bind label width to a bit less than parent width
    content.prefWidthProperty().bind(contentDisplayer.widthProperty().subtract(50));

    //Create the buttons, representing links
    HBox buttons = createButtons(passage, player, storyBox);

    Label title = createMediumLabel(passage.getTitle());
    storyBox.getChildren().clear();
    storyBox.getChildren().addAll(title, contentScrollPane, buttons);
  }

  /**
   * Creates and sets the controls of the buttons one can click during a game.
   *
   * @param passage  The current passage in the story.
   * @param storyBox The root box that contains the passage title, its content and the link
   *                 buttons.
   * @return HBox containing the link buttons.
   */
  private HBox createButtons(Passage passage, Player player, VBox storyBox) {
    //Node used to contain the link buttons
    HBox buttons = new HBox(5);
    buttons.setAlignment(Pos.CENTER);
    buttons.setPadding(new Insets(25));

    //Creates normal buttons if passage has links
    if (passage.hasLinks()) {
      createLinkButtons(passage, player, storyBox, buttons);
    } else { //Create restart and main menu button if passage is end passage
      //Adding buttons to end passages
      Button restartBtn = new Button("Restart");
      restartBtn.setOnAction(btnPress -> {
        Main.playSoundOnClick();
        //Reset the visited links
        igsc.clearProgress(LINK_FILE_PATH);
        game = igsc.readGameFromFile(GAME_FILE_PATH);
        //Take player back to start with base stats
        setCenterContent(game.getStory().getOpeningPassage(), game.getPlayer(), storyBox);
      });

      Button mainMenuBtn = new Button("Main menu");
      mainMenuBtn.setOnAction(btnPress -> {
        Main.playSoundOnClick();
        MainMenu mainMenu = new MainMenu();
        igsc.switchScene("MainMenu.css", mainMenu.getRoot(), "Main Menu");
      });
      buttons.getChildren().addAll(restartBtn, mainMenuBtn);
    }
    return buttons;
  }

  /**
   * Creates and sets the controls of the buttons that represent the links that one can choose
   * during a play through.
   *
   * @param passage         The current passage in the story.
   * @param storyBox        The root box that contains the passage title, its content and the link
   *                        buttons.
   * @param buttonContainer The container of the link buttons
   */
  private void createLinkButtons(Passage passage, Player player, VBox storyBox,
      HBox buttonContainer) {
    List<Link> allLinks = passage.getLinks();
    //Creates a button for each link in the passage

    allLinks.forEach(link -> {
      Button button = new Button(link.getText());
      if (game.getAccessibleLinks(passage, player).contains(link)) {
        //Gets the passage of the chosen link
        button.setOnAction(btnPress -> {
          Main.playSoundOnClick();
          Passage chosenPassage = igsc.selectLink(game, link);
          //Set where the game is saved if one save and quits from settings
          setSettingsBtn(game);
          //Sets the current passage, in a recursive loop
          if (chosenPassage != null) {
            setCenterContent(chosenPassage, player, storyBox);
          }
        });
      } else {
        button.setDisable(true);
      }

      buttonContainer.getChildren().add(button);
    });
  }

  /*
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

    itemStage.setScene(new Scene(itemDisplayer));

    //Setting hover property on button
    button.hoverProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        itemStage.show();
      } else {
        itemStage.hide();
      }
    });
  }*/
}
