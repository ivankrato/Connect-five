package piskvorky;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a player (human or AI), who has a name and a mark. It
 * implements the Saveable interface.
 *
 * @author Ivan Kratochvíl
 */
public class Player implements Saveable {

    /**
     * Name of the player.
     */
    private String name;
    /**
     * Player's mark.
     */
    private int mark;
    /**
     * True, if this player is AI, else false.
     */
    private boolean ai;

    /**
     * Constructor, creates and initializes the player.
     *
     * @param name name of the player
     * @param mark player's mark
     * @param ai true, if the player is AI, else false
     */
    public Player(String name, int mark, boolean ai) {
        this.name = name;
        this.mark = mark;
        this.ai = ai;
    }

    /**
     * @return return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return player's mark
     */
    public int getMark() {
        return mark;
    }

    @Override
    /**
     * @return name of the player
     */
    public String toString() {
        return this.name;
    }

    /**
     * @return true, if the player is AI, else false
     */
    public boolean isAi() {
        return ai;
    }

    /**
     * This method computes priority of squares in gaming are, based on numerous
     * factors. Then it takes the best move and return its coordinates.
     *
     * @param gamingArea gaming area of the game
     * @param markOnMove mark of the player, who is on move
     * @param marksInRowToWin number of marks in row needed to win
     * @return array of 2 coordinates, x and y, of the best move
     */
    public int[] playAi(GamingArea gamingArea, int markOnMove, int marksInRowToWin) {
        int[][] gamingAreaArray = gamingArea.toArray();
        int[][] priorities = new int[gamingAreaArray.length][gamingAreaArray[0].length];
        int markEnemy = 1;
        if (markOnMove == 1) {
            markEnemy = 2;
        }
        //count defense priorities
        for (int i = 0; i < priorities.length; i++) {
            for (int j = 0; j < priorities[0].length; j++) {
                if (!gamingArea.isSquareEmpty(i, j)) {
                    priorities[i][j] = Integer.MIN_VALUE;
                    continue;
                } else {
                    priorities[i][j] = 0;
                }

                rightTest:
                for (int right = 1; right <= gamingAreaArray.length; right++) {
                    if (i + right < gamingAreaArray.length) {
                        int dalsi = gamingAreaArray[i + right][j];
                        if (dalsi == markOnMove) {
                            break rightTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= right - 1;
                            for (int k = 0; k < gamingAreaArray.length; k++) {
                                if (i + (right + k) < gamingAreaArray.length
                                        && gamingAreaArray[i + (right + k)][j] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && right == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && i + (right + k + 1) < gamingAreaArray.length
                                                && gamingAreaArray[i + (right + k + 1)][j] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break rightTest;
                                }
                            }
                        }
                    }
                }

                leftTest:
                for (int left = 1; left <= gamingAreaArray.length; left++) {
                    if (i - left >= 0) {
                        int dalsi = gamingAreaArray[i - left][j];
                        if (dalsi == markOnMove) {
                            break leftTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= left - 1;
                            for (int k = 0; k < gamingAreaArray.length; k++) {
                                if (i - (left + k) >= 0
                                        && gamingAreaArray[i - (left + k)][j] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && left == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && i - (left + k + 1) >= 0
                                                && gamingAreaArray[i - (left + k + 1)][j] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break leftTest;
                                }
                            }
                        }
                    }
                }

                downTest:
                for (int down = 1; down <= gamingAreaArray[0].length; down++) {
                    if (j + down < gamingAreaArray[0].length) {
                        int dalsi = gamingAreaArray[i][j + down];
                        if (dalsi == markOnMove) {
                            break downTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= down - 1;
                            for (int k = 0; k < gamingAreaArray[0].length; k++) {
                                if (j + (down + k) < gamingAreaArray[0].length
                                        && gamingAreaArray[i][j + (down + k)] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && down == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && j + (down + k + 1) < gamingAreaArray[0].length
                                                && gamingAreaArray[i][j + (down + k + 1)] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break downTest;
                                }
                            }
                        }
                    }
                }

                upTest:
                for (int up = 1; up <= gamingAreaArray[0].length; up++) {
                    if (j - up >= 0) {
                        int dalsi = gamingAreaArray[i][j - up];
                        if (dalsi == markOnMove) {
                            break upTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= up - 1;
                            for (int k = 0; k < gamingAreaArray[0].length; k++) {
                                if (j - (up + k) >= 0
                                        && gamingAreaArray[i][j - (up + k)] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && up == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && j - (up + k + 1) >= 0
                                                && gamingAreaArray[i][j - (up + k + 1)] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break upTest;
                                }
                            }
                        }
                    }
                }

                rightDownTest:
                for (int rightDown = 1; rightDown <= gamingAreaArray.length; rightDown++) {
                    if (i + rightDown < gamingAreaArray.length
                            && j + rightDown < gamingAreaArray[0].length) {
                        int dalsi = gamingAreaArray[i + rightDown][j + rightDown];
                        if (dalsi == markOnMove) {
                            break rightDownTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= rightDown - 1;
                            for (int k = 0; k < gamingAreaArray.length; k++) {
                                if (i + (rightDown + k) < gamingAreaArray.length
                                        && j + (rightDown + k) < gamingAreaArray[0].length
                                        && gamingAreaArray[i + (rightDown + k)][j + (rightDown + k)] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && rightDown == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && i + (rightDown + k + 1) < gamingAreaArray.length
                                                && j + (rightDown + k + 1) < gamingAreaArray[0].length
                                                && gamingAreaArray[i + (rightDown + k + 1)][j + (rightDown + k + 1)] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break rightDownTest;
                                }
                            }
                        }
                    }
                }

                rightUpTest:
                for (int rightUp = 1; rightUp <= gamingAreaArray.length; rightUp++) {
                    if (i + rightUp < gamingAreaArray.length
                            && j - rightUp >= 0) {
                        int dalsi = gamingAreaArray[i + rightUp][j - rightUp];
                        if (dalsi == markOnMove) {
                            break rightUpTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= rightUp - 1;
                            for (int k = 0; k < gamingAreaArray.length; k++) {
                                if (i + (rightUp + k) < gamingAreaArray.length
                                        && j - (rightUp + k) >= 0
                                        && gamingAreaArray[i + (rightUp + k)][j - (rightUp + k)] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && rightUp == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && i + (rightUp + k + 1) < gamingAreaArray.length
                                                && j - (rightUp + k + 1) >= 0
                                                && gamingAreaArray[i + (rightUp + k + 1)][j - (rightUp + k + 1)] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break rightUpTest;
                                }
                            }
                        }
                    }
                }

                leftDownTest:
                for (int leftDown = 1; leftDown <= gamingAreaArray.length; leftDown++) {
                    if (i - leftDown >= 0
                            && j + leftDown < gamingAreaArray[0].length) {
                        int dalsi = gamingAreaArray[i - leftDown][j + leftDown];
                        if (dalsi == markOnMove) {
                            break leftDownTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= leftDown - 1;
                            for (int k = 0; k < gamingAreaArray.length; k++) {
                                if (i - (leftDown + k) >= 0
                                        && j + (leftDown + k) < gamingAreaArray[0].length
                                        && gamingAreaArray[i - (leftDown + k)][j + (leftDown + k)] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && leftDown == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && i - (leftDown + k + 1) >= 0
                                                && j + (leftDown + k + 1) < gamingAreaArray[0].length
                                                && gamingAreaArray[i - (leftDown + k + 1)][j + (leftDown + k + 1)] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break leftDownTest;
                                }
                            }
                        }
                    }
                }

                leftUpTest:
                for (int leftUp = 1; leftUp <= gamingAreaArray.length; leftUp++) {
                    if (i - leftUp >= 0
                            && j - leftUp >= 0) {
                        int dalsi = gamingAreaArray[i - leftUp][j - leftUp];
                        if (dalsi == markOnMove) {
                            break leftUpTest;
                        } else if (dalsi == 0) {
                            continue;
                        } else {
                            priorities[i][j] -= leftUp - 1;
                            for (int k = 0; k < gamingAreaArray.length; k++) {
                                if (i - (leftUp + k) >= 0
                                        && j - (leftUp + k) >= 0
                                        && gamingAreaArray[i - (leftUp + k)][j - (leftUp + k)] == markEnemy) {
                                    if (k >= marksInRowToWin - 3 && leftUp == 1) {
                                        priorities[i][j] += 8;
                                        if (k == marksInRowToWin - 3
                                                && i - (leftUp + k + 1) >= 0
                                                && j - (leftUp + k + 1) >= 0
                                                && gamingAreaArray[i - (leftUp + k + 1)][j - (leftUp + k + 1)] == markOnMove) {
                                            priorities[i][j]--;
                                        }
                                    }
                                    priorities[i][j]++;
                                } else {
                                    break leftUpTest;
                                }
                            }
                        }
                    }
                }

                //if the ai has the chance to win (markInRowToWin - 2 marks is somewhere after each other), increase the priorities
                //right
                for (int right = 1; right < gamingAreaArray.length; right++) {
                    if (i + right < gamingAreaArray.length
                            && gamingAreaArray[i + right][j] == markOnMove) {
                        if (right >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //left
                for (int left = 1; left < gamingAreaArray.length; left++) {
                    if (i - left >= 0
                            && gamingAreaArray[i - left][j] == markOnMove) {
                        if (left >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //down
                for (int down = 1; down < gamingAreaArray.length; down++) {
                    if (j + down < gamingAreaArray[0].length
                            && gamingAreaArray[i][j + down] == markOnMove) {
                        if (down >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //up
                for (int up = 1; up < gamingAreaArray.length; up++) {
                    if (j - up >= 0
                            && gamingAreaArray[i][j - up] == markOnMove) {
                        if (up >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //rightdown
                for (int rightDown = 1; rightDown < gamingAreaArray.length; rightDown++) {
                    if (i + rightDown < gamingAreaArray.length
                            && j + rightDown < gamingAreaArray[0].length
                            && gamingAreaArray[i + rightDown][j + rightDown] == markOnMove) {
                        if (rightDown >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //rightup
                for (int rightUp = 1; rightUp < gamingAreaArray.length; rightUp++) {
                    if (i + rightUp < gamingAreaArray.length
                            && j - rightUp >= 0
                            && gamingAreaArray[i + rightUp][j - rightUp] == markOnMove) {
                        if (rightUp >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //leftdown
                for (int leftDown = 1; leftDown < gamingAreaArray.length; leftDown++) {
                    if (i - leftDown >= 0
                            && j + leftDown < gamingAreaArray[0].length
                            && gamingAreaArray[i - leftDown][j + leftDown] == markOnMove) {
                        if (leftDown >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
                //lefttup
                for (int leftDown = 1; leftDown < gamingAreaArray.length; leftDown++) {
                    if (i - leftDown >= 0
                            && j - leftDown >= 0
                            && gamingAreaArray[i - leftDown][j - leftDown] == markOnMove) {
                        if (leftDown >= marksInRowToWin - 2) {
                            priorities[i][j] += 15;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        //find greates priority
        int maxPriority = Integer.MIN_VALUE;
        for (int i = 0; i < priorities.length; i++) {
            for (int j = 0; j < priorities[0].length; j++) {
                if (priorities[i][j] > maxPriority) {
                    maxPriority = priorities[i][j];
                }
            }
        }
        //find moves with the greates priority and put them in list of good moves
        ArrayList<int[]> goodMoves = new ArrayList<int[]>();
        for (int i = 0; i < priorities.length; i++) {
            for (int j = 0; j < priorities[0].length; j++) {
                if (priorities[i][j] == maxPriority) {
                    goodMoves.add(new int[]{i, j, priorities[i][j]});
                }
            }
        }
        //look through good moves and find the best move (the closest to ai's marks) and increase their priorities
        for (int[] move : goodMoves) {
            int i = move[0];
            int j = move[1];
            //right
            for (int right = 1; right < gamingAreaArray.length; right++) {
                if (i + right < gamingAreaArray.length
                        && gamingAreaArray[i + right][j] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //left
            for (int left = 1; left < gamingAreaArray.length; left++) {
                if (i - left >= 0
                        && gamingAreaArray[i - left][j] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //down
            for (int down = 1; down < gamingAreaArray.length; down++) {
                if (j + down < gamingAreaArray[0].length
                        && gamingAreaArray[i][j + down] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //up
            for (int up = 1; up < gamingAreaArray.length; up++) {
                if (j - up >= 0
                        && gamingAreaArray[i][j - up] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //rightdown
            for (int rightDown = 1; rightDown < gamingAreaArray.length; rightDown++) {
                if (i + rightDown < gamingAreaArray.length
                        && j + rightDown < gamingAreaArray[0].length
                        && gamingAreaArray[i + rightDown][j + rightDown] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //rightup
            for (int rightUp = 1; rightUp < gamingAreaArray.length; rightUp++) {
                if (i + rightUp < gamingAreaArray.length
                        && j - rightUp >= 0
                        && gamingAreaArray[i + rightUp][j - rightUp] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //leftdown
            for (int leftDown = 1; leftDown < gamingAreaArray.length; leftDown++) {
                if (i - leftDown >= 0
                        && j + leftDown < gamingAreaArray[0].length
                        && gamingAreaArray[i - leftDown][j + leftDown] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
            //lefttup
            for (int leftDown = 1; leftDown < gamingAreaArray.length; leftDown++) {
                if (i - leftDown >= 0
                        && j - leftDown >= 0
                        && gamingAreaArray[i - leftDown][j - leftDown] == markOnMove) {
                    move[2]++;
                } else {
                    break;
                }
            }
        }

        //find greatest priority in good moves
        for(int[] move : goodMoves) {
            if(move[2] > maxPriority) {
                maxPriority = move[2];
            }
        }
        //find moves with the greates priority and put them in list of better moves
        ArrayList<int[]> betterMoves = new ArrayList<int[]>();
        for(int[] move : goodMoves) {
            if(move[2] == maxPriority) {
                betterMoves.add(move);
            }
        }
       
        //randomly take move from better moves
        Random random = new Random();
        return betterMoves.get(random.nextInt(betterMoves.size()));
    }

    @Override
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name = ");
        sb.append(name);
        sb.append("\n");
        sb.append("mark = ");
        sb.append(mark);
        sb.append("\n");
        sb.append("ai = ");
        sb.append(ai);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void loadFromString(String saveString) throws IOException {
        IOException error = new IOException("File corrupted.");
        Scanner sc = new Scanner(saveString);
        String toFind;
        toFind = "name = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNext()) {
                name = sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
        toFind = "mark = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNextInt()) {
                mark = sc.nextInt();
                sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
        toFind = "ai = ";
        if (sc.findInLine(toFind) != null) {
            if (sc.hasNextBoolean()) {
                ai = sc.nextBoolean();
                sc.nextLine();
            } else {
                throw error;
            }
        } else {
            throw error;
        }
    }

}
