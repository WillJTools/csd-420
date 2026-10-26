# CSD420 — Module 1.3: Random Playing Cards

**Student:** William Judd  
**Date:** October 25, 2026  
**Course:** CSD420 Advanced Java Programming  
**Assignment:** Module 1.3 Programming Assignment

## What it does

This JavaFX program displays four randomly selected cards from a standard 52-card deck. The **Refresh Cards** button deals four new cards. The cards never repeat within a hand, and none of the four cards from the previous hand are included in the next one.

## Requirements

- JDK 21
- Apache Maven 3.9 or later (a compatible Maven 3.x version may also work)
- Internet access on the first run to download JavaFX dependencies

## Run on macOS

Open Terminal in this project directory and enter:

```bash
java -version
mvn -version
mvn clean javafx:run
```

Maven downloads JavaFX and launches the application. There is no need to configure the JavaFX SDK module path manually.

## Project structure

```text
csd420_module_1_cards/
├── pom.xml
├── README.md
└── src/main/
    ├── java/edu/bellevue/csd420/CardDealerApp.java
    └── resources/cards/1.png ... 52.png
```

## Implementation notes

- `Collections.shuffle` randomizes the available card numbers.
- `previousHand` excludes the last four cards when refreshing.
- The Refresh button uses a Java lambda expression.
- Images are loaded from the `cards` resources directory.
- Image-loading failures are logged to the console and shown in an alert.

**Image attribution:** Card images supplied by the instructor in `cards.zip` for this assignment.

## Submission evidence

Capture screenshots of the application running, the result after clicking Refresh, and Terminal showing `java -version` and the successful `mvn javafx:run` command. Place them in one Word document with your name, date, and assignment number.
