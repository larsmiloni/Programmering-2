package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidGoalFormatException;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidLinkFormatException;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidStoryFormatException;
import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.model.data.LinkRegister;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class that is responsible for reading from and writing to .game files that contain information of
 * a game. The class is coupled with file handling classes that read and write the constituent parts
 * of a game, being a player, story, goal and links. The lines in the .game file is used as a
 * reference for the file handling classes to retrieve specific lines in their files, such as a
 * distinct player or story.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 04.05.2023
 */
public class GameFileHandling {

  private static final String gameFileType = ".game";

  /**
   * Reads a .game file and creates a game from the data extracted from the file. The lines in the
   * file is used to reference a distinct player, story, goals file and links file which are used to
   * create the game.
   *
   * @param filePath The absolute file path of the file.
   * @param inMain   Indicates if the files should be in main or test.
   * @return An instance of the Game object.
   * @throws IOException                 If the filetype is not .game, if the formatting of the
   *                                     .game file is invalid or if any of the constituent files
   *                                     could not be found.
   * @throws InvalidStoryFormatException If the formatting of the .paths file is invalid.
   * @throws InvalidLinkFormatException  If the formatting of the .links file is invalid.
   * @throws InvalidGoalFormatException  If the formatting of the .goals file is invalid.
   * @throws NullPointerException        If the file has only one line.
   */
  public static Game readGameFromFile(String filePath, boolean inMain)
      throws IOException, InvalidStoryFormatException, InvalidLinkFormatException,
      InvalidGoalFormatException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (!filePath.endsWith(gameFileType)) {
      throw new IOException("The file does not have the correct " + gameFileType + " file type");
    }
    String mainOrTest = "main";
    if (!inMain) {
      mainOrTest = "test";
    }
    String[] gameFilesArray = GameFileHandling.readFirstLineFromFile(
            System.getProperty("user.dir") + "/src/" + mainOrTest
               + "/resources/gameFiles/game.game")
        .split("[\\\\/]");

    Player player = PlayerFileHandling.readPlayerFromFile(
        GameFileHandling.readSecondLineFromFile(filePath),
        filePath.replace("gameFiles/game.game", "playerFiles/players.players"));
    Story story = StoryFileHandling.readStoryFromFile(GameFileHandling.readFirstLineFromFile(
        System.getProperty("user.dir") + "/src/" + mainOrTest + "/resources/gameFiles/game.game"));
    List<Goal> goals = GoalFileHandling.readGoalsFromFile(
        System.getProperty("user.dir") + "/src/" + mainOrTest + "/resources/goalFiles/"
            + gameFilesArray[gameFilesArray.length - 1].replace(".paths", ".goals")).getGoals();
    LinkRegister links = LinkFileHandling.readLinksFromFile(
        System.getProperty("user.dir") + "/src/" + mainOrTest + "/resources/linkFiles/links.links");
    return new Game(player, story, goals, links);
  }

  /**
   * Writes a single line to a file, both of which are specified by the method parameters.
   *
   * @param filePath    The absolute file path of the file.
   * @param lineToWrite The line that is written to the file.
   * @throws IOException If the file type is not .game.
   */
  public static void writeLineToFile(String filePath, String lineToWrite) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (lineToWrite == null) {
      throw new IllegalArgumentException("LineToWrite cannot be null.");
    }
    if (!filePath.endsWith(gameFileType)) {
      throw new IOException("The file does not have the correct " + gameFileType + " file type");
    }

    try (FileWriter fileWriter = new FileWriter(filePath, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
      bufferedWriter.write(lineToWrite);
      bufferedWriter.newLine();
    } catch (IOException e) {
      throw new IOException("Error writing to file: " + filePath);
    }
  }

  /**
   * Reads the first line from a file, specified by the parameter. This is the absolute file path of
   * the .paths file.
   *
   * @param filePath The absolute file path of the file.
   * @return The first line of the file as a string. Is the .paths file path.
   * @throws IOException If the file type is not .game.
   */
  public static String readFirstLineFromFile(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (!filePath.endsWith(gameFileType)) {
      throw new IOException("The file does not have the correct " + gameFileType + " file type");
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      return reader.readLine();
    } catch (IOException e) {
      throw new IOException("Failed to read first line from file: " + filePath, e);
    }
  }

  /**
   * Reads the second line from the file, specified by the parameter. This is the name of the
   * player.
   *
   * @param filePath The absolute file path of the file.
   * @return The second line of the file as a string. Is the player name.
   * @throws IOException If the file type is not .game.
   */
  public static String readSecondLineFromFile(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (!filePath.endsWith(gameFileType)) {
      throw new IOException("The file does not have the correct " + gameFileType + " file type");
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      reader.readLine();
      return reader.readLine();
    } catch (IOException e) {
      throw new IOException("Failed to read first line from file: " + filePath, e);
    }
  }

  /**
   * Replaces the first line in the file with a new line, both specified by the method parameters.
   * These are file paths to .paths files.
   *
   * @param filePath     The absolute file path of the file.
   * @param newFirstLine The line that replaces the old line.
   * @throws IOException If the file type is not .game.
   */
  public static void replaceFirstLine(String filePath, String newFirstLine) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (!filePath.endsWith(gameFileType)) {
      throw new IOException("The file does not have the correct " + gameFileType + " file type");
    }

    String secondLine = readSecondLineFromFile(filePath);

    removeAllLinesFromFile(filePath);
    writeLineToFile(filePath, newFirstLine);
    if (secondLine != null) {
      writeLineToFile(filePath, secondLine);
    }
  }

  /**
   * Replaces the second line in the file with a new line, both specified by the method parameters.
   * These are player names.
   *
   * @param filePath      The absolute file path of the file.
   * @param newSecondLine The line that replaces the old line.
   * @throws IOException If the file type is not .game.
   */
  public static void replaceSecondLine(String filePath, String newSecondLine) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (!filePath.endsWith(gameFileType)) {
      throw new IOException("The file does not have the correct " + gameFileType + " file type");
    }

    String firstLine = readFirstLineFromFile(filePath);

    removeAllLinesFromFile(filePath);
    if (firstLine != null) {
      writeLineToFile(filePath, firstLine);
    }
    writeLineToFile(filePath, newSecondLine);
  }

  /**
   * Clears an entire file, specified by the parameter.
   *
   * @param filePath The absolute file path of the file.
   * @throws IOException If the file cannot be written to file.
   */
  public static void removeAllLinesFromFile(String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath)) {
      writer.write("");
    } catch (IOException e) {
      throw new IOException("Failed to empty file: " + filePath, e);
    }
  }
}

