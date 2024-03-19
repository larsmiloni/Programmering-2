package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidGoalFormatException;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidLinkFormatException;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidStoryFormatException;
import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameFileHandlingTest {

  @Nested
  class ReadGameFromFile {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/gameFiles/game.game";
    @BeforeEach
    void setUp() throws IOException {
      GameFileHandling.replaceFirstLine(filePath, System.getProperty("user.dir") + "/src/test/resources/storyFiles/basic_format.paths");
      GameFileHandling.replaceSecondLine(filePath, "Dole");
    }

    @Test
    void testReadGameFromFile() throws InvalidStoryFormatException, InvalidLinkFormatException, InvalidGoalFormatException, IOException {
      Game game = GameFileHandling.readGameFromFile(filePath, false);

      Story story = game.getStory();
      Player player = game.getPlayer();
      List<Goal> goalList = game.getGoals();

      assertEquals("Haunted House", story.getTitle());

      assertEquals("Dole", player.getName());
      assertEquals(50, player.getHealth());
      assertEquals(0, player.getGold());

      assertEquals(new GoldGoal(19), goalList.get(0));
    }

    @Test
    void testReadGameFromFileWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.readGameFromFile(null, false));
    }

    @Test
    void testReadGameFromFileWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.readGameFromFile("", false));
    }

    @Test
    void testReadGameFromFIleWhenFileDontEndWithDotGame() {
      String storyFilePath = System.getProperty("user.dir") + "/src/test/resources/storyFiles/basic_format.paths";
      assertThrows(IOException.class, () -> GameFileHandling.readGameFromFile(storyFilePath, false));
    }

    @Test
    void testReadGameWithEmptyFile() {
      String emptyFilePath = System.getProperty("user.dir") + "/src/test/resources/gameFiles/empty.game";
      assertThrows(IllegalArgumentException.class,
          () -> GameFileHandling.readGameFromFile(emptyFilePath, false));
    }
  }
  
  @Nested
  class TestReadFirstLineFromFile {

    @Test
    void readFirstLineFromFile() throws IOException {
      String filePath = System.getProperty("user.dir") + "/src/test/resources/gameFiles/read_first_line.game";
      assertEquals("testFirstLine", GameFileHandling.readFirstLineFromFile(filePath));
    }

    @Test
    void readFirstLineFromFileWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.readFirstLineFromFile(null));
    }

    @Test
    void readFirstLineFromFileWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.readFirstLineFromFile(""));
    }

    @Test
    void readFirstLineFromFileWhenFilePathDontEndInDotGame() {
      String storyFilePath = System.getProperty("user.dir") + "/src/test/resources/storyFiles/basic_format.paths";
      assertThrows(IOException.class, () -> GameFileHandling.readFirstLineFromFile(storyFilePath));
    }
  }

  @Nested
  class TestReplaceFirsLine {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/gameFiles/replace_first.game";
    @Test
    void testReplaceFirstLine() throws IOException {
      GameFileHandling.replaceFirstLine(filePath, "testNewFirstLine");
      assertEquals("testNewFirstLine", GameFileHandling.readFirstLineFromFile(filePath));
      GameFileHandling.replaceFirstLine(filePath, "");
    }

    @Test
    void testReplaceFirstLineWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.replaceFirstLine(null, "test"));
    }

    @Test
    void testReplaceFirstLineWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.replaceFirstLine("", "test"));
    }

    @Test
    void testReplaceFirstLineWhenFilePathDontEndWithDotGame() {
      String storyFilePath = System.getProperty("user.dir") + "/src/test/resources/storyFiles/basic_format.paths";
      assertThrows(IOException.class, () -> GameFileHandling.replaceFirstLine(storyFilePath, "test"));
    }
  }

  @Nested
  class TestReplaceSecondLine {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/gameFiles/replace_second.game";
    @Test
    void testReplaceSecondLine() throws IOException {
      GameFileHandling.replaceSecondLine(filePath, "testNewFirstLine");
      assertEquals("testNewFirstLine", GameFileHandling.readSecondLineFromFile(filePath));
      GameFileHandling.replaceSecondLine(filePath, "");
    }

    @Test
    void testReplaceSecondLineWhenFilePathIsNull() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.replaceSecondLine(null, "test"));
    }

    @Test
    void testReplaceSecondLineWhenFilePathIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> GameFileHandling.replaceSecondLine("", "test"));
    }

    @Test
    void testReplaceSecondLineWhenFilePathDontEndWithDotGame() {
      String storyFilePath = System.getProperty("user.dir") + "/src/test/resources/storyFiles/basic_format.paths";
      assertThrows(IOException.class, () -> GameFileHandling.replaceSecondLine(storyFilePath, "test"));
    }
  }
}
