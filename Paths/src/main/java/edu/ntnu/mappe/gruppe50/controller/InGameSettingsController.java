package edu.ntnu.mappe.gruppe50.controller;

import edu.ntnu.mappe.gruppe50.model.data.LinkRegister;
import edu.ntnu.mappe.gruppe50.model.fileutils.LinkFileHandling;
import edu.ntnu.mappe.gruppe50.model.fileutils.SettingsFileHandling;
import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.scene.layout.Pane;

/**
 * Controller class for InGameSettingsDialogBox. This class handles the values of the music and
 * sound volumes by calling on file handling methods from the model. This class also clearing and
 * saving progress of a game play through, given that the dialog box provides reset and save and
 * exit buttons. This is also done by interacting with model classes. The class inherits from both
 * the DialogController and SwitchSceneController which provide handy methods for controllers and
 * reduces code duplicity.
 *
 * @author Harry Linrui Xu
 * @since 17.05.2023
 */
public class InGameSettingsController extends DialogController implements SwitchSceneController {

  /**
   * Empty constructor whose only purpose is to instantiate the controller object.
   */
  public InGameSettingsController() {

  }

  /**
   * Reads the music volume from a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return A double between 0 and 1, indicating the music volume slider progress if the file can
   *     be read from. Else, returns 0.5.
   */
  public double readMusicVolumeFromFile(String filePath) {
    try {
      return SettingsFileHandling.readMusicVolumeFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not change music volume", e.getMessage());
    }
    return 0.5;
  }

  /**
   * Reads the sound volume from a file, specified by the method parameter.
   *
   * @param filePath The absolute path of the file.
   * @return A double between 0 and 1, indicating the sound volume slider progress if the file can
   *     be read from. Else, returns 0.5.
   */
  public double readSoundVolumeFromFile(String filePath) {
    try {
      return SettingsFileHandling.readSoundVolumeFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not change sound volume", e.getMessage());
    }
    return 0.5;
  }

  /**
   * Saves the progress of the music and volume sliders to a file, specified by the path parameter.
   *
   * @param musicVolume The progress value of the music volume slider.
   * @param soundVolume The progress value of the sound volume slider.
   * @param filePath    The absolute path of the file.
   */
  public void saveVolumeSettings(double musicVolume, double soundVolume, String filePath) {
    try {
      SettingsFileHandling.writeVolumeToFile(musicVolume, soundVolume, filePath);
      Main.setMediaPlayerVolume(
          System.getProperty("user.dir") + "/src/main/resources/settings/volume.txt");
    } catch (Exception e) {
      displayErrorBox("Could not save volume to file", e.getMessage());
    }
  }

  /**
   * Resets the progress of a game by clearing a file, specified by the parameter. This method is
   * used on the links.links file that tracks visited links.
   *
   * @param filePath The absolute path of the file.
   */
  public void clearProgress(String filePath) {
    try {
      LinkFileHandling.removeAllLinesFromFile(filePath);
    } catch (Exception e) {
      displayErrorBox("Could not clear the links.links file",
          System.getProperty("user.dir") + "/src/main/resources/linkFiles/links.links");
    }
  }

  /**
   * Saves the visited links in a game play through to file, allowing for loading games.
   *
   * @param links    The register that tracks the visited links.
   * @param filePath The absolute path of the file.
   */
  public void saveLinksToFile(LinkRegister links, String filePath) {
    try {
      LinkFileHandling.writeLinksToFile(links, filePath);
    } catch (Exception e) {
      displayErrorBox("Could not save the visited links to file", e.getMessage());
    }
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
