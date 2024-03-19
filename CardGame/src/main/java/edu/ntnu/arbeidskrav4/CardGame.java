package edu.ntnu.arbeidskrav4;

/**
 * This class will create a new deck and deal cards from it.
 */
public class CardGame {
  private DeckOfCards deck;
  private HandOfCards hand;

  /**
   * Creates an instance of CardGame.
   */
  public CardGame() {
    deck = new DeckOfCards();
    hand = new HandOfCards(deck.dealHand(5));
  }

  /**
   * Creates a new deck of cards
   */
  public void createNewDeck() {
    deck = new DeckOfCards();
  }


  /**
   * Deals a new hand of 5 cards.
   */
  public void dealNewHand() {
    hand = new HandOfCards(deck.dealHand(5));
  }

  /**
   * Returns the hand of cards.
   *
   * @return the hand of cards
   */
  public HandOfCards getHand() {
    return hand;
  }

  /**
   * Returns the deck of cards.
   *
   * @return the deck of cards
   */
  public DeckOfCards getDeck() {
    return deck;
  }
}

