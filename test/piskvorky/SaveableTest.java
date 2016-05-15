/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piskvorky;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class, test methods toSaveString() and loadFromString() on all classes
 * which implements Saveable interface
 *
 * @author Ivan Kratochvíl
 */
public class SaveableTest {

    public SaveableTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toSaveString and loadFromString methods of class Checker
     */
    @Test
    public void testCheckerSaveLoad() {
        System.out.println("Checker save/load test");
        Checker checker = new Checker(1, new GamingArea(1, 1));
        String save = checker.toSaveString();
        String expSave = "marksInRowToWin = 1\nwin = false\n";
        assertEquals(expSave, save);
        Checker newChecker = new Checker(5, new GamingArea(5, 5));
        try {
            newChecker.loadFromString(save);
        } catch (IOException ex) {
            fail(ex.toString());
        }
        assertEquals(newChecker.getMarksInRowToWin(), checker.getMarksInRowToWin());
        assertEquals(newChecker.isWin(), checker.isWin());
        assertNotEquals(newChecker.getMarksInRowToWin(), 5);
    }

    @Test
    /**
     * Test of toSaveString and loadFromString methods of class GamingArea
     */
    public void testGamingAreaSaveLoad() {
        System.out.println("GamingArea save/load test");
        GamingArea gamingArea = new GamingArea(3, 3);
        gamingArea.markSquare(1, 1, 1);
        String save = gamingArea.toSaveString();
        String expSave = "width = 3\nheight = 3\n0 0 0 \n0 1 0 \n0 0 0 \n";
        assertEquals(expSave, save);
        GamingArea newGamingArea = new GamingArea(2, 2);
        try {
            newGamingArea.loadFromString(save);
        } catch (IOException ex) {
            fail(ex.toString());
        }
        assertEquals(newGamingArea.getWidth(), gamingArea.getWidth());
        assertEquals(newGamingArea.getHeight(), newGamingArea.getHeight());
        assertArrayEquals(newGamingArea.getGameGrid(), gamingArea.getGameGrid());
        assertNotEquals(newGamingArea.getWidth(), 2);
        assertNotEquals(newGamingArea.getHeight(), 2);
    }
    
    @Test
    /**
     * Test of toSaveString and loadFromString methods of class MoveHistory 
     */
    public void testMoveHistorySaveLoad() {
        System.out.println("MoveHistory save/load test");
        MoveHistory moveHistory = new MoveHistory();
        moveHistory.add(new Move(2, 2, 1));
        moveHistory.add(new Move(1, 1, 1));
        moveHistory.add(new Move(1, 2, 2));
        String save = moveHistory.toSaveString();
        String expSave = "2 2 1\n1 1 1\n1 2 2\n";
        assertEquals(expSave, save);
        MoveHistory newMoveHistory = new MoveHistory();
        try {
            newMoveHistory.loadFromString(save);
        } catch (IOException ex) {
            fail(ex.toString());
        }
        assertEquals(moveHistory.getMoveOnIteratorValue(), newMoveHistory.getMoveOnIteratorValue());
        assertEquals(moveHistory.getMoveOnIteratorValue(), newMoveHistory.getMoveOnIteratorValue());
        assertNotEquals(moveHistory.getMoveOnIteratorValue(), new Move(1, 1, 1));
        assertEquals(moveHistory.getMoveOnIteratorValue(), null);
    }
    
    @Test
    /**
     * Test of toSaveString and loadFromString methods of class Player
     */
    public void testPlayerSaveLoad() {
        System.out.println("Player save/load test");
        Player player = new Player("Tes ter", 1, false);
        String save = player.toSaveString();
        String expSave = "name = Tes ter\nmark = 1\nai = false\n";
        assertEquals(expSave, save);
        Player newPlayer = new Player("Tester", 2, true);
        try {
            newPlayer.loadFromString(save);
        } catch (IOException ex) {
            fail(ex.toString());
        }
        assertEquals(newPlayer.getName(), player.getName());
        assertEquals(newPlayer.getMark(), player.getMark());
        assertEquals(newPlayer.isAi(), player.isAi());
        assertNotEquals(newPlayer.getName(), "Tester");
        assertNotEquals(newPlayer.getMark(), 2);
        assertNotEquals(newPlayer.isAi(), true);
        
    }
}
