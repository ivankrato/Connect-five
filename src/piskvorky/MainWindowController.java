package piskvorky;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Ivan Kratochvíl
 */
public class MainWindowController implements Initializable {

    @FXML
    /**
     * Main container of all controls.
     */
    private BorderPane mainBorderPane;
    @FXML
    /**
     * Main menu bar.
     */
    private MenuBar mainMenuBar;
    @FXML
    /**
     * First menu - "Hra".
     */
    private Menu gameMenu;
    @FXML
    /**
     * Menu item "Nová hra".
     */
    private MenuItem newGameMenuItem;
    @FXML
    /**
     * Menu item "Uložit hru".
     */
    private MenuItem saveGameMenuItem;
    @FXML
    /**
     * Menu item "Nahrát hru".
     */
    private MenuItem loadGameMenuItem;
    @FXML
    /**
     * Menu item "Konec".
     */
    private MenuItem endMenuItem;
    @FXML
    /**
     * Second menu - "Nápověda".
     */
    private Menu helpMenu;
    @FXML
    /**
     * Menu item "Nápověda".
     */
    private MenuItem helpMenuItem;
    @FXML
    /**
     * Menu item "O programu".
     */
    private MenuItem aboutMenuItem;
    @FXML
    /**
     * Button bar at the bottom.
     */
    private ButtonBar bottomButtonBar;
    @FXML
    /**
     * Vertical box on the right.
     */
    private VBox infovBox;
    @FXML
    /**
     * Label containing name of cross player.
     */
    private Label infoCrossPlayer;
    @FXML
    /**
     * Label containing name of circle player.
     */
    private Label infoCirclePlayer;
    @FXML
    /**
     * Label containing number of marks in row to win.
     */
    private Label infoMarksInRowToWin;
    @FXML
    /**
     * Label containing name of player on move.
     */
    private Label infoCurrentPlayer;
    @FXML
    /**
     * Label containing information of game status.
     */
    private Label infoGameStatus;

    /**
     * Main grid representing gaming area.
     */
    private GridPane mainGrid;

    /**
     * Instance of class Game - main logic of the game.
     */
    private Game game;

    /**
     * Array of rectangles - each rectangle represents one square.
     */
    private Rectangle[][] squareArray;
    /**
     * Sequential transition, contains empty animations, which are played
     * between each move, while replaying the game.
     */
    private SequentialTransition gameAnimation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    /**
     * This method is called by clicking on menu item "Nová hra". It asks the
     * user about game information (names of player, width and height of the
     * game grid, number of marks in row needed to win) and initializes variable
     * game.
     *
     */
    private void handleNewGameMenuItemAction(ActionEvent event) {
        String result;
        String crossPlayer = null;
        String circlePlayer = null;
        int gridWidth = 0;
        int gridHeight = 0;
        int marksInRowToWin = 0;
        boolean crossAI = false;
        boolean circleAI = false;
        while (crossPlayer == null) {
            crossPlayer = showInputDialog("Hráč číslo 1", "Hráč s křížky", "Zadejte jméno: ");
            if ("".equals(crossPlayer)) {
                showError("Musíte zadat jméno.");
                crossPlayer = null;
            } else if (crossPlayer == null) {
                return;
            }
        }
        crossAI = showAIdialog(crossPlayer);
        while (circlePlayer == null) {
            circlePlayer = showInputDialog("Hráč číslo 2", "Hráč s kolečky", "Zadejte jméno: ");
            if ("".equals(circlePlayer)) {
                showError("Musíte zadat jméno.");
                circlePlayer = null;
            } else if (circlePlayer == null) {
                return;
            }
        }
        circleAI = showAIdialog(circlePlayer);
        while (gridWidth == 0) {
            result = showInputDialog("Rozměry", "Šířka mřížky (3-45)", "Zadejte číslo: ");
            if (result == null) {
                return;
            } else {
                try {
                    gridWidth = Integer.valueOf(result);
                    if (gridWidth < 3 || gridWidth > 45) {
                        showError("Musíte zadat číslo mezi 3 a 45");
                        gridWidth = 0;
                    }
                } catch (Exception ex) {
                    showError("Musíte zadat číslo.");
                    gridWidth = 0;
                }
            }
        }
        while (gridHeight == 0) {
            result = showInputDialog("Rozměry", "Výška mřížky (3-45)", "Zadejte číslo: ");
            if (result == null) {
                return;
            } else {
                try {
                    gridHeight = Integer.valueOf(result);
                    if (gridHeight < 3 || gridHeight > 45) {
                        showError("Musíte zadat číslo mezi 3 a 45");
                        gridHeight = 0;
                    }
                } catch (Exception ex) {
                    showError("Musíte zadat číslo.");
                    gridHeight = 0;
                }
            }
        }
        while (marksInRowToWin == 0) {
            result = showInputDialog("Pravidla", "Počet značek za sebou k výhře", "Zadejte číslo: ");
            if (result == null) {
                return;
            } else {
                try {
                    marksInRowToWin = Integer.valueOf(result);
                    if ((marksInRowToWin > gridWidth && marksInRowToWin > gridHeight) || marksInRowToWin <= 0) {
                        showError("Musíte zadat číslo menší než velikost pole a větší než 0.");
                        marksInRowToWin = 0;
                    }
                } catch (Exception ex) {
                    showError("Musíte zadat číslo.");
                    marksInRowToWin = 0;
                }
            }
        }
        game = new Game(crossPlayer, crossAI, circlePlayer, circleAI, gridWidth, gridHeight, marksInRowToWin);
        resetGame(gridWidth, gridHeight);
        aiCycle();
    }

    /**
     * This method show error window.
     *
     * @param content string to show in the error window
     */
    private void showError(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText("Chyba");
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    /**
     * This method is called by clicking on the menu item "Uložit hru". It
     * creates a file chooser, which let's the user choose a file to save the
     * game into, the it saves the game and show information windows if it was
     * succesfull.
     */
    private void handleSaveGameMenuItemAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Uložit hru");
        fileChooser.setInitialFileName("piskvorkySave.sav");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Soubor uložené hry piškvorek (*.sav)", "*.sav"));
        File saveFile = fileChooser.showSaveDialog(new Stage());
        if (saveFile != null) {
            try {
                game.save(saveFile);
            } catch (Exception ex) {
                return;
            }
            Alert saveAlert = new Alert(Alert.AlertType.INFORMATION);
            saveAlert.setContentText("Hra úspěšně uložena do " + saveFile);
            saveAlert.setTitle("Úspěch");
            saveAlert.setHeaderText("Hra uložena");
            saveAlert.showAndWait();
        }

    }

    @FXML
    /**
     * This method is called by clicking on the menu item "Nahrát hru". It
     * creates file chooser, which lets the user choose a file to load. Then it
     * loads the game an if the game is alredy over, it show replay controls.
     */
    private void handleLoadGameMenuItemAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Načíst hru");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Soubor uložené hry piškvorek (*.sav)", "*.sav"));
        File loadFile = fileChooser.showOpenDialog(new Stage());
        if (loadFile != null) {
            game = new Game();
            game.load(loadFile);
            resetGame(game.getGamingArea().getWidth(), game.getGamingArea().getHeight());
        } else {
            return;
        }
        if (game.getChecker().isWin()) {
            setGamingAreaInactive();
            startReplay();
        } else {
            playAnimation(null, Duration.ONE);
        }
    }

    @FXML
    /**
     * This method is called by clicking on the menu item "Konec". It exits the
     * program.
     */
    private void handleEndGameMenuItem(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    /**
     * This method is called by clicking on the menu item "Nápověda". It shows
     * information window with help.
     */
    private void handleHelpMenuItem(ActionEvent event) {
        Alert help = new Alert(Alert.AlertType.INFORMATION);
        help.setTitle("Nápověda");
        help.setHeaderText("Nápověda ke hře Piškvorky");
        help.setContentText("Piškvorky jsou strategická hra, ve které spolu soupeří dva hráči. "
                + "Vyhrává hráč, který jako první vytvoří nepřerušenou řadu několika svých značek. Začíná vždy hráč s křížky.\n\n"
                + "Pro spuštění nové hry zvolte v menu Hra, Nová hra. "
                + "Hra se ovládá kliknutím do vytvořené mřížky.\n\n"
                + "Pro uložení rozehrané či dohrané hry zvolte v menu Hra, Uložit hru.\n\n"
                + "Pro načtení rozehrané či dohrané hry zvolte v menu Hra, Načíst hru. "
                + "Pokud je načtená hra dohrána, objeví se dole v okně tlačítka pro ovládání přehrání hry.\n\n"
                + "Pro ukončení hry zvole v menu Hra, Konec.");
        help.showAndWait();
    }

    @FXML
    /**
     * This method is called by clicking on the menu item "O programu". It shows
     * information about the program.
     */
    private void handleAboutMenuItem(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("O program");
        about.setHeaderText("O programu piškvorky");
        about.setContentText("Verze: " + Piskvorky.getVersion() + "\nAutor: Ivan Kratochvíl");
        about.showAndWait();
    }
    
    /**
     * This method show input dialog.
     *
     * @param title title of the input dialog
     * @param header header of the input dialog
     * @param content of the input dialog
     * @return string which the user entered, or null, if he didn't enter
     * anything
     */
    private String showInputDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }
    
    /**
     * This method show confirmation dialog asking if the player is AI.
     *
     * @param player name of the player the dialog is asking about
     * @return true, if the user clicked yes, else false
     */
    private boolean showAIdialog(String player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("AI");
        alert.setHeaderText("AI");
        alert.setContentText("Je " + player + " AI?");
        ButtonType yesButton = new ButtonType("Ano");
        ButtonType noButton = new ButtonType("Ne");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Button defaultButton = (Button) alert.getDialogPane().lookupButton(noButton);
        defaultButton.setDefaultButton(true);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This method resets the GUI - creates new grid, clears buttons, setx
     * informations texts, resizes window.
     *
     * @param gridWidth width of the main grid
     * @param gridHeight height of the main grid
     */
    private void resetGame(int gridWidth, int gridHeight) {
        mainGrid = null;
        createGrid(gridWidth, gridHeight);
        saveGameMenuItem.setDisable(false);
        bottomButtonBar.getButtons().clear();
        infovBox.setStyle("-fx-opacity: 1;");
        infoCurrentPlayer.setText(game.getOnMove().toString());
        infoMarksInRowToWin.setText("" + game.getChecker().getMarksInRowToWin());
        infoCrossPlayer.setText(game.getCrossPlayer().getName());
        infoCirclePlayer.setText(game.getCirclePlayer().getName());
        infoGameStatus.setText("Na řadě je");
        mainBorderPane.getScene().getWindow().sizeToScene();
    }

    /**
     * This method creates the main grid full of rectangles. It sets their
     * color, stroke and events on mouse click, mouse enter and mouse exit.
     *
     * @param width width of the main grid
     * @param height height of the main grid
     */
    private void createGrid(int width, int height) {
        mainGrid = new GridPane();
        mainBorderPane.setCenter(mainGrid);
        squareArray = new Rectangle[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int xCoord = i;
                int yCoord = j;
                squareArray[i][j] = new Rectangle(20, 20, Color.WHITE);
                squareArray[i][j].setStroke(Color.BLACK);
                squareArray[i][j].setStrokeWidth(1);
                squareArray[i][j].setOnMouseClicked((MouseEvent e) -> {
                    squareClick(xCoord, yCoord);
                });
                squareArray[i][j].setOnMouseEntered((MouseEvent e) -> {
                    squareArray[xCoord][yCoord].setStroke(Color.WHITE);
                });
                squareArray[i][j].setOnMouseExited((MouseEvent e) -> {
                    squareArray[xCoord][yCoord].setStroke(Color.BLACK);
                });
                mainGrid.add(squareArray[i][j], i, j);
            }
        }
        mainGrid.setPadding(new Insets(10));
    }

    /**
     * This method is called after clicking on one rectangle (square) in the
     * main grid. It tries to play the move, if it's possible. It also checks
     * for win, checks for full gaming area, change player and show info about
     * the player.
     *
     * @param xCoord x-coordinate of the clicked square
     * @param yCoord y-coordinate of the clicked square
     */
    private void squareClick(int xCoord, int yCoord) {
        if (!game.playMove(xCoord, yCoord)) {
            return;
        }
        markSquareGUI(xCoord, yCoord, game.getOnMove().getMark());
        if (game.getChecker().isWin()) {
            endGame();
            return;
        }
        if (game.getGamingArea().isFull()) {
            endGame();
            infoGameStatus.setText("Pole je plné.");
            return;
        }
        game.changePlayer();
        infoCurrentPlayer.setText(game.getOnMove().toString());
        aiCycle();
    }

    /**
     * This method marks a square on the game grid with the right mark. It also
     * marks the latest marked square with white stroke.
     *
     * @param xCoord x-coordinate of the square which should be marked
     * @param yCoord y-coordinate of the square which should be marked
     * @param mark mark which should mark the square
     */
    private void markSquareGUI(int xCoord, int yCoord, int mark) {
        for (int i = 0; i < game.getGamingArea().getWidth(); i++) {
            for (int j = 0; j < game.getGamingArea().getHeight(); j++) {
                int xCoordTemp = i;
                int yCoordTemp = j;
                squareArray[xCoordTemp][yCoordTemp].setStroke(Color.BLACK);
                squareArray[i][j].setOnMouseExited((MouseEvent e) -> {
                    squareArray[xCoordTemp][yCoordTemp].setStroke(Color.BLACK);
                });
            }
        }
        Image fill = null;
        if (mark == 1) {
            fill = new Image(getClass().getResourceAsStream("/gfx/cross.png"));

        } else if (mark == 2) {
            fill = new Image(getClass().getResourceAsStream("/gfx/circle.png"));
        }
        squareArray[xCoord][yCoord].setFill(new ImagePattern(fill));
        squareArray[xCoord][yCoord].setStroke(Color.WHITE);
        squareArray[xCoord][yCoord].setOnMouseExited(null);
    }
    
    /**
     * This method disables all the squares, so clicking on them doesn't do
     * anything.
     */
    private void setGamingAreaInactive() {
        for (int i = 0; i < squareArray.length; i++) {
            for (int j = 0; j < squareArray[0].length; j++) {
                squareArray[i][j].setOnMouseClicked(null);
                squareArray[i][j].setOnMouseEntered(null);
                squareArray[i][j].setOnMouseExited(null);
            }
        }
    }
    
     /**
     * This method is called when AI should play. It gets the move's coordinates
     * from AI and try to play it. It also check for win, checks if the gaming
     * area is full and change players. If the game is played by two AIs, this
     * method contains the full cycle - the game is first played and then the
     * userý can replay it.
     */
    private void aiCycle() {
        while (game.getOnMove().isAi()) {
            int[] coords = game.getOnMove().playAi(game.getGamingArea(), game.getOnMove().getMark(), game.getChecker().getMarksInRowToWin());
            game.playMove(coords[0], coords[1]);
            if (!game.isTwoAi()) {
                markSquareGUI(coords[0], coords[1], game.getOnMove().getMark());
            }
            if (game.getChecker().isWin()) {
                endGame();
                return;
            }
            if (game.getGamingArea().isFull()) {
                endGame();
                infoGameStatus.setText("Pole je plné.");
                return;
            }
            game.changePlayer();
            infoCurrentPlayer.setText(game.getOnMove().toString());
        }
    }

    /**
     * This method is called when the game ended and it starts replay, if the
     * game was played by two ai or shows win alert if not. It also updates the
     * info of the game.
     */
    private void endGame() {
        if (game.isTwoAi()) {
            setGamingAreaInactive();
            startReplay();
        } else {
            setGamingAreaInactive();
            showWinAlert();
        }
        infoGameStatus.setText("Vyhrál");
    }

    /**
     * This method shows information window with the name of the winning player.
     */
    private void showWinAlert() {
        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
        winAlert.setHeaderText("Konec hry.");
        winAlert.setContentText("Vyhrál hráč " + game.getOnMove() + ".");
        winAlert.setTitle("Konec hry.");
        winAlert.show();
    }
    
    /**
     * This method fill sequantial transition with empty animations (for delay)
     * and then plays them. Each of this animation call markNextMoveInHistory()
     * method on finish.
     *
     * @param eventOnFinished event to call after the whole animation finished
     * @param duration duration of each transition (for delay)
     */
    private void playAnimation(EventHandler<ActionEvent> eventOnFinished, Duration duration) {
        ArrayList<FadeTransition> transitionSquareList = new ArrayList<FadeTransition>();
        Rectangle temp = new Rectangle();
        for (int i = 0; i < game.getMoveHistory().size() - game.getMoveHistory().getIterator(); i++) {
            transitionSquareList.add(new FadeTransition(duration, temp));
            transitionSquareList.get(i).setOnFinished((ActionEvent e) -> markNextMoveInHistory());
        }
        gameAnimation = new SequentialTransition();
        gameAnimation.getChildren().setAll(transitionSquareList);
        gameAnimation.setOnFinished(eventOnFinished);
        gameAnimation.play();
    }

    /**
     * This method takes the next move from move history and calls the method to
     * mark the grid with the right mark.
     */
    private void markNextMoveInHistory() {
        Move tempMove = game.getMoveHistory().getMoveOnIteratorValue();
        int xCoord = tempMove.getxCoord();
        int yCoord = tempMove.getyCoord();
        int mark = tempMove.getMark();
        markSquareGUI(xCoord, yCoord, mark);
    }

    /**
     * This method creates buttons on the bottom button bar. These buttons are
     * controlling the replay of the game. One starts/stops animation, one just
     * show the next move and one skips to the end of the replay.
     */
    private void startReplay() {
        Button startStopButton = new Button("Spustit/zastavit animaci");
        Button nextButton = new Button("Další tah");
        Button skipReplay = new Button("Na konec");
        startStopButton.setOnAction((ActionEvent e) -> {
            if (gameAnimation != null && gameAnimation.getStatus() == Status.RUNNING) {
                gameAnimation.stop();
                nextButton.setDisable(false);
                skipReplay.setDisable(false);
            } else {
                playAnimation((ActionEvent ev) -> disableBottomButtonBarButtonsIfEnd(), Duration.millis(500));
                nextButton.setDisable(true);
                skipReplay.setDisable(true);
            }
        });
        nextButton.setOnAction((ActionEvent e) -> {
            markNextMoveInHistory();
            disableBottomButtonBarButtonsIfEnd();
        });
        skipReplay.setOnAction((ActionEvent e) -> {
            playAnimation(null, Duration.ONE);
            startStopButton.setDisable(true);
            nextButton.setDisable(true);
            skipReplay.setDisable(true);
        });
        bottomButtonBar.getButtons().add(startStopButton);
        bottomButtonBar.getButtons().add(nextButton);
        bottomButtonBar.getButtons().add(skipReplay);
    }

    /**
     * This method checks if the game has ended and disables replay controls if
     * so.
     */
    private void disableBottomButtonBarButtonsIfEnd() {
        if (game.getMoveHistory().isEnd()) {
            Button tempButton;
            for (int i = 0; i < bottomButtonBar.getButtons().size(); i++) {
                tempButton = (Button) bottomButtonBar.getButtons().get(i);
                tempButton.setDisable(true);
            }
        }
    }
}
