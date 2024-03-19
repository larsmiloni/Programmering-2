package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

  @Test
  @DisplayName("Test player builder default values.")
  void testPlayerBuilderDefaultValues() {
    Player player = new Player.Builder("testName").build();

    assertEquals(1, player.getHealth());
    assertEquals(0, player.getScore());
    assertEquals(0, player.getGold());
  }

  @Nested
  class PlayerConstructor {

    Player player;

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
      player = new Player.Builder("testName")
        .health(10)
        .score(20)
        .gold(30)
        .build();

      assertEquals("testName", player.getName());
      assertEquals(10, player.getHealth());
      assertEquals(20, player.getScore());
      assertEquals(30, player.getGold());
    }

    @Test
    @DisplayName("Test constructor with name as null")
    void testConstructorWithNameAsNull() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player.Builder(null)
        .build());
    }

    @Test
    @DisplayName("TestConstructorWithBlankName")
    void testConstructorWithBlankName() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player.Builder("")
        .build());
    }

    @Test
    @DisplayName("TestConstructorWithStartingHealthBelowZero")
    void testConstructorWithStartingHealthBelowZero() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player.Builder(null)
        .health(-10)
        .build());
    }

    @Test
    @DisplayName("TestConstructorWithStartingHealthAtZero")
    void testConstructorWithStartingHealthAtZero() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player.Builder("testName")
        .health(0)
        .build());
    }

    @Test
    @DisplayName("TestConstructorWithScoreBelowZero")
    void testConstructorWithScoreBelowZero() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player.Builder("testName")
        .score(-10)
        .build());
    }

    @Test
    @DisplayName("TestConstructorWithGoldBelowZero")
    void testConstructorWithGoldBelowZero() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player.Builder("testName")
            .gold(-10)
            .build());
    }
  }

  @Nested
  class PlayerSetters {

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
    @DisplayName("Test setting health to positive value")
    void testSettingHealthToPositiveValue() {
      player.setHealth(20);

      assertEquals(20, player.getHealth());
    }

    @Test
    @DisplayName("Test setting health to zero")
    void testSettingHealthToZero() {
      player.setHealth(0);

      assertEquals(0, player.getHealth());
    }

    @Test
    @DisplayName("Test setting health to negative value")
    void testSettingHealthToNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.setHealth(-10));
    }

    @Test
    @DisplayName("Test setting score to positive value")
    void testSettingScoreToPositiveValue() {
      player.setScore(20);

      assertEquals(20, player.getScore());
    }

    @Test
    @DisplayName("Test setting score to zero")
    void testSettingScoreToZero() {
      player.setScore(0);

      assertEquals(0, player.getScore());
    }

    @Test
    @DisplayName("Test setting score to negative value")
    void testSettingScoreToNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.setScore(-10));
    }

    @Test
    @DisplayName("Test setting gold to positive value")
    void testSettingGoldToPositiveValue() {
      player.setGold(20);

      assertEquals(20, player.getGold());
    }

    @Test
    @DisplayName("Test setting gold to zero")
    void testSettingGoldToZero() {
      player.setGold(0);

      assertEquals(0, player.getGold());
    }

    @Test
    @DisplayName("Test setting gold to negative value")
    void testSettingGoldToNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.setGold(-10));
    }
  }

  @Nested
  class PlayerAdders {

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
    @DisplayName("Test adding positive health")
    void testAddingPositiveHealth() {
      player.addHealth(20);
      assertEquals(30, player.getHealth());
    }

    @Test
    @DisplayName("Test adding negative health")
    void testAddingNegativeHealth() {
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-1));
    }

    @Test
    @DisplayName("Test adding zero health")
    void testAddingZeroHealth() {
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(0));
    }

    @Test
    @DisplayName("Test adding positive score")
    void testAddingPositiveScore() {
      player.addScore(20);
      assertEquals(30, player.getScore());
    }

    @Test
    @DisplayName("Test adding negative score")
    void testAddingNegativeScore() {
      assertThrows(IllegalArgumentException.class, () -> player.addScore(-1));
    }

    @Test
    @DisplayName("Test adding zero score")
    void testAddingZeroScore() {
      assertThrows(IllegalArgumentException.class, () -> player.addScore(0));
    }

    @Test
    @DisplayName("Test adding positive gold")
    void testAddingPositiveGold() {
      player.addGold(20);
      assertEquals(30, player.getGold());
    }

    @Test
    @DisplayName("Test adding negative gold")
    void testAddingNegativeGold() {
      assertThrows(IllegalArgumentException.class, () -> player.addGold(-1));
    }

    @Test
    @DisplayName("Test adding zero gold")
    void testAddingZeroGold() {
      assertThrows(IllegalArgumentException.class, () -> player.addGold(0));
    }

    @Test
    @DisplayName("Test adding valid item")
    void testAddingValidItem() {
      String item = "testItem";
      player.addToInventory(item);
      assertTrue(player.getInventory().contains(item));
    }

    @Test
    @DisplayName("Test adding null as item")
    void testAddingNullAsItem() {
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(null));
    }

    @Test
    @DisplayName("Test adding blank item")
    void testAddBlankItem() {
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(""));
    }
  }

  @Nested
  class PlayerRemovers {

    Player player;

    @BeforeEach
    void setUp() {
      player = new Player.Builder("testName")
        .health(20)
        .score(10)
        .gold(10)
        .build();
    }

    @Test
    @DisplayName("Test removing positive health")
    void testRemovingPositiveHealth() {
      player.removeHealth(5);
      assertEquals(15, player.getHealth());
    }

    @Test
    @DisplayName("Test removing more health than the player has")
    void testRemovingMoreHealthThanThePlayerHas() {
      player.removeHealth(100);
      assertEquals(0, player.getHealth());
    }

    @Test
    @DisplayName("Test removing negative health")
    void testRemovingNegativeHealth() {
      assertThrows(IllegalArgumentException.class, () -> player.removeHealth(-1));
    }

    @Test
    @DisplayName("Test removing zero health")
    void testRemovingZeroHealth() {
      assertThrows(IllegalArgumentException.class, () -> player.removeHealth(0));
    }

    @Test
    @DisplayName("Test removing positive score")
    void testRemovingPositiveScore() {
      player.removeScore(5);
      assertEquals(5, player.getScore());
    }

    @Test
    @DisplayName("Test removing more score than the player has")
    void testRemovingMoreScoreThanThePlayerHas() {
      player.removeScore(100);
      assertEquals(0, player.getScore());
    }

    @Test
    @DisplayName("Test removing negative score")
    void testRemovingNegativeScore() {
      assertThrows(IllegalArgumentException.class, () -> player.removeScore(-1));
    }

    @Test
    @DisplayName("Test removing zero score")
    void testRemovingZeroScore() {
      assertThrows(IllegalArgumentException.class, () -> player.removeScore(0));
    }

    @Test
    @DisplayName("Test removing positive gold")
    void testRemovingPositiveGold() {
      player.removeGold(5);
      assertEquals(5, player.getGold());
    }

    @Test
    @DisplayName("Test removing more gold than the player has")
    void testRemovingMoreGoldThanThePlayerHas() {
      player.removeGold(100);
      assertEquals(0, player.getGold());
    }

    @Test
    @DisplayName("Test removing negative gold")
    void testRemovingNegativeGold() {
      assertThrows(IllegalArgumentException.class, () -> player.removeGold(-1));
    }

    @Test
    @DisplayName("Test removing zero gold")
    void testRemovingZeroGold() {
      assertThrows(IllegalArgumentException.class, () -> player.removeGold(0));
    }

    @Test
    @DisplayName("Test removing valid item")
    void testRemovingValidItem() {
      String item = "testItem";
      player.addToInventory(item);
      player.removeFromInventory(item);
      assertFalse(player.getInventory().contains(item));
    }

    @Test
    @DisplayName("Test removing null as item")
    void testRemovingNullAsItem() {
      assertThrows(IllegalArgumentException.class, () -> player.removeFromInventory(null));
    }

    @Test
    @DisplayName("Test removing blank item")
    void testRemovingBlankItem() {
      assertThrows(IllegalArgumentException.class, () -> player.removeFromInventory(""));
    }

    @Test
    @DisplayName("Test removing item that is not in inventory")
    void testRemovingItemThatIsNotInInventory() {
      assertThrows(IllegalArgumentException.class,
          () -> player.removeFromInventory("testItem2"));
    }
  }

  @Test
  @DisplayName("Test equals")
  void testEquals() {
    Player player = new Player.Builder("testName").build();
    assertNotEquals(player, null);
  }

  @Test
  @DisplayName("Test HashCode")
  void testHashCode() {
    Player player = new Player.Builder("testName").build();

    int expectedHashCode = Objects.hash("testName");

    int actualHashCode = player.hashCode();

    assertEquals(expectedHashCode, actualHashCode);
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    Player player = new Player.Builder("testName")
      .health(10)
      .score(20)
      .gold(30)
      .build();

    assertEquals("Player{" +
      "name='" + "testName" + '\'' +
      ", health=" + 10 +
      ", score=" + 20 +
      ", gold=" + 30 +
      ", inventory=" + "[]" +
      '}', player.toString());
  }
}

