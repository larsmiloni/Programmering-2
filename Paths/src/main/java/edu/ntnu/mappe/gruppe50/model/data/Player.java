package edu.ntnu.mappe.gruppe50.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that simulates a player. A player represents a player character with various attributes
 * that can influence a story. A player has a name, health, score, gold and an inventory.
 *
 * @author Harry Linrui Xu
 * @since 11.2.2023
 */
public class Player {

  private final String name;
  private int health;
  private int score;
  private int gold;
  private final List<String> inventory;

  /**
   * Constructor for a player instance. The constructor uses a Builder pattern to instantiate the
   * values of the player object, allowing some fields to be excluded during the instantiation. The
   * only necessary parameter is the player name.
   *
   * @param builder The builder used to set the values of the player object.
   * @throws IllegalArgumentException If name is blank or null, health is less than one, gold is
   *                                  less than zero or score is less than zero.
   */
  public Player(Builder builder) throws IllegalArgumentException {
    name = builder.name;
    health = builder.health;
    score = builder.score;
    gold = builder.gold;
    inventory = new ArrayList<>();
  }

  /**
   * Gets the player name.
   *
   * @return The player name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the player health.
   *
   * @return The player health.
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * Sets the value of the player health.
   *
   * @param health The value to which the health will be set.
   * @throws IllegalArgumentException If the health is less than zero.
   */
  public void setHealth(int health) throws IllegalArgumentException {
    if (health < 0) {
      throw new IllegalArgumentException("Cannot set health less than zero");
    }
    this.health = health;
  }

  /**
   * Increases the player health.
   *
   * @param health The value by which the player health will be increased.
   * @throws IllegalArgumentException If the added health is zero or negative.
   */
  public void addHealth(int health) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException("Health gained cannot be zero or negative.");
    }
    this.health += health;
  }

  /**
   * Decreases the player health. The player health is set to zero if the input health value is
   * greater than the player health.
   *
   * @param health The value by which the player health will be decreased.
   * @throws IllegalArgumentException If the removed health is zero or negative.
   */
  public void removeHealth(int health) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException("Health lost cannot be zero or negative.");
    }

    if (health > this.health) {
      this.health = 0;
    } else {
      this.health -= health;
    }
  }

  /**
   * Gets the player score.
   *
   * @return The player score
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Sets the value of the player score.
   *
   * @param score The value to which the score will be set.
   * @throws IllegalArgumentException If the score is less than zero.
   */
  public void setScore(int score) throws IllegalArgumentException {
    if (score < 0) {
      throw new IllegalArgumentException("Cannot set score to less than zero");
    }
    this.score = score;
  }

  /**
   * Increases player score.
   *
   * @param score The value by which the player score will be increased.
   * @throws IllegalArgumentException If the score is zero or negative.
   */
  public void addScore(int score) throws IllegalArgumentException {
    if (score <= 0) {
      throw new IllegalArgumentException("Score gained cannot be zero or negative.");
    }
    this.score += score;
  }

  /**
   * Decreases player score.
   *
   * @param score The value by which the players score will be decreased.
   * @throws IllegalArgumentException If the score is zero or negative.
   */
  public void removeScore(int score) throws IllegalArgumentException {
    if (score <= 0) {
      throw new IllegalArgumentException("Score lost cannot be zero or negative.");
    }

    if (score > this.score) {
      this.score = 0;
    } else {
      this.score -= score;
    }
  }

  /**
   * Gets the player gold.
   *
   * @return The player gold.
   */
  public int getGold() {
    return this.gold;
  }

  /**
   * Sets the value of the player gold.
   *
   * @param gold The value to which the gold will be set.
   * @throws IllegalArgumentException If the score is less than zero.
   */
  public void setGold(int gold) {
    if (gold < 0) {
      throw new IllegalArgumentException("Gold cannot be less than zero");
    }
    this.gold = gold;
  }

  /**
   * Increase player gold.
   *
   * @param gold The value by which the player gold will be increased.
   * @throws IllegalArgumentException If gold is zero or negative.
   */
  public void addGold(int gold) throws IllegalArgumentException {
    if (gold <= 0) {
      throw new IllegalArgumentException("Gold gained cannot be zero or negative.");
    }
    this.gold += gold;
  }

  /**
   * Decreases player gold.
   *
   * @param gold The value by which the player gold will be decreased.
   * @throws IllegalArgumentException If the gold is zero or negative.
   */
  public void removeGold(int gold) throws IllegalArgumentException {
    if (gold <= 0) {
      throw new IllegalArgumentException("Lost gold cannot be zero or negative.");
    }

    if (gold > this.gold) {
      this.gold = 0;
    } else {
      this.gold -= gold;
    }
  }

  /**
   * Returns a deep copy of inventory list.
   *
   * @return A deep copied list containing items.
   */
  public List<String> getInventory() {
    return new ArrayList<>(inventory);
  }

  /**
   * Adds an item to the player inventory.
   *
   * @param item The item which is added to the player inventory.
   * @throws IllegalArgumentException If item is null or blank.
   */
  public void addToInventory(String item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Cannot add item to inventory because it is null.");
    }
    if (item.isBlank()) {
      throw new IllegalArgumentException("Cannot add item to inventory because it is blank.");
    }

    this.inventory.add(item);
  }

  /**
   * Removes an item from the player inventory.
   *
   * @param item The item which is removed from the player inventory.
   * @throws IllegalArgumentException If item is null or blank, or if the player inventory does not
   *                                  contain the item.
   */
  public void removeFromInventory(String item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Cannot remove item from inventory because it is null.");
    }
    if (item.isBlank()) {
      throw new IllegalArgumentException("Cannot remove item from inventory because it is blank.");
    }
    if (!inventory.contains(item)) {
      throw new IllegalArgumentException("Item cannot be removed if it is not in the inventory.");
    }

    this.inventory.remove(item);
  }

  /**
   * Returns a concatenated string of the players attributes being its name, health, score, gold and
   * inventory.
   *
   * @return A string of the player attributes.
   */
  @Override
  public String toString() {
    return "Player{"
        + "name='" + name + '\''
        + ", health=" + health
        + ", score=" + score
        + ", gold=" + gold
        + ", inventory=" + inventory
        + '}';
  }

  /**
   * Equals method for player. Equality is achieved if the player names are the same.
   *
   * @param obj The object that the caller is compared to.
   * @return True, if both player names are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Player other = (Player) obj;
    return name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /**
   * Static inner Builder class, used to implement the Builder design pattern into player. A
   * dedicated Builder class allows the creation of several representations of a player. The only
   * required parameter is the player name. By default, the player health, score and gold are one,
   * zero and zero respectively.
   */
  public static class Builder {

    private final String name;
    private int health = 1;
    private int score = 0;
    private int gold = 0;

    /**
     * Builds the player name.
     *
     * @param name The value to which the player name will be set.
     */
    public Builder(String name) {
      if (name == null) {
        throw new IllegalArgumentException("Player name cannot be null.");
      }
      if (name.isBlank()) {
        throw new IllegalArgumentException("Player name cannot be blank.");
      }

      this.name = name;
    }

    /**
     * Builds the player health.
     *
     * @param val The value to which the player health will be set.
     * @return A Builder instance for health.
     * @throws IllegalArgumentException If the starting health is set to less than one.
     */
    public Builder health(int val) {
      if (val <= 0) {
        throw new IllegalArgumentException("Starting health cannot be zero or less.");
      }
      health = val;
      return this;
    }

    /**
     * Builds the player score.
     *
     * @param val The value to which the player score will be set.
     * @return A Builder instance for score.
     * @throws IllegalArgumentException If the starting score is set to less than zero.
     */
    public Builder score(int val) {
      if (val < 0) {
        throw new IllegalArgumentException("Starting score cannot be less than zero.");
      }
      score = val;
      return this;
    }

    /**
     * Builds the player gold.
     *
     * @param val The value to which the player gold will be set.
     * @return A Builder instance for gold.
     * @throws IllegalArgumentException If the starting gold is set to less than zero.
     */
    public Builder gold(int val) {
      if (val < 0) {
        throw new IllegalArgumentException("Starting gold cannot be less than zero.");
      }
      gold = val;
      return this;
    }

    /**
     * Builds an instance of a player, using the values created by the separate Builder instances.
     *
     * @return An instance of the Player class.
     */
    public Player build() {
      return new Player(this);
    }
  }
}
