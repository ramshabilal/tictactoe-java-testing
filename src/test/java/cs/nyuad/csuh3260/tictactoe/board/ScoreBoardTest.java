package cs.nyuad.csuh3260.tictactoe.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.nyuad.csuh3260.tictactoe.board.ScoreBoard;

public class ScoreBoardTest {

    private ScoreBoard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new ScoreBoard();
    }

    // Test if initial scores are all set to zero
    @Test
    public void testInitialScores() {
        assertEquals(0, scoreboard.xWins);
        assertEquals(0, scoreboard.oWins);
        assertEquals(0, scoreboard.ties);
    }


        // Test updating scores when X wins
    @Test
    public void testUpdateScores_XWins() {
        scoreboard.updateScores(Board.Player.X); // X wins
        assertEquals(1, scoreboard.xWins);       // X wins incremented
        assertEquals(0, scoreboard.oWins);       // O wins remain 0
        assertEquals(0, scoreboard.ties);        // Ties remain 0
    }

    // Test updating scores when O wins
    @Test
    public void testUpdateScores_OWins() {
        scoreboard.updateScores(Board.Player.O);
        assertEquals(0, scoreboard.xWins);
        assertEquals(1, scoreboard.oWins);
        assertEquals(0, scoreboard.ties);
    }

    // Test updating scores when the game is a tie
    @Test
    public void testUpdateScores_Tie() {
        scoreboard.updateScores(null);
        assertEquals(0, scoreboard.xWins);
        assertEquals(0, scoreboard.oWins);
        assertEquals(1, scoreboard.ties);
    }
}