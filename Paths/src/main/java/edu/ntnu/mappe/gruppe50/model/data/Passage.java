package edu.ntnu.mappe.gruppe50.model.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents a passage. A passage is a smaller part of a story. If a story is an entire
 * house, then the passage are the rooms, connected by doors. It is possible to move from one
 * passage to another using links, which are the doors between rooms.
 *
 * @author Harry Xu and Lars Mikkel Lødeng Nilsen
 * @since 9.2.2022
 */
public class Passage {

  private final String title;
  private final String content;
  private final List<Link> links;

  /**
   * Creates an instance of the Passage class.
   *
   * @param title   Description that dual functions as an identifier.
   * @param content Text that functions as a paragraph or a part of dialogue.
   * @throws IllegalArgumentException if title or content is null or blank.
   */
  public Passage(String title, String content) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("Title cannot be null.");
    }
    if (title.isBlank()) {
      throw new IllegalArgumentException("Title cannot be blank.");
    }
    if (content == null) {
      throw new IllegalArgumentException("Content cannot be null.");
    }
    if (content.isBlank()) {
      throw new IllegalArgumentException("Content cannot be blank.");
    }

    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  /**
   * Getter for the passage's title.
   *
   * @return The passage's title as a string
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Getter for the passage's content.
   *
   * @return The passage's content as a string
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Adds a link to a passage. Adding two or more links to a passage makes the story non-linear.
   *
   * @param link A link that links passages together.
   * @return True, if the link is successfully added. Returns false, if else.
   * @throws IllegalArgumentException If link argument is null.
   */
  public boolean addLink(Link link) throws IllegalArgumentException {
    if (link == null) {
      throw new IllegalArgumentException("Cannot add a link to a passage if it is null.");
    }
    return this.links.add(link);
  }

  /**
   * Removes a link from a passage. This makes the story more linear.
   *
   * @param link A link that links passages together.
   * @return True, if the link is successfully removed. Returns false, if else.
   * @throws IllegalArgumentException If link argument is null or if the passage does not contain
   *                                  the link.
   */
  public boolean removeLink(Link link) throws IllegalArgumentException {
    if (link == null) {
      throw new IllegalArgumentException("Cannot add a link to a passage if it is null.");
    }
    if (!links.contains(link)) {
      throw new IllegalArgumentException(
          "The link that is attempted to be removed does not exist in the passage's list of links");
    }
    return this.links.remove(link);
  }

  /**
   * Returns a deep copied list of the passages list.
   *
   * @return A deep copied list of links.
   */
  public List<Link> getLinks() {
    return this.links;
  }

  /**
   * Checks if a passage has any links.
   *
   * @return True, if the passage has one or more links. Returns false, if the passage is empty.
   */
  public boolean hasLinks() {
    return !this.links.isEmpty();
  }

  /**
   * Returns a concatenated, formatted string of the Passage class's attributes, being the title,
   * content and links.
   *
   * @return Returns a concatenated, formatted string of the Passage class's attributes.
   */
  @Override
  public String toString() {
    StringBuilder linkString = new StringBuilder();
    for (Link link : links) {
      linkString.append("\n").append(link.toString());
    }
    return "::" + title + '\n' + content + linkString;
  }


  /**
   * Compares two passages to see if they are equal. The condition is satisfied if the passages
   * titles, contents and links are equal. The order of the links do not matter.
   *
   * @param o A second passage that the first passage is compared against.
   * @return True, if equal. Returns false, if else.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Passage passage)) {
      return false;
    }
    return Objects.equals(title, passage.title) && Objects.equals(content, passage.content)
        && (new HashSet<>(links).containsAll(passage.links) && new HashSet<>(
            passage.links).containsAll(links));
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, links);
  }
}


