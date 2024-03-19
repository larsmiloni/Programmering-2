package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.controller.CreateCharacterController;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for the CreateCharacter scene. The user can create a character, which will be added to the
 * {@link ChooseCharacter} table. The character has a name, starting health and gold and a player
 * avatar. Only the name is mandatory.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 02.05.2023
 */
public class CreateCharacter extends Ui {

  private final BorderPane root;
  private final CreateCharacterController ccc;
  private String avatarImageFilePath;

  /**
   * Instantiates the view for the CreateCharacter GUI. The method instantiates the controller and
   * set the action on buttons.
   */
  public CreateCharacter() {
    ccc = new CreateCharacterController();

    root = createAll("Create Your Character", HelpDialogBox.Mode.CREATE_CHARACTER);
    root.setCenter(createCenterContent());

    getNextBtn().setDisable(false);

    getBackBtn().setOnAction(event -> {
      Main.playSoundOnClick();
      ChooseCharacter cc = new ChooseCharacter();
      ccc.switchScene("ChooseCharacter.css", cc.getRoot(), "Choose Character");
    });
  }

  /**
   * Creates the centre content of the scene, being the fields for inputting name, health, gold and
   * choosing a player avatar.
   *
   * @return A HBox containing the scene's center content.
   */
  private HBox createCenterContent() {
    // Name
    ImageView nameImageView = createPngImageView("pencil", 50, 50);
    Label nameLabel = createMediumLabel("Name: ");
    TextField nameTextField = new TextField();
    nameTextField.setPromptText("Name");
    nameTextField.setPrefSize(400, 50);
    VBox nameVbox = new VBox(nameLabel, nameTextField);
    HBox nameHbox = new HBox(5, nameImageView, nameVbox);
    nameHbox.setAlignment(Pos.BOTTOM_LEFT);

    // Health
    TextField healthTextField = new TextField();
    addTextLimiter(healthTextField, 9);
    healthTextField.setPromptText("Health (Optional)");
    healthTextField.setPrefSize(400, 50);
    Label healthLabel = createMediumLabel("Health: ");
    VBox healthVbox = new VBox(healthLabel, healthTextField);
    ImageView healthImageView = createPngImageView("heart", 50, 50);
    HBox healthHbox = new HBox(5, healthImageView, healthVbox);
    healthHbox.setAlignment(Pos.BOTTOM_LEFT);

    //Gold
    TextField goldTextField = new TextField();
    addTextLimiter(goldTextField, 9);
    goldTextField.setPromptText("Gold (Optional)");
    goldTextField.setPrefSize(400, 50);
    Label goldLabel = createMediumLabel("Gold");
    VBox goldVbox = new VBox(goldLabel, goldTextField);
    ImageView goldImageView = createPngImageView("gold", 50, 50);
    HBox goldHbox = new HBox(5, goldImageView, goldVbox);
    goldHbox.setAlignment(Pos.BOTTOM_LEFT);
    Label goldWarningLabel = createWarningLabel("");
    VBox goldWarningVbox = new VBox(goldHbox, goldWarningLabel);

    Label nameWarningLabel = createWarningLabel("");
    VBox nameWarningVbox = new VBox(nameHbox, nameWarningLabel);
    Label healthWarningLabel = createWarningLabel("");
    VBox healthWarningVbox = new VBox(healthHbox, healthWarningLabel);
    VBox playerParamsVbox = new VBox(50, nameWarningVbox, healthWarningVbox, goldWarningVbox);
    playerParamsVbox.setAlignment(Pos.CENTER);

    avatarImageFilePath = "images/unknown.png";
    Image unknownAvatarImage = new Image(avatarImageFilePath);

    ImageView avatarImageView = new ImageView(unknownAvatarImage);
    avatarImageView.setFitWidth(400);
    avatarImageView.setFitHeight(600);
    avatarImageView.setPreserveRatio(true);

    Button uploadImageButton = new Button("Upload character image");
    uploadImageButton.setOnAction(event -> {
      Main.playSoundOnClick();
      avatarImageFilePath = ccc.addAvatarImage();
      if (avatarImageFilePath != null) {
        avatarImageView.setImage(new Image(avatarImageFilePath));
      }
    });

    VBox avatarVbox = new VBox(40, avatarImageView, uploadImageButton);
    avatarVbox.setAlignment(Pos.CENTER);

    HBox mainHbox = new HBox(100, playerParamsVbox, avatarVbox);
    mainHbox.setAlignment(Pos.CENTER);
    mainHbox.setMaxSize(1200, 800);
    mainHbox.setStyle(
        "-fx-border-width: 4px; -fx-border-color: black; -fx-background-color: white");

    //Write player to file and switch scene when pressing next
    getNextBtn().setOnAction(event -> {
      Main.playSoundOnClick();
      Player player = ccc.createPlayer(nameTextField.getText(),
          healthTextField.getText(), goldTextField.getText());

      ccc.writeCharacterToFile(player, avatarImageFilePath);

      ChooseCharacter cc = new ChooseCharacter();
      ccc.switchScene("ChooseCharacter.css", cc.getRoot(), "Choose Character");
    });

    //Prevents creating invalid characters
    getNextBtn().addEventFilter(
        ActionEvent.ACTION, event -> {
          if (!ccc.isValidName(nameTextField.getText(), nameWarningLabel)) {
            event.consume();
          } else if (ccc.isDuplicateName(nameTextField.getText(),
              System.getProperty("user.dir") + "/src/main/resources/playerFiles/players.players",
              nameWarningLabel, "A player of the same name already exists")
          ) {
            event.consume();
          } else if (!ccc.isValidHealth(healthTextField.getText(),
              healthWarningLabel, "Health can only be a positive integer")) {
            event.consume();
          } else if (!ccc.isValidGold(goldTextField.getText(),
              goldWarningLabel, "Gold can only be a positive integer")) {
            event.consume();
          }
        });

    return mainHbox;
  }

  /**
   * Gets the root node of the scene.
   *
   * @return The root borderpane in the scene.
   */
  public BorderPane getRoot() {
    return this.root;
  }
}


