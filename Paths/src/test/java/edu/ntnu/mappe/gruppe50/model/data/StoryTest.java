package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {

  @Nested
  class StoryConstructor {
    Passage passage;
    Story story;

    @BeforeEach
    void setUp() {
      passage = new Passage("testTitle", "testContent");
    }


    @Test
    @DisplayName("Test constructor with valid arguments")
    void testConstructorWithValidArguments() {
      story = new Story("testTitle", passage);
      assertEquals("testTitle", story.getTitle());
      assertEquals(passage, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if title is null")
    void testConstructorThrowsIllegalArgumentExceptionIfTitleIsNull() {
      assertThrows(IllegalArgumentException.class, () -> story = new Story(null, passage));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if openingPassage is null")
    void testConstructorThrowsIllegalArgumentExceptionIfOpeningPassageIsNull() {
      assertThrows(IllegalArgumentException.class, () -> passage = new Passage("testTitle", null));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if title is blank")
    void testConstructorThrowsIllegalArgumentExceptionIfTitleIsPlank() {
      assertThrows(IllegalArgumentException.class, () -> story = new Story("", passage));
    }
  }

  @Nested
  class StoryGetters {

    Story story;
    Passage passage, passage1;
    Link link;

    @BeforeEach
    void setUp() {
      passage = new Passage("testTitle", "testContent");
      passage1 = new Passage("testTitle1", "testContent1");
      story = new Story("testTitle", passage);
    }

    @Test
    @DisplayName("Test title getter returns 'testTitle' if title attribute is 'testTitle'")
    void testTitleGetterReturnsTestTitleIfTitleAttributeIsTestTitle() {
      assertEquals("testTitle", story.getTitle());
    }

    @Test
    @DisplayName("Test openingPassage getter returns passage if openingPassage attribute is passage")
    void testOpeningPassageGetterReturnsPassageIfOpeningPassageAttributeIsPassage() {
      assertEquals(passage, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test Passage getter given a valid link")
    void testGetPassageWithValidLink() {
      link = new Link(passage1.getTitle(), passage1.getTitle());
      story.addPassage(passage1);
      assertEquals(passage1, story.getPassage(link));
    }

    @Test
    @DisplayName("Test Passage getter with null link")
    void testGetPassageWithNullLink() {
      assertThrows(IllegalArgumentException.class, () -> story.getPassage(null));
    }

    @Test
    @DisplayName("Test Passage getter with a broken link")
    void testPassageGetterWithABrokenLink() {
      Link link1 = new Link("text", "reference");
      assertThrows(IllegalArgumentException.class, () -> story.getPassage(link1));
    }

    @Test
    @DisplayName("Test getEndPassages where no passages are final")
    void testEndPassagesWithNoEndPassages() {
      passage.addLink(new Link("text", "reference"));

      assertEquals(0, story.getEndPassages().size());
    }

    @Test
    @DisplayName("Test getEndPassages with a single final passage")
    void testEndPassagesWithOneEndPassages() {
      assertEquals(1, story.getEndPassages().size());
    }
  }

  @Nested
  @DisplayName("Test adding and removing passages")
  class StoryPassage {

    Passage openingPassage, passage1, passage2;
    Story story;

    @BeforeEach
    void setUp() {
      openingPassage = new Passage("openingTitle", "openingContent");
      passage1 = new Passage("testTitle", "testContent");
      story = new Story("grandTitle", openingPassage);
    }

    @Test
    @DisplayName("Test add passage with valid passage")
    void testAddPassageWithValidPassage() {
      story.addPassage(passage1);
      assertTrue(story.getPassages().contains(openingPassage));
      assertTrue(story.getPassages().contains(passage1));
      assertEquals(2, story.getPassages().size());
    }

    @Test
    @DisplayName("Test addPassage throws Illegal Argument Exception if passage is null")
    void testAddPassageWherePassageIsNull() {
      assertThrows(IllegalArgumentException.class, () -> story.addPassage(null));
    }

    @Test
    @DisplayName("Testing addPassage with duplicate passage throws IllegalArgumentException")
    void testAddingDuplicatePassage() {
      story.addPassage(passage1);
      assertThrows(IllegalArgumentException.class, () -> story.addPassage(passage1));
    }

    @Test
    @DisplayName("Test removePassage with valid passage")
    void testRemovePassageWithValidPassage() {
      story.addPassage(passage1);
      story.removePassage(new Link(passage1.getTitle(), passage1.getTitle()));

      assertFalse(story.getPassages().contains(passage1));
      assertEquals(1, story.getPassages().size());
      assertThrows(IllegalArgumentException.class, () -> story.getPassage(new Link("testTitle", "testContent")));
    }

    @Test
    @DisplayName("Test removePassage throws Illegal Argument Exception if passage is null")
    void testRemovePassageWherePassageIsNull() {
      assertThrows(IllegalArgumentException.class, () -> story.removePassage(null));
    }

    @Test
    @DisplayName("Test removePassage throws Illegal Argument Exception if link does not link to a passage.")
    void testRemovePassageIfLinkLinksToNull() {
      assertThrows(IllegalArgumentException.class,
        () -> story.removePassage(new Link(passage1.getTitle(), passage1.getTitle())));
    }

    @Test
    @DisplayName("Test removePassage throws Illegal Argument Exception if other passages links to it")
    void testRemovePassageThrowsIllegalArgumentExceptionIfOtherPassagesLinksToIt() {
      passage2 = new Passage("title", "content");
      passage2.addLink(new Link(passage1.getTitle(), passage1.getTitle()));
      story.addPassage(passage1);
      story.addPassage(passage2);

      assertThrows(IllegalArgumentException.class, ()
        -> story.removePassage(new Link(passage1.getTitle(), passage1.getTitle())));
    }
  }

  @Nested
  class BrokenLinks {

    Story story;
    Passage openingPassage;
    Passage passage1;

    @BeforeEach
    void setUp() {
      openingPassage = new Passage("openingTitle", "openingContent");
      passage1 = new Passage("testTitle", "testContent");
      story = new Story("grandTitle", openingPassage);
      story.addPassage(passage1);
    }

    @Test
    @DisplayName("Test getBrokenLinks with zero broken links")
    void testGetBrokenLinksWithZeroBrokenLink() {
      assertEquals(0, story.getBrokenLinks().size());
    }

      @Test
      @DisplayName("Test getBrokenLinks with one broken link")
      void testGetBrokenLinksWithOneBrokenLink() {
        Link link = new Link("testLink","testReference");
        passage1.addLink(link);
        List<Link> linkList = story.getBrokenLinks();
        assertEquals(link, linkList.get(0));
      }
    }
  }

