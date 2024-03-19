package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.customexceptions.NoMatchingPassageException;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents a game. A game is a facade of a Paths game. The class connects a player
 * with a story and has handy methods for starting and maneuvering a game. The Game class implements
 * an observer pattern, acting as the source that updates its subscribers, being a list of goals
 * that have been inherited from the AbstractGame class.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 9.2.2023
 */
public class Game extends AbstractGame {

  private final Player player;
  private final Story story;
  private final LinkRegister visitedLinks;

  /**
   * Creates an instance of a game object.
   *
   * @param player       The player character that will be used to play the story.
   * @param story        The story that will be played.
   * @param goals        A list of goals for the game.
   * @param visitedLinks The list of links that have been visited in the game.
   * @throws IllegalArgumentException If player, story or goals is null.
   */
  public Game(Player player, Story story, List<Goal> goals, LinkRegister visitedLinks)
      throws IllegalArgumentException {
    super(goals);
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    if (story == null) {
      throw new IllegalArgumentException("Story cannot be null");
    }
    if (visitedLinks == null) {
      throw new IllegalArgumentException("Links cannot be null");
    }

    this.player = player;
    this.story = story;
    this.visitedLinks = visitedLinks;
  }

  /**
   * Gets the player in the game.
   *
   * @return The player in the game.
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Gets the game story.
   *
   * @return The game story.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Returns a deep copied arraylist containing the custom goals that have been added to the game.
   *
   * @return A deep copied arraylist of goals.
   */
  public List<Goal> getGoals() {
    return new ArrayList<>(goals);
  }

  /**
   * Returns a deep copied instance of a LinkRegister class, which tracks the links have been
   * visited in the game.
   *
   * @return A deep copied LinkRegister.
   */
  public LinkRegister getVisitedLinks() {
    return visitedLinks;
  }

  /**
   * Gets the opening passage for the story in the game.
   *
   * @return The opening passage in the game story.
   */
  public Passage begin() {
    return this.story.getOpeningPassage();
  }

  /**
   * Gets a passage, given the provided link. The method updates all its goal listeners, using the
   * player. The method also iterates through all the actions in the provided link, before adding it
   * to the list of visited links.
   *
   * @param link The key that references the passage in a key-value pair in the story.
   * @return The referenced passage.
   * @throws IllegalArgumentException   If link is null, or the player has not fulfilled the
   *                                    necessary goals to access the passage.
   * @throws NoMatchingPassageException If the link is dead, meaning that the referenced passage
   *                                    does not exist.
   */
  public Passage go(Link link) throws IllegalArgumentException, NoMatchingPassageException {
    if (link == null) {
      throw new IllegalArgumentException("Cannot go the next passage because the link is null");
    }
    if (story.getBrokenLinks().contains(link)) {
      throw new NoMatchingPassageException("The referenced passage does not exist");
    }
    if (link.getGoals().stream().anyMatch(goal -> !goal.isFulfilled(player))) {
      throw new IllegalArgumentException(
          "All the goals in the link must be fulfilled to access the passage");
    }

    update(player);
    link.getActions().forEach(action -> action.execute(player));
    visitedLinks.addLink(link);

    return story.getPassage(link);
  }

  /**
   * Gets the passage where the game was last saved. This would be the passage referenced by the
   * last link visited in the game. To keep player stats consistent, each link in the list of
   * visited links is looped through and executed.
   *
   * @return The passage where the game was saved. If no links have been visited, the opening
   *     passage is returned.
   */
  public Passage getSavedPassage() {
    if (visitedLinks.isEmpty()) {
      return story.getOpeningPassage();
    }

    visitedLinks.getLinks()
        .forEach(visitedLink -> visitedLink.getActions()
            .forEach(action -> action.execute(player))
        );
    int lastIndex = visitedLinks.getLinks().size() - 1;
    Link recentLink = visitedLinks.getLinks().get(lastIndex);

    return story.getPassage(recentLink);
  }

  /**
   * Method for finding links that can be accessed with the players current stats and inventory in a
   * given passage. This is done by comparing the player stats with the link's goals and seeing if
   * they are fulfilled.
   *
   * @param passage The passage whose links are evaluated.
   * @param player  The player that the links are evaluated against.
   * @return List of links that can be accessed by the player.
   */
  public List<Link> getAccessibleLinks(Passage passage, Player player) {
    return passage.getLinks().stream().filter(link -> link.getGoals()
        .stream().allMatch(goal -> goal.isFulfilled(player))).collect(Collectors.toList());
  }

  /**
   * Checks if a play through is new.
   *
   * @return If the size of the visited links list is zero.
   */
  public boolean isNewPlayThrough() {
    return visitedLinks.getLinks().size() == 0;
  }

  /**
   * Returns a concatenated string of the game's formatted story title and player name.
   *
   * @return String containing the story title and the player name.
   */
  @Override
  public String toString() {
    return getStory().getTitle().replace(" ", "_") + "\n" + getPlayer().getName();
  }
}

