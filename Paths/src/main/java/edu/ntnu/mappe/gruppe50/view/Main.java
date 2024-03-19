package edu.ntnu.mappe.gruppe50.view;

import edu.ntnu.mappe.gruppe50.model.fileutils.SettingsFileHandling;
import edu.ntnu.mappe.gruppe50.view.scenes.MainMenu;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This is the class that the main application is run from. When a user decides to run the
 * application, this class calls on the start method which starts the application.
 *
 * @author Harry Linrui Xu
 * @since 12.05.2023
 */

public class Main extends Application {

  private static String prevPane;
  private static Scene scene;
  private static MediaPlayer musicMediaPlayer;
  private static Stage stage;

  /**
   * The main method that calls on the start method.
   *
   * @param args String of arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Initializer method that is run each time class is called upon.
   *
   * @throws Exception If the method cannot be run.
   */
  @Override
  public void init() throws Exception {
    super.init();
  }

  /**
   * Method for getting the previous pane that was displayed.
   *
   * @return A string that references the previous pane.
   */
  public static String getPrevPane() {
    return prevPane;
  }

  /**
   * Sets the string that references the previous pane.
   *
   * @param prevPane The new string that will reference the previous pane.
   */
  public static void setPrevPane(String prevPane) {
    Main.prevPane = prevPane;
  }

  /**
   * Sets the audio level in the game based on the data it retrieves from file. If the file cannot
   * be read from, the audio is set to 0.
   *
   * @param filePath The absolute file path of the file that the audio levels are read from.
   */
  public static void setMediaPlayerVolume(String filePath) {
    try {
      musicMediaPlayer.setVolume(SettingsFileHandling.readMusicVolumeFromFile(filePath) / 100);
    } catch (IOException e) {
      musicMediaPlayer.setVolume(0);
    }
  }

  /**
   * Method for playing a sound effect on a button click. The sound effect is retrieved from a file.
   * If the file cannot be read from, the audio levels are set to zero.
   */
  public static void playSoundOnClick() {
    Media clickMedia = new Media(Paths.get(
            System.getProperty("user.dir")
                + "/src/main/resources/sound/sound_effects/clickSound.mp3")
        .toUri().toString());
    MediaPlayer clickMediaPlayer = new MediaPlayer(clickMedia);
    try {
      clickMediaPlayer.setVolume(SettingsFileHandling.readSoundVolumeFromFile(
          System.getProperty("user.dir") + "/src/main/resources/settings/volume.txt") / 100);
    } catch (IOException e) {
      clickMediaPlayer.setVolume(0);
    }
    clickMediaPlayer.play();
  }

  /**
   * Switches the current pane to the given pane.
   *
   * @param styleSheet The name of the style sheet of the scene.
   * @param root       The pane to switch to.
   * @param newTitle   The title of the stage.
   */
  public static void switchPane(String styleSheet, Pane root, String newTitle) {
    scene.setRoot(root);
    scene.getStylesheets().clear();
    scene.getStylesheets().add("styles/" + styleSheet);
    stage.setTitle(newTitle);
  }


  /**
   * The start method which is where the application is opened.
   *
   * @param primaryStage The stage or window on which the GUI is displayed.
   */
  @Override
  public void start(Stage primaryStage) {
    stage = primaryStage;
    Media musicMedia = new Media(
        Objects.requireNonNull(getClass().getResource("/sound/music/bm.mp3")).toString());
    // Create a MediaPlayer and play the background music
    musicMediaPlayer = new MediaPlayer(musicMedia);
    Main.setMediaPlayerVolume(
        System.getProperty("user.dir") + "/src/main/resources/settings/volume.txt");
    musicMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    musicMediaPlayer.play();

    MainMenu mainMenu = new MainMenu();

    scene = new Scene(mainMenu.getRoot());
    scene.getStylesheets().add("styles/MainMenu.css");

    primaryStage.setScene(scene);
    primaryStage.setTitle("Main menu");

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    primaryStage.setWidth(bounds.getWidth());
    primaryStage.setHeight(bounds.getHeight());

    primaryStage.setResizable(false);

    primaryStage.show();
  }
}
