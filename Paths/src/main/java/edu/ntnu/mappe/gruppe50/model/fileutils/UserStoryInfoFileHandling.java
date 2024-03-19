package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfo;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfoRegister;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File handling class for reading and writing user stories from and to file. A user story contains
 * information of the story's file name, path and number of broken links.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 10.05.2023
 */
public class UserStoryInfoFileHandling {

  private static final String userStoryInfoFileType = ".userStoryInfo";

  /**
   * Reads a UserStoryInfoRegister from file.
   *
   * @param fileToReadFromPath The absolute file path of the file.
   * @return An instance of UserStoryInfoRegister.
   * @throws IOException              If the file type is not .userStoryInfo.
   * @throws IllegalArgumentException If the file path is null or blank.
   */
  public static UserStoryInfoRegister readUserStoryInfoFromFile(String fileToReadFromPath)
      throws IOException {
    if (fileToReadFromPath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (fileToReadFromPath.isBlank()) {
      throw new IllegalArgumentException("filePath cannot be blank.");
    }

    if (!fileToReadFromPath.endsWith(userStoryInfoFileType)) {
      throw new IOException(
          "The file does not have the correct " + userStoryInfoFileType + " file type");
    }

    UserStoryInfoRegister userStoryInfoRegister = new UserStoryInfoRegister();

    try (FileReader fileReader = new FileReader(fileToReadFromPath);
        BufferedReader br = new BufferedReader(fileReader)) {

      String line;
      String nextLine = br.readLine();

      while ((line = nextLine) != null) {
        nextLine = br.readLine();
        if (!line.isBlank()) {
          String[] userStoryInfoArray = line.split(", ");
          UserStoryInfo userStoryInfo = new UserStoryInfo(userStoryInfoArray[0],
              userStoryInfoArray[1], Integer.parseInt(userStoryInfoArray[2]));
          userStoryInfoRegister.addUserStoryInfo(userStoryInfo);
        }
      }

    } catch (IOException ioException) {
      throw new IOException("Cannot read from " + fileToReadFromPath);
    }
    return userStoryInfoRegister;
  }

  /**
   * Writes a UserStoryInfoRegister object to file. Loops through user stories and writes each one
   * to file.
   *
   * @param userStoryInfoRegister The UserStoryInfoRegister instance that is written to file.
   * @param filePath              The absolute file path of the file.
   * @throws IOException If the file type is not .userStoryInfo.
   */
  public static void writeUserStoryInfoRegisterToFile(UserStoryInfoRegister userStoryInfoRegister,
      String filePath) throws IOException {
    if (userStoryInfoRegister == null) {
      throw new IllegalArgumentException("userStoryInfoRegister cannot be null.");
    }
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank.");
    }
    if (!filePath.endsWith(userStoryInfoFileType)) {
      throw new IOException(
          "The file does not have the correct " + userStoryInfoFileType + " file type");
    }

    for (UserStoryInfo userStoryInfo : userStoryInfoRegister.getUserStoryInfos()) {
      writeUserStoryInfoToFile(userStoryInfo, filePath);
    }
  }

  /**
   * Writes an instance of UserStoryInfo to file.
   *
   * @param userStoryInfo The UserStoryInfo object written to file.
   * @param filePath      The absolute file path of the file.
   * @throws IOException If the file type is not .userStoryInfo, if the formatting is invalid or if
   *                     the files created in the method cannot be deleted or renamed.
   */
  public static void writeUserStoryInfoToFile(UserStoryInfo userStoryInfo, String filePath)
      throws IOException {
    if (userStoryInfo == null) {
      throw new IllegalArgumentException("UserStoryInfo cannot be null.");
    }
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank.");
    }
    if (!filePath.endsWith(userStoryInfoFileType)) {
      throw new IOException(
          "The file does not have the correct " + userStoryInfoFileType + " file type");
    }

    File file = new File(filePath);
    boolean userStoryInfoExists = readUserStoryInfoFromFile(filePath).getUserStoryInfoPaths()
        .contains(userStoryInfo.getFilePath());

    // Write the userStoryData's information to the file
    if (userStoryInfoExists) {
      // If the userStoryData exists, modify the existing line in the file
      String tempFilePath = System.getProperty("user.dir")
          + "/src/main/resources/userStoryInfoFiles/temp.userStoryInfo";
      File tempFile = new File(tempFilePath);
      FileWriter fw = new FileWriter(tempFile);
      try (BufferedWriter bw = new BufferedWriter(fw);
          BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
          String[] userStoryInfoData = line.split(", ");
          if (userStoryInfoData[1].equals(userStoryInfo.getFilePath())) {
            bw.write(userStoryInfo.getFileName() + ", " + userStoryInfo.getFilePath() + ", "
                + userStoryInfo.getNumBrokenLinks());
          } else {
            bw.write(line);
          }
          bw.newLine();
        }
      }

      // Replace the original file with the modified file
      if (!file.delete()) {
        throw new IOException("Error deleting file");
      }
      if (!tempFile.renameTo(file)) {
        throw new IOException("Error renaming file");
      }

    } else {
      // If the userStoryData does not exist, write a new line to the file
      try (FileWriter fw = new FileWriter(file, true);
          BufferedWriter bw = new BufferedWriter(fw)) {
        bw.write(userStoryInfo.getFileName() + ", " + userStoryInfo.getFilePath() + ", "
            + userStoryInfo.getNumBrokenLinks());
        bw.newLine();
      }
    }
  }

  /**
   * Removes an instance of UserStoryInfoFrom from a .userStoryInfo file. Uses the path of the user
   * story as a reference to delete the entry.
   *
   * @param filePathToRemove The file path of the user story that is being deleted.
   * @param filePath         The file path of the target .userStoryInfo file
   * @throws IOException If the formatting of the .userStoryInfo is invalid, if its file type is not
   *                     .userStoryInfo or if the files created in the method cannot be renamed or
   *                     deleted.
   */
  public static void removeUserStoryInfoFromFile(String filePathToRemove, String filePath)
      throws IOException {
    if (filePathToRemove == null) {
      throw new IllegalArgumentException("FilePathToRemove cannot be null.");
    }
    if (filePathToRemove.isBlank()) {
      throw new IllegalArgumentException("FilePathToRemove cannot be blank.");
    }
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank.");
    }
    if (!filePath.endsWith(userStoryInfoFileType)) {
      throw new IOException(
          "The file does not have the correct " + userStoryInfoFileType + " file type");
    }

    File file = new File(filePath);
    String tempFilePath = System.getProperty("user.dir")
        + "/src/main/resources/userStoryInfoFiles/temp.userStoryInfo";
    File tempFile = new File(tempFilePath);

    try (BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] userStoryInfoData = line.split(", ");
        if (!userStoryInfoData[1].equals(filePathToRemove)) {
          bw.write(line);
          bw.newLine();
        }
      }
    } catch (IndexOutOfBoundsException ex) {
      throw new IOException(
          "Invalid UserStoryInfo format. The UserStoryInfo needs a file name,"
              + " a file path and the number of broken links",
          ex);
    }

    // Replace the original file with the modified file
    if (!file.delete()) {
      throw new IOException("Error deleting file");
    }
    if (!tempFile.renameTo(file)) {
      throw new IOException("Error renaming file");
    }
  }
}
