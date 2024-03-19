package edu.ntnu.arbeidskrav4;

import java.util.Collection;

/**
 * This class represents a hand of cards.
 */
public class HandOfCards {
  private final Collection<PlayingCard> hand;

  public HandOfCards(Collection<PlayingCard> hand) {
    this.hand = hand;
  }

  public Collection<PlayingCard> getHand() {
    return hand;
  }

  /**
   * Sum the values of the cards in the hand.
   */
  public int getSum() {
    return hand.stream().mapToInt(PlayingCard::getFace).sum();
  }

  /**
   * Returns the playing cards in the hand that are hearts.
   */
  public String getHearts() {
    return hand.stream().filter(card -> card.getSuit() == 'H').map(PlayingCard::toString)
      .reduce("", (a, b) -> a + " " + b);
  }

  /**
   * Boolean of whether the queen of spades is in the hand.
   *
   * @return true if the queen of spades is in the hand, false otherwise.
   */
  public boolean queenOfSpadesInHand() {
    return hand.stream().anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);
  }

  /**
   * Boolean of whether the hand contains a 5 card flush of any suit.
   */
  public boolean fiveCardFlushInHand() {
    return hand.stream().anyMatch(card -> hand.stream().filter(card2 -> card2.getSuit() == card
      .getSuit()).count() == 5);
  }

  /**
   * ToString method for the hand of cards.
   */
  @Override
  public String toString() {
    StringBuilder handString = new StringBuilder();
    for (PlayingCard card : hand) {
      handString.append(card.toString()).append(" ");
    }
    return handString.toString();
  }
}
