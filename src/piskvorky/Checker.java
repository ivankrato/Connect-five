package piskvorky;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class contains variables and methods which are used to check for win.
 *
 * @author Ivan Kratochvíl
 */
public class Checker implements Saveable {

    /**
     * This variable contains the number of marks of one type in row which are
     * needed for winning.
     */
    private int marksInRowToWin;
    /**
     * This variable contains a reference to gaming area.
     */
    private GamingArea gamingArea;
    /**
     * This variable is true, if the game has been already won.
     */
    private boolean win;

    /**
     * Constructor, initializes variables, which are neede to check for win.
     *
     * @param marksInRowToWin number of marks in row needed to win
     * @param gamingArea gaming area (grid) of the game
     */
    public Checker(int marksInRowToWin, GamingArea gamingArea) {
        this.marksInRowToWin = marksInRowToWin;
        this.gamingArea = gamingArea;
    }

    /**
     * This method checks the gaming area (grid), if there is the right number
     * of marks of one type needed to win the game.
     *
     * @param x x-coordinate of the last move
     * @param y y-coordinate of the last move
     * @param player player, who play the lsat move
     * @return true, if the player won, else false
     */
    public boolean checkWin(int x, int y, Player player) {
        int[][] arrayGamingArea = gamingArea.toArray();
        int row = 1;
        int column = 1;
        int axis1 = 1; //top left -> bottom right
        int axis2 = 1; //top right -> bottom left

        for (int i = 1; i < marksInRowToWin; i++) { //right
            if ((x + i < arrayGamingArea.length)
                    && (arrayGamingArea[x + i][y] == player.getMark())) {
                row++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //left
            if ((x - i >= 0)
                    && (arrayGamingArea[x - i][y] == player.getMark())) {
                row++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //down
            if ((y + i < arrayGamingArea[0].length)
                    && (arrayGamingArea[x][y + i] == player.getMark())) {
                column++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //up
            if ((y - i >= 0)
                    && (arrayGamingArea[x][y - i] == player.getMark())) {
                column++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //right down
            if ((x + i < arrayGamingArea.length)
                    && (y + i < arrayGamingArea[0].length)
                    && (arrayGamingArea[x + i][y + i] == player.getMark())) {
                axis1++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //left up
            if ((x - i >= 0)
                    && (y - i >= 0)
                    && (arrayGamingArea[x - i][y - i] == player.getMark())) {
                axis1++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //left down
            if ((x - i >= 0)
                    && (y + i < arrayGamingArea[0].length)
                    && (arrayGamingArea[x - i][y + i] == player.getMark())) {
                axis2++;
            } else {
                break;
            }
        }

        for (int i = 1; i < marksInRowToWin; i++) { //right up
            if ((x + i < arrayGamingArea.length)
                    && (y - i >= 0)
                    && (arrayGamingArea[x + i][y - i] == player.getMark())) {
                axis2++;
            } else {
                break;
            }
        }

        if (row >= marksInRowToWin || column >= marksInRowToWin
                || axis1 >= marksInRowToWin || axis2 >= marksInRowToWin) {
            win = true;
        } else {
            win = false;
        }
        return win;
    }
    
    /**
     * @return true, if the game has been won, else false
     */

    public boolean isWin() {
        return win;
    }
    /**
     * @return number of marks in a row neede to win the game
     */
    public int getMarksInRowToWin() {
        return marksInRowToWin;
    }

    @Override
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("marksInRowToWin = ");
        sb.append(marksInRowToWin);
        sb.append("\n");
        sb.append("win = ");
        sb.append(win);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void loadFromString(String saveString) throws IOException {
        IOException error = new IOException("File corrupted.");
        Scanner sc = new Scanner(saveString);
        String toFind;
        toFind = "marksInRowToWin = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNextInt()) {
                marksInRowToWin = sc.nextInt();
                sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
        toFind = "win = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNextBoolean()) {
                win = sc.nextBoolean();
                sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
    }

    
}
