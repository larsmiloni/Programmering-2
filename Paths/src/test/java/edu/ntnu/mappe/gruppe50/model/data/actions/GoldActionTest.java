package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.actions.GoldAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GoldActionTest {

  @Nested
  class GoldActionConstructor {

    GoldAction goldAction;
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
    @DisplayName("Test Gold Action is positive")
    void testGoldActionIsPositive() {
      goldAction = new GoldAction(10);
      goldAction.execute(player);

      assertEquals(player.getGold(), 20);
    }

    @Test
    @DisplayName("Test Gold Action is negative")
    void testGoldActionIsNegative() {
      goldAction = new GoldAction(-10);
      goldAction.execute(player);

      assertEquals(player.getGold(), 0);
    }

    @Test
    @DisplayName("Test Gold Action is zero throws Illegal Argument Exception")
    void testGoldActionIsZeroThrowsIllegalArgumentException() {
      goldAction = new GoldAction(0);

      assertThrows(IllegalArgumentException.class, () -> goldAction.execute(player));
    }

    @Test
    @DisplayName("Test Illegal Argument Exception is thrown if player is null")
    void testIllegalArgumentExceptionIsThrownIfPlayerIsNull() {
      goldAction = new GoldAction(10);

      assertThrows(IllegalArgumentException.class, () -> goldAction.execute(null));
    }
  }

  @Nested
  @DisplayName("Test equals")
  class TestEquals {
    GoldAction goldAction1, goldAction2, goldAction3;

    @BeforeEach
    void setUp() {
      goldAction1 = new GoldAction(10);
      goldAction2 = new GoldAction(10);
      goldAction3 = new GoldAction(20);
    }

    @Test
    @DisplayName("Test goldAction with the same gold are equal")
    void testEqualsWhenEqual() {
      assertEquals(goldAction2, goldAction1);
    }

    @Test
    @DisplayName("Test goldAction with different gold are unequal")
    void testEqualsWhenNotEqual() {
      assertNotEquals(goldAction1, goldAction3);
    }

    @Test
    @DisplayName("Test goldAction with objects from different classes")
    void testGoldActionWithObjectsFromDifferentClasses() {
      assertNotEquals(goldAction1, "test");
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    GoldAction goldAction = new GoldAction(10);
    int expectedHashCode = Objects.hash(10);
    int actualHashCode = goldAction.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
