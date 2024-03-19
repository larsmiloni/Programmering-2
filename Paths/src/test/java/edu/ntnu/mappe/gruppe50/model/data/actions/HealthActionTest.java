package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.actions.HealthAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HealthActionTest {

  @Nested
  class HealthActionConstructor {
    HealthAction healthAction;
    Player player;

    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
          .health(10)
          .score(10)
          .gold(10)
          .build();
    }
    @Test
    @DisplayName("Test Health Action is positive")
    void testHealthActionIsPositive() {
      healthAction = new HealthAction(10);
      healthAction.execute(player);

      assertEquals(player.getHealth(), 20);
    }

    @Test
    @DisplayName("Test Health Action is negative")
    void testHealthActionIsNegative() {
      healthAction = new HealthAction(-10);
      healthAction.execute(player);

      assertEquals(0, player.getHealth());
    }

    @Test
    @DisplayName("Test throws Illegal Argument Exception if Health Action is zero")
    void testHealthActionIsZeroThrowsIllegalArgumentException() {
      healthAction = new HealthAction(0);

      assertThrows(IllegalArgumentException.class, () -> healthAction.execute(player));
    }

    @Test
    @DisplayName("Test throws Illegal Argument Exception if player is null")
    void testIllegalArgumentExceptionIsThrownIfPlayerIsNull() {
      healthAction = new HealthAction(10);

      assertThrows(IllegalArgumentException.class, () -> healthAction.execute(null));
    }
  }

  @Nested
  @DisplayName("Equals")
  class Equals {
    HealthAction healthAction1, healthAction2, healthAction3;

    @BeforeEach
    void setUp() {
      healthAction1 = new HealthAction(10);
      healthAction2 = new HealthAction(10);
      healthAction3 = new HealthAction(20);
    }

    @Test
    @DisplayName("Test healthAction with the same health are equal")
    void testEqualHealthAction() {
      assertEquals(healthAction1, healthAction2);
    }

    @Test
    @DisplayName("Test healthAction with different health are unequal")
    void testUnequalHealthAction() {
      assertNotEquals(healthAction1, healthAction3);
    }

    @Test
    @DisplayName("Test healthAction with objects from different classes")
    void testHealthActionWithObjectsFromDifferentClasses() {
      assertNotEquals(healthAction1, "test");
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    HealthAction healthAction = new HealthAction(10);
    int expectedHashCode = Objects.hash(10);
    int actualHashCode = healthAction.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
