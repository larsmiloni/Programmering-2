package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.goals.GoalsRegister;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.HealthGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.InventoryGoal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GoalsRegisterTest {

  @Nested
  @DisplayName("Test add and remove goal")
  class AddAndRemoveGoal {
    GoalsRegister register;

    List<String> items;

    GoldGoal goldGoal;

    InventoryGoal inventoryGoal;

    @BeforeEach
    void setUp() {
      register = new GoalsRegister();
      goldGoal = new GoldGoal(10);
      items = new ArrayList<>();
      items.add("Sword");
      inventoryGoal = new InventoryGoal(items);
    }

    @Test
    @DisplayName("Test adding valid and removing valid goals")
    void testAddAndRemoveValidGoals() {
      register.addGoal(inventoryGoal);
      register.addGoal(goldGoal);
      assertEquals(2, register.getGoals().size());

      register.removeGoal(inventoryGoal);
      register.removeGoal(goldGoal);

      assertEquals(0, register.getGoals().size());
    }

    @Test
    @DisplayName("Test adding null throws Illegal Argument Exception")
    void testAddingNull() {
      assertThrows(IllegalArgumentException.class,
          () -> register.addGoal(null));
    }

    @Test
    @DisplayName("Test removing null throws Illegal Argument Exception")
    void testRemovingNull() {
      assertThrows(IllegalArgumentException.class,
          () -> register.removeGoal(null));
    }
  }

  @Test
  @DisplayName("Test clearRegister")
  void clearRegister() {
    GoalsRegister register = new GoalsRegister();
    register.addGoal(new GoldGoal(10));
    register.addGoal(new HealthGoal(19));

    assertEquals(2, register.getGoals().size());
    register.clearRegister();

    assertEquals(0, register.getGoals().size());
  }
  @Nested
  @DisplayName("Test isEmpty")
  class IsEmpty {

    GoalsRegister register;

    GoldGoal goldGoal;

    @BeforeEach
    void setUp() {
      register = new GoalsRegister();
      goldGoal = new GoldGoal(10);
    }

    @Test
    @DisplayName("Test isEmpty on empty register returns true")
    void testIsEmptyOnEmptyRegister() {
      assertTrue(register.isEmpty());
    }

    @Test
    @DisplayName("Test isEmpty on non empty register returns false")
    void testIsEmptyOnNonEmptyRegister() {
      register.addGoal(goldGoal);
      assertFalse(register.isEmpty());
    }
  }

  @Nested
  @DisplayName("Test contains")
  class Contains {

    GoalsRegister register;
    @BeforeEach
    void setUp()  {
    register = new GoalsRegister();
    register.addGoal(new GoldGoal(10));
    }

    @Test
    @DisplayName("Test contains with a goal that the register contains")
    void testContainsWithGoalThatIsContained() {
      assertTrue(register.contains(new GoldGoal(10)));
    }

    @Test
    @DisplayName("Test contains with a goal that the register does not contain")
    void testContainsWithGoalThatIsNotContained() {
      assertFalse(register.contains(new HealthGoal(10)));
    }
  }
}
