package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidGoalFormatException;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalsRegister;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File handling class used to for reading and writing a goal register to file. Goals are a story
 * element, thus the class extends the BaseStoryFileHandling class.
 *
 * @author Harry Linrui Xu
 * @since 8.4.2023
 */
public class GoalFileHandling extends BaseStoryFileHandling {

  private static final String goalFileType = ".goals";

  /**
   * Writes a goal register to a specified file. The method uses the toString of the goals
   * register.
   *
   * @param goals    The target goal register which will be written to file.
   * @param filePath The absolute path of the file.
   * @throws IOException                If the specified file cannot be written to or if goals or
   *                                    filePath are null.
   * @throws InvalidGoalFormatException If the file type is not .goals.
   */
  public static void writeGoalsToFile(GoalsRegister goals, String filePath)
      throws IOException, InvalidGoalFormatException {
    if (filePath == null) {
      throw new IOException("FilePath cannot be null");
    }
    if (goals == null) {
      throw new IllegalArgumentException("Goals cannot be null");
    }
    if (!filePath.endsWith(goalFileType)) {
      throw new InvalidGoalFormatException("The file does not have the correct .goals file type");
    }

    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fileWriter)) {

      bw.write(goals.toString());
    } catch (IOException ex) {
      throw new IOException("Error writing goals to file: " + ex.getMessage(), ex);
    }
  }


  /**
   * Reads a goal register from a specified file. The method is immediately stopped if the file is
   * invalid, meaning that it does not abide by the goal register file format. &lt;type:value&lt;,
   * in which value is an integer or an arraylist.
   *
   * @param filePath The absolute path of the file.
   * @return A goal register consisting of the goals in the file.
   * @throws IOException                If the file does not exist or if the file path is null
   * @throws InvalidGoalFormatException If the file type is not .goals or if the goal formatting is
   *                                    invalid.
   */
  public static GoalsRegister readGoalsFromFile(String filePath)
      throws IOException, InvalidGoalFormatException {

    GoalsRegister goalsRegister = new GoalsRegister();
    Goal goal;

    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      if (!filePath.endsWith(goalFileType)) {
        throw new InvalidGoalFormatException("The file does not have the correct .goals file type");
      }

      String nextLine = "";
      while (nextLine != null) {
        nextLine = br.readLine();

        //this if branch prevents a nullpointer exception from occurring in the next else-if
        if (nextLine == null) {
          continue;
        } else if (isGoal(nextLine)) {
          goal = readGoalFromFile(nextLine);
          goalsRegister.addGoal(goal);
        } else {
          throw new InvalidGoalFormatException("Goals file does not match .goals file format");
        }
      }

    } catch (IOException ioe) {
      throw new IOException("Could not find a file in path: " + filePath, ioe);
    } catch (StringIndexOutOfBoundsException sioob) {
      throw new InvalidGoalFormatException("The formatting of a goal is invalid", sioob);
    } catch (NumberFormatException nfe) {
      throw new InvalidGoalFormatException(
          "The string value of value cannot be converted to a valid integer", nfe);
    } catch (IllegalArgumentException iae) {
      throw new InvalidGoalFormatException(iae.getMessage(), iae);
    } catch (NullPointerException nfe) {
      throw new IOException("The file path cannot be null", nfe);
    }
    return goalsRegister;
  }

  /**
   * Deletes a .goals file.
   *
   * @param filePath The absolute file path of the file.
   * @return True, if the file is deleted.
   * @throws IOException If the file path is null.
   */
  public static boolean deleteGoalsFile(String filePath) throws IOException {
    try {
      File file = new File(filePath);
      if (file.exists()) {
        return file.delete();
      }
      return false;
    } catch (NullPointerException nfe) {
      throw new IOException("File path cannot be null", nfe);
    }
  }
}

