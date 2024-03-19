package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalFactory;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.InventoryGoal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class GoalFactoryTest {

  @Nested
  @DisplayName("Test buildGoal with type converted using of-method from enum")
  class BuildGoalUsingEnumOf {

    String type, type1, invalidType, value, negativeValue, zeroValue, invalidValue, itemValue;

    @BeforeEach
    void setUp() {
      type = "Gold";
      type1 = "Inventory";
      invalidType = "gold";

      value = "10";
      negativeValue = "-10";
      zeroValue = "0";
      invalidValue = "m";
      itemValue = "[Sword, Wand]";
    }

    @Test
    @DisplayName("Test buildGoal on a positive integer goal using valid parameters")
    void testBuildValidPositiveIntegerGoal() {
      GoalType goalType = GoalType.of(type);
      Goal goal = GoalFactory.buildGoal(goalType, value);

      assertEquals(new GoldGoal(10), goal);
    }

    @Test
    @DisplayName("Test buildGoal on a zero integer goal using valid parameters")
    void testBuildValidZeroIntegerGoal() {
      GoalType goalType = GoalType.of(type);
      Goal goal = GoalFactory.buildGoal(goalType, zeroValue);

      assertEquals(new GoldGoal(0), goal);
    }

    @Test
    @DisplayName("Test buildGoal on a negative integer goal throws Illegal argument exception")
    void testBuildValidNegativeInterGoal() {
      GoalType goalType = GoalType.of(type);

      assertThrows(IllegalArgumentException.class, () ->
          GoalFactory.buildGoal(goalType, negativeValue)
      );
    }

    @Test
    @DisplayName("Test buildGoal on item goal using valid parameters")
    void testBuildValidItemGoal() {
      List<String> items = new ArrayList<>();
      items.add("Wand");
      items.add("Sword");
      GoalType goalType = GoalType.of(type1);
      Goal goal = GoalFactory.buildGoal(goalType, itemValue);

      assertEquals(new InventoryGoal(items), goal);
    }

    @Test
    @DisplayName("Test of-method with invalid string throws Illegal Argument Exception")
    void testBuildGoalWithInvalidType() {
      assertThrows(IllegalArgumentException.class, () -> GoalType.of(invalidType));
    }

    @Nested
    @DisplayName("Test buildGoal with type as GoalType enum")
    class BuildGoalGoalType {

      GoalType type, type1, nullType;
      String value, itemValue, noValue, nonParsableString;

      @BeforeEach
      void setUp() {
        type = GoalType.GOLD;
        type1 = GoalType.INVENTORY;
        nullType = null;
        value = "10";
        itemValue = "[Sword, Wand]";
        noValue = "";
        nonParsableString = "m10";
      }

      @Test
      @DisplayName("Test buildGoal on integer goal using valid parameters")
      void testBuildValidIntegerGoal() {
        Goal goal = GoalFactory.buildGoal(type, value);
        assertEquals(new GoldGoal(10), goal);
      }

      @Test
      @DisplayName("Test buildGoal on item goal using valid parameters")
      void testBuildValidItemGoal() {
        List<String> items = new ArrayList<>();
        items.add("Wand");
        items.add("Sword");
        Goal goal = GoalFactory.buildGoal(type1, itemValue);

        assertEquals(new InventoryGoal(items), goal);
      }

      @Test
      @DisplayName("Test inserting null as type throws Illegal argument exception")
      void testInsertingNull() {
        assertThrows(IllegalArgumentException.class, () -> GoalFactory.buildGoal(nullType, value));
      }

      @Test
      @DisplayName("Test inserting blank as value throws Illegal argument exception")
      void testInsertingBlank() {
        assertThrows(IllegalArgumentException.class, () -> GoalFactory.buildGoal(type, noValue));
      }

      @Test
      @DisplayName("Test inserting non parsable string to integer throws Illegal argument exception")
      void testNonParsableValue() {
        assertThrows(IllegalArgumentException.class, () -> GoalFactory.buildGoal(type, nonParsableString));
      }

      @Test
      @DisplayName("Test inserting single bracket as value throws throws String index out of bounds exception")
      void testSingleBracketInventory() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> GoalFactory.buildGoal(type1, "["));
      }
    }
  }
}
