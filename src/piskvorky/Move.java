package piskvorky;

/**
 * This class represents one move of one player.
 *
 * @author Ivan Kratochvíl
 */
class Move {

    /**
     * x-coordinate of this move
     */
    private int xCoord;
    /**
     * y-coordinate of this move
     */
    private int yCoord;
    /**
     * mark of this move
     */
    private int mark;

    /**
     * Constructor, initializes all variables.
     *
     * @param xCoord x-coordinate of this move
     * @param yCoord y-coordinate of this move
     * @param mark mark of this move
     */
    public Move(int xCoord, int yCoord, int mark) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.mark = mark;
    }

    /**
     * @return x-coordinate of this move
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     * @return y-coordinate of this move 
     */
    public int getyCoord() {
        return yCoord;
    }

    /**
     * @return mark of this move
     */
    public int getMark() {
        return mark;
    }

    /**
     * @return string, which contains x-coordinate, y-coordinate and mark of this move 
     */
    public String toString() {
        return xCoord + " " + yCoord + " " + mark;
    }
    
    @Override
    /**
     * Moves are the same, if they are on the same coordinates with the same mark. For testing.
     */
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Move)) {
            return false;
        }
        Move move = (Move)obj;
        return (this.xCoord == move.getxCoord() && this.yCoord == move.getyCoord() && this.mark == move.getMark());
    }
}
