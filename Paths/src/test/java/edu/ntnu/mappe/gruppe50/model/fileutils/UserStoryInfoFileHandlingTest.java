package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfo;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfoRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoryInfoFileHandlingTest {

  @Nested
  class ReadUserStoryInfoFromFile {

    @Test
    @DisplayName("Test readUserStoryInfoFromFile")
    public void testReadUserStoryInfoFromFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/basic_userStoryInfo.userStoryInfo";

      // Test reading the file
      UserStoryInfoRegister register = UserStoryInfoFileHandling.readUserStoryInfoFromFile(filePath);
      UserStoryInfo userStoryInfo1 = register.getUserStoryInfos().get(0);
      UserStoryInfo userStoryInfo2 = register.getUserStoryInfos().get(1);

      // Verify the read userStoryInfo
      assertEquals("test1.paths", userStoryInfo1.getFileName());
      assertEquals("/test/path/test1.paths", userStoryInfo1.getFilePath());
      assertEquals(1, userStoryInfo1.getNumBrokenLinks());

      assertEquals("test2.paths", userStoryInfo2.getFileName());
      assertEquals("/test/path/test2.paths", userStoryInfo2.getFilePath());
      assertEquals(2, userStoryInfo2.getNumBrokenLinks());

      assertEquals(2, register.getUserStoryInfos().size());
    }

    @Test
    @DisplayName("Test readUserStoryInfoFromFIle when file to read from is null.")
    void testReadUserStoryInfoFromFileWhenFileToReadFromIsNull() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.readUserStoryInfoFromFile(null));
    }

    @Test
    @DisplayName("Test readUserStoryInfoFromFile when file to read from is blank.")
    void testReadUserStoryInfoFromFileWhenFileToReadFromIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.readUserStoryInfoFromFile(""));
    }

    @Test
    @DisplayName("Test readUserStoryInfoFromFile when file is empty.")
    void testReadUserStoryInfoFromFileWhenFileIsEmpty() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/empty_file.userStoryInfo";
      assertEquals(0, UserStoryInfoFileHandling.readUserStoryInfoFromFile(filePath).getUserStoryInfos().size());
    }

    @Test
    @DisplayName("Test readUserStoryInfoFromFile with empty lines between UserStoryInfos.")
    void testReadUserStoryInfoFromFileWithEmptyLinesBetweenUserStoryInfos() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/empty_lines_between.userStoryInfo";

      // Test reading the file
      UserStoryInfoRegister register = UserStoryInfoFileHandling.readUserStoryInfoFromFile(filePath);
      UserStoryInfo userStoryInfo1 = register.getUserStoryInfos().get(0);
      UserStoryInfo userStoryInfo2 = register.getUserStoryInfos().get(1);

      // Verify the read userStoryInfo
      assertEquals("test1.paths", userStoryInfo1.getFileName());
      assertEquals("/test/path/test1.paths", userStoryInfo1.getFilePath());
      assertEquals(1, userStoryInfo1.getNumBrokenLinks());

      assertEquals("test2.paths", userStoryInfo2.getFileName());
      assertEquals("/test/path/test2.paths", userStoryInfo2.getFilePath());
      assertEquals(2, userStoryInfo2.getNumBrokenLinks());

      assertEquals(2, register.getUserStoryInfos().size());
    }
  }


  @Nested
  @DisplayName("Test writeUserStoryInfoToFile")
  class TestWriteUserStoryInfoToFile {
    UserStoryInfo userStoryInfo1, userStoryInfo2;

    @BeforeEach
    void setUp() {
      userStoryInfo1 = new UserStoryInfo("test1.paths", "/test/path/test1.paths", 1);
      userStoryInfo2 = new UserStoryInfo("test2.paths", "/test/path/test2.paths", 2);
    }

    @Test
    @DisplayName("Test writeUserStoryInfoToFile")
    void testWriteUserStoryInfoToFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/write_userStoryInfo_basic.userStoryInfo";
      UserStoryInfoFileHandling.writeUserStoryInfoToFile(userStoryInfo1, filePath);
      UserStoryInfoFileHandling.writeUserStoryInfoToFile(userStoryInfo2, filePath);

      UserStoryInfoRegister register = UserStoryInfoFileHandling.readUserStoryInfoFromFile(filePath);
      UserStoryInfo userStoryInfoFromFile1 = register.getUserStoryInfos().get(0);
      UserStoryInfo userStoryInfoFromFile2 = register.getUserStoryInfos().get(1);

      assertEquals("test1.paths", userStoryInfoFromFile1.getFileName());
      assertEquals("/test/path/test1.paths", userStoryInfoFromFile1.getFilePath());
      assertEquals(1, userStoryInfoFromFile1.getNumBrokenLinks());

      assertEquals("test2.paths", userStoryInfoFromFile2.getFileName());
      assertEquals("/test/path/test2.paths", userStoryInfoFromFile2.getFilePath());
      assertEquals(2, userStoryInfoFromFile2.getNumBrokenLinks());

      assertEquals(2, register.getUserStoryInfos().size());
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFile when userStoryInfo is null.")
    void testWriteUserStoryInfoToFileWhenUserStoryInfoIsNull() {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/write_exception.userStoryInfo";
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.writeUserStoryInfoToFile(null, filePath));
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFile when filePath is null.")
    void testWriteUserStoryInfoToFileWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.writeUserStoryInfoToFile(userStoryInfo1, null));
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFile when filePath is blank.")
    void testWriteUserStoryInfoToFileWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.writeUserStoryInfoToFile(userStoryInfo1, ""));
    }
  }

  @Nested
  @DisplayName("Test WriteUserStoryInfoToFileRegisterToFile.")
  class TestWriteUserStoryInfoRegisterToFile {
    UserStoryInfoRegister userStoryInfoRegister;
    UserStoryInfo userStoryInfo1, userStoryInfo2;

    @BeforeEach
    void setUp() {
      userStoryInfoRegister = new UserStoryInfoRegister();
      userStoryInfo1 = new UserStoryInfo("test1.paths", "/test/path/test1.paths", 1);
      userStoryInfo2 = new UserStoryInfo("test2.paths", "/test/path/test2.paths", 2);
      userStoryInfoRegister.addUserStoryInfo(userStoryInfo1);
      userStoryInfoRegister.addUserStoryInfo(userStoryInfo2);
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFileRegisterToFile.")
    void testWriteUserStoryInfoRegisterToFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/write_userStoryInfoRegister_basic.userStoryInfo";
      UserStoryInfoFileHandling.writeUserStoryInfoRegisterToFile(userStoryInfoRegister, filePath);

      UserStoryInfoRegister userStoryInfoRegisterFromFile = UserStoryInfoFileHandling.readUserStoryInfoFromFile(filePath);

      UserStoryInfo userStoryInfoFromFile1 = userStoryInfoRegisterFromFile.getUserStoryInfos().get(0);
      UserStoryInfo userStoryInfoFromFile2 = userStoryInfoRegisterFromFile.getUserStoryInfos().get(1);

      assertEquals("test1.paths", userStoryInfoFromFile1.getFileName());
      assertEquals("/test/path/test1.paths", userStoryInfoFromFile1.getFilePath());
      assertEquals(1, userStoryInfoFromFile1.getNumBrokenLinks());

      assertEquals("test2.paths", userStoryInfoFromFile2.getFileName());
      assertEquals("/test/path/test2.paths", userStoryInfoFromFile2.getFilePath());
      assertEquals(2, userStoryInfoFromFile2.getNumBrokenLinks());

      assertEquals(2, userStoryInfoRegisterFromFile.getUserStoryInfos().size());
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFileRegisterToFile when UserStoryInfo is null.")
    void testWriteUserStoryRegisterInfoToFileWhenUserStoryInfoIsNull() {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/write_register_exception.userStoryInfo";
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.writeUserStoryInfoRegisterToFile(null, filePath));
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFileRegisterToFile when filePath is null.")
    void testWriteUserStoryInfoRegisterToFileWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.writeUserStoryInfoRegisterToFile(userStoryInfoRegister, null));
    }

    @Test
    @DisplayName("Test WriteUserStoryInfoToFileRegisterToFile when filePath is blank.")
    void testWriteUserStoryInfoRegisterToFileWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.writeUserStoryInfoRegisterToFile(userStoryInfoRegister, ""));
    }
  }

  @Nested
  @DisplayName("Test removeUserStoryInfoFromFile")
  class TestRemoveUserStoryInfoFromFile {
    UserStoryInfo userStoryInfo1, userStoryInfo2;
    String filePathBasic = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/remove_userStoryInfo.userStoryInfo";
    String filePathException = System.getProperty("user.dir") + "/src/test/resources/userStoryInfoFiles/remove_exception.userStoryInfo";

    @BeforeEach
    void setUp() throws IOException {
      userStoryInfo1 = new UserStoryInfo("test1.paths", "/test/path/test1.paths", 1);
      userStoryInfo2 = new UserStoryInfo("test2.paths", "/test/path/test2.paths", 2);
      UserStoryInfoFileHandling.writeUserStoryInfoToFile(userStoryInfo1, filePathBasic);
      UserStoryInfoFileHandling.writeUserStoryInfoToFile(userStoryInfo2, filePathBasic);
    }

    @Test
    @DisplayName("Test removeUserStoryInfoFromFile")
    void testRemoveUserStoryInfoFromFile() throws IOException {
      UserStoryInfoFileHandling.removeUserStoryInfoFromFile("/test/path/test1.paths", filePathBasic);
      UserStoryInfoRegister userStoryInfoRegister = UserStoryInfoFileHandling.readUserStoryInfoFromFile(filePathBasic);
      assertEquals(1, userStoryInfoRegister.getUserStoryInfos().size());
    }

    @Test
    @DisplayName("Test removeUserStoryInfoFromFile when filePathToRemove is null.")
    void testRemoveUserStoryInfoWhenFilePathToRemoveIsNull() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.removeUserStoryInfoFromFile(null, filePathException));
    }

    @Test
    @DisplayName("Test removeUserStoryInfoFromFile when filePathToRemove is blank.")
    void testRemoveUserStoryInfoWhenFilePathToRemoveIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.removeUserStoryInfoFromFile("", filePathException));
    }

    @Test
    @DisplayName("Test removeUserStoryInfoFromFile when filePath is null.")
    void testRemoveUserStoryInfoWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.removeUserStoryInfoFromFile("test", null));
    }

    @Test
    @DisplayName("Test removeUserStoryInfoFromFile when filePath is blank.")
    void testRemoveUserStoryInfoWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> UserStoryInfoFileHandling.removeUserStoryInfoFromFile("test", ""));
    }
  }
}

