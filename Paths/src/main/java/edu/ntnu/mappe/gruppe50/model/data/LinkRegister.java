package edu.ntnu.mappe.gruppe50.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a link register, which is a simple list links. This is used to keep track
 * of the visited links during a game play through.
 *
 * @author Harry Linrui Xu
 * @since 14.05.2023
 */
public class LinkRegister {

  private final List<Link> links;

  /**
   * Constructor that instantiates the links List. This list stores all the links.
   */
  public LinkRegister() {
    this.links = new ArrayList<>();
  }

  /**
   * Gets a deep copied LinkRegister.
   *
   * @return A deep copied LinkRegister.
   */
  public List<Link> getLinks() {
    return new ArrayList<>(links);
  }

  /**
   * Adds a link to the register. The same link can be added multiple times, in case the player
   * decides to go back to a passage.
   *
   * @param link The link that is being added to the register.
   * @throws IllegalArgumentException If the link is null.
   */
  public void addLink(Link link) throws IllegalArgumentException {
    if (link == null) {
      throw new IllegalArgumentException("Cannot add link because it is null");
    }

    links.add(link);
  }

  /**
   * Removes a link from the register.
   *
   * @param link The link object that is being removed from the register.
   * @throws IllegalArgumentException If the link is null or if the link is not in the register.
   */
  public void removeLink(Link link) throws IllegalArgumentException {
    if (link == null) {
      throw new IllegalArgumentException("Cannot remove link because it is null");
    }
    if (!links.contains(link)) {
      throw new IllegalArgumentException("Cannot remove link because it is not in the register");
    }
    links.remove(link);
  }

  /**
   * Method that checks if the register contains a link equal to the parameter link. The result is
   * indicated by the boolean value.
   *
   * @param link The register checks if this link is already added.
   * @return True, if the link already exists in the register. Else, returns false.
   */
  public boolean contains(Link link) {
    return links.contains(link);
  }

  /**
   * Checks if the register is empty.
   *
   * @return True, if the list size is zero.
   */
  public boolean isEmpty() {
    return this.links.size() == 0;
  }

  /**
   * Creates and String that represents the linksregister. The string consists of all the links that
   * make up the register
   *
   * @return A string version of the link register.
   */
  @Override
  public String toString() {
    StringBuilder linksAsString = new StringBuilder();
    for (Link link : this.links) {
      linksAsString.append(link.toString()).append("\n\n");
    }
    return linksAsString.toString();
  }

}
