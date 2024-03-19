package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidGoalFormatException;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalsRegister;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.HealthGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.InventoryGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.ScoreGoal;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoalFileHandlingTest {

  @Nested
  @DisplayName("Test positive tests on read and write to file")
  class PositiveWriteAndRead {

    private GoalsRegister register, register1;

    @BeforeEach
    void setUp() {
      List<String> items = new ArrayList<>();
      items.add("Sword");
      items.add("Wand");

      register = new GoalsRegister();
      register.addGoal(new GoldGoal(10));
      register.addGoal(new HealthGoal(10));
      register.addGoal(new ScoreGoal(10));
      register.addGoal(new InventoryGoal(items));
    }

    @Test
    @DisplayName("Test reading valid GoalsRegister from file")
    void testReadingValidGoalsRegisterFromFile()
        throws IOException, InvalidGoalFormatException {
      String goalsFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/haunted_house_test.goals";

      register1 = GoalFileHandling.readGoalsFromFile(goalsFilePath);

      assertEquals(register.toString(), register1.toString());
    }

    @Test
    @DisplayName("Test writing valid GoalRegister from file")
    void testWriteValidGoalRegisterToFile() throws IOException, InvalidGoalFormatException {
      String writeGoalsFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/test_write_goals.goals";

      GoalFileHandling.writeGoalsToFile(register, writeGoalsFilePath);
      register1 = GoalFileHandling.readGoalsFromFile(writeGoalsFilePath);

      assertEquals(register.toString(), register1.toString());
    }

    @Test
    @DisplayName("Test reading empty file returns empty register")
    void testReadEmptyFile() throws IOException, InvalidGoalFormatException {
      String emptyFileFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/empty_file.goals";

      GoalsRegister goalsRegister = GoalFileHandling.readGoalsFromFile(emptyFileFilePath);

      assertTrue(goalsRegister.isEmpty());
    }

    @Test
    @DisplayName("Test deleting file file twice")
    void testDeletingFileTwice() throws IOException {
      String filePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/test.goals";
      File file = new File(filePath);
      file.createNewFile();

      assertTrue(GoalFileHandling.deleteGoalsFile(filePath));
      assertFalse(GoalFileHandling.deleteGoalsFile(filePath));
    }
  }

  @Nested
  @DisplayName("Test negative tests for read and write goals")
  class NegativeWriteAndRead {

    @Test
    @DisplayName("Test reading throws IOException if file does not exist")
    void testReadGoalsWithNonExistentFile() {
      assertThrows(IOException.class, () -> GoalFileHandling.readGoalsFromFile("sheesh"));
    }

    @Test
    @DisplayName("Test reading with invalid type throws InvalidGoalFormatException")
    void testReadGoalsWithInvalidType() {
      String invalidGoalTypeFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/invalid_goal_type.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(invalidGoalTypeFilePath));
    }

    @Test
    @DisplayName("Test reading with invalid value throws InvalidGoalFormatException")
    void testReadGoalsWithInvalidValue() {
      String invalidGoalValueFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/invalid_goal_value.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(invalidGoalValueFilePath));
    }

    @Test
    @DisplayName("Test reading with invalid format - no colon - throws InvalidGoalFormatException")
    void testReadGoalsInvalidFormat() {

      String invalidGoalFormatFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/goal_with_no_colon.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(invalidGoalFormatFilePath));
    }

    @Test
    @DisplayName("Test reading with empty first line throws InvalidGoalFormatException")
    void testReadWithEmptyFirstLine() {
      String emptyFirstLineFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/empty_first_line.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(emptyFirstLineFilePath));
    }

    @Test
    @DisplayName("Test reading with empty line between goals throws InvalidGoalFormatException")
    void testReadWithEmptyLineBetweenGoals() {
      String emptyLineBetweenGoalFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/empty_line_between_goals.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(emptyLineBetweenGoalFilePath));
    }

    @Test
    @DisplayName("Test reading with space before goal throws InvalidGoalFormatException")
    void testReadWithSpaceBeforeGoal() {
      String spaceBeforeGoalFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/space_before_goal.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(spaceBeforeGoalFilePath));
    }

    @Test
    @DisplayName("Test reading duplicate goal throws InvalidGoalFormatException")
    void testReadDuplicateGoal() {
      String duplicateGoalFilePath = System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/duplicate_goal.goals";

      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.readGoalsFromFile(duplicateGoalFilePath));
    }

    @Test
    @DisplayName("Test reading null throws IOException")
    void testReadingNull() {
      assertThrows(IOException.class,
          () -> GoalFileHandling.readGoalsFromFile(null));
    }

    @Test
    @DisplayName("Test writing null GoalsRegister throws IllegalArgumentException")
    void testWritingNullRegister() {
      String filePath =  System.getProperty("user.dir") +
          "/src/test/resources/goalFiles/write.goals";
      assertThrows(IllegalArgumentException.class,
          () -> GoalFileHandling.writeGoalsToFile(null, filePath));
    }

    @Test
    @DisplayName("Test writing null filePath throws IOException")
    void testWritingNullFilePath() {
      assertThrows(IOException.class,
          () -> GoalFileHandling.writeGoalsToFile(new GoalsRegister(), null));
    }

    @Test
    @DisplayName("Test writing with invalid file type throws InvalidGoalFormatException")
    void testWriterWithInvalidFileType() {
      assertThrows(InvalidGoalFormatException.class,
          () -> GoalFileHandling.writeGoalsToFile(new GoalsRegister(), "shesh.gals"));
    }
  }
}

