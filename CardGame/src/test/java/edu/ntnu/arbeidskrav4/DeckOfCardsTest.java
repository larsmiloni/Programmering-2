package edu.ntnu.arbeidskrav4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests the DeckOfCards class by creating a deck of cards, shuffling the deck and dealing a hand.
 */
public class DeckOfCardsTest {
  @Test
  public void createDeckOfCards() {
    DeckOfCards deck = new DeckOfCards();
    assertEquals(52, deck.getCards().size());
  }

  @Test
  public void shuffleDeckOfCards() {
    DeckOfCards deck = new DeckOfCards();
    String deckBeforeShuffle = deck.toString();
    deck.shuffleDeck();
    String deckAfterShuffle = deck.toString();
    assertNotEquals(deckBeforeShuffle, deckAfterShuffle);
  }

  @Nested
  class DealHand {
    @Test
    public void dealHandSize() {
      DeckOfCards deck = new DeckOfCards();
      int numberOfCards = 5;
      assertEquals(numberOfCards, deck.dealHand(numberOfCards).size());
    }

    @Test
    public void dealEntireDeck() {
      DeckOfCards deck = new DeckOfCards();
      assertEquals(52, deck.getCards().size());

      for (int i = 0; i < 13; i++) {
        assertEquals(4, deck.dealHand(4).size());
        assertEquals(52-((i+1)*4), deck.getCards().size());
      }
      assertEquals(0, deck.getCards().size());
    }
  }

  @Test
  public void dealHandTooManyCards() {
    DeckOfCards deck = new DeckOfCards();
    int numberOfCards = 53;

    try {
      deck.dealHand(numberOfCards);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough cards in the deck to deal a hand of " + numberOfCards + " cards.",
        e.getMessage());
    }
  }

  @Test
  public void toStringTest() {
    DeckOfCards deck = new DeckOfCards();
    String deckString = deck.toString();

    assertEquals(52, deckString.split(" ").length);
  }
}
