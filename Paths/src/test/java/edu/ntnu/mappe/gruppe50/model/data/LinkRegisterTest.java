package edu.ntnu.mappe.gruppe50.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import edu.ntnu.mappe.gruppe50.model.data.LinkRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LinkRegisterTest {

  @Nested
  @DisplayName("Test add and remove link")
  class AddAndRemoveLink {
    LinkRegister register;

    Link link1, link2;

    @BeforeEach
    void setUp() {
      register = new LinkRegister();
      link1 = new Link("text1", "reference1");
      link2 = new Link("text2", "reference2");
    }

    @Test
    @DisplayName("Test adding and removing valid links")
    void testAddAndRemoveValidLinks() {
      register.addLink(link1);
      register.addLink(link2);
      assertEquals(2, register.getLinks().size());
      register.removeLink(link1);
      assertEquals(1, register.getLinks().size());
    }

    @Test
    @DisplayName("Test adding null throws Illegal Argument Exception")
    void testAddingNull() {
      assertThrows(IllegalArgumentException.class,
          () -> register.addLink(null));
    }

    @Test
    @DisplayName("Test removing null throws Illegal Argument Exception")
    void testRemovingNull() {
      assertThrows(IllegalArgumentException.class,
          () -> register.removeLink(null));
    }
  }


  @Nested
  @DisplayName("Test isEmpty")
  class IsEmpty {

    LinkRegister register;

    Link link;

    @BeforeEach
    void setUp() {
      register = new LinkRegister();
      link = new Link("text", "reference");
    }

    @Test
    @DisplayName("Test isEmpty on empty register returns true")
    void testIsEmptyOnEmptyRegister() {
      assertTrue(register.isEmpty());
    }

    @Test
    @DisplayName("Test isEmpty on non empty register returns false")
    void testIsEmptyOnNonEmptyRegister() {
      register.addLink(link);
      assertFalse(register.isEmpty());
    }
  }

  @Nested
  @DisplayName("Test contains")
  class Contains {

    LinkRegister register;

    Link link = new Link("text", "reference");
    @BeforeEach
    void setUp()  {
      register = new LinkRegister();
      register.addLink(link);
    }

    @Test
    @DisplayName("Test contains with a link that the register contains")
    void testContainsWithLinkThatIsContained() {
      assertTrue(register.contains(link));
    }

    @Test
    @DisplayName("Test contains with a link that the register does not contain")
    void testContainsWithLinkThatIsNotContained() {
      assertFalse(register.contains(new Link("t", "r")));
    }
  }
}
