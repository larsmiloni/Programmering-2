package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.HealthGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HealthGoalTest {

  @Nested
  @DisplayName("Health goal constructor")
  class HealthGoalConstructor {

    HealthGoal healthGoal;

    @Test
    @DisplayName("Test constructor with valid health goal")
    void testValidHealthGoal() {
      healthGoal = new HealthGoal(10);
      assertEquals(10, healthGoal.getValue());
    }

    @Test
    @DisplayName("Test constructor with zero")
    void testZeroHealthGoal() {
      healthGoal = new HealthGoal(0);
      assertEquals(0, healthGoal.getValue());
    }

    @Test
    @DisplayName("Test constructor with health goal less than zero")
   void testConstructorWithHealthGoalLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> healthGoal = new HealthGoal(-10));
    }
  }

  @Nested
  @DisplayName("Health goal is fulfilled")
  class HealthGoalIsFulfilled {
    Player player;
    HealthGoal healthGoal1, healthGoal2;

    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
        .health(10)
        .score(10)
        .gold(10)
        .build();

      healthGoal1 = new HealthGoal(20);
      healthGoal2 = new HealthGoal(5);
    }

    @Test
    @DisplayName("Test health goal is fulfilled with null player")
    void testHealthGoalIsFulfilledWithNullPlayer() {
      assertThrows(IllegalArgumentException.class, () -> healthGoal1.isFulfilled(null));
    }

    @Test
    @DisplayName("Test health goal is fulfilled when it is not fulfilled")
    void testHealthGoalIsFulfilledWhenItIsNotFulfilled() {
      assertFalse(healthGoal1.isFulfilled(player));
    }

    @Test
    @DisplayName("Test health goal is fulfilled when it is fulfilled")
    void testHealthGoalIsFulfilledWhenItIsFulfilled() {
      assertTrue(healthGoal2.isFulfilled(player));
    }
  }

  @Test
  @DisplayName("Test getType")
  void testGetType() {
    HealthGoal healthGoal = new HealthGoal(10);
    assertEquals(GoalType.HEALTH, healthGoal.getType());
  }

  @Nested
  @DisplayName("Equals")
  class Equals {

    HealthGoal healthGoal1, healthGoal2, healthGoal3;

    @BeforeEach
    void setUp() {
      healthGoal1 = new HealthGoal(10);
      healthGoal2 = new HealthGoal(10);
      healthGoal3 = new HealthGoal(20);
    }

    @Test
    @DisplayName("Test health goals with the same minimumHealth are equal")
    void testEqualHealthGoals() {
      assertEquals(healthGoal1, healthGoal1);
    }

    @Test
    @DisplayName("Test health goals with different minimumHealth are unequal")
    void testUnequalHealth() {
      assertNotEquals(healthGoal1, healthGoal3);
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    HealthGoal healthGoal = new HealthGoal(10);
    int expectedHashCode = Objects.hash(10);
    int actualHashCode = healthGoal.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
