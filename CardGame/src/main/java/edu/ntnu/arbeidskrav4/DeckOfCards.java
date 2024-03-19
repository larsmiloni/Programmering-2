package edu.ntnu.arbeidskrav4;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents a deck of cards. A deck of cards consists of 52 cards, 13 cards of each suit. The
 * deck can be shuffled and a random hand can be dealt from the deck.
 */
public class DeckOfCards {
  private final List<PlayingCard> cards = new ArrayList<>();

  /**
   * Creates a deck of cards with all the cards in order.
   */
  public DeckOfCards() {
    char[] suit = {'S', 'H', 'D', 'C'};
    for (char s : suit) {
      for (int i = 1; i <= 13; i++) {
        cards.add(new PlayingCard(s, i));
      }
    }
  }

  /**
   * Shuffles the deck of cards.
   */
  public void shuffleDeck() {
    Collections.shuffle(cards);
  }

  /**
   * Returns the deck as a list of cards.
   *
   * @return the deck as a list of cards
   */
  public List<PlayingCard> getCards() {
    return cards;
  }

  /**
   * Deals a random hand from the deck of cards and removes the cards from the deck. Throws an
   * exception if there are not enough cards in the deck to deal the hand.
   *
   * @param numberOfCards the number of cards to deal
   * @return a random hand from the deck of cards
   */
  public Collection<PlayingCard> dealHand(int numberOfCards) {
    List<PlayingCard> hand = new ArrayList<>();
    Random random = new Random();

    if (cards.size() < numberOfCards) {
      throw new IllegalArgumentException(
        "Not enough cards in the deck to deal a hand of " + numberOfCards + " cards.");
    }

    for (int i = 0; i < numberOfCards; i++) {
      hand.add(cards.remove(random.nextInt(cards.size())));
    }
    return hand;
  }

  /**
   * Returns the deck as a string with each card separated by a space.
   *
   * @return the deck as a string
   */
  @Override
  public String toString() {
    StringBuilder deckString = new StringBuilder();
    for (PlayingCard card : cards) {
      deckString.append(card.toString()).append(" ");
    }

    return deckString.toString();
  }
}
