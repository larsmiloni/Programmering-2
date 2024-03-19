package edu.ntnu.arbeidskrav4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests that the hand of cards can get the sum of the cards in the hand, the amount of hearts in
 * the hand, whether the queen of spades is in the hand, and whether the hand contains a 5 card
 * flush.
 */
public class HandOfCardsTest {

  /**
   * Tests that the hand of cards can get the sum of the cards in the hand. As there are only 4
   * ones, the minimum sum of the hand is 6, because of 1 + 1 + 1 + 1 + 2 = 6.
   */
  @Test
  public void createHandOfCards() {
    DeckOfCards deckOfCards = new DeckOfCards();
    HandOfCards handOfCards = new HandOfCards(deckOfCards.dealHand(5));

    assertEquals(handOfCards.getHand().size(), 5);
    assertTrue(handOfCards.getSum() > 5 && handOfCards.getSum() < 65);
  }

  @Test
  public void sumHand() {
    ArrayList<PlayingCard> hand = new ArrayList<>();
    hand.add(new PlayingCard('H', 1));
    hand.add(new PlayingCard('H', 2));
    hand.add(new PlayingCard('D', 3));
    hand.add(new PlayingCard('C', 4));
    hand.add(new PlayingCard('S', 5));

    HandOfCards handOfCards = new HandOfCards(hand);

    assertEquals(handOfCards.getSum(), 15);
  }

  @Test
  public void heartsInHand() {
    ArrayList<PlayingCard> hand = new ArrayList<>();
    hand.add(new PlayingCard('H', 1));
    hand.add(new PlayingCard('H', 2));
    hand.add(new PlayingCard('D', 3));
    hand.add(new PlayingCard('C', 4));
    hand.add(new PlayingCard('H', 5));

    HandOfCards handOfCards = new HandOfCards(hand);

    assertEquals(handOfCards.getHearts(), " H1 H2 H5");
  }

  @Nested
  class queenOfSpades {
    @Test
    public void queenOfSpadesInHand() {
      ArrayList<PlayingCard> hand = new ArrayList<>();
      hand.add(new PlayingCard('H', 1));
      hand.add(new PlayingCard('H', 2));
      hand.add(new PlayingCard('S', 12));
      hand.add(new PlayingCard('C', 4));
      hand.add(new PlayingCard('S', 9));

      HandOfCards handOfCards = new HandOfCards(hand);

      assertTrue(handOfCards.queenOfSpadesInHand());
    }

    @Test
    public void queenOfSpadesNotInHand() {
      ArrayList<PlayingCard> hand = new ArrayList<>();
      hand.add(new PlayingCard('H', 1));
      hand.add(new PlayingCard('H', 2));
      hand.add(new PlayingCard('D', 3));
      hand.add(new PlayingCard('C', 4));
      hand.add(new PlayingCard('S', 5));

      HandOfCards handOfCards = new HandOfCards(hand);

      assertFalse(handOfCards.queenOfSpadesInHand());
    }
  }

  @Nested
  class fiveCardFlush {
    @Test
    public void fiveCardFlushInHand() {
      ArrayList<PlayingCard> hand = new ArrayList<>();
      hand.add(new PlayingCard('H', 1));
      hand.add(new PlayingCard('H', 2));
      hand.add(new PlayingCard('H', 3));
      hand.add(new PlayingCard('H', 4));
      hand.add(new PlayingCard('H', 5));

      HandOfCards handOfCards = new HandOfCards(hand);

      assertTrue(handOfCards.fiveCardFlushInHand());

      hand.set(0, new PlayingCard('D', 1));
      hand.set(1, new PlayingCard('D', 2));
      hand.set(2, new PlayingCard('D', 3));
      hand.set(3, new PlayingCard('D', 4));
      hand.set(4, new PlayingCard('D', 5));

      handOfCards = new HandOfCards(hand);

      assertTrue(handOfCards.fiveCardFlushInHand());
    }

    @Test
    public void fiveCardFlushNotInHand() {
      ArrayList<PlayingCard> hand = new ArrayList<>();
      hand.add(new PlayingCard('H', 1));
      hand.add(new PlayingCard('H', 2));
      hand.add(new PlayingCard('D', 3));
      hand.add(new PlayingCard('C', 4));
      hand.add(new PlayingCard('S', 5));

      HandOfCards handOfCards = new HandOfCards(hand);

      assertFalse(handOfCards.fiveCardFlushInHand());
    }
  }
}