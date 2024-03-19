package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidStoryFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoryInfoTest {
  private UserStoryInfo userStoryInfo;
  private String fileName;
  private String path;
  private int numBrokenLinks;

  @Nested
  @DisplayName("Test UserStoryInfo constructor")
  class UserStoryInfoConstructor {

    @Test
    @DisplayName("UserStoryInfo constructor with valid arguments")
    void userStoryInfoConstructorWithValidArguments() {
      UserStoryInfo userStoryInfo = new UserStoryInfo("testFileName", "testFilePath", 10);
      assertEquals("testFileName", userStoryInfo.getFileName());
      assertEquals("testFilePath", userStoryInfo.getFilePath());
      assertEquals(10, userStoryInfo.getNumBrokenLinks());
    }

    @Test
    @DisplayName("UserStoryInfo constructor with fileName as null")
    void userStoryInfoConstructorWithFileNameAsNull() {
      assertThrows(IllegalArgumentException.class,
        () -> new UserStoryInfo(null, "testFilePath", 10));
    }

    @Test
    @DisplayName("UserStoryInfo constructor with fileName as blank")
    void userStoryInfoConstructorWithFileNameAsBlank() {
      assertThrows(IllegalArgumentException.class,
        () -> new UserStoryInfo("", "testFilePath", 10));
    }

    @Test
    @DisplayName("UserStoryInfo constructor with filePath as null")
    void userStoryInfoConstructorWithFilepathAsNull() {
      assertThrows(IllegalArgumentException.class,
        () -> new UserStoryInfo("testFileName", null, 10));
    }

    @Test
    @DisplayName("UserStoryInfo constructor with filePath as blank")
    void userStoryInfoConstructorWithFilePathAsBlank() {
      assertThrows(IllegalArgumentException.class,
        () -> new UserStoryInfo("testFileName", "", 10));
    }

    @Test
    @DisplayName("UserStoryInfo constructor with numBrokenLinks less that zero")
    void userStoryInfoConstructorWithNumBrokenLinksLessThanZero() {
      assertThrows(IllegalArgumentException.class,
        () -> new UserStoryInfo("testFileName", "testFilePath", -10));
    }
  }

  @Nested
  @DisplayName("Test getter and setter methods")
  class getterAndSetterMethods {
    @BeforeEach
    void setup() throws InvalidStoryFormatException, IOException {
      fileName = "test.txt";
      path = "/path/to/test.txt";
      numBrokenLinks = 5;
      userStoryInfo = new UserStoryInfo(fileName, path, numBrokenLinks);
    }

    @Test
    void testGetFileName() {
      assertEquals(fileName, userStoryInfo.getFileName());
    }

    @Test
    void testGetPath() {
      assertEquals(path, userStoryInfo.getFilePath());
    }

    @Test
    void testGetNumBrokenLinks() {
      assertEquals(numBrokenLinks, userStoryInfo.getNumBrokenLinks());
    }
  }

  @Nested
  class TestEquals {
    @Test
    @DisplayName("Test equals when equal")
    void testEqualsWhenEqual() {
      UserStoryInfo userStoryInfo1 = new UserStoryInfo("testFileName1", "testFilePath1", 1);
      UserStoryInfo userStoryInfo2 = new UserStoryInfo("testFileName1", "testFilePath1", 1);
      assertEquals(userStoryInfo1, userStoryInfo2);
    }

    @Test
    @DisplayName("Test equals when not equals")
    void testEqualsWhenNotEqual() {
      UserStoryInfo userStoryInfo1 = new UserStoryInfo("testFileName1", "testFilePath1", 1);
      assertNotEquals(userStoryInfo1, "fewfe");
    }
  }

  @Nested
  class TestHashCode {
    @Test
    void testHashCode() {
      UserStoryInfo userStoryInfo = new UserStoryInfo("testFileName", "testFilePath", 1);

      int expectedHashCode = Objects.hash("testFilePath");

      int actualHashCode = userStoryInfo.hashCode();

      assertEquals(expectedHashCode, actualHashCode);
    }
  }
}

