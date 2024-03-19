package edu.ntnu.arbeidskrav4;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CardGameTest {

  @Test
  void testCreateNewDeck() {
    CardGame cardGame1 = new CardGame();
    CardGame cardGame2 = new CardGame();
    cardGame1.createNewDeck();
    cardGame2.createNewDeck();
    assertNotEquals(cardGame1, cardGame2);
  }

  @Test
  void testDealNewHand() {
    CardGame cardGame1 = new CardGame();
    CardGame cardGame2 = new CardGame();
    cardGame1.createNewDeck();
    cardGame2.createNewDeck();
    cardGame1.dealNewHand();
    cardGame2.dealNewHand();
    assertNotEquals(cardGame1, cardGame2);
  }
}
