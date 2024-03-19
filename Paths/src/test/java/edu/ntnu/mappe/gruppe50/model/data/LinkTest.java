package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.Passage;
import edu.ntnu.mappe.gruppe50.model.data.actions.Action;
import edu.ntnu.mappe.gruppe50.model.data.actions.GoldAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.HealthAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.InventoryAction;
import edu.ntnu.mappe.gruppe50.model.data.actions.ScoreAction;
import edu.ntnu.mappe.gruppe50.model.data.goals.Goal;
import edu.ntnu.mappe.gruppe50.model.data.goals.GoldGoal;
import edu.ntnu.mappe.gruppe50.model.data.goals.ScoreGoal;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.mappe.gruppe50.model.data.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {
  @Nested
  class LinkConstructor {
    Link link;
    @Test
    @DisplayName("Test constructor with valid arguments")
    void testConstructorWithValidArguments() {

      link = new Link("text", "reference");
      assertEquals("text", link.getText());
      assertEquals("reference", link.getReference());
    }
    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if text is null")
    void testConstructorThrowsIllegalArgumentExceptionIfTextIsNull() {

      assertThrows(IllegalArgumentException.class, () -> link = new Link(null, "reference"));
    }
    @Test
    @DisplayName("Test constructor throws Illegal Argument Exception if text is empty string")
    void testConstructorThrowsIllegalArgumentExceptionIfTextIsEmptyString() {

      assertThrows(IllegalArgumentException.class, () -> link = new Link("", "reference"));
    }
    @Test
    @DisplayName("Test constructor throw Illegal Argument Exception if reference is null")
    void testConstructorThrowsIllegalArgumentExceptionIfReferenceIsNull() {

      assertThrows(IllegalArgumentException.class, () -> link = new Link("text", null));
    }
    @Test
    @DisplayName("Test constructor throw Illegal Argument Exception if reference is null")
    void testConstructorThrowsIllegalArgumentExceptionIfReferenceIsEmptyString() {

      assertThrows(IllegalArgumentException.class, () -> link = new Link("text", "    "));
    }

    @Test
    @DisplayName("Test deep copy constructor creates identical copy")
    void testDeepCopyConstructorCreatesIdenticalCopy() {
      link = new Link("text", "reference");
      link.addAction(new HealthAction(10));
      link.addAction(new GoldAction(10));
      Link linkCopy = new Link(link);
      assertEquals(link, linkCopy);
    }
  }

  @Nested
  class LinkGetters {
    Link link;
    @BeforeEach
    void setUp() {
      link = new Link("text", "reference");
    }

    @Test
    @DisplayName("Test text getter returns 'text' if text attribute is 'text")
    void testTextGetterReturnsTextIfTextAttributeIsText() {
      assertEquals("text", link.getText());
    }

    @Test
    @DisplayName("Test reference getter returns 'reference' if reference attribute is 'reference")
    void testReferenceGetterReturnsReferenceIfReferenceAttributeIsText() {
      assertEquals("reference", link.getReference());
    }

    @Test
    @DisplayName("Test getActions returns a list equal to 'actions'")
    void testGetActionsReturnsAListEqualToActions() {
      Action action = new HealthAction(10);
      link.addAction(action);
      List<Action> actions = new ArrayList<>();
      actions.add(action);
      assertEquals(actions, link.getActions());
    }

    @Test
    @DisplayName("Test getActions getter returns empty list if actions is empty")
    void testGetActionsReturnsEmptyListIfActionsIsEmpty() {
      assertEquals(0, link.getActions().size());

    }
  }

  @Nested
  class LinkActionMutators {

    Link link;
    Action action1;
    Action action2;
    @BeforeEach
    void setUp() {
     link = new Link("text", "reference");
     action1 = new InventoryAction("sword");
     action2 = new ScoreAction(10);
    }

    @Test
    @DisplayName("Test adding valid actions")
    void testAddingValidActions() {
      link.addAction(action1);
      link.addAction(action2);
      assertEquals(2, link.getActions().size());
    }

    @Test
    @DisplayName("Test adding null to actions throws illegal argument exception")
    void testAddingNullToActionsThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> link.addAction(null));
    }

    @Test
    @DisplayName("Test removing valid actions")
    void testRemovingValidActions() {
      link.addAction(action1);
      link.addAction(action2);
      link.removeAction(action1);
      link.removeAction((action2));

      assertEquals(0, link.getActions().size());
    }

    @Test
    @DisplayName("Test removing null to actions throws illegal argument exception")
    void testRemovingNullFromActionsThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> link.removeAction(null));
    }

    @Test
    @DisplayName("Test adding to actions of the same type throws Illegal argument exception")
    void testAddingDuplicateActionTypes() {
      link.addAction(new GoldAction(10));
      assertThrows(IllegalArgumentException.class, () -> link.addAction(new GoldAction(10)));
    }
  }

  @Nested
  class LinkGoalMutators {

    Link link;
    Goal goal1;
    Goal goal2;
    @BeforeEach
    void setUp() {
      link = new Link("text", "reference");
      goal1 = new GoldGoal(10);
      goal2 = new ScoreGoal(10);
    }

    @Test
    @DisplayName("Test adding valid goals")
    void testAddingValidGoals() {
      link.addGoal(goal1);
      link.addGoal(goal2);
      assertEquals(2, link.getGoals().size());
    }

    @Test
    @DisplayName("Test adding null to goals throws illegal argument exception")
    void testAddingNullToGoalsThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> link.addGoal(null));
    }

    @Test
    @DisplayName("Test removing valid goals")
    void testRemovingValidGoals() {
      link.addGoal(goal1);
      link.addGoal(goal2);
      link.removeGoal(goal1);
      link.removeGoal((goal2));

      assertEquals(0, link.getActions().size());
    }

    @Test
    @DisplayName("Test removing null from goals throws illegal argument exception")
    void testRemovingNullFromGoalsThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> link.removeGoal(null));
    }

    @Test
    @DisplayName("Test adding to goals of the same type throws Illegal argument exception")
    void testAddingDuplicateGoalTypes() {
      link.addGoal(new GoldGoal(10));
      assertThrows(IllegalArgumentException.class, () -> link.addGoal(new GoldGoal(10)));
    }
  }

  @Nested
  class LinkEquals {
    Link link1, link2, link3;
    @BeforeEach
    void setUp() {
      link1 = new Link("text", "reference");
      link2 = new Link("text", "reference");
      link3 = new Link("notText", "reference");
    }
    @Test
    @DisplayName("Test Equals with equal text and reference")
    void testEqualsWithEqualTextAndReference() {
      assertEquals(link1, link2);
    }

    @Test
    @DisplayName("Test Equals with equal reference but different text")
    void testEqualsWithEqualReference() {
      assertEquals(link1, link3);
    }

    @Test
    @DisplayName("Test Equals with equal actions, text and reference")
    void testEqualsWithEqualActions() {
      link1.addAction(new HealthAction(10));
      link2.addAction(new HealthAction(10));
      assertEquals(link1,link2);
    }
    @Test
    @DisplayName("Test Equals with unequal actions but equal reference")
    void testEqualsWithUnEqualActions() {
      link1.addAction(new HealthAction(10));
      link2.addAction(new HealthAction(20));
      assertEquals(link1,link2);
    }

    @Test
    @DisplayName("Test Equals with different references")
    void testEqualsWithDifferentReferences() {
      Link link = new Link("text", "notReference");
      assertNotEquals(link1, link);
    }

    @Test
    @DisplayName("Test Equals when object is not a instance of link.")
    void testEqualsWhenObjectIsNotAInstanceOfLink() {
      Passage passage = new Passage("testTitle", "testContent");
      assertNotEquals(link1, passage);
    }
  }

  @Test
  @DisplayName("Test link ToString")
  void testLinkToString() {
    Link link = new Link("text", "reference");
    Action healthAction = new HealthAction(10);
    Action goldAction = new GoldAction(10);
    link.addAction(healthAction);
    link.addAction(goldAction);
    assertEquals(link.toString(), "[text](reference)\n{Health:10}\n{Gold:10}");
  }
}
