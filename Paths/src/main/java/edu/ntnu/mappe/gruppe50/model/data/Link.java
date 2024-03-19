package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents a Link object. A link references a passage and makes it possible to move
 * from one passage to another. If each passage is a room, then the link is the door from room to
 * room. Links are what bind the whole story together.
 *
 * @author Harry Linrui Xu
 * @since 9.2.2023
 */
public class Link {

  private final String text;
  private final String reference;
  private final List<Action> actions;
  private final List<Goal> goals;

  /**
   * Creates an instance of a link object.
   *
   * @param text      A text that indicates a choice in a story. The text is a part of the link that
   *                  is visible for the player.
   * @param reference Unambiguously identifies a passage (part of the story). Used when referring to
   *                  a passage. In practice, this will be the title of the referenced passage.
   * @throws IllegalArgumentException if either the text or reference are null or blank.
   */
  public Link(String text, String reference) throws IllegalArgumentException {
    if (text == null) {
      throw new IllegalArgumentException("The link text cannot be null.");
    }
    if (text.isBlank()) {
      throw new IllegalArgumentException("The link text cannot be blank.");
    }
    if (reference == null) {
      throw new IllegalArgumentException("The link reference cannot be null.");
    }
    if (reference.isBlank()) {
      throw new IllegalArgumentException("The link reference cannot be blank.");
    }

    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
    this.goals = new ArrayList<>();
  }

  /**
   * Deep copy constructor. Allows for deep copying a link by sending in a link object as an
   * argument. The new link is an identical copy of the original content wise, but gains
   * independence by having its own reference.
   *
   * @param link The link object that will be copied.
   */
  public Link(Link link) {
    this.text = link.getText();
    this.reference = link.getReference();
    this.actions = link.actions;
    this.goals = link.goals;
  }

  /**
   * Getter for the caller's text.
   *
   * @return The text of the caller.
   */
  public String getText() {
    return this.text;
  }

  /**
   * Getter for the caller's reference.
   *
   * @return The reference of the caller.
   */
  public String getReference() {
    return this.reference;
  }

  /**
   * Adds an action to the link. Only one of each action type can be added.
   *
   * @param action An object that changes the state of the player.
   * @throws IllegalArgumentException If the action is null or if an action of the same type already
   *                                  exists in the link.
   */
  public void addAction(Action action) throws IllegalArgumentException {
    if (action == null) {
      throw new IllegalArgumentException("Cannot add an action to a link if the action is null.");
    }
    if (this.actions.stream()
        .anyMatch(thisAction -> thisAction.getClass().equals(action.getClass()))) {
      throw new IllegalArgumentException("Link can only contain one of each goal type");
    }
    this.actions.add(action);
  }

  /**
   * Removes an action from the link.
   *
   * @param action An object that changes the state of the player.
   * @throws IllegalArgumentException If action is null, or if the link does not contain the
   *                                  action.
   */
  public void removeAction(Action action) throws IllegalArgumentException {
    if (action == null) {
      throw new IllegalArgumentException(
          "Cannot remove an action from a link if the action is null.");
    }
    if (!actions.contains(action)) {
      throw new IllegalArgumentException(
          "The action that is attempted to be removed does not exist in the link");
    }
    this.actions.remove(action);
  }

  /**
   * Returns a deep copy of the list of actions that is contained in the link.
   *
   * @return A deep copied list containing actions.
   */
  public List<Action> getActions() {
    return new ArrayList<>(actions);
  }

  /**
   * Adds a goal to the link. In order to choose a link, all of its goals must first be completed.
   *
   * @param goal An object that represents a desired result in the players stats.
   * @throws IllegalArgumentException If goal is null or if a goal of the same type already exists
   *                                  in the link.
   */
  public void addGoal(Goal goal) throws IllegalArgumentException {
    if (goal == null) {
      throw new IllegalArgumentException("Cannot add a goal to a link if the goal is null.");
    }
    if (this.goals.stream().anyMatch(thisGoal -> thisGoal.getClass().equals(goal.getClass()))) {
      throw new IllegalArgumentException("Link can only contain one of each goal type");
    }
    this.goals.add(goal);
  }

  /**
   * Removes a goal from the link.
   *
   * @param goal An object that represents a desired result in the players stats.
   * @throws IllegalArgumentException If goal is null.
   */
  public void removeGoal(Goal goal) throws IllegalArgumentException {
    if (goal == null) {
      throw new IllegalArgumentException("Cannot add a goal to a link if the goal is null.");
    }
    if (!goals.contains(goal)) {
      throw new IllegalArgumentException(
          "The goal that is attempted to be removed does not exist in the link");
    }
    this.goals.remove(goal);
  }

  /**
   * Returns a deep copy of the list of goals that is contained in the link.
   *
   * @return A deep copied list containing goals.
   */
  public List<Goal> getGoals() {
    return new ArrayList<>(goals);
  }

  /**
   * Returns a concatenated string of the attributes of the Link class, being the text, reference,
   * each goal as a string and each action as a string.
   *
   * @return A concatenated string of the attributes of the Link class.
   */
  @Override
  public String toString() {
    StringBuilder actionsString = new StringBuilder();
    actions.forEach(action -> actionsString.append("\n").append(action.toString()));

    StringBuilder goalsString = new StringBuilder();
    goals.forEach(goal -> goalsString.append("\n").append(goal.toString()));

    return "[" + this.getText() + "]" + "(" + this.getReference() + ")" + goalsString
        + actionsString;
  }

  /**
   * Compares two link to see if they are equal, which only occurs if the references are the same.
   *
   * @param o A second link that the first link is compared against.
   * @return True, if the references are equal. Returns false, if else.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Link link)) {
      return false;
    }
    return getReference().equals(link.getReference());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getReference());
  }
}
