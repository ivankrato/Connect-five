package piskvorky;

import java.io.File;

/**
 * This class contains variables and methods for the basic game logic - it takes
 * care about players, gaming area, calls methods from checker, saver and move
 * history. It implements the Saveable interface.
 *
 * @author Ivan Kratochvíl
 */
public class Game {

    /**
     * Instance of class Player - player with crosses.
     */
    private Player crossPlayer;
    /**
     * Instance of class Player - player with circles.
     */
    private Player circlePlayer;
    /**
     * Instance of class GamingArea. It contains the grid with marks.
     */
    private GamingArea gamingArea;
    /**
     * Instance of class Checker. It checks if someone has won.
     */
    private Checker checker;
    /**
     * Instance of class MoveHistory. It contains moves which were played.
     */
    private MoveHistory moveHistory;
    /**
     * Instance of class Player - player who is on the move.
     */
    private Player onMove;
    /**
     * Instance of class Saver. It saves and loads a game from file.
     */
    private Saver saver;
    
    /**
     * Constructor, creates "empty" game, used when loading.
     */
    public Game() {
        this.crossPlayer = new Player(null, 1, false);
        this.circlePlayer = new Player(null, 2, false);
        this.gamingArea = new GamingArea(1, 1);
        this.checker = new Checker(1, gamingArea);
        this.moveHistory = new MoveHistory();
        this.saver = new Saver(this.crossPlayer, this.circlePlayer, this.gamingArea, this.checker, this.moveHistory);
    }

    /**
     * Constructor, creates and initializes game based on parameters.
     * 
     * @param crossPlayer name of the player with crosses
     * @param crossAI true, if the cross player is ai, else false
     * @param circlePlayer name of the player with circles
     * @param circleAI true, if the circle player is ai, else false
     * @param width width of the gaming area (grid)
     * @param height height of the gaming area (grid)
     * @param marksInRowToWin number of marks in row needed to win
     */
    public Game(String crossPlayer, boolean crossAI, String circlePlayer, boolean circleAI, int width, int height, int marksInRowToWin) {
        this.crossPlayer = new Player(crossPlayer, 1, crossAI);
        this.circlePlayer = new Player(circlePlayer, 2, circleAI);
        this.gamingArea = new GamingArea(width, height);
        this.checker = new Checker(marksInRowToWin, gamingArea);
        this.moveHistory = new MoveHistory();
        this.saver = new Saver(this.crossPlayer, this.circlePlayer, this.gamingArea, this.checker, this.moveHistory);
        this.onMove = this.crossPlayer;
    }

    /**
     * Loads important variables from file.
     *
     * @param file file with saved game from the past
     */
    public void load(File file) {
        saver.load(file);
        onMove = saver.getOnMove();
    }

    /**
     * Saves important variable to file.
     *
     * @param file file which the variables should be save in
     */
    public void save(File file) {
        saver.setOnMove(onMove);
        saver.save(file);
    }

    /**
     * Saves the right (player which is on the move) instance of class Player to
     * variable onMove.
     */
    public void changePlayer() {
        if (onMove == crossPlayer) {
            onMove = circlePlayer;
        } else {
            onMove = crossPlayer;
        }
    }

    /**
     * This mathod is called when a move is played. It marks the square in
     * gaming area, adds the move to move history and checks for win. Return if
     * the move was succesfull.
     *
     * @param x x coordinate of the move
     * @param y y coordinate of the move
     * @return true if the move was succesfull, else false
     */
    public boolean playMove(int x, int y) {
        int mark = onMove.getMark();
        if (gamingArea.markSquare(x, y, mark)) {
            moveHistory.add(new Move(x, y, mark));
            checker.checkWin(x, y, onMove);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return instance of class GamingArea, which contains the grid with marks
     */
    public GamingArea getGamingArea() {
        return gamingArea;
    }

    /**
     * @return instance of class Player - the player who is on the move
     */
    public Player getOnMove() {
        return onMove;
    }

    /**
     * @return instance of class Checker, which checks for win
     */
    public Checker getChecker() {
        return checker;
    }

    /**
     * @return instance of class MoveHistory, which contains moves which were
     * played
     */
    public MoveHistory getMoveHistory() {
        return moveHistory;
    }

    /**
     * @return true, if this game is played only by AIs, else false
     */
    public boolean isTwoAi() {
        if (crossPlayer.isAi() && circlePlayer.isAi()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return instance of class Player - player with crosses
     */
    public Player getCrossPlayer() {
        return crossPlayer;
    }

    /**
     * @return instance of class Player - player with circles
     */
    public Player getCirclePlayer() {
        return circlePlayer;
    }
}
