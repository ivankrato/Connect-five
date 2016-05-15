/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piskvorky;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class, tests ai priorities.
 *
 * @author Ivan Kratochvíl
 */
public class AiTest {

    public AiTest() {
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
     * Test of playAi method, of class Player.
     */
    public void testPlayAi() {
        System.out.println("playAi");
        GamingArea gamingArea = new GamingArea(5, 5);
        Player ai = new Player("AI", 2, true);
        gamingArea.markSquare(1, 2, 1);
        gamingArea.markSquare(2, 2, 1);
        gamingArea.markSquare(3, 2, 1);
        gamingArea.markSquare(4, 1, 2);
        int[] expResult = new int[]{4, 2, 12}; //test of blocking
        int[] result = ai.playAi(gamingArea, ai.getMark(), 5);
        assertArrayEquals(expResult, result);

        gamingArea.markSquare(2, 1, 2);
        gamingArea.markSquare(3, 1, 2);
        expResult = new int[]{1, 1, 20}; //test of attacking
        result = ai.playAi(gamingArea, ai.getMark(), 5);
        assertArrayEquals(expResult, result);

        gamingArea = new GamingArea(5, 5);
        gamingArea.markSquare(2, 2, 1);
        result = ai.playAi(gamingArea, ai.getMark(), 5);
        assertNotEquals(0, result[0]); //the ai should never return these coordinates
        assertNotEquals(4, result[0]);
        assertNotEquals(0, result[1]);
        assertNotEquals(4, result[1]);
    }
}
