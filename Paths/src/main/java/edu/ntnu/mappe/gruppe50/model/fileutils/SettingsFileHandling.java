package edu.ntnu.mappe.gruppe50.model.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File handling class for reading and writing the audio levels for music and sound in the game
 * settings, from and to a file.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 28.03.2023
 */
public class SettingsFileHandling {

  /**
   * Writes the music and sound volume levels to file.
   *
   * @param musicVolume The decimal value (between 0 and 1) of the music volume to file.
   * @param soundVolume The decimal value (between 0 and 1) of the sound volume to file.
   * @param filePath    The absolute file path of the file.
   * @throws IOException If the file does not exist.
   */
  public static void writeVolumeToFile(double musicVolume, double soundVolume, String filePath)
      throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    if (musicVolume < 0 || musicVolume > 100) {
      throw new IllegalArgumentException("MusicVolume can only be between 0 and 100");
    }
    if (soundVolume < 0 || soundVolume > 100) {
      throw new IllegalArgumentException("SoundVolume can only be between 0 and 100");
    }
    String volume = "Music Volume: " + musicVolume + "\nSound Volume: " + soundVolume;

    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fileWriter)) {
      bw.write(volume);
    } catch (IOException ex) {
      throw new IOException("Error writing story to file: " + ex.getMessage());
    }
  }

  /**
   * Reads the music volume from file and returns the extracted value.
   *
   * @param filePath The absolute file path of the file.
   * @return A double between 0 and 1.
   * @throws IOException If the file does not exist or if the formatting is invalid.
   */
  public static double readMusicVolumeFromFile(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      String[] lineSplit = br.readLine().split(" ");
      return Double.parseDouble(lineSplit[2]);

    } catch (IOException ioException) {
      throw new IOException("No file with that name found.");
    } catch (IndexOutOfBoundsException e) {
      throw new IOException(
          "There is no second index in the file to retrieve the music volume from", e);
    }
  }

  /**
   * Reads the sound volume from file and returns the extracted value.
   *
   * @param filePath The absolute file path of the file.
   * @return A double between 0 and 1.
   * @throws IOException If the file does not exist, of if the formatting is invalid.
   */
  public static double readSoundVolumeFromFile(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("FilePath cannot be null.");
    }
    if (filePath.isBlank()) {
      throw new IllegalArgumentException("FilePath cannot be blank");
    }
    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      br.readLine();
      String[] lineSplit = br.readLine().split(" ");
      return Double.parseDouble(lineSplit[2]);

    } catch (IOException ioException) {
      throw new IOException("No file with that name found.");
    } catch (IndexOutOfBoundsException e) {
      throw new IOException(
          "There is no second index in the file to retrieve the sound volume from", e);
    }

  }
}
