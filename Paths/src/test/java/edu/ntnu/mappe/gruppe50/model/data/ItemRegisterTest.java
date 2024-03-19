package edu.ntnu.mappe.gruppe50.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import edu.ntnu.mappe.gruppe50.model.data.ItemRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemRegisterTest {

  ItemRegister register;

  String item1, item2;

  @BeforeEach
  void setUp() {
    register = new ItemRegister();
    item1 = "sword";
    item2 = "wand";
  }

  @Test
  @DisplayName("Test adding and removing valid items")
  void testAddAndRemoveValidItems() {
    register.addItem(item1);
    register.addItem(item2);
    assertEquals(2, register.getItems().size());

    register.removeItem(item1);
    register.removeItem(item2);
    assertEquals(0, register.getItems().size());
  }

  @Test
  @DisplayName("Test adding null throws Illegal Argument Exception")
  void testAddingNull() {
    assertThrows(IllegalArgumentException.class,
        () -> register.addItem(null));
  }

  @Test
  @DisplayName("Test removing null throws Illegal Argument Exception")
  void testRemovingNull() {
    assertThrows(IllegalArgumentException.class,
        () -> register.removeItem(null));
  }

  @Test
  @DisplayName("Test adding blank throws Illegal Argument Exception")
  void testAddingBlank() {
    assertThrows(IllegalArgumentException.class,
        () -> register.addItem(""));
  }
  @Test
  @DisplayName("Test removing blank throws Illegal Argument Exception")
  void testRemovingBlank() {
    assertThrows(IllegalArgumentException.class,
        () -> register.removeItem(""));
  }


  @Test
  @DisplayName("Test removing duplicate item throws Illegal Argument Exception")
  void testAddingDuplicate() {
    register.addItem(item1);
    assertThrows(IllegalArgumentException.class,
        () -> register.addItem(item1));
  }
}

