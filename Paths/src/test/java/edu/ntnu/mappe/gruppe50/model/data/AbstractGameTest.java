package edu.ntnu.mappe.gruppe50.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.mappe.gruppe50.model.data.Game;
import edu.ntnu.mappe.gruppe50.model.data.LinkRegister;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.Player;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AbstractGameTest {

  Game game;
  Passage passage;
  Player player;
  Story story;
  List<Goal> goals;

  LinkRegister links;

  @BeforeEach
  void setUp() {
    passage = new Passage("title", "content");
    player = new Player.Builder("testName")
        .health(10)
        .score(10)
        .gold(10)
        .build();
    story = new Story("title", passage);
    goals = new ArrayList<>();
    links = new LinkRegister();
    game = new Game(player, story, goals, links);
  }

  @Nested
  @DisplayName("Test goal list mutators")
  class GoalListMutators {


    @Test
    @DisplayName("Test adding valid goal")
    void testAddingAndRemovingValidGoal() {
      game.addGoal(new GoldGoal(10));

      assertEquals(1, game.getGoals().size());

      game.removeGoal(new GoldGoal(10));

      assertEquals(0, game.getGoals().size());
    }

    @Test
    @DisplayName("Test adding null as a goal throws Illegal argument exception")
    void testAddingNull() {
      assertThrows(IllegalArgumentException.class,
          () -> game.addGoal(null));
    }

    @Test
    @DisplayName("Test adding duplicate goal throws Illegal argument exception")
    void testAddingDuplicateGoal() {
      game.addGoal(new GoldGoal(10));

      assertThrows(IllegalArgumentException.class,
          () -> game.addGoal(new GoldGoal(10)));
    }

    @Test
    @DisplayName("Test removing null as a goal throws Illegal argument exception")
    void testRemovingNull() {
      assertThrows(IllegalArgumentException.class,
          () -> game.removeGoal(null));
    }
  }

    @Test
    @DisplayName("Test adding null in update method throws Illegal argument exception")
    void testAddingInUpdate() {
      assertThrows(IllegalArgumentException.class,
          () -> game.update(null));
    }
  }

