package edu.ntnu.arbeidskrav4;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class Main extends Application {

  CardGame game;

  public static void main(String[] args) {
    launch(args);
  }



  @Override
  public void start(Stage primaryStage) throws Exception {
    game = new CardGame();


    //Buttons
    Button dealCardsButton = new Button("Deal Cards");
    Button checkHandButton = new Button("Check Hand");
    Button newDeckButton = new Button("New Deck");

    dealCardsButton.setTranslateX(150);
    dealCardsButton.setTranslateY(250);
    dealCardsButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

    checkHandButton.setTranslateX(450);
    checkHandButton.setTranslateY(250);
    checkHandButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

    newDeckButton.setTranslateX(300);
    newDeckButton.setTranslateY(250);
    newDeckButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");


    //Setup and style of the title
    Text titleText = new Text("Card Game");
    titleText.setTranslateX(300);

    titleText.setTranslateY(-250);
    titleText.setStyle("-fx-font-size: 50px; -fx-font-weight: bold");


    //Setup and style of the labels
    Label sumOfHandLabel = new Label("Sum of hand: ");
    sumOfHandLabel.setTranslateX(150);
    sumOfHandLabel.setTranslateY(100);
    sumOfHandLabel.setStyle("-fx-font-size: 20px");

    Label amountOfHeartsLabel = new Label("Hearts:");
    amountOfHeartsLabel.setTranslateX(150);
    amountOfHeartsLabel.setTranslateY(150);
    amountOfHeartsLabel.setStyle("-fx-font-size: 20px");

    Label queenOfSpadesLabel = new Label("Queen of Spades: ");
    queenOfSpadesLabel.setTranslateX(450);
    queenOfSpadesLabel.setTranslateY(100);
    queenOfSpadesLabel.setStyle("-fx-font-size: 20px");

    Label flushLabel = new Label("5-Flush: ");
    flushLabel.setTranslateX(450);
    flushLabel.setTranslateY(150);
    flushLabel.setStyle("-fx-font-size: 20px");

    Label handLabel = new Label(game.getHand().toString());
    handLabel.setTranslateX(300);

    Label cardsLeftLabel = new Label("Cards left: " + game.getDeck().getCards().size());
    cardsLeftLabel.setTranslateX(300);
    cardsLeftLabel.setTranslateY(-90);
    cardsLeftLabel.setStyle("-fx-font-size: 15px");

    Label waringMessage = new Label();
    handLabel.setTranslateY(-50);
    handLabel.setStyle("-fx-font-size: 40px");


    Image image1 = new Image(new FileInputStream("/home/larsmiloni/Skole/CardGame/src/main/resources/PNG-cards/C1.png"));
    Image image2 = new Image(new FileInputStream("/home/larsmiloni/Skole/CardGame/src/main/resources/PNG-cards/C5.png"));

    ImageView imageView1 = new ImageView();
    imageView1.setImage(image1);
    imageView1.setFitWidth(100);
    imageView1.setFitHeight(140);

    ImageView imageView2 = new ImageView();
    imageView2.setImage(image2);
    imageView2.setFitWidth(100);
    imageView2.setFitHeight(140);

    VBox vBox = new VBox();
    vBox.getChildren().add(imageView1);
    vBox.getChildren().add(imageView2);


    //Setup of the layout
    StackPane layout = new StackPane();
    layout.getChildren()
      .addAll(dealCardsButton, checkHandButton, titleText, sumOfHandLabel,
        amountOfHeartsLabel, queenOfSpadesLabel, flushLabel,
        handLabel, cardsLeftLabel, newDeckButton, waringMessage, vBox);


    //Updates the labels when the buttons are pressed
    dealCardsButton.setOnAction(event -> {
      try {
        game.dealNewHand();
        handLabel.setText(game.getHand().toString());
        cardsLeftLabel.setText("Cards left: " + game.getDeck().getCards().size());
      } catch (Exception e) {
        waringMessage.setText(e.getMessage() + " Please press 'New Deck' to start a new game");
      }
    });

    checkHandButton.setOnAction(event -> {
      sumOfHandLabel.setText("Sum of hand: " + game.getHand().getSum());
      amountOfHeartsLabel.setText("Hearts: " + game.getHand().getHearts());
      queenOfSpadesLabel.setText("Queen of Spades: " + game.getHand().queenOfSpadesInHand());
      flushLabel.setText("5-Flush: " + game.getHand().fiveCardFlushInHand());
    });

    newDeckButton.setOnAction(event -> {
      game.createNewDeck();
      cardsLeftLabel.setText("Cards left: " + game.getDeck().getCards().size());
      waringMessage.setText("");
    });

    //Setup of the scene
    Scene scene = new Scene(layout, 1200, 600);

    primaryStage.setTitle("Card Game");
    primaryStage.setScene(scene);

    primaryStage.show();
  }

/**
  public static ImageView cardsImageView(String cards) {
    ImageView imageView = new ImageView();

    String[] cardsInHand = cards.split("\\s");
    for (String card: cardsInHand) {
      Image image = new Image(System.getProperty("user.dir") + "/resources" + card);
      imageView.setImage(image);
    }
    return imageView;
  }
 **/
}