package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.actions.GoldAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.HealthAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.InventoryAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.ScoreAction;
import edu.ntnu.mappe.gruppe50.model.customexceptions.NoMatchingPassageException;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

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
  }
    @Nested
  class GameConstructor {

    @BeforeEach
    void setUp() {

      Goal goal = new GoldGoal(10);
      goals.add(goal);
    }

    @Test
    @DisplayName("Test constructor with valid arguments")
    void testConstructorWithValidArguments() {
      game = new Game(player, story, goals, links);

      assertEquals(game.getPlayer(), player);
      assertEquals(game.getStory(), story);
      assertEquals(game.getGoals(), goals);
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if player is null")
    public void testConstructorThrowsIllegalArgumentExceptionIfPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () -> game = new Game(null, story, goals, links));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if story is null")
    public void testConstructorThrowsIllegalArgumentExceptionIfStoryIsNull() {
      assertThrows(IllegalArgumentException.class, () -> game = new Game(player, null, goals, links));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if goals is null")
    public void testConstructorThrowsIllegalArgumentExceptionIfGoalsIsNull() {
      assertThrows(IllegalArgumentException.class, () -> game = new Game(player, story, null, links));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if links is null")
    public void testConstructorThrowsIllegalArgumentExceptionIfLinksIsNull() {
      assertThrows(IllegalArgumentException.class, () -> game = new Game(player, story, goals, null));
    }
  }

  @Test
  @DisplayName("Test Begin returns opening passage")
  public void testGetOpeningPassage() {
    game = new Game(player, story, goals, links);
    assertEquals(passage, game.begin());
  }

  @Nested
  class GameGo{
    Link link;

    @BeforeEach
    void setUp() {
      link = new Link(passage.getTitle(), passage.getTitle());
      game = new Game(player, story, goals, links);
    }

    @Test
    @DisplayName("Test Go returns correct passage for valid link")
    void testGoReturnsCorrectPassageWithValidLink() throws NoMatchingPassageException {
      assertEquals(passage, game.go(link));
    }

    @Test
    @DisplayName("Test Go throws Illegal Argument Exception if Link is null")
    void testGoThrowsIllegalArgumentExceptionIfLinkIsNull() {
      assertThrows(IllegalArgumentException.class, () -> game.go(null));
    }

    @Test
    @DisplayName("Test Go throws No Matching Link Exception for invalid link")
    void testGoThrowsNoMatchingLinkExceptionIfLinkIsInvalid() {
      Link link1 = new Link("testText", "testReference");
      passage.addLink(link1);

      assertThrows(NoMatchingPassageException.class, () -> game.go(link1));
    }

    @Test
    @DisplayName("Test go executes all actions")
    void testGoExecutesAllActions() throws NoMatchingPassageException {
      Link link1 = new Link(passage.getTitle(), passage.getTitle());
      link1.addAction(new HealthAction(10));
      link1.addAction(new ScoreAction(-10));
      link1.addAction(new GoldAction(5));
      link1.addAction(new InventoryAction("Rusty sword"));

      game.go(link1);
      assertEquals(20, player.getHealth());
      assertEquals(0, player.getScore());
      assertEquals(15, player.getGold());
      assertEquals(1, player.getInventory().size());
    }

    @Test
    @DisplayName("Test Go method increments visitedLinks by 1")
    void testGoIncrementsVisitedLinks() throws NoMatchingPassageException {
      assertEquals(0, game.getVisitedLinks().getLinks().size());

      game.go(link);

      assertEquals(1, game.getVisitedLinks().getLinks().size());
    }

    @Test
    @DisplayName("Test visiting the same link twice will increment visitedLinks by 2")
    void testGoToSameLink() throws NoMatchingPassageException {
      game.go(link);

      assertEquals(1, game.getVisitedLinks().getLinks().size());

      game.go(link);

      assertEquals(2, game.getVisitedLinks().getLinks().size());
    }
  }

  @Nested
  class GameAccessibleLinks {
    Link link1, link2;

    @BeforeEach
    void setUp() {
      game = new Game(player, story, goals, links);

      link1 = new Link(passage.getTitle(), passage.getTitle());
      link2 = new Link(passage.getTitle(), "reference");
      passage.addLink(link1);
      passage.addLink(link2);
    }

    @Test
    @DisplayName("Test getAccessibleLinks with zero accessible links")
    void testZeroAccessibleLinks() {
      link1.addGoal(new GoldGoal(20));
      link2.addGoal(new GoldGoal(20));

      assertEquals(0, game.getAccessibleLinks(passage, player).size());
    }

    @Test
    @DisplayName("Test getAccessibleLinks with multiple accessible links")
    void testMultipleAccessibleLinks() {
      link1.addGoal(new GoldGoal(5));
      link2.addGoal(new GoldGoal(5));

      assertEquals(2, game.getAccessibleLinks(passage, player).size());
    }
  }
  @Nested
  class GameSavedPassages {
    Passage passage1, passage2;

    Link link12, link23;
    @BeforeEach
    void setUp() {
      passage1 = new Passage("title1", "content1");
      passage2 = new Passage("title2", "content2");

      link12 = new Link(passage1.getTitle(), passage1.getTitle());
      passage.addLink(link12);

      link23 = new Link(passage2.getTitle(), passage2.getTitle());
      passage1.addLink(link23);

      game = new Game(player, story, goals, links);

      story.addPassage(passage1);
      story.addPassage(passage2);
    }
    @Test
    @DisplayName("Test getSavedPassage when one link has been visited")
    void testOneLinkVisited() throws NoMatchingPassageException {
      game.go(link12);

      assertEquals(passage1, game.getSavedPassage());
    }

    @Test
    @DisplayName("Test getSavedPassage when two links have been visited")
    void testTwoLinksVisited() throws NoMatchingPassageException {
      game.go(link12);
      game.go(link23);

      assertEquals(passage2, game.getSavedPassage());
    }

    @Test
    @DisplayName("Test getSavedPassage when no links have been visited returns openingPassage")
    void testNoLinksVisited() {
      assertEquals(passage, game.getSavedPassage());
    }
  }

  @Test
  @DisplayName("Test isNewPlayThrough")
  void testIsNewPlayThrough() {
    passage = new Passage("title", "content");
    player = new Player.Builder("testName")
      .health(10)
      .score(10)
      .gold(10)
      .build();
    story = new Story("title", passage);
    goals = new ArrayList<>();
    Link link = new Link("testText", "testReference");
    LinkRegister linksWithLink = new LinkRegister();
    linksWithLink.addLink(link);
    LinkRegister linksWithoutLink = new LinkRegister();
    Game gameWithLinks = new Game(player, story, goals, linksWithLink);
    Game gameWithoutLinks = new Game(player, story, goals, linksWithoutLink);

    assertFalse(gameWithLinks.isNewPlayThrough());
    assertTrue(gameWithoutLinks.isNewPlayThrough());
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    passage = new Passage("title", "content");
    player = new Player.Builder("testName").build();
    story = new Story("test title", passage);
    goals = new ArrayList<>();
    links = new LinkRegister();
    game = new Game(player, story, goals, links);
    assertEquals("test_title" + "\n" + "testName", game.toString());
  }
}


