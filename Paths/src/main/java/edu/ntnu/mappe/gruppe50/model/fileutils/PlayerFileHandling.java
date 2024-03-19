package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * File handling class for reading and writing Player objects from and to a .players file. The
 * .players file contain information about players.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 02.05.2023
 */
public class PlayerFileHandling {

  private static final String playerFileType = ".players";

  /**
   * Reads a player, based on a reference player name from a file. Both the player name and the file
   * are specified by the method parameters.
   *
   * @param playerName The name of the player that is used to reference the saved player object.
   * @param filePath   The absolute file path of the file.
   * @return An instance of the Player class whose name is the input player name.
   * @throws IOException              If the file type is not .players, if the file does not exist
   *                                  or if the formatting of the file is invalid.
   * @throws IllegalArgumentException If the player name is null or blank.
   */
  public static Player readPlayerFromFile(String playerName, String filePath)
      throws IOException, IllegalArgumentException {
    if (playerName == null) {
      throw new IllegalArgumentException("playerName cannot be null.");
    }
    if (playerName.isBlank()) {
      throw new IllegalArgumentException("playerName cannot be blank.");
    }

    if (!filePath.endsWith(playerFileType)) {
      throw new IOException("The file does not have the correct " + playerFileType + "  file type");
    }

    Player player = null;

    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      String line;
      String nextLine = br.readLine();

      while ((line = nextLine) != null) {
        if (line.isBlank()) {
          nextLine = br.readLine();
          continue;
        }
        nextLine = br.readLine();

        String[] playerArray = line.split(", ");

        if (playerArray[0].equals(playerName)) {
          player = new Player.Builder(playerName).health(Integer.parseInt(playerArray[1]))
              .gold(Integer.parseInt(playerArray[2])).build();
        }
      }
    } catch (IOException ioException) {
      throw new IOException("Cannot read from " + filePath);
    } catch (IndexOutOfBoundsException ex) {
      throw new IOException("The players needs a name, health and gold", ex);
    }

    return player;
  }

  /**
   * Writes an instance of the Player class with a custom avatar image to a target file.
   *
   * @param player              The Player object that is written to file.
   * @param avatarImageFilePath The file path of the avatar image of the player.
   * @param filePath            The absolute file path of the target file.
   * @throws IOException If the file type is not .players, if the formatting is invalid or if the
   *                     files created in the method cannot be deleted or renamed.
   */
  public static void writePlayerToFile(Player player, String avatarImageFilePath, String filePath)
      throws IOException {

    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (avatarImageFilePath == null) {
      throw new IllegalArgumentException("AvatarImageFilePath cannot be blank.");
    }
    if (filePath == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }

    File file = new File(filePath);
    boolean playerExists = false;

    if (!filePath.endsWith(playerFileType)) {
      throw new IOException("The file does not have the correct " + playerFileType + "  file type");
    }

    // Read the contents of the file and check if the player already exists
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] playerData = line.split(",");
        if (playerData[0].trim().equals(player.getName())) {
          playerExists = true;
          break;
        }
      }
    }

    // Write the player's information to the file
    if (playerExists) {
      // If the player exists, modify the existing line in the file
      String tempFilePath =
          System.getProperty("user.dir") + "/src/test/resources/playerFiles/temp.players";
      File tempFile = new File(tempFilePath);
      FileWriter fw = new FileWriter(tempFile);
      try (BufferedWriter bw = new BufferedWriter(fw);
          BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
          String[] playerData = line.split(",");
          if (playerData[0].trim().equals(player.getName())) {
            bw.write(player.getName() + ", " + player.getHealth() + ", " + player.getGold() + ", "
                + avatarImageFilePath);
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
      // If the player does not exist, write a new line to the file
      try (FileWriter fw = new FileWriter(file, true);
          BufferedWriter bw = new BufferedWriter(fw)) {
        bw.newLine();
        bw.write(player.getName() + ", " + player.getHealth() + ", " + player.getGold() + ", "
            + avatarImageFilePath);
      }
    }
  }

  /**
   * Method for reading all players from a file, specified by the method parameter.
   *
   * @param filePath The absolute file path of the file.
   * @return A list of Player instances.
   * @throws IOException If the file type is not .players, if the file does not exist of if the
   *                     formatting in the file is invalid.
   */
  public static List<Player> readAllPlayersFromFile(String filePath) throws IOException {
    List<Player> players = new ArrayList<>();

    if (filePath == null) {
      throw new IllegalArgumentException("File path cannot be null");
    }
    if (!filePath.endsWith(playerFileType)) {
      throw new IOException("The file does not have the correct " + playerFileType + " file type");
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.isBlank()) {
          continue;
        }
        String[] playerArray = line.split(",");
        String name = playerArray[0].trim();
        int health = Integer.parseInt(playerArray[1].trim());
        int gold = Integer.parseInt(playerArray[2].trim());

        Player player = new Player.Builder(name).health(health).gold(gold).build();
        players.add(player);
      }
    } catch (IOException e) {
      throw new IOException("Error reading player data from file: " + e.getMessage(), e);
    } catch (IllegalArgumentException iae) {
      throw new IOException("Invalid data. Some players in the player file could not be created",
          iae);
    } catch (IndexOutOfBoundsException ex) {
      throw new IOException(
          "Invalid formatting on file. A player needs a name, health, gold and an image URL", ex);
    }

    return players;
  }

  /**
   * Removes a player from a .players file.
   *
   * @param nameOfPlayerToRemove The name of the player that references the player to be removed.
   * @param filePath             The absolute file path of the .players file.
   * @return True, if the player was removed.
   * @throws IOException If the file type is not .players, if the formatting is invalid or if the
   *                     files created in the method cannot be removed or renamed.
   */
  public static boolean removePlayerFromFile(String nameOfPlayerToRemove, String filePath)
      throws IOException {
    boolean removePlayer = false;

    if (nameOfPlayerToRemove == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (filePath == null) {
      throw new IllegalArgumentException("AvatarImageFilePath cannot be blank.");
    }

    if (!filePath.endsWith(playerFileType)) {
      throw new IOException("The file does not have the correct " + playerFileType + "  file type");
    }

    File file = new File(filePath);
    String tempFilePath = System.getProperty("user.dir") + "/src/main/resources/temp.players";
    File tempFile = new File(tempFilePath);

    try (BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] playerData = line.split(", ");
        if (!playerData[0].equals(nameOfPlayerToRemove)) {
          bw.write(line);
          bw.newLine();
        } else {
          removePlayer = true;
        }
      }
    } catch (IndexOutOfBoundsException ex) {
      throw new IOException("Player entry cannot be empty", ex);
    }

    // Replace the original file with the modified file
    if (!file.delete()) {
      throw new IOException("Error deleting file");
    }
    if (!tempFile.renameTo(file)) {
      throw new IOException("Error renaming file");
    }
    return removePlayer;
  }

  /**
   * Reads the file path of a player avatar image from a target file and returns it to the caller.
   *
   * @param playerName The name of the player whose avatar image is retrieved.
   * @param filePath   The absolute file path of the target file.
   * @return A string that contains the file path of the player avatar image.
   * @throws IOException              If the file type is not .players or if the formatting is
   *                                  invalid.
   * @throws IllegalArgumentException If the player name is null or blank.
   */
  public static String readPlayerAvatarImagePath(String playerName, String filePath)
      throws IOException {
    if (playerName == null) {
      throw new IllegalArgumentException("playerName cannot be null.");
    }
    if (playerName.isBlank()) {
      throw new IllegalArgumentException("playerName cannot be blank.");
    }

    String avatarImageFilePath = null;

    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      String line;
      String nextLine = br.readLine();

      while ((line = nextLine) != null) {
        if (line.isBlank()) {
          nextLine = br.readLine();
          continue;
        }
        nextLine = br.readLine();

        String[] playerArray = line.split(", ");

        if (playerArray[0].equals(playerName)) {
          avatarImageFilePath = playerArray[3];
        }
      }

    } catch (IOException ioException) {
      throw new IOException("Cannot read from " + filePath);
    } catch (IndexOutOfBoundsException ex) {
      throw new IOException("The player must have a name, health, gold and a image URL", ex);
    }

    return avatarImageFilePath;
  }
}