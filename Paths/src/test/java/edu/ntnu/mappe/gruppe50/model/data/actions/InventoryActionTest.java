package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.actions.InventoryAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryActionTest {

  @Nested
  class InventoryActionConstructor {

    InventoryAction inventoryAction;
    Player player;

    String item;

    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
          .health(10)
          .score(10)
          .gold(10)
          .build();
    }

   @Test
    @DisplayName("Test inventory action with valid item")
    void testInventoryAction() {
      item = "Rusty sword";
      inventoryAction = new InventoryAction(item);
      inventoryAction.execute(player);

      assertTrue(player.getInventory().contains(item));
    }

    @Test
    @DisplayName("Test inventory action with two actions")
    void testInventoryActionWithTwoActions() {
      item = "Rusty sword";
      inventoryAction = new InventoryAction(item);
      Action inventoryAction1 = new InventoryAction(item);
      inventoryAction.execute(player);
      inventoryAction1.execute(player);

      assertEquals(player.getInventory().size(), 2);
      assertTrue(player.getInventory().contains("Rusty sword"));
    }
    @Test
    @DisplayName("Test Illegal Argument Exception is thrown if player is null")
    void testIllegalArgumentExceptionIsThrownIfPlayerIsNull() {
      item = "Rusty sword";
      inventoryAction = new InventoryAction(item);
      assertThrows(IllegalArgumentException.class, () -> inventoryAction.execute(null));
    }

    @Test
    @DisplayName("Test Illegal Argument Exception is thrown if item is null")
    void testIllegalArgumentExceptionIsThrownIfItemIsNull() {
      assertThrows(IllegalArgumentException.class, () -> inventoryAction = new InventoryAction(null));
    }

    @Test
    @DisplayName("Test Illegal Argument Exception is thrown if item is blank")
    void testIllegalArgumentExceptionIsThrownIfItemIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> inventoryAction = new InventoryAction(""));
    }
  }

  @Nested
  @DisplayName("Equals")
  class TestEquals {
    InventoryAction inventoryAction1, inventoryAction2, inventoryAction3;

    @BeforeEach
    void setUp() {
      inventoryAction1 = new InventoryAction("item1");
      inventoryAction2 = new InventoryAction("item1");
      inventoryAction3 = new InventoryAction("item2");
    }

    @Test
    @DisplayName("Test gold goals with the same inventory")
    void testEqualInventoryActionWhenEqual() {
      assertEquals(inventoryAction1, inventoryAction2);
    }

    @Test
    @DisplayName("Test gold goals with different inventory")
    void testEqualInventoryActionWhenNotEqual() {
      assertNotEquals(inventoryAction1, inventoryAction3);
    }

    @Test
    @DisplayName("Test inventoryAction with objects from different classes")
    void testEqualsWithObjectsFromDifferentClasses() {
      assertNotEquals(inventoryAction1, "test");
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    InventoryAction inventoryAction = new InventoryAction("item");
    int expectedHashCode = Objects.hash("item");
    int actualHashCode = inventoryAction.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
