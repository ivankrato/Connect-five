package piskvorky;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents the gaming area/gaming grid of squares, where the
 * players play the game.
 *
 * @author Ivan Kratochvíl
 */
public class GamingArea implements Saveable {

    /**
     * Array, which represents the gaming grid.
     */
    private Square[][] gameGrid;

    /**
     * Constructor, it calls a method which creates the gaming area.
     *
     * @param width width of the gaming grid
     * @param height height of the gaming grid
     */
    public GamingArea(int width, int height) {
        create(width, height);
    }

    /**
     * This method initializes the array of squares - gaming grid.
     * 
     * @param width width of the gaming grid
     * @param height height of the gaming grid
     */
    public void create(int width, int height) {
        gameGrid = new Square[width][height];
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                gameGrid[i][j] = new Square();
            }
        }
    }

    /**
     * This method checks the gaming grid ïf it's empty and marks the square with the right mark.
     * 
     * @param x x-coordinate to mark
     * @param y y-coordinate to mark
     * @param mark mark to mark the square with 
     * @return true, if the marking was succesful (the square is empty), else false
     */
    public boolean markSquare(int x, int y, int mark) {
        if (gameGrid[x][y].isEmpty() && (mark == 1 || mark == 2)) {
            gameGrid[x][y].setState(mark);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param x x-coordinate of the square to check
     * @param y y-coordinate of the square to check
     * @return true, if the square is empty, else false
     */
    public boolean isSquareEmpty(int x, int y) {
        return gameGrid[x][y].isEmpty();
    }

    /**
     * @return array of integers made out of gaming grid
     */
    public int[][] toArray() {
        int[][] array = new int[gameGrid.length][gameGrid[0].length];
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                array[i][j] = gameGrid[i][j].getState();
            }
        }
        return array;
    }

    /**
     * @return width of the gaming area
     */
    public int getWidth() {
        return gameGrid.length;
    }

    /**
     * @return height of the gaming area
     */
    public int getHeight() {
        return gameGrid[0].length;
    }

    /**
     * @return true, if the gaming area is full (no squares can be marked), else false
     */
    public boolean isFull() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the array of squares (gaming grid)
     */
    public Square[][] getGameGrid() {
        return gameGrid;
    }
    
    @Override
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("width = ");
        sb.append(gameGrid.length);
        sb.append("\n");
        sb.append("height = ");
        sb.append(gameGrid[0].length);
        sb.append("\n");
        for (int i = 0; i < gameGrid[0].length; i++) {
            for (int j = 0; j < gameGrid.length; j++) {
                sb.append(gameGrid[j][i]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void loadFromString(String saveString) throws IOException {
        IOException error = new IOException("File corrupted.");
        Scanner sc = new Scanner(saveString);
        String toFind;
        int width = 0;
        int height = 0;
        toFind = "width = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNextInt()) {
                width = sc.nextInt();
                sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
        toFind = "height = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNextInt()) {
                height = sc.nextInt();
                sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
        create(width, height);
        for (int i = 0; i < gameGrid[0].length; i++) {
            for (int j = 0; j < gameGrid.length; j++) {
                if (sc.hasNextInt()) {
                    gameGrid[j][i].setState(sc.nextInt());
                } else {
                    throw error;
                }
            }
            sc.nextLine();
        }
    }

}
