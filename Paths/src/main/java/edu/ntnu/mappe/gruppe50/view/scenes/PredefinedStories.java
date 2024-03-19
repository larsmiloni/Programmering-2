package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.PredefinedStoryController;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

/**
 * This is the view(the interface) of story goal selector that the player interacts with. In this
 * interface, the player selects the story they wish to play. This class, similar to all the scenes
 * that precede the actual gameplay inherit from the Ui class.
 *
 * @author Harry Linrui Xu
 * @since 9.4.2023
 */
public class PredefinedStories extends Ui {

  private final PredefinedStoryController controller;

  private final BorderPane root;

  private String storyPath;

  /**
   * Constructor of the StorySelector class. Creates all the visual elements of the scene, meaning
   * the title, the help and settings buttons, the centre story buttons and the next and back
   * buttons.
   */

  public PredefinedStories() {
    controller = new PredefinedStoryController();

    root = createAll("Choose Story", HelpDialogBox.Mode.PREDEFINED_STORIES);
    root.setCenter(createCentreContent());

    getNextBtn().setOnAction(e -> {
      Main.playSoundOnClick();
      ChooseCharacter cc;
      if (storyPath != null) {
        controller.writeSelectedStoryToFile(storyPath);

        cc = new ChooseCharacter();
        Main.setPrevPane("StorySelector");
        controller.switchScene("ChooseCharacter.css", cc.getRoot(), "Choose Character");
      }
    });

    getBackBtn().setOnAction(event -> {
      Main.playSoundOnClick();
      SelectStoryType sst = new SelectStoryType();
      controller.switchScene("SelectStoryType.css", sst.getRoot(), "Select Story Type");
    });
  }

  /**
   * Gets the rood node of the scene.
   *
   * @return The root BorderPane of the scene.
   */
  public BorderPane getRoot() {
    return this.root;
  }

  /**
   * Creates the centre content of the view, which are the buttons where one can choose which story
   * to play.
   *
   * @return A GridPane containing the centre visual elements.
   */
  private GridPane createCentreContent() {
    GridPane centreContent = new GridPane();

    //Add padding to create air between title and buttons
    centreContent.setPadding(new Insets(50));

    //Set alignment and hor/vert spacing between each node
    centreContent.setAlignment(Pos.CENTER);
    centreContent.setVgap(100);
    centreContent.setHgap(200);

    //Instantiate buttons - add title, location and number of broken links
    Button deadlyCastleBtn = new Button("Deadly Castle");
    deadlyCastleBtn.setTextAlignment(TextAlignment.CENTER);

    Button underProgressBtn = new Button("Under Progress");
    underProgressBtn.setDisable(true);
    underProgressBtn.setTextAlignment(TextAlignment.CENTER);

    //Instantiate image views of each story which are laid on top of the buttons
    ImageView deadlyCastleImgView = createJpgImageView("deadly-castle", 550, 250);
    ImageView underProgressImgView = createPngImageView("unknown", 550, 250);

    //Set image views on buttons
    deadlyCastleBtn.setGraphic(deadlyCastleImgView);
    underProgressBtn.setGraphic(underProgressImgView);

    //Set image placement within button
    deadlyCastleBtn.setContentDisplay(ContentDisplay.TOP);
    underProgressBtn.setContentDisplay(ContentDisplay.TOP);

    centreContent.add(deadlyCastleBtn, 0, 0);
    centreContent.add(underProgressBtn, 1, 0);

    //Disable other story options if one is chosen
    deadlyCastleBtn.setOnAction(btnPress -> {
      Main.playSoundOnClick();
      deadlyCastleBtn.setStyle("-fx-border-color: yellow ; -fx-border-width: 2px ;");
      //Enable getNextBtn() once a story has been chosen
      getNextBtn().setDisable(false);
      storyPath =
          System.getProperty("user.dir") + "/src/main/resources/storyFiles/deadly_castle.paths";
    });
    return centreContent;
  }
}