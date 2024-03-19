package edu.ntnu.mappe.gruppe50.model.fileutils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StoryElementFormatTest {

  @Nested
  @DisplayName("Test valid story element formats")
  class ValidFormats {
    String linkPattern, passageTitlePattern, actionPattern, goalPattern;

    @BeforeEach
    void setUp() {
      linkPattern = "[text](reference)";
      passageTitlePattern = "::passagetitele";
      actionPattern = "{type:value}";
      goalPattern = "<type:value>";
    }

    @Test
    @DisplayName("Test match on valid link")
    void testValidLink() {
      assertTrue(StoryElementFormat.LINK.matches(linkPattern));
    }

    @Test
    @DisplayName("Test match on valid passage title")
    void testValidPassageTitle() {
      assertTrue(StoryElementFormat.PASSAGE_TITLE.matches(passageTitlePattern));
    }

    @Test
    @DisplayName("Test match on valid action")
    void testValidAction() {
      assertTrue(StoryElementFormat.ACTION.matches(actionPattern));
    }

    @Test
    @DisplayName("Test match on valid goal")
    void testValidGoal() {
      assertTrue(StoryElementFormat.GOAL.matches(goalPattern));
    }

    @Test
    @DisplayName("Test match on link without text or content")
    void testLinkWithoutTextOrContent() {
      assertTrue(StoryElementFormat.LINK.matches("[]()"));
    }

    @Test
    @DisplayName("Test match on passage title without title")
    void testPassageTitleWithoutTitle() {
      assertTrue(StoryElementFormat.PASSAGE_TITLE.matches("::"));
    }

    @Test
    @DisplayName("Test match on action without type or value")
    void testActionWithoutTypeOrValue() {
      assertTrue(StoryElementFormat.ACTION.matches("{:}"));
    }
  }

  @Nested
  @DisplayName("Test invalid story element formats")
  class InvalidFormats {

    String linkPattern, passageTitlePattern, actionPattern, goalPattern;

    @BeforeEach
    void setUp() {
      linkPattern = "[text(reference)";
      passageTitlePattern = ":passagetitele";
      actionPattern = "{typevalue}";
      goalPattern = "<type:value";
    }

    @Test
    @DisplayName("Test matches on link without second square bracket")
    void testLinkWithMissingEndBracket() {
      assertFalse(StoryElementFormat.LINK.matches(linkPattern));
    }

    @Test
    @DisplayName("Test matches on passage title with single colon")
    void testPassageTitleWithSingeColon() {
      assertFalse(StoryElementFormat.LINK.matches(linkPattern));
    }

    @Test
    @DisplayName("Test matches on action without colon")
    void testActionWithoutColon() {
      assertFalse(StoryElementFormat.ACTION.matches(actionPattern));
    }

    @Test
    @DisplayName("Test matches on goal without closing bracket")
    void testGoalWithMissingEndBracket() {
      assertFalse(StoryElementFormat.GOAL.matches(goalPattern));
    }
  }
}
