package edu.ntnu.mappe.gruppe50.model.data;

import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfo;
import edu.ntnu.mappe.gruppe50.model.data.UserStoryInfoRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoryInfoRegisterTest {

  @Nested
  class TestAddUserStoryInfo {
    UserStoryInfoRegister userStoryInfoRegister;
    UserStoryInfo userStoryInfo;

    @BeforeEach
    public void setUp() {
      userStoryInfoRegister = new UserStoryInfoRegister();
      userStoryInfo = new UserStoryInfo("testFileName", "testFilePath", 10);
    }

    @Test
    void testAddUserStoryInfo() {
      boolean added = userStoryInfoRegister.addUserStoryInfo(userStoryInfo);
      assertTrue(added);
      assertEquals(1, userStoryInfoRegister.getUserStoryInfos().size());
      assertTrue(userStoryInfoRegister.getUserStoryInfos().contains(userStoryInfo));
    }

    @Test
    void testAddUserStoryInfoWithNullAsUserStoryInfo() {
      assertThrows(IllegalArgumentException.class, () -> userStoryInfoRegister.addUserStoryInfo(null));
    }

    @Test
    void testAddUserStoryInfoWithDuplicateUserStoryInfo() {
      userStoryInfoRegister.addUserStoryInfo(userStoryInfo);
      assertThrows(IllegalArgumentException.class, () -> userStoryInfoRegister.addUserStoryInfo(userStoryInfo));
    }
  }

  @Nested()
  class testContains {
    UserStoryInfoRegister userStoryInfoRegister;
    UserStoryInfo userStoryInfo;

    @BeforeEach
    void setUp() {
      userStoryInfoRegister = new UserStoryInfoRegister();
      userStoryInfo = new UserStoryInfo("testFileName", "testFilePath", 10);
    }

    @Test
    void testWhenUserStoryInfoRegisterContainsTheUserStoryInfo() {
      userStoryInfoRegister.addUserStoryInfo(userStoryInfo);
      assertTrue(userStoryInfoRegister.contains(userStoryInfo));
    }

    @Test
    void testWhenUserStoryInfoRegisterDoesNotContainTheUserStoryInfo() {
      assertFalse(userStoryInfoRegister.contains(userStoryInfo));
    }
  }

  @Nested()
  class testIsEmpty {
    UserStoryInfoRegister userStoryInfoRegister;
    UserStoryInfo userStoryInfo;

    @BeforeEach
    void setUp() {
      userStoryInfoRegister = new UserStoryInfoRegister();
      userStoryInfo = new UserStoryInfo("testFileName", "testFilePath", 10);
    }

    @Test
    void testWhenUserStoryInfoRegisterIsEmpty() {
      assertTrue(userStoryInfoRegister.isEmpty());
    }

    @Test
    void testWhenUserStoryInfoRegisterIsNotEmpty() {
      userStoryInfoRegister.addUserStoryInfo(userStoryInfo);
      assertFalse(userStoryInfoRegister.isEmpty());
    }
  }

  @Test
  void testGetUserStoryInfoPaths() {
    UserStoryInfoRegister userStoryInfoRegister = new UserStoryInfoRegister();
    UserStoryInfo userStoryInfo1 = new UserStoryInfo("testFileName1", "testFilePath1", 5);
    UserStoryInfo userStoryInfo2 = new UserStoryInfo("testFileName2", "testFilePath2", 10);
    userStoryInfoRegister.addUserStoryInfo(userStoryInfo1);
    userStoryInfoRegister.addUserStoryInfo(userStoryInfo2);

    List<String> paths = userStoryInfoRegister.getUserStoryInfoPaths();
    assertEquals(2, paths.size());
    assertTrue(paths.contains("testFilePath1"));
    assertTrue(paths.contains("testFilePath2"));
  }

  @Nested
  class testRemoveUserStoryInfo {
    UserStoryInfoRegister userStoryInfoRegister;
    UserStoryInfo userStoryInfo;

    @BeforeEach
    void setUp() {
      userStoryInfoRegister = new UserStoryInfoRegister();
      userStoryInfo = new UserStoryInfo("testFileName", "testFilePath", 10);
      userStoryInfoRegister.addUserStoryInfo(userStoryInfo);
    }

    @Test
    void testRemoveUserStoryInfo() {
      userStoryInfoRegister.removeUserStoryInfo(userStoryInfo);
      assertFalse(userStoryInfoRegister.contains(userStoryInfo));
      assertTrue(userStoryInfoRegister.getUserStoryInfos().size() == 0);
    }

    @Test
    void testRemoveUserStoryInfoWhenUserStoryInfoIsNull() {
      assertThrows(IllegalArgumentException.class, () -> userStoryInfoRegister.removeUserStoryInfo(null));
    }

    @Test
    void testRemoveUserStoryInfoWhenUserStoryInfoIsNotItUserStoryInfos() {
      UserStoryInfo userStoryInfo1 = new UserStoryInfo("testFileName2", "testFilePath2", 10);
      assertThrows(IllegalArgumentException.class, () -> userStoryInfoRegister.removeUserStoryInfo(userStoryInfo1));

    }
  }
}