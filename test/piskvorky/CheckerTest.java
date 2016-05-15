package piskvorky;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class, test checkWin method from class Checker
 * 
 * @author Ivan Kratochvíl
 */
public class CheckerTest {
    
    public CheckerTest() {
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

    @Test
    /**
     * Test, tests checkWin() in row
     */
    public void testCheckWinRow() {
        System.out.println("checkWinRow");
        int x = 1;
        int y = 1;
        Player player = new Player("Tester", 1, false);
        GamingArea gamingArea = new GamingArea(5, 5);
        Checker instance = new Checker(3, gamingArea);
        gamingArea.markSquare(0, 1, 1);
        gamingArea.markSquare(1, 1, 1);
        gamingArea.markSquare(2, 1, 1);
        boolean expResult = true;
        boolean result = instance.checkWin(x, y, player);
        assertEquals(expResult, result);
    }
    
    @Test
    /**
     * Test, tests checkWin() in column
     */
    public void testCheckWinColumn() {
        System.out.println("checkWinColumn");
        int x = 1;
        int y = 1;
        Player player = new Player("Tester", 1, false);
        GamingArea gamingArea = new GamingArea(5, 5);
        Checker instance = new Checker(3, gamingArea);
        gamingArea.markSquare(1, 0, 1);
        gamingArea.markSquare(1, 1, 1);
        gamingArea.markSquare(1, 2, 1);
        boolean expResult = true;
        boolean result = instance.checkWin(x, y, player);
        assertEquals(expResult, result);
    }
    
    @Test
    /**
     * Test, tests checkWin() in first axis (left up -> right down)
     */
    public void testCheckWinAxis1() {
        System.out.println("checkWinAxis1");
        int x = 1;
        int y = 1;
        Player player = new Player("Tester", 1, false);
        GamingArea gamingArea = new GamingArea(5, 5);
        Checker instance = new Checker(3, gamingArea);
        gamingArea.markSquare(0, 0, 1);
        gamingArea.markSquare(1, 1, 1);
        gamingArea.markSquare(2, 2, 1);
        boolean expResult = true;
        boolean result = instance.checkWin(x, y, player);
        assertEquals(expResult, result);
    }
    
    @Test
    /**
     * Test, tests checkWin() in second axis (left down -> right up)
     */
    public void testCheckWinAxis2() {
        System.out.println("checkWinAxis2");
        int x = 1;
        int y = 1;
        Player player = new Player("Tester", 1, false);
        GamingArea gamingArea = new GamingArea(5, 5);
        Checker instance = new Checker(3, gamingArea);
        gamingArea.markSquare(2, 0, 1);
        gamingArea.markSquare(1, 1, 1);
        gamingArea.markSquare(0, 2, 1);
        boolean expResult = true;
        boolean result = instance.checkWin(x, y, player);
        assertEquals(expResult, result);
    }
    

    @Test
    /**
     * Negative test of checkWin()
     */
    public void testCheckWinFalse() {
        System.out.println("checkWinFalse");
        int x = 1;
        int y = 1;
        Player player = new Player("Tester", 1, false);
        GamingArea gamingArea = new GamingArea(5, 5);
        Checker instance = new Checker(3, gamingArea);
        gamingArea.markSquare(0, 1, 1);
        gamingArea.markSquare(1, 2, 1);
        gamingArea.markSquare(1, 1, 1);
        gamingArea.markSquare(4, 3, 1);
        boolean expResult = false;
        boolean result = instance.checkWin(x, y, player);
        assertEquals(expResult, result);
    }
    
}
