package edu.ntnu.mappe.gruppe50.view.popups;


import edu.ntnu.mappe.gruppe50.view.Main;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A help dialog that informs the user how to play the game. The dialog has several modes to choose
 * between, one for each scene in the GUI.
 *
 * @author Lars Mikkel Lødeng Nilsen
 * @since 28.03.2023
 */
public class HelpDialogBox {

  /**
   * The mode of the dialog. These represent each scene that has a help functionality.
   */
  public enum Mode {

    /**
     * When the scene is MainMenu.
     */
    MAIN_MENU,

    /**
     * When the scene is InGameScene.
     */
    IN_GAME,

    /**
     * When the scene is PredefinedStories.
     */
    PREDEFINED_STORIES,

    /**
     * When the scene is UserStories.
     */
    USER_STORIES,

    /**
     * When the scene is ChooseCharacter.
     */
    CHOOSE_CHARACTER,

    /**
     * When the scene is CreateCharacter.
     */
    CREATE_CHARACTER,

    /**
     * When the scene is GoalSelector.
     */
    GOAL_SELECTOR
  }

  /**
   * The mode of the dialog.
   */
  private final Mode mode;


  /**
   * Creates a new instance of the HelpDialogBox of a type specified by the parameter.
   *
   * @param mode The mode of the dialog box.
   */
  public HelpDialogBox(Mode mode) {
    this.mode = mode;
    // Create the content of the dialog
    createContent();
  }

  private String text;

  /**
   * Creates the content of the dialog.
   */
  private void createContent() {
    // Set title depending upon mode...
    String title;
    switch (this.mode) {
      case MAIN_MENU -> {
        title = "About";
        text = getMainMenuAbout();
      }
      case IN_GAME -> {
        title = "How to play";
        text = getInGameHelp();
      }
      case PREDEFINED_STORIES -> {
        title = "Choose story - Help";
        text = getPredefinedStoriesHelp();
      }
      case USER_STORIES -> {
        title = "Your stories - Help";
        text = getYourStoriesHelp();
      }
      case CHOOSE_CHARACTER -> {
        title = "Choose you character - Help";
        text = getChooseCharacterHelp();
      }
      case CREATE_CHARACTER -> {
        title = "Create you character - Help";
        text = getCreateCharacterHelp();
      }
      case GOAL_SELECTOR -> {
        title = "Select goals - Help";
        text = getGoalSelectorHelp();
      }
      default -> title = "Default - Help";
    }

    Stage dialog = new Stage();
    dialog.setMinWidth(750);
    dialog.setMaxWidth(750);

    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setTitle(title);

    Label contentTitle = new Label(title);
    contentTitle.setFont(new Font(18));

    Label content = new Label(text);

    Font font = new Font(16);
    content.setFont(font);

    Button okButton = new Button("OK");
    okButton.setMinSize(100, 50);
    okButton.setFocusTraversable(false);
    okButton.setOnAction(event -> {
      Main.playSoundOnClick();
      dialog.close();
    });

    //box that contains the text and button
    VBox contentHolder = new VBox(contentTitle, content, okButton);
    contentHolder.setAlignment(Pos.CENTER);
    contentHolder.setSpacing(50);

    //The canvas that contains the vbox - centralizes the text
    StackPane canvas = new StackPane(contentHolder);
    canvas.setAlignment(Pos.CENTER);

    //Gives spacing
    canvas.prefWidthProperty().bind(contentHolder
        .widthProperty().subtract(10));

    //Used for scrolling
    ScrollPane aboutScroller = new ScrollPane();
    aboutScroller.setContent(canvas);
    aboutScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
    aboutScroller.setMaxHeight(500);
    aboutScroller.setFocusTraversable(true);

    //Make stackpane fill entire parent's width
    canvas.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
            aboutScroller.getViewportBounds().getWidth(),
        aboutScroller.viewportBoundsProperty()));

    Scene scene = new Scene(aboutScroller);

    dialog.setScene(scene);
    dialog.showAndWait();
  }

  /**
   * Gets the help text for the MainMenu page.
   *
   * @return A string of text for how to navigate the main menu.
   */
  private String getMainMenuAbout() {
    return
        """ 
            Welcome to our game engine, created by two students studying at NTNU. Our
            goal is to provide you with a versatile platform for interactive storytelling.
            Whether you prefer to delve into our predefined story or unleash your
            creativity by making and uploading your own .paths file.
                  
            With our intuitive interface, you can create and customize
            characters that will play a role in your story. Set their goals and play them
            in either a predefined or a uploaded story.
                  
            Key Features:
            - Predefined Stories: Immerse yourself in captivating tales crafted by our
              team, designed to challenge and entertain.
            - Upload your own .paths file: Unleash your imagination by uploading your own
              Paths file and experience your unique story.
            - Character Creation: Create and customize your own character, and play them
              in a story.
            - Goal Setting: Define the goals your characters strive to achieve, influencing
              the direction and outcome of the story.
                  
            Get ready to embark on an unforgettable storytelling journey with our game
            engine. Create, play, and explore the endless possibilities that await you.
            Let your imagination soar and become the author of your own virtual worlds.
                  
            Enjoy your adventure!
                  
            Developers: Harry Linrui Xu and Lars Mikkel Lødeng Nilsen
            The "add", "next", "back" and "settings" icons are attributed to
            FlatIcon: https://www.flaticon.com
            """;
  }

  /**
   * Gets the help text for the InGameScene page.
   *
   * @return A string of text for how to navigate the InGameScene.
   */
  private String getInGameHelp() {
    return
        """                                                                           
             Navigating through passages:
             Press the buttons below the main text to choose how you wish to traverse the story.
             Clicking on the buttons brings you to a different passage in the story.
             A passage is made up of the title of the center box and its content.
             Each button represents a link. A link references a passage, which is how
             you can traverse the story.
              
             Player Inventory and Stats:
             The Heads-Up Display or HUD is located at the bottom of the screen and displays
             the player stats:
            - Player Profile Picture: Located on the left side of the HUD, this
              picture represents your character.
            - Health: The number next to the heart is your player's current health.
            - Gold: The number next to the gold coin is your player's current gold.
            - Score/XP: The number next to the XP symbol is your player's current score
              or experience points.
            - Inventory: Items in your inventory are represented by labelled images.
              Hover the mouse over an item to see it more clearly.
              
             Viewing Goals:
             To view the goals for the story and check if you have completed them or not,
             click the "View Goals" button. This will provide you with an overview of
             your goals and progress.
             """;
  }

  /**
   * Gets the help text for the PredefinedStories page.
   *
   * @return A string of text for how to navigate the predefined stories.
   */
  private String getPredefinedStoriesHelp() {
    return
        """      
            Selecting a Story:
            To choose a story, simply click on its image.
                  
            Moving Forward:
            After selecting a story, click the "Next" button to proceed. This will allow
            you to explore the chosen story in more depth.
                  
            Going Back:
            If you wish to return to the previous page or story, use the "Back" button.
            It will take you back one step at a time.
            """;
  }

  /**
   * Gets the help text for the UserStories page.
   *
   * @return A string of text for how to navigate the user stories.
   */
  private String getYourStoriesHelp() {
    return
        """  
            Adding a Story:
            To add a story, click the "Add Story" button. This will allow you upload
            a Paths file from your computer. Only valid .paths files can be uploaded.
            Once a story is added, it is displayed in the table and can now be played.
            The story's name, file path and number of broken links are displayed as well.
                  
            Removing a Story:
            Click the story in the table. Once selected, click the "Remove Story"
            button to delete it from your collection.
                  
            Broken links?
            A broken link is a link in a story that does not lead to an existing passage.
              
            Viewing Broken Links:
            Click the story in the table. Then, click on the "View Broken Links" button.
            This will provide you a list of any broken links in that particular story.
                  
            Playing a Story:
            To play a story, click it in the table. Once selected, click
            the "Next" button.
                  
            Going Back:
            If you need to return to the previous page, click the "Back" button.
            """;
  }

  /**
   * Gets the help text for the ChooseCharacter page.
   *
   * @return A string of text for how to navigate the Choose character view.
   */
  private String getChooseCharacterHelp() {
    return
        """
            Adding a Player:
            To create a new player, click the "New Player" button.
                  
            Removing a Player:
            To remove a player, select it in the table. Once selected, click the "Remove Player"
            button to permanently delete the player.
                  
            Choosing a Player:
            To play a specific player in a story, click the player
            on the table. Then click the "Next" button.
                  
            Going Back:
            If you need to return to the previous page, simply click the "Back" button.
            """;
  }

  /**
   * Gets the help text for the CreateCharacter page.
   *
   * @return A string of text for how to navigate the Create character view.
   */
  private String getCreateCharacterHelp() {
    return
        """
            Uploading character image:
            Uploading a character image means that you can see this image when you play
            the character in-game.
              
            Filling in Player base stats:
            A Player must have a name. Health and gold are optional and are set to
            1 and 0 respectively if left empty. If you fill in these fields, make sure
            that they are only numbers, and that there are no spaces. The max amount
            for both are 999999999.
             
            Next button:
            If the player has a name (and the gold and health are valid) you can click the "Next"
            button. This will take you back to the "Choose Character" page, where you can
            select the character you have just created.
              
            Going Back:
            To return to the "Choose Character" screen, click the "Back" button.
            """;
  }

  /**
   * Gets the help text for the GoalSelector page.
   *
   * @return A string of text for how to navigate the goal selector.
   */
  private String getGoalSelectorHelp() {
    return
        """
            Goals?
            A goal is a result you wish to achieve in the story. These can
            be related to player health, gold, score and inventory
                  
            For example, a health goal of 100 that is achieved if the player health reaches 100.
            The goal can become uncompleted again if health dips below 100.

            Adding a goal:
            To add a goal, select a type in the "Select type" dropdown box.
            Then input a value in the "Value" field and press the "+" button.
            Max value is 999999999 - Min value is 0.
            If the type is INVENTORY, select the items you wish to add to the inventory goal
            in the dropdown box.
                  
            Adding and deleting selectable items:
            The selectable items for inventory goals start off empty.
            You can create selectable items by pressing the "Add selectable items" button.
            Input the name of the item and press the "+" button.
            To delete an item, right click it in the list and press "Delete".
                  
            Deleting a goal:
            Right click the goal and press the "Delete" option.
                  
            Deleting all goals:
            Pressing the "Clear table" button deletes all goals.
                      
            Moving Forward:
            Once you have added your goals, click the "Next" button to proceed to the to
            start the game. You can view the goals you created in-game.
                      
            Going Back:
            If you need to return to the previous page, simply click the "Back" button.
            """;
  }
}