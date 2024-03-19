package edu.ntnu.mappe.gruppe50.model.data.actions;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.actions.ScoreAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreActionTest {

  @Nested
  class ScoreActionConstructor {

    ScoreAction scoreAction;
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
    @DisplayName("Test Score Action is positive")
    void testScoreActionIsPositive() {
      scoreAction = new ScoreAction(10);
      scoreAction.execute(player);

      assertEquals(player.getScore(), 20);
    }

    @Test
    @DisplayName("Test Score Action is negative")
    void testScoreActionIsNegative() {
      scoreAction = new ScoreAction(-10);
      scoreAction.execute(player);

      assertEquals(player.getScore(), 0);
    }

    @Test
    @DisplayName("Test Score Action is zero throws Illegal Argument Exception")
    void testScoreActionIsZeroThrowsIllegalArgumentException() {
      scoreAction = new ScoreAction(0);

      assertThrows(IllegalArgumentException.class, () -> scoreAction.execute(player));
    }

    @Test
    @DisplayName("Test Illegal Argument Exception is thrown if player is null")
    void testIllegalArgumentExceptionIsThrownIfPlayerIsNull() {
      scoreAction = new ScoreAction(10);

      assertThrows(IllegalArgumentException.class, () -> scoreAction.execute(null));
    }
  }

  @Nested
  @DisplayName("Equals")
  class Equals {
    ScoreAction scoreAction1, scoreAction2, scoreAction3;

    @BeforeEach
    void setUp() {
      scoreAction1 = new ScoreAction(10);
      scoreAction2 = new ScoreAction(10);
      scoreAction3 = new ScoreAction(20);
    }

    @Test
    @DisplayName("Test scoreAction with the same score are equal")
    void testEqualScoreAction() {
      assertEquals(scoreAction1, scoreAction2);
    }

    @Test
    @DisplayName("Test scoreAction with different score are unequal")
    void testUnequalScoreAction() {
      assertNotEquals(scoreAction1, scoreAction3);
    }

    @Test
    @DisplayName("Test scoreAction with objects from different classes")
    void testScoreActionWithObjectsFromDifferentClasses() {
      assertNotEquals(scoreAction1, "efwewf");
    }

  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    ScoreAction scoreAction = new ScoreAction(10);
    int expectedHashCode = Objects.hash(10);
    int actualHashCode = scoreAction.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
