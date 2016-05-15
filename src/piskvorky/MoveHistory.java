package piskvorky;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represetns a journal of moves made in the game.
 * 
 * @author Ivan Kratochvíl
 */
public class MoveHistory implements Saveable {

    /**
     * List of the moves made in this game.
     */
    private ArrayList<Move> moveHistory;
    /**
     * Integer, which is a kind of iterator, which goes through the list of the moves.
     */
    private int iterator;

    /**
     * Constructor, initializes the list of moves and sets the iterator to zero.
     */
    public MoveHistory() {
        moveHistory = new ArrayList<Move>();
        iterator = 0;
    }

    /**
     * Adds move to the list.
     * @param move move to add to the list
     */
    public void add(Move move) {
        moveHistory.add(move);
    }

    /**
     * @return move on the iterator's value
     */
    public Move getMoveOnIteratorValue() {
        if (!isEnd()) {
            iterator++;
            return moveHistory.get(iterator - 1);
        } else {
            return null;
        }
    }

    /**
     * Sets the iterator to zero.
     */
    public void resetIterator() {
        iterator = 0;
    }

    /**
     * @return true, if the iterator is at the end of the list
     */
    public boolean isEnd() {
        return iterator >= moveHistory.size();
    }

    /**
     * @return number of moves made in the game
     */
    public int size() {
        return moveHistory.size();
    }

    /**
     * @return the iterator's value
     */
    public int getIterator() {
        return iterator;
    }

    @Override
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        for (Move move : moveHistory) {
            sb.append(move);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void loadFromString(String saveString) throws IOException {
        IOException error = new IOException("File corrupted.");
        Scanner sc = new Scanner(saveString);
        int x;
        int y;
        int mark;
        while (sc.hasNextInt()) {
            if (sc.hasNextInt()) {
                x = sc.nextInt();
            } else {
                throw error;
            }
            if (sc.hasNextInt()) {
                y = sc.nextInt();
            } else {
                throw error;
            }
            if (sc.hasNextInt()) {
                mark = sc.nextInt();
            } else {
                throw error;
            }
            add(new Move(x, y, mark));
            sc.nextLine();
        }
    }

}
