package edu.ntnu.arbeidskrav4;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Only test the toString() method and the equals method of the PlayingCard class as all the
 * other methods are trivial.
 */
public class PlayingCardTest {

  @Nested
  class testConstructorPlayingCard {

    @Test
    void testValidConstructor() {
      PlayingCard playingCard = new PlayingCard('H', 5);
      assertEquals('H', playingCard.getSuit());
      assertEquals(5, playingCard.getFace());
    }

    @Test
    void testNotValidConstructor() {
      assertThrows(IllegalArgumentException.class, () -> new PlayingCard('H', 18));
    }
  }


  @Test
  public void testToString() {
    PlayingCard card = new PlayingCard('H', 4);
    assertEquals("H4", card.toString());

    card = new PlayingCard('S', 13);
    assertEquals("S13", card.toString());
  }
}
