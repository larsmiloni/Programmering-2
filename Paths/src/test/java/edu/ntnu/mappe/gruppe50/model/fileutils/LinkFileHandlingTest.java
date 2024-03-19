package edu.ntnu.mappe.gruppe50.model.fileutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.LinkRegister;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.actions.HealthAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.InventoryAction;
import edu.ntnu.mappe.gruppe50.model.customexceptions.InvalidLinkFormatException;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.InventoryGoal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LinkFileHandlingTest {

  @Nested
  @DisplayName("Tests writeToFile and readToFile with valid formats")
  class PositiveWriteAndRead {

    private LinkRegister links, linkRegister;
    private Link tryOpenDoorToAnotherRoom, openTheBookOfSpells;

    private Action healthAction, inventoryAction;

    private Goal goldGoal, inventoryGoal;

    @BeforeEach
    void setUp() {
      List<String> items = new ArrayList<>();
      items.add("sword");
      items.add("spear");

      tryOpenDoorToAnotherRoom = new Link("Try to open the door", "Another room");
      openTheBookOfSpells = new Link("Open the book", "The book of spells");

      healthAction = new HealthAction(20);
      inventoryAction = new InventoryAction("Sword");
      goldGoal = new GoldGoal(10);
      inventoryGoal = new InventoryGoal(items);

      links = new LinkRegister();
      links.addLink(tryOpenDoorToAnotherRoom);
      links.addLink(openTheBookOfSpells);
    }

    @Test
    @DisplayName("Test read from file with base links")
    void testReadWithBaseLinks() throws InvalidLinkFormatException, IOException {
      String basicLinkFilePath = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/basic_links.links";
      LinkRegister linksFromFile = LinkFileHandling.readLinksFromFile(basicLinkFilePath);

      assertEquals(links.toString(), linksFromFile.toString());
    }

    @Test
    @DisplayName("Test read from file with links that have goals and actions")
    void testReadWithActionsAndGoals() throws InvalidLinkFormatException, IOException {
      String actionAndGoalLinkFilePath = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/links_with_goals_and_actions.links";

      tryOpenDoorToAnotherRoom.addGoal(inventoryGoal);
      tryOpenDoorToAnotherRoom.addAction(inventoryAction);
      openTheBookOfSpells.addGoal(goldGoal);
      openTheBookOfSpells.addAction(healthAction);

      linkRegister = LinkFileHandling.readLinksFromFile(actionAndGoalLinkFilePath);

      assertEquals(links.toString(), linkRegister.toString());
    }

    @Test
    @DisplayName("Test write to file with base links")
    void testWriteWithBaseLinks() throws IOException, InvalidLinkFormatException {
      String writeBasicLinksFilePath = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/test_write_basic_links.links";

      LinkFileHandling.writeLinksToFile(links, writeBasicLinksFilePath);
      linkRegister = LinkFileHandling.readLinksFromFile(writeBasicLinksFilePath);

      assertEquals(links.toString(), linkRegister.toString());
    }

    @Test
    @DisplayName("Test write to file with links that have actions and goals")
    void testWriteWithActionsAndGoals() throws IOException, InvalidLinkFormatException {
      String writeLinksWithActionAndGoalFilePath = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/test_write_links_with_goals_and_actions.links";

      tryOpenDoorToAnotherRoom.addGoal(inventoryGoal);
      tryOpenDoorToAnotherRoom.addAction(inventoryAction);
      openTheBookOfSpells.addGoal(goldGoal);
      openTheBookOfSpells.addAction(healthAction);

      LinkFileHandling.writeLinksToFile(links, writeLinksWithActionAndGoalFilePath);
      linkRegister = LinkFileHandling.readLinksFromFile(writeLinksWithActionAndGoalFilePath);

      assertEquals(links.toString(), linkRegister.toString());
    }

    @Test
    @DisplayName("Test writing and reading empty link register")
    void testWritingEmptyRegister() throws IOException, InvalidLinkFormatException {
      String emptyFileFilePath =  System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/empty.links";

      linkRegister = new LinkRegister();
      LinkFileHandling.writeLinksToFile(linkRegister, emptyFileFilePath);
      linkRegister = LinkFileHandling.readLinksFromFile(emptyFileFilePath);

      assertEquals(0, linkRegister.getLinks().size());
    }
  }

  @Nested
  @DisplayName("Test writeToFile and readToFile with invalid formats")
  class NegativeWriteAndRead {

    @Test
    @DisplayName("Test reading file with invalid file type throws InvalidLinkFormatException")
    void testReadingFileWithInvalidFileType() {
      String invalidFileType = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/invalid_file_type.lonks";

      assertThrows(InvalidLinkFormatException.class,
          () -> LinkFileHandling.readLinksFromFile(invalidFileType));
    }

    @Test
    @DisplayName("Test writing file with invalid file type throws InvalidLinkFormatException")
    void testWritingFileWithInvalidFileType() {
      String invalidFileType = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/invalid_file_type.lonks";

      assertThrows(InvalidLinkFormatException.class,
          () -> LinkFileHandling.writeLinksToFile(new LinkRegister(), invalidFileType));
    }

    @Test
    @DisplayName("Test reading file with throws IOException if file does not exist")
    void testReadingFileNonExistentFile() {
      assertThrows(IOException.class,
          () -> LinkFileHandling.readLinksFromFile("sheesh.links"));
    }

    @Test
    @DisplayName("Test reading file with double line breaks between links throws InvalidLinkFormatException")
    void testReadingFileWithDoubleLineBreak() {
      String doubleEmptyLineBetweenLinks = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/double_empty_line_between_links.links";

      assertThrows(InvalidLinkFormatException.class,
          () -> LinkFileHandling.readLinksFromFile(doubleEmptyLineBetweenLinks));
    }

    @Test
    @DisplayName("Test reading null throws IOException")
    void testReadingNull() {
      assertThrows(IOException.class,
          () -> LinkFileHandling.readLinksFromFile(null));
    }

    @Test
    @DisplayName("Test writing null LinkRegister throws IOException")
    void testWritingNullRegister() {
      String filePath =  System.getProperty("user.dir") +
          "/src/test/resources/linkFiles/write.links";
      assertThrows(IOException.class,
          () -> LinkFileHandling.writeLinksToFile(null, filePath));
    }

    @Test
    @DisplayName("Test reading null file path throws IOException")
    void testWritingNullFilePath() {
      assertThrows(IOException.class,
          () -> LinkFileHandling.writeLinksToFile(new LinkRegister(), null));
    }

    @Test
    @DisplayName("Test reading file with empty first line throws InvalidLinkFormatException")
    void testReadingFileWithEmptyFirstLine() {
      String emptyFirstLineFilePath = System.getProperty("user.dir")
          + "/src/test/resources/linkFiles/empty_first_line.links";

      assertThrows(InvalidLinkFormatException.class,
          () -> LinkFileHandling.readLinksFromFile(emptyFirstLineFilePath));
    }
  }
}
