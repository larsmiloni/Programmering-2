package edu.ntnu.mappe.gruppe50.model.data.goals;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoalType;
import edu.ntnu.mappe.gruppe50.model.data.goals.ScoreGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreGoalTest {

  @Nested
  @DisplayName("Score goal constructor")
  class ScoreGoalConstructor {

    ScoreGoal scoreGoal;

    @Test
    @DisplayName("Test constructor with valid score goal")
    void testValidScoreGoal() {
      scoreGoal = new ScoreGoal(10);
      assertEquals(10, scoreGoal.getValue());
    }

    @Test
    @DisplayName("Test constructor with zero")
    void testZeroScoreGoal() {
      scoreGoal = new ScoreGoal(0);
      assertEquals(0, scoreGoal.getValue());
    }

    @Test
    @DisplayName("Test constructor with score goal less than zero")
    void testConstructorWithScoreGoalLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> scoreGoal = new ScoreGoal(-10));
    }
  }

  @Nested
  @DisplayName("Score goal is fulfilled")
  class ScoreGoalIsFulfilled {
    Player player;
    ScoreGoal scoreGoal1, scoreGoal2;

    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
        .health(10)
        .score(10)
        .gold(10)
        .build();

      scoreGoal1 = new ScoreGoal(20);
      scoreGoal2 = new ScoreGoal(5);
    }

    @Test
    @DisplayName("Test score goal is fulfilled with null player")
    void testScoreGoalIsFulfilledWithNullPlayer() {
      assertThrows(IllegalArgumentException.class, () -> scoreGoal1.isFulfilled(null));
    }

    @Test
    @DisplayName("Test score goal is fulfilled when it is not fulfilled")
    void testScoreGoalIsFulfilledWhenItIsNotFulfilled() {
      assertFalse(scoreGoal1.isFulfilled(player));
    }

    @Test
    @DisplayName("Test score goal is fulfilled when it is fulfilled")
    void testScoreGoalIsFulfilledWhenItIsFulfilled() {
      assertTrue(scoreGoal2.isFulfilled(player));
    }
  }

  @Test
  @DisplayName("Test getType")
  void testGetType() {
    ScoreGoal scoreGoal = new ScoreGoal(10);
    assertEquals(GoalType.SCORE, scoreGoal.getType());
  }

  @Nested
  @DisplayName("Equals")
  class Equals {

    ScoreGoal scoreGoal1, scoreGoal2, scoreGoal3;

    @BeforeEach
    void setUp() {
      scoreGoal1 = new ScoreGoal(10);
      scoreGoal2 = new ScoreGoal(10);
      scoreGoal3 = new ScoreGoal(20);
    }

    @Test
    @DisplayName("Test score goals with the same minimumScore are equal")
    void testEqualScoreGoals() {
      assertEquals(scoreGoal1, scoreGoal2);
    }

    @Test
    @DisplayName("Test score goals with different minimumScore are unequal")
    void testUnequalScore() {
      assertNotEquals(scoreGoal1, scoreGoal3);
    }
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    ScoreGoal scoreGoal = new ScoreGoal(10);
    int expectedHashCode = Objects.hash(10);
    int actualHashCode = scoreGoal.hashCode();
    assertEquals(expectedHashCode, actualHashCode);
  }
}
