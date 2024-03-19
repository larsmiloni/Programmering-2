package edu.ntnu.mappe.gruppe50.model.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that represents the story. A story is an interactive, non-linear narrative that consists of
 * a collection of passages. The story can be thought of as a house, consisting of many room. These
 * are the passages. The passages are connected by doors, which is represented by links. Each
 * passage in the story has a corresponding link mapping to it.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 9.2.2023
 */
public class Story {

  private final String title;
  private final Map<Link, Passage> passages;
  private final Passage openingPassage;

  /**
   * Creates an instance of a story object.
   *
   * @param title          The title of the story.
   * @param openingPassage The first passage of the story. This passage is also included in the
   *                       stories list of passages.
   * @throws IllegalArgumentException if title is null or blank, or if openingPassage is null.
   */
  public Story(String title, Passage openingPassage) throws IllegalArgumentException {
    if (title == null) {
      throw new IllegalArgumentException("The title of the story cannot be null.");
    }
    if (title.isBlank()) {
      throw new IllegalArgumentException("The title of the story cannot be blank.");
    }
    if (openingPassage == null) {
      throw new IllegalArgumentException("The opening passage of the story cannot be null.");
    }

    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
    addPassage(openingPassage);
  }

  /**
   * Returns the story title.
   *
   * @return The story title.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Returns the first passage of the story.
   *
   * @return The first passage of the story.
   */
  public Passage getOpeningPassage() {
    return this.openingPassage;
  }

  /**
   * Adds a passage with a link as key to the passages hashmap. The link's text and reference are
   * both created from the passages title. The link's reference will therefore link to the passage's
   * title.
   *
   * @param passage The passage that is added as value to the passages hashmap.
   * @throws IllegalArgumentException if passage in null, or if there already exists a key-value
   *                                  pair with the same key. This is done to prevent the existing
   *                                  value to be overwritten by the added value.
   */
  public void addPassage(Passage passage) throws IllegalArgumentException {
    if (passage == null) {
      throw new IllegalArgumentException(
          "Cannot add passage to story because the passage is null.");
    }

    Link key = new Link(passage.getTitle(), passage.getTitle());
    if (passages.containsKey(key)) {
      throw new IllegalArgumentException("Cannot add a duplicate link-key to the passages hashmap");
    }

    passages.put(key, passage);
  }

  /**
   * Removes a given passage from the passages hashmap using a link to identify the value-half of
   * the key-value pair.
   *
   * @param link The key that references the passage that will be removed.
   * @throws IllegalArgumentException If the link is null, links to a non-existent passage or if
   *                                  there exists other passages that links to the referenced
   *                                  passage.
   */
  public void removePassage(Link link) throws IllegalArgumentException {
    if (link == null) {
      throw new IllegalArgumentException(
          "Cannot remove passage from story because the link is null.");
    }
    if (passages.get(link) == null) {
      throw new IllegalArgumentException("The linked passage does not exist.");
    }
    if (getPassages().stream().anyMatch(passage -> passage.getLinks().contains(link))) {
      throw new IllegalArgumentException(
          "Cannot remove a passage that has other passages linking to it.");
    }
    passages.remove(link);
  }

  /**
   * Finds and returns a list of dead links. A link is dead if it references a passage that is
   * non-existent in the passages hashmap.
   *
   * @return A list of story's dead links.
   */
  public List<Link> getBrokenLinks() {
    return getLinks().stream()
        .filter(link -> passages.get(link) == null)
        .toList();
  }

  /**
   * Gets all links that are contained by all passages in the passages hashmap..
   *
   * @return A list of all links in the story.
   */
  private List<Link> getLinks() {
    return getPassages().stream().map(Passage::getLinks).toList().stream()
        .flatMap(Collection::stream).toList();
  }

  /**
   * Gets a passage that is referenced by the input link.
   *
   * @param link The key that references the passage value in a key-value pair in the story.
   * @return The passage that the link references.
   * @throws IllegalArgumentException If link is null.
   */
  public Passage getPassage(Link link) throws IllegalArgumentException {
    if (link == null) {
      throw new IllegalArgumentException("Cannot get the passage because the link is null.");
    }
    if (passages.get(link) == null) {
      throw new IllegalArgumentException("This link links to a passage that does not exist");
    }

    return passages.get(link);
  }

  /**
   * Gets all passages from passages map.
   *
   * @return A Collection of all values in the passages map.
   */
  public Collection<Passage> getPassages() {
    Collection<Passage> passageCollection = new ArrayList<>();
    for (Map.Entry<Link, Passage> entry : passages.entrySet()) {
      passageCollection.add(entry.getValue());
    }
    return passageCollection;
  }

  /**
   * Gets all the final passages of the story. A final passage is where the branch of a story ends.
   * This passage has no links.
   *
   * @return A collection of passages with no links.
   */
  public Collection<Passage> getEndPassages() {
    return getPassages().stream()
        .filter(passage -> !passage.hasLinks())
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {

    StringBuilder passagesString = new StringBuilder();
    for (Passage passage : getPassages()) {
      passagesString.append("\n\n").append(passage.toString());
    }
    return title + passagesString;
  }
}
