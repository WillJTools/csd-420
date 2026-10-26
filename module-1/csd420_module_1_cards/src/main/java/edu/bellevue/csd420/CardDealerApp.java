package edu.bellevue.csd420;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * CSD420 - Module 1.3 Programming Assignment
 * Author: William Judd
 * Date: October 25, 2026
 *
 * Displays four unique playing cards and deals a different set when refreshed.
 * Card images are provided in the assignment's cards.zip archive.
 */
public class CardDealerApp extends Application {

    private static final Logger LOGGER = Logger.getLogger(CardDealerApp.class.getName());
    private static final int DECK_SIZE = 52;
    private static final int HAND_SIZE = 4;
    private final HBox cardRow = new HBox(12);
    private List<Integer> previousHand = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        Label heading = new Label("Four Random Cards");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        cardRow.setAlignment(Pos.CENTER);
        Button refreshButton = new Button("Refresh Cards");
        refreshButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 18;");
        refreshButton.setOnAction(event -> dealCards()); // Lambda expression

        VBox layout = new VBox(20, heading, cardRow, refreshButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #f4f6f8;");

        stage.setTitle("CSD420 - Playing Cards");
        stage.setScene(new Scene(layout, 670, 350));
        stage.show();
        dealCards();
    }

    /** Picks four cards without repeats, excluding the previous hand. */
    private void dealCards() {
        List<Integer> availableCards = new ArrayList<>();
        for (int card = 1; card <= DECK_SIZE; card++) {
            if (!previousHand.contains(card)) {
                availableCards.add(card);
            }
        }
        Collections.shuffle(availableCards);
        List<Integer> nextHand = new ArrayList<>(availableCards.subList(0, HAND_SIZE));

        try {
            List<ImageView> images = new ArrayList<>();
            for (int cardNumber : nextHand) {
                URL cardFile = getClass().getResource("/cards/" + cardNumber + ".png");
                if (cardFile == null) {
                    throw new IllegalStateException("Missing card image: " + cardNumber + ".png");
                }
                Image image = new Image(cardFile.toExternalForm(), false);
                if (image.isError()) {
                    throw new IllegalStateException("Could not load card " + cardNumber, image.getException());
                }
                ImageView view = new ImageView(image);
                view.setFitHeight(190);
                view.setFitWidth(140);
                view.setPreserveRatio(true);
                images.add(view);
            }
            cardRow.getChildren().setAll(images);
            previousHand = nextHand;
        } catch (RuntimeException exception) {
            LOGGER.log(Level.SEVERE, "Unable to display playing cards", exception);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Card loading error");
            alert.setHeaderText("Unable to display the cards");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
