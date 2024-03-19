package edu.ntnu.mappe.gruppe50.model.data.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.actions.ActionFactory;
import edu.ntnu.mappe.gruppe50.model.data.actions.ActionType;
import edu.ntnu.mappe.gruppe50.model.data.actions.GoldAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.InventoryAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ActionFactoryTest {

  @Nested
  @DisplayName("Test buildAction with type as ActionType enum")
  class BuildActionActionType {

    ActionType type, type1;
    String value, negativeValue, itemValue, blankValue, invalidValue;

    @BeforeEach
    void setUp() {
      type = ActionType.of("Gold");
      type1 = ActionType.of("Inventory");

      value = "10";
      negativeValue = "-10";
      invalidValue = "m";
      blankValue = "";
      itemValue = "Sword";
    }

    @Test
    @DisplayName("Test buildAction on a positive integer action using valid parameters")
    void testBuildValidPositiveIntegerAction() {
      Action action = ActionFactory.buildAction(type, value);
      assertEquals(new GoldAction(10), action);
    }

    @Test
    @DisplayName("Test buildAction on a negative integer action using valid parameters")
    void testBuildValidNegativeIntegerAction() {
      Action action = ActionFactory.buildAction(type, negativeValue);
      assertEquals(new GoldAction(-10), action);
    }

    @Test
    @DisplayName("Test buildAction on item action using valid parameters")
    void testBuildValidItemAction() {
      Action action = ActionFactory.buildAction(type1, itemValue);

      assertEquals(new InventoryAction(itemValue), action);
    }

    @Test
    @DisplayName("Test buildAction for GoldAction using invalid value throws Illegal argument exception")
    void testBuildActionForGoldActionWithInvalidValue() {
      assertThrows(IllegalArgumentException.class,
          () -> ActionFactory.buildAction(type, invalidValue));
    }

    @Test
    @DisplayName("Test buildAction with null as type throws Illegal argument exception")
    void testBuildActionWithNull() {
      assertThrows(IllegalArgumentException.class,
          () -> ActionFactory.buildAction(null, value));
    }

    @Test
    @DisplayName("Test buildAction for GoldAction using blank value throws Illegal argument exception")
    void testBuildActionWithBlankValue() {
      assertThrows(IllegalArgumentException.class,
          () -> ActionFactory.buildAction(type, blankValue));
    }
  }
}


