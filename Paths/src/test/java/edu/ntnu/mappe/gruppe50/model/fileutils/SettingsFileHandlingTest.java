package edu.ntnu.mappe.gruppe50.model.fileutils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SettingsFileHandlingTest {

  @Nested
  class TestReadMusicVolumeFromFile {

    @Test
    void testReadMusicVolumeFromFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/settings/volume.txt";
      assertEquals(50, SettingsFileHandling.readMusicVolumeFromFile(filePath));
    }

    @Test
    void testReadMusicVolumeFromFileWhenFileIsNull() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.readMusicVolumeFromFile(null));
    }

    @Test
    void testReadMusicVolumeFromFileWhenFileIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.readMusicVolumeFromFile(""));
    }
  }

  @Nested
  class TestReadSoundVolumeFromFile {

    @Test
    void testReadSoundVolumeFromFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/settings/volume.txt";
      assertEquals(25, SettingsFileHandling.readSoundVolumeFromFile(filePath));
    }

    @Test
    void testReadSoundVolumeFromFileWhenFileIsNull() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.readSoundVolumeFromFile(null));
    }

    @Test
    void testReadSoundVolumeFromFileWhenFileIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.readSoundVolumeFromFile(""));
    }
  }

  @Nested
  class TestWriteVolumeToFile {
    String exceptionFilePath = System.getProperty("user.dir") + "/src/test/resources/settings/exception_volume.txt";
    @Test
    void testWriteVolumeToFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/settings/write_to_file.txt";
      SettingsFileHandling.writeVolumeToFile(30, 60, filePath);
      assertEquals(30, SettingsFileHandling.readMusicVolumeFromFile(filePath));
      assertEquals(60, SettingsFileHandling.readSoundVolumeFromFile(filePath));
    }

    @Test
    void testWriteVolumeToFileWhenFileIsNull() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.writeVolumeToFile(50, 50, null));
    }

    @Test
    void testWriteVolumeToFileWhenFileIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.writeVolumeToFile(50, 50, ""));
    }

    @Test
    void testWriteVolumeToFileWhenMusicVolumeLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.writeVolumeToFile(-50, 50, exceptionFilePath));
    }

    @Test
    void testWriteVolumeToFileWhenMusicVolumeIsMoreThanAHundred() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.writeVolumeToFile(150, 50, exceptionFilePath));
    }

    @Test
    void testWriteVolumeToFileWhenSoundVolumeLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.writeVolumeToFile(50, -50, exceptionFilePath));
    }

    @Test
    void testWriteVolumeToFileWhenSoundVolumeIsMoreThanAHundred() {
      assertThrows(IllegalArgumentException.class, () -> SettingsFileHandling.writeVolumeToFile(50, 150, exceptionFilePath));
    }
  }
}
