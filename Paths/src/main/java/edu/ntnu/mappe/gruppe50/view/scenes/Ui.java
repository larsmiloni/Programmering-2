package edu.ntnu.mappe.gruppe50.view.scenes;

import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.view.Main;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox;
import edu.ntnu.mappe.gruppe50.view.popups.HelpDialogBox.Mode;
import edu.ntnu.mappe.gruppe50.view.popups.InGameSettingsDialogBox;
import edu.ntnu.mappe.gruppe50.view.popups.SettingsDialogBox;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * Class that contains all the necessary Ui elements for the scenes preceding the actual gameplay.
 * This includes a title, help and settings and next and back buttons. The class is used as a
 * template that the other scenes inherit from. It has a method that returns the general Ui
 * appearance where all elements are included, however, the methods that construct each set piece
 * are also made public so that inheriting classes can pick and choose which parts to include. .
 *
 * @author Harry Linrui Xu
 * @since 29.4.2023
 */
public abstract class Ui {

  private Button backBtn;
  private Button nextBtn;
  private Button settingsBtn;

  /**
   * Empty constructor who's only purpose is to instantiate the Ui object.
   */
  public Ui() {

  }

  /**
   * Getter for the back button.
   *
   * @return The back button.
   */
  public Button getBackBtn() {
    return this.backBtn;
  }

  /**
   * Getter for the next button.
   *
   * @return The next button.
   */
  public Button getNextBtn() {
    return this.nextBtn;
  }

  /**
   * Method for setting the game that the {@link InGameSettingsDialogBox} saves to file.
   *
   * @param game The game that is saved to file.
   */
  public void setSettingsBtn(Game game) {
    this.settingsBtn.setOnAction(btnPress -> {
      Main.playSoundOnClick();
      new InGameSettingsDialogBox(game);
    });
  }

  /**
   * Creates the general Ui appearance, in which all the visual elements are included.
   *
   * @param title Title of the scene.
   * @param mode The mode of the Help dialog.
   * @return A borderpane containing the general Ui view.
   */
  public BorderPane createAll(String title, Mode mode) {
    BorderPane root = new BorderPane();

    root.setTop(createTitleHelpSettings(title, mode));
    root.setBottom(createBackNextBtn());
    root.setBackground(new Background(getBackGroundImage("pixel-mountains.jpg")));
    return root;
  }

  /**
   * Creates the top border visual elements which are the title, help and settings buttons in a
   * borderpane in an in-game scene.
   *
   * @param title The title of the view.
   * @param game  The game that is saved if the "save and exit" button is pressed.
   * @return Borderpane containing the top border elements of the Ui.
   */
  public BorderPane createTitleHelpSettingsInGame(String title, Game game) {
    BorderPane topContainer = new BorderPane();

    HBox titleContainer = createTitle(title);
    HBox buttonsContainer = createHelpSettingsBtnInGame(game);

    Region region = new Region();

    //Used to create space on the left side to centralize title
    region.setPrefWidth(buttonsContainer.getPrefWidth());

    topContainer.setCenter(titleContainer);
    topContainer.setRight(buttonsContainer);
    topContainer.setLeft(region);

    return topContainer;
  }

  /**
   * Creates the top border visual elements which are the title, help and settings buttons in a
   * borderpane, not in the in-game scene.
   *
   * @param title The title of the view.
   * @return Borderpane containing the top border elements of the Ui.
   */
  private BorderPane createTitleHelpSettings(String title, Mode mode) {
    BorderPane topContainer = new BorderPane();

    HBox titleContainer = createTitle(title);
    HBox buttonsContainer = createHelpSettingsBtn(mode);

    Region region = new Region();

    //Used to create space on the left side to centralize title
    region.setPrefWidth(buttonsContainer.getPrefWidth());

    topContainer.setCenter(titleContainer);
    topContainer.setRight(buttonsContainer);
    topContainer.setLeft(region);

    return topContainer;
  }

  /**
   * Creates only title of the scene.
   *
   * @param titleText The title.
   * @return HBox containing the title.
   */
  private HBox createTitle(String titleText) {
    HBox titleContainer = new HBox();
    Text title = new Text(titleText);
    title.setFont(new Font(46));
    titleContainer.getChildren().add(title);
    titleContainer.setAlignment(Pos.CENTER);

    return titleContainer;
  }

  /**
   * Creates only the help and settings buttons in the in-game scenes. Useful in case a scenes does
   * not need a title and only the help/settings buttons.
   *
   * @return HBox containing the buttons.
   */
  private HBox createHelpSettingsBtnInGame(Game game) {
    HBox helpAndSettings = new HBox(5);
    helpAndSettings.setPrefWidth(180);

    ImageView settingsImgView = createPngImageView("settings", 75, 75);
    settingsBtn = new Button();
    settingsBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      new InGameSettingsDialogBox(game);
    });
    settingsBtn.setGraphic(settingsImgView);

    Button helpBtn = new Button("Help");
    helpBtn.setPrefWidth(82);
    helpBtn.setPrefHeight(82);
    helpBtn.setFont(new Font(18));
    //Display help dialog box
    helpBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      showHelp(Mode.IN_GAME);
    });

    helpAndSettings.getChildren().addAll(helpBtn, settingsBtn);

    return helpAndSettings;
  }

  /**
   * Creates only the help and settings buttons in the scenes that are not in-game. Useful in case a
   * scenes does not need a title and only the help/settings buttons.
   *
   * @return HBox containing the buttons.
   */
  private HBox createHelpSettingsBtn(Mode mode) {
    HBox helpAndSettings = new HBox(5);
    helpAndSettings.setPrefWidth(180);

    ImageView settingsImgView = createPngImageView("settings", 75, 75);
    settingsBtn = new Button();
    settingsBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      new SettingsDialogBox();
    });
    settingsBtn.setGraphic(settingsImgView);

    Button helpBtn = new Button("Help");
    helpBtn.setPrefWidth(82);
    helpBtn.setPrefHeight(82);
    helpBtn.setFont(new Font(18));
    //Display help dialog box
    helpBtn.setOnAction(e -> {
      Main.playSoundOnClick();
      showHelp(mode);
    });

    helpAndSettings.getChildren().addAll(helpBtn, settingsBtn);

    return helpAndSettings;
  }

  /**
   * Creates and formats a JPG image view containing a given image.
   *
   * @param imageName Name of image without the .jpg image type.
   * @param width     Image width.
   * @param height    Image height.
   * @return A formatted image view with the specified JPG image.
   */
  public ImageView createJpgImageView(String imageName, int width, int height) {
    Image image = new Image("images/" + imageName + ".jpg", width, height, false, false);
    return new ImageView(image);
  }

  /**
   * Creates and formats a PNG image view containing a given image.
   *
   * @param imageName Name of image without the .png image type.
   * @param width     Image width.
   * @param height    Image height.
   * @return A formatted image view with the specified PNG image.
   */
  public ImageView createPngImageView(String imageName, int width, int height) {
    Image image = new Image("images/" + imageName + ".png", width, height, false, false);
    return new ImageView(image);
  }

  /**
   * Creates the back and next buttons.
   *
   * @return BorderPane containing the back and next buttons.
   */
  private BorderPane createBackNextBtn() {
    backBtn = new Button("Back");
    nextBtn = new Button("Next");

    javafx.scene.image.ImageView nextImgView = new javafx.scene.image.ImageView(
        new javafx.scene.image.Image("images/next.png", 50, 50, false, false));
    javafx.scene.image.ImageView backImgView = new javafx.scene.image.ImageView(
        new Image("images/back.png", 50, 50, false, false));

    backBtn.setGraphic(backImgView);
    backBtn.setContentDisplay(ContentDisplay.LEFT);

    nextBtn.setGraphic(nextImgView);
    nextBtn.setContentDisplay(ContentDisplay.RIGHT);

    //By default is disabled - conditions must be met first
    nextBtn.setDisable(true);

    BorderPane buttonBar = new BorderPane();
    buttonBar.setLeft(backBtn);
    buttonBar.setRight(nextBtn);
    buttonBar.setPadding(new Insets(5, 20, 5, 20));
    return buttonBar;
  }

  /**
   * Displays a customized help dialog box.
   */
  private void showHelp(Mode mode) {
    new HelpDialogBox(mode);
  }

  /**
   * Method for getting the current background image in the GUI.
   *
   * @param imageName The name of the image.
   * @return The current background image.
   */
  public BackgroundImage getBackGroundImage(String imageName) {
    double width = Screen.getPrimary().getVisualBounds().getWidth();
    double height = Screen.getPrimary().getVisualBounds().getHeight();

    //maintain aspect ratio is turned off
    return new BackgroundImage(new Image("images/" + imageName, width, height, false, true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
  }

  /**
   * Method for creating a label with customized text and font size 24.
   *
   * @param text The customizable text.
   * @return A label with font size 24.
   */
  public Label createSmallLabel(String text) {
    Label label = new Label(text);
    label.setFont(new Font(24));
    return label;
  }

  /**
   * Method for creating a label with customized text and font size 40.
   *
   * @param text The customizable text.
   * @return A label with font size 40.
   */
  public Label createMediumLabel(String text) {
    Label label = new Label(text);
    label.setFont(new Font(40));
    return label;
  }

  /**
   * Method for creating a label with customized text and font size 80.
   *
   * @param text The customizable text.
   * @return A label with font size 80.
   */
  public Label createBigLabel(String text) {
    Label label = new Label(text);
    label.setFont(new Font(80));
    return label;
  }

  /**
   * Method for creating a warning label with customized text and font size 20.
   *
   * @param text The customizable text.
   * @return A warning with font size 20.
   */
  public Label createWarningLabel(String text) {
    Label label = new Label(text);
    label.setFont(new Font(20));
    label.setStyle("-fx-text-fill: #a62828");
    return label;
  }

  /**
   * Binds a button disable property to a given condition provided by the caller.
   *
   * @param button    The button whose disable property will be bounded to a condition.
   * @param condition A boolean condition.
   */
  public void setButtonDisableProperty(Button button, BooleanBinding condition) {
    button.disableProperty().bind(condition);
  }

  /**
   * Adds a text limiter to the provided TextField.
   *
   * @param textField The TextField to which the text limiter will be added.
   * @param maxLength The maximum length of the text allowed in the TextField.
   */
  public void addTextLimiter(final TextField textField, final int maxLength) {
    textField.textProperty().addListener((ov, oldValue, newValue) -> {
      if (textField.getText().length() > maxLength) {
        String s = textField.getText().substring(0, maxLength);
        textField.setText(s);
      }
    });
  }
}
