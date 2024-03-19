package edu.ntnu.mappe.gruppe50.model.fileutils;

import edu.ntnu.mappe.gruppe50.model.data.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerFileHandlingTest {
  @Nested
  @DisplayName("Test readPlayerFromFile and writePlayerToFIle")
  class ReadAndWriteTest {

    String filePath = System.getProperty("user.dir") + "/src/test/resources/playerFiles/basic_players.players";

    @Test
    void testWritingAndReadingPlayerWithNameHealthGoldParametersToFile() throws IOException {
      Player player1 = new Player.Builder("Ole").health(50).gold(150).build();
      PlayerFileHandling.writePlayerToFile(player1, "test", filePath);
      Player player2 = PlayerFileHandling.readPlayerFromFile("Ole", filePath);

      assertEquals(player1, player2);
    }

    @Test
    @DisplayName("Test readPlayerFromFile throws IllegalArgumentException if playerName is null")
    void testReadPlayerFromFileWithPlayerNameAsNull() {
      assertThrows(IllegalArgumentException.class, () -> PlayerFileHandling.readPlayerFromFile(null, filePath));
    }

    @Test
    @DisplayName("Test readPlayerFromFile throws IllegalArgumentException if playerName is blank")
    void testReadPlayerFromFileWithPlayerNameAsBlank() {
      assertThrows(IllegalArgumentException.class, () -> PlayerFileHandling.readPlayerFromFile("", filePath));
    }

    @Test
    @DisplayName("Test read and write player with only name as parameters.")
    void testReadAndWriteWithNameAsParameters() throws IOException {
      Player player1 = new Player.Builder("Doffen").build();
      PlayerFileHandling.writePlayerToFile(player1, "test", filePath);
      Player player2 = PlayerFileHandling.readPlayerFromFile("Doffen", filePath);
      assertEquals(player1, player2);
    }

    @Test
    @DisplayName("Test read and write player with name and health as parameters.")
    void testReadAndWriteWithNameAndHealthAsParameters() throws IOException {
      Player player1 = new Player.Builder("Dole").health(50).build();
      PlayerFileHandling.writePlayerToFile(player1, "test", filePath);
      Player player2 = PlayerFileHandling.readPlayerFromFile("Dole", filePath);
      assertEquals(player1, player2);
    }

    @Test
    @DisplayName("Test read and write player with only name and gold as parameters.")
    void testReadAndWriteWithNameAndGoldAsParameters() throws IOException {
      Player player1 = new Player.Builder("Ola").gold(50).build();
      PlayerFileHandling.writePlayerToFile(player1, "test", filePath);
      Player player2 = PlayerFileHandling.readPlayerFromFile("Ola", filePath);
      assertEquals(player1, player2);
    }

    @Test
    @DisplayName("Test read and write player with only name, health and gold as parameters.")
    void testReadAndWriteWithNameHealthAndGoldAsParameters() throws IOException {
      Player player1 = new Player.Builder("Pål").health(50).gold(50).build();
      PlayerFileHandling.writePlayerToFile(player1, "test", filePath);
      Player player2 = PlayerFileHandling.readPlayerFromFile("Pål", filePath);
      assertEquals(player1, player2);
    }
  }

  @Nested
  @DisplayName("Test writeToFile and readFromFile with invalid formats")
  class InvalidFormatsWriteAndRead {
    @Test
    @DisplayName("Test readPlayerFromFile with empty lines between players")
    void testReadPlayerFromFileWithEmptyLinesBetweenPlayers() throws IOException {
      Player testPlayer1 = new Player.Builder("Frank").health(50).gold(50).build();
      Player testPlayer2 = PlayerFileHandling.readPlayerFromFile("Frank", System.getProperty("user.dir") + "/src/test/resources/playerFiles/empty_lines_between_players.players");
      assertEquals(testPlayer1, testPlayer2);
    }
  }

  @Nested
  @DisplayName("Test readPlayerAvatarImagePath")
  class testReadPlayerAvatarImagePath {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/playerFiles/read_player_avatar_image.players";

    @Test
    @DisplayName("Test readPlayerAvatarImagePath with existing name")
    void readPlayerAvatarImagePathWithExistingName() throws IOException {
      String playerAvatarImageFilePath = PlayerFileHandling.readPlayerAvatarImagePath("Ole", filePath);
      assertEquals("testPlayerAvatarImageFilePath", playerAvatarImageFilePath);
    }

    @Test
    @DisplayName("Test readPlayerAvatarImagePath with non existing name")
    void readPlayerAvatarImagePathWithNonExistingName() throws IOException {
      String playerAvatarImageFilePath = PlayerFileHandling.readPlayerAvatarImagePath("NonExistingName", filePath);
      assertNull(playerAvatarImageFilePath);
    }
  }

  @Nested
  @DisplayName("Test removePlayerFromFile")
  class removePlayerFromFile {
    String filePath = System.getProperty("user.dir") + "/src/test/resources/playerFiles/remove_player_from_file.players";

    @BeforeEach
    void setUp() throws IOException {
      Player player = new Player.Builder("Roald").health(50).gold(50).build();
      PlayerFileHandling.writePlayerToFile(player, "testFilePath", filePath);
    }

    @Test
    @DisplayName("Remove existing player from file")
    void removeExistingPlayerFromFile() throws IOException {
      PlayerFileHandling.removePlayerFromFile("Roald", filePath);
      assertNull(PlayerFileHandling.readPlayerFromFile("Roald", filePath));
    }

    @Test
    @DisplayName("Remove non existing player from file")
    void removeNonExistingPlayerFromFile() throws IOException {
      assertFalse(PlayerFileHandling.removePlayerFromFile("Frank", filePath));
    }
  }

  @Nested
  @DisplayName("Test read all players from file")
  class readAllPlayersFromFile {
    Player player1, player2, player3;

    @BeforeEach
    void setUp() {
      player1 = new Player.Builder("Ole").health(50).gold(50).build();
      player2 = new Player.Builder("Dole").health(100).gold(100).build();
      player3 = new Player.Builder("Doffen").health(150).gold(150).build();
    }
    @Test
    @DisplayName("test read all players from file")
    void testReadAllPlayersFromFile() throws IOException {
      List<Player> playerList = PlayerFileHandling.readAllPlayersFromFile(System.getProperty("user.dir") + "/src/test/resources/playerFiles/read_all_players_from_file.players");
      assertTrue(playerList.contains(player1) && playerList.contains(player2) && playerList.contains(player3));
    }

    @Test
    @DisplayName("test read all players from file with blank lines between players")
    void readAllPlayersFromFileWithBlankLinesBetweenPlayers() throws IOException {
      List<Player> playerList = PlayerFileHandling.readAllPlayersFromFile(System.getProperty("user.dir") + "/src/test/resources/playerFiles/read_all_players_from_file.players");
      assertTrue(playerList.contains(player1) && playerList.contains(player2) && playerList.contains(player3));
    }
  }
}
