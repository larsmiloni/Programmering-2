package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GoldGoalTest {

  @Nested
  @DisplayName("Gold goal constructor")
  class GoldGoalConstructor {

    GoldGoal goldGoal;

    @Test
    @DisplayName("Test constructor with valid gold goal")
    void testValidGoldGoal() {
      goldGoal = new GoldGoal(10);
      assertEquals(10, goldGoal.getValue());
    }

    @Test
    @DisplayName("Test constructor with zero")
    void testZeroGoldGoal() {
      goldGoal = new GoldGoal(0);
      assertEquals(0, goldGoal.getValue());
    }

    @Test
    @DisplayName("Test constructor with gold goal less than zero")
    void testConstructorWithGoldGoalLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> goldGoal = new GoldGoal(-10));
    }
  }

  @Nested
  @DisplayName("Gold goal is fulfilled")
  class GoldGoalIsFulfilled {
    Player player;
    GoldGoal goldGoal1, goldGoal2;

    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
        .health(10)
        .score(10)
        .gold(10)
        .build();

      goldGoal1 = new GoldGoal(20);
      goldGoal2 = new GoldGoal(5);
    }

    @Test
    @DisplayName("Test isFulfilled with null player")
    void testGoldGoalIsFulfilledWithNullPlayer() {
      assertThrows(IllegalArgumentException.class, () -> goldGoal1.isFulfilled(null));
    }

    @Test
    @DisplayName("Test isFulfilled when it is not fulfilled")
    void testIsFulfilledWhenItIsNotFulfilled() {
      assertFalse(goldGoal1.isFulfilled(player));
    }

    @Test
    @DisplayName("Test isFulfilled when it is fulfilled")
    void testIsFulfilledWhenItIsFulfilled() {
      assertTrue(goldGoal2.isFulfilled(player));
    }
  }

  @Test
  @DisplayName("Test getType")
  void testGetType() {
    GoldGoal goldGoal = new GoldGoal(10);
    assertEquals(GoalType.GOLD, goldGoal.getType());
  }

  @Nested
  @DisplayName("Equals")
  class Equals {
    GoldGoal goldGoal1, goldGoal2, goldGoal3;

    @BeforeEach
    void setUp() {
      goldGoal1 = new GoldGoal(10);
      goldGoal2 = new GoldGoal(10);
      goldGoal3 = new GoldGoal(20);
    }

    @Test
    @DisplayName("Test gold goals with the same minimumGold are equal")
    void testEqualGoldGoals() {
      assertEquals(goldGoal1, goldGoal2);
    }

    @Test
    @DisplayName("Test gold goals with different minimumGold are unequal")
    void testUnequalGold() {
      assertNotEquals(goldGoal1, goldGoal3);
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    GoldGoal goldGoal = new GoldGoal(10);
    int expectedHashCode = Objects.hash(10);
    int actualHashCode = goldGoal.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
