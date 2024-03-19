package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.Passage;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassageTest {

  @Nested
  class PassageConstructor {

    Passage passage;

    @Test
    @DisplayName("Test constructor with valid arguments")
    void testConstructorWithValidArguments() {
      passage = new Passage("testTitle", "testContent");
      assertEquals("testTitle", passage.getTitle());
      assertEquals("testContent", passage.getContent());
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if title is null")
    void testConstructorThrowsIllegalArgumentExceptionIfTitleIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> passage = new Passage(null, "testContent"));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if content is null")
    void testConstructorThrowsIllegalArgumentExceptionIfContentIsNull() {
      assertThrows(IllegalArgumentException.class, () -> passage = new Passage("testTitle", null));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if title is blank")
    void testConstructorThrowsIllegalArgumentExceptionIfTitleIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> passage = new Passage("", "testContent"));
    }

    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if content is blank")
    void testConstructorThrowsIllegalArgumentExceptionIfContentIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> passage = new Passage("testTitle", ""));
    }
  }

    @Nested
    class LinkMutators {
      Passage passage;
      Link link;

      @BeforeEach
      void setUp() {
        passage = new Passage("testTitle", "testContent");
      }

      @Test
      @DisplayName("Test add link to passage with valid link")
      void testAddLinkToPassageWithValidLink() {
        link = new Link("testText", "testReference");
        assertTrue(passage.addLink(link));
        assertTrue(passage.getLinks().contains(link));
      }

      @Test
      @DisplayName("Test add link to passage where link is null")
      void testAddLinkToPassageWhereLinkIsNull() {
        assertThrows(IllegalArgumentException.class, () -> passage.addLink(null));
      }

      @Test
      @DisplayName("Test add link to passage with valid link")
      void testRemoveLinkFromPassageWithValidLink() {
        link = new Link("testText", "testReference");
        passage.addLink(link);
        assertTrue(passage.removeLink(link));
        assertFalse(passage.getLinks().contains(link));

      }

      @Test
      @DisplayName("Test remove link from passages where link is null")
      void testRemoveLinkFromPassageWhereLinkIsNull() {
        assertThrows(IllegalArgumentException.class, () -> passage.removeLink(null));
      }
    }

    @Nested
    class PassageGetters {
      Passage passage;

      @BeforeEach
      void setUp() {
        passage = new Passage("testTitle", "testContent");
      }

      @Test
      @DisplayName("Test title getter returns 'testTitle' if title attribute is 'testTitle'")
      void testTitleGetterReturnsTestTitleIfTitleAttributeIsTestTitle() {
        assertEquals("testTitle", passage.getTitle());
      }

      @Test
      @DisplayName("Test content getter returns 'testContent' if content attribute is 'testContent'")
      void testContentGetterReturnsTestContentIfContentAttributeIsTestContent() {
        assertEquals("testContent", passage.getContent());
      }

      @Test
      @DisplayName("Test link getter returns link if links list contains only link")
      void testLinkGetterReturnsLinkIfLinksListContainsOnlyLink() {
        Link link = new Link("testText", "testReference");
        List<Link> linkList = new ArrayList<>();
        linkList.add(link);
        passage.addLink(link);
        assertEquals(linkList, passage.getLinks());
      }
    }

    @Nested
    class HasLinks {
      Passage passage;
      Link link;

      @BeforeEach
      void setUp() {
        passage = new Passage("testTitle", "testContent");
        link = new Link("testText", "testReference");
      }

      @Test
      @DisplayName("Test hasLinks if passage does not have links")
      void testHasLinksIfPassageDoesNotHaveLinks() {
        assertFalse(passage.hasLinks());
      }

      @Test
      @DisplayName("Test hasLinks if passage has links")
      void testHasLinksIfPassageHasLinks() {
        passage.addLink(link);
        assertTrue(passage.hasLinks());
      }
    }

    @Nested
    class PassageEquals {
      Passage passage1;
      Passage passage2;

      @BeforeEach
      void setUp() {
        passage1 = new Passage("testTitle", "testContent");
        passage2 = new Passage("testTitle", "testContent");
      }

      @Test
      @DisplayName("Test Equals with equal passages")
      void testEqualsWithEqualPassages() {
        assertEquals(passage1, passage2);
      }

      @Test
      @DisplayName("Test Equals with equal passages and equal links")
      void testEqualsWithEqualPassagesAndEqualLinks() {
        passage1.addLink(new Link("text", "reference"));
        passage2.addLink(new Link("text", "reference"));
        assertEquals(passage1, passage2);
      }

      @Test
      @DisplayName("Test Equals with equal links that are in different order")
        void testEqualLinksButInDifferentOrder() {
          Link link = new Link("testText", "testReference");
          Link link1 = new Link("text", "reference");

          passage2.addLink(link);
          passage2.addLink(link1);

          Passage passage3 = new Passage(passage2.getTitle(), passage2.getContent());
          passage3.addLink(link1);
          passage3.addLink(link);

          assertEquals(passage2, passage3);
        }

      @Test
      @DisplayName("Test Equals with different link lists")
      void testEqualsWithDifferentLinkLists() {
        Link link = new Link("testText", "testReference");
        passage2.addLink(link);

        assertNotEquals(passage1, passage2);
      }
    }

    @Nested
    class PassageHashCode {
      Passage passage1, passage2;

      @BeforeEach
      void setUp() {
        passage1 = new Passage("testTitle", "testContent");
        passage2 = new Passage("testTitle", "testContent");
      }

      @Test
      @DisplayName("Test hashcode")
      void testHashCode() {
        assertEquals(passage1.hashCode(), passage2.hashCode());
      }
    }

    @Test
    @DisplayName("Test Passage toString")
    void testToString() {
      Passage passage = new Passage("testTitle", "testContent");
      Link link = new Link("testText", "testReference");
      passage.addLink(link);

      assertEquals("::testTitle\ntestContent\n[testText](testReference)", passage.toString());
  }
}

