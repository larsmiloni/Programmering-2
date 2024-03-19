package edu.ntnu.mappe.gruppe50.model.fileutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ItemFileHandlingTest {

  @Nested
  @DisplayName("Test positive test for read and write")
  class PositiveReadAndWrite {

    private ItemRegister itemRegister1, itemRegister2;
    @BeforeEach
    void setUp() {
      String sword = "Sword";
      String wand = "Wand";
      String potion = "Potion";

      itemRegister1 = new ItemRegister();
      itemRegister1.addItem(sword);
      itemRegister1.addItem(wand);
      itemRegister1.addItem(potion);

    }

    @Test
    @DisplayName("Test reading valid ItemRegister from file")
    void testReadingValidItemsFromFile() throws IOException {
      String itemsFilePath = System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/items.items";

      itemRegister2 = ItemFileHandling.readItemsFromFile(itemsFilePath);

      assertEquals(itemRegister1.toString(), itemRegister2.toString());
    }

    @Test
    @DisplayName("Test reading empty file returns empty register")
    void testReadingEmptyFile() throws IOException {
      String emptyFileFilePath = System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/empty.items";

      itemRegister2 = ItemFileHandling.readItemsFromFile(emptyFileFilePath);

      assertTrue(itemRegister2.getItems().isEmpty());
    }

    @Test
    @DisplayName("Test writing valid ItemRegister to file")
    void testWritingValidItemsToFile() throws IOException {
      String writeItemsFilePath = System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/test_write_items.items";

      ItemFileHandling.writeItemsToFile(itemRegister1, writeItemsFilePath);
      itemRegister2 = ItemFileHandling.readItemsFromFile(writeItemsFilePath);

      assertEquals(itemRegister1.toString(), itemRegister2.toString());
    }

  }

  @Nested
  @DisplayName("Test negative tests for read and write")
  class NegativeReadAndWrite {
    @Test
    @DisplayName("Test writing to file with invalid file type throws IOException")
    void testWritingFileWithInvalidFileType() {
      assertThrows(IOException.class,
          () -> ItemFileHandling.writeItemsToFile(new ItemRegister(), "shesh.itms"));
    }

    @Test
    @DisplayName("Test reading file with empty first line throws IOException")
    void testReadingFileWithEmptyFirstLine() {
      String emptyFirstLineFilePath = System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/empty_first_line.items";
      assertThrows(IOException.class,
          () -> ItemFileHandling.readItemsFromFile(emptyFirstLineFilePath));
    }

    @Test
    @DisplayName("Test reading file with duplicate item throws IOException")
    void testReadingFileWithDuplicateItem() {
      String duplicateItemFilePath = System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/duplicate_item.items";

      assertThrows(IOException.class,
          () -> ItemFileHandling.readItemsFromFile(duplicateItemFilePath));
    }


    @Test
    @DisplayName("Test reading file with empty line between items throws IOException")
    void testReadingFileWithEmptyLineBetweenItems() {
      String emptyLineBetweenItemFilePath = System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/empty_line_between_items.items";

      assertThrows(IOException.class,
          () -> ItemFileHandling.readItemsFromFile(emptyLineBetweenItemFilePath));
    }

    @Test
    @DisplayName("Test reading null throws IOException")
    void testReadingNull() {
      assertThrows(IOException.class,
          () -> ItemFileHandling.readItemsFromFile(null));
    }

    @Test
    @DisplayName("Test writing null ItemRegister throws IllegalArgumentException")
    void testWritingNullRegister() {
      String filePath =  System.getProperty("user.dir") +
          "/src/test/resources/itemFiles/write.items";
      assertThrows(IllegalArgumentException.class,
          () -> ItemFileHandling.writeItemsToFile(null, filePath));
    }

    @Test
    @DisplayName("Test reading null file path throws IOException")
    void testReadingNullFilePath() {
      assertThrows(IOException.class,
          () -> ItemFileHandling.writeItemsToFile(new ItemRegister(), null));
    }
  }
}