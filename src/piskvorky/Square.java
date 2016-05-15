package piskvorky;

/**
 * This class represents one square in the gaming area (grid).
 *
 * @author Ivan Kratochvíl
 */
public class Square {

    /**
     * State of the square. 0 - empty 1 - cross 2 - circle
     */
    private int state;

    /**
     * Constructor, creates empty square
     */
    public Square() {
        state = 0;
    }

    /**
     * Constructor, creates marked or empty square 1 - cross 2 - circle
     *
     * @param state state (mark) of the square
     */
    public Square(int state) {
        if (state == 1 || state == 2) {
            this.state = state;
        } else {
            throw new IllegalArgumentException("State must be 1 (cross) or 2 (circle).");
        }
    }

    /**
     * 0 - empty 1 - cross 2 - circle
     *
     * @return state (mark) of the square
     */
    public int getState() {
        return state;
    }

    /**
     * @return true, if the square isn't marked
     */
    public boolean isEmpty() {
        if (this.state == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the squares state (mark), if it isn't marked
     *
     * @param state state (mark) to set
     */
    public void setState(int state) {
        this.state = state;
    }

    @Override
    /**
     * @return state (mark) of this square
     */
    public String toString() {
        return String.valueOf(state);
    }
   
    @Override
    /**
     * Squares are the same, if their states are the same. For testing.
     */
    public boolean equals(Object square) {
        if(square == null) {
            return false;
        }
        if(!(square instanceof Square)) {
            return false;
        }
        return this.state == ((Square)square).getState();
    }
}
