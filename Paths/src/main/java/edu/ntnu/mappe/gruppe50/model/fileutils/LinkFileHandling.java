package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidLinkFormatException;
import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.LinkRegister;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File handling class for reading and writing visited links in a game, from or to a .links file.
 * Links are a story element, thus the class extends the BaseStoryFileHandling class.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class LinkFileHandling extends BaseStoryFileHandling {

  private static final String linkFileType = ".links";

  /**
   * Writes a LinkRegister to a file, both of which are specified by the method parameters.
   *
   * @param links    The target LinkRegister that is written to file.
   * @param filePath The absolute file path of the file.
   * @throws IOException                If links cannot be written to file.
   * @throws InvalidLinkFormatException If the file type is not .link or if the links or file path
   *                                    are null.
   */
  public static void writeLinksToFile(LinkRegister links, String filePath)
      throws IOException, InvalidLinkFormatException {
    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fileWriter)) {

      if (!filePath.endsWith(linkFileType)) {
        throw new InvalidLinkFormatException("The file does not have the correct .links file type");
      }

      bw.write(links.toString());
    } catch (IOException ex) {
      throw new IOException("Error writing links to file: " + ex.getMessage(), ex);
    } catch (NullPointerException nfe) {
      throw new IOException("The links and file path cannot be null", nfe);
    }
  }

  /**
   * Reads a LinkRegister from a file, specified by the method parameter.
   *
   * @param filePath The absolute file path of the file.
   * @return A LinkRegister that is contained in the file.
   * @throws IOException                If the file does not exist or if file path is null.
   * @throws InvalidLinkFormatException If the file type is not .links, or if the formatting of the
   *                                    file is invalid.
   */
  public static LinkRegister readLinksFromFile(String filePath)
      throws IOException, InvalidLinkFormatException {
    LinkRegister links = new LinkRegister();

    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      if (!filePath.endsWith(linkFileType)) {
        throw new InvalidLinkFormatException("The file does not have the correct .links file type");
      }

      Link link = null;

      String line;
      String nextLine = "nextLine";

      while ((line = nextLine) != null) {
        nextLine = br.readLine();

        //Adds a link to the register at an empty line or at the end of the file
        if (((nextLine == null) || nextLine.isBlank() && !line.isBlank()) && link != null) {
          links.addLink(link);
          link = null;

          //This branch prevents null pointer exceptions in the next else if
        } else if (nextLine == null) {
          continue;

        } else if (isLink(nextLine)) {
          link = readLinkFromFile(nextLine);

          //Checks if the line is a goal. Goals are written before actions
        } else if (isGoal(nextLine) && !isAction(line) && link != null) {
          Goal goal = readGoalFromFile(nextLine);
          link.addGoal(goal);

        } else if (isAction(nextLine) && link != null) {
          Action action = readActionFromFile(nextLine);
          link.addAction(action);
        } else {
          throw new InvalidLinkFormatException("File does not match .links file format");
        }
      }
    } catch (IOException ioe) {
      throw new IOException("Could not find a file in path: " + filePath, ioe);
    } catch (NumberFormatException sioob) {
      throw new InvalidLinkFormatException("The formatting of an action or link "
          + "is invalid", sioob);
    } catch (IllegalArgumentException iae) {
      throw new InvalidLinkFormatException(iae.getMessage(), iae);
    } catch (NullPointerException nfe) {
      throw new IOException("File path cannot be null", nfe);
    }
    return links;
  }
}
