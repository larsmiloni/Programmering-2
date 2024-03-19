package edu.ntnu.mappe.gruppe50.model.fileutils;


import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidStoryFormatException;
import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File handling class used for reading and writing a Story instance, from and to a .paths file. The
 * class is very strict on the formatting of a .paths file and therefore has extensive exception
 * handling to prevent invalid .paths files to be read from.
 *
 * @author Harry Linrui Xu
 * @since 20.03.2023
 */
public class StoryFileHandling extends BaseStoryFileHandling {

  private static final String storyFileType = ".paths";

  /**
   * Writes an instance of Story to file a .paths file.
   *
   * @param story    The story object that is written to file.
   * @param filePath The absolute file path of the .paths file.
   * @throws IOException                 If the file cannot be written to, or if story or filePath
   *                                     are null
   * @throws InvalidStoryFormatException If the file path is not .paths.
   */
  public static void writeStoryToFile(Story story, String filePath)
      throws IOException, InvalidStoryFormatException {

    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fileWriter)) {

      if (!filePath.endsWith(storyFileType)) {
        throw new InvalidStoryFormatException(
            "The file does not have the correct " + storyFileType + " file type");
      }

      bw.write(story.toString());
    } catch (IOException ex) {
      throw new IOException("Error writing story to file: " + ex.getMessage(), ex);
    } catch (NullPointerException nfe) {
      throw new IOException("The input story and filePath cannot be null", nfe);
    }
  }

  /**
   * Method for reading a story from a .paths file. The method is very strict on the formatting of
   * the .paths file that is read and contains extensive exception handling to prevent invalid
   * stories from being read.
   *
   * @param filePath The absolute path of the file that is read.
   * @return A story object created from the .paths file
   * @throws IOException                 If the file does not exist or if the file path is null.
   * @throws InvalidStoryFormatException If the file violates the .paths file format. This entails
   *                                     invalid file type, invalid passages, links, actions, goals
   *                                     and general formatting issues such as extra empty lines or
   *                                     spaces before an entry.
   */
  public static Story readStoryFromFile(String filePath)
      throws IOException, InvalidStoryFormatException {

    Story story = null;
    Passage passage = null;
    String passageTitle = null;
    Link link = null;
    String storyTitle = null;

    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      if (!filePath.endsWith(storyFileType)) {
        throw new InvalidStoryFormatException(
            "The file does not have the correct " + storyFileType + " file type");
      }

      String nextLine = "nextLine";
      String line;

      while ((line = nextLine) != null) {

        nextLine = br.readLine();

        //Instantiates storyTitle
        if (storyTitle == null) {

          //Checks if file only contains a title
          if ((storyTitle = nextLine) == null) {
            throw new InvalidStoryFormatException("Story must consist of at least one passage");
          }

          nextLine = br.readLine();

          //Checks if the line after title is null
          if (nextLine == null) {
            throw new InvalidStoryFormatException("Story must consist of at least one passage");
          } else if (!(nextLine.isBlank())) { //Checks if line after title is empty
            throw new InvalidStoryFormatException("Line after story title must be blank");
          }

          continue;
        }

        //Check if it is the end of a block (Passage) or end of file, in which case
        //passage is added (if it is not null)
        if (((nextLine == null) || nextLine.isBlank() && !line.isBlank()) && passage != null) {
          if (story == null) {
            story = new Story(storyTitle, passage);
          } else {
            story.addPassage(passage);
          }
          passage = null;
          link = null;

          //Triggers if there are trailing empty lines at the end of the file
        } else if (nextLine == null) {
          throw new InvalidStoryFormatException("File cannot have trailing empty lines");
        } else if (isPassageTitle(nextLine) && line.isBlank()) { //Checks if the line is
          // a passageTitle AND that the previous line is blank
          passageTitle = nextLine;
          //Checks if the line is a passage content that is not blank AND
          // that the passage title is not null
        } else if (isPassageContent(line, nextLine) && passageTitle != null
            && !nextLine.isBlank()) {
          passage = readPassageFromFile(line, nextLine);

          //Checks if the line is a link and that passage is not null
        } else if (isLink(nextLine) && passage != null) {
          link = readLinkFromFile(nextLine);
          passage.addLink(link);

          //Goals are always before actions. Also checks that passage is not null
        } else if (isGoal(nextLine) && !isAction(line) && link != null) {
          Goal goal = readGoalFromFile(nextLine);
          link.addGoal(goal);

          //Checks if the line is an action and that link is not null
        } else if (isAction(nextLine) && link != null) {
          Action action = readActionFromFile(nextLine);
          link.addAction(action);

          //If else block is reached, the file contains invalid formatting
        } else {
          throw new InvalidStoryFormatException(
              "File does not match " + storyFileType + " file format");
        }
      }
    } catch (IOException ioe) {
      throw new IOException("Could not find a file in path: " + filePath, ioe);
    } catch (NumberFormatException nfe) {
      throw new InvalidStoryFormatException(
          "The string value of value cannot be converted to a valid integer", nfe);
    } catch (IllegalArgumentException iae) {
      throw new InvalidStoryFormatException(iae.getMessage(), iae);
    } catch (NullPointerException nfe) {
      throw new IOException("File path cannot be null", nfe);
    }
    return story;
  }
}
