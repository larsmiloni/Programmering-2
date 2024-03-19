package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.InventoryGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryGoalTest {

  @Nested
  class InventoryGoalConstructor {

    InventoryGoal inventoryGoal;

    @Test
    @DisplayName("Test constructor with valid inventory goal")
    void testValidGoldGoal() {
      List<String> items = new ArrayList<>();
      items.add("Sword");
      inventoryGoal = new InventoryGoal(items);
      assertEquals(1, inventoryGoal.getValue().size());
    }

    @Test
    @DisplayName("Test constructor with null as mandatory items throws IllegalArgumentException")
    void testConstructorWithNull() {
    assertThrows(IllegalArgumentException.class, () ->  inventoryGoal = new InventoryGoal(null));
    }

    @Test
    @DisplayName("Test constructor with a blank list as mandatory items throws IllegalArgumentException")
    void testConstructorWithBlankList() {
      assertThrows(IllegalArgumentException.class, () ->  inventoryGoal = new InventoryGoal(new ArrayList<>()));
    }
  }

  @Nested
  class InventoryGoalIsFulfilled {
    Player player;
    InventoryGoal inventoryGoal;
    List<String> mandatoryItems;


    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
        .health(5)
        .score(5)
        .gold(5)
        .build();

      mandatoryItems = new ArrayList<>();
      mandatoryItems.add("Knife");
      mandatoryItems.add("Sword");
      inventoryGoal = new InventoryGoal(mandatoryItems);
    }

    @Test
    @DisplayName("Test isFulfilled with null player")
    void testIsFulfilledWithNullPlayer() {
      assertThrows(IllegalArgumentException.class, () -> inventoryGoal.isFulfilled(null));
    }


    @Test
    @DisplayName("Test isFulfilled when it is not fulfilled")
    void testIsFulfilledWhenItIsNotFulfilled() {
      player.addToInventory("Knife");
      player.addToInventory("Sword");
      player.addToInventory("Dagger");

      assertTrue(inventoryGoal.isFulfilled(player));
    }

    @Test
    @DisplayName("Test isFulfilled when it is fulfilled")
    void testIsFulfilledWhenItIsFulfilled() {
      player.addToInventory("Knife");
      assertFalse(inventoryGoal.isFulfilled(player));
    }
  }

  @Nested
  @DisplayName("Test equals")
  class Equals {

    InventoryGoal inventoryGoal1, inventoryGoal2, inventoryGoal3;

    List<String> itemPool1, itemPool2, itemsPool3;

    @BeforeEach
    void setUp() {
      itemPool1 = new ArrayList<>();
      itemPool2 = new ArrayList<>();
      itemsPool3 = new ArrayList<>();

      itemPool1.add("Sword");
      itemPool1.add("Wand");

      itemPool2.add("Wand");
      itemPool2.add("Sword");

      itemsPool3.add("Sword");

      inventoryGoal1 = new InventoryGoal(itemPool1);
      inventoryGoal2 = new InventoryGoal(itemPool1);
      inventoryGoal3 = new InventoryGoal(itemsPool3);
    }
    @Test
    @DisplayName("Test getType")
    void testGetType() {
      List<String> list = new ArrayList<>();
      list.add("item");
      InventoryGoal inventoryGoal = new InventoryGoal(list);
      assertEquals(GoalType.INVENTORY, inventoryGoal.getType());
    }

    @Test
    @DisplayName("Test inventory goals with the same inventory are equal")
    void testEqualInventoryGoals() {
      assertEquals(inventoryGoal1, inventoryGoal2);
    }
    
    @Test
    @DisplayName("Test inventory goals with the same inventory, but different item order are equal")
    void testEqualInventoryGoalsWithDifferentOrder() {
      assertEquals(inventoryGoal1, new InventoryGoal(itemPool2));
    }
    @Test
    @DisplayName("Test inventory goals with different inventory are unequal")
    void testUnequalInventory() {
      assertNotEquals(inventoryGoal1, inventoryGoal3);
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    List<String> mandatoryItems1 = new ArrayList<>();
    mandatoryItems1.add("item1");
    mandatoryItems1.add("item2");
    List<String> mandatoryItems2 = new ArrayList<>();
    mandatoryItems2.add("item1");
    mandatoryItems2.add("item2");
    InventoryGoal inventoryGoal = new InventoryGoal(mandatoryItems1);

    int expectedHashCode = Objects.hash(mandatoryItems2);
    int actualHashCode = inventoryGoal.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
