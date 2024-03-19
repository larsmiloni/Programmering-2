package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File handling class for reading and writing item registers to and from a .items file. The item
 * register is a list of strings.
 *
 * @author Harry Linrui Xu
 * @since 14.05.23
 */
public class ItemFileHandling {

  private static final String itemFileType = ".items";


  /**
   * Writes a goal register to a specified file. The method uses the toString of the item register.
   *
   * @param items    The target item register which will be written to file.
   * @param filePath The absolute path of the file.
   * @throws IOException If the specified file cannot be written to or if the items or file path are
   *                     null.
   */
  public static void writeItemsToFile(ItemRegister items, String filePath) throws IOException {
    if (filePath == null) {
      throw new IOException("FilePath cannot be null");
    }
    if (items == null) {
      throw new IllegalArgumentException("Items cannot be null");
    }
    if (!filePath.endsWith(itemFileType)) {
      throw new IOException("The file does not have the correct " + itemFileType + " file type");
    }

    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fileWriter)) {

      bw.write(items.toString());
    } catch (IOException ex) {
      throw new IOException("Error writing items to file: " + ex.getMessage(), ex);
    }
  }

  /**
   * Reads an item register from a file, specified by the method parameter.
   *
   * @param filePath The absolute file path of the file.
   * @return The item register, stored in the target file.
   * @throws IOException If the file does not exist or if the formatting of the file is invalid -
   *                     empty item or duplicate item, or if file path is null.
   */
  public static ItemRegister readItemsFromFile(String filePath) throws IOException {
    ItemRegister itemRegister = new ItemRegister();

    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader)) {

      if (!filePath.endsWith(itemFileType)) {
        throw new IOException("The file does not have the correct " + itemFileType + " file type");
      }

      String nextLine = "";
      while (nextLine != null) {
        nextLine = br.readLine();

        //this if branch prevents a nullpointer exception from occurring in the next else-if
        if (nextLine == null) {
          continue;
        } else if (!nextLine.isBlank()) {
          itemRegister.addItem(nextLine);
        } else {
          throw new IOException(
              "The file does not have the correct " + itemFileType + " file type");
        }
      }
    } catch (IOException | IllegalArgumentException ioe) {
      throw new IOException(ioe.getMessage(), ioe);
    } catch (NullPointerException npe) {
      throw new IOException("File path cannot be null");
    }
    return itemRegister;
  }
}
