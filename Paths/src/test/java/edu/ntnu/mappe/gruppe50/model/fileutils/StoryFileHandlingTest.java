package edu.ntnu.mappe.gruppe50.model.fileutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.actions.GoldAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.InventoryAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.ScoreAction;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidStoryFormatException;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import java.io.IOException;

import org.junit.jupiter.api.*;

public class StoryFileHandlingTest {

  @Nested
  @DisplayName("Tests writeToFile and readToFile with valid formats")
  class PositiveWriteAndRead {

    private Link tryOpenDoorToAnotherRoom;
    private Link openTheBookOfSpells;
    private Passage beginnings, anotherRoom;
    private String storyTitle;

    @BeforeEach
    void setUp() {
      tryOpenDoorToAnotherRoom = new Link("Try to open the door", "Another room");
      openTheBookOfSpells = new Link("Open the book", "The book of spells");
      Link goBackToBeginnings = new Link("Go back", "Beginnings");

      beginnings = new Passage("Beginnings",
          "You are in a small, dimly lit room. There is a door in front of you.");
      anotherRoom = new Passage("Another room",
          "The door opens to another room. You see a desk with a large, dusty book.");

      storyTitle = "Haunted House";

      beginnings.addLink(tryOpenDoorToAnotherRoom);
      anotherRoom.addLink(openTheBookOfSpells);
      anotherRoom.addLink(goBackToBeginnings);
    }

    @Test
    @DisplayName("Test reading a story from file with actions and goals")
    void testReadFromFileWithActionsAndGoals() throws IOException, InvalidStoryFormatException {
      String storyFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/haunted_house_test.paths";

      Story writtenStory = StoryFileHandling.readStoryFromFile(storyFilePath);

      Action scoreAction = new ScoreAction(50);
      Action inventoryAction = new InventoryAction("sword");
      Action goldAction = new GoldAction(100);
      Goal goldGoal = new GoldGoal(100);

      Link goBackToAnotherRoom = new Link("Go back", "Another room");
      Passage theBookOfSpells = new Passage("The book of spells", "The book opens.");

      goBackToAnotherRoom.addAction(scoreAction);
      goBackToAnotherRoom.addAction(inventoryAction);
      openTheBookOfSpells.addAction(goldAction);
      openTheBookOfSpells.addGoal(goldGoal);

      theBookOfSpells.addLink(goBackToAnotherRoom);

      Story codedStory = new Story(storyTitle, beginnings);
      codedStory.addPassage(anotherRoom);
      codedStory.addPassage(theBookOfSpells);

      assertEquals(writtenStory.toString(), codedStory.toString());
    }

    @Test
    @DisplayName("Test read from file with the basic .paths file format")
    void testReadFromFileWithBaseFormat() throws IOException, InvalidStoryFormatException {
      String basicFormatFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/basic_format.paths";

      Story writtenStory = StoryFileHandling.readStoryFromFile(basicFormatFilePath);

      Story codedStory = new Story(storyTitle, beginnings);
      codedStory.addPassage(anotherRoom);

      assertEquals(writtenStory.toString(), codedStory.toString());
    }

    @Test
    @DisplayName("Test reading a base .paths file where one passage has no links")
    void testReadPassageWithNoLink() throws IOException, InvalidStoryFormatException {
      String noLinkPassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/passage_with_no_link.paths";

      Story writtenStory = StoryFileHandling.readStoryFromFile(noLinkPassageFilePath);

      beginnings.removeLink(tryOpenDoorToAnotherRoom);
      Story codedStory = new Story(storyTitle, beginnings);
      codedStory.addPassage(anotherRoom);

      assertEquals(codedStory.toString(), writtenStory.toString());
    }

    @Test
    @DisplayName("Test writing base .paths file to file")
    void testWriteBaseFile() throws InvalidStoryFormatException, IOException {
      String writeBasicFormatFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/write_basic_format.paths";

      Story story = new Story(storyTitle, beginnings);
      story.addPassage(anotherRoom);
      StoryFileHandling.writeStoryToFile(story, writeBasicFormatFilePath);

      assertEquals(story.toString(), StoryFileHandling.readStoryFromFile(writeBasicFormatFilePath).toString());
    }

    @Test
    @DisplayName("Test writing .paths file with goals and actions to file")
    void testWriteStoryWithGoalsAndActions() throws InvalidStoryFormatException, IOException {
      String writeFormatWithGoalsAndLinksFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/write_format_with_goals_and_actions.paths";

      Action scoreAction = new ScoreAction(50);
      Action inventoryAction = new InventoryAction("sword");
      Action goldAction = new GoldAction(100);
      Goal goldGoal = new GoldGoal(100);

      Link goBackToAnotherRoom = new Link("Go back", "Another room");
      Passage theBookOfSpells = new Passage("The book of spells", "The book opens.");

      goBackToAnotherRoom.addAction(scoreAction);
      goBackToAnotherRoom.addAction(inventoryAction);
      openTheBookOfSpells.addAction(goldAction);
      openTheBookOfSpells.addGoal(goldGoal);

      theBookOfSpells.addLink(goBackToAnotherRoom);

      Story codedStory = new Story(storyTitle, beginnings);
      codedStory.addPassage(anotherRoom);
      codedStory.addPassage(theBookOfSpells);

      StoryFileHandling.writeStoryToFile(codedStory, writeFormatWithGoalsAndLinksFilePath);

      assertEquals(codedStory.toString(), StoryFileHandling.readStoryFromFile(writeFormatWithGoalsAndLinksFilePath).toString());
    }

  }

  @Nested
  @DisplayName("Test writeToFile and readToFile with invalid formats")
  class NegativeWriteAndRead {

    static Story story;
    static String doublePassageFilePath, noContentPassageFilePath, onlyStoryTitleFilePath,
        noTitleFilePath, noEmptyLineAfterTitleFilePath, missingColonPassageFilePath,
        missingLinkBracketsFilePath, missingActionBracketFilePath, doubleEmptyLineAfterTitleFilePath,
        doubleEmptyLineAfterPassageFilePath, trailingBlankLinesFilePath, passagesNotSeparatedByLineFilePath,
        emptyLineBetweenLinkAndPassageFilePath, emptyLineBetweenActionAndLinkFilePath,
        emptyLineBetweenLinksFilePath, emptyLineBetweenActionsFilePath,
        invalidFileType, invalidActionTypeFilePath, invalidActionValueFilePath,
        emptyFileFilePath, doubleActionTypeFilePath, doubleGoalTypeFilePath,
        emptyLineBetweenLinkAndGoalFilePath, emptyLineBetweenGoalsFilePath;

    @BeforeAll
    static void setUp() {

      story = new Story("title", new Passage("title", "content"));

      doublePassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/double_passage_title.paths";

      noContentPassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/passage_no_content.paths";

      onlyStoryTitleFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/title_only.paths";

      noTitleFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/no_title.paths";

      noEmptyLineAfterTitleFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/no_empty_line_after_title.paths";

      doubleEmptyLineAfterTitleFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/double_empty_line_after_title.paths";

      missingColonPassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/no_passage_prefix.paths";

      missingLinkBracketsFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/invalid_link.paths";

      missingActionBracketFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/invalid_action.paths";

      doubleEmptyLineAfterPassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/double_empty_line_after_passage.paths";

      trailingBlankLinesFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/trailing_empty_lines.paths";

      passagesNotSeparatedByLineFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/no_empty_line_between_passages.paths";

      emptyLineBetweenLinkAndPassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_line_between_passage_link.paths";

      emptyLineBetweenActionAndLinkFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_line_between_action_link.paths";

      emptyLineBetweenLinksFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_line_between_links.paths";

      emptyLineBetweenActionsFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_line_between_actions.paths";

      invalidFileType = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/invalid_file_type.poths";

      invalidActionTypeFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/invalid_action_type.paths";

      invalidActionValueFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/invalid_action_value.paths";

      emptyFileFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_file.paths";

      doubleActionTypeFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/double_action_type.paths";

      doubleGoalTypeFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/double_goal_type.paths";

      emptyLineBetweenLinkAndGoalFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_line_between_goal_link.paths";

      emptyLineBetweenGoalsFilePath  = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/empty_line_between_goals.paths";
    }

    @Test
    @DisplayName("Test reading file with double passage title throws InvalidStoryFormatException")
    void testDoublePassageTitle() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(doublePassageFilePath));
    }

    @Test
    @DisplayName("Test reading file where passage has no content throws InvalidStoryFormatException")
    void testNoContentPassage() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(noContentPassageFilePath));
    }

    @Test
    @DisplayName("Test reading file with only story title throws InvalidStoryFormatException")
    void testStoryWithOnlyTitle() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(onlyStoryTitleFilePath));
    }

    @Test
    @DisplayName("Test reading file with no story title throws InvalidStoryFormatException")
    void testStoryWithNoTitle() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(noTitleFilePath));
    }

    @Test
    @DisplayName("Test reading file with no empty line after story title throws InvalidStoryFormatException")
    void testStoryWithNoEmptyLineAfterTitle() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(noEmptyLineAfterTitleFilePath));
    }

    @Test
    @DisplayName("Test reading file with double empty line after story title throws InvalidStoryFormatException")
    void testStoryWithDoubleEmptyLineAfterTitle() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(doubleEmptyLineAfterTitleFilePath));
    }

    @Test
    @DisplayName("Test reading file with missing :: in front of passage throws InvalidStoryFormatException")
    void testInvalidPassageFormat() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(missingColonPassageFilePath));
    }

    @Test
    @DisplayName("Test reading file with missing () in link throws InvalidStoryFormatException")
    void testInvalidLinkFormat() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(missingLinkBracketsFilePath));
    }

    @Test
    @DisplayName("Test reading file with missing value and } after action throws InvalidStoryFormatException")
    void testInvalidActionFormat() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(missingActionBracketFilePath));
    }

    @Test
    @DisplayName("Test reading file with double empty line after passage throws InvalidStoryFormatException")
    void testDoubleEmptyLineAfterPassage() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(doubleEmptyLineAfterPassageFilePath));
    }

    @Test
    @DisplayName("Test reading file with trailing blank lines throws InvalidStoryFormatException")
    void testStoryWithEmptyLineAtEndOfFile() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(trailingBlankLinesFilePath));
    }
    @Test
    @DisplayName("Test reading file with passages not separated by empty line throws InvalidStoryFormatException")
    void testStoryWithNoEmptyLineBetweenPassages() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(passagesNotSeparatedByLineFilePath));
    }

    @Test
    @DisplayName("Test reading file with empty line between passage and link throws InvalidStoryFormatException")
    void testEmptyLineBetweenPassageAndLink() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyLineBetweenLinkAndPassageFilePath));
    }

    @Test
    @DisplayName("Test reading file with empty line between action and link throws InvalidStoryFormatException")
    void testEmptyLineBetweenActionAndLink() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyLineBetweenActionAndLinkFilePath));
    }

    @Test
    @DisplayName("Test reading file with empty line between links throws InvalidStoryFormatException")
    void testEmptyLineBetweenLinks() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyLineBetweenLinksFilePath));
    }

    @Test
    @DisplayName("Test reading file with empty line between actions throws InvalidStoryFormatException")
    void testEmptyLineBetweenActions() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyLineBetweenActionsFilePath));
    }

    @Test
    @DisplayName("Test that readStoryFromFile where the file does not have .paths ending throws InvalidStoryFormatException")
    void testReadInvalidFileType() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(invalidFileType));
    }

    @Test
    @DisplayName("Test readStoryFromFile throws IOException if file does not exist")
    void testReaStoryWithNonExistentFile() {
      assertThrows(IOException.class, () -> StoryFileHandling.readStoryFromFile("sheesh"));
    }

    @Test
    @DisplayName("Test readStoryFromFile with invalid type throws InvalidStoryFormatException")
    void testReadStoryWithInvalidActionType() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(invalidActionTypeFilePath));
    }

    @Test
    @DisplayName("Test readStoryFromFile with non parsable string to integer throws InvalidStoryFormatException")
    void testReadStoryWithInvalidActionValue() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(invalidActionValueFilePath));
    }

    @Test
    @DisplayName("Test reading empty file throws InvalidStoryFormatException")
    void testReadEmptyFile() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyFileFilePath));
    }

    @Test
    @DisplayName("Test adding two actions of the same type to a link throws InvalidStoryFormatException")
    void testReadStoryWithDuplicateActionsInLink() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(doubleActionTypeFilePath));
    }

    @Test
    @DisplayName("Test adding two goals of the same type to a link throws InvalidStoryFormatException")
    void testReadStoryWithDuplicateGoalsInLink() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(doubleGoalTypeFilePath));
    }

    @Test
    @DisplayName("Test reading file with empty line between goal and link throws InvalidStoryFormatException")
    void testEmptyLineBetweenGoalAndLink() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyLineBetweenLinkAndGoalFilePath));
    }

    @Test
    @DisplayName("Test reading file with empty line between goals throws InvalidStoryFormatException")
    void testEmptyLineBetweenGoals() {
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(emptyLineBetweenGoalsFilePath));
    }

    @Test
    @DisplayName("Test reading duplicate passage throws InvalidStoryFormatException ")
    void testReadDuplicatePassage() {
      String duplicatePassageFilePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/duplicate_passage.paths";
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.readStoryFromFile(duplicatePassageFilePath)
      );
    }

    @Test
    @DisplayName("Test reading null file path throws IOException")
    void testReadingNullFilePath() {

      assertThrows(IOException.class,
          () -> StoryFileHandling.readStoryFromFile(null)
      );
    }

    @Test
    @DisplayName("Test writing file with invalid file type throws InvalidStoryFormatException")
    void testWritingWithInvalidFileType() {
      String writingNullToFilePath = System.getProperty("user.dir")
        + "/src/test/resources/storyFiles/nullToFile.gppg";
      assertThrows(InvalidStoryFormatException.class,
          () -> StoryFileHandling.writeStoryToFile(story, writingNullToFilePath));
    }

    @Test
    @DisplayName("Test writing null story throws IOException")
    void testWritingNullStory() {
      String filePath = System.getProperty("user.dir")
          + "/src/test/resources/storyFiles/write.paths";
      assertThrows(IOException.class,
          () -> StoryFileHandling.writeStoryToFile(null, filePath)
      );
    }

    @Test
    @DisplayName("Test writing null file path throws IOException")
    void testWritingNullFilePath() {
      Story story = new Story("test", new Passage("test", "title"));

      assertThrows(IOException.class,
          () -> StoryFileHandling.writeStoryToFile(story, null)
      );
    }
  }
}
